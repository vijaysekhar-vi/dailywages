package com.hogslab.dailywages.pojo;

public class AttendancePojo {
    int employeeId;
    long date;
    int status;
    String notes;
    Boolean marked;
    int daily_wage;
    int pay_wage;


    public AttendancePojo(int employeeId, long date, int status, String notes, Boolean marked, int daily_wage, int pay_wage) {
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
        this.notes = notes;
        this.marked = marked;
        this.daily_wage = daily_wage;
        this.pay_wage = pay_wage;
    }

    @Override
    public String toString() {
        return "AttendancePojo{" +
                "employeeId=" + employeeId +
                ", date=" + date +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                ", marked=" + marked +
                ", daily_wage=" + daily_wage +
                ", pay_wage=" + pay_wage +
                '}';
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public int getDaily_wage() {
        return daily_wage;
    }

    public void setDaily_wage(int daily_wage) {
        this.daily_wage = daily_wage;
    }

    public int getPay_wage() {
        return pay_wage;
    }

    public void setPay_wage(int pay_wage) {
        this.pay_wage = pay_wage;
    }
}
