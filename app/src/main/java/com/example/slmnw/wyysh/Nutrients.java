package com.example.slmnw.wyysh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ho Yin Ho on 4/15/2017.
 */

public class Nutrients extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrients);
        String food =  getIntent().getStringExtra("foodName");
        System.out.println(food);
        TextView name = (TextView) findViewById(R.id.food);
        name.setText(food.substring(0, 1).toUpperCase() + food.substring(1));
        String url = "https://api.nutritionix.com/v1_1/search/" + food + "?results=0%3A1&cal_min=0&cal_max=50000&fields=*&appId=840e8f6d&appKey=9f433f3c4fbaf449b1f2530b36ce4fe1";
        try{
            JSONObject holder = new JSONObject(run(url));
            JSONObject fields = ((JSONObject)holder.getJSONArray("hits").get(0)).getJSONObject("fields");
            String[] categories = {"nf_calories", "nf_sugars", "nf_protein", "nf_vitamin_a_dv","nf_vitamin_c_dv","nf_calcium_dv","nf_iron_dv", "nf_potassium"};
            Iterator<String> iter = fields.keys();
            ArrayList<String> nutritionValues = new ArrayList<String>();
            while (iter.hasNext()){
                String field = iter.next();
                if (Arrays.asList(categories).contains(field)) {
                    if (fields.get(field) != null) {
                        String output = "";
                        String[] words = field.split("_");
                        if (words.length > 3) {
                            output += words[1].substring(0, 1).toUpperCase() + words[1].substring(1) + " " + words[2].substring(0, 1).toUpperCase() + words[2].substring(1);
                        } else {
                            output += words[1].substring(0, 1).toUpperCase() + words[1].substring(1);
                        }
                        output += ":   " + Double.toString(fields.getDouble(field));
                        if (words[1].equals("sugars") || words[1].equals("protein")) {
                            output += "g";
                        } else if (words[1].equals("calories")) {
                        } else {
                            output += "mg";
                        }
                        nutritionValues.add(output);
                    }
                }
            }
            ListView list = (ListView) findViewById(R.id.nutrients);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, nutritionValues);

            list.setAdapter(arrayAdapter);
        }
        catch(Exception e) {
        }

    }
    String run(String url) throws IOException {
        OkHttpClient client1 = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client1.newCall(request).execute();
        return response.body().string();
    }
}
