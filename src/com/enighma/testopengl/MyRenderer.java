package com.enighma.testopengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.content.Context;
import android.content.res.AssetManager;
public class MyRenderer extends GLES20 implements Renderer {
    
    
    private static final float FAR_PLANE = 100f;
	private static final float NEAR_PLANE = 0.1f;
	private static final int FOV = 45;
	private Context mContext = null;
    float eye[] = {0f, 0f, 15.5f};
    
    // text related
    Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
    Canvas textCanvas = new Canvas(bitmap);

    float bgBrightness = 0.3f;

    float[] mvp = new float[16];
    float[] m 	= new float[16];
    float[] v 	= new float[16];
    float[] p 	= new float[16];
    float[] rx 	= new float[16];
    float[] ry 	= new float[16];
    float[] t  = new float[16];
    
    float angleX = 0f;
    float angleY = 0f;
    float posX = 0f;
    float posY = 0f;
    
    TexturedCube rectangle;
    private float mScale = 1.0f;
    
    
    
    public MyRenderer(Context context, GLSurfaceView master) {
        mContext = context;
        rectangle = new TexturedCube(context, new Point(0, 0), new Point(400, 400));
        
        
    }

  
    public void rotate(float dx, float dy){
        float scale = 5f;
        
        dx = dx/scale;
        dy = dy/scale;        

        angleY += dy;
        angleX += dx;
    }

    public void scale(float scale) {
        mScale = scale;
        // TODO Auto-generated method stub

    }

    
    
    
    @Override
    public void onDrawFrame(GL10 gl) {                
        gl.glClearColor(bgBrightness, bgBrightness, bgBrightness, 1f);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -10.0f;
     
        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;
     
        Matrix.setIdentityM(t, 0);
        Matrix.setIdentityM(m, 0);
        
        Matrix.translateM(t, 0, posX, posY, 0f);
        
        Matrix.setRotateM(ry, 0, angleX, 0f, 1f, 0f);
        Matrix.setRotateM(rx, 0, angleY, 1f, 0f, 0f);
        Matrix.multiplyMM(m, 0, rx, 0, ry, 0);
 
        
        Matrix.multiplyMM(m, 0, m, 0, t, 0);
        
        Matrix.scaleM(m, 0, mScale, mScale, mScale);
        
        Matrix.setLookAtM(v, 0, eye[0], eye[1], eye[2], lookX, lookY, lookZ, upX, upY, upZ);          
        Matrix.multiplyMM(mvp, 0, v, 0, m, 0);
        Matrix.multiplyMM(mvp, 0, p, 0, mvp, 0);
 		
        rectangle.draw(mvp);
        
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        float aspect = (float)width/height;
        Matrix.perspectiveM(p, 0, FOV, aspect, NEAR_PLANE, FAR_PLANE);
        //System.out.println("GL VERSION: " + glGetString(GL_VERSION));
        System.out.println("Surface changed");

    }


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        Matrix.setIdentityM(rx, 0);
        Matrix.setIdentityM(ry, 0);
        Matrix.setIdentityM(t, 0);
        Matrix.setIdentityM(m, 0);
        //initShapes();        
        rectangle.onCreate();
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }


    public void translate(float dx, float dy) {
        float scale = 0.01f;
        posX = dx*scale;
        posY = -dy*scale;
        // TODO Auto-generated method stub
        
    }
    

    

    

}
