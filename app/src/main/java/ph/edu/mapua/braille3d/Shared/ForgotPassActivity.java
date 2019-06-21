package ph.edu.mapua.braille3d.Shared;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import ph.edu.mapua.braille3d.R;

public class ForgotPassActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailTxt;
    private Button sendBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailTxt = (EditText)findViewById(R.id.email_reset_editTxt);
        sendBtn = (Button)findViewById(R.id.send_btn);
        cancelBtn = (Button)findViewById(R.id.back_login_btn);

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://braille3d.firebaseio.com/")
                .setApiKey("AIzaSyCsz4zidAXPfcJ2J0OANvTFCVKw6kf45IM")
                .setApplicationId("braille3d").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }

        mAuth.signInWithEmailAndPassword("admin@gmail.com", "admin1234");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailTxt.getText().toString().trim().isEmpty()) {
                    mAuth.sendPasswordResetEmail(emailTxt.getText().toString());
                    Toast.makeText(ForgotPassActivity.this, "Email for password reset is sent!", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(ForgotPassActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
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
