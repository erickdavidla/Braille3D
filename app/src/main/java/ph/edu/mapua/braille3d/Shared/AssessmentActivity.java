package ph.edu.mapua.braille3d.Shared;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ph.edu.mapua.braille3d.Others.Assessment;
import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.R;

public class AssessmentActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Assessment assessment;
    private Exercise exercise;

    private TextView assessForTxt;
    private TextView durationTxt;
    private TextView studentEditTxt;
    private TextView teacherEditTxt;
    private CheckBox module1ChkBox;
    private CheckBox module2ChkBox;
    private CheckBox module3ChkBox;
    private CheckBox module4ChkBox;
    private LinearLayout itemsLayout;
    private LinearLayout retriesLayout;
    private TextView item1, item2, item3, item4, item5,
                        item6, item7, item8, item9, item10;
    private TextView retries1, retries2, retries3, retries4, retries5,
                retries6, retries7, retries8, retries9, retries10;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("exercises");
        assessment = (Assessment) getIntent().getSerializableExtra("assessObject");

        assessForTxt = (TextView)findViewById(R.id.exerName_txtview);
        durationTxt = (TextView)findViewById(R.id.duration_txt);
        studentEditTxt = (EditText)findViewById(R.id.student_editTxt);
        teacherEditTxt = (EditText)findViewById(R.id.teacher_editTxt);
        module1ChkBox = (CheckBox)findViewById(R.id.module1_chkbox);
        module2ChkBox = (CheckBox)findViewById(R.id.module2_chkbox);
        module3ChkBox = (CheckBox)findViewById(R.id.module3_chkbox);
        module4ChkBox = (CheckBox)findViewById(R.id.module4_chkbox);
        itemsLayout = (LinearLayout)findViewById(R.id.item_layout);
        retriesLayout = (LinearLayout)findViewById(R.id.retries_layout);
        backBtn = (Button)findViewById(R.id.back2assess_btn);

        item1 = (TextView)findViewById(R.id.item1_txtview);
        item2 = (TextView)findViewById(R.id.item2_txtview);
        item3 = (TextView)findViewById(R.id.item3_txtview);
        item4 = (TextView)findViewById(R.id.item4_txtview);
        item5 = (TextView)findViewById(R.id.item5_txtview);
        item6 = (TextView)findViewById(R.id.item6_txtview);
        item7 = (TextView)findViewById(R.id.item7_txtview);
        item8 = (TextView)findViewById(R.id.item8_txtview);
        item9 = (TextView)findViewById(R.id.item9_txtview);
        item10 = (TextView)findViewById(R.id.item10_txtview);

        retries1 = (TextView)findViewById(R.id.retries1_txtview);
        retries2 = (TextView)findViewById(R.id.retries2_txtview);
        retries3 = (TextView)findViewById(R.id.retries3_txtview);
        retries4 = (TextView)findViewById(R.id.retries4_txtview);
        retries5 = (TextView)findViewById(R.id.retries5_txtview);
        retries6 = (TextView)findViewById(R.id.retries6_txtview);
        retries7 = (TextView)findViewById(R.id.retries7_txtview);
        retries8 = (TextView)findViewById(R.id.retries8_txtview);
        retries9 = (TextView)findViewById(R.id.retries9_txtview);
        retries10 = (TextView)findViewById(R.id.retries10_txtview);


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Exercise ex = dataSnapshot.getValue(Exercise.class);

                if(ex.name.equals(assessment.exercise)) {
                    exercise = ex;

                    //assessForTxt.setText(String.valueOf(assessment.item1));
                    assessForTxt.setText("Assessment for " + assessment.exercise);
                    studentEditTxt.setText(assessment.student);
                    teacherEditTxt.setText(assessment.teacher);
                    durationTxt.setText(assessment.duration + " (mm:ss)");
                    if(exercise.module1)
                        module1ChkBox.setChecked(true);
                    if(exercise.module2)
                        module2ChkBox.setChecked(true);
                    if(exercise.module2)
                        module3ChkBox.setChecked(true);
                    if(exercise.num1 || exercise.num2)
                        module4ChkBox.setChecked(true);

                    item1.setText("Item 1: " + exercise.item1);
                    item2.setText("Item 2: " + exercise.item2);
                    item3.setText("Item 3: " + exercise.item3);
                    item4.setText("Item 4: " + exercise.item4);
                    item5.setText("Item 5: " + exercise.item5);
                    retries1.setText(String.valueOf(assessment.item1));
                    retries2.setText(String.valueOf(assessment.item2));
                    retries3.setText(String.valueOf(assessment.item3));
                    retries4.setText(String.valueOf(assessment.item4));
                    retries5.setText(String.valueOf(assessment.item5));

                    if(assessment.item6 == 0) {
                        item6.setVisibility(View.GONE);
                        item7.setVisibility(View.GONE);
                        item8.setVisibility(View.GONE);
                        item9.setVisibility(View.GONE);
                        item10.setVisibility(View.GONE);
                        retries6.setVisibility(View.GONE);
                        retries7.setVisibility(View.GONE);
                        retries8.setVisibility(View.GONE);
                        retries9.setVisibility(View.GONE);
                        retries10.setVisibility(View.GONE);
                    } else {
                        item6.setText("Item 6: " + exercise.item6);
                        item7.setText("Item 7: " + exercise.item7);
                        item8.setText("Item 8: " + exercise.item8);
                        item9.setText("Item 9: " + exercise.item9);
                        item10.setText("Item 10: " + exercise.item10);
                        retries6.setText(String.valueOf(assessment.item6));
                        retries7.setText(String.valueOf(assessment.item7));
                        retries8.setText(String.valueOf(assessment.item8));
                        retries9.setText(String.valueOf(assessment.item9));
                        retries10.setText(String.valueOf(assessment.item10));
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
