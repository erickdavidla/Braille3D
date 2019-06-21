package ph.edu.mapua.braille3d.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Others.User;
import ph.edu.mapua.braille3d.Shared.SelectAssessmentActivity;

public class StudentActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference userRef;
    private FirebaseUser user;
    private Button assessBtn;
    private Button backBtn;
    private TextView name;
    private TextView phone;
    private TextView email;
    private TextView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        assessBtn = (Button)findViewById(R.id.assess_prof_btn);
        backBtn = (Button)findViewById(R.id.back_prof_btn);
        name = (TextView)findViewById(R.id.studentname_txtview);
        phone = (TextView)findViewById(R.id.studentphone_txtview);
        email = (TextView)findViewById(R.id.studentemail_txtview);
        home = (TextView)findViewById(R.id.studenthome_txtview);

        final User student = (User) getIntent().getSerializableExtra("userObject");
        String uID = getIntent().getStringExtra("userID");

        name.setText("Name: " + student.full_name);
        phone.setText("Contact Number: " + student.phone_num);
        email.setText("Account Email: " + student.email);
        home.setText("Home Address: " + student.home_add);

        user = FirebaseAuth.getInstance().getCurrentUser();

        final String[] userProf = new String[1];
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User myUser = dataSnapshot.getValue(User.class);
                userProf[0] = myUser.full_name;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        assessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StudentActivity.this, SelectAssessmentActivity.class);
                myIntent.putExtra("student", student.full_name);
                myIntent.putExtra("teacher", userProf[0]);
                StudentActivity.this.startActivity(myIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
