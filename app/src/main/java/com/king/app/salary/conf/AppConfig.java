package com.king.app.salary.conf;

import android.os.Environment;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 13:53
 */
public class AppConfig {
    public static final String DB_NAME = "salary.db";

    public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();

    public static final String APP_ROOT = SDCARD + "/salary";

    public static final String APP_DIR_EXPORT = APP_ROOT + "/export";

    public static final String APP_DIR_HISTORY = APP_ROOT + "/history";

    public static final String[] DIRS = new String[] {
            APP_ROOT, APP_DIR_EXPORT, APP_DIR_HISTORY
    };

}
