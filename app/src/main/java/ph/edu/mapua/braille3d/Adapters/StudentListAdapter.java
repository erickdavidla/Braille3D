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

import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Teacher.StudentActivity;
import ph.edu.mapua.braille3d.Others.User;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private ArrayList<User> students;
    private ArrayList<String> userID;
    private Context context;

    public StudentListAdapter(Context context, ArrayList<User> students, ArrayList<String> userID) {
        this.students = students;
        this.context = context;
        this.userID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.student_name.setText(students.get(position).full_name);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, StudentActivity.class);
                myIntent.putExtra("userObject", students.get(position));
                myIntent.putExtra("userID", userID.get(position));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
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
