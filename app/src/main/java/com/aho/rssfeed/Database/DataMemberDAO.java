package com.aho.rssfeed.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DataMemberDAO {
    @Insert
    Long insertData(DataMember dataMember);

    @Query("SELECT * FROM `datamember_db` where username= :user and password= :password")
    DataMember getMember(String user, String password);
}
