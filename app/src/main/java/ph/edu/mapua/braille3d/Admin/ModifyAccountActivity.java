package ph.edu.mapua.braille3d.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.mapua.braille3d.Others.Assessment;
import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.Others.User;
import ph.edu.mapua.braille3d.R;

public class ModifyAccountActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference aDatabase;
    private DatabaseReference bDatabase;
    private TextView nameTxt;
    private TextView contactTxt;
    private TextView homeTxt;
    private Button submitBtn;
    private Button cancelBtn;
    private Button changeBtn;
    private RadioButton activeBtn;
    private RadioButton inactiveBtn;

    private Intent myIntent;
    private User user;
    private String userID;
    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);

        nameTxt = (TextView)findViewById(R.id.mod_name_txtview);
        contactTxt = (TextView)findViewById(R.id.mod_contact_txtview);
        homeTxt = (TextView)findViewById(R.id.mod_home_txtview);
        submitBtn = (Button)findViewById(R.id.modacc_submit_btn);
        cancelBtn = (Button)findViewById(R.id.modacc_cancel_btn);
        changeBtn = (Button)findViewById(R.id.changepass_btn);
        activeBtn = (RadioButton)findViewById(R.id.active_rdBtn);
        inactiveBtn = (RadioButton)findViewById(R.id.inactive_rdBtn);

        myIntent = getIntent();
        user = (User) myIntent.getSerializableExtra("userObject");
        userID = myIntent.getStringExtra("userID");

        oldName = user.full_name;
        nameTxt.setText(user.full_name);
        contactTxt.setText(user.phone_num);
        homeTxt.setText(user.home_add);
        if(user.status.equals("Active"))
            activeBtn.setChecked(true);
        else if(user.status.equals("Inactive"))
            inactiveBtn.setChecked(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameTxt.getText().toString().trim().isEmpty() && !contactTxt.getText().toString().trim().isEmpty()
                        && !homeTxt.getText().toString().trim().isEmpty()) {
                    if(contactTxt.getText().length() > 7) {
                        user.full_name = nameTxt.getText().toString();
                        user.phone_num = contactTxt.getText().toString();
                        user.home_add = homeTxt.getText().toString();
                        if(activeBtn.isChecked())
                            user.status = "Active";
                        else if(inactiveBtn.isChecked())
                            user.status = "Inactive";
                        mDatabase.setValue(user);

                        aDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                        aDatabase.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                User student = (User)dataSnapshot.getValue(User.class);

                                if(student.teacher != null) {
                                    if(student.teacher.equals(oldName)) {
                                        bDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(dataSnapshot.getKey()).child("teacher");
                                        bDatabase.setValue(user.full_name);
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        aDatabase = FirebaseDatabase.getInstance().getReference().child("exercises");
                        aDatabase.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Exercise exercise = (Exercise)dataSnapshot.getValue(Exercise.class);

                                if(user.role.equals("Student")) {
                                    if(exercise.assigned.equals(oldName)) {
                                        bDatabase = FirebaseDatabase.getInstance().getReference().child("exercises").child(dataSnapshot.getKey()).child("assigned");
                                        bDatabase.setValue(user.full_name);
                                    }
                                } else if(user.role.equals("Teacher")) {
                                    if(exercise.owner.equals(oldName)) {
                                        bDatabase = FirebaseDatabase.getInstance().getReference().child("exercises").child(dataSnapshot.getKey()).child("owner");
                                        bDatabase.setValue(user.full_name);
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        aDatabase = FirebaseDatabase.getInstance().getReference().child("assessments");
                        aDatabase.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Assessment assessment = (Assessment)dataSnapshot.getValue(Assessment.class);

                                if(user.role.equals("Student")) {
                                    if(assessment.student.equals(oldName)){
                                        bDatabase = FirebaseDatabase.getInstance().getReference().child("assessments").child(dataSnapshot.getKey()).child("student");
                                        bDatabase.setValue(user.full_name);
                                    }
                                }else if(user.role.equals("Teacher")) {
                                    if(assessment.teacher.equals(oldName)){
                                        bDatabase = FirebaseDatabase.getInstance().getReference().child("assessments").child(dataSnapshot.getKey()).child("teacher");
                                        bDatabase.setValue(user.full_name);
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(ModifyAccountActivity.this, "Account Updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(ModifyAccountActivity.this, "Minimum phone length is 8.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ModifyAccountActivity.this, "Please complete the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ModifyAccountActivity.this, ChangePassActivity.class);
                myIntent.putExtra("email", user.email);
                ModifyAccountActivity.this.startActivity(myIntent);
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
