package com.aho.rssfeed.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aho.rssfeed.R;

import com.aho.rssfeed.Database.AppDatabase;
import com.aho.rssfeed.Database.FeedEntry;
import com.aho.rssfeed.Service.ParserApplications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LiveFeeds extends AppCompatActivity {

    AppDatabase appDatabase;

    private ListView listApps;
    ArrayList<FeedEntry> feedEntries;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feeds);
        appDatabase= AppDatabase.initDb(getApplicationContext());
        listApps=(ListView) findViewById(R.id.listApps);
        downloadUrl("https://www.jawharafm.net/fr/rss/showRss/88/1/1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedtype_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId( );
        String feedurl;
        switch(id){
            case R.id.divers:
                feedurl="https://www.jawharafm.net/fr/rss/showRss/88/1/1";
                break;
            case R.id.sport:
                feedurl="https://www.jawharafm.net/fr/rss/showRss/88/1/6";
                break;
            case R.id.culture:
                feedurl="https://www.jawharafm.net/fr/rss/showRss/88/1/5";
                break;
            case R.id.tech:
                feedurl="https://www.jawharafm.net/fr/rss/showRss/88/1/4";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        downloadUrl(feedurl);
        return true;

    }

    private void downloadUrl(String feedUrl) {
        DownloadData downloadData=new DownloadData();
        downloadData.execute(feedUrl);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        Context c;
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new ParserApplications(LiveFeeds.this,s,listApps).execute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String rssFeed = downLoadXML(strings[0]);
            if(rssFeed==null) {
                Log.d(TAG, "doInBackground: Error Downloading");
            }
            return rssFeed;
        }

        private String downLoadXML(String urlPath){
            StringBuilder XMLResult=new StringBuilder();

            try{
                URL url=new URL(urlPath);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                int response=connection.getResponseCode();
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead ;
                char[] inputBuffer =new char[500];
                while(true){
                    charsRead=reader.read(inputBuffer);
                    if(charsRead<0)
                    {
                        break;
                    }
                    if(charsRead>0)
                    {
                        XMLResult.append(String.copyValueOf(inputBuffer,0,charsRead));
                    }
                }
                reader.close();

                return XMLResult.toString();
            }
            catch(MalformedURLException e)
            {
                Log.d(TAG, "downLoadXML Invalid URL :"+e.getMessage());
            }
            catch(IOException e){
                Log.d(TAG, "downLoadXML: IOException Reading data "+e.getMessage());
            }
            catch(SecurityException e)
            {
                Log.d(TAG, "downLoadXML: Security exception"+e.getMessage());
            }
            return null;
        }
    }

    public void onButtonReadMore(View v){
        ViewGroup row = (ViewGroup) listApps.getParent();
        TextView link_view = (TextView) row.findViewById(R.id.item_Description);
        String url_link = link_view.getText().toString();

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url_link));
        startActivity(i);

        Toast.makeText(LiveFeeds.this, url_link ,
                Toast.LENGTH_SHORT).show();

    }
//    public void onButtonSave(View v) {
//
//        ViewGroup row = (ViewGroup) listApps.getParent();
//        TextView title_view = (TextView) row.findViewById(R.id.item_Title);
//
//        TextView description_view = (TextView) row.findViewById(R.id.item_Description);
//        TextView update_view = (TextView) row.findViewById(R.id.item_UpdateDate);
//        ImageView image_view = (ImageView) row.findViewById(R.id.item_Image);
//
//
//        FeedEntry feed= new FeedEntry(
//                title_view.getText().toString(),
//                update_view.getText().toString(),
//                image_view.getTag().toString(),
//                description_view.getText().toString());
//
//        appDatabase.Feeddao().insertFeed(feed);
//        System.out.println(feed.title+" done");
//        System.out.println(feed.updatedat+" done");
//        System.out.println(feed.description+" done");
//        System.out.println(feed.storyimage+" done");
//        int size =appDatabase.Feeddao().getFeed().size();
//
//        Toast.makeText(LiveFeeds.this, title_view.getText()+" "+String.valueOf(size) ,
//                Toast.LENGTH_SHORT).show();
//    }


}

