package com.me.desaprajurit.wisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
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

import java.util.ArrayList;

public class WisataActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private String URL = StringParameter.URL_BASE+"api-wisata.php";
    String sId, sNamaWisata, sKeterangan, sFoto, sSejarah, sUserInput, sVideo, sAlamat, sLongitude, sLatitude;
    private ArrayList<WisataModel> modelArray;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchView searchView;
    private WisataAdapter adapterWisata;


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
        setContentView(R.layout.activity_wisata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        modelArray = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycleViewWisata);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapterWisata = new WisataAdapter(getApplicationContext(), modelArray);

        getWisata();

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }



    private void getWisata(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);

                for (int i = 0; i < response.length(); i++) {
                    WisataModel b = new WisataModel();
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

                        String photo= "http://tutechdev.com/FIKAR/" +"images/"+ sFoto;
                        b.setsFoto(photo);
                        b.setsAlamat(sAlamat);
                        b.setsId(sId);
                        b.setsNamaWisata(sNamaWisata);
                        b.setsKeterangan(sKeterangan);
                        b.setsSejarah(sSejarah);
                        b.setsLatitude(sLatitude);
                        b.setsLongitude(sLongitude);
                        b.setsUser(sUserInput);
                        b.setsVideo(sVideo);

                        System.out.println("hasil dari foto ini" + sFoto);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    modelArray.add(b);
                }
                mRecyclerView.setAdapter(adapterWisata);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        adapterWisata.getFilter().filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapterWisata.getFilter().filter(s);
        System.out.println("hum >" + s);
        return false;
    }
}
