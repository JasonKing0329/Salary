package com.king.app.salary.model.repository;

import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.entity.SalaryDao;
import com.king.app.salary.model.entity.SalaryDetail;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:31
 */
public class SalaryRepository extends BaseRepository {

    public Observable<List<Salary>> getSalaries() {
        return Observable.create(e -> {
            QueryBuilder<Salary> builder = getDaoSession().getSalaryDao().queryBuilder();
            builder.orderDesc(SalaryDao.Properties.Year);
            builder.orderDesc(SalaryDao.Properties.Month);
            e.onNext(builder.build().list());
        });
    }

    public Observable<Salary> insertOrUpdateSalary(Salary salary) {
        return Observable.create(e -> {
            getDaoSession().getSalaryDao().insertOrReplace(salary);
            e.onNext(salary);
        });
    }

    public Observable<SalaryDetail> insertOrUpdateSalaryDetail(SalaryDetail detail) {
        return Observable.create(e -> {
            getDaoSession().getSalaryDetailDao().insertOrReplace(detail);
            e.onNext(detail);
        });
    }
}
