package com.king.app.salary.page.salary;

import com.king.app.salary.R;
import com.king.app.salary.base.adapter.HeadChildBindingAdapter;
import com.king.app.salary.databinding.AdapterSalaryDetailBinding;
import com.king.app.salary.databinding.AdapterSalaryTitleBinding;
import com.king.app.salary.page.salary.SalaryDetailViewItem;
import com.king.app.salary.page.salary.SalaryTitle;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:26
 */
public class SalaryDetailAdapter extends HeadChildBindingAdapter<AdapterSalaryTitleBinding, AdapterSalaryDetailBinding, SalaryTitle, SalaryDetailViewItem> {

    @Override
    protected Class getItemClass() {
        return SalaryTitle.class;
    }

    @Override
    protected int getHeaderRes() {
        return R.layout.adapter_salary_title;
    }

    @Override
    protected int getItemRes() {
        return R.layout.adapter_salary_detail;
    }

    @Override
    protected void onBindHead(AdapterSalaryTitleBinding binding, int position, SalaryTitle head) {
        binding.setModel(head);
    }

    @Override
    protected void onBindItem(AdapterSalaryDetailBinding binding, int position, SalaryDetailViewItem item) {
        binding.setModel(item);
    }
}
