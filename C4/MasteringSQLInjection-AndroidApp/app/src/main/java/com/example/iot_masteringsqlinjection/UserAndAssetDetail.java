package com.example.iot_masteringsqlinjection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UserAndAssetDetail extends AppCompatActivity {

    TextView username_txt, password_txt, user_id_txt, dev_id_txt, dev_user_id_txt, dev_name_txt, dev_status_txt;

    TextView new_status;

    String dev_id, new_status_txt;
    private  String username, password, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_and_asset_detail);

        Bundle extras = getIntent().getExtras();

        username = extras.getString("username");
        password = extras.getString("password");
        user_id = extras.getString("user_id");

        Toast.makeText(getApplicationContext(), username + password + user_id, Toast.LENGTH_LONG).show();

        username_txt = findViewById(R.id.user);
        password_txt = findViewById(R.id.pass);
        user_id_txt = findViewById(R.id.userid);

        dev_id_txt = findViewById(R.id.dev_id);
        dev_user_id_txt = findViewById(R.id.dev_userid);
        dev_name_txt = findViewById(R.id.dev_name);
        dev_status_txt = findViewById(R.id.dev_status);


        username_txt.setText(username);
        password_txt.setText(password);
        user_id_txt.setText(user_id);

        new_status = findViewById(R.id.new_status_text);

        updateDeviceValues();


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_status_txt = new_status.getText().toString();
                setStatus();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private void updateDeviceValues(){

        new  AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... strings) {

                //Initialize soap request + add parameters
                SoapObject request = new SoapObject("http://service.masteringsqlj.packt.com", "getDevice");
                //Use this to add parameters
                request.addProperty("user_id", username);
                //Declare the version of the SOAP request
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(Utils.ENDPOINT);
                    //this is the actual part that will call the webservice
                    androidHttpTransport.call("http://service.masteringsqlj.packt.com/getDevice", envelope);
                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    if (result != null) {
                        //Get the first property and change the label text
                        Log.d("OUT:", result.getProperty(0).toString());

                        SoapObject root =  ((SoapObject) result.getProperty(0));//.getProperty("getUserReturn"));

                        String id =  root.getProperty("id").toString();
                        String name =  root.getProperty("name").toString();
                        String status = root.getProperty("status").toString();
                        String user_id = root.getProperty("user_id").toString();

                        return id + "-" + name + "-" + status + "-" + user_id;

                    } else {
                        Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "An error occured. Please check the entered values.", Toast.LENGTH_LONG).show();
                }

                return null;
            }

            @Override
            public void onPostExecute(String string){

                String[] splitted_string = string.split("-");

                String id =  splitted_string[0];
                String name =  splitted_string[1];
                String status = splitted_string[2];
                String user_id = splitted_string[3];

                dev_id = id;
                new_status_txt = new_status.getText().toString();

                dev_id_txt.setText(id);
                dev_user_id_txt.setText(user_id);
                dev_name_txt.setText(name);
                dev_status_txt.setText(status);

             }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void setStatus(){

        new  AsyncTask<String, Boolean, Boolean>(){

            @Override
            protected Boolean doInBackground(String... strings) {

                //Initialize soap request + add parameters
                SoapObject request = new SoapObject("http://service.masteringsqlj.packt.com", "setStatus");
                //SOAP Request parameters
                request.addProperty("id", dev_id);
                request.addProperty("status", new_status_txt);
                //Declare the version of the SOAP request
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(Utils.ENDPOINT);
                    //this is the actual part that will call the webservice
                    androidHttpTransport.call("http://service.masteringsqlj.packt.com/setStatus", envelope);
                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    if (result != null) {
                        //Get the first property and change the label text
                        Log.d("OUT:", result.getProperty(0).toString());

                        String bool_res = result.getProperty("setStatusReturn").toString();

                        return Boolean.getBoolean(bool_res);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "An error occured. Please check the entered values.", Toast.LENGTH_LONG).show();

                }

                return null;
            }

            @Override
            public void onPostExecute(Boolean value){

                if(value) updateDeviceValues();

                finish();
                startActivity(getIntent());

            }
        }.execute();
    }
}

