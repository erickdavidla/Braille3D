package ph.edu.mapua.braille3d.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ph.edu.mapua.braille3d.R;

public class Exercise2Activity extends AppCompatActivity {

    private Intent myIntent;

    private Button newExBtn;
    private Button assignExBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);

        newExBtn = (Button)findViewById(R.id.newEx_btn);
        assignExBtn = (Button)findViewById(R.id.assignEx_btn);
        backBtn = (Button)findViewById(R.id.back2menu2_btn);

        newExBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Exercise2Activity.this, NewExerciseActivity.class);
                Exercise2Activity.this.startActivity(myIntent);
            }
        });

        assignExBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Exercise2Activity.this, AssignExerciseActivity.class);
                Exercise2Activity.this.startActivity(myIntent);
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
