package ph.edu.mapua.braille3d.Student;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;

import ph.edu.mapua.braille3d.Others.ExampleWords;
import ph.edu.mapua.braille3d.R;

public class LearningActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private Button downloadBtn;
    private Button nextWordBtn;
    private TextView exampleTxt;
    private final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        downloadBtn = (Button) findViewById(R.id.download3d_btn);
        nextWordBtn = (Button) findViewById(R.id.example_word_btn);
        exampleTxt = (TextView) findViewById(R.id.textViewExWord);

        final int[] pattern;
        Intent intent = getIntent();
        final String passedLetter = intent.getStringExtra("letter");
        pattern = getBitPattern(passedLetter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("word_examples").child(passedLetter);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!StringUtils.isNumeric(passedLetter)) {
                    final ExampleWords exampleWords = dataSnapshot.getValue(ExampleWords.class);
                    //final String[] examples = {exampleWords.example1, exampleWords.example2, exampleWords.example3,
                    //                        exampleWords.example4, exampleWords.example5};
                    final ArrayList<String> examples = new ArrayList<>();
                    examples.add(exampleWords.example1);
                    examples.add(exampleWords.example2);
                    examples.add(exampleWords.example3);
                    examples.add(exampleWords.example4);
                    examples.add(exampleWords.example5);

                    exampleTxt.setText(passedLetter + " for " + (examples.get(0).toString()));
                    nextWordBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int currentIndex = examples.indexOf(exampleTxt.getText().toString().split("\\s+")[2]);
                            if(currentIndex + 1 != examples.size()) {
                                if (examples.get(currentIndex + 1) != null)
                                    exampleTxt.setText(passedLetter + " for " + (examples.get(currentIndex + 1)));
                                else
                                    exampleTxt.setText(passedLetter + " for " + (examples.get(0)));
                            } else
                                exampleTxt.setText(passedLetter + " for " + (examples.get(0)));
                        }
                    });

                    storageRef = FirebaseStorage.getInstance().getReference().child("models/" + exampleWords.example1.toLowerCase() + ".stl");
                    downloadBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(LearningActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                // If do not grant write external storage permission.
                                if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
                                {
                                    // Request user to grant write external storage permission.
                                    ActivityCompat.requestPermissions(LearningActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                                }

                                final File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), exampleWords.example1.toLowerCase() + ".stl");

                                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        // Local temp file has been created
                                        // use localFile
                                        Toast.makeText(LearningActivity.this, "Download complete! File saved at " + localFile, Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                        Toast.makeText(LearningActivity.this, "Download fail.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getBytesTransferred()
                                        // taskSnapshot.getTotalByteCount();
                                    }
                                });
                            } catch (Exception ex) {

                            }
                        }
                    });
                } else {
                    downloadBtn.setEnabled(false);
                    nextWordBtn.setEnabled(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView tvLetter = (TextView)findViewById(R.id.txtViewLetter);
        tvLetter.setText(String.format("%s %s", tvLetter.getText(), passedLetter));

        ImageView dot;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 50f);
        LinearLayout row;

        row  = (LinearLayout)findViewById(R.id.topRowLayout);
        dot = new ImageView(this);
        dot.setImageResource(getDot(pattern[0]));
        dot.setContentDescription("Dot number 1, " + getContDesc(pattern[0]));
        dot.setLayoutParams(params);
        row.addView(dot);

        dot = new ImageView(this);
        dot.setImageResource(getDot(pattern[3]));
        dot.setContentDescription("Dot number 4, " + getContDesc(pattern[3]));
        dot.setLayoutParams(params);
        row.addView(dot);

        row  = (LinearLayout)findViewById(R.id.middlwRowLayout);
        dot = new ImageView(this);
        dot.setImageResource(getDot(pattern[1]));
        dot.setContentDescription("Dot number 2, " + getContDesc(pattern[1]));
        dot.setLayoutParams(params);
        row.addView(dot);

        dot = new ImageView(this);
        dot.setImageResource(getDot(pattern[4]));
        dot.setContentDescription("Dot number 5, " + getContDesc(pattern[4]));
        dot.setLayoutParams(params);
        row.addView(dot);

        row  = (LinearLayout)findViewById(R.id.bottomRowLayout);
        dot = new ImageView(this);
        dot.setImageResource(getDot(pattern[2]));
        dot.setContentDescription("Dot number 3, " + getContDesc(pattern[2]));
        dot.setLayoutParams(params);
        row.addView(dot);

        dot = new ImageView(this);
        dot.setImageResource(getDot(pattern[5]));
        dot.setContentDescription("Dot number 6, " + getContDesc(pattern[5]));
        dot.setLayoutParams(params);
        row.addView(dot);

        //TextView txtView = (TextView)findViewById(R.id.textViewExWord);
        //if(!StringUtils.isNumeric(passedLetter))
        //    txtView.setText("A word that starts with " + passedLetter + " is " + getWord(passedLetter));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "You grant write external storage permission. Please click original button again to continue.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void toSelectLetterActivity(View view) {
        finish();
    }

    private String getContDesc(int bit) {
        if(bit == 0)
            return "flat.";
        else
            return "raised";
    }

    private int getDot(int bit) {
        if(bit == 0)
            return R.drawable.graydot;
        else
            return R.drawable.blackdot;
    }

    private String getWord(String letter) {
        switch (letter) {

            case "A": return "Apple";
            case "B": return "Banana";
            case "C": return "Candy";
            case "D": return "Door";
            case "E": return "Egg";
            case "F": return "Fish";
            case "G": return "Gold";
            case "H": return "Heart";
            case "I": return "Ice";
            case "J": return "Jackfruit";
            case "K": return "Key";
            case "L": return "Leaf";
            case "M": return "Mouse";
            case "N": return "Nuts";
            case "O": return "Orange";
            case "P": return "Paper";
            case "Q": return "Queen";
            case "R": return "Rock";
            case "S": return "Shoe";
            case "T": return "Table";
            case "U": return "Umbrella";
            case "V": return "Vegetable";
            case "W": return "Watch";
            case "X": return "Xylophone";
            case "Y": return "Yellow";
            case "Z": return "Zebra";

        }

        return "None";
    }

    private int[] getBitPattern(String letter) {
        switch (letter) {

            case "A": return new int[]{1, 0, 0, 0, 0, 0};
            case "B": return new int[]{1, 1, 0, 0, 0, 0};
            case "C": return new int[]{1, 0, 0, 1, 0, 0};
            case "D": return new int[]{1, 0, 0, 1, 1, 0};
            case "E": return new int[]{1, 0, 0, 0, 1, 0};
            case "F": return new int[]{1, 1, 0, 1, 0, 0};
            case "G": return new int[]{1, 1, 0, 1, 1, 0};
            case "H": return new int[]{1, 1, 0, 0, 1, 0};
            case "I": return new int[]{0, 1, 0, 1, 0, 0};
            case "J": return new int[]{0, 1, 0, 1, 1, 0};
            case "K": return new int[]{1, 0, 1, 0, 0, 0};
            case "L": return new int[]{1, 1, 1, 0, 0, 0};
            case "M": return new int[]{1, 0, 1, 1, 0, 0};
            case "N": return new int[]{1, 0, 1, 1, 1, 0};
            case "O": return new int[]{1, 0, 1, 0, 1, 0};
            case "P": return new int[]{1, 1, 1, 1, 0, 0};
            case "Q": return new int[]{1, 1, 1, 1, 1, 0};
            case "R": return new int[]{1, 1, 1, 0, 1, 0};
            case "S": return new int[]{0, 1, 1, 1, 0, 0};
            case "T": return new int[]{0, 1, 1, 1, 1, 0};
            case "U": return new int[]{1, 0, 1, 0, 0, 1};
            case "V": return new int[]{1, 1, 1, 0, 0, 1};
            case "W": return new int[]{0, 1, 0, 1, 1, 1};
            case "X": return new int[]{1, 0, 1, 1, 0, 1};
            case "Y": return new int[]{1, 0, 1, 1, 1, 1};
            case "Z": return new int[]{1, 0, 1, 0, 1, 1};
            case "0": return new int[]{0, 1, 0, 1, 1, 0};
            case "1": return new int[]{1, 0, 0, 0, 0, 0};
            case "2": return new int[]{1, 1, 0, 0, 0, 0};
            case "3": return new int[]{1, 0, 0, 1, 0, 0};
            case "4": return new int[]{1, 0, 0, 1, 1, 0};
            case "5": return new int[]{1, 0, 0, 0, 1, 0};
            case "6": return new int[]{1, 1, 0, 1, 0, 0};
            case "7": return new int[]{1, 1, 0, 1, 1, 0};
            case "8": return new int[]{1, 1, 0, 0, 1, 0};
            case "9": return new int[]{0, 1, 0, 1, 0, 0};
        }

        return new int[]{0, 0, 0, 0, 0, 0};
    }
}
