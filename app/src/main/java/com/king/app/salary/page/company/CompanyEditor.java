package com.king.app.salary.page.company;

import android.arch.lifecycle.ViewModelProviders;
import android.text.TextUtils;

import com.king.app.salary.R;
import com.king.app.salary.base.IFragmentHolder;
import com.king.app.salary.databinding.FragmentContentCompanyEditorBinding;
import com.king.app.salary.model.entity.Company;
import com.king.app.salary.view.dialog.DatePickerFragment;
import com.king.app.salary.view.dialog.DraggableContentFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 9:22
 */
public class CompanyEditor extends DraggableContentFragment<FragmentContentCompanyEditorBinding> {

    private CompanyEditorViewModel mModel;

    private Company mCompany;

    private OnUpdateListener onUpdateListener;

    private SimpleDateFormat dateFormat;

    private String mDateEnter;

    private String mDateExit;

    @Override
    protected void bindFragmentHolder(IFragmentHolder holder) {

    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_content_company_editor;
    }

    @Override
    protected void initView() {
        mModel = ViewModelProviders.of(this).get(CompanyEditorViewModel.class);

        mBinding.tvOk.setOnClickListener(view -> onSave());
        mBinding.btnEnter.setOnClickListener(view -> {
            DatePickerFragment picker = new DatePickerFragment();
            picker.setDate(mDateEnter);
            picker.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mDateEnter = dateFormat.format(calendar.getTime());
                mBinding.btnEnter.setText(mDateEnter);
            });
            picker.show(getChildFragmentManager(), "DatePickerFragment");
        });
        mBinding.btnExit.setOnClickListener(view -> {
            DatePickerFragment picker = new DatePickerFragment();
            picker.setDate(mDateExit);
            picker.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mDateExit = dateFormat.format(calendar.getTime());
                mBinding.btnExit.setText(mDateExit);
            });
            picker.show(getChildFragmentManager(), "DatePickerFragment");
        });

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (mCompany != null) {
            mBinding.etName.setText(mCompany.getName());
            mBinding.etCity.setText(mCompany.getCity());
            mBinding.etDistrict.setText(mCompany.getDistrict());
            mBinding.btnEnter.setText(dateFormat.format(mCompany.getEnterDate()));
            if (mCompany.getExitDate() == null) {
                mBinding.btnExit.setText("离职日期");
            }
            else {
                mBinding.btnExit.setText(dateFormat.format(mCompany.getExitDate()));
            }
        }

        mModel.updateObserver.observe(this, salary -> {
            if (onUpdateListener != null) {
                onUpdateListener.onUpdateSuccess(salary);
            }
            dismissAllowingStateLoss();
        });
    }

    public void setCompany(Company mCompany) {
        this.mCompany = mCompany;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    private void onSave() {
        if (TextUtils.isEmpty(mDateEnter)) {
            showMessageShort("Please select enter date");
            return;
        }
        if (TextUtils.isEmpty(mDateExit)) {
            showMessageShort("Please select exit date");
            return;
        }
        if (mCompany == null) {
            mCompany = new Company();
        }
        mCompany.setName(mBinding.etName.getText().toString());
        mCompany.setCity(mBinding.etCity.getText().toString());
        mCompany.setDistrict(mBinding.etDistrict.getText().toString());
        try {
            mCompany.setEnterDate(dateFormat.parse(mDateEnter));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            mCompany.setExitDate(dateFormat.parse(mDateExit));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mModel.insertOrUpdate(mCompany);
    }

    public interface OnUpdateListener {
        void onUpdateSuccess(Company company);
    }
}
