package com.hogslab.dailywages.pojo;


public class PaymentsPojo {
    int employeeId;
    long paymentDateTime;
    int paymentAmount;
    String paymentNotes;
    Boolean paymentType;

    public PaymentsPojo(int employeeId, long paymentDateTime, int paymentAmount, String paymentNotes, Boolean paymentType) {
        this.employeeId = employeeId;
        this.paymentDateTime = paymentDateTime;
        this.paymentAmount = paymentAmount;
        this.paymentNotes = paymentNotes;
        this.paymentType = paymentType;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public long getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(long paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public Boolean getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Boolean paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "PaymentsPojo{" +
                "employeeId=" + employeeId +
                ", paymentDateTime=" + paymentDateTime +
                ", paymentAmount=" + paymentAmount +
                ", paymentNotes='" + paymentNotes + '\'' +
                ", paymentType=" + paymentType +
                '}';
    }
}
