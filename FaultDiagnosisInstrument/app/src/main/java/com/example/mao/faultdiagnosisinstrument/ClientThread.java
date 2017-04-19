//package com.example.mao.faultdiagnosisinstrument;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.net.SocketTimeoutException;
//
///**
// * Created by mao on 2016/12/15.
// */
//public class ClientThread extends Thread
//{
//    String i="1";
//    public Socket socket;
//    Handler msgHandler;
//    Handler revHandler;
//    Handler closeSocket;
//    BufferedReader br = null;
//    OutputStream os = null;
//    DataInputStream dins = null;
//    String IP;
//    int port;
//
//    public ClientThread(Handler msgHandler,String IP,int port)
//    {
//        this.msgHandler = msgHandler;
//        this.IP=IP;
//        this.port=port;
//    }
//
//    public void run(){
//        try{
//
//            socket = new Socket(IP,port);
//            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            dins = new DataInputStream(socket.getInputStream());
//            new ReceiveThread().start();
//            os = socket.getOutputStream();
//
//
//
//            Looper.prepare();
//            revHandler = new Handler()
//            {
//                @Override
//                public void handleMessage(Message receiveMsg)
//                {
//                    if (receiveMsg.what == 0x003)
//                    {
//                        try
//                        {
//                            os.write((receiveMsg.obj.toString() ).getBytes("utf-8"));
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            };
//
//            closeSocket = new Handler()
//            {
//                public void handleMessage(Message msg)
//                {
//                    if (msg.what== 0x005){
//                        try {
//                         socket.close();
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            };
//            Looper.loop();
//        }
//        catch (SocketTimeoutException e1)
//        {
//            System.out.println("网络链接超时");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public class ReceiveThread extends Thread {
//        @Override
//        public void run() {
//            String content = null;
//            try
//            {
//
//                byte[] cbuff = new byte[256];
//                char[] charBuff = new char[256];
//                int size = 0;
//
//                while( (size =dins.read(cbuff))> 0) {
//                    convertByteToChar(cbuff, charBuff, size);
//                    StringBuilder sb = new StringBuilder();
//                    sb.append(charBuff);
//
//                    Message msg = new Message();
//                    msg.what = 0x004;
//                    msg.obj = sb.toString();
//                    msgHandler.sendMessage(msg);
//                }
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void convertByteToChar(byte[] cbuff, char[] charBuff, int size) {
//        for (int i = 0; i < charBuff.length; i++) {
//            if (i < size) {
//                charBuff[i] = (char) cbuff[i];
//            } else {
//                charBuff[i] = ' ';
//            }
//        }
//    }
//
//
//}
//
//
//
//
//
//
//
//
