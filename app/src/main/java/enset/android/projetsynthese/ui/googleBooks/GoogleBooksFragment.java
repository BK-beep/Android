package enset.android.projetsynthese.ui.googleBooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.databinding.*;
import enset.android.projetsynthese.ui.googleBooks.activities.BookDetailActivity;
import enset.android.projetsynthese.ui.googleBooks.adapters.BookAdapter;
import enset.android.projetsynthese.ui.googleBooks.models.Book;
import enset.android.projetsynthese.ui.googleBooks.models.GoogleBooksResponse;
import enset.android.projetsynthese.ui.googleBooks.services.GoogleBooksApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleBooksFragment extends Fragment {

    private FragmentGoogleBooksBinding binding;
    private List<Book> books;
    private EditText editTextQuery;
    private Button buttonSearch;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGoogleBooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        books = new ArrayList<>();
        bookAdapter = new BookAdapter(requireContext(), R.layout.book_list_item_layout, books);
        ListView listView = root.findViewById(R.id.listViewBooks);
        listView.setAdapter(bookAdapter);

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GoogleBooksApiService service = retrofit.create(GoogleBooksApiService.class);

        buttonSearch = root.findViewById(R.id.button);
        editTextQuery = root.findViewById(R.id.editTextQuery);

        buttonSearch.setOnClickListener(v -> {
            String query = editTextQuery.getText().toString();
            Call<GoogleBooksResponse> call = service.searchBooks(query);
            call.enqueue(new Callback<GoogleBooksResponse>() {
                @Override
                public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                    if (response.isSuccessful()) {
                        GoogleBooksResponse googleResponse = response.body();
                        if (googleResponse != null) {
                            books.clear();
                            books.addAll(googleResponse.getBooks());
                            bookAdapter.notifyDataSetChanged();
                            Log.i("info_kkkk", googleResponse.getKind() + " " + googleResponse.getTotalItems());
                        } else {
                            Log.i("info_kkkk", "Response body is null");
                        }
                    } else {
                        Log.i("info_kkkk", "Response unsuccessful: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<GoogleBooksResponse> call, Throwable t) {
                    Log.e("info_kkkk", "Failed to fetch books", t);
                    Toast.makeText(requireContext(), "Error fetching books", Toast.LENGTH_SHORT).show();
                }
            });
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(requireContext(), BookDetailActivity.class);
            intent.putExtra("book", books.get(position));
            startActivity(intent);
        });

        return root;
    }
}
