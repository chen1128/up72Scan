package com.zbar.lib.scaner;

import android.os.Handler;
import android.os.Looper;

import com.zbar.lib.ScanCodeActivity;

import java.util.concurrent.CountDownLatch;


/**
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

    ScanCodeActivity activity;
    private Handler handler;
    private final CountDownLatch handlerInitLatch;

    DecodeThread(ScanCodeActivity activity) {
        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
