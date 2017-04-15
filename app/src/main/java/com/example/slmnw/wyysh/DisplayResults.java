package com.example.slmnw.wyysh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




public class DisplayResults extends AppCompatActivity {

    private List<String> RESULTLIST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        /*ImageView picView = (ImageView)findViewById(R.id.imageDisplay);
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/calorieme.jpg");
        picView.setImageBitmap(bitmap);*/

        RESULTLIST = new ArrayList<String>();

        ListView list = (ListView) findViewById(R.id.resultingList);

       // intent.putStringArrayListExtra("RESULTLIST", (ArrayList<String>) RESULTLIST);
        ArrayList<String> RESULTLIST = getIntent().getStringArrayListExtra("resultList");
       // System.out.println(RESULTLIST.size());





//////////////////////////////////////////////////////////////OKHttp///////////////////////////////////////////////

        OkHttpClient client = new OkHttpClient();
        ArrayList<String> acceptableFoods = new ArrayList<String>();
        for(int x=0; x < RESULTLIST.size(); x+=2) {
            System.out.println(RESULTLIST.get(x) + " " + String.valueOf(RESULTLIST.get(x+1)));
            if (Float.parseFloat(RESULTLIST.get(x+1)) > 0.85 ) {
                String url = "https://api.nutritionix.com/v1_1/search/" + RESULTLIST.get(x) + "?results=0%3A1&cal_min=0&cal_max=50000&fields=nf_calories&appId=840e8f6d&appKey=9f433f3c4fbaf449b1f2530b36ce4fe1";
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    JSONObject holder = new JSONObject(response.body().string());
                    double calories = ((JSONObject) holder.getJSONArray("hits").get(0)).getJSONObject("fields").getDouble("nf_calories");
                    acceptableFoods.add(RESULTLIST.get(x) + " " + String.valueOf(calories));
                } catch (Exception e) {
                }
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, acceptableFoods);

        list.setAdapter(arrayAdapter);

    }
}
