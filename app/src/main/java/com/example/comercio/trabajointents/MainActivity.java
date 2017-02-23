package com.example.comercio.trabajointents;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final Integer MY_PERMISSIONS_REQUEST_CALL_PHONE =0;

    private EditText et_numero;
    private Button b_telefono;
    private String telefono;
    private TextView tvresultado;
    private TextView tvpermiso;
    private int permissionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_numero = (EditText)findViewById(R.id.et_celular);
        b_telefono = (Button)findViewById(R.id.b_telefono);
        tvresultado = (TextView)findViewById(R.id.tv_resultado);
        tvpermiso = (TextView)findViewById(R.id.tv_permiso);
        telefono = et_numero.getText().toString();

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        tvpermiso.setText(String.valueOf(permissionCheck));
        tvresultado.setText(et_numero.getText().toString());

        b_telefono.setOnClickListener(new View.OnClickListener() {
            @Override    public void onClick(View v) {
                validarPermisos();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    hacerLlamada();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    tvresultado.setText(String.valueOf("Fallo"));
                }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void validarPermisos(){
        //no hay permiso
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    private void hacerLlamada(){
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+ telefono));
//        tvresultado.setText(String.valueOf(i.getData()));
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            this.startActivity(i);
        }
        else
        {
            validarPermisos();
        }

    }
}
