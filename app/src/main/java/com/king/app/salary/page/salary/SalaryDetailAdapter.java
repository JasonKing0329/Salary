package com.king.app.salary.page.salary;

import android.graphics.Color;
import android.view.View;

import com.king.app.salary.R;
import com.king.app.salary.base.adapter.HeadChildBindingAdapter;
import com.king.app.salary.databinding.AdapterSalaryDetailBinding;
import com.king.app.salary.databinding.AdapterSalaryTitleBinding;
import com.king.app.salary.model.params.SalaryType;
import com.king.app.salary.page.salary.SalaryDetailViewItem;
import com.king.app.salary.page.salary.SalaryTitle;

import java.util.Map;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:26
 */
public class SalaryDetailAdapter extends HeadChildBindingAdapter<AdapterSalaryTitleBinding, AdapterSalaryDetailBinding, SalaryTitle, SalaryDetailViewItem> {

    private boolean selectMode;

    private Map<Long, Boolean> mCheckMap;

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
        if (selectMode) {
            mCheckMap.clear();
        }
    }

    public void setCheckMap(Map<Long, Boolean> mCheckMap) {
        this.mCheckMap = mCheckMap;
    }

    @Override
    protected Class getItemClass() {
        return SalaryDetailViewItem.class;
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
        binding.cbCheck.setVisibility(selectMode ? View.VISIBLE:View.GONE);
        if (mCheckMap.get(item.getSalary().getId()) == null) {
            binding.cbCheck.setChecked(false);
        }
        else {
            binding.cbCheck.setChecked(true);
        }

        binding.tvDeduction.setVisibility(item.getSalary().getDeduction() == 0 ? View.GONE:View.VISIBLE);

        if (item.getSalary().getType() == SalaryType.BONUS.ordinal()) {
            binding.getRoot().setBackgroundColor(Color.parseColor("#f9ebed"));
            binding.llAdd.setVisibility(View.GONE);
            binding.tvGroup.setVisibility(View.GONE);

        }
        else {
            binding.getRoot().setBackgroundColor(Color.WHITE);
            binding.llAdd.setVisibility(View.VISIBLE);
            binding.tvGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onClickItem(View view, int position, SalaryDetailViewItem item) {
        if (selectMode) {
            Boolean check = mCheckMap.get(item.getSalary().getId());
            if (check == null) {
                mCheckMap.put(item.getSalary().getId(), true);
            }
            else {
                mCheckMap.remove(item.getSalary().getId());
            }
            notifyItemChanged(position);
        }
        else {
            super.onClickItem(view, position, item);
        }
    }

    public boolean isHead(int position) {
        return list.get(position) instanceof SalaryTitle;
    }
}
