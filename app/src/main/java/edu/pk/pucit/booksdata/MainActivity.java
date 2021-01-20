package edu.pk.pucit.booksdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    RVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        String json = "";
        ArrayList<Book> booksArrayList = new ArrayList<Book>();
        try {

            InputStream is = getResources().openRawResource(R.raw.data);
            byte[] data = new byte[is.available()];

            //loop will read data into byte[] data
            while (is.read(data)!=-1){
                //empty as nothing needs to be done here
            }
            json = new String(data , StandardCharsets.UTF_8);
            //Log.i("data.json","length => "+ json.length());

            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = new JSONArray(jsonObject.getString("books"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                booksArrayList.add(new Book(obj.getString("title"),obj.getString("level"),
                        obj.getString("info"), obj.getString("url") ,obj.getString("cover") ));
            }

            //Toast.makeText(this, String.valueOf(titles.size()), Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        adapter = new RVAdapter(this,booksArrayList);
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    /*// Function to convert ArrayList<String> to String[]
    private String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // Iterating and converting to String
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String)obj;
        }

        return str;
    }*/
}
