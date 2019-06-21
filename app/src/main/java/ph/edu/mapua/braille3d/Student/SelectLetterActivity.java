package ph.edu.mapua.braille3d.Student;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import ph.edu.mapua.braille3d.R;

public class SelectLetterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_letter);

        Intent intent = getIntent();
        String value = intent.getStringExtra("set");
        String[] letters1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
                 letters2 = {"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"},
                 letters3 = {"U", "V", "W", "X", "Y", "Z"},
                  numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"},
                 selectedSet;

        if(value.equals("aToJ"))
            selectedSet = letters1;
        else if(value.equals("kToT"))
            selectedSet = letters2;
        else if(value.equals("uToZ"))
            selectedSet = letters3;
        else
            selectedSet = numbers;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(60));
        params.setMargins(dpToPx(15), dpToPx(10), dpToPx(15), 0);

        for(String a : selectedSet) {
            LinearLayout container = (LinearLayout)findViewById(R.id.scrViewLayout);
            final String letterToPass = a;

            Button nButton = new Button(this);
            nButton.setText(a);
            nButton.setContentDescription("Practice the Braille pattern for letter " + a);
            nButton.setLayoutParams(params);
            nButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(SelectLetterActivity.this, LearningActivity.class);
                    myIntent.putExtra("letter", letterToPass);
                    SelectLetterActivity.this.startActivity(myIntent);
                }
            });
            container.addView(nButton);
        }
    }

    public void toSelectLevelActivity(View view) {
        finish();
    }

    private int dpToPx(int dp) {
        Resources r = this.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }
}
