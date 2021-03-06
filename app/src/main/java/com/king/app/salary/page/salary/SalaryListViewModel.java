package com.king.app.salary.page.salary;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.king.app.salary.base.BaseViewModel;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.entity.SalaryDao;
import com.king.app.salary.model.entity.SalaryDetailDao;
import com.king.app.salary.model.repository.SalaryRepository;
import com.king.app.salary.utils.FormatUtil;

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
 * @date: 2018/8/29 16:30
 */
public class SalaryListViewModel extends BaseViewModel {

    public MutableLiveData<List<Object>> salaryObserver = new MutableLiveData<>();

    public MutableLiveData<Boolean> deleteObserver = new MutableLiveData<>();

    public ObservableField<String> countReceiveText = new ObservableField<>();
    public ObservableField<String> countSubText = new ObservableField<>();

    private SalaryRepository repository;

    private Map<Long, Boolean> mCheckMap;

    private SimpleDateFormat dateFormat;

    public SalaryListViewModel(@NonNull Application application) {
        super(application);
        repository = new SalaryRepository();
        mCheckMap = new HashMap<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public Map<Long, Boolean> getCheckMap() {
        return mCheckMap;
    }

    public void loadSalary() {
        loadingObserver.setValue(true);
        repository.getSalaries()
                .flatMap(list -> count(list))
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
                        salaryObserver.setValue(salaries);
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

    private ObservableSource<List<Salary>> count(List<Salary> list) {
        return observer -> {
            float sumReceive = 0;
            float sumFund = 0;
            float sumHealth = 0;
            for (int i = 0; i < list.size(); i ++) {
                Salary salary = list.get(i);
                sumReceive += salary.getReceive();
                if (salary.getDetail() != null) {
                    sumFund += salary.getDetail().getHousingFund();
                    sumHealth += salary.getDetail().getInsuranceHealth();
                }
            }
            sumFund = sumFund * 2;
            countReceiveText.set(FormatUtil.formatPrice(sumReceive));
            countSubText.set("医保" + FormatUtil.formatPrice(sumHealth) + " 公积金" + FormatUtil.formatPrice(sumFund));
            observer.onNext(list);
        };
    }

    private ObservableSource<List<Object>> toViewItems(List<Salary> list) {
        return observer -> {
            List<Object> results = new ArrayList<>();
            // list is already ordered by year
            int lastYear = 0;
            SalaryTitle title = null;
            for (Salary salary:list) {
                if (salary.getYear() != lastYear) {
                    // count for year
                    if (title != null) {
                        title.setTotalString(FormatUtil.formatPrice(title.getTotal()));
                    }
                    title = createTitle(salary.getYear());
                    results.add(title);
                    lastYear = salary.getYear();
                }
                title.setTotal(title.getTotal() + salary.getReceive());
                results.add(createDetailViewItem(salary));
            }
            // count for year
            if (title != null) {
                title.setTotalString(FormatUtil.formatPrice(title.getTotal()));
            }
            observer.onNext(results);
        };
    }

    private SalaryTitle createTitle(int year) {
        SalaryTitle title = new SalaryTitle();
        title.setYear(String.valueOf(year));
        return title;
    }

    private SalaryDetailViewItem createDetailViewItem(Salary salary) {
        SalaryDetailViewItem item = new SalaryDetailViewItem();
        item.setSalary(salary);
        item.setReceive(FormatUtil.formatPrice(salary.getReceive()));
        item.setTotal(FormatUtil.formatPrice(salary.getTotal()));
        item.setDeduction("-" + FormatUtil.formatPrice(salary.getDeduction()));
        item.setMonth(salary.getMonth() +"月");
        if (salary.getCompany() != null) {
            item.setCompany(salary.getCompany().getName() + " " + dateFormat.format(salary.getDate()));
        }
        if (salary.getDetail() != null) {
            item.setGroup("岗位" + getDisplayMoney(salary.getDetail().getBasic()) +" + 技能" + getDisplayMoney(salary.getDetail().getTech())
                +" + 绩效" + getDisplayMoney(salary.getDetail().getPerformance()));

            StringBuffer buffer = new StringBuffer();
            if (salary.getDetail().getSupply() > 0) {
                buffer.append(" 绩效补发/工资/补发").append(getDisplayMoney(salary.getDetail().getSupply()));
            }
            if (salary.getDetail().getAllowancePhone() > 0) {
                buffer.append(" 电话").append(getDisplayMoney(salary.getDetail().getAllowancePhone()));
            }
            if (salary.getDetail().getAllowanceFood() > 0) {
                buffer.append(" 餐补").append(getDisplayMoney(salary.getDetail().getAllowanceFood()));
            }
            if (salary.getDetail().getAllowanceSeason() > 0) {
                buffer.append(" 防暑降温/采暖补贴").append(getDisplayMoney(salary.getDetail().getAllowanceSeason()));
            }
            if (salary.getDetail().getOvertime() > 0) {
                buffer.append(" 值班费/加班费/其它").append(getDisplayMoney(salary.getDetail().getOvertime()));
            }
            String add = buffer.toString();
            if (add.length() > 1) {
                add = add.substring(1);
            }
            item.setAdd(add);

            buffer = new StringBuffer();
            if (salary.getDetail().getAbsence() > 0) {
                buffer.append(" 事假/病假扣款/旷工").append(getDisplayMoney(salary.getDetail().getAbsence()));
            }
            if (salary.getDetail().getTax() > 0) {
                buffer.append(" 个税").append(getDisplayMoney(salary.getDetail().getTax()));
            }
            if (salary.getDetail().getTax() > 0) {
                buffer.append(" 应纳税工资总额").append(getDisplayMoney(salary.getDetail().getTotalToTax()));
            }
            if (salary.getDetail().getInsurancePension() > 0) {
                buffer.append(" 养老保险").append(getDisplayMoney(salary.getDetail().getInsurancePension()));
            }
            if (salary.getDetail().getInsuranceJobless() > 0) {
                buffer.append(" 失业保险").append(getDisplayMoney(salary.getDetail().getInsuranceJobless()));
            }
            if (salary.getDetail().getInsuranceHealth() > 0) {
                buffer.append(" 医疗保险").append(getDisplayMoney(salary.getDetail().getInsuranceHealth()));
            }
            if (salary.getDetail().getHousingFund() > 0) {
                buffer.append(" 公积金").append(getDisplayMoney(salary.getDetail().getHousingFund()));
            }
            if (salary.getDetail().getInsuranceHugeMedical() > 0) {
                buffer.append(" 大额医疗").append(getDisplayMoney(salary.getDetail().getInsuranceHugeMedical()));
            }
            if (salary.getDetail().getExtraDrop() > 0) {
                if (TextUtils.isEmpty(salary.getDetail().getExtraDropDesc())) {
                    buffer.append(" 其他");
                }
                else {
                    buffer.append(" ").append(salary.getDetail().getExtraDropDesc());
                }
                buffer.append(getDisplayMoney(salary.getDetail().getExtraDrop()));
            }
            String minus = buffer.toString();
            if (minus.length() > 1) {
                minus = minus.substring(1);
            }
            item.setMinus(minus);
        }
        return item;
    }
    
    private String getDisplayMoney(float money) {
        return FormatUtil.formatFloat(money);
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
            long salaryId = it.next();
            Salary salary = getDaoSession().getSalaryDao().queryBuilder()
                    .where(SalaryDao.Properties.Id.eq(salaryId))
                    .build().unique();
            if (salary != null) {
                // delete from salary_detail
                getDaoSession().getSalaryDetailDao().queryBuilder()
                    .where(SalaryDetailDao.Properties.Id.eq(salary.getDetailId()))
                    .buildDelete()
                    .executeDeleteWithoutDetachingEntities();
                getDaoSession().getSalaryDetailDao().detachAll();
            }
            // delete from salary
            getDaoSession().getSalaryDao().deleteByKey(salaryId);
            getDaoSession().getSalaryDao().detachAll();
        }
    }
}
