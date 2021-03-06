package com.husher.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NIOTest {
    private static int PORT = 8000;
    private static final long PAUSE_BETWEEEN_MSGS = 10; // millisecs
    private static ByteBuffer echoBuffer = ByteBuffer.allocate(4);
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(256);
    private static ConcurrentHashMap<Integer, SocketChannel> chm
            = new ConcurrentHashMap<Integer, SocketChannel>();
    private static int msg = 0;

    public static void main(String args[]) throws Exception {
        Selector selector = Selector.open();
        // Open a listener on each port, and register each one
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("localhost",PORT);
        ssc.bind(address);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Going to listen on " + PORT);
        while (true){
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();

            System.out.println(selectedKeys.size());
            while (it.hasNext()){
                String msg = new String();
                SelectionKey key = (SelectionKey) it.next();
                if(key.isAcceptable()){
                    ServerSocketChannel sscNew = (ServerSocketChannel) key
                            .channel();
                    SocketChannel sc = sscNew.accept();
                    sc.configureBlocking(false);
                    // Add the new connection to the selector
                    sc.register(selector, SelectionKey.OP_READ);
                    // Add the socket channel to the list
                    chm.put(sc.hashCode(), sc);
                    it.remove();
                }else if(key.isReadable()){
                    SocketChannel sc = (SocketChannel) key.channel();
                    int code = 0;
                    while ((code = sc.read(echoBuffer)) > 0) {
                        byte b[] = new byte[echoBuffer.position()];
                        echoBuffer.flip();
                        echoBuffer.get(b);
                        msg+=new String(b, "UTF-8");
                    }
                    //                    if(msg.length()>1)
                    //                        msg = msg.substring(0, msg.length()-2);

                    //client?????????????????????????????????code = -1
                    if (code == -1 ||
                            msg.toUpperCase().indexOf("BYE")>-1){
                        chm.remove(sc.hashCode());
                        sc.close();
                    } else {
                        //code=0?????????????????????echoBuffer?????????????????????????????????????????????select?????????
                        echoBuffer.clear();
                    }
                    System.out.println("msg: " + msg  + " from: " + sc + "code:  " + code );
                    it.remove();

                    //??????????????????
                    sc.register(selector,SelectionKey.OP_WRITE);
                }else if(key.isWritable()){
                    SocketChannel client = (SocketChannel) key.channel();
                    String sendTxt = "Message from Server";
                    sendBuffer.put(sendTxt.getBytes());
                    sendBuffer.flip();
                    int code = 0;

                    //??????sendBuffer???????????????????????????????????????????????????????????????
                    while (client.write(sendBuffer) != 0){
                    }
                    if (code == -1 ){
                        chm.remove(client.hashCode());
                        client.close();
                    } else {
                        //code=0???????????????
                        sendBuffer.clear();
                    }
                    it.remove();
                    System.out.println("Send message to client ");

                    //????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    client.register(selector,SelectionKey.OP_READ);
                }
            }
            Thread.sleep(5000);
        }
    }
}