package com.myapp.underscore.imc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "imc.db";
    private static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCmd = "CREATE TABLE persons ("
                            +" id integer primary key autoincrement,"
                            +" nomComplet varchar(30) not null,"
                            +" sexe varchar(10) not null,"
                            +" status varchar(15) not null,"
                            +" taille double not null,"
                            +" poids double not null,"
                            +" imc double not null,"
                            +" description varchar(50) not null,"
                            +" imcDate varchar(255)"
                        +")";
        db.execSQL(sqlCmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlCmd = "DROP TABLE persons";
        db.execSQL(sqlCmd);
        this.onCreate(db);
    }

    public boolean CreatePerson(Person person){
        ContentValues values = new ContentValues();
        values.put("nomComplet",person.getNomComplet());
        values.put("sexe",person.getSexe());
        values.put("status",person.getStatus());
        values.put("taille",person.getTaille());
        values.put("poids",person.getPoids());
        values.put("imc",person.getImc());
        values.put("description",person.getDescription());
        values.put("imcDate",person.getImcDate().getTime().toString());
        long result = getWritableDatabase().insert("persons",null,values);
        if(result == -1)
            return false;
        else
            return true;
    }


    public ArrayList<Person> getAllPeople(){
        ArrayList<Person> people = new ArrayList<>();
        //String[] args = {"Normal","Masculin"};
        //Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM persons WHERE 'description' = ? AND 'sexe' = ?",args);
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM persons",null);
        if(cursor.moveToFirst()){
            do{

                String nom = cursor.getString(1);
                String sexe = cursor.getString(2);
                String status = cursor.getString(3);
                double taille = Double.parseDouble(cursor.getString(4));
                double poids = Double.parseDouble(cursor.getString(5));
                double imc = Double.parseDouble(cursor.getString(6));
                String description = cursor.getString(7);
                Calendar imcDate = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss");
                try {
                    imcDate.setTime(format.parse(cursor.getString(8)));
                } catch (ParseException e) {
                    imcDate = null;
                }

                Person p = new Person(nom,sexe,status,taille,poids,imc,description,imcDate);
                people.add(p);
            }while (cursor.moveToNext());
        }
        return people;
    }

    public int getPeopleCount(){
        return (int) DatabaseUtils.queryNumEntries(getReadableDatabase(),"persons",null);
    }

    public boolean emptyTable(String tableName) {
        String sqlCmd = "DELETE FROM "+tableName;
        try {
            getWritableDatabase().execSQL(sqlCmd);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
