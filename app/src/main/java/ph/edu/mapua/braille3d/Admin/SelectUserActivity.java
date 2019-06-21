package ph.edu.mapua.braille3d.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.mapua.braille3d.Adapters.UserListAdapter;
import ph.edu.mapua.braille3d.Others.User;
import ph.edu.mapua.braille3d.R;

public class SelectUserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<User> userList;
    private ArrayList<String> idList;

    private RecyclerView recyclerView;
    private UserListAdapter adapter;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        userList = new ArrayList<>();
        idList = new ArrayList<>();

        backBtn = (Button) findViewById(R.id.back2admin_btn);
        recyclerView = (RecyclerView) findViewById(R.id.user_recyclerview);
        adapter = new UserListAdapter(this, userList, idList, "Admin");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User users = dataSnapshot.getValue(User.class);
                String key = dataSnapshot.getKey();

                userList.add(users);
                idList.add(key);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int index = idList.indexOf(dataSnapshot.getKey());

                idList.remove(index);
                userList.remove(index);
                idList.add(dataSnapshot.getKey());
                userList.add(dataSnapshot.getValue(User.class));
                adapter.notifyDataSetChanged();
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
