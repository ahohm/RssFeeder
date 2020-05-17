package com.aho.rssfeed.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {DataMember.class, FeedEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract DataMemberDAO dao();
    public abstract FeedEntryDAO Feeddao();
    private static  AppDatabase appDatabase;

    public static AppDatabase initDb(Context context) {
        if(appDatabase == null)
            appDatabase= Room.databaseBuilder(context, AppDatabase.class, "database").allowMainThreadQueries().build();
        return appDatabase;
    }

    public static void destroyInstance(){appDatabase=null;}
}
