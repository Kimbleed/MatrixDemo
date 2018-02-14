package com.example.a49479.matrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 49479 on 2017/12/10.
 */

public class MatrixView extends View {

    private Paint paint;

    //图片中心点
    private Point center;

    private Point offset;

    private Bitmap bitmap;

    private int bw, bh;

    private Matrix matrix;


    //图片matrix 状态
    public int state = STATE_NORMAL;

    public static final int STATE_NORMAL = 1;
    public static final int STATE_90DEGREE_ROTATE = 2;
    public static final int STATE_TRANSLATE = 3;


    //
    Point mDown = new Point();

    public MatrixView(Context context) {
        super(context);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (state) {
            case STATE_NORMAL:
                normalBitmap(canvas);
                break;
            case STATE_90DEGREE_ROTATE:
                rotateBitmap(canvas);
                break;
            case STATE_TRANSLATE:
                translateBitmap(canvas);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fate, opts);
        matrix = new Matrix();
        bw = bitmap.getWidth();
        bh = bitmap.getHeight();
        center = new Point();
        center.set(bw / 2, bh / 2);
        offset = new Point();
    }

    public void translateBitmap(Canvas canvas){
        Log.i("MatrixView","center:"+center.x+" "+center.y);
        Log.i("MatrixView","offset:"+offset.x+" "+offset.y);
        float[] src = {center.x, center.y};
        float[] dst = {center.x-offset.x, center.y-offset.y};
        matrix.setPolyToPoly(src, 0, dst, 0, 1);
        canvas.drawBitmap(bitmap, matrix, paint);
        center.x =center.x-offset.x;
        center.y = center.y-offset.y;
    }

    public void rotateBitmap(Canvas canvas) {
        float[] src = {center.x, center.y, center.x + bw / 2, 0};
        float[] dst = {center.x, center.y, center.x + bh / 2, center.y + bw / 2};
        matrix.setPolyToPoly(src, 0, dst, 0, 2);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public void scaleBitmap(){

    }

    public void normalBitmap(Canvas canvas) {
        float[] src = {center.x, center.y};
        float[] dst = {center.x, center.y};
        matrix.setPolyToPoly(src, 0, dst, 0, 1);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public void change(int state) {
        this.state = state;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //让view内所有内容跟随手指移动
//                scrollBy(mDown.x-(int)event.getX(),mDown.y-(int)event.getY());
//                mDown.x= (int)event.getX();
//                mDown.y = (int)event.getY();


                //只让图片移动
                offset.x = mDown.x-(int)event.getX();
                offset.y = mDown.y-(int)event.getY();
                invalidate();

                break;
            case MotionEvent.ACTION_DOWN:
                state = STATE_TRANSLATE;
                mDown.x = (int) event.getX();
                mDown.y = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}

