package enset.android.projetsynthese.ui.UserChat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.UserChat.ChatActivity;
import enset.android.projetsynthese.ui.UserChat.models.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<User> users;

    public UserAdapter(Context context) {
        this.context = context;
        this.users=new ArrayList<>();
    }
    public void addUser(User user){
        users.add(user);
    }
    public void clear(){
        users.clear();
    }

    public List<User> getUsers() {
        return users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user=users.get(position);
        holder.name.setText(user.getUserName());
        holder.email.setText(user.getUserEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("id",user.getUserId());
                intent.putExtra("name",user.getUserName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> usersList) {
        this.users=usersList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name,email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.username);
            email=itemView.findViewById(R.id.useremail);
        }
    }
}
