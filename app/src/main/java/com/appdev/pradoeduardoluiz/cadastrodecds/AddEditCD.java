package com.appdev.pradoeduardoluiz.cadastrodecds;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditCD extends AppCompatActivity {

    static final int REQUEST_PICTURE_CAPTURE = 1;
    private String pictureFilePath;

    private EditText txtAlbum;
    private EditText txtArtista;
    private EditText txtAno;
    private ImageView imageViewCd;
    private TextView txtImagePath;
    Cd cd = null;
    private int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_cd);

        txtAlbum = findViewById(R.id.txtAlbum);
        txtArtista = findViewById(R.id.txtArtista);
        txtAno = findViewById(R.id.txtAno);
        imageViewCd = findViewById(R.id.imageViewCD);
        txtImagePath = findViewById(R.id.imagePath);

        position = getIntent().getIntExtra("position", -1);

        if(position == -1) {
            cd = new Cd();
        }else{
            cd = DataStore.sharedInstance().getCd(position);
            txtAlbum.setText(cd.getNome());
            txtArtista.setText(cd.getArtista());
            txtAno.setText(String.valueOf(cd.getAno()));
            txtImagePath.setText(cd.getImagePath());
            File imgFile = new File(cd.getImagePath());
            if (imgFile.exists()){
                imageViewCd.setImageURI(Uri.fromFile(imgFile));
            }
        }

        BottomNavigationView navigation = findViewById(R.id.bottom_nav_view_add_edit_cd);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_salvar_cd:
                    saveCd();
                    finish();
                    return true;
                case R.id.nav_cancelar_cd:
                    finish();
                    return true;
            }
            return false;
        }
    };


    public void saveCd(){

        cd = new Cd(txtAlbum.getText().toString(), txtArtista.getText().toString(),
                Integer.parseInt(txtAno.getText().toString()),txtImagePath.getText().toString());

        if(position == -1){
            DataStore.sharedInstance().AddCd(cd);
        }else{
            DataStore.sharedInstance().editCd(cd,position);
        }

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(new Intent("updateData"));

        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnCapturarImagemOnClick(View view){

        if(temPermissao()){
            tirarFoto();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean temPermissao() {

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},100);
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tirarFoto();
            }else{
                Toast.makeText(this, "Sem permissão para usar a camera!",Toast.LENGTH_LONG).show();
            }
        }
    }


    private void tirarFoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null){

            File pictureFile = null;
            try {

                pictureFile = getPictureFile();

            } catch (IOException e){
                Toast.makeText(this, "Não foi possível criar arquivo de imagem!",Toast.LENGTH_LONG).show();
                return;
            }

            if(pictureFile != null){
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.appdev.pradoeduardoluiz.cadastrodecds.fileprovider",pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK){
            File imgFile = new File(pictureFilePath);
            if(imgFile.exists()){
                imageViewCd.setImageURI(Uri.fromFile(imgFile));
                txtImagePath.setText(pictureFilePath);
            }
        }
    }

    private File getPictureFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "cd_" + timeStamp;
        File storeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile, ".jpg", storeDir);
        pictureFilePath = image.getAbsolutePath();

        return image;
    }
}