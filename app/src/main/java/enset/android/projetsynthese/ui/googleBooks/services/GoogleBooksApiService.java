package enset.android.projetsynthese.ui.googleBooks.services;

import enset.android.projetsynthese.ui.googleBooks.models.GoogleBooksResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApiService {
    @GET(value = "volumes")
    Call<GoogleBooksResponse> searchBooks(@Query("q") String keyword);
}
