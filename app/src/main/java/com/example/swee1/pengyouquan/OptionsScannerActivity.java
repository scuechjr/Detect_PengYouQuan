package com.example.swee1.pengyouquan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;

public class OptionsScannerActivity extends AppCompatActivity implements OnScannerCompletionListener {
    private static final String SCAN_RESULT_KEY = "scan_result";
    private ScannerView mScannerView;

    public static String getScannerResult(Intent intent) {
        if (null != intent) {
            return intent.getStringExtra(SCAN_RESULT_KEY);
        }
        return null;
    }

    public static void gotoActivity(Activity activity) {
        activity.startActivity(new Intent(activity, OptionsScannerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scanner_options);

        mScannerView = (ScannerView) findViewById(R.id.scanner_view);

        mScannerView.setOnScannerCompletionListener(this);

//        mScannerView.toggleLight(true);

        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder
                .setFrameStrokeColor(Color.WHITE)
                .setFrameStrokeWidth(1.5f)
//                .setFrameSize(256, 256)
//                .setFrameCornerLength(22)
//                .setFrameCornerWidth(2)
//                .setFrameCornerColor(0xff06c1ae)
//                .setFrameCornerInside(true)

//                .setLaserLineColor(0xff06c1ae)
//                .setLaserLineHeight(18)

//                .setLaserStyle(ScannerOptions.LaserStyle.RES_LINE,R.mipmap.wx_scan_line)

//                .setLaserStyle(ScannerOptions.LaserStyle.RES_GRID, R.mipmap.zfb_grid_scan_line)//网格图
//                .setFrameCornerColor(0xFF26CEFF)//支付宝颜色

//                .setScanFullScreen(true)

//                .setFrameHide(false)
//                .setFrameCornerHide(false)
//                .setLaserMoveFullScreen(false)

//                .setViewfinderCallback(new ScannerOptions.ViewfinderCallback() {
//                    @Override
//                    public void onDraw(View view, Canvas canvas, Rect frame) {
//                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.connect_logo);
//                        canvas.drawBitmap(bmp, frame.right / 2, frame.top - bmp.getHeight(), null);
//                    }
//                })

                .setScanMode(BarcodeFormat.QR_CODE)
                .setTipText("将二维码/条码放入框内，即可自动扫描")
                .setTipTextSize(12)
                .setTipTextColor(getResources().getColor(R.color.colorAccent))
//                .setCameraZoomRatio(2)
        ;

        mScannerView.setScannerOptions(builder.build());
    }


    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
//        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
        vibrate();
        mScannerView.restartPreviewAfterDelay(0);

        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra(SCAN_RESULT_KEY, rawResult.getText());
        NavUtils.navigateUpTo(this, intent);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
