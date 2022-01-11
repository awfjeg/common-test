/**
 * @Title:IO辅助工具
 * @Description:IO辅助工具
 * @Company:CSN
 * @Author:well
 * @CreateDate:2016-10-12
 * @UpdateUser:
 * @Version:0.1
 *
 */
package com.husher;

import java.io.*;

/**
 * @ClassName:IOUtil
 * @Description:IO辅助工具
 * @author well
 * @date:2016-10-12
 *
 */
public class IOUtil {

	public static void closeQuietly(Reader input) {
        closeQuietly((Closeable)input);
    }
	
	public static void closeQuietly(Writer output) {
        closeQuietly((Closeable)output);
    }
	
	public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable)input);
    }
	
	public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable)output);
    }

	public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
