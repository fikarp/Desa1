package com.me.desaprajurit.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionKu {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    public static final String my_shared_preferences = "my_shared_preferences";
    public final static String TAG_ID = "id";
    public final static String TAG_NOMOR_PONSEL = "no_hp";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_NAMA = "nama";
    public final static String TAG_LEVEL = "level";
    public final static String TAG_ALAMAT = "alamat";
    public final static String TAG_PASSWORD = "password";

    public final static String TAG_FOTO = "foto";
    public static final String session_status = "session_status";




    private static final String IS_LOGIN = "IsLoggedIn";

    public SessionKu(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(my_shared_preferences, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession( String id,String nomorPonsel, String nama, String username, String level, String alamat, String foto){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // id string
        editor.putString(TAG_ID, id);
        // Storing phone number in pref
        editor.putString(TAG_NOMOR_PONSEL, nomorPonsel);
        // Storing name in pref
        editor.putString(TAG_NAMA, nama);
        editor.putString(TAG_USERNAME,username);
        editor.putString(TAG_LEVEL, level);
        editor.putString(TAG_ALAMAT, alamat);
        editor.putString(TAG_FOTO, foto);

        // commit changes
        editor.commit();
    }

}
