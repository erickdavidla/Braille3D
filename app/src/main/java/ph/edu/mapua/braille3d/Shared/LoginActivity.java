package ph.edu.mapua.braille3d.Shared;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.mapua.braille3d.Admin.Main3Activity;
import ph.edu.mapua.braille3d.Others.User;
import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Student.MainActivity;
import ph.edu.mapua.braille3d.Teacher.Main2Activity;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private EditText emailTxt;
    private EditText passwordTxt;
    private Button fPassBtn;

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();

        emailTxt = (EditText) findViewById(R.id.email_editxt);
        passwordTxt = (EditText) findViewById(R.id.pass_editxt);
        fPassBtn = (Button) findViewById(R.id.forgot_pass_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        fPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        emailTxt.setText(null);
        passwordTxt.setText(null);
    }

    public void toMainActivity(View view) {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    private void signIn() {
        String email = emailTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Incomplete field!", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                myRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);

                                        if(user.status.equals("Active")){
                                            Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
                                            if(user.role.equals("Student")) {
                                                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                                                LoginActivity.this.startActivity(myIntent);
                                            }else if(user.role.equals("Teacher")) {
                                                Intent myIntent = new Intent(LoginActivity.this, Main2Activity.class);
                                                LoginActivity.this.startActivity(myIntent);
                                            }else if(user.role.equals("Admin")) {
                                                Intent myIntent = new Intent(LoginActivity.this, Main3Activity.class);
                                                LoginActivity.this.startActivity(myIntent);
                                            }
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Incorrect email or password!", Toast.LENGTH_LONG).show();
                                            passwordTxt.setText("");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect email or password!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
