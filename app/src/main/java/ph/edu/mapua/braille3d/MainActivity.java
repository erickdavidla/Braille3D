package ph.edu.mapua.braille3d;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Intent myIntent;

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private Button howToBtn;
    private Button learnBtn;
    private Button exerBtn;
    private Button accountBtn;
    private Button signOutBtn;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        howToBtn = (Button)findViewById(R.id.how_to_btn);
        learnBtn = (Button)findViewById(R.id.learn_btn);
        exerBtn = (Button)findViewById(R.id.exer_btn);
        accountBtn = (Button)findViewById(R.id.account_btn);
        signOutBtn = (Button)findViewById(R.id.sign_out_btn);

        learnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(MainActivity.this, SelectLevelActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        exerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(MainActivity.this, SelectExerciseActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(MainActivity.this, AccountActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });

        /*
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users").child(FirebaseAuth.getInstance().getUid());

        user = new User(FirebaseAuth.getInstance().getCurrentUser().getEmail(), "Mary Jane Samonte", "Makati City", "09455783947", "Teacher", null);
        myRef.setValue(user);
        */

    }
}
