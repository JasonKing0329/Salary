package com.king.app.salary.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 13:28
 */
@Entity(nameInDb = "company")
public class Company {

    @Id(autoincrement = true)
    private Long id;

    private String name;

    private Date enterDate;

    private Date exitDate;

    private String city;

    private String district;

    @Generated(hash = 1354193647)
    public Company(Long id, String name, Date enterDate, Date exitDate, String city,
            String district) {
        this.id = id;
        this.name = name;
        this.enterDate = enterDate;
        this.exitDate = exitDate;
        this.city = city;
        this.district = district;
    }

    @Generated(hash = 1096856789)
    public Company() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEnterDate() {
        return this.enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
