package com.me.desaprajurit.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.me.desaprajurit.R;
import com.me.desaprajurit.berita.BeritaActivity;
import com.me.desaprajurit.login.LoginActivity;
import com.me.desaprajurit.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
