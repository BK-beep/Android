package enset.android.projetsynthese.ui.googleBooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.googleBooks.models.Book;


public class BookDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_detail_layout);
        Intent intent=getIntent();
        Book book=(Book) intent.getSerializableExtra("book");

        System.out.println(book);

        ImageView imageView=findViewById(R.id.imageViewBook1);
        TextView textViewTitle=findViewById(R.id.textViewTitle1);
        TextView textViewDescription=findViewById(R.id.textViewAuthors1);
        TextView textViewAuthors=findViewById(R.id.textViewDescription1);
        Button buttonShare=findViewById(R.id.buttonShare);

        textViewTitle.setText(book.getVolumeInfo().getTitle());
        textViewDescription.setText(book.getVolumeInfo().getAuthors().toString());
        textViewAuthors.setText(book.getVolumeInfo().getDescription());

        Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail().replace("http","https")).into(imageView);

        buttonShare.setOnClickListener(v -> {
            String sharedMsg=book.toString();
            Intent sharedIntent=new Intent(Intent.ACTION_SEND);
            sharedIntent.setType("text/plain");
            sharedIntent.putExtra(Intent.EXTRA_TEXT,sharedMsg);
            startActivity(Intent.createChooser(sharedIntent,"Partager via"));
        });
    }
}
