package com.me.desaprajurit.berita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.me.desaprajurit.R;
import com.me.desaprajurit.parameter.StringParameter;
import com.me.desaprajurit.profile.ProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailsBeritaActivity extends AppCompatActivity {
    private String URL = StringParameter.URL_BASE+"api-berita.php?id=";
    String sId, sBerita, sJudul, sFoto, sStatus, sUserInput;
    private TextView textJudul, textBerita;
    private ImageView imgFoto;
    String idKu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_berita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textJudul = findViewById(R.id.textJudul);
        textBerita = findViewById(R.id.textBerita);
        imgFoto = findViewById(R.id.imgDetails);
        idKu = getIntent().getStringExtra("id");
        System.out.println("idKU" + idKu);



        getDetailBerita();
    }

    private void getDetailBerita(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL + idKu, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("responya detail" + response);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        sId = obj.getString("id");
                        sBerita = obj.getString("berita");
                        sJudul = obj.getString("judul");
                        sFoto = obj.getString("foto");
                        sStatus = obj.getString("status_aktif");
                        sUserInput = obj.getString("user_input");

                        textJudul.setText(sJudul);
                        textBerita.setText(sBerita);
                        String URLKU = "http://tutechdev.com/FIKAR/" + sFoto;
                        new DownloadImageTask((ImageView) findViewById(R.id.imgDetails))
                                .execute(URLKU);
                        System.out.println("hasil dari foto ini" + sFoto);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        requestQueue.add(jsonArrayRequest);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
