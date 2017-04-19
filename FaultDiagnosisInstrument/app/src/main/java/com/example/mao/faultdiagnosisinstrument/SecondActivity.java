package com.example.mao.faultdiagnosisinstrument;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SecondActivity extends AppCompatActivity {
    Button paintTimeDomain;
    Button FFtPaintButton;
    Button HilbertButton;
    Button button4;
    Button button5;
    Button button6;
    Button back;
    Button pageBackButton;
    Button pageForwardButton;
    Button reportButton;

    SurfaceView sfv;
    Paint paint;
    Paint paintRed;
    InputStreamReader inputStreamReader;
    String filename;
    public float data[];
    public float paintDadt[];
    public int page;
    public int paintKind;

    public String caiyangpinglv;
    public String caiyangdianshu;
    public String jiezhipinglv;
    TextView caiyangpinglvT;
    TextView caiyangdianshuT;
    TextView jiezhipinglvT;


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

    TextView T1;
    TextView T2;
    TextView T3;
    TextView T4;
    TextView T5;
    TextView T6;

    TextView Y1;
    TextView Y2;
    TextView Y3;
    TextView Y4;
    TextView Y5;



    Thread paintThread;
    Boolean isPainting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_screen);
        paintTimeDomain = (Button) findViewById(R.id.b1);
        FFtPaintButton = (Button) findViewById(R.id.b2);
        HilbertButton = (Button) findViewById(R.id.b4);
        button4 = (Button) findViewById(R.id.b3);
        button5 = (Button) findViewById(R.id.b5);
        button6 = (Button) findViewById(R.id.b6);
        back = (Button) findViewById(R.id.back);
        reportButton = (Button) findViewById(R.id.reportButton);
        pageBackButton = (Button) findViewById(R.id.pageBackButton);
        pageForwardButton = (Button) findViewById(R.id.pageForwardButton);
        caiyangpinglvT = (TextView) findViewById(R.id.caiyangpinlvT);
        caiyangdianshuT = (TextView) findViewById(R.id.caiyangdianshuT);
        jiezhipinglvT = (TextView) findViewById(R.id.jiezhipinlvT);

        T1 = (TextView) findViewById(R.id.fangcha);
        T2 = (TextView) findViewById(R.id.youxiaozhi);
        T3 = (TextView) findViewById(R.id.waidu);
        T4 = (TextView) findViewById(R.id.qiaodu);
        T5 = (TextView) findViewById(R.id.yudu);
        T6 = (TextView) findViewById(R.id.qiaoduzhibiao);


        X1 = (TextView) findViewById(R.id.X1);
        X2 = (TextView) findViewById(R.id.X2);
        X3 = (TextView) findViewById(R.id.X3);
        X4 = (TextView) findViewById(R.id.X4);
        X5 = (TextView) findViewById(R.id.X5);

        Y1 = (TextView) findViewById(R.id.Y1);
        Y2 = (TextView) findViewById(R.id.Y2);
        Y3 = (TextView) findViewById(R.id.Y3);
        Y4 = (TextView) findViewById(R.id.Y4);
        Y5 = (TextView) findViewById(R.id.Y5);

        paint = new Paint();
        paint.setColor(Color.BLACK);// 画笔为黑色
        paint.setStrokeWidth(1);// 设置画笔粗细

        paintRed = new Paint();
        paintRed.setColor(Color.RED);// 画笔为红色
        paintRed.setStrokeWidth(1);// 设置画笔粗细
//        page = 0;//初始页码
        sfv = (SurfaceView) findViewById(R.id.sfv);
        isPainting = false;//绘图标志
//接收数据
        Intent intent = getIntent();
        caiyangpinglv = intent.getStringExtra("extra_data2");
        caiyangdianshu = intent.getStringExtra("extra_data1");
        jiezhipinglv = intent.getStringExtra("extra_data3");
        caiyangpinglvT.setText(caiyangpinglv);
        caiyangdianshuT.setText(caiyangdianshu);
        jiezhipinglvT.setText(jiezhipinglv);


        //返回主界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


//画图线程
//画出时域图
        paintTimeDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //        读取数据
                filename = "neiquanyausnhi.txt";
                try {
                    InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(filename));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    int i = 0;
                    data = new float[65536];
                    while ((line = bufReader.readLine()) != null) {
                        data[i] = 3100* Float.parseFloat(line);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//              设置标志
                isPainting = true;
                paintKind = 1;
                page = 0;
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
                Y1.setText("0.1V");
                Y2.setText("0.05");
                Y3.setText("0");
                Y4.setText("-0.05");
                Y5.setText("-0.1");
                T1.setText("0.0004");
                T2.setText("0.0210");
                T3.setText("-0.24");
                T4.setText("16.32");
                T5.setText("17.57");
                T6.setText("830000");
                paintThread = new PaintThread(sfv, paint,paintRed);
                new Thread(paintThread).start();
            }
        });

//        画出fft变换图形
        FFtPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        读取数据
                filename = "neiquanFFT.txt";
                try {
                    InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(filename));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    int i = 0;
                    data = new float[65536];
                    while ((line = bufReader.readLine()) != null) {
                        data[i] = 5*Float.parseFloat(line);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPainting = true;
                paintKind = 2;
                page = 0;
                x1 = 0;
                x2 = 2515;
                x3 = 5030;
                x4 = 7045;
                x5 = 10060;
                X1.setText(String.valueOf(((float) x1 / 100)));
                X2.setText(String.valueOf(((float) x2 / 100)));
                X3.setText(String.valueOf(((float) x3 / 100)));
                X4.setText(String.valueOf(((float) x4 / 100)));
                X5.setText(String.valueOf(((float) x5 / 100)) + "hz");
                Y1.setText("200");
                Y2.setText("150");
                Y3.setText("100");
                Y4.setText("50");
                Y5.setText("0");
                T1.setText("");
                T2.setText("");
                T3.setText("");
                T4.setText("");
                T5.setText("");
                T6.setText("");

                FFtPaintThread fftpaintThread = new FFtPaintThread(sfv, paint,paintRed);
                new Thread(fftpaintThread).start();
            }
        });

        //        画出Hilbert图形
        HilbertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        读取数据
                filename = "neiquanHILBERT.txt";
                try {
                    InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(filename));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    int i = 0;
                    data = new float[65536];
                    while ((line = bufReader.readLine()) != null) {
                        data[i] =  Float.parseFloat(line);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPainting = true;
                paintKind = 3;
                page = 0;
                x1 = 0;
                x2 = 2515;
                x3 = 5030;
                x4 = 7045;
                x5 = 10060;
                X1.setText(String.valueOf(((float) x1 / 100)));
                X2.setText(String.valueOf(((float) x2 / 100)));
                X3.setText(String.valueOf(((float) x3 / 100)));
                X4.setText(String.valueOf(((float) x4 / 100)));
                X5.setText(String.valueOf(((float) x5 / 100)) + "hz");
                Y1.setText("600");
                Y2.setText("450");
                Y3.setText("300");
                Y4.setText("150");
                Y5.setText("0");
                T1.setText("");
                T2.setText("");
                T3.setText("");
                T4.setText("");
                T5.setText("");
                T6.setText("");

                HilbertPaintThread hilbertPaintThread = new HilbertPaintThread(sfv, paint,paintRed);
                new Thread(hilbertPaintThread).start();
            }
        });

        //上一页
        pageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((page > 0) && (isPainting)) {
                    page--;
                    switch (paintKind) {
                        case 1:
                            X1.setText(String.valueOf(((float) x5 * page + x1) / 10000));
                            X2.setText(String.valueOf(((float) x5 * page + x2) / 10000));
                            X3.setText(String.valueOf(((float) x5 * page + x3) / 10000));
                            X4.setText(String.valueOf(((float) x5 * page + x4) / 10000));
                            X5.setText(String.valueOf(((float) x5 * page + x5) / 10000) + "s");
                            paintThread = new PaintThread(sfv, paint,paintRed);
                            new Thread(paintThread).start();
                            break;
                        case 2:
                            X1.setText(String.valueOf(((float) x5 * page + x1) / 100));
                            X2.setText(String.valueOf(((float) x5 * page + x2) / 100));
                            X3.setText(String.valueOf(((float) x5 * page + x3) / 100));
                            X4.setText(String.valueOf(((float) x5 * page + x4) / 100));
                            X5.setText(String.valueOf(((float) x5 * page + x5) / 100) + "hz");
                            FFtPaintThread fftpaintThread = new FFtPaintThread(sfv, paint,paintRed);
                            new Thread(fftpaintThread).start();
                            break;
                        case 3:
                            X1.setText(String.valueOf(((float) x5 * page + x1) / 100));
                            X2.setText(String.valueOf(((float) x5 * page + x2) / 100));
                            X3.setText(String.valueOf(((float) x5 * page + x3) / 100));
                            X4.setText(String.valueOf(((float) x5 * page + x4) / 100));
                            X5.setText(String.valueOf(((float) x5 * page + x5) / 100) + "hz");
                            HilbertPaintThread hilbertPaintThread = new HilbertPaintThread(sfv, paint,paintRed);
                            new Thread(hilbertPaintThread).start();
                            break;
                        default:
                            break;
                    }


                }
            }
        });

        //下一页
        pageForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPainting) {
                    page++;
                    switch (paintKind) {
                        case 1:
                            X1.setText(String.valueOf(((float) x5 * page) / 10000));
                            X2.setText(String.valueOf(((float) x5 * page + x2) / 10000));
                            X3.setText(String.valueOf(((float) x5 * page + x3) / 10000));
                            X4.setText(String.valueOf(((float) x5 * page + x4) / 10000));
                            X5.setText(String.valueOf(((float) x5 * page + x5) / 10000) + "s");
                            paintThread = new PaintThread(sfv, paint,paintRed);
                            new Thread(paintThread).start();
                            break;
                        case 2:
                            X1.setText(String.valueOf(((float) x5 * page + x1) / 100));
                            X2.setText(String.valueOf(((float) x5 * page + x2) / 100));
                            X3.setText(String.valueOf(((float) x5 * page + x3) / 100));
                            X4.setText(String.valueOf(((float) x5 * page + x4) / 100));
                            X5.setText(String.valueOf(((float) x5 * page + x5) / 100) + "hz");
                            FFtPaintThread fftpaintThread = new FFtPaintThread(sfv, paint,paintRed);
                            new Thread(fftpaintThread).start();
                            break;
                        case 3:
                            X1.setText(String.valueOf(((float) x5 * page + x1) / 100));
                            X2.setText(String.valueOf(((float) x5 * page + x2) / 100));
                            X3.setText(String.valueOf(((float) x5 * page + x3) / 100));
                            X4.setText(String.valueOf(((float) x5 * page + x4) / 100));
                            X5.setText(String.valueOf(((float) x5 * page + x5) / 100) + "hz");
                            HilbertPaintThread hilbertPaintThread = new HilbertPaintThread(sfv, paint,paintRed);
                            new Thread(hilbertPaintThread).start();
                            break;
                        default:
                            break;

                    }

                }
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, DiagnosticReport.class);
                startActivity(intent);

            }
        });


    }
//绘制FFT
    public class FFtPaintThread extends Thread {

        SurfaceView sfv;
        Paint paint;
        Paint paintRed;
        public int oldX = 0;// 上次绘制的X坐标
        public float oldY = 0;// 上次绘制的Y坐标
        public int sfvBottom;//底部位置
        int pageMax;


        public FFtPaintThread(SurfaceView sfv, Paint paint,Paint paintRed) {
            this.sfv = sfv;
            this.paint = paint;
            this.paintRed =paintRed;
        }


        public void run() {
            Log.d("SecondActivity",String.valueOf(sfv.getHeight()));
            Log.d("SecondActivity",String.valueOf(sfv.getWidth()));
            sfvBottom = sfv.getHeight() ;

            float y;
            int x;
            pageMax = 65536 / sfv.getWidth();

            if (page < pageMax) {
                paintDadt = new float[sfv.getWidth()];
                for (int j = 0; j < sfv.getWidth(); j++) {
                    paintDadt[j] = data[sfv.getWidth() * page + j];
                }//获取数据
                Canvas canvas = sfv.getHolder().lockCanvas(
                        new Rect(0, 0, sfv.getWidth(), sfv.getHeight()));// 关键:获取画布
                canvas.drawColor(Color.WHITE);// 清除背景
                float xline = 0;
                for (int j = 0; j < 21; j++) {
                    canvas.drawLine(xline, 0, xline, sfv.getHeight(), paint);
                    xline = xline + 33;
                }//画出纵轴线
                float yline = 0;
                for (int j = 0; j < 21; j++) {
                    canvas.drawLine(0, yline, sfv.getWidth(), yline, paint);
                    yline = yline + sfv.getHeight() / 20;
                }//画出横轴线

                canvas.drawLine(0, sfv.getHeight() - 1, sfv.getWidth(), sfv.getHeight() - 1, paint);
                canvas.drawPoint(oldX, paintDadt[0], paint);
                oldY = paintDadt[0];
                for (int i = 1; i < paintDadt.length; i++) {
                    y = (float)sfvBottom-paintDadt[i];
                    x = oldX + 1;
                    canvas.drawLine(oldX, oldY, x, y, paint);
                    oldX = x;
                    oldY = y;
                }

                xline = 0;
                for (int j = 0; j < 5; j++) {
                    canvas.drawLine(xline, 0, xline, sfv.getHeight(), paintRed);
                    xline = xline + 33*5;
                }
//                canvas.drawLine(sfv.getWidth()-10, 0, xline-20, sfv.getWidth()-10, paintRed);
                //画出红色纵轴线
                yline = 0;
                for (int j = 0; j < 5; j++) {
                    canvas.drawLine(0, yline, sfv.getWidth(), yline, paintRed);
                    yline = yline + sfv.getHeight() / 20 * 5;
                }//画出红色横轴线
                canvas.drawLine(0, sfv.getHeight() - 1, sfv.getWidth(), sfv.getHeight() - 1, paintRed);

                sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
            }
        }
    }

    //绘制时域图
    public class PaintThread extends Thread {
        SurfaceView sfv;
        Paint paint;
        Paint paintRed;
        private int oldX = 0;// 上次绘制的X坐标
        private float oldY = 0;// 上次绘制的Y坐标
        private int center;//x轴位置
        private int pageMax;


        public PaintThread(SurfaceView sfv, Paint paint,Paint paintRed) {
            this.sfv = sfv;
            this.paint = paint;
            this.paintRed =paintRed;
        }


        public void run() {
            center = sfv.getHeight() / 2;
            float y;
            int x;
            pageMax = 65536 / sfv.getWidth();

            if (page < pageMax) {
                paintDadt = new float[sfv.getWidth()];
                for (int j = 0; j < sfv.getWidth(); j++) {
                    paintDadt[j] = data[sfv.getWidth() * page + j];
                }//获取数据
                Canvas canvas = sfv.getHolder().lockCanvas(
                        new Rect(0, 0, sfv.getWidth(), sfv.getHeight()));// 关键:获取画布
                canvas.drawColor(Color.WHITE);// 清除背景
                float xline = 0;
                for (int j = 0; j < 21; j++) {
                    canvas.drawLine(xline, 0, xline, sfv.getHeight(), paint);
                    xline = xline + 33;
                }//画出纵轴线
                float yline = 0;
                for (int j = 0; j < 21; j++) {
                    canvas.drawLine(0, yline, sfv.getWidth(), yline, paint);
                    yline = yline + sfv.getHeight() / 20;
                }//画出横轴线



//                canvas.drawPoint(oldX, paintDadt[0], paint);
                oldY = paintDadt[1];
                for (int i = 2; i < paintDadt.length; i++) {
                    y = (float) center-paintDadt[i]  ;
                    x = oldX + 1;
                    canvas.drawLine(oldX, oldY, x, y, paint);
                    oldX = x;
                    oldY = y;
                }

                xline = 0;
                for (int j = 0; j < 5; j++) {
                    canvas.drawLine(xline, 0, xline, sfv.getHeight(), paintRed);
                    xline = xline + 33*5;
                }
//                canvas.drawLine(sfv.getWidth()-10, 0, xline-20, sfv.getWidth()-10, paintRed);
                //画出红色纵轴线
                yline = 0;
                for (int j = 0; j < 5; j++) {
                    canvas.drawLine(0, yline, sfv.getWidth(), yline, paintRed);
                    yline = yline + sfv.getHeight() / 20 * 5;
                }//画出红色横轴线
                canvas.drawLine(0, sfv.getHeight() - 1, sfv.getWidth(), sfv.getHeight() - 1, paintRed);

                sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
            }
        }
    }

    //绘制Hlibert
    public class HilbertPaintThread extends Thread {

        SurfaceView sfv;
        Paint paint;
        Paint paintRed;
        public int oldX = 0;// 上次绘制的X坐标
        public float oldY = 0;// 上次绘制的Y坐标
        public int sfvBottom;//底部位置
        int pageMax;


        public HilbertPaintThread(SurfaceView sfv, Paint paint, Paint paintRed) {
            this.sfv = sfv;
            this.paint = paint;
            this.paintRed=paintRed;
        }


        public void run() {
            sfvBottom = sfv.getHeight() ;

            float y;
            int x;
            pageMax = 65536 / sfv.getWidth();

            if (page < pageMax) {
                paintDadt = new float[sfv.getWidth()];
                for (int j = 0; j < sfv.getWidth(); j++) {
                    paintDadt[j] = data[sfv.getWidth() * page + j];
                }//获取数据
                Canvas canvas = sfv.getHolder().lockCanvas(
                        new Rect(0, 0, sfv.getWidth(), sfv.getHeight()));// 关键:获取画布
                canvas.drawColor(Color.WHITE);// 清除背景
                float xline = 0;
                for (int j = 0; j < 21; j++) {
                    canvas.drawLine(xline, 0, xline, sfv.getHeight(), paint);
                    xline = xline + 33;
                }//画出纵轴线
                float yline = 0;
                for (int j = 0; j < 21; j++) {
                    canvas.drawLine(0, yline, sfv.getWidth(), yline, paint);
                    yline = yline + sfv.getHeight() / 20;
                }//画出横轴线


                canvas.drawLine(0, sfv.getHeight() - 1, sfv.getWidth(), sfv.getHeight() - 1, paint);
                paint.setColor(Color.BLACK);
                canvas.drawPoint(oldX, paintDadt[0], paint);
                oldY = paintDadt[0];
                for (int i = 1; i < paintDadt.length; i++) {
                    y = (float)sfvBottom-paintDadt[i];
//                    y = paintDadt[i];
                    x = oldX + 1;
                    canvas.drawLine(oldX, oldY, x, y, paint);
                    oldX = x;
                    oldY = y;
                }

                xline = 0;
                for (int j = 0; j < 5; j++) {
                    canvas.drawLine(xline, 0, xline, sfv.getHeight(), paintRed);
                    xline = xline + 33*5;
                }
//                canvas.drawLine(sfv.getWidth()-10, 0, xline-20, sfv.getWidth()-10, paintRed);
                //画出红色纵轴线
                yline = 0;
                for (int j = 0; j < 5; j++) {
                    canvas.drawLine(0, yline, sfv.getWidth(), yline, paintRed);
                    yline = yline + sfv.getHeight() / 20 * 5;
                }//画出红色横轴线
                canvas.drawLine(0, sfv.getHeight() - 1, sfv.getWidth(), sfv.getHeight() - 1, paintRed);

                sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
            }
        }
    }




}



