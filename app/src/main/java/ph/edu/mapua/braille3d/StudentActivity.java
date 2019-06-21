package ph.edu.mapua.braille3d;

import android.nfc.TagLostException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

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

        backBtn = (Button)findViewById(R.id.back_prof_btn);
        name = (TextView)findViewById(R.id.studentname_txtview);
        phone = (TextView)findViewById(R.id.studentphone_txtview);
        email = (TextView)findViewById(R.id.studentemail_txtview);
        home = (TextView)findViewById(R.id.studenthome_txtview);

        User student = (User) getIntent().getSerializableExtra("userObject");
        String uID = getIntent().getStringExtra("userID");

        name.setText(student.full_name);
        phone.setText(student.phone_num);
        email.setText(student.email);
        home.setText(student.home_add);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
