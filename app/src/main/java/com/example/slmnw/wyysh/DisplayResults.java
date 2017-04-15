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
       /* for(String result : RESULTLIST) {

        System.out.println(result);
        }*/


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, RESULTLIST);

        list.setAdapter(arrayAdapter);



//////////////////////////////////////////////////////////////OKHttp///////////////////////////////////////////////

        OkHttpClient client = new OkHttpClient();
        for(int x=0; x < RESULTLIST.size(); x++) {
            System.out.println(RESULTLIST.get(x));
            String url = "https://api.nutritionix.com/v1_1/search/" + RESULTLIST.get(x) + "?results=0%3A1&cal_min=0&cal_max=50000&fields=nf_calories&appId=840e8f6d&appKey=9f433f3c4fbaf449b1f2530b36ce4fe1";
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());
            } catch (Exception e) {

            }
        }

    }
}
