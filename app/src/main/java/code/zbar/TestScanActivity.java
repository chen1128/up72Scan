package code.zbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.zbar.lib.ScanCodeActivity;

/**
 * Created by cwb on 2018/1/11.
 */

public class TestScanActivity extends Activity {

    private TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);

        tvInfo = (TextView) findViewById(R.id.tvInfo);

        Intent intent =new Intent(this,ScanCodeActivity.class);
        startActivityForResult(intent,ScanCodeActivity.SCAN_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==ScanCodeActivity.SCAN_CODE){
            String erCodeInfo =data.getStringExtra("erCode");
//            Toast.makeText(TestScanActivity.this, "信息："+erCodeInfo, Toast.LENGTH_SHORT).show();
            tvInfo.setText(erCodeInfo);
        }

    }
}
