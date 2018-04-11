package com.hogslab.dailywages.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hogslab.dailywages.R;
import com.hogslab.dailywages.adapters.EmployeeAdapter;
import com.hogslab.dailywages.pojo.EmployeeBriefPojo;
import com.hogslab.dailywages.providers.DBAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBAdapter db;
    List<EmployeeBriefPojo> employeeList = null;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;
    public static String employeeMobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText search;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.patient_list_label));

        search = (EditText) findViewById(R.id.idSearch);
        search.setSelected(false);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    void filter(String text) {
        List<EmployeeBriefPojo> employeeBriefPojos = new ArrayList<>();
        employeeBriefPojos.clear();

        Log.e("OPM", " entering filter .....");

        if (employeeList == null) {
            return;
        }
        for (EmployeeBriefPojo e : employeeList) {
            Log.e("OPM", "patient name is " + e.getEmployeeName());
            if ((e.getEmployeeName().toLowerCase().contains(text.toLowerCase())) || (e.getEmployeeRole().toLowerCase().contains(text.toLowerCase()))) {
                employeeBriefPojos.add(e);
            }
        }
        System.out.println("Total Employee Count is " + employeeBriefPojos.size());

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        employeeAdapter = new EmployeeAdapter(getApplicationContext(), employeeBriefPojos, employeeBriefPojos.size(), this);
        recyclerView.setAdapter(employeeAdapter);
        employeeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("Daily wages", "onResume getting called....");
        db = new DBAdapter(this);
        try {
            db.open();
        } catch (Exception e) {
            Log.d("OSS", "Unable to open the database");
            return;
        }

        Cursor c = db.getAllEmployeeBrief();
        employeeList = new ArrayList<>();

        if (c.getCount() > 0) {
            findViewById(R.id.id_patientlist_empty).setVisibility(View.GONE);
            findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);

            c.moveToFirst();
            do {

                employeeList.add(new EmployeeBriefPojo(c.getString(c.getColumnIndex("employee_name")), c.getString(c.getColumnIndex("employee_mobile")), c.getString(c.getColumnIndex("employee_role")), c.getInt(c.getColumnIndex("_id"))));

            } while (c.moveToNext());
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            employeeAdapter = new EmployeeAdapter(getApplicationContext(), employeeList, employeeList.size(), this);
            recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(employeeAdapter);
        } else {
            findViewById(R.id.id_patientlist_empty).setVisibility(View.VISIBLE);
            findViewById(R.id.recycler_view).setVisibility(View.GONE);
        }

        c.close();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.idEmployeeList:
                break;
            case R.id.idContactUs:
                startActivity(new Intent(this, ContactUsActivity.class));
                break;

            case R.id.idExit:
                moveTaskToBack(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEmployee(View v) {
        startActivity(new Intent(this, AddEmployee.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        final int REQUEST_CODE_CALL_PERMISSION = 1;

        switch (requestCode) {
            case REQUEST_CODE_CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + employeeMobileNumber));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Permission required")
                            .setIcon(R.drawable.call_button)
                            .setMessage("Please ALLOW Daily Wages to make phone calls")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
                break;
        }
    }

}
