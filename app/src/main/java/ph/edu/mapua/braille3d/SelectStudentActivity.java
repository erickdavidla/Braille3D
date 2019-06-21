package ph.edu.mapua.braille3d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SelectStudentActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<User> studentList;
    private ArrayList<String> idList;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        studentList = new ArrayList<>();
        idList = new ArrayList<>();

        backBtn = (Button) findViewById(R.id.back2mystud_btn);
        recyclerView = (RecyclerView) findViewById(R.id.stud_recyclerview);
        adapter = new RecyclerViewAdapter(this, studentList, idList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User students = dataSnapshot.getValue(User.class);
                String key = dataSnapshot.getKey();

                if(students.role.equals("Student")) {
                    studentList.add(students);
                    idList.add(key);
                    adapter.notifyDataSetChanged();
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
