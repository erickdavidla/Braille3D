package ph.edu.mapua.braille3d.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Shared.AccountActivity;
import ph.edu.mapua.braille3d.Teacher.AddUserActivity;
import ph.edu.mapua.braille3d.Teacher.Main2Activity;

public class Main3Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Intent myIntent;

    private Button addBtn;
    private Button modifyBtn;
    private Button accountBtn;
    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mAuth = FirebaseAuth.getInstance();

        addBtn = (Button)findViewById(R.id.new_user_btn);
        modifyBtn = (Button)findViewById(R.id.modify_acc_btn);
        accountBtn = (Button)findViewById(R.id.my_acc_btn);
        signOutBtn = (Button)findViewById(R.id.sign_out3_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main3Activity.this, AddUserActivity.class);
                myIntent.putExtra("role", "Admin");
                Main3Activity.this.startActivity(myIntent);
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main3Activity.this, SelectUserActivity.class);
                Main3Activity.this.startActivity(myIntent);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(Main3Activity.this, AccountActivity.class);
                Main3Activity.this.startActivity(myIntent);
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
}
