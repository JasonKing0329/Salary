package com.king.app.salary.page.company;

import com.king.app.salary.model.entity.Company;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 10:44
 */
public class CompanyItem {

    private String index;

    private String place;

    private String name;

    private String date;

    private Company company;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
