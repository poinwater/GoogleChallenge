package com.example.googlechallenge;

public class Item {
    String _name;
    int _icon;
    int _id;
    int _value;
    int _rare_level;
    int _amount;

    public Item(String name, int icon, int id, int value, int rare_level) {
        this._name = name;
        this._icon = icon;
        this._id = id;
        this._value = value;
        this._rare_level = rare_level;
    }

    public String getName(){
        return this._name;
    }
    public int getIcon() { return this._icon; }
    public int getValue() {return this._value; }


}

