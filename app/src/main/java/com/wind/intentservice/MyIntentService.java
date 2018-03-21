package com.wind.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

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
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 1000) {
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
}
