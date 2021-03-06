package com.king.app.salary.utils;

import android.util.Log;

import com.king.app.salary.base.SalaryApplication;
import com.king.app.salary.conf.AppConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class DBExporter {
	
	public static void execute() {

		String dbPath = SalaryApplication.getInstance().getFilesDir().getParent() + "/databases";
	//	String dbPath = Environment.getExternalStorageDirectory() + "/tcslSystem";
		String targetPath = AppConfig.APP_DIR_EXPORT;
		try {
			DBExporter.copyDirectiory(dbPath, targetPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportAsHistory() {

		String dbPath = SalaryApplication.getInstance().getFilesDir().getParent() + "/databases/" + AppConfig.DB_NAME;

		Calendar calendar = Calendar.getInstance();
		StringBuffer target = new StringBuffer();
		target.append(AppConfig.APP_DIR_HISTORY).append("/salary_");
		target.append(calendar.get(Calendar.YEAR)).append("_");
		target.append(calendar.get(Calendar.MONTH) + 1).append("_");
		target.append(calendar.get(Calendar.DAY_OF_MONTH)).append("_");
		target.append(calendar.get(Calendar.HOUR)).append("_");
		target.append(calendar.get(Calendar.MINUTE)).append("_");
		target.append(calendar.get(Calendar.SECOND));
		target.append(".db");
		try {
			copyFile(new File(dbPath), new File(target.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {

		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		// 刷新此缓冲的输出流
		outbuff.flush();

		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		Log.e("DBExporter", "copy from [" + sourceDir + "] to [" + targetDir + "]");
		// 新建目标目录
		File target = new File(targetDir);
		if (!target.exists()) {
			target.mkdirs();
		}

		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		if (file == null) {
			return;
		}

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());

				copyFile(sourceFile, targetFile);

			}

			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();

				copyDirectiory(dir1, dir2);
			}
		}

	}
}
