package com.example.words;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    private MyDatabaseHelper dbHelper;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view=inflater.inflate(R.layout.detail_fragment,container,false);
        return view;
    }
    public void refresh(String Word,String Mean,String Ep){//用于在界面显示标题和内容
        View visibilityLayout=view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView WordText=(TextView)view.findViewById(R.id.word);
        TextView MeanText=(TextView)view.findViewById(R.id.mean);
        TextView EpText=(TextView)view.findViewById(R.id.ep);
        WordText.setText(Word);//刷新单词
        MeanText.setText("\n\n释义：\n  "+Mean);//刷新释义
        EpText.setText("\n\n例句：\n  "+Ep);//刷新释义
    }
}
