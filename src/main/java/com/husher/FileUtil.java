package com.husher;

import java.io.*;
import java.util.Arrays;

/**
 * 
 * 
 * 功能描述： 文件
 * 
 * @author liubinwang create on: 2013-4-26
 * 
 */

public class FileUtil {

	private static String emailFilePatch="";

	/**
	 * 文件路径为字节数组
	 *
	 * @param path 路径
	 * @author caiming
	 * @date 2022/01/06
	 */
	public static byte[] getBytesFromFile(String path) {
		if (path != null) {
			if (path.startsWith("classpath:")) {
				path = path.replace("classpath:", "");
				return getBytesFromFile(FileUtil.class.getResourceAsStream(path));
			}
			File file = new File(path);
			return getBytesFromFile(file);
		}
		return null;
	}

	/**
	 * 获取文件夹下边所有文件
	 *
	 * @param path
	 * @return
	 */
	public static File[] getFilesInDirst(String path){
		File file =new File(path);
		if(file.isDirectory()){
			return file.listFiles();
		}
		return null;
	}
	/**
	 * 文件转为字节数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) {
		if (file != null) {
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				return getBytesFromFile(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				IOUtil.closeQuietly(in);
			}
		}
		return null;
	}

	/**
	 * 
	 * 文件输入流转化为字节数组
	 * 
	 * @param inputStream
	 * @return
	 */
	public static byte[] getBytesFromFile(InputStream inputStream) {
		byte[] ret = null;
		ByteArrayOutputStream out = null;
		try {
			if (inputStream == null) {
				// log.error("helper:the file is null!");
				return null;
			}
			out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = inputStream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			out.flush();
			ret = out.toByteArray();
		} catch (IOException e) {
			// log.error("helper:get bytes from file process error!");
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(out);
			IOUtil.closeQuietly(inputStream);
		}
		return ret;
	}

	/**
	 * 文件输入流转化为字节数组
	 *
	 * @param path 路径
	 * @author caiming
	 * @date 2022/01/06
	 */
	public static String getStringFromFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		StringBuffer sb=new StringBuffer(2000);
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				sb.append(tempString+"\r\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 把字节数组保存为一个文件
	 * 
	 * @param b
	 * @param outputFile
	 * @return
	 */
	public static void saveFileFromBytes(byte[] b, String outputFile,String fileName) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			File dir=new File(outputFile);
			if(!dir.isDirectory()){
				dir.mkdirs();
			}
			ret = new File(outputFile+fileName);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
			stream.flush();
		} catch (Exception e) {
			// log.error("helper:get file from byte process error!");
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// log.error("helper:get file from byte process error!");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 转化　File
	 * 
	 * @param b
	 * @param fileName
	 * @return
	 */
	public static File paseFile(byte[] b, String fileName) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			ret = new File(fileName);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
			stream.flush();
		} catch (Exception e) {
			// log.error("helper:get file from byte process error!");
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// log.error("helper:get file from byte process error!");
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * 
	 * 功能描述：文件后缀 前面带　*
	 * 
	 * @param fileName
	 * @return
	 * @author liubinwang create on: 2013-5-7
	 * 
	 */
	public static String getFileSuffix(String fileName) {
		if (fileName != null && !"".equals(fileName)) {
			return "*.*";
		} else {
			fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			return "*" + fileName;
		}
	}

	/**
	 * 
	 * 
	 * 功能描述：打开文件
	 * 
	 * @param path
	 * @author liubinwang create on: 2013-5-13
	 * 
	 */
	public static void open(String path) {
		File file = new File(path);
		open(file);
	}

	/**
	 * 
	 * 
	 * 功能描述：打开文件
	 * 
	 * @param file
	 * @author liubinwang create on: 2013-5-13
	 * 
	 */
	public static void open(File file) {
		try {
			Runtime.getRuntime().exec("cmd.exe /c start " + file.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能描述：生成三位随机数
	 * 
	 * @return
	 * @author likeyong create on: 2013-9-24
	 * 
	 */
	public static int get3RandomNo() {
		return (int) (Math.random() * 900) + 100;
	}

	@SuppressWarnings("unused")
	public static boolean move(File oldfile, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldfile); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			IOUtil.closeQuietly(fs);
			IOUtil.closeQuietly(inStream);
		}
		return true;
	}
	
	/**
	 * 获取指定目录下的固定数量的文件
	 * 
	 * @param dir
	 * @param num
	 * @return
	 */
	public static File[] getFiles(String dir, Integer num) {
		if (dir == null || dir.trim().length() == 0) {
			return null;
		}
		if (num == null 
				|| num.equals(0)) {
			return new File[0];
		}
		File fileDir = new File(dir);
		File[] files = fileDir.listFiles();
		if (files != null 
				&& files.length > num) {
			return Arrays.copyOfRange(files, 0, num);
		}
		return files;
	}
}
