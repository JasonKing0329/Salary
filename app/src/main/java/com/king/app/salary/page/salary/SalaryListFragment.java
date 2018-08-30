package com.king.app.salary.page.salary;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.king.app.salary.R;
import com.king.app.salary.base.IFragmentHolder;
import com.king.app.salary.base.MvvmFragment;
import com.king.app.salary.base.adapter.HeadChildBindingAdapter;
import com.king.app.salary.databinding.FragmentSalaryListBinding;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.utils.ScreenUtils;
import com.king.app.salary.view.dialog.DraggableDialogFragment;

import java.util.List;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 16:34
 */
public class SalaryListFragment extends MvvmFragment<FragmentSalaryListBinding, SalaryListViewModel> {

    private SalaryDetailAdapter salaryAdapter;

    private SalaryListHolder holder;

    @Override
    protected void bindFragmentHolder(IFragmentHolder holder) {
        if (holder instanceof SalaryListHolder) {
            this.holder = (SalaryListHolder) holder;
        }
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
        mBinding.rvItems.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (salaryAdapter.isHead(position) && position != 0) {
                    outRect.top = ScreenUtils.dp2px(10);
                }
                else {
                    outRect.top = 0;
                }
            }
        });
    }

    @Override
    protected void onCreateData() {
        mModel.salaryObserver.observe(this, salaries -> showSalaries(salaries));
        mModel.deleteObserver.observe(this, deleted -> {
            holder.notifyDeleteFinished();
            mModel.loadSalary();
        });
        mModel.loadSalary();
    }

    private void showSalaries(List<Object> salaries) {
        if (salaryAdapter == null) {
            salaryAdapter = new SalaryDetailAdapter();
            salaryAdapter.setList(salaries);
            salaryAdapter.setOnHeadChildListener(new HeadChildBindingAdapter.OnHeadChildListener<SalaryTitle, SalaryDetailViewItem>() {
                @Override
                public void onClickHead(View view, int position, SalaryTitle item) {

                }

                @Override
                public void onClickItem(View view, int position, SalaryDetailViewItem item) {
                    editSalary(item.getSalary());
                }
            });
            salaryAdapter.setCheckMap(mModel.getCheckMap());
            mBinding.rvItems.setAdapter(salaryAdapter);
        }
        else {
            salaryAdapter.setList(salaries);
            salaryAdapter.notifyDataSetChanged();
        }
    }

    private void editSalary(Salary salary) {
        SalaryEditor editor = new SalaryEditor();
        editor.setSalary(salary);
        editor.setOnUpdateListener(result -> refresh());
        DraggableDialogFragment dialogFragment = new DraggableDialogFragment();
        dialogFragment.setTitle("Add salary");
        dialogFragment.setMaxHeight(ScreenUtils.getScreenHeight() * 3 / 4);
        dialogFragment.setContentFragment(editor);
        dialogFragment.show(getChildFragmentManager(), "SalaryEditor");
    }

    public void refresh() {
        mModel.loadSalary();
    }

    public void setSelectionMode(boolean selection) {
        if (salaryAdapter != null) {
            salaryAdapter.setSelectMode(selection);
            salaryAdapter.notifyDataSetChanged();
        }
    }

    public void deleteSelectedItems() {
        showConfirmCancelMessage("Delete this item will delete all related items in database, continue?"
                , (dialog, which) -> mModel.deleteSelectedItems(), null);
    }
}
