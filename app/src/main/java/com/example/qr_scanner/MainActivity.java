package com.example.qr_scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.BasicHttpParams;

import org.apache.http.params.HttpParams;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   public static Button ScanButton,sendData;
   public static TextView Result;
   public static EditText text;

   //Google Sheets//
   // public static final String URL ="https://script.google.com/macros/s/AKfycbxLQGSmN9S6eOiWrfnFYn2P4OHiDmCVP0JpKiKKD95KWBNrsicJ/exec";
    //Server
    public static final String URL = "http://1234image.000webhostapp.com/pictures/";
   private static final int CAMERA_REQUEST_CODE = 2828;

   String CameraPermission[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = findViewById(R.id.text);

        CameraPermission = new String[]
                {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};


        Permission();

        ScanButton = findViewById(R.id.Scanbutton);
        Result = findViewById(R.id.Result);
        sendData = findViewById(R.id.sendData);

       // sendData.setVisibility(View.INVISIBLE);


        //Start Scanning
        ScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartScan();
            }
        });
        //Send Data to server
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendData();

            }
        });
    }




    public void StartScan()
    {
        //CameraPermission();

        startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));

    }



    public void Permission()
    {
        if(!checkCameraPermission())
        {
            RequestCameraPermission();
        }
        else
        {
            System.out.println("Camera Permissions already granted");
        }

    }


    public boolean checkCameraPermission()
    {
        boolean Camera_request = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        return Camera_request;

    }

    public void RequestCameraPermission()
    {
        ActivityCompat.requestPermissions(this,CameraPermission,CAMERA_REQUEST_CODE);
    }


    public void SendData()
    {

        new PostData(Result.getText().toString()).execute();


    }
    private class PostData extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog pd = new ProgressDialog(MainActivity.this);
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd.setMessage("Uploading To Server");
            pd.show();

        }
        String data;

        public PostData(String data)
        {
            this.data = data;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("sdata",data));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(URL);


            post.setEntity(new UrlEncodedFormEntity(dataToSend));
             client.execute(post);
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.hide();
            pd.dismiss();
            Toast.makeText(MainActivity.this,"Data Sent to Server"+URL,Toast.LENGTH_LONG).show();

        }
    }

    private HttpParams getHttpRequestParams()
    {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams,1000 * 60);
        HttpConnectionParams.setSoTimeout(httpRequestParams,1000 * 60);
        return httpRequestParams;

    }
}
