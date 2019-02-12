package com.example.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.newsapp.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private static String URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=a5fbb8ec5b1e42a0a33d126bb633a736";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Data> newsList;
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_newsList);
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(getApplicationContext(), newsList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        makeJsonRequest();
    }


    // fetch data from endpoint
    private void makeJsonRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray articles = response.getJSONArray("articles");

                            for (int i = 0; i < articles.length(); i++) {
                                JSONObject jsonObject = articles.getJSONObject(i);

                                // extract data one by one
                                Data data = new Data();

                                // bring date in required format dd/mm/yyyy
                                String[] date = (jsonObject.getString("publishedAt")).split("-");
                                String year = date[0];
                                String month = date[1];
                                String day = date[2].split("T")[0];

                                String author = jsonObject.getString("author");
                                if (author.equals("null")) {
                                    author = "Source unknown";
                                }

                                data.setTitle(jsonObject.getString("title"));
                                data.setAuthor(author);
                                data.setDate(day + "/" + month + "/" + year);
                                data.setDescription(jsonObject.getString("description"));

                                newsList.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_country:
                return true;
            case R.id.action_others:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
