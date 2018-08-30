package com.king.app.salary.page.company;

import android.view.View;

import com.king.app.salary.R;
import com.king.app.salary.base.adapter.BaseBindingAdapter;
import com.king.app.salary.databinding.AdapterCompanyBinding;

import java.util.Map;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/30 10:59
 */
public class CompanyAdapter extends BaseBindingAdapter<AdapterCompanyBinding, CompanyItem> {

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
    protected int getItemLayoutRes() {
        return R.layout.adapter_company;
    }

    @Override
    protected void onBindItem(AdapterCompanyBinding binding, int position, CompanyItem bean) {
        binding.setModel(bean);
        binding.cbCheck.setVisibility(selectMode ? View.VISIBLE:View.GONE);
        if (mCheckMap.get(bean.getCompany().getId()) == null) {
            binding.cbCheck.setChecked(false);
        }
        else {
            binding.cbCheck.setChecked(true);
        }
    }

    @Override
    protected void onClickItem(View view, int position, CompanyItem item) {
        if (selectMode) {
            Boolean check = mCheckMap.get(item.getCompany().getId());
            if (check == null) {
                mCheckMap.put(item.getCompany().getId(), true);
            }
            else {
                mCheckMap.remove(item.getCompany().getId());
            }
            notifyItemChanged(position);
        }
        else {
            super.onClickItem(view, position, item);
        }
    }
}
