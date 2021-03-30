package com.example.googlechallenge.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "item_table")
public class Item implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private int _id;

    @NonNull
    @ColumnInfo(name = "name")
    private String _name;

    @NonNull
    @ColumnInfo(name = "icon")
    private String _icon;

    @NonNull
    @ColumnInfo(name = "value")
    private int _value;

    @NonNull
    @ColumnInfo(name = "level")
    private int _rare_level;

    public Item(@NonNull int _id, @NonNull String _name, @NonNull String _icon,  @NonNull int _value, @NonNull int _rare_level) {
        this._name = _name;
        this._icon = _icon;
        this._id = _id;
        this._value = _value;
        this._rare_level = _rare_level;
    }
    @NonNull
    public int getId() {
        return _id;
    }

    @NonNull
    public String getName() {
        return _name;
    }
    @NonNull
    public String getIcon() {
        return _icon;
    }
    @NonNull
    public int getValue() {
        return _value;
    }
    @NonNull
    public int get_rare_level() {
        return _rare_level;
    }


}

