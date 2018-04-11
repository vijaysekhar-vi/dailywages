package com.hogslab.dailywages.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hogslab.dailywages.R;

public class ContactUsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CALL_PERMISSION = 1;
    private String[] callPermArray = new String[]{Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Contact us");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_only_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.idExit:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void call(View v) {
        if (!isPermissionGranted(Manifest.permission.CALL_PHONE)) {
            requestPermission(callPermArray, REQUEST_CODE_CALL_PERMISSION);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "+91-8754903797"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            }
        }
        return;
    }

    public void mail(View v) {
        Intent i = new Intent();
        i.setType("text/plain");
        i.setData(Uri.parse("mailto:hogslab@gmail.com"));
        try {
            startActivity(Intent.createChooser(i, "Write to us"));
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, "hogslab@gmail.com");
            startActivity(intent);
        }
        return;
    }

    public void browse(View v) {
        String url = "http://www.hogslab.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        return;
    }

    private boolean isPermissionGranted(String permission) {
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(String[] permissionArray, int requestCode) {
        ActivityCompat.requestPermissions(this, permissionArray, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "+91-8754903797"));
                    startActivity(callIntent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Permission required")
                            .setIcon(R.drawable.call_button)
                            .setMessage("Please ALLOW Daily Wages App to make phone calls")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();

                    Toast.makeText(this, "Permission Denied, app wont be able to make the call.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
