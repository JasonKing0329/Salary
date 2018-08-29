package com.king.app.salary.view.dialog;

import android.databinding.ViewDataBinding;

import com.king.app.salary.base.BaseBindingFragment;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/6/7 10:05
 */
public abstract class DraggableContentFragment<T extends ViewDataBinding> extends BaseBindingFragment<T> {

    private DraggableHolder dialogHolder;

    public void setDialogHolder(DraggableHolder dialogHolder) {
        this.dialogHolder = dialogHolder;
    }

    protected void dismiss() {
        dialogHolder.dismiss();
    }

    protected void dismissAllowingStateLoss() {
        dialogHolder.dismissAllowingStateLoss();
    }
}
