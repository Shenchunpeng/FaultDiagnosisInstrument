package com.example.mao.faultdiagnosisinstrument;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShiyanActivity extends AppCompatActivity {

    Handler msgHandler1;
    TextView messgaeShow1;
    EditText sendMessage1;
    Button connectButton1;
    Button sendButton1;
    TcpServerThread tcpServerThread;
    protected float paintDadt1[];
    protected float data1[];
    protected int page;
    SurfaceView sfv1;
    Paint paint;
    Paint paintRed;
    TextView Y1;
    TextView Y2;
    TextView Y3;
    TextView Y4;
    TextView Y5;
    TextView X1;
    TextView X2;
    TextView X3;
    TextView X4;
    TextView X5;
    int x1;
    int x2;
    int x3;
    int x4;
    int x5;
    Thread paintThread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiyan);
        X1 = (TextView) findViewById(R.id.X11);
        X2 = (TextView) findViewById(R.id.X21);
        X3 = (TextView) findViewById(R.id.X31);
        X4 = (TextView) findViewById(R.id.X41);
        X5 = (TextView) findViewById(R.id.X51);

        Y1 = (TextView) findViewById(R.id.Y11);
        Y2 = (TextView) findViewById(R.id.Y21);
        Y3 = (TextView) findViewById(R.id.Y31);
        Y4 = (TextView) findViewById(R.id.Y41);
        Y5 = (TextView) findViewById(R.id.Y51);

        connectButton1 = (Button) findViewById(R.id.correctButton1);
        sendButton1 = (Button) findViewById(R.id.sendButton1);
        sendMessage1 = (EditText) findViewById(R.id.sendMessage1);
        messgaeShow1 = (TextView) findViewById(R.id.messageShow1);
        paint = new Paint();
        paint.setColor(Color.BLACK);// 画笔为黑色
        paint.setStrokeWidth(1);// 设置画笔粗细

        paintRed = new Paint();
        paintRed.setColor(Color.RED);// 画笔为红色
        paintRed.setStrokeWidth(1);// 设置画笔粗细
//        page = 0;//初始页码
        sfv1 = (SurfaceView) findViewById(R.id.sfv1);
        data1 = new float[1029];

        x1 = 0;
        x2 = 165;
        x3 = 330;
        x4 = 495;
        x5 = 660;
        X1.setText(String.valueOf(((float) x1 / 10000)));
        X2.setText(String.valueOf(((float) x2 / 10000)));
        X3.setText(String.valueOf(((float) x3 / 10000)));
        X4.setText(String.valueOf(((float) x4 / 10000)));
        X5.setText(String.valueOf(((float) x5 / 10000)) + "s");
        Y1.setText("0.3V");
        Y2.setText("0.15");
        Y3.setText("0");
        Y4.setText("-0.15");
        Y5.setText("-0.3");


        //  读取数据
        String filename = "666.txt";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(filename));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            int i = 0;
            data1 = new float[16384];
            while ((line = bufReader.readLine()) != null) {
                data1[i] = 1033 * Float.parseFloat(line);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        page = 0;
        msgHandler1 = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x004) {
//                    char[] ch = msg.obj.toString().toCharArray();
//                    int i = 0;
//                    while (i < ch.length) {
//                        data1[i] = (float) ch[i];
//                        i++;
//                    }

//              设置标志
//                    Timer timer = new Timer();
//                    TimerTask timerTask = new TimerTask() {
//                        @Override
//                        public void run() {
//
//                        }
//                    };
//                    timer.schedule(timerTask,1000);

                    paintThread1 = new PaintThread1(sfv1, paint, paintRed);
                    new Thread(paintThread1).start();
                    page++;

                }
            }
        };

        connectButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tcpServerThread = new TcpServerThread(msgHandler1);
                new Thread(tcpServerThread).start();

            }
        });

        sendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Message msg = new Message();
                    msg.what = 0x003;
                    msg.obj = sendMessage1.getText().toString();
                    tcpServerThread.revHandler1.sendMessage(msg);
                    sendMessage1.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //绘制时域图
    public class PaintThread1 extends Thread {
        SurfaceView sfv1;
        Paint paint;
        Paint paintRed;
        private int oldX = 0;// 上次绘制的X坐标
        private float oldY = 0;// 上次绘制的Y坐标
        private int center;//x轴位置


        public PaintThread1(SurfaceView sfv1, Paint paint, Paint paintRed) {
            this.sfv1 = sfv1;
            this.paint = paint;
            this.paintRed = paintRed;
        }


        public void run() {
            center = sfv1.getHeight() / 2;
            float y;
            int x;
            int pagemax = 16384 / sfv1.getWidth();
            if (page > pagemax) {
                page = 0;
            }
//            int m = 0;
//            while (m < data1.length) {
//                if (data1[m] < 32768) {
//                    data1[m] = (data1[1] / 32768) * 100000;
//                } else {
//                    data1[m] = ((65535 - data1[m]) / 32768) * 100000;
//                }
//                m++;
//            }
            paintDadt1 = new float[512];
            for (int j = 0; j < 512; j++) {
                paintDadt1[j] = data1[512*page + j];
            }

            Canvas canvas = sfv1.getHolder().lockCanvas(
                    new Rect(0, 0, sfv1.getWidth(), sfv1.getHeight()));// 关键:获取画布
            canvas.drawColor(Color.WHITE);// 清除背景
            float xline = 0;
            for (int j = 0; j < 21; j++) {
                canvas.drawLine(xline, 0, xline, sfv1.getHeight(), paint);
                xline = xline + 33;
            }//画出纵轴线
            float yline = 0;
            for (int j = 0; j < 21; j++) {
                canvas.drawLine(0, yline, sfv1.getWidth(), yline, paint);
                yline = yline + sfv1.getHeight() / 20;
            }//画出横轴线


            oldY = paintDadt1[1];
            for (int i = 2; i < paintDadt1.length; i++) {
                y = (float) center - paintDadt1[i];
                x = oldX + 1;
                canvas.drawLine(oldX, oldY, x, y, paint);
                oldX = x;
                oldY = y;
            }

            xline = 0;
            for (int j = 0; j < 5; j++) {
                canvas.drawLine(xline, 0, xline, sfv1.getHeight(), paintRed);
                xline = xline + 33 * 5;
            }
//                canvas.drawLine(sfv.getWidth()-10, 0, xline-20, sfv.getWidth()-10, paintRed);
            //画出红色纵轴线
            yline = 0;
            for (int j = 0; j < 5; j++) {
                canvas.drawLine(0, yline, sfv1.getWidth(), yline, paintRed);
                yline = yline + sfv1.getHeight() / 20 * 5;
            }//画出红色横轴线
            canvas.drawLine(0, sfv1.getHeight() - 1, sfv1.getWidth(), sfv1.getHeight() - 1, paintRed);

            sfv1.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
        }
    }


}



