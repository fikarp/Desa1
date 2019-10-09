package com.me.desaprajurit.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.me.desaprajurit.R;
import com.me.desaprajurit.berita.BeritaActivity;
import com.me.desaprajurit.login.LoginActivity;
import com.me.desaprajurit.profile.ProfileActivity;
import com.me.desaprajurit.wisata.WisataActivity;

import static com.me.desaprajurit.session.SessionKu.TAG_ID;
import static com.me.desaprajurit.session.SessionKu.TAG_USERNAME;
import static com.me.desaprajurit.session.SessionKu.my_shared_preferences;
import static com.me.desaprajurit.session.SessionKu.session_status;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean valueSession;
    String username, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        valueSession = sharedPreferences.getBoolean(session_status, false);
        username = sharedPreferences.getString(TAG_USERNAME, null);
        id = sharedPreferences.getString(TAG_ID, null);


        CardView profil = findViewById(R.id.cardProfile);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

        TextView logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        CardView berita = findViewById(R.id.berita);
        berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), BeritaActivity.class);
                startActivity(i);
            }
        });

        CardView wisata = findViewById(R.id.kampungPrajurit);
        wisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), WisataActivity.class);
                startActivity(i);
            }
        });


    }
}
