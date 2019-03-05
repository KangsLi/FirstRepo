package com.example.goaheadlkk.small_game_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    TextView text_dwon, text_time, text_ci, moemy_ci;
    int recLen = 9, i = 0, j = 0;
    String k = null;
    Timer timer = new Timer();
    SharedPreferences sp;
    Context ctx;
    ImageButton text_topBack;
    Button btn_play,btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ctx = MainActivity.this;

        sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        text_dwon = (TextView) findViewById(R.id.text_dwon);
        text_time = (TextView) findViewById(R.id.text_time);
        text_ci = (TextView) findViewById(R.id.text_ci);
        moemy_ci = (TextView) findViewById(R.id.moemy_ci);
        text_topBack  = (ImageButton) findViewById(R.id.text_topBack);
        btn_play=(Button)findViewById(R.id.play);
        btn_exit=(Button)findViewById(R.id.exit);
        if (!sp.getString("user","").equals("")) {
            //get highest score
            k = sp.getString("user", "");
            //convert to Int
            j = Integer.valueOf(k).intValue();
            moemy_ci.setText("Highest : " + k);
        }
        //hide control
        text_time.setVisibility(View.GONE);
        text_dwon.setVisibility(View.GONE);
        text_ci.setVisibility(View.GONE);
        moemy_ci.setVisibility(View.GONE);
        ((TextView)findViewById(R.id.text_tip)).setVisibility(View.GONE);
        ((FrameLayout)findViewById(R.id.frameLayout)).setVisibility(View.GONE);
        text_topBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                i += 1;
                if(i<5){
                    text_dwon.setBackgroundResource(R.drawable.bear);
                }else if(i<10){
                    text_dwon.setBackgroundResource(R.drawable.elephant);
                }else if(i<15){
                    text_dwon.setBackgroundResource(R.drawable.honey);
                }else if(i<20){
                    text_dwon.setBackgroundResource(R.drawable.cat);
                }else if(i<25){
                    text_dwon.setBackgroundResource(R.drawable.fish);
                }else if(i<30){
                    text_dwon.setBackgroundResource(R.drawable.giraffe);
                }else if(i<35){
                    text_dwon.setBackgroundResource(R.drawable.kangaroo);
                }else {
                    text_dwon.setBackgroundResource(R.drawable.lion);
                }
                text_ci.setText("score: "+i);
            }
        });
        btn_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.schedule(task, 1000, 1000); // timeTask
                //hide control

                btn_exit.setVisibility(View.GONE);
                btn_play.setVisibility(View.GONE);
                //set control visable
                text_time.setVisibility(View.VISIBLE);
                text_dwon.setVisibility(View.VISIBLE);
                text_ci.setVisibility(View.VISIBLE);
                moemy_ci.setVisibility(View.VISIBLE);
                ((FrameLayout)findViewById(R.id.frameLayout)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.text_tip)).setVisibility(View.VISIBLE);
            }
        });
        btn_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    text_time.setText(recLen + 1 + " seconds");
                    if (recLen < 0) {// Event after time expires
                        timer.cancel();
                        text_dwon.setClickable(false);
                        dialog();// Dialog when game over
                        if (i > j) {
                            //Save the highest score
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("user", String.valueOf(i));
                            editor.commit();
                        }
                    }
                }
            });
        }
    };

    protected void dialog() {
        AlertDialog.Builder builder = new Builder(MainActivity.this);
        builder.setMessage("Your score:" + i);
        builder.setTitle("Game Over");
        builder.setPositiveButton("Exit",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setNegativeButton("Replay",

                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                });
        builder.create().show();
    }
}
