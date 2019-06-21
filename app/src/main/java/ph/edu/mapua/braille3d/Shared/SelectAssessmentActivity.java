package ph.edu.mapua.braille3d.Shared;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.mapua.braille3d.Adapters.AssessListAdapter;
import ph.edu.mapua.braille3d.Adapters.ExerciseListAdapter;
import ph.edu.mapua.braille3d.Others.Assessment;
import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.Others.User;
import ph.edu.mapua.braille3d.R;

public class SelectAssessmentActivity extends AppCompatActivity {

    private DatabaseReference exerRef;
    private DatabaseReference assessRef;
    private ArrayList<Assessment> assessList;
    private ArrayList<String> idList;

    private RecyclerView recyclerView;
    private AssessListAdapter adapter;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);

        backBtn = findViewById(R.id.back2prof_btn);

        final String teacher = getIntent().getStringExtra("teacher");
        final String student = getIntent().getStringExtra("student");

        exerRef = FirebaseDatabase.getInstance().getReference().child("exercises");
        assessList = new ArrayList<>();
        idList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.assess_recyclerview);
        adapter = new AssessListAdapter(this, assessList, idList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessRef = FirebaseDatabase.getInstance().getReference().child("assessments");
        assessRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Assessment assessment = dataSnapshot.getValue(Assessment.class);
                String key = dataSnapshot.getKey();

                if(assessment.teacher.equals(teacher) && assessment.student.equals(student)) {
                    assessList.add(assessment);
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
