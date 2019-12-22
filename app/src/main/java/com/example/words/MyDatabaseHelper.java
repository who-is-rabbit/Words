package com.example.words;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_WORDS="create table F("
            +"id integer primary key autoincrement,"
            +"word text,"
            +"mean text,"
            +"simple text)";
    private Context mContext;
    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_WORDS);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_LONG).show();
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }
}
