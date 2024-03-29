package com.me.desaprajurit.berita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.me.desaprajurit.R;
import com.me.desaprajurit.parameter.StringParameter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class BeritaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<BeritaModel> modelArray;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipe;

    private String URL = StringParameter.URL_BASE+"api-berita.php";
    String sId, sBerita, sJudul, sFoto, sStatus, sUserInput;
    public static String URL_FOTO;

    private String[] fotoArray;



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
        setContentView(R.layout.activity_berita);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        modelArray = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        getBerita();



    }


    private void getBerita(){
        modelArray.clear();
        swipe.setRefreshing(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);

                        for (int i = 0; i < response.length(); i++) {
                            BeritaModel b = new BeritaModel();
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                sId = obj.getString("id");
                                sBerita = obj.getString("berita");
                                sJudul = obj.getString("judul");
                                sFoto = obj.getString("foto");
                                sStatus = obj.getString("status_aktif");
                                sUserInput = obj.getString("user_input");
                                b.setsJudul(sJudul);
                                b.setsFoto(URL_FOTO);
                                b.setsId(sId);

                                URL_FOTO= "http://tutechdev.com/FIKAR/" + sFoto;

                                System.out.println("url fotonya" + URL_FOTO);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            modelArray.add(b);
                        }
                        System.out.println("hasil dari foto ini" + sFoto);
                        swipe.setRefreshing(false);
                        mAdapter = new BeritaAdapter(getApplicationContext(), modelArray);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        swipe.setRefreshing(false);

                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onRefresh() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getBerita();
                swipe.setRefreshing(false);
            }
        }, 1500);

    }
}
