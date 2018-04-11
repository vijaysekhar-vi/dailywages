package com.hogslab.dailywages.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hogslab.dailywages.Pager;
import com.hogslab.dailywages.R;
import com.hogslab.dailywages.fragments.ViewEmployeeFragment;
import com.hogslab.dailywages.providers.DBAdapter;

public class EmployeeProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;

    private ViewPager viewPager;

    private int openingTab = 0;

    public static Activity employeeProfileActivity;
    int id;
    String employeeName;

    Bundle bundle;

    Fragment fragmentPayments = null;

    private static final String TAG = "Daily Wages";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Employee Profile");

        bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        fetchEmployeeDetails(id);

        Log.e("Weekly wages", "Employee id is ------------> " + id);

        openingTab = getIntent().getIntExtra("opening_tab", 0);

        employeeProfileActivity = this;



        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        Pager adapter = new Pager(getSupportFragmentManager());

        Fragment viewEmployeeFragment = new ViewEmployeeFragment();
        viewEmployeeFragment.setArguments(bundle);

        Fragment fragmentAttendance = new ViewEmployeeFragment();
        fragmentAttendance.setArguments(bundle);

        fragmentPayments = new ViewEmployeeFragment();
        fragmentPayments.setArguments(bundle);

        adapter.addFrag(fragmentAttendance, "Attendances");
        adapter.addFrag(fragmentPayments, "Payments");
        adapter.addFrag(viewEmployeeFragment, "Profile");

        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_profile_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.idEmployeeList:
                finish();
                break;
            case R.id.idContactUs:
                Intent i = new Intent(this, ContactUsActivity.class);
                startActivity(i);
                break;
            case R.id.idExit:
                moveTaskToBack(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


        @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void fetchEmployeeDetails(int id) {
        DBAdapter db;
        db = new DBAdapter(EmployeeProfileActivity.this);
        try {
            db.open();
        } catch (Exception e) {
            Log.d(TAG, "Unable to open the database");
            return;
        }

        Cursor c = db.getEmployeeDetailed(id);
        if (c.getCount() <= 0) {
            Log.d(TAG, "Error fetching employee details");
            return;
        }
        c.moveToFirst();
        employeeName = c.getString(c.getColumnIndex("employee_name"));
        db.close();
        return;
    }

/*
    @Override
    public void sendCalenderStart(long calenderStart) {
        ((SaveCalenderStart)fragmentPayments).receiveCalenderStart(calenderStart);
    }
*/

/*
    public void makePayment(View view) {
        final Dialog dialog = new Dialog(EmployeeProfileActivity.this);
        dialog.setContentView(R.layout.payment_layout);
        dialog.setCancelable(true);

        ((TextView)dialog.findViewById(R.id.id_title)).setText("Pay " + employeeName);
        ((Button)dialog.findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pay = ((EditText)dialog.findViewById(R.id.idEditPay)).getText().toString();
                if (pay.isEmpty() || pay == null) {
                    ((EditText)dialog.findViewById(R.id.idEditPay)).setError("Amount cannot be empty !!!");
                    return;
                }
                if (Integer.parseInt(pay) == 0) {
                    ((EditText)dialog.findViewById(R.id.idEditPay)).setError("Amount cannot be 0 (Zero) !!!");
                    return;
                }
                String notes = ((EditText)dialog.findViewById(R.id.idEditNotes)).getText().toString();

                DBAdapter db;
                db = new DBAdapter(EmployeeProfileActivity.this);
                try {
                    db.open();
                } catch (Exception e) {
                    Log.d(TAG, "Unable to open the database");
                    return;
                }

                PaymentsPojo paymentsPojo = new PaymentsPojo(id, System.currentTimeMillis(), Integer.parseInt(pay), notes);
                long ret = db.insertPayments(paymentsPojo);
                if (ret < 0) {
                    Log.e(TAG, "Error inserting rows into the payments table!!!!");
                }
                db.close();

                dialog.dismiss();
            }
        });


        dialog.show();

    }
*/

    public interface SaveCalenderStart
    {
        public void receiveCalenderStart(long calenderStart);
    }
}
