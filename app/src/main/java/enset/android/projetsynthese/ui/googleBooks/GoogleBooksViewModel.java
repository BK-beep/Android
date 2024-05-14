package enset.android.projetsynthese.ui.googleBooks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoogleBooksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GoogleBooksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}