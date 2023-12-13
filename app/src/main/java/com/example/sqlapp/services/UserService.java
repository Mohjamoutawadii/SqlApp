package com.example.sqlapp.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.sqlapp.entities.User;

public class UserService {


    private Context context;


    private static final String TABLE_USER = "user";

    private static final String KEY_ID = "id";


    private static final String KEY_name = "FullName";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    private static String[] COLUMNS_USER = {KEY_ID, KEY_USERNAME, KEY_PASSWORD};

    private MyDataBaseHelper helper;


    public UserService(Context context) {
        this.helper = new MyDataBaseHelper(context);
        this.context = context;
    }


    public void createUser(User user) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_name, user.getFullName());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        long result = db.insert(TABLE_USER, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        User user;
        if (c.moveToFirst()) {
            do {
                user = new User();
                user.setId(c.getInt(0));
                user.setFullName(c.getString(1));
                user.setUsername(c.getString(2));
                user.setPassword(c.getString(3));
                users.add(user);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return users;
    }


}
