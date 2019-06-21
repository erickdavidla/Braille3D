package ph.edu.mapua.braille3d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    private Button backBtn;
    private Button assessBtn;
    private TextView name;
    private TextView teacher;
    private TextView email;
    private TextView home;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        backBtn = (Button)findViewById(R.id.back_btn);
        assessBtn = (Button)findViewById(R.id.assess_btn);
        name = (TextView)findViewById(R.id.name_txtview);
        teacher = (TextView)findViewById(R.id.teacher_txtview);
        email = (TextView)findViewById(R.id.email_txtview);
        home = (TextView)findViewById(R.id.addr_txtview);
        phone = (TextView)findViewById(R.id.phone_txtview);

        if(user != null) {
            ValueEventListener accountListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User myUser = dataSnapshot.getValue(User.class);
                    name.setText("Name: " + myUser.full_name);
                    if(myUser.role.equals("Student"))
                        teacher.setText("Teacher: " + myUser.teacher);
                    else
                        teacher.setText("");
                    email.setText("Email: " + myUser.email);
                    home.setText("Home Address: " + myUser.home_add);
                    phone.setText("Contact Number: " + myUser.phone_num);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                }
            };
            mDatabase.addValueEventListener(accountListener);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
