package com.enighma.testopengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.graphics.Point;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class TexturedRectangle extends GLES20{
    Point mPos;
    Point mSize;
    
    public int mProgram = 0;
    
    float mDx;
    float mDy;
    
    //data
    private FloatBuffer mTriangleVB;
    private FloatBuffer mTextureVB;
    private int mPositionHandle = -1;
    private int mTextureHandle  = -1;

    public TexturedRectangle(Point position, Point size) {
        mPos = position;
        mSize = size;
    }
    
    
    public void onCreate() {
        
        if(mProgram == 0){
            mProgram = glCreateProgram();    
        }
        

        int sizeOfFloat = 8;        

        
        float triangleCoords[] = {                
                0.0f, 0.0f, 0.0f, 
                1.0f, 0.0f, 0.0f, 
                0.0f, 1.0f, 0.0f,                 
                1.0f, 1.0f, 0.0f,
                1.0f, 0.0f, 0.0f
                };
        
        float texCoords[] = {
                1.0f, 0.0f,
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
        };
        
        ByteBuffer vbb = ByteBuffer.allocateDirect(triangleCoords.length * sizeOfFloat);
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        mTriangleVB = vbb.asFloatBuffer(); // create a floating point buffer from the ByteBuffer
        mTriangleVB.put(triangleCoords); // add the coordinates to the FloatBuffer
        mTriangleVB.position(0); // set the buffer to read the first coordinate

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * sizeOfFloat);
        tbb.order(ByteOrder.nativeOrder());
        mTextureVB = tbb.asFloatBuffer();
        mTextureVB.put(triangleCoords); // add the coordinates to the FloatBuffer
        mTextureVB.position(0); // set the buffer to read the first coordinate
    }
    
    
    
    public void attachShaders(int vertexShader, int fragmentShader){
        glAttachShader(mProgram, vertexShader);
        glAttachShader(mProgram, fragmentShader);
        glLinkProgram(mProgram);
        
        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mTextureHandle  = glGetAttribLocation(mProgram, "texCoord");
        glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, false, 0, mTriangleVB);
        glVertexAttribPointer(mTextureHandle, 2, GL_FLOAT, false, 0, mTextureVB);
    }
    
    
    public void draw() {        
        glUseProgram(mProgram);
        
        
        glEnableVertexAttribArray(mPositionHandle);

        glEnableVertexAttribArray(mTextureHandle);
        
        // Draw the triangle strip
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 5);
        
        glUseProgram(0);
    }
    
    
    
}
