package com.example.lab8_sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SQL extends AppCompatActivity implements View.OnClickListener {
    EditText f1,f2,result;
    Button in,out,del;
    DBHelper dbHelper = null;//определяет объект класса DBHelper
    SQLiteDatabase DB;// объект класса SQLiteDatabase

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        dbHelper = new DBHelper(this,"myDB",null,1);//экземпляр класса DBHelper для вохзможности управления БД

        f1 = (EditText) findViewById(R.id.f1);
        f2 = (EditText) findViewById(R.id.f2);
        result = (EditText) findViewById(R.id.result);

       in = (Button) findViewById(R.id.in);
        in.setOnClickListener(this);

        out = (Button) findViewById(R.id.out);
        out.setOnClickListener(this);

        del = (Button) findViewById(R.id.button4);
        del.setOnClickListener(this);
    }


   @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.in:
                insertIntoDatabase(v);
                break;
            case R.id.out:
                readDatabase(v);
                break;
            case R.id.button4:
                deleteDatabase(v);
                break;
        }
       DB.close();//закрываме экземпляр класса
    }

    public void insertIntoDatabase(View v){
        if(!f1.getText().toString().equals("")&& !f2.getText().toString().equals("")){//если поля ввода не пусты
            Log.d("myLogs","Insert INTO DB ("+f1.getText().toString()+","+f2.getText().toString()+")");

            DB = dbHelper.getWritableDatabase();//разрешение на зпись в БД
           /* String query = "create table if not exist " + dbHelper.TABLE_NAME+" (_id integer primary key autoincrement, " + dbHelper.FIELD_NAME_1
                    +" text, " + dbHelper.FIELD_NAME_2 + " text)";//запрос*/

            ContentValues CV = new ContentValues();//класс ContentValues(), используется для добавления новых строк в БД
            CV.put(dbHelper.FIELD_NAME_1, f1.getText().toString());//метод put() используется для создание новой строки(задает ей значение)
            CV.put(dbHelper.FIELD_NAME_2, f2.getText().toString());//(name/value)
            DB.insert(dbHelper.TABLE_NAME,null,CV);//запись в БД

            f1.setText("");
            f2.setText("");
        }
    }

    public void readDatabase(View v){
        result.setText("");

        Log.d("myLogs","READ FROM DB");
        DB=dbHelper.getReadableDatabase();//разрещение на чтение

        String columns[] = {"_id",dbHelper.FIELD_NAME_1,dbHelper.FIELD_NAME_2};//массив
        Cursor cursor = DB.query(dbHelper.TABLE_NAME, columns,null,null,null,null,"_id");
        //формируем запрос в БД на получение всех значений и результат записываем в обхект класса Cursor
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    result.setText(result.getText().toString() + "\n" + //считывание даных
                    cursor.getString(0) + ") " +
                    cursor.getString(1) + "," +
                    cursor.getString(2));
                }while(cursor.moveToNext());
            }
        }

    }

    public void deleteDatabase(View v){
        Log.d("myLogs","Dekete Databse");
        DB=dbHelper.getWritableDatabase();

        DB.delete(dbHelper.TABLE_NAME,null,null);//удаление таблица TABLE_NAME

    }



}
