package ph.edu.mapua.braille3d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExerciseResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);
    }

    public void toMainActivity(View view) {
        Intent myIntent = new Intent(ExerciseResultActivity.this, MainActivity.class);
        ExerciseResultActivity.this.startActivity(myIntent);
    }
}
