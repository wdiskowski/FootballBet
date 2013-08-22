package org.mw.bi.util;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class FileCreateCopy {

	/**
	 * @param args
	 */
	public static final Logger logger = Logger.getLogger(FileCreateCopy.class
			.getName());

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		copy(in, out, 0xFFFF);
	}

	private static void copy(InputStream in, OutputStream out, int bufferLength)
			throws IOException {
		byte[] buffer = new byte[bufferLength];
		for (int len; (len = in.read(buffer)) != -1;)
			out.write(buffer, 0, len);
	}

	public static boolean copyFile(File from, String to_path) {
		logger.debug("from: " + from);
		logger.debug("to: " + to_path);
		boolean successful = true;
		BufferedInputStream is = null;
		BufferedOutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(from));
			os = new BufferedOutputStream(new FileOutputStream(to_path));
			copy(is, os);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			successful = false;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			try {
				os.close();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
		return successful;
	}

	public static boolean copyFile(InputStream from, String to_path) {
		logger.debug("from: " + from);
		logger.debug("to: " + to_path);

		boolean successful = true;
		BufferedInputStream is = null;
		BufferedOutputStream os = null;
		try {
			is = new BufferedInputStream(from);
			os = new BufferedOutputStream(new FileOutputStream(to_path));
			copy(is, os);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			successful = false;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			try {
				os.close();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
		return successful;
	}

	public static boolean copyFile(byte[] input, String to_path) {
		logger.debug(">");
		logger.debug("to: " + to_path);
		boolean returnValue = true;
		BufferedOutputStream os = null;
		BufferedInputStream is = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(to_path));
			is = new BufferedInputStream(new ByteArrayInputStream(input));
			copy(is, os);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			returnValue = false;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			try {
				os.close();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}

		logger.debug("<");
		return returnValue;
	}

	public static byte[] getFileContent(File file) throws Exception {
		logger.debug(">" + file);

		byte[] fileContent = new byte[(int) file.length()];
		BufferedInputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(file));
			is.read(fileContent);
		} catch (IOException e) {
			logger.warn(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				logger.warn(ExceptionUtils.getFullStackTrace(e));
			}
		}
		logger.debug("<" + fileContent.length);
		return fileContent;
	}

	public static byte[] getFileContent(InputStream inputStream)
			throws IOException {

		int count = 0;
		byte[] buffer = new byte[1];
		byte[] fileContent = new byte[] {};
		BufferedInputStream is = null;
		int read = 0;
		try {
			is = new BufferedInputStream(inputStream);
			while ((count = is.read(buffer)) != -1) {
				fileContent = ArrayUtils.addAll(fileContent, buffer);
				read += count;
			}
		} catch (IOException e) {
			logger.warn(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				;
			}
		}
		logger.debug("<" + read);
		return fileContent;
	}

	public static String getTextFileContent(File from, String lineSeparator)
			throws Exception {
		logger.debug("from: " + from);

		String fileContent = "";
		FileInputStream fis = null;
		BufferedReader br = null;
		String temp;
		try {
			fis = new FileInputStream(from);
			br = new BufferedReader(new InputStreamReader(fis));
			while ((temp = br.readLine()) != null) {
				fileContent += temp + lineSeparator;
			}

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.warn(ExceptionUtils.getFullStackTrace(e));
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					logger.warn(ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		return fileContent;
	}

	public static List<String> getTextFileContentAsList(File from) throws Exception {
		logger.debug("from: " + from);

		List<String> fileContent = new ArrayList<String>();
		FileInputStream fis = null;
		BufferedReader br = null;
		String temp;
		try {
			fis = new FileInputStream(from);
			br = new BufferedReader(new InputStreamReader(fis));
			while ((temp = br.readLine()) != null) {
				fileContent.add(temp);
			}

		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.warn(ExceptionUtils.getFullStackTrace(e));
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					logger.warn(ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		return fileContent;
	}

	public static void createNewFolder(String path) {
		logger.debug(">");
		createNewFolder(path, true);
		logger.debug("<");
	}

	public static void createNewFolder(String path, boolean deleteIfExist) {
		logger.debug(">");
		if (NoNull.noNullTrimLength(path) > 0) {
			if (deleteIfExist)
				deleteTree(path);
			File file = new File(path);
			if (!file.exists())
				file.mkdir();
		}
		logger.debug("<");
	}

	public static void deleteTree(String path) {
		logger.debug(">");
		if (NoNull.noNullTrimLength(path) > 0)
			deleteTree(new File(path));
		logger.debug("<");
	}

	public static void deleteTree(File path) {
		logger.debug(">");
		if (path.exists()) {
			if (path.isDirectory()) {
				File[] files = path.listFiles();
				for (int i = 0; i < NoNull.noNullLength(files); i++) {
					deleteTree(files[i]);
				}
			}
			try {
				path.delete();
			} catch (Exception e) {
				logger.warn("Can't delete file: " + path.getAbsolutePath());
			}
		}
		logger.debug("<");
	}

	
	/**
	 * This method reads Image from
	 * current path
	 * @param path - relative context path
	 * @param ctx - ServletContext
	 * @return Image
	 * @throws IOException if image can not be read 
	 * @throws IllegalArgumentException if path not exists
	 */
	public static Image readImageFromContextPath(String path, ServletContext ctx) throws IOException{
		Image image = null;
		logger.debug(">");
		if(NoNull.noNullTrimLength(path) > 0){
			image = ImageIO.read(ctx
					.getResourceAsStream(path));
		}
		logger.debug("<");
		return image;
	}

}
