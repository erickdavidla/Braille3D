package ph.edu.mapua.braille3d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);
    }

    public void toSelecExerciseActivity(View view) {
        Intent myIntent = new Intent(SelectExerciseActivity.this, ExerciseActivity.class);
        SelectExerciseActivity.this.startActivity(myIntent);
    }

    public void toMainActivity(View view) {
        Intent myIntent = new Intent(SelectExerciseActivity.this, MainActivity.class);
        SelectExerciseActivity.this.startActivity(myIntent);
    }
}
