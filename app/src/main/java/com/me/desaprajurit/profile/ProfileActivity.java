package com.me.desaprajurit.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.desaprajurit.R;

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

        RelativeLayout back = findViewById(R.id.relBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
