package com.king.app.salary.page.company;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.king.app.salary.base.BaseViewModel;
import com.king.app.salary.model.entity.Company;
import com.king.app.salary.model.repository.CompanyRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 9:43
 */
public class CompanyEditorViewModel extends BaseViewModel {

    public MutableLiveData<Company> updateObserver = new MutableLiveData<>();

    private CompanyRepository repository;

    public CompanyEditorViewModel(@NonNull Application application) {
        super(application);
        repository = new CompanyRepository();
    }

    public void insertOrUpdate(Company company) {
        repository.insertOrUpdate(company)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Company>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Company company1) {
                        messageObserver.setValue("Success");
                        updateObserver.setValue(company1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
