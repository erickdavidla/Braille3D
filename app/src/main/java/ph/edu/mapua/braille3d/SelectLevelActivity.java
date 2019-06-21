package ph.edu.mapua.braille3d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
    }

    public void toMainActivity(View view) {
        finish();
    }

    public void toSelectAtoJActivity(View view) {
        Intent myIntent = new Intent(SelectLevelActivity.this, SelectLetterActivity.class);
        myIntent.putExtra("set", "aToJ");
        SelectLevelActivity.this.startActivity(myIntent);
    }

    public void toSelectKtoTActivity(View view) {
        Intent myIntent = new Intent(SelectLevelActivity.this, SelectLetterActivity.class);
        myIntent.putExtra("set", "kToT");
        SelectLevelActivity.this.startActivity(myIntent);
    }

    public void toSelectUtoZActivity(View view) {
        Intent myIntent = new Intent(SelectLevelActivity.this, SelectLetterActivity.class);
        myIntent.putExtra("set", "uToZ");
        SelectLevelActivity.this.startActivity(myIntent);
    }

    public void toSelect0to9Activity(View view) {
        Intent myIntent = new Intent(SelectLevelActivity.this, SelectLetterActivity.class);
        myIntent.putExtra("set", "0To9");
        SelectLevelActivity.this.startActivity(myIntent);
    }
}
