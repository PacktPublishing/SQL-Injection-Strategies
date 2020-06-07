package com.example.iot_masteringsqlinjection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView username_txt;
    TextView password_txt;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = findViewById(R.id.button);
        username_txt = findViewById(R.id.username_text);
        password_txt = findViewById(R.id.password_text);

        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    doLogin(username_txt.getText().toString(), password_txt.getText().toString());
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "An error occured. Please check the entered values.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void doLogin(final String username, final String password){

        new  AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... strings) {

                //Initialize soap request + add parameters
                SoapObject request = new SoapObject("http://service.masteringsqlj.packt.com", "getUser");
                //Use this to add parameters
                request.addProperty("username", username);
                request.addProperty("password", password);
                //Declare the version of the SOAP request
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try {
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(Utils.ENDPOINT);
                    //this is the actual part that will call the webservice
                    androidHttpTransport.call("http://service.masteringsqlj.packt.com/getUser", envelope);
                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    if (result != null) {
                        //Get the first property and change the label text
                        Log.d("OUT:", result.getProperty(0).toString());

                        SoapObject root =  ((SoapObject) result.getProperty(0));//.getProperty("getUserReturn"));

                        String user =  root.getProperty("username").toString();
                        String pass =  root.getProperty("password").toString();
                        String user_id = root.getProperty("user_id").toString();

                        return user + "-" + pass + "-" + user_id;

                    } else {
                        Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void onPostExecute(String string){

                if(string != null) {
                    String[] splitted_string = string.split("-");
                    String username = splitted_string[0];
                    String password = splitted_string[1];
                    String user_id = splitted_string[2];

                    Intent intent = new Intent(MainActivity.this, UserAndAssetDetail.class);
                    Bundle b = new Bundle();

                    b.putString("username", username);
                    b.putString("password", password);
                    b.putString("user_id", user_id);

                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "An error occured. Please check the entered values.", Toast.LENGTH_LONG).show();

            }
        }.execute();
    }
}
