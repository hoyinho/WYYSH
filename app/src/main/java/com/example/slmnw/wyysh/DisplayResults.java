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

import java.util.ArrayList;
import java.util.List;



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
        System.out.println(RESULTLIST.size());
        for(String result : RESULTLIST) {

        System.out.println(result);
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, RESULTLIST);

        list.setAdapter(arrayAdapter);
        System.out.println("jajajaja");


    }
}
