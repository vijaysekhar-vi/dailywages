package com.hogslab.dailywages.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hogslab.dailywages.R;
import com.hogslab.dailywages.Utility;
import com.hogslab.dailywages.providers.DBAdapter;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewEmployeeFragment extends Fragment {

    View rootView;
    int id;
    String employeeName;
    String employeeRole;
    int  employeeWage;
    int employeeGender;
    int employeeAge;
    String employeeMobile;
    String employeeMobileAlt;
    String employeeEmail;
    String employeeNotes;
    String employeeAddress;
    String employeeCity;
    long employeeDOJ;
    long ageReferenceDateTime = 0;
    int employeeStatus;
    ImageView imageView;

    private static final int REQUEST_CODE_CALL_PERMISSION = 1;
    private String[] callPermArray = new String[]{Manifest.permission.CALL_PHONE};

    private static final String TAG = "Employee View Fragment";

    public ViewEmployeeFragment() {
        Log.e(TAG, "ViewEmployeeFragment Constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null) {
            id = bundle.getInt("id");
        } else {
            Log.e("Weekly wages", "nullllllllllllllll " );
        }
        Log.e("Weekly wages", "Employee id is -> " + id);

        rootView = inflater.inflate(R.layout.fragment_view_employee, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        TextView tV;
        String pAge;
        Cursor c;
        int rowC;
        DBAdapter db;

        super.onResume();

        Log.d(TAG, "OnResume of ViewEmployeeFragment");
        db = new DBAdapter(getActivity());
        try {
            db.open();
        } catch (Exception e) {
            Log.d(TAG, "Unable to open the database");
            return;
        }
        c = db.getEmployeeDetailed(id);
        rowC = c.getCount();
        if (rowC <= 0) {
            Log.d(TAG, "Error fetching patient details");
            return;
        }
        c.moveToFirst();
        employeeName = c.getString(c.getColumnIndex("employee_name"));
        employeeRole = c.getString(c.getColumnIndex("employee_role"));
        employeeWage = c.getInt(c.getColumnIndex("employee_wage"));
        employeeAge = c.getInt(c.getColumnIndex("employee_age"));
        employeeMobile = c.getString(c.getColumnIndex("employee_mobile"));

        employeeMobileAlt = c.getString(c.getColumnIndex("employee_mobile_alt"));
        employeeAddress = c.getString(c.getColumnIndex("employee_address"));
        employeeCity = c.getString(c.getColumnIndex("employee_city"));
        employeeNotes = c.getString(c.getColumnIndex("employee_notes"));
        ageReferenceDateTime = c.getLong(c.getColumnIndex("employee_age_reference"));

        employeeDOJ = c.getLong(c.getColumnIndex("employee_doj"));
        employeeGender = c.getInt(c.getColumnIndex("employee_gender"));
        employeeStatus = c.getInt(c.getColumnIndex("employee_status"));

        short age = Utility.calculateAge((short) employeeAge, ageReferenceDateTime);

        tV = (TextView) rootView.findViewById(R.id.idViewEmployeeName);
        if (employeeGender == 0) {
            tV.setText(employeeName + " (" + age + "M)");
        } else {
            tV.setText(employeeName + " (" + age + "F)");
        }

        tV = (TextView) rootView.findViewById(R.id.idViewEmployeeRole);
        tV.setText(employeeRole);

        tV = (TextView) rootView.findViewById(R.id.idViewEmployeeWage);
        tV.setText("Rs " + Integer.toString(employeeWage) + " (day)");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        tV = (TextView) rootView.findViewById(R.id.idViewEmployeeDOJ);
        tV.setText(dateFormat.format(employeeDOJ));

        tV = (TextView) rootView.findViewById(R.id.idViewEmployeeStatus);
        tV.setText(employeeStatus == 1 ? "Active": "Inactive");

        tV = (TextView) rootView.findViewById(R.id.idViewEmployeeMobile1);
        tV.setText(employeeMobile);
        imageView = rootView.findViewById(R.id.idMobile1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialEmployee(employeeMobile);
            }
        });
        imageView = rootView.findViewById(R.id.idSmsEmployee1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsEmployee(employeeMobile);
            }
        });

        LinearLayout linearLayout = rootView.findViewById(R.id.ll_mobile_2);
        if ((employeeMobileAlt != null) && (!employeeMobileAlt.isEmpty())) {
            linearLayout.setVisibility(View.VISIBLE);
            tV = (TextView) rootView.findViewById(R.id.idViewEmployeeMobile2);
            tV.setText(employeeMobileAlt);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        imageView = rootView.findViewById(R.id.idMobile2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialEmployee(employeeMobileAlt);
            }
        });
        imageView = rootView.findViewById(R.id.idSmsEmployee2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsEmployee(employeeMobileAlt);
            }
        });


        linearLayout = rootView.findViewById(R.id.ll_note);
        if ((employeeNotes != null) && (!employeeNotes.isEmpty())) {
            Log.e("OPM", "inside patientNotes...");
            linearLayout.setVisibility(View.VISIBLE);
            tV = (TextView) rootView.findViewById(R.id.idViewEmployeeNote);
            tV.setText(employeeNotes);
        } else {
            linearLayout.setVisibility(View.GONE);
        }

        linearLayout = rootView.findViewById(R.id.ll_address);
        if ((employeeAddress != null) && (employeeAddress.isEmpty() != true)) {
            linearLayout.setVisibility(View.VISIBLE);
            tV = (TextView) rootView.findViewById(R.id.idViewEmployeeAddress);
            tV.setText(employeeAddress);
        } else {
            linearLayout.setVisibility(View.GONE);
        }

        linearLayout = rootView.findViewById(R.id.ll_city);
        if ((employeeCity != null) && (employeeCity.isEmpty() != true)) {
            linearLayout.setVisibility(View.VISIBLE);
            tV = (TextView) rootView.findViewById(R.id.idViewEmployeeCity);
            tV.setText(employeeCity);
        } else {
            linearLayout.setVisibility(View.GONE);
        }



    }
        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.idEdit);
        item.setVisible(true);

        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.idEdit) {
            editEmployee();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestPermission(String[] permissionArray, int requestCode) {
        requestPermissions(permissionArray, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        final int REQUEST_CODE_CALL_PERMISSION = 1;

        switch (requestCode) {
            case REQUEST_CODE_CALL_PERMISSION:
                Log.e(TAG, "onRequestPermissionsResult switch..................");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + employeeMobile));
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Permission required")
                            .setIcon(R.drawable.call_button)
                            .setMessage("Please ALLOW App to make phone calls")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
                break;
        }
    }

    public void dialEmployee(String mobile) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile)));
        } else {
            requestPermission(callPermArray, REQUEST_CODE_CALL_PERMISSION);
        }
        return;
    }

    public void smsEmployee(String mobileNumber) {

        TelephonyManager telephonyManager1 = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager1.getPhoneType()==TelephonyManager.PHONE_TYPE_NONE)
        {
            Toast.makeText(getActivity(), "Messaging Not supported", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("address", mobileNumber);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);

        return;
    }

    public void editEmployee() {
/*
        Intent i = new Intent(getContext(), EditEmployee.class);
        i.putExtra("id", id);
        i.putExtra("employeeName", employeeName);
        i.putExtra("employeeRole", employeeRole);
        i.putExtra("employeeWage", employeeWage);
        i.putExtra("employeeDOJ", employeeDOJ);
        i.putExtra("employeeGender", employeeGender);
        i.putExtra("employeeAge", employeeAge);
        i.putExtra("employeeMobile", employeeMobile);
        i.putExtra("employeeMobileAlt", employeeMobileAlt);
        i.putExtra("employeeNotes", employeeNotes);
        i.putExtra("employeeAddress", employeeAddress);
        i.putExtra("employeeCity", employeeCity);
        i.putExtra("ageReferenceDateTime", ageReferenceDateTime);
        i.putExtra("employeeStatus", employeeStatus);
        startActivity(i);
*/
        return;
    }
}