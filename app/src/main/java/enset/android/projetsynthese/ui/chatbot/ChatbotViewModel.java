package enset.android.projetsynthese.ui.chatbot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatbotViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ChatbotViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is chatbot fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}