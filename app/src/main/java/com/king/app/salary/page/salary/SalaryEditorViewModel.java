package com.king.app.salary.page.salary;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.king.app.salary.base.BaseViewModel;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.entity.SalaryDetail;
import com.king.app.salary.model.repository.SalaryRepository;

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
public class SalaryEditorViewModel extends BaseViewModel {

    public MutableLiveData<Salary> updateObserver = new MutableLiveData<>();

    private SalaryRepository repository;

    public SalaryEditorViewModel(@NonNull Application application) {
        super(application);
        repository = new SalaryRepository();
    }

    public void insertOrUpdate(Salary salary, SalaryDetail salaryDetail) {
        repository.insertOrUpdateSalaryDetail(salaryDetail)
                .flatMap(detail -> {
                    salary.setDetailId(detail.getId());
                    return repository.insertOrUpdateSalary(salary);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Salary>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Salary salary) {
                        messageObserver.setValue("Success");
                        updateObserver.setValue(salary);
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
