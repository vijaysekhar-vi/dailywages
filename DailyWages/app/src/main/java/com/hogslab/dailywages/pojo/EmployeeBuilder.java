package com.hogslab.dailywages.pojo;

public class EmployeeBuilder {

    int employeeId;
    String employeeName;
    String employeeRole;
    int employeeWage;
    Boolean employeeStatus;
    Boolean employeeGender;
    int employeeAge;
    String employeeMobile;
    String employeeMobileAlt;
    String employeeEmail;
    String employeeNotes;
    String employeeAddress;
    String employeeCity;
    Long employeeDOJ;
    Long ageReference;

    public EmployeeBuilder setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public EmployeeBuilder setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public EmployeeBuilder setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
        return this;
    }

    public EmployeeBuilder setEmployeeWage(int employeeWage) {
        this.employeeWage = employeeWage;
        return this;
    }

    public EmployeeBuilder setEmployeeStatus(Boolean employeeStatus) {
        this.employeeStatus = employeeStatus;
        return this;
    }

    public EmployeeBuilder setEmployeeGender(Boolean employeeGender) {
        this.employeeGender = employeeGender;
        return this;
    }

    public EmployeeBuilder setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
        return this;
    }

    public EmployeeBuilder setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
        return this;
    }

    public EmployeeBuilder setEmployeeMobileAlt(String employeeMobileAlt) {
        this.employeeMobileAlt = employeeMobileAlt;
        return this;
    }

    public EmployeeBuilder setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public EmployeeBuilder setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
        return this;
    }

    public EmployeeBuilder setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
        return this;
    }

    public EmployeeBuilder setEmployeeCity(String employeeCity) {
        this.employeeCity = employeeCity;
        return this;
    }

    public EmployeeBuilder setEmployeeDOJ(Long employeeDOJ) {
        this.employeeDOJ = employeeDOJ;
        return this;
    }

    public EmployeeBuilder setAgeReference(Long ageReference) {
        this.ageReference = ageReference;
        return this;
    }

    public EmployeePojo getEmployeePojo() {
        return new EmployeePojo(employeeId, employeeName, employeeRole, employeeWage, employeeStatus, employeeGender, employeeAge, employeeMobile,
                employeeMobileAlt, employeeEmail, employeeNotes, employeeAddress, employeeCity, employeeDOJ, ageReference);
    }
}
