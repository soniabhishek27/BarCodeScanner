package com.example.qr_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import com.example.qr_scanner.MainActivity;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);

        setContentView(ScannerView);

    }

    @Override
    public void handleResult(Result result) {

        MainActivity.Result.setText(result.getText());
        //MainActivity.sendData.setVisibility(View.VISIBLE);

        //com.example.qr_scanner.Result.output.setText(result.getText());


        onBackPressed();


    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();

    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
