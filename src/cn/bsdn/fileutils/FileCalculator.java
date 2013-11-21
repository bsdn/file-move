package cn.bsdn.fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileCalculator {
	public static long getDirectorySize(File f, long fromDate) {
		long size = 0;
		if (f.isDirectory()) {
			File files[] = f.listFiles();
			System.out.println(f.getAbsolutePath());
			for (File file : files) {
				if (file.isDirectory()) {
					size += getDirectorySize(file, fromDate);
				} else {
					size += getFileSize(file, fromDate);
				}
			}
		}
		return size;
	}

	public static long getFileSize(File f, long fromDate) {
		FileInputStream fis = null;
		if (f.lastModified() >= fromDate) {
			try {
				fis = new FileInputStream(f);
				return fis.available();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return 0;
	}
}
