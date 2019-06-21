package ph.edu.mapua.braille3d.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.Others.User;
import ph.edu.mapua.braille3d.R;

public class AssignExerciseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference aDatabase;
    private DatabaseReference nDatabase;
    private FirebaseUser mAuth;
    private Button confirmBtn;
    private Button backBtn;
    private LinearLayout studentListLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_exercise);

        final ArrayList<String> items = getIntent().getStringArrayListExtra("items");
        final String difficulty = getIntent().getStringExtra("difficulty");
        final String name = getIntent().getStringExtra("name");
        final String module = getIntent().getStringExtra("module");

        backBtn = (Button) findViewById(R.id.backToCreate_btn);
        confirmBtn = (Button) findViewById(R.id.confirmAssign_btn);
        studentListLayout = (LinearLayout) findViewById(R.id.studentList_layout);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        aDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
        aDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User myAcc = dataSnapshot.getValue(User.class);

                mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                mDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);

                        if(myAcc.full_name.equals(user.teacher)) {
                            AppCompatCheckBox c = new AppCompatCheckBox(AssignExerciseActivity.this);
                            c.setText(user.full_name);
                            studentListLayout.addView(c);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> selectedStud = new ArrayList<>();
                        int childCount = studentListLayout.getChildCount();

                        for(int x = 0; x < childCount; x++) {
                            final CheckBox c = (CheckBox) studentListLayout.getChildAt(x);
                            if(c.isChecked())
                                selectedStud.add(c.getText().toString());
                        }

                        if(difficulty.equals("Hard") || difficulty.equals("10 digits")) {
                            for(int y = 0; y < selectedStud.size(); y++) {
                                Exercise ex = new Exercise(name, difficulty, selectedStud.get(y), myAcc.full_name, "Ongoing",
                                        items.get(0), items.get(1), items.get(2), items.get(3), items.get(4), items.get(5),
                                         items.get(6), items.get(7), items.get(8), items.get(9));
                                if(difficulty.equals("Easy") && module.equals("Module 1"))
                                    ex.module1 = true;
                                else if(difficulty.equals("Easy") && module.equals("Module 2"))
                                    ex.module2 = true;
                                else if(difficulty.equals("Easy") && module.equals("Module 3"))
                                    ex.module3 = true;
                                else if(difficulty.equals("Normal") && module.equals("Module 1 and 2")) {
                                    ex.module1 = true;
                                    ex.module2 = true;
                                }else if(difficulty.equals("Normal") && module.equals("Module 1 and 3")) {
                                    ex.module1 = true;
                                    ex.module3 = true;
                                }else if(difficulty.equals("Normal") && module.equals("Module 2 and 3")) {
                                    ex.module2 = true;
                                    ex.module3 = true;
                                }else if(difficulty.equals("Hard")) {
                                    ex.module1 = true;
                                    ex.module2 = true;
                                    ex.module3 = true;
                                }else if(difficulty.equals("5 digits") || difficulty.equals("10 digits")) {
                                    ex.num1 = true;
                                    ex.num2 = true;
                                }
                                nDatabase = FirebaseDatabase.getInstance().getReference().child("exercises").push();
                                nDatabase.setValue(ex);
                            }
                        } else {
                            for(int y = 0; y < selectedStud.size(); y++) {
                                Exercise ex = new Exercise(name, difficulty, selectedStud.get(y), myAcc.full_name, "Ongoing",
                                        items.get(0), items.get(1), items.get(2), items.get(3), items.get(4));
                                if(difficulty.equals("Easy") && module.equals("Module 1"))
                                    ex.module1 = true;
                                else if(difficulty.equals("Easy") && module.equals("Module 2"))
                                    ex.module2 = true;
                                else if(difficulty.equals("Easy") && module.equals("Module 3"))
                                    ex.module3 = true;
                                else if(difficulty.equals("Normal") && module.equals("Module 1 and 2")) {
                                    ex.module1 = true;
                                    ex.module2 = true;
                                }else if(difficulty.equals("Normal") && module.equals("Module 1 and 3")) {
                                    ex.module1 = true;
                                    ex.module3 = true;
                                }else if(difficulty.equals("Normal") && module.equals("Module 2 and 3")) {
                                    ex.module2 = true;
                                    ex.module3 = true;
                                }else if(difficulty.equals("Hard")) {
                                    ex.module1 = true;
                                    ex.module2 = true;
                                    ex.module3 = true;
                                }else if(difficulty.equals("5 digits")) {
                                    ex.num1 = true;
                                    ex.num2 = true;
                                }
                                nDatabase = FirebaseDatabase.getInstance().getReference().child("exercises").push();
                                nDatabase.setValue(ex);
                            }
                        }
                        Toast.makeText(AssignExerciseActivity.this, "Exercise assigned!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent("finish_activity");
                        sendBroadcast(myIntent);
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
