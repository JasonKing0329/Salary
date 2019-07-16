package com.king.app.salary.model.params;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.king.app.salary.base.SalaryApplication;
import com.king.app.salary.model.entity.Salary;
import com.king.app.salary.model.entity.SalaryDetail;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 14:30
 */
public class SettingProperty {

    public static final String getString(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        return sp.getString(key, "");
    }

    private static final void setString(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static final int getInt(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        return sp.getInt(key, -1);
    }

    private static final int getInt(String key, int defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        return sp.getInt(key, defaultValue);
    }

    private static final void setInt(String key, int value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static final long getLong(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        return sp.getLong(key, -1);
    }

    private static final void setLong(String key, long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    private static final boolean getBoolean(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        return sp.getBoolean(key, false);
    }

    private static final void setBoolean(String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SalaryApplication.getInstance());
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setEnableFingerPrint(boolean enable) {
        setBoolean("enable_finger_print", enable);
    }

    public static boolean isEnableFingerPrint() {
        return getBoolean("enable_finger_print");
    }

    public static void setAutoFillSalary(long companyId, SalaryDetail detail) {
        setLong("auto_fill_company", companyId);
        setString("auto_fill_detail", new Gson().toJson(detail));
    }

    public static long getAutoFillCompany() {
        return getLong("auto_fill_company");
    }

    public static SalaryDetail getAutoFillDetail() {
        String json = getString("auto_fill_detail");
        try {
            return new Gson().fromJson(json, SalaryDetail.class);
        } catch (Exception e){}
        return null;
    }

}
