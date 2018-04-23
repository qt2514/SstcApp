package com.quaticstech.sstcapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {
EditText ETotp;
Button Bsubmit;
String Smobilenum,Sotp,Soldotp;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
ETotp=findViewById(R.id.otp_mobil);
Bsubmit=findViewById(R.id.otp_submit);
        session = new SessionManager(getApplicationContext());

        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null)
        {
            Smobilenum=(String) Bintent.get("mob_num");
            Soldotp=(String) Bintent.get("otp_num");
        }
        Bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sotp=ETotp.getText().toString();
                if (Sotp.equals(Soldotp))
                {
                    loginverify(Smobilenum,Sotp);
                }
                else
                {
                    Toast.makeText(OtpActivity.this, "Otp Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void loginverify(final String smobilenum, final String sotp)
    {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, GlobalUrl.Otp_activity, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    Boolean Bexits = jObj.getBoolean("exits");

                    if (Bexits) {
                        session.setLogin(true);
                        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                        intent.putExtra("mob_num", smobilenum);
                        intent.putExtra("otp_num", sotp);
                        startActivity(intent);
                        finish();

                    } else {
                        String Smessage = jObj.getString("message");
                        Toast.makeText(OtpActivity.this, Smessage, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                        intent.putExtra("mob_num", "123456");
                        startActivity(intent);
                        finish();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ErrorLogin", "Error :" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mob_num", smobilenum);
                params.put("otp_num", sotp);

                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
