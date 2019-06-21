package ph.edu.mapua.braille3d.Student;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import ph.edu.mapua.braille3d.Others.Assessment;
import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.R;

public class ExerciseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView qNum;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Random rnd;
    private int currentQ;
    private int currentA;
    private char q;
    private char[][] a;
    private char[] b;
    private int[] retries;
    private Exercise exercise;
    private String eID;
    private Chronometer timer;
    private long elapsed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("assessments");
        exercise = (Exercise) getIntent().getSerializableExtra("exerObject");
        eID = getIntent().getStringExtra("exerID");
        timer = findViewById(R.id.chrono);
        timer.setBase(SystemClock.elapsedRealtime() - elapsed);

        rnd = new Random();
        if(exercise.difficulty.equals("Easy") || exercise.difficulty.equals("Normal") || exercise.difficulty.equals("5 digits")) {
            a = new char[5][3];
            b = new char[] {exercise.item1.charAt(0), exercise.item2.charAt(0),
                    exercise.item3.charAt(0), exercise.item4.charAt(0), exercise.item5.charAt(0)};
            retries = new int[5];
            for(int x = 0; x < 5; x++)
                    retries[x] = 0;
        }else {
            a = new char[10][3];
            b = new char[] {exercise.item1.charAt(0), exercise.item2.charAt(0),
                    exercise.item3.charAt(0), exercise.item4.charAt(0), exercise.item5.charAt(0),
                    exercise.item6.charAt(0), exercise.item7.charAt(0), exercise.item8.charAt(0),
                    exercise.item9.charAt(0), exercise.item10.charAt(0)};
            retries = new int[10];
            for(int x = 0; x < 10; x++)
                retries[x] = 0;
        }

        currentQ = 0;
        currentA = 0;

        qNum = (TextView)findViewById(R.id.txtViewQNum);
        choice1 = (Button)findViewById(R.id.btnChoice1);
        choice2 = (Button)findViewById(R.id.btnChoice2);
        choice3 = (Button)findViewById(R.id.btnChoice3);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));

        qNum.setText(exercise.name +": Question " + (currentQ + 1)  + " out of " + (b.length));
        timer.start();
        setAnswers();
        startQuiz();
    }

    public void toMainActivity(View view) {
        finish();
    }

    private void startQuiz() {
        if(currentQ < b.length) {
            retries[currentQ]++;
            setBraille(getBitPattern(String.valueOf(b[currentQ])));
            choice1.setText(String.valueOf(a[currentQ][0]));
            choice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a[currentQ][0] == b[currentQ]) {
                        Toast.makeText(ExerciseActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                        qNum.setText(exercise.name +": Question " + (currentQ + 2)  + " out of " + (b.length));
                        currentQ++;
                        startQuiz();
                    } else {
                        Toast.makeText(ExerciseActivity.this, "Wrong Answer, Try Again!", Toast.LENGTH_SHORT).show();
                        retries[currentQ]++;
                    }
                }
            });

            choice2.setText(String.valueOf(a[currentQ][1]));
            choice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a[currentQ][1] == b[currentQ]) {
                        Toast.makeText(ExerciseActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                        qNum.setText(exercise.name +": Question " + (currentQ + 2)  + " out of " + (b.length));
                        currentQ++;
                        startQuiz();
                    } else {
                        Toast.makeText(ExerciseActivity.this, "Wrong Answer, Try Again!", Toast.LENGTH_SHORT).show();
                        retries[currentQ]++;
                    }
                }
            });

            choice3.setText(String.valueOf(a[currentQ][2]));
            choice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a[currentQ][2] == b[currentQ]) {
                        Toast.makeText(ExerciseActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                        qNum.setText(exercise.name +": Question " + (currentQ + 2)  + " out of " + (b.length));
                        currentQ++;
                        startQuiz();
                    } else {
                        Toast.makeText(ExerciseActivity.this, "Wrong Answer, Try Again!", Toast.LENGTH_SHORT).show();
                        retries[currentQ]++;
                    }
                }
            });
        } else {
            timer.stop();
            elapsed = SystemClock.elapsedRealtime() - timer.getBase();
            String secondsInStrings = String.valueOf(elapsed);
            secondsInStrings = secondsInStrings.substring(0, secondsInStrings.length() - 3);
            int minutes = Integer.parseInt(secondsInStrings)/60;
            int seconds = Integer.parseInt(secondsInStrings)%60;

            Assessment assessment;
            String duration;
            if(seconds < 10)
                duration = minutes + ":0" + seconds;
            else
                duration = minutes + ":" + seconds;
            if(b.length == 5)
                assessment = new Assessment(exercise.assigned, exercise.owner, exercise.name, duration, retries[0],
                                        retries[1], retries[2], retries[3], retries[4]);
            else
                assessment = new Assessment(exercise.assigned, exercise.owner, exercise.name, duration, retries[0],
                        retries[1], retries[2], retries[3], retries[4], retries[5], retries[6], retries[7], retries[8], retries[9]);
            mDatabase.push().setValue(assessment);

            mDatabase = FirebaseDatabase.getInstance().getReference().child("exercises").child(eID).child("status");
            mDatabase.setValue("Finished");

            Intent myIntent = new Intent(ExerciseActivity.this, ExerciseResultActivity.class);
            ExerciseActivity.this.startActivity(myIntent);
        }
    }

    private  void  setAnswers() {
        int n;
        for(int w = 0; w < b.length; w++) {
            n = rnd.nextInt((2) + 1);
            a[w][n] = b[w];
        }

        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (exercise.module1 && exercise.module2 && exercise.module3) {
            charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        } else if (exercise.module1 && exercise.module2) {
            charSet = "ABCDEFGHIJKLMNOPQRST";
        } else if(exercise.module1 && exercise.module3) {
            charSet = "ABCDEFGHIJUVWXYZ";
        } else if(exercise.module2 && exercise.module3) {
            charSet = "KLMNOPQRSTUVWXYZ";
        } else if(exercise.module1) {
            charSet = "ABCDEFGHIJ";
        } else if(exercise.module2) {
            charSet = "KLMNOPQRST";
        } else if(exercise.module3) {
            charSet = "UVWXYZ";
        } else if(exercise.num1 || exercise.num2) {
            charSet = "0123456789";
        }
        int length = charSet.length();

        for(int w = 0; w < b.length; w++) {
            for(int x = 0; x < 3; x++) {
                //q = (char) (rnd.nextInt(10) + 'A');
                q = charSet.charAt(rnd.nextInt(length));
                for(int y = 0; y < 3; y++)
                    if(a[w][y] == q) {
                        //q = (char) (rnd.nextInt(10) + 'A');
                        q = charSet.charAt(rnd.nextInt(length));
                        y = -1;
                    }
                if(a[w][x] != b[w])
                    a[w][x] = q;
            }
        }
    }

    private void setBraille(int[] pattern) {
        ImageView dot;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 50f);
        LinearLayout row;

        row  = (LinearLayout)findViewById(R.id.topRowLayout);
        row.removeAllViews();
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
        row.removeAllViews();
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
        row.removeAllViews();
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
