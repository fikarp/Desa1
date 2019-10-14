package com.me.desaprajurit.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.me.desaprajurit.R;
import com.me.desaprajurit.home.HomeActivity;
import com.me.desaprajurit.login.LoginActivity;
import com.me.desaprajurit.parameter.StringParameter;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;

public class RegisterActivity extends AppCompatActivity {

    File file = null;

    private String mCurrentPhotoPath;
    private final int CAMERA_CAPTURE = 1;
    Uri tempUri;
    String currentDateandTime;
    private static final int PERMS_REQUEST_CODE = 123;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int SELECT_IMAGE = 5;
    EditText edtUsername, edtPassword, edtNama, edtAlamat, edtNoHp;
    String sFoto, sUsername, sPassword, sNama, sAlamat, sNoHp;
    private String URL = StringParameter.URL_BASE+"api-register.php";
    private String encodedImageProfile;


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
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        edtUsername = findViewById(R.id.edtUsername);
        edtNama = findViewById(R.id.edtNama);
        edtAlamat = findViewById(R.id.edtAlamatUser);
        edtPassword = findViewById(R.id.edtPassword);
        edtNoHp = findViewById(R.id.edtNoHp);


        Button buttonDaftar = findViewById(R.id.btnDaftar);
        buttonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUsername = edtUsername.getText().toString();
                sPassword = edtPassword.getText().toString();
                sNama = edtNama.getText().toString();
                sNoHp = edtNoHp.getText().toString();
                sAlamat = edtAlamat.getText().toString();

                if(sUsername.length()>0 && sPassword.length()>0 && sNama.length()>0 && sNoHp.length()>0 && sAlamat.length()>0){

                    if (encodedImageProfile != null ){
                        register();
                    } else {
                        Toast.makeText(getBaseContext(), "foto wajib di masukkan",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Semua kolom dan foto wajib diisi",Toast.LENGTH_LONG).show();
                }

            }
        });

        TextView pilihFoto = findViewById(R.id.textFile);
        pilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent,SELECT_IMAGE);
            }
        });

        ImageView foto = findViewById(R.id.imgFoto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissions()) {
                    try {
                        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (captureIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File
                                System.out.println("CAN NOT CREATE IMAGE FILE");
                            }
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(RegisterActivity.this,
                                        "com.me.desaprajurit.fileprovider",
                                        photoFile);
                                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(captureIntent, REQUEST_TAKE_PHOTO);
                            }
                        }
                        //startActivityForResult(captureIntent, CAMERA_CAPTURE);
                    } catch (ActivityNotFoundException anfe) {
                        Toast.makeText(RegisterActivity.this, "Tidak ada camera ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    requestPerms();
                }
            }
        });

    }

    private void register(){
        final KProgressHUD progressDialog=  KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Mohon Tunggu...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hasilnya" + response);

                        progressDialog.dismiss();
                        try {
                            //Parsing Json
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                 sNama = obj.getString("nama");
                                 sUsername = obj.getString("username");
                                 sPassword = obj.getString("password");
                                 sAlamat = obj.getString("alamat");
                                 sNoHp = obj.getString("no_hp");
                                 sFoto = obj.getString("foto");

                                 Intent in = new Intent(getBaseContext(), LoginActivity.class);
                                 startActivity(in);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", sUsername);
                params.put("password", sPassword);
                params.put("nama", sNama);
                params.put("alamat", sAlamat);
                params.put("no_hp", sNoHp);
                params.put("foto", encodedImageProfile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @SuppressLint("WrongConstant")
    private boolean hasPermissions (){
        int res = 0;

        String[] permissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for  (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms () {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMS_REQUEST_CODE:
                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;

        }
        if (allowed){
            try {

            } catch (ActivityNotFoundException anfe) {
                Toast toast = Toast.makeText(getBaseContext(), getResources().getString(R.string.devicenotsupportcropaction), Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                    Toast.makeText(RegisterActivity.this, "Camera permission tidak diizinkan", Toast.LENGTH_SHORT).show();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(RegisterActivity.this, "Akses storage permission tidak diizinkan", Toast.LENGTH_SHORT).show();
                }
                Intent skipInten = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(skipInten);
                finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE) {
            if (resultCode == RESULT_OK) {
                System.out.println("IMAGE PATH ::: " + mCurrentPhotoPath);
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                if (mCurrentPhotoPath == null) {

                }
                Uri tempUri = Uri.fromFile(new File(mCurrentPhotoPath));
                System.out.println("tempUri untuk crop :::: " + tempUri);
                CropImage.activity(tempUri).start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {
                    try {
                        ImageView profile = findViewById(R.id.imgFoto);
                        final Uri imageUri = result.getUri();
                        profile.setImageURI(imageUri);
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageToString(selectedImage);
                        encodedImageProfile = encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    System.out.println("ERROR CROP IMAGE ::::: " + error.getMessage());
                }

            }
        }

        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        ImageView profile = findViewById(R.id.imgFoto);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        profile.setImageBitmap(bitmap);
                        imageToString(bitmap);
                        encodedImageProfile = encodeImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED)  {
                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
}
