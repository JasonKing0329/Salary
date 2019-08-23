package com.king.app.salary.page.company;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chenenyu.router.annotation.Route;
import com.king.app.jactionbar.OnConfirmListener;
import com.king.app.salary.R;
import com.king.app.salary.base.MvvmActivity;
import com.king.app.salary.databinding.ActivityCompanyBinding;
import com.king.app.salary.model.entity.Company;
import com.king.app.salary.utils.ScreenUtils;
import com.king.app.salary.view.dialog.DraggableDialogFragment;

import java.util.List;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 17:20
 */
@Route("Company")
public class CompanyActivity extends MvvmActivity<ActivityCompanyBinding, CompanyViewModel> {

    public static final String EXTRA_SELECT_COMPANY = "select_company";
    public static final String RESP_COMPANY_ID = "company_id";

    private CompanyAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_company;
    }

    @Override
    protected void initView() {
        mBinding.actionbar.setOnBackListener(() -> finish());
        mBinding.actionbar.setOnMenuItemListener(menuId -> {
            switch (menuId) {
                case R.id.menu_add:
                    editCompany(null);
                    break;
                case R.id.menu_delete:
                    if (adapter != null) {
                        adapter.setSelectMode(true);
                        adapter.notifyDataSetChanged();
                    }
                    mBinding.actionbar.showConfirmStatus(menuId);
                    break;
                case R.id.menu_edit:
                    break;
            }
        });
        mBinding.actionbar.setOnConfirmListener(actionId -> {
            switch (actionId) {
                case R.id.menu_delete:
                    if (adapter != null) {
                        adapter.setSelectMode(true);
                        adapter.notifyDataSetChanged();
                    }
                    mModel.deleteSelectedItems();
                    break;
            }
            return false;
        });
        mBinding.actionbar.setOnCancelListener(actionId -> {
            if (adapter != null) {
                adapter.setSelectMode(false);
                adapter.notifyDataSetChanged();
            }
            return true;
        });
        mBinding.rvItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.rvItems.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = ScreenUtils.dp2px(10);
            }
        });
    }

    @Override
    protected CompanyViewModel createViewModel() {
        return ViewModelProviders.of(this).get(CompanyViewModel.class);
    }

    @Override
    protected void initData() {
        mModel.companyObserver.observe(this, list -> showCompanies(list));
        mModel.deleteObserver.observe(this, deleted -> {
            mBinding.actionbar.cancelConfirmStatus();
            mModel.loadCompany();
        });
        mModel.loadCompany();
    }

    private void showCompanies(List<CompanyItem> list) {
        if (adapter == null) {
            adapter = new CompanyAdapter();
            adapter.setList(list);
            adapter.setCheckMap(mModel.getCheckMap());
            adapter.setOnItemClickListener((view, position, data) -> onClickCompany(data));
            mBinding.rvItems.setAdapter(adapter);
        }
        else {
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
    }

    private void onClickCompany(CompanyItem data) {
        if (isSelectCompany()) {
            Intent intent = new Intent();
            intent.putExtra(RESP_COMPANY_ID, data.getCompany().getId());
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            editCompany(data.getCompany());
        }
    }

    private void editCompany(Company company) {
        CompanyEditor editor = new CompanyEditor();
        editor.setCompany(company);
        editor.setOnUpdateListener(salary -> mModel.loadCompany());
        DraggableDialogFragment dialogFragment = new DraggableDialogFragment();
        dialogFragment.setTitle("Add company");
        dialogFragment.setContentFragment(editor);
        dialogFragment.show(getSupportFragmentManager(), "CompanyEditor");
    }

    private boolean isSelectCompany() {
        return getIntent().getBooleanExtra(EXTRA_SELECT_COMPANY, false);
    }
}
