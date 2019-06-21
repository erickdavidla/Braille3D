package ph.edu.mapua.braille3d.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import ph.edu.mapua.braille3d.Shared.AccountActivity;
import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Shared.InstructionsActivity;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Intent myIntent;

    private Button howToBtn;
    private Button signOutBtn;
    private Button accountBtn;
    private Button myStudentsBtn;
    private Button exercises2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        howToBtn = (Button)findViewById(R.id.how_to2_btn);
        signOutBtn = (Button)findViewById(R.id.sign_out2_btn);
        accountBtn = (Button)findViewById(R.id.account2_btn);
        myStudentsBtn = (Button)findViewById(R.id.my_students_btn);
        exercises2Btn = (Button)findViewById(R.id.exercises2_btn);

        myStudentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main2Activity.this, ClassActivity.class);
                Main2Activity.this.startActivity(myIntent);
            }
        });

        exercises2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main2Activity.this, NewExerciseActivity.class);
                Main2Activity.this.startActivity(myIntent);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main2Activity.this, AccountActivity.class);
                Main2Activity.this.startActivity(myIntent);
            }
        });

        howToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main2Activity.this, InstructionsActivity.class);
                myIntent.putExtra("user", "Teacher");
                Main2Activity.this.startActivity(myIntent);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
