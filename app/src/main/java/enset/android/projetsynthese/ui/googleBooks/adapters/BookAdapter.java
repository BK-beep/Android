package enset.android.projetsynthese.ui.googleBooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import java.util.List;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.googleBooks.models.Book;


public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.book_list_item_layout,parent,false);
        }
        Book book=getItem(position);
        ImageView imageView=convertView.findViewById(R.id.bookImage);
        TextView textViewTitle=convertView.findViewById(R.id.textViewTitle);
        TextView textViewDescription=convertView.findViewById(R.id.textViewDescription);
        TextView textViewAuthors=convertView.findViewById(R.id.textViewAuthors);
        textViewTitle.setText(book.getVolumeInfo().getTitle());
        textViewDescription.setText(book.getVolumeInfo().getDescription());
        textViewAuthors.setText(book.getVolumeInfo().getAuthors().toString());

        Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail().replace("http","https")).into(imageView);

        return convertView;
    }

}
