package com.example.myapplication.item;

import java.io.Serializable;

public class Item implements Serializable {
    String _name;
    String _icon;
    int _id;
    int _value;
    int _rare_level;

    public Item(String name, String icon, int id, int value, int rare_level) {
        this._name = name;
        this._icon = icon;
        this._id = id;
        this._value = value;
        this._rare_level = rare_level;
    }

    public String getName(){
        return this._name;
    }
    public String getIcon() { return this._icon; }
    public int getValue() {return this._value; }


}
