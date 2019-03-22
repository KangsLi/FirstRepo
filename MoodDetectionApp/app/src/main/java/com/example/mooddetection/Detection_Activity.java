package com.example.mooddetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Detection_Activity extends AppCompatActivity {

    FaceppService faceppService;
    TextView Emotion;
    TextView Beauty;
    MyTempView mTempView;
    android.support.v7.widget.Toolbar top;
    int data[]=new int[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Emotion=(TextView)findViewById(R.id.txtEmotion);
        //Beauty=(TextView)findViewById(R.id.txtBeauty);
        top = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(top);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTempView = (MyTempView) findViewById(R.id.mTemp);

        Intent intent = getIntent();
        if (intent != null) {
            data= intent.getIntArrayExtra("emotion");
            int sum=0;
            for(int i=0;i<7;i++)
                sum+=data[i];
            int good=data[3]+data[4];
            double ratio=good/sum;
            mTempView.setTemp(String.valueOf(ratio),this);
        }
    }
}
