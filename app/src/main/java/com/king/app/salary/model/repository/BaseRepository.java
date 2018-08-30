package com.king.app.salary.model.repository;

import com.king.app.salary.base.SalaryApplication;
import com.king.app.salary.model.entity.DaoSession;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:31
 */
public abstract class BaseRepository {

    protected DaoSession getDaoSession() {
        return SalaryApplication.getInstance().getDaoSession();
    }
}
