package com.king.app.salary.model.repository;

import com.king.app.salary.model.entity.Company;
import com.king.app.salary.model.entity.CompanyDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:31
 */
public class CompanyRepository extends BaseRepository {

    public Observable<List<Company>> getCompanies() {
        return Observable.create(e -> {
            QueryBuilder<Company> builder = getDaoSession().getCompanyDao().queryBuilder();
            builder.orderDesc(CompanyDao.Properties.EnterDate);
            e.onNext(builder.build().list());
        });
    }

    public Observable<Company> getCompany(long id) {
        return Observable.create(e -> e.onNext(getDaoSession().getCompanyDao().load(id)));
    }

    public Observable<Company> insertOrUpdate(Company company) {
        return Observable.create(e -> {
            getDaoSession().getCompanyDao().insertOrReplace(company);
            getDaoSession().getCompanyDao().detachAll();
            e.onNext(company);
        });
    }
}
