package ph.edu.mapua.braille3d.Admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Teacher.AddUserActivity;

public class ChangePassActivity extends AppCompatActivity {

    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;
    private FirebaseUser user;
    private Intent myIntent;
    private String email;

    private TextView cPass;
    private TextView nPass;
    private TextView rnPass;
    private Button submitBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        cPass = (TextView)findViewById(R.id.current_pass_txtview);
        nPass = (TextView)findViewById(R.id.new_pass_txtview);
        rnPass = (TextView)findViewById(R.id.renew_pass_txtview);
        submitBtn = (Button)findViewById(R.id.chPass_submit_btn);
        cancelBtn = (Button)findViewById(R.id.chPass_cancel_btn);

        myIntent = getIntent();
        email = myIntent.getStringExtra("email");

        mAuth1 = FirebaseAuth.getInstance();
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://braille3d.firebaseio.com/")
                .setApiKey("AIzaSyCsz4zidAXPfcJ2J0OANvTFCVKw6kf45IM")
                .setApplicationId("braille3d").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cPass.getText().toString().trim().isEmpty() && !nPass.getText().toString().trim().isEmpty()
                        && !rnPass.getText().toString().trim().isEmpty()) {
                    mAuth2.signInWithEmailAndPassword(email, cPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                user = mAuth2.getCurrentUser();

                                AuthCredential credential = EmailAuthProvider.getCredential(email, cPass.getText().toString());

                                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            if(!nPass.getText().toString().trim().isEmpty() && !rnPass.getText().toString().trim().isEmpty()) {
                                                if(nPass.getText().toString().equals(rnPass.getText().toString())) {
                                                    if(nPass.getText().length() > 5) {
                                                        user.updatePassword(nPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    Toast.makeText(ChangePassActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();
                                                                    mAuth2.signOut();
                                                                    finish();
                                                                } else
                                                                    Toast.makeText(ChangePassActivity.this, "Error! Password not updated.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }  else
                                                        Toast.makeText(ChangePassActivity.this, "Minimum password length is 6.", Toast.LENGTH_LONG).show();
                                                }else
                                                    Toast.makeText(ChangePassActivity.this, "New password does not match!", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(ChangePassActivity.this, "Please complete the fields.", Toast.LENGTH_SHORT).show();
                                        }else
                                            Toast.makeText(ChangePassActivity.this, "Reauthentication failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else
                                Toast.makeText(ChangePassActivity.this, "Current Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(ChangePassActivity.this, "Please complete the field.", Toast.LENGTH_SHORT).show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
