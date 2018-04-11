package com.hogslab.dailywages.pojo;


public class EmployeeBriefPojo {
    String employeeName;
    String employeeRole;
    String employeeMobile;
    int employeeId;
    int wagesPending;

    public EmployeeBriefPojo(String employeeName, String employeeMobile, String employeeRole, int employeeId) {
        this.employeeName = employeeName;
        this.employeeRole = employeeRole;
        this.employeeId = employeeId;
        this.employeeMobile = employeeMobile;
    }

    public EmployeeBriefPojo(String employeeName, String employeeMobile, String employeeRole, int employeeId, int wages) {
        this.employeeName = employeeName;
        this.employeeRole = employeeRole;
        this.employeeId = employeeId;
        this.employeeMobile = employeeMobile;
        this.wagesPending = wages;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getEmployeeMobile() {
        return employeeMobile;
    }

    public void setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getWagesPending() {
        return wagesPending;
    }

    public void setWagesPending(int wagesPending) {
        this.wagesPending = wagesPending;
    }

    @Override
    public String toString() {
        String s = "Employee Name:" + getEmployeeName() + " Employee Role; " + getEmployeeMobile() + " Employee Mobile "
                + getEmployeeMobile() + " Employee ID " + getEmployeeId() +" ;";
        return s;
    }
}

