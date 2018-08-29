package com.king.app.salary.view.dialog;

import com.king.app.salary.base.IFragmentHolder;

/**
 * @desc
 * @auth 景阳
 * @time 2018/3/24 0024 23:18
 */

public interface DraggableHolder extends IFragmentHolder {
    void dismiss();
    void dismissAllowingStateLoss();
}
