package com.hogslab.dailywages.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.hogslab.dailywages.R;
import com.hogslab.dailywages.pojo.EmployeeBuilder;
import com.hogslab.dailywages.pojo.EmployeePojo;
import com.hogslab.dailywages.providers.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Boolean gender;
    Boolean genderSelected = false;
    TextView textViewShowMoreFields;
    Boolean proceed = true;
    final Context context = this;

    SimpleDateFormat dateFormat;
    TextView textViewDOJ;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    long doj;

    Boolean roleSelected = false;
    String role = null;

    private static final String TAG = "Daily Wages";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add Employee");


        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dateFormat = new SimpleDateFormat("dd MMM yyyy");
        textViewDOJ = (TextView) findViewById(R.id.idEmployeeDOJ);
        textViewDOJ.setText("  " + dateFormat.format(System.currentTimeMillis()));

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        Spinner spinnerRole = (Spinner) findViewById(R.id.spinner_role);
        spinnerRole.setOnItemSelectedListener(this);

        List<String> listRole = new ArrayList<>();
        listRole.add("Role");
        DBAdapter db = new DBAdapter(this);
        try {
            db.open();
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.toString());
            return;
        }
        Cursor c = db.getAllRoles();
        if (c.getCount() <= 0) {
            Log.e(TAG, "No roles found....");
        }
        c.moveToFirst();
        do {
            listRole.add(c.getString(c.getColumnIndex("employee_role_names")));

        } while (c.moveToNext());
        c.close();
        db.close();

        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listRole);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(arrayAdapter);

        Spinner spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        spinnerGender.setOnItemSelectedListener(this);

        List<String> listGender = new ArrayList<>();
        listGender.add("Gender");
        listGender.add("Male");
        listGender.add("Female");

        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(arrayAdapter);

        textViewShowMoreFields = (TextView) findViewById(R.id.shoe_more_fields_button);
        textViewShowMoreFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayoutMobileAlt = (TextInputLayout) findViewById(R.id.mobileAltWrapper);
                textInputLayoutMobileAlt.setVisibility(View.VISIBLE);

                TextInputLayout textInputLayoutNotes = (TextInputLayout) findViewById(R.id.notesWrapper);
                textInputLayoutNotes.setVisibility(View.VISIBLE);

                TextInputLayout textInputLayoutAddress = (TextInputLayout) findViewById(R.id.addressWrapper);
                textInputLayoutAddress.setVisibility(View.VISIBLE);

                TextInputLayout textInputLayoutCity = (TextInputLayout) findViewById(R.id.cityWrapper);
                textInputLayoutCity.setVisibility(View.VISIBLE);

                textViewShowMoreFields.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.idEmployeeList) {
            finish();
            return true;
        }
        if (id == R.id.idSave) {
            addEmployee();
            return true;
        }
        if (id == R.id.idContactUs) {
            Intent i = new Intent(this, ContactUsActivity.class);
            startActivity(i);
        }
        if (id == R.id.idExit) {
            moveTaskToBack(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEmployee() {
        Integer employeeGender = -1;
        Integer iemployeeWage = 0;
        Integer iemployeeAge = 0;
        EditText eT;
        TextInputEditText textInputEditText;

        ((TextInputLayout)findViewById(R.id.nameWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.wageWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.ageWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.mobileWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.mobileAltWrapper)).setError(null);
//        ((TextInputLayout)findViewById(R.id.emailWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.notesWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.addressWrapper)).setError(null);
        ((TextInputLayout)findViewById(R.id.cityWrapper)).setError(null);

        textInputEditText = (TextInputEditText) findViewById(R.id.idEditEmployeeName);
        String employeeName = textInputEditText.getText().toString();
        if (employeeName.matches("")) {
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.nameWrapper);
            textInputLayoutName.setError("You need to enter the employee name");
            return;
        }
        if (employeeName.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee name");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.nameWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }

        if (roleSelected == false) {
            Toast.makeText(this, "Select employee Role", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        textInputEditText = (TextInputEditText) findViewById(R.id.idEditEmployeeWage);
        String employeeWage = textInputEditText.getText().toString();
        if (employeeWage.matches("")) {
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.wageWrapper);
            textInputLayoutName.setError("You need to enter the employee wages per day");
            return;
        }
        iemployeeWage = Integer.parseInt(employeeWage);
        if (iemployeeWage <= 0 || iemployeeWage > 99999) {
            TextInputLayout textInputLayoutAge = (TextInputLayout) findViewById(R.id.wageWrapper);
            textInputLayoutAge.setError("Wage should be between 1 and 99999");
            return;
        }

        if (genderSelected == false) {
            Toast.makeText(this, "Select employee Gender", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        textInputEditText = (TextInputEditText) findViewById(R.id.idEditEmployeeAge);
        String employeeAge = textInputEditText.getText().toString();
        if (employeeAge.matches("")) {
            TextInputLayout textInputLayoutAge = (TextInputLayout) findViewById(R.id.ageWrapper);
            textInputLayoutAge.setError("You need to enter the employee age");
            return;
        }
        iemployeeAge = Integer.parseInt(employeeAge);
        if (iemployeeAge <= 0 || iemployeeAge > 999) {
            TextInputLayout textInputLayoutAge = (TextInputLayout) findViewById(R.id.ageWrapper);
            textInputLayoutAge.setError("Age should be between 1 and 999");
            return;
        }

        textInputEditText = (TextInputEditText) findViewById(R.id.idEditEmployeeMobile);
        String employeeMobile = textInputEditText.getText().toString();
        if (employeeMobile.matches("")) {
            TextInputLayout textInputLayoutMobile = (TextInputLayout) findViewById(R.id.mobileWrapper);
            textInputLayoutMobile.setError("You need to enter the employee mobile");
            return;
        }
        if (employeeMobile.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee mobile");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.mobileWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }


        eT = (EditText) findViewById(R.id.idEditEmployeeMobileAlt);
        String employeeMobileAlt = eT.getText().toString();
        if (employeeMobileAlt.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee mobile alt");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.mobileAltWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }

/*
        eT = (EditText) findViewById(R.id.idEditEmployeeEmail);
        String employeeEmail = eT.getText().toString();
        if (employeeEmail.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee mobile alt");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.emailWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }
*/

        eT = (EditText) findViewById(R.id.idEditEmployeeNotes);
        String notes = eT.getText().toString();
        if (notes.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee notes");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.notesWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }

        eT = (EditText) findViewById(R.id.idEditEmployeeAddress);
        String employeeAddress = eT.getText().toString();
        if (employeeAddress.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee address");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.addressWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }

        eT = (EditText) findViewById(R.id.idEditEmployeeCity);
        String employeeCity = eT.getText().toString();
        if (employeeCity.indexOf(';') >= 0) {
            Log.e(TAG," semicolon found in employee city");
            TextInputLayout textInputLayoutName = (TextInputLayout) findViewById(R.id.cityWrapper);
            textInputLayoutName.setError("Semicolon (;) not allowed in any fields !!!");
            return;
        }

        employeeGender = gender == true ? 1  : 0;

        EmployeePojo employeePojo = new EmployeeBuilder().setEmployeeName(employeeName).setEmployeeRole(role).setEmployeeDOJ(doj).setEmployeeWage(iemployeeWage)
                                    .setEmployeeGender(gender).setEmployeeAge(iemployeeAge).setEmployeeMobile(employeeMobile).setEmployeeMobileAlt(employeeMobileAlt)
                                    .setEmployeeEmail(null).setEmployeeNotes(notes).setEmployeeAddress(employeeAddress).setEmployeeCity(employeeCity).setAgeReference((long) 0).setEmployeeStatus(true).getEmployeePojo();


        DBAdapter db = new DBAdapter(this);
        long ret = -1;
        try {
            db.open();
            ret = db.insertEmployee(employeePojo);
            if (ret == -1) {
                Log.d(TAG, "Inserting employee Failed!!!!!!!!");
            }
            Log.e("OPM", "id is " + ret);
        } catch (Exception e) {
            Log.d(TAG, "Unable to open the database");
        } finally {
            db.close();
        }

/*
        Intent i = new Intent(this, PatientProfileActivity.class);
        i.putExtra("id",(int) ret);
        startActivity(i);
*/
        finish();
        return;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        switch (adapterView.getId()) {
            case R.id.spinner_role:
                role = item;
                if (item.equals("Role")) {
                    roleSelected = false;
                } else {
                    roleSelected = true;
                }
                break;
            case R.id.spinner_gender:

                if (item.equals("Male")) {
                    gender = false;
                    genderSelected = true;
                } else if (item.equals("Female")) {
                    gender = genderSelected = true;
                } else if (item.equals("Gender")) {
                    genderSelected = false;
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setDOJ(View v) {

        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        return;
    }

    private void updateLabel() {

        doj = myCalendar.getTime().getTime();

        textViewDOJ.setText("  " + dateFormat.format(myCalendar.getTime()));
    }
}

