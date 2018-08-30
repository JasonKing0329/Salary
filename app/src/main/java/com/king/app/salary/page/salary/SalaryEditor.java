package com.king.app.salary.page.salary;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.EditText;

import com.king.app.salary.R;
import com.king.app.salary.base.IFragmentHolder;
import com.king.app.salary.databinding.FragmentContentSalaryEditorBinding;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.entity.SalaryDetail;
import com.king.app.salary.utils.FormatUtil;
import com.king.app.salary.view.dialog.DraggableContentFragment;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 9:22
 */
public class SalaryEditor extends DraggableContentFragment<FragmentContentSalaryEditorBinding> {

    private SalaryEditorViewModel mModel;

    private Salary mSalary;
    
    private SalaryDetail mSalaryDetail;

    private OnUpdateListener onUpdateListener;

    private long mCompanyId;

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

        mBinding.tvCompany.setOnClickListener(view -> selectCompany());
        mBinding.tvOk.setOnClickListener(view -> onSave());
        
        if (mSalary != null) {
            mBinding.etReceive.setText(FormatUtil.formatFloat(mSalary.getReceive()));
            mBinding.etTotal.setText(FormatUtil.formatFloat(mSalary.getTotal()));
            mBinding.spMonth.setSelection(mSalary.getMonth() - 1);
            if (mSalary.getCompany() != null) {
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
            }
        }

        mModel.updateObserver.observe(this, salary -> {
            if (onUpdateListener != null) {
                onUpdateListener.onUpdateSuccess(salary);
            }
            dismissAllowingStateLoss();
        });
    }

    public void setSalary(Salary salary) {
        this.mSalary = salary;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    private void selectCompany() {
        
    }

    private void onSave() {
        if (mCompanyId == 0) {
            showMessageShort("Please select company");
            return;
        }
        if (mSalary == null) {
            mSalary = new Salary();
        }
        if (mSalaryDetail == null) {
            mSalaryDetail = new SalaryDetail();
        }
        mSalary.setReceive(getFloat(mBinding.etReceive));
        mSalary.setTotal(getFloat(mBinding.etTotal));
        mSalary.setMonth(mBinding.spMonth.getSelectedItemPosition() + 1);
        mSalary.setCompanyId(mCompanyId);
        mSalaryDetail.setSupply(getFloat(mBinding.etReceive));
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
