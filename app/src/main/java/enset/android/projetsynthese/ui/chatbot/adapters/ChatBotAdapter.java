package enset.android.projetsynthese.ui.chatbot.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.chatbot.models.Message;


public class ChatBotAdapter extends RecyclerView.Adapter {
    private List<Message> messages;
    private Context context;
    public ChatBotAdapter() {
    }

    public ChatBotAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(context).inflate(R.layout.user_msg_item,parent,false);
                return new UserViewHolder(view);
            case 1:
                view= LayoutInflater.from(context).inflate(R.layout.bot_msg_item,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        String sender=messages.get(position).getSender();
        switch (sender){
            case "user" : return 0;
            case "bot"  : return 1;
            default : return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message=messages.get(position);
        switch (message.getSender()){
            case "user" : ((UserViewHolder)holder).UserMessage.setText(message.getMessage());
            break;
            case "bot"  : ((BotViewHolder)holder).botMessage.setText(message.getMessage());
            break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView UserMessage;
        public UserViewHolder(@NonNull View viewItem){
            super(viewItem);
            this.UserMessage=viewItem.findViewById(R.id.usermsg);
            
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView botMessage;
        public BotViewHolder(@NonNull View viewItem){
            super(viewItem);
            this.botMessage=viewItem.findViewById(R.id.botmsg);

        }
    }
    
}
