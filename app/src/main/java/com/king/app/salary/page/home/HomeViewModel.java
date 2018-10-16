package com.king.app.salary.page.home;

import android.app.Application;
import android.support.annotation.NonNull;

import com.king.app.salary.base.BaseViewModel;
import com.king.app.salary.utils.DBExporter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 17:20
 */
public class HomeViewModel extends BaseViewModel {
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveDatabase() {
        copyDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        messageObserver.setValue("Save success");
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

    private Observable<Boolean> copyDatabase() {
        return Observable.create(e -> {
            DBExporter.exportAsHistory();
            e.onNext(true);
        });
    }
}
