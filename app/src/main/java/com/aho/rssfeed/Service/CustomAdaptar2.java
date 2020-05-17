package com.aho.rssfeed.Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aho.rssfeed.Database.FeedEntry;
import com.aho.rssfeed.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdaptar2 extends ArrayAdapter<FeedEntry> {
    ArrayList<FeedEntry> feed;
    Context cont;
    //AppDatabase appDatabase;

    public CustomAdaptar2(ArrayList<FeedEntry> fe, Context c){
        super(c,R.layout.list_item_custom2, fe);
//        appDatabase= AppDatabase.initDb(c.getApplicationContext());
//        feed= (ArrayList<FeedEntry>) appDatabase.Feeddao().getFeed();
        this.feed=fe;
        this.cont=c;
    }

    @Override
    public FeedEntry getItem(int position) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        return feed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return feed.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(cont).inflate(R.layout.list_item_custom2,parent,false);
        }

        TextView id_Txt = (TextView) convertView.findViewById(R.id.id);
        TextView titleTxt= (TextView) convertView.findViewById(R.id.item_Title);
        titleTxt.setTextColor(Color.BLUE);
        TextView descTxt= (TextView) convertView.findViewById(R.id.item_Description);
        TextView dateTxt= (TextView) convertView.findViewById(R.id.item_UpdateDate);
        ImageView img= (ImageView) convertView.findViewById(R.id.item_Image);

        FeedEntry article= (FeedEntry) this.getItem(position);

        int id = article.getId();
        String title = article.getTitle();
        String desc = article.getDescription();
        String date = article.getUpdatedat();
        String imageUrl = article.getStoryimage();

        id_Txt.setText(String.valueOf(id));
        titleTxt.setText(title);
        descTxt.setText(desc);
        dateTxt.setText(date);
        img.setTag(imageUrl);
        System.out.println(id);
        System.out.println(title);
        System.out.println(desc);
        System.out.println(date);
        System.out.println(imageUrl);
        new DownloadImageTask().execute(img);
        return convertView;
    }

    public static class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {
        ImageView imageView=null;
        private static final String TAG = "DownloadImageTask";
        @SuppressLint("WrongThread")
        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
//            Log.d(TAG, "doInBackground: image downloading start");
            this.imageView=imageViews[0];
            return download_Image((String)imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
//            Log.d(TAG, "onPostExecute: image downloading end");

        }
        private Bitmap download_Image(String url){
            Bitmap bmp=null;
            try{
                URL url1=new URL(url);
                HttpURLConnection con=(HttpURLConnection)url1.openConnection();
                InputStream is=con.getInputStream();
                bmp= BitmapFactory.decodeStream(is);
                if(null!=bmp)
                    return bmp;
            }catch(Exception e){
                Log.d(TAG, "download_Image: Error in Downloading the image"+e.getMessage());
            }
            return bmp;
        }
    }
}
