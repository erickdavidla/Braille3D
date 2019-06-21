package ph.edu.mapua.braille3d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class LearningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);


        int[] pattern;
        Intent intent = getIntent();
        String passedLetter = intent.getStringExtra("letter");
        pattern = getBitPattern(passedLetter);

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

        TextView txtView = (TextView)findViewById(R.id.textViewExWord);
        if(!StringUtils.isNumeric(passedLetter))
            txtView.setText("A word that starts with " + passedLetter + " is " + getWord(passedLetter));
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
