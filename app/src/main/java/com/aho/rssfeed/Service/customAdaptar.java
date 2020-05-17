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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aho.rssfeed.Database.FeedEntry;
import com.aho.rssfeed.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class customAdaptar extends BaseAdapter {
    List<FeedEntry> feed;
    Context cont;


    public customAdaptar(ArrayList<FeedEntry> fe, Context c){
            this.feed=fe;
            this.cont=c;
    }
    @Override
    public Object getItem(int position) {
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
            convertView= LayoutInflater.from(cont).inflate(R.layout.list_item_custom,parent,false);
        }

        TextView titleTxt= (TextView) convertView.findViewById(R.id.item_Title);
        titleTxt.setTextColor(Color.BLUE);
        TextView descTxt= (TextView) convertView.findViewById(R.id.item_Description);
        TextView dateTxt= (TextView) convertView.findViewById(R.id.item_UpdateDate);
        Button save = (Button) convertView.findViewById(R.id.save);
        ImageView img= (ImageView) convertView.findViewById(R.id.item_Image);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//              //  ViewGroup row = (ViewGroup) listApps.getParent();
//                TextView title_view = (TextView) row.findViewById(R.id.item_Title);
//
//                TextView description_view = (TextView) row.findViewById(R.id.item_Description);
//                TextView update_view = (TextView) row.findViewById(R.id.item_UpdateDate);
//                ImageView image_view = (ImageView) row.findViewById(R.id.item_Image);
//
//
//                FeedEntry feed= new FeedEntry(
//                        title_view.getText().toString(),
//                        update_view.getText().toString(),
//                        image_view.getTag().toString(),
//                        description_view.getText().toString());
//
//                appDatabase.Feeddao().insertFeed(feed);
//                System.out.println(feed.title+" done");
//                System.out.println(feed.updatedat+" done");
//                System.out.println(feed.description+" done");
//                System.out.println(feed.storyimage+" done");
//                int size =appDatabase.Feeddao().getFeed().size();
//
//                Toast.makeText(LiveFeeds.this, title_view.getText()+" "+String.valueOf(size) ,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        FeedEntry article= (FeedEntry) this.getItem(position);

        String title=article.getTitle();
        String desc=article.getDescription();
        String date=article.getUpdatedat();
        String imageUrl=article.getStoryimage();

        titleTxt.setText(title);
        descTxt.setText(desc);
        dateTxt.setText(date);
        img.setTag(imageUrl);
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
