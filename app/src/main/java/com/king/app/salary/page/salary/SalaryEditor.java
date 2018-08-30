package com.king.app.salary.page.salary;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.king.app.salary.R;
import com.king.app.salary.base.IFragmentHolder;
import com.king.app.salary.databinding.FragmentContentSalaryEditorBinding;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.entity.SalaryDetail;
import com.king.app.salary.model.params.SalaryType;
import com.king.app.salary.page.company.CompanyActivity;
import com.king.app.salary.utils.FormatUtil;
import com.king.app.salary.view.dialog.DatePickerFragment;
import com.king.app.salary.view.dialog.DraggableContentFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 9:22
 */
public class SalaryEditor extends DraggableContentFragment<FragmentContentSalaryEditorBinding> {

    private final int REQUEST_SELECT_COMPANY = 601;

    private SalaryEditorViewModel mModel;

    private Salary mSalary;
    
    private SalaryDetail mSalaryDetail;

    private OnUpdateListener onUpdateListener;

    private long mCompanyId;

    private SimpleDateFormat dateFormat;

    /**
     * Calendar及DatePicker生成的month均为下标从0开始
     */
    private Date mDate;

    @Override
    protected void bindFragmentHolder(IFragmentHolder holder) {

    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_content_salary_editor;
    }

    @Override
    protected void initView() {
        mModel = ViewModelProviders.of(this).get(SalaryEditorViewModel.class);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        mBinding.tvCompany.setOnClickListener(view -> selectCompany());
        mBinding.tvOk.setOnClickListener(view -> onSave());
        mBinding.btnDate.setOnClickListener(view -> {
            DatePickerFragment picker = new DatePickerFragment();
            if (mDate != null) {
                picker.setDate(dateFormat.format(mDate));
            }
            // DatePicker生成的month下标从0开始
            picker.setOnDateSetListener((view1, year, month, dayOfMonth) -> updateDate(year, month, dayOfMonth));
            picker.show(getChildFragmentManager(), "DatePickerFragment");
        });
        
        if (mSalary != null) {
            mBinding.cbBonus.setChecked(mSalary.getType() == SalaryType.BONUS.ordinal());
            mBinding.etReceive.setText(FormatUtil.formatFloat(mSalary.getReceive()));
            mBinding.etTotal.setText(FormatUtil.formatFloat(mSalary.getTotal()));

            mDate = mSalary.getDate();
            mBinding.btnDate.setText(dateFormat.format(mDate));

            if (mSalary.getCompany() != null) {
                mCompanyId = mSalary.getCompanyId();
                mBinding.tvCompany.setText(mSalary.getCompany().getName());
            }
            if (mSalary.getDetail() != null) {
                mSalaryDetail = mSalary.getDetail();
                mBinding.etBasic.setText(FormatUtil.formatFloat(mSalaryDetail.getBasic()));
                mBinding.etTech.setText(FormatUtil.formatFloat(mSalaryDetail.getTech()));
                mBinding.etPerformance.setText(FormatUtil.formatFloat(mSalaryDetail.getPerformance()));
                mBinding.etSupply.setText(FormatUtil.formatFloat(mSalaryDetail.getSupply()));
                mBinding.etOvertime.setText(FormatUtil.formatFloat(mSalaryDetail.getOvertime()));
                mBinding.etAllowancePhone.setText(FormatUtil.formatFloat(mSalaryDetail.getAllowancePhone()));
                mBinding.etAllowanceFood.setText(FormatUtil.formatFloat(mSalaryDetail.getAllowanceFood()));
                mBinding.etAllowanceSeason.setText(FormatUtil.formatFloat(mSalaryDetail.getAllowanceSeason()));
                mBinding.etTax.setText(FormatUtil.formatFloat(mSalaryDetail.getTax()));
                mBinding.etTotalToTax.setText(FormatUtil.formatFloat(mSalaryDetail.getTotalToTax()));
                mBinding.etInsuranceHealth.setText(FormatUtil.formatFloat(mSalaryDetail.getInsuranceHealth()));
                mBinding.etInsuranceJobless.setText(FormatUtil.formatFloat(mSalaryDetail.getInsuranceJobless()));
                mBinding.etInsuranceHugeMedical.setText(FormatUtil.formatFloat(mSalaryDetail.getInsuranceHugeMedical()));
                mBinding.etInsurancePension.setText(FormatUtil.formatFloat(mSalaryDetail.getInsurancePension()));
                mBinding.etHousingFund.setText(FormatUtil.formatFloat(mSalaryDetail.getHousingFund()));
                mBinding.etReceiveAllowanceless.setText(FormatUtil.formatFloat(mSalaryDetail.getReceiveAllowanceLess()));
                mBinding.etOtherRaise.setText(FormatUtil.formatFloat(mSalaryDetail.getExtraRaise()));
                if (!TextUtils.isEmpty(mSalaryDetail.getExtraRaiseDesc())) {
                    mBinding.etOtherRaiseDesc.setText(mSalaryDetail.getExtraRaiseDesc());
                }
                mBinding.etOtherDrop.setText(FormatUtil.formatFloat(mSalaryDetail.getExtraDrop()));
                if (!TextUtils.isEmpty(mSalaryDetail.getExtraDropDesc())) {
                    mBinding.etOtherDropDesc.setText(mSalaryDetail.getExtraDropDesc());
                }
            }
        }

        mModel.companyObserver.observe(this, company -> mBinding.tvCompany.setText(company.getName()));
        mModel.updateObserver.observe(this, salary -> {
            if (onUpdateListener != null) {
                onUpdateListener.onUpdateSuccess(salary);
            }
            dismissAllowingStateLoss();
        });
    }

    /**
     *  @param year
     * @param month 下标从0开始
     * @param dayOfMonth
     */
    private void updateDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDate = calendar.getTime();
        mBinding.btnDate.setText(dateFormat.format(mDate));
    }

    public void setSalary(Salary salary) {
        this.mSalary = salary;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    private void selectCompany() {
        Router.build("Company")
                .with(CompanyActivity.EXTRA_SELECT_COMPANY, true)
                .requestCode(REQUEST_SELECT_COMPANY)
                .go(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_COMPANY) {
            if (resultCode == Activity.RESULT_OK) {
                mCompanyId = data.getLongExtra(CompanyActivity.RESP_COMPANY_ID, -1);
                mModel.loadCompany(mCompanyId);
            }
        }
    }

    private void onSave() {
        if (mCompanyId == 0) {
            showMessageShort("Please select company");
            return;
        }
        if (mDate == null) {
            showMessageShort("Please select date");
            return;
        }
        if (mSalary == null) {
            mSalary = new Salary();
        }
        if (mSalaryDetail == null) {
            mSalaryDetail = new SalaryDetail();
        }
        mSalary.setType(mBinding.cbBonus.isChecked() ? SalaryType.BONUS.ordinal():SalaryType.NORMAL.ordinal());
        mSalary.setReceive(getFloat(mBinding.etReceive));
        mSalary.setTotal(getFloat(mBinding.etTotal));
        mSalary.setDate(mDate);
        // 一般来说当前月发的是上个月的工资，目前仅考虑这种情况
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(mDate);
        calendar.add(GregorianCalendar.MONTH, -1);
        mSalary.setYear(calendar.get(GregorianCalendar.YEAR));
        mSalary.setMonth(calendar.get(GregorianCalendar.MONTH) + 1);
        mSalary.setCompanyId(mCompanyId);
        mSalaryDetail.setBasic(getFloat(mBinding.etBasic));
        mSalaryDetail.setTech(getFloat(mBinding.etTech));
        mSalaryDetail.setPerformance(getFloat(mBinding.etPerformance));
        mSalaryDetail.setSupply(getFloat(mBinding.etSupply));
        mSalaryDetail.setOvertime(getFloat(mBinding.etOvertime));
        mSalaryDetail.setAllowanceFood(getFloat(mBinding.etAllowanceFood));
        mSalaryDetail.setAllowancePhone(getFloat(mBinding.etAllowancePhone));
        mSalaryDetail.setAllowanceSeason(getFloat(mBinding.etAllowanceSeason));
        mSalaryDetail.setTax(getFloat(mBinding.etTax));
        mSalaryDetail.setTotalToTax(getFloat(mBinding.etTotalToTax));
        mSalaryDetail.setInsuranceHealth(getFloat(mBinding.etInsuranceHealth));
        mSalaryDetail.setInsuranceJobless(getFloat(mBinding.etInsuranceJobless));
        mSalaryDetail.setInsuranceHugeMedical(getFloat(mBinding.etInsuranceHugeMedical));
        mSalaryDetail.setInsurancePension(getFloat(mBinding.etInsurancePension));
        mSalaryDetail.setHousingFund(getFloat(mBinding.etHousingFund));
        mSalaryDetail.setAbsence(getFloat(mBinding.etAbsence));
        mSalaryDetail.setReceiveAllowanceLess(getFloat(mBinding.etReceiveAllowanceless));
        mSalaryDetail.setExtraRaise(getFloat(mBinding.etOtherRaise));
        mSalaryDetail.setExtraRaiseDesc(mBinding.etOtherRaiseDesc.getText().toString());
        mSalaryDetail.setExtraDrop(getFloat(mBinding.etOtherDrop));
        mSalaryDetail.setExtraDropDesc(mBinding.etOtherDropDesc.getText().toString());

        mSalary.setDeduction(mSalary.getTotal() - mSalary.getReceive());

        mModel.insertOrUpdate(mSalary, mSalaryDetail);
    }

    public interface OnUpdateListener {
        void onUpdateSuccess(Salary salary);
    }

    public float getFloat(EditText editText) {
        float result = 0;
        try {
            result = Float.parseFloat(editText.getText().toString());
        } catch (Exception e) {}
        return result;
    }
}
