package com.husher.net;


import java.awt.Color;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 * 本机流量统计
 *
 * @author caiming
 * @version 1.0.0
 * @date 2022/01/11
 */
public class NetWorkThread extends JFrame implements Runnable{

    NetWork netWork = new NetWork();

    private String recevied;

    private String sent;

    private String stepRecevied;

    private String stepSent;

    private JLabel lab1 = new JLabel("总接受流量:");

    private JLabel lab2 = new JLabel();

    private JLabel lab3 = new JLabel("总发送流量:");

    private JLabel lab4 = new JLabel();

    private JLabel lab5 = new JLabel("本程序启动后接受流量:");

    private JLabel lab6 = new JLabel();

    private JLabel lab7 = new JLabel("本程序启动后发送流量:");

    private JLabel lab8 = new JLabel();

    private JPanel p1 = new JPanel();

    private JPanel p2 = new JPanel();

    private JPanel p3 = new JPanel();

    private JPanel p4 = new JPanel();

    private JPanel p = new JPanel();

    public NetWorkThread() {

        p1.add(lab1);

        p1.add(lab2);

        p2.add(lab3);

        p2.add(lab4);

        p3.add(lab5);

        p3.add(lab6);

        p4.add(lab7);

        p4.add(lab8);

        p1.setBackground(Color.PINK);

        p2.setBackground(Color.YELLOW);

        p3.setBackground(Color.PINK);

        p4.setBackground(Color.YELLOW);

        p.add(p1);

        p.add(p2);

        p.add(p3);

        p.add(p4);

        p.setLayout(new GridLayout(4, 1));

        this.setTitle("流量计算");

        this.add(p);

        this.setSize(400,200);

        this.setResizable(false);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        this.setVisible(true);

    }

    @Override
    public void run() {

        do

        {

            netWork.NetWorkFlux("netstat -e");

            this.recevied = netWork.recevied;
            this.sent = netWork.sent;
            this.stepRecevied = netWork.stepRecevied;
            this.stepSent = netWork.stepSent;
            lab2.setText(recevied);
            lab4.setText(sent);
            lab6.setText(stepRecevied);
            lab8.setText(stepSent);

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }while(true);

    }

    public static void main(String[] args) {

        new Thread(new NetWorkThread()).start();

    }

}
class NetWork {

    Pattern pattern = Pattern.compile("\\s*([0-9]+)\\s*([0-9]+)");

    public String recevied;
    public String sent;
    private long firstRecevied = -1L;
    private long firstSent = -1L;
    public String stepRecevied;
    public String stepSent;

    public void NetWorkFlux(String cmdline) {

        try {

            String line;

            Process p = Runtime.getRuntime().exec(cmdline);

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            int c = 0;

            while ((line = input.readLine()) != null) {

                if (c > 3) {

                    //                    String s [] = line.replaceAll("Bytes", "").trim().replace(" ", "@").split(" ");

                    //System.out.println(s.length);

                    //                    for(String str : s)
                    //
                    //                    {
                    //
                    //                            recevied = str.substring(0, str.indexOf("@"));
                    //
                    //                        sent = str.substring(str.lastIndexOf("@") + 1, str.length());
                    //
                    //                    }

                    /**System.out.println(recevied + " " + sent);

                     System.out.println("接受流量:" + Long.parseLong(recevied) / 1024 / 1024 + "MB");

                     System.out.println("发送流量:" + Long.parseLong(sent) / 1024 / 1024 + "MB");
                     �ֽ�@@@@@@@@@@@@@@@@@@@@1926108528@@@@@@@282886504
                     **/

                    Matcher match = pattern.matcher(line);
                    if (match.find()) {
                        long l1 = Long.parseLong(match.group(1));
                        long l2 = Long.parseLong(match.group(2));
                        recevied = l1 / 1024 + " KB";

                        sent = l2 / 1024 + " KB";
                        if (-1 == firstRecevied) {
                            firstRecevied = l1;
                            firstSent = l2;
                        }
                        stepRecevied = (l1 - firstRecevied)/1024  + "KB";
                        stepSent = (l2 - firstSent)/1024  + "KB";
                    }
                    break;

                }

                c++;

            }

            input.close();

        } catch (Exception err) {

        }

    }

}