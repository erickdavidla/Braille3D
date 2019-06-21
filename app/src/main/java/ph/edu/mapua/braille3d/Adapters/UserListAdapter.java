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

import ph.edu.mapua.braille3d.Admin.ModifyAccountActivity;
import ph.edu.mapua.braille3d.R;
import ph.edu.mapua.braille3d.Teacher.StudentActivity;
import ph.edu.mapua.braille3d.Others.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ArrayList<User> users;
    private ArrayList<String> userID;
    private String userType;
    private Context context;

    public UserListAdapter(Context context, ArrayList<User> students, ArrayList<String> userID, String userType) {
        this.users = students;
        this.context = context;
        this.userID = userID;
        this.userType = userType;
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

        holder.user_name.setText(users.get(position).full_name);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                if(userType.equals("Teacher"))
                    myIntent = new Intent(context, StudentActivity.class);
                else if(userType.equals("Admin"))
                    myIntent = new Intent(context, ModifyAccountActivity.class);
                myIntent.putExtra("userObject", users.get(position));
                myIntent.putExtra("userID", userID.get(position));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parent_layout;
        TextView user_name;

        public ViewHolder(View itemView) {
            super(itemView);
            parent_layout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
            user_name = (TextView) itemView.findViewById(R.id.name_txtview);
        }
    }

}
