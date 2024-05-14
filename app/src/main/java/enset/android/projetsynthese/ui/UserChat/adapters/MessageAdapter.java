package enset.android.projetsynthese.ui.UserChat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import enset.android.projetsynthese.R;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.UserChat.models.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private static final int VIEW_TYPE_SENT=1;
    private static final int VIEW_TYPE_RECEIVE=2;
    private Context context;
    private List<Message> messages;

    public MessageAdapter(Context context) {
        this.context = context;
        this.messages=new ArrayList<>();
    }
    public void addMessage(Message Message){
        messages.add(Message);
    }
    public void clear(){
        messages.clear();
    }

    public List<Message> getMessages() {
        return messages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if(viewType==VIEW_TYPE_SENT){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row_sent,parent,false);
            return new MyViewHolder(view);
        }else{
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row_received,parent,false);
            return new MyViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message=messages.get(position);
        if (message.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            holder.textViewSentMessage.setText(message.getMessage());
        }
        else {
            holder.textViewReceivedMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewSentMessage,textViewReceivedMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSentMessage=itemView.findViewById(R.id.textViewSentMessage);
            textViewReceivedMessage=itemView.findViewById(R.id.textViewReceivedMessage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVE;
        }
    }
}
