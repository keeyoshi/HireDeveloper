package com.theakatsuki.hiredevelopers.Model;

import java.util.Date;
import java.util.List;

public class Job {
    String title;
    String price;
    List<String> requirement;
    Date closingDate;

    public Job(String title, String price, List<String> requirement, Date closingDate) {
        this.title = title;
        this.price = price;
        this.requirement = requirement;
        this.closingDate = closingDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getRequirement() {
        return requirement;
    }

    public void setRequirement(List<String> requirement) {
        this.requirement = requirement;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Job() {
    }
}
