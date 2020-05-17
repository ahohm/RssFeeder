package com.aho.rssfeed.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedEntryDAO {
    @Insert
    Long insertFeed(FeedEntry feed);

    @Query("SELECT * FROM `feedEntry`")
    List<FeedEntry> getFeed();

    @Query("DELETE FROM `feedEntry` WHERE id = :id")
    void deleteFeed(int id);
}
