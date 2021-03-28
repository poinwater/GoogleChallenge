package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sleep_time_table")
public class Sleep {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "duration")
    private long duration;

    public Sleep(@NonNull String date, @NonNull long duration) {
        this.date = date;
        this.duration = duration;
    }

    @NonNull
    public long getDuration() {
        return this.duration;
    }

    @NonNull
    public String getDate() {
        return this.date;
    }
}
