package com.example.words;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private MyDatabaseHelper dbHelper;
   private String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> words=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new MyDatabaseHelper(this,"F.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("F",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String word=cursor.getString(cursor.getColumnIndex("word"));
                words.add(word);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        final ArrayAdapter<String>adapter=new ArrayAdapter<String>(
                MainActivity.this,android.R.layout.simple_list_item_1,words
        );
        final ListView listView=(ListView)findViewById(R.id.lis_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,show_details.class);
                word=(String)adapter.getItem(i);
                intent.putExtra("word",word);
                startActivity(intent);
            }
        });
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle("选择操作");
                contextMenu.add(0,0,0,"修改");
                contextMenu.add(0,1,0,"删除");
            }
        });
        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.land);
            List<String> Gwords=new ArrayList<>();
            dbHelper=new MyDatabaseHelper(this,"F.db",null,1);
            SQLiteDatabase Gdb=dbHelper.getWritableDatabase();
            Cursor Gcursor=db.query("F",null,null,null,null,null,null);
            if(Gcursor.moveToFirst()){
                do{
                    String word=Gcursor.getString(Gcursor.getColumnIndex("word"));
                    Gwords.add(word);
                }
                while (Gcursor.moveToNext());
            }
            Gcursor.close();
            final ArrayAdapter<String>Gadapter=new ArrayAdapter<String>(
                    MainActivity.this,android.R.layout.simple_list_item_1,Gwords
            );
            final ListView lisView=(ListView)findViewById(R.id.list_view);
            lisView.setAdapter(adapter);
            lisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String word=(String)Gadapter.getItem(i);
                    DetailFragment detail_fragment=(DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
                    SQLiteDatabase db=dbHelper.getWritableDatabase();
                    Cursor cursor=db.rawQuery("select * from F where word=?",new String[]{word});
                    cursor.moveToFirst();
                    String Sword=cursor.getString(cursor.getColumnIndex("word"));
                    String Smean=cursor.getString(cursor.getColumnIndex("mean"));
                    String Ssimple=cursor.getString(cursor.getColumnIndex("simple"));
                    db.close();
                    detail_fragment.refresh(Sword,Smean,Ssimple);
                }
            });
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                AlertDialog.Builder c_dialog=new AlertDialog.Builder(this);
                final View change_view= LayoutInflater.from(MainActivity.this).inflate(R.layout.change_dialog,null);
                c_dialog.setTitle("修改单词");
                c_dialog.setView(change_view);
                final EditText word_text=((EditText) change_view.findViewById(R.id.change_word));
                final EditText mean_text=change_view.findViewById(R.id.change_mean);
                final EditText simple_text=change_view.findViewById(R.id.change_simple);
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select * from F where word=?",new String[]{word});
                cursor.moveToFirst();
                String Cword=cursor.getString(cursor.getColumnIndex("word"));
                String Cmean=cursor.getString(cursor.getColumnIndex("mean"));
                String Csimple=cursor.getString(cursor.getColumnIndex("simple"));
                word_text.setText(Cword);
                mean_text.setText(Cmean);
                simple_text.setText(Csimple);
                db.close();
                cursor.close();
                c_dialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String f_word=word_text.getText().toString();
                        String f_mean=mean_text.getText().toString();
                        String f_simple=simple_text.getText().toString();
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        db.execSQL("update F set word=?,mean=?,simple=? where word=?",new String[]{f_word,f_mean,f_simple,word});
                        db.close();
                        Toast.makeText(MainActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }
                });
                c_dialog.setNegativeButton("取消",null);
                c_dialog.show();
                break;
            case 1:
        }

        return super.onContextItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,Add_word.class);
                startActivity(intent);
                break;
            case R.id.search:
                Intent search=new Intent(MainActivity.this,Search_word.class);
                startActivity(search);
                break;
        }
        return true;
    }
}
