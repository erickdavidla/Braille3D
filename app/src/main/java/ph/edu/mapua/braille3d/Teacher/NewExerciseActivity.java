package ph.edu.mapua.braille3d.Teacher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.R;

public class NewExerciseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText exName;
    private TextView moduleTxt;
    private TextView charSetTxt;
    private Spinner diffSpinner;
    private Spinner moduleSpinner;
    private LinearLayout lettersLayout;
    private LinearLayout numbersLayout;
    private LinearLayout layoutA;
    private LinearLayout layoutK;
    private LinearLayout layoutU;
    private LinearLayout layout0;
    private LinearLayout layout5;
    private ArrayList<LinearLayout> layouts;
    private ArrayList<String> selectedChars;
    private Button nextBtn;
    private Button backBtn;
    private int numOfChecked;
    private int maxNumOfChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        exName = (EditText) findViewById(R.id.ex_name_editTxt);
        moduleTxt = (TextView) findViewById(R.id.module_txt);
        charSetTxt = (TextView) findViewById(R.id.charSet_txt);
        diffSpinner = (Spinner) findViewById(R.id.diff_spinner);
        moduleSpinner = (Spinner) findViewById(R.id.module_spinner);
        lettersLayout = (LinearLayout) findViewById(R.id.letters_layout);
        numbersLayout = (LinearLayout) findViewById(R.id.numbers_layout);
        layoutA = (LinearLayout) findViewById(R.id.aToJ_layout);
        layoutK = (LinearLayout) findViewById(R.id.kToT_layout);
        layoutU = (LinearLayout) findViewById(R.id.uToZ_layout);
        layout0 = (LinearLayout) findViewById(R.id.digits1_layout);
        layout5 = (LinearLayout) findViewById(R.id.digits2_layout);
        nextBtn = (Button) findViewById(R.id.createToAssign_btn);
        backBtn = (Button) findViewById(R.id.newExToMenu_btn);

        moduleTxt.setVisibility(View.GONE);
        charSetTxt.setVisibility(View.GONE);
        moduleSpinner.setVisibility(View.GONE);
        lettersLayout.setVisibility(View.GONE);
        numbersLayout.setVisibility(View.GONE);

        numOfChecked = 0;
        selectedChars = new ArrayList<>();

        diffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2 || position == 4)
                    maxNumOfChecked = 10;
                else
                    maxNumOfChecked = 5;

                uncheckAll();
                numOfChecked = 0;
                selectedChars.clear();
                if(position == 0) {
                    moduleTxt.setVisibility(View.VISIBLE);
                    moduleSpinner.setVisibility(View.VISIBLE);
                    charSetTxt.setVisibility(View.GONE);

                    String[] entries = getResources().getStringArray(R.array.modules_easy);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            NewExerciseActivity.this, android.R.layout.simple_spinner_item, entries );
                    moduleSpinner.setAdapter(spinnerArrayAdapter);
                } else if(position == 1) {
                    moduleTxt.setVisibility(View.VISIBLE);
                    moduleSpinner.setVisibility(View.VISIBLE);
                    charSetTxt.setVisibility(View.GONE);

                    String[] entries = getResources().getStringArray(R.array.modules_normal);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            NewExerciseActivity.this, android.R.layout.simple_spinner_item, entries );
                    moduleSpinner.setAdapter(spinnerArrayAdapter);
                } else if(position == 2) {
                    moduleTxt.setVisibility(View.GONE);
                    moduleSpinner.setVisibility(View.GONE);
                    lettersLayout.setVisibility(View.VISIBLE);
                    layoutA.setVisibility(View.VISIBLE);
                    layoutK.setVisibility(View.VISIBLE);
                    layoutU.setVisibility(View.VISIBLE);
                    numbersLayout.setVisibility(View.GONE);
                    charSetTxt.setVisibility(View.VISIBLE);
                } else if(position == 3) {
                    moduleTxt.setVisibility(View.GONE);
                    moduleSpinner.setVisibility(View.GONE);
                    lettersLayout.setVisibility(View.GONE);
                    numbersLayout.setVisibility(View.VISIBLE);
                    charSetTxt.setVisibility(View.VISIBLE);
                } else if(position == 4) {
                    moduleTxt.setVisibility(View.GONE);
                    moduleSpinner.setVisibility(View.GONE);
                    lettersLayout.setVisibility(View.GONE);
                    charSetTxt.setVisibility(View.VISIBLE);
                    numbersLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        moduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                charSetTxt.setVisibility(View.VISIBLE);
                lettersLayout.setVisibility(View.VISIBLE);
                uncheckAll();
                if(diffSpinner.getSelectedItem().toString().equals("Easy") && position == 0) {
                    layoutA.setVisibility(View.VISIBLE);
                    layoutK.setVisibility(View.GONE);
                    layoutU.setVisibility(View.GONE);
                    numbersLayout.setVisibility(View.GONE);
                } else if(diffSpinner.getSelectedItem().toString().equals("Easy") && position == 1) {
                    layoutA.setVisibility(View.GONE);
                    layoutK.setVisibility(View.VISIBLE);
                    layoutU.setVisibility(View.GONE);
                    numbersLayout.setVisibility(View.GONE);
                } else if(diffSpinner.getSelectedItem().toString().equals("Easy") && position == 2) {
                    layoutA.setVisibility(View.GONE);
                    layoutK.setVisibility(View.GONE);
                    layoutU.setVisibility(View.VISIBLE);
                    numbersLayout.setVisibility(View.GONE);
                } else if(diffSpinner.getSelectedItem().toString().equals("Normal") && position == 0) {
                    layoutA.setVisibility(View.VISIBLE);
                    layoutK.setVisibility(View.VISIBLE);
                    layoutU.setVisibility(View.GONE);
                    numbersLayout.setVisibility(View.GONE);
                } else if(diffSpinner.getSelectedItem().toString().equals("Normal") && position == 1) {
                    layoutA.setVisibility(View.VISIBLE);
                    layoutK.setVisibility(View.GONE);
                    layoutU.setVisibility(View.VISIBLE);
                    numbersLayout.setVisibility(View.GONE);
                } else if(diffSpinner.getSelectedItem().toString().equals("Normal") && position == 2) {
                    layoutA.setVisibility(View.GONE);
                    layoutK.setVisibility(View.VISIBLE);
                    layoutU.setVisibility(View.VISIBLE);
                    numbersLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int childCount = 0;
        layouts = new ArrayList<>();
        layouts.add(layoutA);
        layouts.add(layoutK);
        layouts.add(layoutU);
        layouts.add(layout0);
        layouts.add(layout5);

        for(int i = 0; i < layouts.size(); i++) {
            childCount = layouts.get(i).getChildCount();
            for(int j = 0; j < childCount; j++) {
                final CheckBox c = (CheckBox) layouts.get(i).getChildAt(j);
                c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked && numOfChecked >= maxNumOfChecked) {
                            c.setChecked(false);
                        } else {
                            if (isChecked) {
                                numOfChecked++;
                                selectedChars.add(c.getText().toString());
                            }
                            else{
                                numOfChecked--;
                                selectedChars.remove(c.getText().toString());
                            }
                        }
                    }
                });
            }
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maxNumOfChecked == numOfChecked) {
                    if(!exName.getText().toString().trim().isEmpty()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("exercises");

                        Intent myIntent = new Intent(NewExerciseActivity.this, AssignExerciseActivity.class);
                        myIntent.putStringArrayListExtra("items", selectedChars);
                        myIntent.putExtra("difficulty", diffSpinner.getSelectedItem().toString());
                        myIntent.putExtra("name", exName.getText().toString());
                        if(diffSpinner.getSelectedItem().toString().equals("Easy") || diffSpinner.getSelectedItem().toString().equals("Normal"))
                            myIntent.putExtra("module", moduleSpinner.getSelectedItem().toString());
                        NewExerciseActivity.this.startActivity(myIntent);
                    } else
                        Toast.makeText(NewExerciseActivity.this,"Please enter the exercise name.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(NewExerciseActivity.this,"Please select " + (maxNumOfChecked - numOfChecked) + " more items.", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void uncheckAll() {
        int childCount = 0;
        for(int i = 0; i < layouts.size(); i++) {
            childCount = layouts.get(i).getChildCount();
            for(int j = 0; j < childCount; j++) {
                CheckBox c = (CheckBox) layouts.get(i).getChildAt(j);
                c.setChecked(false);
            }
        }
    }
}
