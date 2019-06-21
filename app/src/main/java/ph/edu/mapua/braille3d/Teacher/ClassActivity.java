package ph.edu.mapua.braille3d.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ph.edu.mapua.braille3d.R;

public class ClassActivity extends AppCompatActivity {

    private Button studentListBtn;
    private Button addStudentBtn;
    private Button backBtn;

    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        studentListBtn = (Button)findViewById(R.id.student_list_btn);
        addStudentBtn = (Button)findViewById(R.id.add_student_btn);
        backBtn = (Button)findViewById(R.id.menu2_back_btn);

        studentListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(ClassActivity.this, SelectStudentActivity.class);
                ClassActivity.this.startActivity(myIntent);
            }
        });

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(ClassActivity.this, AddUserActivity.class);
                myIntent.putExtra("role", "Teacher");
                ClassActivity.this.startActivity(myIntent);
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
