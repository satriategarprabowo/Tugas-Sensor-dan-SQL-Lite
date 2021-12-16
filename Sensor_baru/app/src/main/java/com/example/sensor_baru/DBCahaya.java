package com.example.sensor_baru;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBCahaya extends SQLiteOpenHelper{

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    static abstract class MyColumns implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "Cahaya";
        static final Integer id =0;
        static final String Nilai = "nilai";
    }

    private static final String NamaDatabse = "cahaya.db";
    private static final int VersiDatabase = 14;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+MyColumns.NamaTabel+
            "("+MyColumns.id+" INTEGER PRIMARY KEY, "+MyColumns.Nilai+" TEXT NOT NULL)";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    DBCahaya(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}