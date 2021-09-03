package com.husher;

import java.io.*;

public class CurlUtil {

    public static void main(String[] args) {
        getHttpPost();
    }

    /**
     * java 调用 Curl的方法
     *
     * @param cmds
     * @return
     */
    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 接口调用
     *
     * @return
     */
    public static String getHttpPost() {

        String[] cmds = {"curl", "--location",
                "--request POST 'http://10.72.149.100:7003/ghc/messageWs/sendMsgDops?accept-encoding=gzip, deflate&Content-Type=application/xml; charset=UTF-8&Accept=application/xml,text/xml,application/json'",
                "--header 'accept-encoding: gzip, deflate'",
                "--header 'Content-Type: application/xml; charset=UTF-8'",
                "--header 'Accept: application/xml,text/xml,application/json'",
                getStringFromFile("f:/curl.txt")};

        //HTTP请求有两个超时时间：一个是连接超时时间，另一个是数据传输的最大允许时间（请求资源超时时间）。 单位秒
        //String[] cmds = {"curl","--connect-timeout","5","m","6", "-H", "Content-Type:application/json", "-X","POST","--data",""+requestJson+"",""+address+""};


        //命令的空格在jva数组里单个的,必须分开写，不能有空格,
        String responseMsg=execCurl(cmds);
        System.out.println("curl===curl"+responseMsg);

        return responseMsg;
    }

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
}
