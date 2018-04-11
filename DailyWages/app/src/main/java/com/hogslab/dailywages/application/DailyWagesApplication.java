package com.hogslab.dailywages.application;

import android.app.Application;
import android.widget.Toast;

import com.hogslab.dailywages.pojo.EmployeeBuilder;
import com.hogslab.dailywages.pojo.EmployeePojo;
import com.hogslab.dailywages.providers.DBAdapter;

/**
 * Created by vijay on 11/4/18.
 */

public class DailyWagesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

            EmployeePojo employeePojo;
            employeePojo = new EmployeeBuilder().setEmployeeName("Vijay").setEmployeeGender(false).setEmployeeRole("Cleaner").setEmployeeAddress("Nanthavanappati street").setEmployeeWage(300).setEmployeeAge(38)
                    .setEmployeeMobile("8754903797").setEmployeeCity("Sattur").setAgeReference((long) 0).setEmployeeDOJ(System.currentTimeMillis()).setEmployeeStatus(true).getEmployeePojo();


        DBAdapter db1 = new DBAdapter(this);
        try {
            db1.open();
        } catch (Exception e) {
            return;
        }

            long r1 = db1.insertEmployee(employeePojo);
            if (r1 < 0) {
                Toast.makeText(this, "Unable to insert employee into the table", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "insert success... " + Integer.toString((int) r1), Toast.LENGTH_SHORT).show();
            }

        db1.insertRole("Driver");
        db1.insertRole("Cleaner");
        db1.insertRole("Watchman");
        db1.insertRole("Security");
        db1.close();

    }
}
