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

public class LoginActivity extends AppCompatActivity {
EditText ETmobile_number;
Button Bnext;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
ETmobile_number=findViewById(R.id.mobilenumber_edit);
Bnext=findViewById(R.id.button_verify);
        session = new SessionManager(getApplicationContext());

Bnext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      String mobile_num= ETmobile_number.getText().toString();
      if (mobile_num.isEmpty())
      {
          Toast.makeText(LoginActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
      }
      else
      {
          mobileverify(mobile_num);
      }
    }
});

    }
public void mobileverify(final String mobile_num)
{
    RequestQueue mRequestQueue = Volley.newRequestQueue(this);

    //String Request initialized
    StringRequest mStringRequest = new StringRequest(Request.Method.POST, GlobalUrl.Login_activity, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jObj = new JSONObject(response);
                Boolean Bexits = jObj.getBoolean("exits");

                if (Bexits) {
                    String Smob_num = jObj.getString("mob_num");
                    String Sotp = jObj.getString("otp_num");
                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    intent.putExtra("mob_num", Smob_num);
                    intent.putExtra("otp_num", Sotp);
                    startActivity(intent);
                    finish();
                } else {
                    String Smessage = jObj.getString("message");
                    session.setLogin(true);
                    Toast.makeText(LoginActivity.this, Smessage, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
            params.put("mob_num", mobile_num);

            return params;
        }
    };

    mRequestQueue.add(mStringRequest);
}
}

