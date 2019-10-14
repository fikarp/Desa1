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
import com.me.desaprajurit.session.SessionKu;
import com.me.desaprajurit.wisata.WisataActivity;

import static com.me.desaprajurit.session.SessionKu.TAG_ID;
import static com.me.desaprajurit.session.SessionKu.TAG_USERNAME;
import static com.me.desaprajurit.session.SessionKu.my_shared_preferences;
import static com.me.desaprajurit.session.SessionKu.session_status;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    Boolean session = false;
    SessionKu prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new SessionKu(getApplicationContext());
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        String id = sharedpreferences.getString(TAG_ID, null);
        String username = sharedpreferences.getString(TAG_USERNAME, null);

        if (session) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }

        TextView login = findViewById(R.id.textLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
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

        CardView kPrajurit = findViewById(R.id.kampungPrajurit);
        kPrajurit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), WisataActivity.class);
                startActivity(i);
            }
        });

    }
}
