package com.aho.rssfeed.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "FeedEntry")
public class FeedEntry  implements Serializable {


    @ColumnInfo(name = "id")
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "update")
    public String updatedat;
    @ColumnInfo(name = "image")
    public String storyimage;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "link")
    public String link;

    public FeedEntry() {
    }

    @Ignore
    public FeedEntry(String title, String updatedat, String storyimage, String description, String link) {

        this.title = title;
        this.updatedat = updatedat;
        this.storyimage = storyimage;
        this.description = description;
        this.link = link;
    }

    @Ignore
    public FeedEntry(String title, String update, String image, String url_link) {
        this.title = title;
        this.updatedat = update;
        this.storyimage = image;
        this.link = url_link;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public String getStoryimage() {
        return storyimage;
    }

    public void setStoryimage(String storyimage) {
        this.storyimage = storyimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


//    @Override
//    public String toString() {
//        return "title=" + title + '\n' +
//                ", updatedat=" + updatedat + '\n' +
//                ", storyimage=" + storyimage + '\n' +
//                ", description=" + description + '\n';
//                }


}

