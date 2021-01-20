package edu.pk.pucit.booksdata;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements Filterable {
    Context context;

    private final String basePath = "https://raw.githubusercontent.com/revolunet/PythonBooks/master/";
    private final ArrayList<Book> books;
    private ArrayList<Book> booksFull;

    RVAdapter(Context context , ArrayList<Book> booksArrayList){
        this.context = context;
        this.books = booksArrayList;
        booksFull = new ArrayList<Book>(books);
    }


    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Layout Inflation is done
        View rowView = LayoutInflater.from(context).inflate(R.layout.my_rowlayout,null);

        //Log.i("Inflater" ,"Done" );

        // Layout Binding is done
        //Log.i("Binding" ,"Done" );
        return new RVAdapter.ViewHolder(rowView);
    }

    // Placing Data in Layout will be dine in this function
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Image loading is done by picasso
        Picasso.get().load(basePath + books.get(position).getBookImgPaths()).into(holder.tv_bookImg);

        //Log.i("Path ", basePath + books.get(position).getBookImgPaths());


        holder.tv_bookName.setText(books.get(position).getBookTitle());
        holder.tv_level.setText(books.get(position).getBookLevel());
        holder.tv_bookInfo.setText(books.get(position).getBookInfo());
        String str = books.get(position).getBookUrl().substring(books.get(position).getBookUrl().length() - 4);
        if(str.equals(".zip") || str.equals(".pdf")){
           // change the text of button to "DOWNLOAD"
            holder.tv_btn.setText("DOWNLOAD");
            // set click event on Button and then start downloading file through download manger
            holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = books.get(position).getBookUrl();
                    DownloadManager downloadManager = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long ref = downloadManager.enqueue(request);
                }
            });
        }
        else{
            holder.tv_btn.setText("READ ONLINE");
            //set click event on Button and then move to google chorme to read book online
            holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = books.get(position).getBookUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    ContextCompat.startActivity(context, intent, null);
                }
            });
        }

        Log.i("Text Binding" ,"In layout Done" );

        //Log.i("Image Binding" ,"In Layout Done" );
    }

    @Override
    public int getItemCount() {
        return books.size();
    }



    // Binding of Layout Elements is done here
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_bookName;
        public TextView tv_level;
        public TextView tv_bookInfo;
        public ImageView tv_bookImg;
        public Button tv_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bookName = itemView.findViewById(R.id.bookName);
            tv_level = itemView.findViewById(R.id.level);
            tv_bookInfo = itemView.findViewById(R.id.bookDetails);
            tv_bookImg = itemView.findViewById(R.id.bookImg);
           tv_btn = itemView.findViewById(R.id.button);
        }

    }

    @Override
    public Filter getFilter() {
        return booksFilter;
    }

    private Filter booksFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Book> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(booksFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Book item:
                     booksFull) {
                    if(item.getBookTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            books.clear();
            books.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
