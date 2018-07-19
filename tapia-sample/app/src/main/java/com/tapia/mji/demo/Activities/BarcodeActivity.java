package com.tapia.mji.demo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tapia.mji.demo.Integration.BarcodeIntegrator;
import com.tapia.mji.demo.Integration.BarcodeResult;
import com.tapia.mji.demo.R;
import com.tapia.mji.tapialib.Activities.TapiaActivity;
import com.tapia.mji.tapialib.Providers.Interfaces.TTSProvider;
import com.tapia.mji.tapialib.TapiaApp;

public class BarcodeActivity extends  TapiaActivity {
    TextView codeView;
    TextView apiView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        ttsProvider = TapiaApp.currentLanguage.getTTSProvider();

        codeView = (TextView) findViewById(R.id.codeView);
        apiView = (TextView) findViewById(R.id.apiResultView);

    }

    public void onActivityResult(int request, int result, Intent i) {
        BarcodeResult scan   = BarcodeIntegrator.parseActivityResult(request,result, i);

        if (scan!=null) {
            codeView.setText(scan.getContents());
            apiView.setText(scan.getFormatName());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putString("apiResult", apiView.getText().toString());
        state.putString("code", codeView.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        apiView.setText(state.getString("apiResult"));
        codeView.setText(state.getString("code"));
    }

}
