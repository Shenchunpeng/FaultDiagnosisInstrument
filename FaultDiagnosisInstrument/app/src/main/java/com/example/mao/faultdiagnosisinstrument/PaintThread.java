package com.example.mao.faultdiagnosisinstrument;

import android.graphics.Paint;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mao on 2016/12/16.
 */
public class PaintThread extends  Thread {

    SurfaceView surfaceView;
    Paint paint;

    int X_OFFSET;
    int centerY;//坐标轴中心
    int cx ;
    int startX;
    Timer timer = new Timer();
    TimerTask task = null;

    public PaintThread(SurfaceView surfaceView, Paint paint){
        this.surfaceView=surfaceView;
        this.paint=paint;
    }

//    public void run() {
//
//        cx = X_OFFSET;
//        startX=5;
//        drawBack(holder,X_OFFSET);
//        if(task != null)
//        {
//            task.cancel();
//        }
//        task = new TimerTask()
//        {
//            public void run()



//            {
//
//
//                drawBack(holder,X_OFFSET);
//                Canvas canvas = holder.lockCanvas();
//                centerY=surface.getHeight()/2;
//                for (cx=X_OFFSET;cx<=surface.getWidth();cx++)
//                {
//                    int cy = source.getId() == R.id.sin ? centerY
//                            - (int) (500 * Math.sin((cx - startX) * 2 * Math.PI / 1000))
//                            : centerY - (int) (500 * Math.cos((cx - startX) * 2 * Math.PI / 1000));
//                    canvas.drawPoint(cx, cy, paint);
//                }
//                holder.unlockCanvasAndPost(canvas);
//                startX+=1;
//            }
//        };
//        timer.schedule(task , 0 , 100);
//    }
//    }

//    private SurfaceHolder holder;
//    private SurfaceView surface;
//    private Paint paint;

//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        surface = (SurfaceView) findViewById(R.id.show);
//        holder = surface.getHolder();
//        X_OFFSET = 5;
//        paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(3);
//        Button sin = (Button)findViewById(R.id.sin);
//        Button cos = (Button)findViewById(R.id.cos);
//
//        OnClickListener listener = (new OnClickListener()
//        {
//            @Override
//            public void onClick(final View source)
//            {
//                cx = X_OFFSET;
//                startX=5;
//                drawBack(holder,X_OFFSET);
//                if(task != null)
//                {
//                    task.cancel();
//                }
//                task = new TimerTask()
//                {
//                    public void run()
//                    {
//
//
//                        drawBack(holder,X_OFFSET);
//                        Canvas canvas = holder.lockCanvas();
//                        centerY=surface.getHeight()/2;
//                        for (cx=X_OFFSET;cx<=surface.getWidth();cx++)
//                        {
//                            int cy = source.getId() == R.id.sin ? centerY
//                                    - (int) (500 * Math.sin((cx - startX) * 2 * Math.PI / 1000))
//                                    : centerY - (int) (500 * Math.cos((cx - startX) * 2 * Math.PI / 1000));
//                            canvas.drawPoint(cx, cy, paint);
//                        }
//                        holder.unlockCanvasAndPost(canvas);
//                        startX+=1;
//                    }
//                };
//                timer.schedule(task , 0 , 100);
//            }
//        });
//        sin.setOnClickListener(listener);
//        cos.setOnClickListener(listener);
//        holder.addCallback(new Callback()
//        {
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format,
//                                       int width, int height)
//            {
//
//            }
//
//            @Override
//            public void surfaceCreated(final SurfaceHolder myHolder)
//            {
//                drawBack(holder,X_OFFSET);
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder)
//            {
//                timer.cancel();
//            }
//        });
//    }
//    private void drawBack(SurfaceHolder holder, int X_OFFSET)
//    {
//        Canvas canvas = holder.lockCanvas();
//        canvas.drawColor(Color.WHITE);
//        Paint p = new Paint();
//        p.setColor(Color.BLACK);
//        p.setStrokeWidth(5);
//        canvas.drawLine(X_OFFSET ,surface.getHeight()/2 , surface.getWidth() ,surface.getHeight()/2 , p);
////    绘制x轴
//        canvas.drawLine(X_OFFSET , 0 , X_OFFSET , surface.getHeight() , p);
////    绘制y轴
//        holder.unlockCanvasAndPost(canvas);
//        holder.lockCanvas(new Rect(0 , 0 , 0 , 0));
//        holder.unlockCanvasAndPost(canvas);
//    }




}
