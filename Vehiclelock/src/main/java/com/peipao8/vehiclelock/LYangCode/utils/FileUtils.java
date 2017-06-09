/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.peipao8.vehiclelock.LYangCode.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import com.peipao8.vehiclelock.LYangCode.utils.log.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/**
 * 文件工具类 Created by Jorstin on 2015/3/18.
 */
public class FileUtils {

	/**
	 * 
	 * @param root
	 * @param fileName
	 * @return
	 */
	public static String getMD5FileDir(String root, String fileName) {
		if (TextUtils.isEmpty(root)) {
			return null;
		}
		File file = new File(root);
		if (!file.exists()) {
			file.mkdirs();
		}

		File fullPath = new File(file,
				FileAccessor.getSecondLevelDirectory(fileName));
		if (!fullPath.exists()) {
			fullPath.mkdirs();
		}
		return fullPath.getAbsolutePath();
	}

	@SuppressLint("NewApi")
	public static Bitmap createVideoThumbnail(String filePath) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			// retriever.setMode(MediaMetadataRetriever.);
			retriever.setDataSource(filePath);

			bitmap = retriever.getFrameAtTime(1000);

		} catch (Exception ex) {

		} finally {
			try {
				retriever.release();

			} catch (RuntimeException ex) {
			}

		}
		return bitmap;

	}

	/**
	 * decode file length
	 * 
	 * @param filePath
	 * @return
	 */
	public static int decodeFileLength(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return 0;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return 0;
		}
		return (int) file.length();
	}

	/**
	 * Gets the extension of a file name, like ".png" or ".jpg".
	 * 
	 * @param uri
	 * @return Extension including the dot("."); "" if there is no extension;
	 *         null if uri was null.
	 */
	public static String getExtension(String uri) {
		if (uri == null) {
			return null;
		}

		int dot = uri.lastIndexOf(".");
		if (dot >= 0) {
			return uri.substring(dot);
		} else {
			// No extension.
			return "";
		}
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean checkFile(String filePath) {
		if (TextUtils.isEmpty(filePath) || !(new File(filePath).exists())) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param filePath
	 * @param seek
	 * @param length
	 * @return
	 */
	public static byte[] readFlieToByte(String filePath, int seek, int length) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		if (length == -1) {
			length = (int) file.length();
		}

		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			byte[] bs = new byte[length];
			randomAccessFile.seek(seek);
			randomAccessFile.readFully(bs);
			randomAccessFile.close();
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(LogUtil.getLogUtilsTag(FileUtils.class),
					"readFromFile : errMsg = " + e.getMessage());
			return null;
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param fileDir
	 * @param fileName
	 * @param buffer
	 * @return
	 */
	public static int copyFile(String fileDir, String fileName, byte[] buffer) {
		if (buffer == null) {
			return -2;
		}

		try {
			File file = new File(fileDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			File resultFile = new File(file, fileName);
			if (!resultFile.exists()) {
				resultFile.createNewFile();
			}
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(resultFile, true));
			bufferedOutputStream.write(buffer);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			return 0;

		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 根据文件名和后缀 拷贝文件
	 * 
	 * @param fileDir
	 * @param fileName
	 * @param ext
	 * @param buffer
	 * @return
	 */
	public static int copyFile(String fileDir, String fileName, String ext,
			byte[] buffer) {
		return copyFile(fileDir, fileName + ext, buffer);
	}

}
