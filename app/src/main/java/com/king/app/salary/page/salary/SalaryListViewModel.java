package com.king.app.salary.page.salary;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.king.app.salary.base.BaseViewModel;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.repository.SalaryRepository;
import com.king.app.salary.utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:30
 */
public class SalaryListViewModel extends BaseViewModel {

    public MutableLiveData<List<Object>> salaryObserver = new MutableLiveData<>();

    private SalaryRepository repository;

    public SalaryListViewModel(@NonNull Application application) {
        super(application);
        repository = new SalaryRepository();
    }

    public void loadSalary() {
        loadingObserver.setValue(true);
        repository.getSalaries()
                .flatMap(list -> toViewItems(list))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<Object> salaries) {
                        loadingObserver.setValue(false);
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

    private ObservableSource<List<Object>> toViewItems(List<Salary> list) {
        return observer -> {
            List<Object> results = new ArrayList<>();
            // list is already ordered by year
            int lastYear = 0;
            for (Salary salary:list) {
                if (salary.getYear() != lastYear) {
                    results.add(createTitle(salary.getYear()));
                }
                results.add(createDetailViewItem(salary));
            }
        };
    }

    private SalaryTitle createTitle(int year) {
        SalaryTitle title = new SalaryTitle();
        title.setYear(String.valueOf(year));
        return title;
    }

    private SalaryDetailViewItem createDetailViewItem(Salary salary) {
        SalaryDetailViewItem item = new SalaryDetailViewItem();
        item.setReceive(FormatUtil.formatPrice(salary.getReceive()));
        item.setTotal(FormatUtil.formatPrice(salary.getTotal()));
        item.setDeduction(FormatUtil.formatPrice(salary.getDeduction()));
        item.setMonth(salary.getMonth() + "月");
        if (salary.getCompany() != null) {
            item.setCompany(salary.getCompany().getName());
        }
        if (salary.getDetail() != null) {
            item.setGroup("(岗位)" + salary.getDetail().getBasic() + " + (技能)" + salary.getDetail().getTech()
                + " + (绩效)" + salary.getDetail().getPerformance());

            StringBuffer buffer = new StringBuffer();
            if (salary.getDetail().getSupply() > 0) {
                buffer.append(" (绩效补发/工资/补发)").append(salary.getDetail().getSupply());
            }
            if (salary.getDetail().getAllowancePhone() > 0) {
                buffer.append(" (电话)").append(salary.getDetail().getAllowancePhone());
            }
            if (salary.getDetail().getAllowanceFood() > 0) {
                buffer.append(" (餐补)").append(salary.getDetail().getAllowanceFood());
            }
            if (salary.getDetail().getAllowanceSeason() > 0) {
                buffer.append(" (防暑降温/采暖补贴)").append(salary.getDetail().getAllowanceSeason());
            }
            if (salary.getDetail().getOvertime() > 0) {
                buffer.append(" (值班费/加班费/其它)").append(salary.getDetail().getOvertime());
            }
            item.setAdd(buffer.toString());

            buffer = new StringBuffer();
            if (salary.getDetail().getAbsence() > 0) {
                buffer.append(" (事假/病假扣款/旷工)").append(salary.getDetail().getAbsence());
            }
            if (salary.getDetail().getTax() > 0) {
                buffer.append(" (个税，应纳税工资总额").append(salary.getDetail().getTotalToTax()).append(")").append(salary.getDetail().getTax());
            }
            if (salary.getDetail().getInsurancePension() > 0) {
                buffer.append(" (养老保险)").append(salary.getDetail().getInsurancePension());
            }
            if (salary.getDetail().getInsuranceJobless() > 0) {
                buffer.append(" (失业保险)").append(salary.getDetail().getInsuranceJobless());
            }
            if (salary.getDetail().getInsuranceHealth() > 0) {
                buffer.append(" (医疗保险)").append(salary.getDetail().getInsuranceHealth());
            }
            if (salary.getDetail().getHousingFund() > 0) {
                buffer.append(" (公积金)").append(salary.getDetail().getHousingFund());
            }
            if (salary.getDetail().getInsuranceHugeMedical() > 0) {
                buffer.append(" (大额医疗)").append(salary.getDetail().getInsuranceHugeMedical());
            }
        }
        return item;
    }
}
