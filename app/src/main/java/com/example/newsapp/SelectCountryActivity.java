package com.example.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectCountryActivity extends AppCompatActivity {

    private ArrayAdapter<String> countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Select your country");

        ListView listView = findViewById(R.id.lv_countries);

        String[] countries = new String[] { "UAE (ae)", "Argentina (ar)", "Austria (at)", "Australia (au)", "Belgium (be)", "Brazil (br)",
                                            "Switzerland (ch)", "China (cn)", "Colombia (co)", "Germany (de)", "Egypt (eg)", "Greece (gr)",
                                            };
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(countries));

        countryAdapter = new ArrayAdapter<>(this, R.layout.item_country, list);

        listView.setAdapter(countryAdapter);
    }
}
