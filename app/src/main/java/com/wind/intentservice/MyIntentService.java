package com.wind.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.wind.intentservice.MainActivity;

/**
 * Created by zhangcong on 2018/3/21.
 */

public class MyIntentService extends IntentService {

    private LocalBroadcastManager localBroadcastManager;
    private boolean isRunning;
    private int count;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MyIntentService() {
        super("MyIntentService");
        Log.i("SIMON","MyIntentService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("SIMON","onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("SIMON","onStartCommand");
        Log.i("SIMON","intent"+intent.getAction());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.i("SIMON","onCreate");
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("SIMON","onHandleIntent");
        try {
            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendMessage("线程运行中",count);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * send message
     */
    private void sendMessage(String status,int count) {
        Intent intent=new Intent(MainActivity.ACTION_THREAD);
        intent.putExtra("status",status);
        intent.putExtra("count",count);
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SIMON","onDestroy");
    }
}
