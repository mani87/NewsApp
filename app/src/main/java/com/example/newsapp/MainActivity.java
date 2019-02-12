package com.example.newsapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.newsapp.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayout;
    private LottieAnimationView lottieAnimationView;
    private List<Data> newsList;
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_newsList);
        linearLayout = findViewById(R.id.ll_main);
        lottieAnimationView = findViewById(R.id.lottie);
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(getApplicationContext(), newsList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        lottieAnimationView.setVisibility(View.VISIBLE);

        makeJsonRequest("in");
    }


    // fetch data from endpoint
    private void makeJsonRequest(String countryCode) {

        String URL = "https://newsapi.org/v2/top-headlines?country=" + countryCode + "&apiKey=a5fbb8ec5b1e42a0a33d126bb633a736";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        lottieAnimationView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);

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
                lottieAnimationView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) search.getActionView();
        search(searchView);

        return super.onCreateOptionsMenu(menu);
    }


    // performs actual filtering
    private void search(android.support.v7.widget.SearchView searchView) {
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String userInput = s.toLowerCase().trim();
                List<Data> newList = new ArrayList<>();

                for (Data item : newsList){
                    if (item.getTitle().toLowerCase().contains(userInput)){
                        newList.add(item);
                    }
                }

                adapter.updateList(newList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String userInput = s.toLowerCase().trim();
                List<Data> newList = new ArrayList<>();

                for (Data item : newsList){
                    if (item.getTitle().toLowerCase().contains(userInput) ||
                        item.getDescription().toLowerCase().contains(userInput) ||
                        item.getAuthor().toLowerCase().contains(userInput)){
                        newList.add(item);
                    }
                }

                adapter.updateList(newList);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_country:
                Intent intent = new Intent(this, SelectCountryActivity.class);
                startActivityForResult(intent, 1);
                return true;
            case R.id.action_others:
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 22) {
                newsList.clear();
                makeJsonRequest(data.getStringExtra("countryCode"));
            }
        }
    }
}
