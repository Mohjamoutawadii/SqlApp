package com.example.sqlapp.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.sqlapp.entities.Marque;

public class MarqueService {


    private Context context;

    private static final String TABLE_MARQUE = "marque";


    private static final String KEY_ID = "id";
    private static final String KEY_Code = "code";

    private static final String KEY_LIBELLE = "libelle";


    private static String[] COLUMNS_MARQUE = {KEY_ID, KEY_Code, KEY_LIBELLE};

    private MyDataBaseHelper helper;

    public MarqueService(Context context) {
        this.helper = new MyDataBaseHelper(context);
        this.context = context;
    }

    public void create(Marque e) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Code, e.getCode());
        values.put(KEY_LIBELLE, e.getLibelle());
        long result=db.insert(TABLE_MARQUE,
                null,
                values);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public List<Marque> findAll(){
        List<Marque> eds = new ArrayList<>();
        String req ="select * from "+TABLE_MARQUE;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Marque e = null;
        if(c.moveToFirst()){
            do{
                e = new Marque();
                e.setId(c.getInt(0));
                e.setCode(c.getString(1));
                e.setLibelle(c.getString(2));
                eds.add(e);
            }while(c.moveToNext());
        }
        return eds;
    }
    public void update(Marque e,int id){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_Code, e.getCode());
        values.put(KEY_LIBELLE, e.getLibelle());

        long result=db.update(TABLE_MARQUE,
                values,
                "id = ?",
                new String[]{String.valueOf(id)});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_MARQUE,
                "id = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
