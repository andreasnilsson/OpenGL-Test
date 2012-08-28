package com.enighma.testopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.SeekBar;

public class HybridGLView extends GLSurfaceView {

    private float mPreviousX;
    private float mPreviousY;

    private MyRenderer mRenderer;
    private int fingersUsed;
    private float savedX;
    private float savedY;
    private float savedDistance;

    public HybridGLView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 4);

        mRenderer = new MyRenderer(context, this);
        setRenderer(mRenderer);

    }

    public HybridGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 4);

        mRenderer = new MyRenderer(context, this);
        setRenderer(mRenderer);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // 

    float oldScale = 1f;
    float scale = 1f;
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
            fingersUsed = 1;
            savedX = event.getX();
            savedY = event.getY();
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            savedX = (event.getX(0) + event.getX(1)) / 2;
            savedY = (event.getY(0) + event.getY(1)) / 2;
            savedDistance = distance(event);
            fingersUsed = 2;
            break;
        case MotionEvent.ACTION_POINTER_UP:
            oldScale = scale*oldScale;
            fingersUsed = 0;
            break;
        case MotionEvent.ACTION_UP:
            fingersUsed = 0;
            break;
        case MotionEvent.ACTION_MOVE:
            float x = event.getX();
            float y = event.getY();
            if (fingersUsed == 1) {
                float dx = x - savedX;
                float dy = y - savedY;
                
                mRenderer.rotate(dx, dy);
                
                savedX = x;
                savedY = y;
            } else if (fingersUsed == 2) {
                float midX = (event.getX(0) + event.getX(1)) / 2;
                float midY = (event.getY(0) + event.getY(1)) / 2;
                float dx = midX - savedX;
                float dy = midY - savedY;
                float newDistance = distance(event);
                scale = newDistance / savedDistance;
                mRenderer.translate(dx, dy);
                mRenderer.scale(scale*oldScale);
            }
            break;
        default:
            break;
        }
        return true;
    }
    
    private float distance(MotionEvent event) {
        float xDiff = event.getX(1) - event.getX(0);
        float yDiff = event.getY(1) - event.getY(0);
        return FloatMath.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // TODO Auto-generated method stub
        super.surfaceChanged(holder, format, w, h);
    }

}
