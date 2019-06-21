package ph.edu.mapua.braille3d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ExerciseActivity extends AppCompatActivity {

    TextView qNum;
    Button choice1;
    Button choice2;
    Button choice3;
    Random rnd;
    int currentQ;
    int currentA;
    char q;
    char[][] a;
    char[] b;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        rnd = new Random();
        a = new char[5][3];
        b = new char[] {'C', 'A', 'G', 'D', 'H'};
        currentQ = 0;
        currentA = 0;

        qNum = (TextView)findViewById(R.id.txtViewQNum);
        choice1 = (Button)findViewById(R.id.btnChoice1);
        choice2 = (Button)findViewById(R.id.btnChoice2);
        choice3 = (Button)findViewById(R.id.btnChoice3);

        setAnswers();
        startQuiz();
    }

    public void toMainActivity(View view) {
        finish();
    }

    private void startQuiz() {
        if(currentQ < 5) {
            setBraille(getBitPattern(String.valueOf(b[currentQ])));
            choice1.setText(String.valueOf(a[currentQ][0]));
            choice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a[currentQ][0] == b[currentQ]) {
                        Toast.makeText(ExerciseActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                        qNum.setText("Exercise 1: Question " + (currentQ + 2)  + " out of 5");
                        currentQ++;
                        startQuiz();
                    } else {
                        Toast.makeText(ExerciseActivity.this, "Wrong Answer, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            choice2.setText(String.valueOf(a[currentQ][1]));
            choice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a[currentQ][1] == b[currentQ]) {
                        Toast.makeText(ExerciseActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                        qNum.setText("Exercise 1: Question " + (currentQ + 2)  + " out of 5");
                        currentQ++;
                        startQuiz();
                    } else {
                        Toast.makeText(ExerciseActivity.this, "Wrong Answer, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            choice3.setText(String.valueOf(a[currentQ][2]));
            choice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a[currentQ][2] == b[currentQ]) {
                        Toast.makeText(ExerciseActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                        qNum.setText("Exercise 1: Question " + (currentQ + 2)  + " out of 5");
                        currentQ++;
                        startQuiz();
                    } else {
                        Toast.makeText(ExerciseActivity.this, "Wrong Answer, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Intent myIntent = new Intent(ExerciseActivity.this, ExerciseResultActivity.class);
            ExerciseActivity.this.startActivity(myIntent);
        }
    }

    private  void  setAnswers() {
        int n;
        for(int w = 0; w < 5; w++) {
            n = rnd.nextInt((2) + 1);
            a[w][n] = b[w];
        }

        for(int w = 0; w < 5; w++) {
            for(int x = 0; x < 3; x++) {
                q = (char) (rnd.nextInt(10) + 'A');
                for(int y = 0; y < 3; y++)
                    if(a[w][y] == q) {
                        q = (char) (rnd.nextInt(10) + 'A');
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
