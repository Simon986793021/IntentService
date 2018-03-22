package com.wind.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public final static String ACTION_THREAD = "action.thread";
    public final static String INTENT_TAG = "com.wind.intent";
    private TextView textView;
    private Button button;
    private ProgressBar progressBar;
    private TextView percent;
    private LocalBroadcastManager localBroadcastManager;
    private MyBroadCastReceiver myBroadCastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_text);
        progressBar = findViewById(R.id.pb_progress);
        percent=findViewById(R.id.tv_percent);
        button=findViewById(R.id.bt_button);
        //registerReceiver
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myBroadCastReceiver=new MyBroadCastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_THREAD);
        localBroadcastManager.registerReceiver(myBroadCastReceiver,intentFilter);
        //initView
        textView.setText("线程状态：未运行");
        progressBar.setMax(100);
        progressBar.setProgress(0);
        percent.setText("0%");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SIMON","SIMON");
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra(INTENT_TAG,"firstintent");
                startService(intent);
                intent.putExtra(INTENT_TAG,"secondintent");
                startService(intent);//mutiTask test
            }
        });
    }

    //define broadcastreceiver
    public class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_THREAD:
                    int progress = intent.getIntExtra("count", 0);
                    String text = intent.getStringExtra("status");
                    textView.setText(text);
                    percent.setText(progress + "%");
                    progressBar.setProgress(progress);
                    if (progress >= 100) {
                        textView.setText("线程结束");
                        break;
                    }
                default:
                    break;
            }

        }
    }
}
