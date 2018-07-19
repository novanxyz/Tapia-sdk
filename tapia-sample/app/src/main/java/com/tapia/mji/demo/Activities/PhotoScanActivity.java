package com.tapia.mji.demo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.tapia.mji.demo.Integration.BarcodeIntegrator;
import com.tapia.mji.demo.Integration.BarcodeResult;
import com.tapia.mji.demo.R;
import com.tapia.mji.tapialib.Actions.Action;
import com.tapia.mji.tapialib.Activities.TapiaActivity;
import com.tapia.mji.tapialib.Exceptions.LanguageNotSupportedException;
import com.tapia.mji.tapialib.Providers.Interfaces.TTSProvider;
import com.tapia.mji.tapialib.TapiaApp;
import com.tapia.mji.tapialib.Utils.CameraHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 08-Jul-16.
 */
public class PhotoScanActivity extends TapiaActivity {
    Handler mHandler;
    SurfaceView previewView;
    ImageView countdownView;

    List<Action> actionListRepeat;



    static final int SAVE_MODE = 0;
    static final int REPEAT_MODE = 1;
    int mode = -1;

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ttsProvider         = TapiaApp.currentLanguage.getTTSProvider();
        IntentIntegrator integrator = new BarcodeIntegrator(BarcodeActivity.this);
        integrator.initiateScan();

    }


    void sayDelayed(final String speech, int delay){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    ttsProvider.say(speech);
                } catch (LanguageNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        },delay);
    }

    void changedDrawableDalayed(final int drawableID, int delay){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countdownView.setImageResource(drawableID);
                countdownView.invalidate();
            }
        },delay);
    }


}
