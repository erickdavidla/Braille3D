package ph.edu.mapua.braille3d.Shared;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import ph.edu.mapua.braille3d.R;

public class InstructionsActivity extends AppCompatActivity {

    private Button backBtn;
    private ScrollView studView;
    private ScrollView teachView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        studView = (ScrollView)findViewById(R.id.stud_ins_scrview);
        teachView = (ScrollView)findViewById(R.id.teach_ins_scrview);

        Intent myIntent = getIntent();
        String userType = myIntent.getStringExtra("user");

        if(userType.equals("Student"))
            studView.setVisibility(View.VISIBLE);
        else if(userType.equals("Teacher"))
            teachView.setVisibility(View.VISIBLE);

        backBtn = (Button)findViewById(R.id.ins_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
