package com.king.app.salary.page.company;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.king.app.salary.base.BaseViewModel;
import com.king.app.salary.model.entity.Company;
import com.king.app.salary.model.repository.CompanyRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 11:38
 */
public class CompanyViewModel extends BaseViewModel {

    public MutableLiveData<List<CompanyItem>> companyObserver = new MutableLiveData<>();

    public MutableLiveData<Boolean> deleteObserver = new MutableLiveData<>();

    private Map<Long, Boolean> mCheckMap;

    private CompanyRepository repository;

    public CompanyViewModel(@NonNull Application application) {
        super(application);
        mCheckMap = new HashMap<>();
        repository = new CompanyRepository();
    }

    public Map<Long, Boolean> getCheckMap() {
        return mCheckMap;
    }

    public void loadCompany() {
        loadingObserver.setValue(true);
        repository.getCompanies()
                .flatMap(list -> toViewItems(list))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CompanyItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<CompanyItem> companies) {
                        loadingObserver.setValue(false);
                        companyObserver.setValue(companies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadingObserver.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private ObservableSource<List<CompanyItem>> toViewItems(List<Company> list) {
        return observer -> {
            List<CompanyItem> result = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < list.size(); i ++) {
                Company company = list.get(i);
                CompanyItem item = new CompanyItem();
                item.setCompany(company);
                item.setName(company.getName());
                item.setIndex(String.valueOf(list.size() - i));
                item.setPlace(company.getCity() + "/" + company.getDistrict());
                if (company.getExitDate() == null) {
                    item.setDate(dateFormat.format(company.getEnterDate()) + " To Now");
                }
                else {
                    item.setDate(dateFormat.format(company.getEnterDate()) + " To " + dateFormat.format(company.getExitDate()));
                }
                result.add(item);
            }
            observer.onNext(result);
        };
    }

    public void deleteSelectedItems() {
        Observable.create(e -> getDaoSession().runInTx(() -> {
            executeDelete();
            e.onNext(new Object());
        }))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Object object) {
                        loadingObserver.setValue(false);
                        deleteObserver.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadingObserver.setValue(false);
                        messageObserver.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void executeDelete() {
        Iterator<Long> it = mCheckMap.keySet().iterator();
        while (it.hasNext()) {
            long companyId = it.next();
            // delete from company
            getDaoSession().getCompanyDao().deleteByKey(companyId);
            getDaoSession().getCompanyDao().detachAll();
        }
    }
}
