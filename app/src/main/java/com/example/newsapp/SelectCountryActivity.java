package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectCountryActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Select your country");


        ListView listView = findViewById(R.id.lv_countries);

        // hardcoded strings fed to listview
        String[] countries = new String[]{"UAE-ae", "Argentina-ar", "Austria-at", "Australia-au", "Belgium-be", "Brazil-br",
                "Switzerland-ch", "China-cn", "Colombia-co", "Germany-de", "Egypt-eg", "Greece-gr",
                "India-in", "Romania-ro"};

        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(countries));

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, R.layout.item_country, list);
        listView.setAdapter(countryAdapter);


        // click listeners on item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView) view).getText().toString();

                String countryCode = text.split("-")[1];
                Log.d(TAG, "onItemClick: " + countryCode);

                // send country code back to previous acitivity
                Intent intent = new Intent();
                intent.putExtra("countryCode", countryCode);
                setResult(22, intent);
                finish();
            }
        });
    }
}
