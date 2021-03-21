package com.example.googlechallenge;

//TODO: ShanshanYu Finish the Item Class
// Three items:
// "Bronze Thread, thread1, 1, 5, 3",
// "Silver Thread, thread2, 2, 10, 2"
// "Gold Thread, thread3, 3, 15, 1"
// -- We may need a database(?)

public class Item {
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

}
