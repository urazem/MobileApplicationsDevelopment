package com.example.lab8_sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

/*Таблица*/
public String TABLE_NAME = "first_table";

/*Столбцы*/
public static final String KEY_ID = "_id";
public String FIELD_NAME_1 = "first_field";
public String FIELD_NAME_2="second_field";

public DBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){//конструктор
    super(context,name,factory,version);//вызов конструктора предка
}



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){//При первом запуске БД
    sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + KEY_ID + " integer primary key autoincrement," + FIELD_NAME_1 + " text," + FIELD_NAME_2 +  " text" + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i, int i1){//при обновлении версии
        Log.d("myLogs","| Upgrade |" + sqLiteDatabase.toString());
    }
}
