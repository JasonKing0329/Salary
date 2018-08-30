package com.king.app.salary.page.salary;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.king.app.salary.R;
import com.king.app.salary.base.IFragmentHolder;
import com.king.app.salary.base.MvvmFragment;
import com.king.app.salary.databinding.FragmentSalaryListBinding;

import java.util.List;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:34
 */
public class SalaryListFragment extends MvvmFragment<FragmentSalaryListBinding, SalaryListViewModel> {

    private SalaryDetailAdapter salaryAdapter;

    @Override
    protected void bindFragmentHolder(IFragmentHolder holder) {

    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_salary_list;
    }

    @Override
    protected SalaryListViewModel createViewModel() {
        return ViewModelProviders.of(this).get(SalaryListViewModel.class);
    }

    @Override
    protected void onCreate(View view) {
        mBinding.rvItems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onCreateData() {
        mModel.salaryObserver.observe(this, salaries -> showSalaries(salaries));
    }

    private void showSalaries(List<Object> salaries) {
        if (salaryAdapter == null) {
            salaryAdapter.setList(salaries);
            mBinding.rvItems.setAdapter(salaryAdapter);
        }
        else {
            salaryAdapter.setList(salaries);
            salaryAdapter.notifyDataSetChanged();
        }
    }

    public void refresh() {
        mModel.loadSalary();
    }
}
