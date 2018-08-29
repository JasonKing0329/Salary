package com.king.app.salary.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 13:18
 */
@Entity(nameInDb = "salary")
public class Salary {

    @Id(autoincrement = true)
    private Long id;

    private int year;

    private int month;

    private int type;

    private Date date;

    private float total;

    private float receive;

    private float deduction;

    private long companyId;

    private long detailId;

    @ToOne(joinProperty = "companyId")
    private Company company;

    @ToOne(joinProperty = "detailId")
    private SalaryDetail detail;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1530642378)
    private transient SalaryDao myDao;

    @Generated(hash = 753481382)
    public Salary(Long id, int year, int month, int type, Date date, float total,
            float receive, float deduction, long companyId, long detailId) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.type = type;
        this.date = date;
        this.total = total;
        this.receive = receive;
        this.deduction = deduction;
        this.companyId = companyId;
        this.detailId = detailId;
    }

    @Generated(hash = 632285593)
    public Salary() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getReceive() {
        return this.receive;
    }

    public void setReceive(float receive) {
        this.receive = receive;
    }

    public float getDeduction() {
        return this.deduction;
    }

    public void setDeduction(float deduction) {
        this.deduction = deduction;
    }

    public long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getDetailId() {
        return this.detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    @Generated(hash = 1496811699)
    private transient Long company__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1266421621)
    public Company getCompany() {
        long __key = this.companyId;
        if (company__resolvedKey == null || !company__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CompanyDao targetDao = daoSession.getCompanyDao();
            Company companyNew = targetDao.load(__key);
            synchronized (this) {
                company = companyNew;
                company__resolvedKey = __key;
            }
        }
        return company;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 474861392)
    public void setCompany(@NotNull Company company) {
        if (company == null) {
            throw new DaoException(
                    "To-one property 'companyId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.company = company;
            companyId = company.getId();
            company__resolvedKey = companyId;
        }
    }

    @Generated(hash = 533089774)
    private transient Long detail__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 35054493)
    public SalaryDetail getDetail() {
        long __key = this.detailId;
        if (detail__resolvedKey == null || !detail__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SalaryDetailDao targetDao = daoSession.getSalaryDetailDao();
            SalaryDetail detailNew = targetDao.load(__key);
            synchronized (this) {
                detail = detailNew;
                detail__resolvedKey = __key;
            }
        }
        return detail;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 219489070)
    public void setDetail(@NotNull SalaryDetail detail) {
        if (detail == null) {
            throw new DaoException(
                    "To-one property 'detailId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.detail = detail;
            detailId = detail.getId();
            detail__resolvedKey = detailId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 897734013)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSalaryDao() : null;
    }
}
