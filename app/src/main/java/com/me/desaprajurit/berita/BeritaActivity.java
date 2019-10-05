package com.me.desaprajurit.berita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.me.desaprajurit.R;
import com.me.desaprajurit.parameter.StringParameter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BeritaActivity extends AppCompatActivity {

    private String URL = StringParameter.URL_BASE+"api/dataa-berita.php";
    String sId, sBerita, sJudul, sFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        getBerita();
    }

    private void getBerita(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++){
                                JSONObject obj = data.getJSONObject(i);
                                sId = obj.getString("id");
                                sBerita = obj.getString("berita");
                                sJudul = obj.getString("judul");
                                sFoto = obj.getString("foto");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }
}
