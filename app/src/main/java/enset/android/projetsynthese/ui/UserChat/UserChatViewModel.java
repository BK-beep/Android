package enset.android.projetsynthese.ui.UserChat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserChatViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}