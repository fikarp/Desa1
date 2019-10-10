package com.me.desaprajurit.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.desaprajurit.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.me.desaprajurit.session.SessionKu.TAG_ALAMAT;
import static com.me.desaprajurit.session.SessionKu.TAG_FOTO;
import static com.me.desaprajurit.session.SessionKu.TAG_ID;
import static com.me.desaprajurit.session.SessionKu.TAG_NAMA;
import static com.me.desaprajurit.session.SessionKu.TAG_NOMOR_PONSEL;
import static com.me.desaprajurit.session.SessionKu.TAG_USERNAME;
import static com.me.desaprajurit.session.SessionKu.my_shared_preferences;
import static com.me.desaprajurit.session.SessionKu.session_status;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean valueSession;
    String username, id, nama, alamat, noHp, foto;
    TextView textNamaLengakap, textNama, textUsername, textAlamat, textNoHp;
    ImageView fotoUser;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        valueSession = sharedPreferences.getBoolean(session_status, false);
        username = sharedPreferences.getString(TAG_USERNAME, null);
        id = sharedPreferences.getString(TAG_ID, null);
        nama = sharedPreferences.getString(TAG_NAMA, null);
        alamat = sharedPreferences.getString(TAG_ALAMAT, null);
        noHp = sharedPreferences.getString(TAG_NOMOR_PONSEL, null);
        foto = sharedPreferences.getString(TAG_FOTO, null);

        textNamaLengakap = findViewById(R.id.namaLengkap);
        textNamaLengakap.setText(nama);
        textNama = findViewById(R.id.textNamaLengkap1);
        textNama.setText(nama);
        textUsername = findViewById(R.id.textUsername);
        textUsername.setText(username);
        textAlamat = findViewById(R.id.textAlamat);
        textAlamat.setText(alamat);
        textNoHp = findViewById(R.id.textNomorHp);
        textNoHp.setText(noHp);
        fotoUser = findViewById(R.id.imgProfile);
        URL = "http://tutechdev.com/FIKAR/" + foto;
        new DownloadImageTask((ImageView) findViewById(R.id.imgProfile))
                .execute(URL);
        RelativeLayout back = findViewById(R.id.relBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
