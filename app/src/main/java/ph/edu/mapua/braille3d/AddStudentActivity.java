package ph.edu.mapua.braille3d;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStudentActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;

    private Button cancelBtn;
    private Button submitBtn;
    private TextView fname;
    private TextView lname;
    private TextView phone;
    private TextView home;
    private TextView email;
    private TextView pass;
    private TextView repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

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

        cancelBtn = (Button)findViewById(R.id.addstud_cancel_btn);
        submitBtn = (Button)findViewById(R.id.addstud_submit_btn);
        fname = (TextView)findViewById(R.id.add_fname_txtview);
        lname = (TextView)findViewById(R.id.add_lname_txtview);
        phone = (TextView)findViewById(R.id.add_contact_txtview);
        home = (TextView)findViewById(R.id.add_home_txtview);
        email = (TextView)findViewById(R.id.add_email_txtview);
        pass = (TextView)findViewById(R.id.add_pass_txtview);
        repass = (TextView)findViewById(R.id.add_repass_textview);

        final String[] currentTeach = new String[1];
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth1.getUid()).child("full_name");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentTeach[0] = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fname.getText().toString().trim().isEmpty() && !lname.getText().toString().trim().isEmpty() && !phone.getText().toString().trim().isEmpty() && !home.getText().toString().trim().isEmpty()
                        && !email.getText().toString().trim().isEmpty() && !pass.getText().toString().trim().isEmpty() && !repass.getText().toString().trim().isEmpty()) {
                    if(pass.getText().toString().equals(repass.getText().toString())) {
                        if(pass.getText().length() > 5) {
                            mAuth2.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                                    .addOnCompleteListener(AddStudentActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                User user = new User(email.getText().toString(),
                                                        fname.getText().toString() + " " + lname.getText().toString(),
                                                        home.getText().toString(),
                                                        phone.getText().toString(),
                                                        "Student",
                                                        currentTeach[0]);

                                                mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth2.getUid());
                                                mDatabase.setValue(user);
                                                mAuth2.signOut();
                                                Toast.makeText(AddStudentActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(AddStudentActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                        } else
                            Toast.makeText(AddStudentActivity.this, "Minimum password length is 6.", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(AddStudentActivity.this, "Password does not match.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AddStudentActivity.this, "Please complete the fields.", Toast.LENGTH_LONG).show();
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
