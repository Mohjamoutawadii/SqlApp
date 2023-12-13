package com.example.sqlapp.services;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.sqlapp.entities.Machine;

public class MachineService {

    private Context context;

    private static final String TABLE_MACHINE = "machine";

    private static final String KEY_ID = "id";
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_PRIX = "prix";
    private static final String KEY_DATE = "date";
    private static final String KEY_MARQUE_CODE = "marque_code";

    private static String[] COLUMNS_MACHINE = {KEY_ID, KEY_REFERENCE, KEY_PRIX, KEY_DATE, KEY_MARQUE_CODE};

    private MyDataBaseHelper helper;

    public MachineService(Context context) {
        this.helper = new MyDataBaseHelper(context);
        this.context = context;
    }

    public void create(Machine machine) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REFERENCE, machine.getReference());
        values.put(KEY_PRIX, machine.getPrix());
        values.put(KEY_DATE, machine.getDate());
        values.put(KEY_MARQUE_CODE, machine.getMarqueCode());

        long result = db.insert(TABLE_MACHINE, null, values);

        if (result == -1) {
            Toast.makeText(context, "Failed to add machine", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Machine added successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public List<Machine> findAll() {
        List<Machine> machines = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MACHINE;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Machine machine = new Machine();
                machine.setId(cursor.getInt(0));
                machine.setReference(cursor.getString(1));
                machine.setPrix(cursor.getInt(2));
                machine.setDate(cursor.getString(3));
                machine.setMarqueCode(cursor.getString(4));

                machines.add(machine);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return machines;
    }

    public void update(Machine machine, int id) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REFERENCE, machine.getReference());
        values.put(KEY_PRIX, machine.getPrix());
        values.put(KEY_DATE, machine.getDate());
        values.put(KEY_MARQUE_CODE, machine.getMarqueCode());

        long result = db.update(TABLE_MACHINE, values, "id = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to update machine", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Machine updated successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        long result =db.delete(TABLE_MACHINE, "id = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to delete machine", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Machine deleted successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
