package com.zbar.lib.scaner;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.dtr.zbar.build.ZBarDecoder;
import com.zbar.lib.R;
import com.zbar.lib.ScanCodeActivity;
import com.zbar.lib.ZbarManager;



/**
 * 描述: 接受消息后解码
 */
final class DecodeHandler extends Handler {

    ScanCodeActivity activity = null;

    DecodeHandler(ScanCodeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.decode) {
            decode((byte[]) message.obj, message.arg1, message.arg2);

        } else if (message.what == R.id.quit) {
            Looper.myLooper().quit();

        }
    }

    private void decode(byte[] data, int width, int height) {
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;

        ZbarManager manager = new ZbarManager();
        String result = manager.decode(rotatedData, width, height, true,
                activity.getX(), activity.getY(), activity.getCropWidth(),
                activity.getCropHeight());
        ZBarDecoder zBarDecoder = new ZBarDecoder();
        String result_line = zBarDecoder.decodeRaw(rotatedData, width, height);
        if (result != null) {
            if (null != activity.getHandler()) {
                Message msg = new Message();
                msg.obj = result;
                msg.what = R.id.decode_succeeded;
                activity.getHandler().sendMessage(msg);
            }
        } else {
            if (result_line != null) {
                if (null != activity.getHandler()) {
                    Message msg = new Message();
                    msg.obj =result_line;
                    msg.what = R.id.decode_succeeded;
                    activity.getHandler().sendMessage(msg);
                }
            } else {
                if (null != activity.getHandler()) {
                    activity.getHandler().sendEmptyMessage(R.id.decode_failed);
                }
            }
            //Toast.makeText(activity, result_line, 0).show();
        }
    }

}
