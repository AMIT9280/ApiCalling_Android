package com.apicalling;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText edtFn,edtLn,edtEmail,edtPassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtFn = findViewById(R.id.edt_fn);
        edtLn = findViewById(R.id.edt_ln);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_add);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fn = edtFn.getText().toString();
                String ln = edtLn.getText().toString();
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();

                loadApi(fn,ln,email,pass);

            }
        });

    }

    private void loadApi(String fn, String ln, String email, String pass) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(""))
                {
                    Log.e("TAG", "onResponse: "+response );
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String fn = jsonObject.getString("first_name");
                        String ln = jsonObject.getString("last_name");

                        Intent i = new Intent(SignupActivity.this,MainActivity.class);
                        i.putExtra("KEY_NAME",fn+" "+ln);
                        startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("first_name",fn);
                map.put("last_name",ln);
                map.put("email",email);
                map.put("password",pass);

                return map;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        edtFn.setText("");
        edtLn.setText("");
        edtEmail.setText("");
        edtPassword.setText("");

    }
}