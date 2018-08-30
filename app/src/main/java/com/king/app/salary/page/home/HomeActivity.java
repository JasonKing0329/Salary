package com.king.app.salary.page.home;

import android.arch.lifecycle.ViewModelProviders;

import com.chenenyu.router.annotation.Route;
import com.king.app.salary.R;
import com.king.app.salary.base.MvvmActivity;
import com.king.app.salary.databinding.ActivityHomeBinding;
import com.king.app.salary.page.salary.SalaryEditor;
import com.king.app.salary.page.salary.SalaryListFragment;
import com.king.app.salary.view.dialog.DraggableDialogFragment;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 17:20
 */
@Route("Home")
public class HomeActivity extends MvvmActivity<ActivityHomeBinding, HomeViewModel> {

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
                    break;
                case R.id.menu_edit:
                    break;
            }
        });
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
        dialogFragment.setContentFragment(editor);
        dialogFragment.show(getSupportFragmentManager(), "SalaryEditor");
    }
}
