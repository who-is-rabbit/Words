package com.example.words;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Search_word extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText editText;
    private String input_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);
       editText=(EditText)findViewById(R.id.input_word) ;
        Button search=(Button)findViewById(R.id.search);
        dbHelper=new MyDatabaseHelper(this,"F.db",null,1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_word=editText.getText().toString();
                List<String> words=new ArrayList<>();
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select word from F where word like ?",new String[]{"%"+input_word+"%"});
                if(cursor.moveToFirst()){
                    do{
                        String word=cursor.getString(cursor.getColumnIndex("word"));
                        words.add(word);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
                dbHelper.close();
                final ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                        Search_word.this,android.R.layout.simple_list_item_1,words
                );
                final ListView listView=(ListView)findViewById(R.id.search_word);
                listView.setAdapter(adapter);
            }
        });

    }
}
