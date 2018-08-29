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

    private Date enterData;

    private Date exitDate;

    @Generated(hash = 1725373506)
    public Company(Long id, String name, Date enterData, Date exitDate) {
        this.id = id;
        this.name = name;
        this.enterData = enterData;
        this.exitDate = exitDate;
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

    public Date getEnterData() {
        return this.enterData;
    }

    public void setEnterData(Date enterData) {
        this.enterData = enterData;
    }

    public Date getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }
}
