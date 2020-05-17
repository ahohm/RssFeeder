package com.aho.rssfeed.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.aho.rssfeed.Database.AppDatabase;
import com.aho.rssfeed.Database.FeedEntry;
import com.aho.rssfeed.R;
import com.aho.rssfeed.Service.CustomAdaptar2;

import java.util.ArrayList;
import java.util.List;

public class SavedFeed extends AppCompatActivity {
    AppDatabase appDatabase;
    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feeds);
        appDatabase= AppDatabase.initDb(getApplicationContext());
        listApps=(ListView) findViewById(R.id.listApps);
        List<FeedEntry>  feedEntries=  appDatabase.Feeddao().getFeed();

        listApps.setAdapter(new CustomAdaptar2((ArrayList<FeedEntry>) feedEntries,SavedFeed.this));

        System.out.println("Visual Purpuse");
        System.out.println(feedEntries.size());
//        System.out.println(feedEntries.get(feedEntries.size()-1).title);
//        System.out.println(feedEntries.get(feedEntries.size()-1).updatedat);
//        System.out.println(feedEntries.get(feedEntries.size()-1).storyimage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedtype_menu,menu);
        return true;
    }

    public void onButtonDeleteFeed(View v){
        ViewGroup row = (ViewGroup) listApps.getParent();
        TextView idTxt = (TextView) row.findViewById(R.id.id);
        String id = idTxt.getText().toString();
        TextView title_Txt = (TextView) row.findViewById(R.id.item_Title);

        String title = title_Txt.getText().toString();
        System.out.println(id+" "+title);
//        appDatabase.Feeddao().deleteFeed(id);


        Toast.makeText(SavedFeed.this, id+" "+title ,
                Toast.LENGTH_SHORT).show();

    }
}

