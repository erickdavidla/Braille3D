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

import ph.edu.mapua.braille3d.Others.Exercise;
import ph.edu.mapua.braille3d.Student.ExerciseActivity;
import ph.edu.mapua.braille3d.R;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {
    private ArrayList<Exercise> exercises;
    private ArrayList<String> userID;
    private Context context;

    public ExerciseListAdapter(Context context, ArrayList<Exercise> exercises, ArrayList<String> userID) {
        this.exercises = exercises;
        this.context = context;
        this.userID = userID;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        ExerciseListAdapter.ViewHolder holder = new ExerciseListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ViewHolder holder, final int position) {

        holder.student_name.setText(exercises.get(position).name);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ExerciseActivity.class);
                myIntent.putExtra("exerObject", exercises.get(position));
                myIntent.putExtra("exerID", userID.get(position));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parent_layout;
        TextView student_name;

        public ViewHolder(View itemView) {
            super(itemView);
            parent_layout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
            student_name = (TextView) itemView.findViewById(R.id.name_txtview);
        }
    }
}
