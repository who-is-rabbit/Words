package com.example.words;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_word extends AppCompatActivity {
    private MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "F.db", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Button ok=(Button)findViewById(R.id.add_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText wordText=(EditText)findViewById(R.id.add_word);
                final EditText meanText=(EditText)findViewById(R.id.add_mean);
                final EditText epText=(EditText)findViewById(R.id.add_ep);
                if(wordText.length()==0){//word为空，出错
                    Toast.makeText(Add_word.this,"添加失败，单词不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(meanText.length()==0){//mean为空，出错
                    Toast.makeText(Add_word.this,"添加失败，释义不能为空",Toast.LENGTH_SHORT).show();
                }
                else {//添加成功结束
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into F(word,mean,simple) values(?,?,?)",new String[]{wordText.getText().toString(),meanText.getText().toString(),epText.getText().toString()});
                    Toast.makeText(Add_word.this,wordText.getText().toString()+"添加成功",Toast.LENGTH_SHORT).show();
                    db.close();
                    dbHelper.close();

                }
            }
        });
    }
}
