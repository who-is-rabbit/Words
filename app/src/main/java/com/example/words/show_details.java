package com.example.words;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class show_details extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Intent intent=getIntent();
        String iword=intent.getStringExtra("word");
        dbHelper=new MyDatabaseHelper(this,"F.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from F where word=?",new String[]{iword});
        cursor.moveToFirst();
        String Sword=cursor.getString(cursor.getColumnIndex("word"));
        String Smean=cursor.getString(cursor.getColumnIndex("mean"));
        String Ssimple=cursor.getString(cursor.getColumnIndex("simple"));
        db.close();
        TextView word=(TextView)findViewById(R.id.word);
        word.setText(Sword);
        TextView mean=(TextView)findViewById(R.id.mean);
        mean.setText(Smean);
        TextView simple=(TextView)findViewById(R.id.simple);
        simple.setText(Ssimple);
    }
}
