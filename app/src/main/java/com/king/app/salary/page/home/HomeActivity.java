package com.king.app.salary.page.home;

import android.arch.lifecycle.ViewModelProviders;

import com.chenenyu.router.annotation.Route;
import com.king.app.jactionbar.OnConfirmListener;
import com.king.app.salary.R;
import com.king.app.salary.base.MvvmActivity;
import com.king.app.salary.base.SalaryApplication;
import com.king.app.salary.databinding.ActivityHomeBinding;
import com.king.app.salary.page.salary.SalaryEditor;
import com.king.app.salary.page.salary.SalaryListFragment;
import com.king.app.salary.page.salary.SalaryListHolder;
import com.king.app.salary.utils.ScreenUtils;
import com.king.app.salary.view.dialog.DraggableDialogFragment;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 17:20
 */
@Route("Home")
public class HomeActivity extends MvvmActivity<ActivityHomeBinding, HomeViewModel> implements SalaryListHolder {

    private SalaryListFragment ftSalaryList;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        mBinding.actionbar.setOnBackListener(() -> finish());
        mBinding.actionbar.setOnMenuItemListener(menuId -> {
            switch (menuId) {
                case R.id.menu_add:
                    addSalary();
                    break;
                case R.id.menu_delete:
                    ftSalaryList.setSelectionMode(true);
                    mBinding.actionbar.showConfirmStatus(menuId);
                    break;
                case R.id.menu_edit:
                    break;
                case R.id.menu_load_from:
                    showLoadFrom();
                    break;
                case R.id.menu_save:
                    mModel.saveDatabase();
                    break;
            }
        });
        mBinding.actionbar.setOnConfirmListener(new OnConfirmListener() {
            @Override
            public boolean disableInstantDismissConfirm() {
                return true;
            }

            @Override
            public boolean disableInstantDismissCancel() {
                return false;
            }

            @Override
            public boolean onConfirm(int actionId) {
                switch (actionId) {
                    case R.id.menu_delete:
                        ftSalaryList.deleteSelectedItems();
                        break;
                }
                return false;
            }

            @Override
            public boolean onCancel(int actionId) {
                ftSalaryList.setSelectionMode(false);
                return true;
            }
        });
    }

    private void showLoadFrom() {
        LoadFromFragment content = new LoadFromFragment();
        content.setOnDatabaseChangedListener(() -> {
            SalaryApplication.getInstance().reCreateGreenDao();
            initData();
        });
        DraggableDialogFragment editDialog = new DraggableDialogFragment.Builder()
                .setTitle("Load from")
                .setShowDelete(false)
                .setContentFragment(content)
                .build();
        editDialog.show(getSupportFragmentManager(), "LoadFromFragment");
    }

    @Override
    protected HomeViewModel createViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    protected void initData() {
        ftSalaryList = new SalaryListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_ft, ftSalaryList, "SalaryListFragment")
                .commit();
    }

    private void addSalary() {
        SalaryEditor editor = new SalaryEditor();
        editor.setOnUpdateListener(salary -> ftSalaryList.refresh());
        DraggableDialogFragment dialogFragment = new DraggableDialogFragment();
        dialogFragment.setTitle("Add salary");
        dialogFragment.setMaxHeight(ScreenUtils.getScreenHeight() * 3 / 4);
        dialogFragment.setContentFragment(editor);
        dialogFragment.show(getSupportFragmentManager(), "SalaryEditor");
    }

    @Override
    public void notifyDeleteFinished() {
        mBinding.actionbar.cancelConfirmStatus();
    }
}
