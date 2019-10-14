package com.me.desaprajurit.wisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailWisataActivity extends AppCompatActivity {
    private String URL = StringParameter.URL_BASE+"api-wisata.php?id=";
    String sId, sNamaWisata, sKeterangan, sFoto, sSejarah, sUserInput, sVideo, sAlamat, sLongitude, sLatitude;
    private String id;
    private TextView textDetail;
    private CardView sejarah, video, map;
    private ImageView imgBanner;

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
        setContentView(R.layout.activity_detail_wisata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textDetail = findViewById(R.id.isiDetail);
        id = getIntent().getStringExtra("id");
        System.out.println("apakah dapat id >>>" + id);
        getWisataDetail();

        sejarah = findViewById(R.id.sejarahKampung);
        sejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String atSejarang = "sejarah";
                Intent i = new Intent(getBaseContext(), SejarahActivity.class);
                i.putExtra(atSejarang, sSejarah);
                startActivity(i);
            }
        });

        video = findViewById(R.id.video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sVideoKu = "video";
                Intent i = new Intent(getBaseContext(), VideoWisataActivity.class);
                i.putExtra(sVideoKu, sVideo);
                startActivity(i);
            }
        });

        map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude ="latitude";
                String longitude = "longitude";
                String alamatku = "alamat";
                Intent i = new Intent(getBaseContext(), MapsWisataActivity.class);
                i.putExtra(latitude, sLatitude);
                i.putExtra(longitude, sLongitude);
                i.putExtra(alamatku, sAlamat);
                startActivity(i);
            }
        });

        imgBanner = findViewById(R.id.imgGambar);

    }


    private void getWisataDetail(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("responya detail" + response);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        sId = obj.getString("id");
                        sNamaWisata = obj.getString("nama_wisata");
                        sKeterangan = obj.getString("keterangan");
                        sFoto = obj.getString("foto");
                        sSejarah = obj.getString("sejarah");
                        sVideo = obj.getString("video");
                        sAlamat = obj.getString("alamat");
                        sLatitude = obj.getString("latitude");
                        sLongitude = obj.getString("longitude");
                        sUserInput = obj.getString("user_input");

                        textDetail.setText(sKeterangan);
                        String urlKu = "http://tutechdev.com/FIKAR/images/" + sFoto;
                        new DownloadImageTask((ImageView) findViewById(R.id.imgGambar))
                                .execute(urlKu);

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
