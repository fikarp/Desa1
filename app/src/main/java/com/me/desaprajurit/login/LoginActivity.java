package com.me.desaprajurit.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.kaopiz.kprogresshud.KProgressHUD;
import com.me.desaprajurit.R;
import com.me.desaprajurit.home.HomeActivity;
import com.me.desaprajurit.home.MainActivity;
import com.me.desaprajurit.parameter.StringParameter;
import com.me.desaprajurit.register.RegisterActivity;
import com.me.desaprajurit.session.SessionKu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.me.desaprajurit.session.SessionKu.TAG_ALAMAT;
import static com.me.desaprajurit.session.SessionKu.TAG_FOTO;
import static com.me.desaprajurit.session.SessionKu.TAG_ID;
import static com.me.desaprajurit.session.SessionKu.TAG_NAMA;
import static com.me.desaprajurit.session.SessionKu.TAG_NOMOR_PONSEL;
import static com.me.desaprajurit.session.SessionKu.TAG_USERNAME;
import static com.me.desaprajurit.session.SessionKu.my_shared_preferences;
import static com.me.desaprajurit.session.SessionKu.session_status;

public class LoginActivity extends AppCompatActivity {

    private String sUsername, sPassword, sNama, sId, sLevel, sAlamat, sNoHp, sFoto;
    EditText edtUsername, edtPassword;
    private String URL = StringParameter.URL_BASE+"api-login.php";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    SessionKu prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefManager = new SessionKu(getApplicationContext());


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

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        String id = sharedpreferences.getString(TAG_ID, null);
        String username = sharedpreferences.getString(TAG_USERNAME, null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
    }


    private void login(){
        final KProgressHUD progressDialog=  KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Mohon Tunggu...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hasilnya" + response);
                        progressDialog.dismiss();

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
                                sId = obj.getString("id");
                                String user = obj.getString("user");
                                JSONObject object = new JSONObject(user);
                                sNama = object.getString("nama");
                                sUsername = object.getString("username");
                                //sLevel = object.getString("level");
                                sAlamat = object.getString("alamat");
                                sNoHp = object.getString("no_hp");
                                sFoto = object.getString("foto");

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(session_status, true);
                                editor.putString(TAG_ID, sId);
                                editor.putString(TAG_NOMOR_PONSEL, sNoHp);
                                editor.putString(TAG_ALAMAT, sAlamat);
                                editor.putString(TAG_USERNAME, sUsername);
                                editor.putString(TAG_FOTO, sFoto);
                                editor.putString(TAG_NAMA, sNama);
                                prefManager.createLoginSession(sId,sNama,sUsername, sLevel, sAlamat, sNoHp, sFoto);
                                editor.commit();
                                Intent i = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(i);
                                finish();
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
                        progressDialog.dismiss();
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
