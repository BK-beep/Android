package enset.android.projetsynthese.ui.chatbot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.chatbot.adapters.ChatBotAdapter;
import enset.android.projetsynthese.ui.chatbot.api.BrainShopApi;

import enset.android.projetsynthese.ui.chatbot.models.BrainShopResponse;
import enset.android.projetsynthese.ui.chatbot.models.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotFragment extends Fragment {
    private List<Message> messages=new ArrayList<>();
    private EditText editTextInput;
    private ImageButton imageButtonSend;
    private RecyclerView recyclerViewChat;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chatbot, container, false);

        //acceder aux éléments
        editTextInput = root.findViewById(R.id.query);
        imageButtonSend = root.findViewById(R.id.buttonSend);
        recyclerViewChat = root.findViewById(R.id.rvChat);

        //instancier retrofit
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://api.brainshop.ai/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        BrainShopApi brainShopApi = retrofit.create(BrainShopApi.class);

        //lier adapter/view
        ChatBotAdapter chatBotAdapter = new ChatBotAdapter(messages, requireContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewChat.setAdapter(chatBotAdapter);
        recyclerViewChat.setLayoutManager(manager);

        //gérer le click d'envoie
        imageButtonSend.setOnClickListener(view -> {
            String msg = editTextInput.getText().toString();
            messages.add(new Message(msg, "user"));
            chatBotAdapter.notifyDataSetChanged();
            String url = "http://api.brainshop.ai/get?bid=181757&key=utDihH9dpUjsACYf&uid=[uid]&msg=" + msg;
            editTextInput.setText("");
            Call<BrainShopResponse> response = brainShopApi.getMesage(url);
            response.enqueue(new Callback<BrainShopResponse>() {
                @Override
                public void onResponse(Call<BrainShopResponse> call, Response<BrainShopResponse> response) {
                    Log.i("___info___", response.body().getCnt());
                    messages.add(new Message(response.body().getCnt(), "bot"));
                    chatBotAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<BrainShopResponse> call, Throwable throwable) {
                    Log.i("___error___", throwable.getMessage());
                }
            });

        });
        return root;
    }
}