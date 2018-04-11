package com.hogslab.dailywages.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hogslab.dailywages.pojo.AttendancePojo;
import com.hogslab.dailywages.pojo.EmployeePojo;
import com.hogslab.dailywages.pojo.PaymentsPojo;


public class DBAdapter {

    private Context context;
    SQLiteDatabase db;
    DBHelper helper;

    public static final String DATABASE_NAME = "wages.db";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_EMPLOYEE_ROLES = "roles";
    public static final String EMPLOYEE_ROLE_NAMES = "employee_role_names";

    private static final String CREATE_TABLE_EMPLOYEE_ROLES = "CREATE TABLE "
            + TABLE_EMPLOYEE_ROLES + " (" + EMPLOYEE_ROLE_NAMES + " VARCHAR(31) NOT NULL);";

    private static final String DELETE_TABLE_EMPLOYEE_ROLES = "DROP TABLE IF EXITS roles";

    String[] employeeRoles = {  EMPLOYEE_ROLE_NAMES };

    public static final String TABLE_PAYMENTS = "payments";

    public static final String PAYMENTS_EMPLOYEE_ID = "employee_id";
    public static final String PAYMENTS_DATE_TIME = "payments_date_time";
    public static final String PAYMENTS_AMOUNT = "payments_amount";
    public static final String PAYMENTS_TYPE = "payments_type";
    public static final String PAYMENTS_NOTES = "payments_notes";

    private static final String CREATE_TABLE_PAYMENTS = "CREATE TABLE "
            + TABLE_PAYMENTS + " ("
            + PAYMENTS_EMPLOYEE_ID + " INTEGER,"
            + PAYMENTS_DATE_TIME + " NUMERIC,"
            + PAYMENTS_AMOUNT + " SMALLINT,"
            + PAYMENTS_TYPE + " BOOLEAN,"
            + PAYMENTS_NOTES + " VARCHAR(63) NOT NULL );";

    private static final String DELETE_TABLE_PAYMENTS = "DROP TABLE IF EXITS payments";

    String[] paymentsDetails = {  PAYMENTS_EMPLOYEE_ID, PAYMENTS_DATE_TIME, PAYMENTS_AMOUNT, PAYMENTS_TYPE, PAYMENTS_NOTES};

    String[] amountOnly = {  PAYMENTS_AMOUNT };

    public Cursor getPaymentsOnlyByEmployeeIdWeekRange(int id, long startDate, long endDate) {
        return db.query(TABLE_PAYMENTS, paymentsDetails, " employee_id = ? AND  payments_date_time >= ? AND payments_date_time <= ? ", new String[]{String.valueOf(id), String.valueOf(startDate), String.valueOf(endDate)}, null, null, PAYMENTS_DATE_TIME + " DESC");
    }

    public Cursor getPaymentsByEmployeeId(int id) {
        return db.query(TABLE_PAYMENTS, paymentsDetails, " employee_id = " + id, null, null, null, PAYMENTS_DATE_TIME + " DESC");
    }

    public long insertPayments(PaymentsPojo paymentsPojo) {
        ContentValues values = new ContentValues();

/*
        values.put(PAYMENTS_EMPLOYEE_ID, paymentsPojo.getEmployeeId());
        values.put(PAYMENTS_DATE_TIME, paymentsPojo.getPaymentDateTime());
        values.put(PAYMENTS_AMOUNT, paymentsPojo.getPaymentAmount());
        values.put(PAYMENTS_NOTES, paymentsPojo.getPaymentNotes());
*/

        return db.insert(TABLE_PAYMENTS, null, values);
    }

    public long updatePayments(PaymentsPojo paymentsPojo) {
        ContentValues values = new ContentValues();

/*
        values.put(PAYMENTS_EMPLOYEE_ID, paymentsPojo.getEmployeeId());
        values.put(PAYMENTS_DATE_TIME, paymentsPojo.getPaymentDateTime());
        values.put(PAYMENTS_AMOUNT, paymentsPojo.getPaymentAmount());
        values.put(PAYMENTS_NOTES, paymentsPojo.getPaymentNotes());
*/

        return db.update(TABLE_PAYMENTS, values, " employee_id = ? AND payments_date_time = ? ", new String[]{String.valueOf(paymentsPojo.getEmployeeId()), String.valueOf(paymentsPojo.getPaymentDateTime())});
    }

    public long deletePayments(PaymentsPojo paymentsPojo) {

        return db.delete(TABLE_PAYMENTS, " employee_id = ? AND payments_date_time = ? ", new String[]{String.valueOf(paymentsPojo.getEmployeeId()), String.valueOf(paymentsPojo.getPaymentDateTime())});
    }

    public static final String TABLE_ATTENDANCE = "attendance";

    public static final String ATTENDANCE_EMPLOYEE_ID = "employee_id";
    public static final String ATTENDANCE_DATE = "attendance_date";
    public static final String ATTENDANCE_STATUS = "attendance_status";
    public static final String ATTENDANCE_NOTES = "attendance_notes";
    public static final String ATTENDANCE_MARKED = "attendance_marked";
    public static final String ATTENDANCE_DAILY_WAGE = "attendance_daily_wage";
    public static final String ATTENDANCE_PAY_WAGE = "attendance_pay_wage";

    private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE "
                                + TABLE_ATTENDANCE + " ("
                                + ATTENDANCE_EMPLOYEE_ID + " INTEGER,"
                                + ATTENDANCE_DATE + " NUMERIC,"
                                + ATTENDANCE_STATUS + " SMALLINT,"
                                + ATTENDANCE_NOTES + " VARCHAR(63) NOT NULL,"
                                + ATTENDANCE_MARKED + " BOOLEAN NOT NULL,"
                                + ATTENDANCE_DAILY_WAGE + " SMALLINT,"
                                + ATTENDANCE_PAY_WAGE + " SMALLINT,"
                                + "PRIMARY KEY (" + ATTENDANCE_EMPLOYEE_ID + "," +  ATTENDANCE_DATE + "));";


    private static final String DELETE_TABLE_ATTENDANCE = "DROP TABLE IF EXITS attendance";


    String[] attendanceDetails = {  ATTENDANCE_EMPLOYEE_ID,  ATTENDANCE_DATE, ATTENDANCE_STATUS, ATTENDANCE_NOTES, ATTENDANCE_MARKED, ATTENDANCE_DAILY_WAGE, ATTENDANCE_PAY_WAGE};

    public long insertAttendance(AttendancePojo attendancePojo) {
        ContentValues values = new ContentValues();

/*
        values.put(ATTENDANCE_EMPLOYEE_ID, attendancePojo.getEmployeeId());
        values.put(ATTENDANCE_DATE, attendancePojo.getDate());
        values.put(ATTENDANCE_STATUS, attendancePojo.getStatus());
        values.put(ATTENDANCE_NOTES, attendancePojo.getNotes());
        values.put(ATTENDANCE_MARKED, attendancePojo.getMarked());
        values.put(ATTENDANCE_DAILY_WAGE, attendancePojo.getDaily_wage());
        values.put(ATTENDANCE_PAY_WAGE, attendancePojo.getPay_wage());
*/

        return db.insert(TABLE_ATTENDANCE, null, values);
    }

    public long updateAttendance(AttendancePojo attendancePojo) {
        ContentValues values = new ContentValues();

/*
        values.put(ATTENDANCE_EMPLOYEE_ID, attendancePojo.getEmployeeId());
        values.put(ATTENDANCE_DATE, attendancePojo.getDate());
        values.put(ATTENDANCE_STATUS, attendancePojo.getStatus());
        switch (attendancePojo.getStatus()) {
            case AttendanceStatus.PRESENT:
                values.put(ATTENDANCE_PAY_WAGE, attendancePojo.getDaily_wage());
                break;
            case AttendanceStatus.HALFDAY:
                values.put(ATTENDANCE_PAY_WAGE, attendancePojo.getDaily_wage()/2);
                break;
            case AttendanceStatus.HOLIDAY:
            case AttendanceStatus.ABSENT:
            case AttendanceStatus.NOT_MARKED:
                values.put(ATTENDANCE_PAY_WAGE, 0);
                break;
        }
        values.put(ATTENDANCE_NOTES, attendancePojo.getNotes());
        values.put(ATTENDANCE_MARKED, true);
        values.put(ATTENDANCE_DAILY_WAGE, attendancePojo.getDaily_wage());
*/

        return db.update(TABLE_ATTENDANCE, values, "employee_id = ? AND attendance_date = ? " , new String[] {String.valueOf(attendancePojo.getEmployeeId()), String.valueOf(attendancePojo.getDate())});
    }

    public Cursor getAttendanceforEmployeeIdAndDate(int id, long date) {
        return db.query(TABLE_ATTENDANCE, attendanceDetails, " employee_id = " + id + " AND attendance_date = " + date, null, null, null, null);
    }

    public Cursor getAttendanceforWeekRange(int id) {
        return db.query(TABLE_ATTENDANCE, attendanceDetails, " employee_id = " + id, null, null, null, null);
    }


    public static final String TABLE_EMPLOYEE = "employee";

    public static final String UID = "_id";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String EMPLOYEE_ROLE = "employee_role";
    public static final String EMPLOYEE_WAGE = "employee_wage";
    public static final String EMPLOYEE_STATUS = "employee_status";
    public static final String EMPLOYEE_GENDER = "employee_gender";
    public static final String EMPLOYEE_AGE = "employee_age";
    public static final String EMPLOYEE_MOBILE = "employee_mobile";
    public static final String EMPLOYEE_MOBILE_ALT = "employee_mobile_alt";
    public static final String EMPLOYEE_EMAIL = "employee_email";
    public static final String EMPLOYEE_ADDRESS = "employee_address";
    public static final String EMPLOYEE_CITY = "employee_city";
    public static final String EMPLOYEE_NOTES = "employee_notes";
    public static final String EMPLOYEE_DOJ = "employee_doj";
    public static final String EMPLOYEE_AGE_REFERENCE = "employee_age_reference";


    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE "
            + TABLE_EMPLOYEE + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EMPLOYEE_NAME + " VARCHAR(63) NOT NULL,"
            + EMPLOYEE_ROLE + " VARCHAR(31) NOT NULL,"
            + EMPLOYEE_WAGE + " INTEGER,"
            + EMPLOYEE_GENDER + " BOOLEAN NOT NULL,"
            + EMPLOYEE_STATUS + " BOOLEAN NOT NULL,"
            + EMPLOYEE_AGE + " SMALLINT NOT NULL,"
            + EMPLOYEE_MOBILE + " VARCHAR(31) NOT NULL,"
            + EMPLOYEE_MOBILE_ALT + " VARCHAR(31),"
            + EMPLOYEE_EMAIL + " VARCHAR(255),"
            + EMPLOYEE_ADDRESS + " VARCHAR(255),"
            + EMPLOYEE_CITY + " VARCHAR(63), "
            + EMPLOYEE_NOTES + " VARCHAR(127), "
            + EMPLOYEE_AGE_REFERENCE + " BIGINT, "
            + EMPLOYEE_DOJ + " BIGINT);";

    private static final String DELETE_TABLE_PATIENTS = "DROP TABLE IF EXITS employee";


    public static class DBHelper extends SQLiteOpenHelper {
        Context context;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
                sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE_ROLES);
                sqLiteDatabase.execSQL(CREATE_TABLE_ATTENDANCE);
                sqLiteDatabase.execSQL(CREATE_TABLE_PAYMENTS);
            } catch (SQLException e) {
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public DBAdapter(Context ctx) {
        this.context = ctx;
        helper = new DBHelper(context);
    }

    public DBAdapter open() throws Exception {
        db = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    String[] employeeBrief = { UID, EMPLOYEE_NAME, EMPLOYEE_MOBILE, EMPLOYEE_ROLE};
    String[] employeeDetailed = { UID, EMPLOYEE_NAME, EMPLOYEE_WAGE, EMPLOYEE_ROLE, EMPLOYEE_STATUS, EMPLOYEE_GENDER, EMPLOYEE_AGE, EMPLOYEE_MOBILE,
                                    EMPLOYEE_MOBILE_ALT, EMPLOYEE_EMAIL, EMPLOYEE_NOTES, EMPLOYEE_ADDRESS, EMPLOYEE_CITY,
                                    EMPLOYEE_DOJ, EMPLOYEE_AGE_REFERENCE};


    public Cursor getAllEmployeesId() {
        return db.query(TABLE_EMPLOYEE, employeeBrief, null, null, null, null, null);
    }

    public Cursor getAllEmployeeBrief() {
        return db.query(TABLE_EMPLOYEE, employeeBrief, null, null, null, null,
                EMPLOYEE_NAME + " COLLATE NOCASE ASC");
    }

    public Cursor getEmployeeDetailed(int employeeId) {
        return db.query(TABLE_EMPLOYEE, employeeDetailed, "_id = " + employeeId, null, null, null, null);
    }

    public int deleteEmployeeById(int id) {
        return db.delete(TABLE_EMPLOYEE, UID + "=" + id , null);
    }

    public long insertEmployee(@NonNull EmployeePojo employeePojo) {
        ContentValues values = new ContentValues();


        values.put(EMPLOYEE_NAME, employeePojo.getEmployeeName());
        values.put(EMPLOYEE_ROLE, employeePojo.getEmployeeRole());
        values.put(EMPLOYEE_WAGE, employeePojo.getEmployeeWage());
        values.put(EMPLOYEE_GENDER, employeePojo.getEmployeeGender());
        values.put(EMPLOYEE_AGE, employeePojo.getEmployeeAge());

        values.put(EMPLOYEE_MOBILE, employeePojo.getEmployeeMobile());
        values.put(EMPLOYEE_MOBILE_ALT, employeePojo.getEmployeeMobileAlt());
        values.put(EMPLOYEE_EMAIL, employeePojo.getEmployeeEmail());
        values.put(EMPLOYEE_ADDRESS, employeePojo.getEmployeeAddress());
        values.put(EMPLOYEE_CITY, employeePojo.getEmployeeCity());

        values.put(EMPLOYEE_NOTES, employeePojo.getEmployeeNotes());
        values.put(EMPLOYEE_STATUS, true);

        if (employeePojo.getEmployeeDOJ() == 0) {
            values.put(EMPLOYEE_DOJ, System.currentTimeMillis());
        } else {
            values.put(EMPLOYEE_DOJ, employeePojo.getEmployeeDOJ());
        }

        if (employeePojo.getAgeReference() == 0) {
            values.put(EMPLOYEE_AGE_REFERENCE, (System.currentTimeMillis() / 1000));
        } else {
            values.put(EMPLOYEE_AGE_REFERENCE, employeePojo.getAgeReference());
        }


        return db.insert(TABLE_EMPLOYEE, null, values);
    }

    public long updateEmployee(@NonNull EmployeePojo employeePojo) {
        ContentValues values = new ContentValues();

/*
        values.put(EMPLOYEE_NAME, employeePojo.getEmployeeName());
        values.put(EMPLOYEE_ROLE, employeePojo.getEmployeeRole());
        values.put(EMPLOYEE_WAGE, employeePojo.getEmployeeWage());
        values.put(EMPLOYEE_GENDER, employeePojo.getEmployeeGender());
        values.put(EMPLOYEE_AGE, employeePojo.getEmployeeAge());

        values.put(EMPLOYEE_MOBILE, employeePojo.getEmployeeMobile());
        values.put(EMPLOYEE_MOBILE_ALT, employeePojo.getEmployeeMobileAlt());
        values.put(EMPLOYEE_EMAIL, employeePojo.getEmployeeEmail());
        values.put(EMPLOYEE_ADDRESS, employeePojo.getEmployeeAddress());
        values.put(EMPLOYEE_CITY, employeePojo.getEmployeeCity());

        values.put(EMPLOYEE_NOTES, employeePojo.getEmployeeNotes());
        values.put(EMPLOYEE_STATUS, true);

        values.put(EMPLOYEE_DOJ, employeePojo.getEmployeeDOJ());

        if (employeePojo.getAgeReference() == 0) {
            values.put(EMPLOYEE_AGE_REFERENCE, (System.currentTimeMillis() / 1000));
        } else {
            values.put(EMPLOYEE_AGE_REFERENCE, employeePojo.getAgeReference());
        }

        Log.e("WW ", " id is " + employeePojo.getEmployeeId());
*/

        return db.update(TABLE_EMPLOYEE, values, "_id = " + employeePojo.getEmployeeId(), null);
    }

    public long insertRole(String employeeRole) {
        ContentValues values = new ContentValues();

        values.put(EMPLOYEE_ROLE_NAMES, employeeRole);

        return db.insert(TABLE_EMPLOYEE_ROLES, null, values);
    }

    public Cursor getAllRoles() {
        return db.query(TABLE_EMPLOYEE_ROLES, employeeRoles , null, null, null, null, null);
    }

}
