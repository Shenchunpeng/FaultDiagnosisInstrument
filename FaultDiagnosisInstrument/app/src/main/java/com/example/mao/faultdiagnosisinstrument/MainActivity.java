package com.example.mao.faultdiagnosisinstrument;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public Button connectButton;
    public Button breakButton;
    public Button sendButton;
    public Button paintButton;
    public EditText IP;
    public EditText port;
    public EditText sendMessage;
    public TextView messgaeShow;
    public SurfaceView surfaceView;
    public String ip;
    public int Port;
    public RadioGroup equipmentGroup;
    public RadioButton eq1;
    public RadioButton eq2;
    public RadioButton eq3;
    public RadioButton eq4;
    public RadioButton eq5;
    public RadioButton eq6;
    public RadioGroup caiyangpinglvGroup;
    public RadioButton c1;
    public RadioButton c2;
    public RadioButton c3;
    public RadioButton c4;
    public RadioButton c5;
    public RadioButton c6;
    public  String caiyangpinglv;
    public RadioGroup pointGroup;
    public RadioButton p1;
    public RadioButton p2;
    public RadioButton p3;
    public RadioButton p4;
    public RadioButton p5;
    public RadioButton p6;
    public String caiyangdianshu;
    public RadioGroup jiezhicaiyangGroup;
    public RadioButton j1;
    public RadioButton j2;
    public RadioButton j3;
    public RadioButton j4;
    public RadioButton j5;
    public RadioButton j6;
    public  String jiezhipinlv;

    public boolean correctSign;

    Handler msgHandler;//信息接收Handler
//    ClientThread clientThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectButton = (Button) findViewById(R.id.connectButton);
        breakButton = (Button) findViewById(R.id.breakButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        paintButton = (Button) findViewById(R.id.paintButton);
        IP = (EditText) findViewById(R.id.IP);
        port = (EditText) findViewById(R.id.port);
        sendMessage = (EditText) findViewById(R.id.sendMessage);
        messgaeShow = (TextView) findViewById(R.id.messageShow);
//        surfaceView=(SurfaceView) findViewById(R.id.surfaceView);
        equipmentGroup = (RadioGroup) findViewById(R.id.equipmentGroup);
        eq1 = (RadioButton) findViewById(R.id.e1);
        eq2 = (RadioButton) findViewById(R.id.e2);
        eq3 = (RadioButton) findViewById(R.id.e3);
        eq4 = (RadioButton) findViewById(R.id.e4);
        eq5 = (RadioButton) findViewById(R.id.e5);
        eq6 = (RadioButton) findViewById(R.id.e6);

        caiyangpinglvGroup=(RadioGroup)findViewById(R.id.caiyangpinglvGroup);
        c1 = (RadioButton) findViewById(R.id.plv1);
        c2 = (RadioButton) findViewById(R.id.plv2);
        c3 = (RadioButton) findViewById(R.id.plv3);
        c4 = (RadioButton) findViewById(R.id.plv4);
        c5 = (RadioButton) findViewById(R.id.plv5);
        c6 = (RadioButton) findViewById(R.id.plv6);

        pointGroup=(RadioGroup)findViewById(R.id.pointGroup);
        p1 = (RadioButton) findViewById(R.id.pg1);
        p2 = (RadioButton) findViewById(R.id.pg2);
        p3 = (RadioButton) findViewById(R.id.pg3);
        p4 = (RadioButton) findViewById(R.id.pg4);
        p5 = (RadioButton) findViewById(R.id.pg5);
        p6 = (RadioButton) findViewById(R.id.pg6);

        jiezhicaiyangGroup=(RadioGroup)findViewById(R.id.jiezhipinlvGroup);
        j1 = (RadioButton) findViewById(R.id.jlv1);
        j2 = (RadioButton) findViewById(R.id.jlv2);
        j3 = (RadioButton) findViewById(R.id.jlv3);
        j4 = (RadioButton) findViewById(R.id.jlv4);
        j5 = (RadioButton) findViewById(R.id.jlv5);
        j6 = (RadioButton) findViewById(R.id.jlv6);


        correctSign = false;
        caiyangdianshu=null;
        caiyangpinglv=null;
        jiezhipinlv=null;

        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x004) {
                    messgaeShow.setText(msg.obj.toString());
                }
            }
        };

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShiyanActivity.class);
                startActivity(intent);

//                if ((ip!=null)&&(port!=null)) {
//                    IP.setText(ip);
//                    port.setText(String.valueOf( Port));
//                    correctSign = true;
//                    Toast.makeText(MainActivity.this, "正在连接中。。。", Toast.LENGTH_SHORT).show();
//                    Timer timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
////                            Toast.makeText(MainActivity.this, "已连接!", Toast.LENGTH_SHORT).show();
//                        }
//                    },2000);//延时1s执行
//                    Toast.makeText(MainActivity.this, "已连接!", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(MainActivity.this, "请先选择设备", Toast.LENGTH_SHORT).show();
//                }
//                ip = IP.getText().toString();
//                Port = Integer.parseInt(port.getText().toString());
//                clientThread = new ClientThread(msgHandler, ip, Port);
//                new Thread(clientThread).start();

            }
        });

        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    correctSign=false;
                    Toast.makeText(MainActivity.this, "已断开",Toast.LENGTH_SHORT).show();
                    Message msg = new Message();
                    msg.what = 0x005;
//                    clientThread.closeSocket.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Message msg = new Message();
                    msg.what = 0x003;
                    msg.obj = sendMessage.getText().toString();
//                    clientThread.revHandler.sendMessage(msg);
                    sendMessage.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//采样频率
        caiyangpinglvGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (c1.isChecked()){
                    caiyangpinglv="1000Hz";
                }
                if (c2.isChecked()){
                    caiyangpinglv="2000Hz";
                }
                if (c3.isChecked()){
                    caiyangpinglv="5000Hz";
                }
                if (c4.isChecked()){
                    caiyangpinglv="10000Hz";
                }
                if (c5.isChecked()){
                    caiyangpinglv="20000Hz";
                }
                if (c6.isChecked()){
                    caiyangpinglv="40000Hz";
                }
            }
        });

        //采样点数
        pointGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (p1.isChecked()){
                    caiyangdianshu="512";
                }
                if (p2.isChecked()){
                    caiyangdianshu="1024";
                }
                if (p3.isChecked()){
                    caiyangdianshu="2048";
                }
                if (p4.isChecked()){
                    caiyangdianshu="4096";
                }
                if (p5.isChecked()){
                    caiyangdianshu="8192";
                }
                if (p6.isChecked()){
                    caiyangdianshu="16384";
                }
            }
        });

        //截止频率
        jiezhicaiyangGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (j1.isChecked()){
                    jiezhipinlv="100Hz";
                }
                if (j2.isChecked()){
                    jiezhipinlv="200Hz";
                }
                if (j3.isChecked()){
                    jiezhipinlv="500Hz";
                }
                if (j4.isChecked()){
                    jiezhipinlv="1000Hz";
                }
                if (j5.isChecked()){
                    jiezhipinlv="2000Hz";
                }
                if (j6.isChecked()){
                    jiezhipinlv="5000Hz";
                }
            }
        });

        equipmentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (eq1.isChecked()){
                    ip="192.168.43.81";
                    Port=60001;
                }
                if (eq2.isChecked()){
                    ip="192.168.43.82";
                    Port=60001;
                }
                if (eq3.isChecked()){
                    ip="192.168.43.83";
                    Port=60001;
                }
                if (eq4.isChecked()){
                    ip="192.168.43.84";
                    Port=60001;

                }
                if (eq5.isChecked()){
                    ip="192.168.43.85";
                    Port=60001;

                }
                if (eq6.isChecked()){
                    ip="192.168.43.86";
                    Port=60001;

                }
            }
        });


        //采样按钮
        paintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((correctSign=true)&&(caiyangdianshu!=null)&&(caiyangpinglv!=null)&&(jiezhipinlv!=null)){
                    Toast.makeText(MainActivity.this, "请等待。。。",Toast.LENGTH_SHORT).show();
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask()
                    {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("extra_data1", caiyangdianshu);
                            intent.putExtra("extra_data2", caiyangpinglv);
                            intent.putExtra("extra_data3", jiezhipinlv);
                            startActivity(intent);
                        }
                    };
                    timer.schedule(timerTask,10000);

                }else{
                    Toast.makeText(MainActivity.this, "请先连接设备和选择相关参数",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

