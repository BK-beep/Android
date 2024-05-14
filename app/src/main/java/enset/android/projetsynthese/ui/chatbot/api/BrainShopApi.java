package enset.android.projetsynthese.ui.chatbot.api;


import enset.android.projetsynthese.ui.chatbot.models.BrainShopResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BrainShopApi {
    @GET
    Call<BrainShopResponse> getMesage(@Url String url);
}
