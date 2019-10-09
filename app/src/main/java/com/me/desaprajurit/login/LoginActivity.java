package com.me.desaprajurit.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.me.desaprajurit.R;
import com.me.desaprajurit.home.HomeActivity;
import com.me.desaprajurit.home.MainActivity;
import com.me.desaprajurit.parameter.StringParameter;
import com.me.desaprajurit.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String sUsername, sPassword;
    EditText edtUsername, edtPassword;
    private String URL = StringParameter.URL_BASE+"api-login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);

        Button login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUsername = edtUsername.getText().toString();
                sPassword = edtPassword.getText().toString();

                if(sUsername.length() > 0 && sPassword.length() > 0 ){
                    login();
                } else {
                    Toast.makeText(getBaseContext(), "Username dan Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button daftar = findViewById(R.id.btnDaftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    private void login(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hasilnya" + response);

                        /**
                         * di bawah ini contoh respon JSON yang di terima dari API/server
                         */
//                                "error": false,
//                                "id": "19",
//                                "user": {
//                                      "nama": "dunia",
//                                      "username": "test1",
//                                      "alamat": "ada deh",
//                                      "no_hp": "085678890078",
//                                      "foto": "foto-profile/dunia.jpeg"
                        try {
                            //Parsing Json
                            JSONObject obj = new JSONObject(response);
                            String error = obj.getString("error");
                            String errorFalse = "false";
                            String errorTrue = "true";
                            if(error == errorFalse) {
                                String id = obj.getString("id");
                                String user = obj.getString("user");
                                JSONObject object = new JSONObject(user);
                                String nama = object.getString("nama");
                                System.out.println("namanya" + nama);

                                Intent i = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(i);
                            } else if (error == errorTrue){
                                String message = obj.getString("error_msg");
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", sUsername);
                params.put("password", sPassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
