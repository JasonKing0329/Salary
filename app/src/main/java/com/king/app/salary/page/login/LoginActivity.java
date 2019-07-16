package com.king.app.salary.page.login;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.chenenyu.router.Router;
import com.king.app.salary.R;
import com.king.app.salary.base.MvvmActivity;
import com.king.app.salary.databinding.ActivityLoginBinding;
import com.king.app.salary.model.fingerprint.samsung.SamsungFingerPrint;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginActivity extends MvvmActivity<ActivityLoginBinding, LoginViewModel> {

    private SamsungFingerPrint fingerPrint;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mBinding.setModel(mModel);
        mModel.fingerprintObserver.observe(this, aBoolean -> checkFingerprint());
        mModel.loginObserver.observe(this, success -> {
            if (success) {
                superUser();
            }
        });
    }

    @Override
    protected LoginViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected void initData() {
        new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isGranted -> {
                    if (isGranted) {
                        initCreate();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    finish();
                });
    }

    private void initCreate() {
        mModel.initCreate();
    }

    private void checkFingerprint() {
        FingerprintManagerCompat compat = FingerprintManagerCompat.from(this);
        if (compat.isHardwareDetected()) {
            if (compat.hasEnrolledFingerprints()) {
                startFingerPrintDialog();
            }
            else {
                showMessageLong("设备未注册指纹");
            }
        }
        else {
            // 三星Tab S(Android6.0)有指纹识别，但是系统方法判断为没有，继续用三星的sdk操作
            checkSamsungFingerprint();
        }
    }

    private void checkSamsungFingerprint() {
        fingerPrint = new SamsungFingerPrint(LoginActivity.this);
        if (fingerPrint.isSupported()) {
            if (fingerPrint.hasRegistered()) {
                startSamsungFingerPrintDialog();
            } else {
                showMessageLong("设备未注册指纹");
            }
            return;
        } else {
            showMessageLong("设备不支持指纹识别");
        }
    }

    /**
     * 通用指纹识别对话框
     */
    private void startFingerPrintDialog() {
        FingerprintDialog dialog = new FingerprintDialog();
        dialog.setOnFingerPrintListener(() -> superUser());
        dialog.show(getSupportFragmentManager(), "FingerprintDialog");
    }

    /**
     * 三星指纹识别对话框
     */
    private void startSamsungFingerPrintDialog() {
        boolean withPW = false;
        fingerPrint.showIdentifyDialog(withPW, new SamsungFingerPrint.SimpleIdentifyListener() {

            @Override
            public void onSuccess() {
                superUser();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    private void superUser() {
        goToHome();
    }

    private void goToHome() {
        Router.build("Home").go(this);
        finish();
    }
}
