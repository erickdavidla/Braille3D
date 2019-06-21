package ph.edu.mapua.braille3d.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ph.edu.mapua.braille3d.Others.Assessment;
import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Shared.AssessmentActivity;

public class AssessListAdapter extends RecyclerView.Adapter<AssessListAdapter.ViewHolder> {
    private ArrayList<Assessment> assessments;
    private ArrayList<String> assessID;
    private Context context;

    public AssessListAdapter(Context context, ArrayList<Assessment> assessments, ArrayList<String> userID) {
        this.assessments = assessments;
        this.context = context;
        this.assessID = userID;
    }

    @NonNull
    @Override
    public AssessListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        AssessListAdapter.ViewHolder holder = new AssessListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessListAdapter.ViewHolder holder, final int position) {

        holder.assess_name.setText(assessments.get(position).exercise);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, AssessmentActivity.class);
                myIntent.putExtra("assessObject", assessments.get(position));
                myIntent.putExtra("assessID", assessID.get(position));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parent_layout;
        TextView assess_name;

        public ViewHolder(View itemView) {
            super(itemView);
            parent_layout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
            assess_name = (TextView) itemView.findViewById(R.id.name_txtview);
        }
    }
}
