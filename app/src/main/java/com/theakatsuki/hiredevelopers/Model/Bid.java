package com.theakatsuki.hiredevelopers.Model;

public class Bid {
    String jobId;
    String payment;
    String delivery;
    String description;
    String userId;

    public Bid(String jobId, String payment, String delivery, String description, String userId) {
        this.jobId = jobId;
        this.payment = payment;
        this.delivery = delivery;
        this.description = description;
        this.userId = userId;
    }

    public Bid() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
