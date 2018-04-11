package com.hogslab.dailywages.pojo;

public class EmployeePojo {
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

    public int getEmployeeWage() {
        return employeeWage;
    }

    public void setEmployeeWage(int employeeWage) {
        this.employeeWage = employeeWage;
    }

    public EmployeePojo(int employeeId, String employeeName, String employeeRole, int employeeWage, Boolean employeeStatus, Boolean employeeGender, int employeeAge, String employeeMobile, String employeeMobileAlt, String employeeEmail, String employeeNotes, String employeeAddress, String employeeCity, Long employeeDOJ, Long ageReference) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeRole = employeeRole;
        this.employeeWage = employeeWage;
        this.employeeStatus = employeeStatus;
        this.employeeGender = employeeGender;
        this.employeeAge = employeeAge;
        this.employeeMobile = employeeMobile;
        this.employeeMobileAlt = employeeMobileAlt;
        this.employeeEmail = employeeEmail;
        this.employeeNotes = employeeNotes;
        this.employeeAddress = employeeAddress;
        this.employeeCity = employeeCity;
        this.employeeDOJ = employeeDOJ;
        this.ageReference = ageReference;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public Boolean getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(Boolean employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public Boolean getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(Boolean employeeGender) {
        this.employeeGender = employeeGender;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEmployeeMobile() {
        return employeeMobile;
    }

    public void setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
    }

    public String getEmployeeMobileAlt() {
        return employeeMobileAlt;
    }

    public void setEmployeeMobileAlt(String employeeMobileAlt) {
        this.employeeMobileAlt = employeeMobileAlt;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }


    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeCity() {
        return employeeCity;
    }

    public void setEmployeeCity(String employeeCity) {
        this.employeeCity = employeeCity;
    }

    public Long getEmployeeDOJ() {
        return employeeDOJ;
    }

    public void setEmployeeDOJ(Long employeeDOJ) {
        this.employeeDOJ = employeeDOJ;
    }

    public Long getAgeReference() {
        return ageReference;
    }

    public void setAgeReference(Long ageReference) {
        this.ageReference = ageReference;
    }

    @Override
    public String toString() {
        return "EmployeePojo{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeRole='" + employeeRole + '\'' +
                ", employeeWage=" + employeeWage +
                ", employeeStatus=" + employeeStatus +
                ", employeeGender=" + employeeGender +
                ", employeeAge=" + employeeAge +
                ", employeeMobile='" + employeeMobile + '\'' +
                ", employeeMobileAlt='" + employeeMobileAlt + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeNotes='" + employeeNotes + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", employeeCity='" + employeeCity + '\'' +
                ", employeeDOJ=" + employeeDOJ +
                ", ageReference=" + ageReference +
                '}';
    }

}
