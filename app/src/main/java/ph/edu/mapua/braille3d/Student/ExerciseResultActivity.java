package ph.edu.mapua.braille3d.Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ph.edu.mapua.braille3d.R;

public class ExerciseResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

        Intent myIntent = new Intent("finish_activity");
        sendBroadcast(myIntent);
    }

    public void toMainActivity(View view) {
        finish();
    }
}

