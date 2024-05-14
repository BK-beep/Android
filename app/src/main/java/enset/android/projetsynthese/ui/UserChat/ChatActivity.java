package enset.android.projetsynthese.ui.UserChat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.UserChat.adapters.MessageAdapter;
import enset.android.projetsynthese.ui.UserChat.models.Message;


public class ChatActivity extends AppCompatActivity {

    String receiverID,receiverName;
    String receiverRoom,senderRoom;
    String senderId,senderName;
    DatabaseReference databaseReferenceSender,databaseReferenceReceiver,userReference;

    ImageView sendButton;
    EditText messageText;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userReference= FirebaseDatabase.getInstance().getReference("users");
        receiverID = getIntent().getStringExtra("id");
        receiverName = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(receiverName);
        if (receiverID!=null){
            senderRoom = FirebaseAuth.getInstance().getUid() + receiverID;
            receiverRoom = receiverID + FirebaseAuth.getInstance().getUid();
        }
        sendButton=findViewById(R.id.sendMessageIcon);
        messageAdapter = new MessageAdapter(this);
        recyclerView = findViewById(R.id.chatrecycler);
        messageText = findViewById(R.id.messageEdit);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender=FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver=FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messages=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Message message=dataSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                messageAdapter.clear();
                for (Message message:messages){
                    messageAdapter.addMessage(message);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                if (message.trim().length()>0){
                    SendMessage(message);
                }else{
                    Toast.makeText(ChatActivity.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendMessage(String message) {
        String messageId= UUID.randomUUID().toString();
        Message sendMessage=new Message(messageId,FirebaseAuth.getInstance().getUid(),message);
        messageAdapter.addMessage(sendMessage);
        databaseReferenceSender.child(messageId).setValue(sendMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Fail to send Message", Toast.LENGTH_SHORT).show();
                    }
                });
        databaseReferenceReceiver.child(messageId).setValue(sendMessage);
        recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
        messageText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ChatActivity.this,SignInActivity.class));
            finish();
            return true;
        }
        return false;
    }
}