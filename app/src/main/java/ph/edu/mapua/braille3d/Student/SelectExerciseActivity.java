package ph.edu.mapua.braille3d.Student;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.mapua.braille3d.Adapters.ExerciseListAdapter;
import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Others.User;

public class SelectExerciseActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference userRef;
    private DatabaseReference exerRef;
    private ArrayList<Exercise> exerciseList;
    private ArrayList<String> idList;

    private RecyclerView recyclerView;
    private ExerciseListAdapter adapter;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        user = FirebaseAuth.getInstance().getCurrentUser();

        final String[] userProf = new String[2];
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User myUser = dataSnapshot.getValue(User.class);
                userProf[0] = myUser.full_name;
                userProf[1] = myUser.teacher;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exerRef = FirebaseDatabase.getInstance().getReference().child("exercises");
        exerciseList = new ArrayList<>();
        idList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.exer_recyclerview);
        adapter = new ExerciseListAdapter(this, exerciseList, idList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Exercise exercises = dataSnapshot.getValue(Exercise.class);
                String key = dataSnapshot.getKey();

                if(exercises.assigned.equals(userProf[0]) && exercises.status.equals("Ongoing")) {
                    exerciseList.add(exercises);
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

        backBtn = (Button)findViewById(R.id.exercise2main_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
    }

}
