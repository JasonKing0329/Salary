package com.king.app.salary.page.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.king.app.salary.R;
import com.king.app.salary.base.IFragmentHolder;
import com.king.app.salary.base.adapter.BaseBindingAdapter;
import com.king.app.salary.conf.AppConfig;
import com.king.app.salary.databinding.AdapterItemLoadfromBinding;
import com.king.app.salary.databinding.FragmentLoadfromBinding;
import com.king.app.salary.utils.FileUtil;
import com.king.app.salary.view.dialog.AlertDialogFragment;
import com.king.app.salary.view.dialog.DraggableContentFragment;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/10/16 9:52
 */
public class LoadFromFragment extends DraggableContentFragment<FragmentLoadfromBinding> {

    private List<File> list;

    private ItemAdapter itemAdapter;

    private OnDatabaseChangedListener onDatabaseChangedListener;

    @Override
    protected void bindFragmentHolder(IFragmentHolder holder) {

    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_loadfrom;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvList.setLayoutManager(manager);

        mBinding.tvOk.setOnClickListener(v -> confirmLoadFrom());
        loadData();
    }

    public void setOnDatabaseChangedListener(OnDatabaseChangedListener onDatabaseChangedListener) {
        this.onDatabaseChangedListener = onDatabaseChangedListener;
    }

    private void loadData() {
        File file = new File(AppConfig.APP_DIR_HISTORY);
        list = Arrays.asList(file.listFiles());

        itemAdapter = new ItemAdapter();
        itemAdapter.setList(list);
        mBinding.rvList.setAdapter(itemAdapter);
    }

    private void confirmLoadFrom() {
        if (itemAdapter.getSelection() != -1) {
            final File file = list.get(itemAdapter.getSelection());
            new AlertDialogFragment()
                    .setMessage(getString(R.string.load_from_warning_msg))
                    .setPositiveText(getString(R.string.ok))
                    .setPositiveListener((dialogInterface, i) -> {
                        FileUtil.replaceDatabase(file);
                        dismissAllowingStateLoss();
                        if (onDatabaseChangedListener != null) {
                            onDatabaseChangedListener.onDatabaseChanged();
                        }
                    })
                    .setNegativeText(getString(R.string.cancel))
                    .show(getChildFragmentManager(), "AlertDialogFragment");
        }
    }

    private class ItemAdapter extends BaseBindingAdapter<AdapterItemLoadfromBinding, File> {

        private int selection = -1;

        public int getSelection() {
            return selection;
        }

        @Override
        protected int getItemLayoutRes() {
            return R.layout.adapter_item_loadfrom;
        }

        public void setSelection(int selection) {
            this.selection = selection;
        }

        @Override
        protected void onBindItem(AdapterItemLoadfromBinding binding, int position, File bean) {
            binding.tvName.setText(bean.getName());
            if (position == selection) {
                binding.groupItem.setBackgroundColor(getResources().getColor(R.color.text_sub));
            }
            else {
                binding.groupItem.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }

        @Override
        protected void onClickItem(View v, int position, File item) {
            int lastPosition = selection;
            selection = position;
            if (lastPosition != -1) {
                notifyItemChanged(lastPosition);
            }
            notifyItemChanged(selection);
        }
    }

    public interface OnDatabaseChangedListener {
        void onDatabaseChanged();
    }
}
