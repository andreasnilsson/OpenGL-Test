package com.enighma.testopengl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import common.ObjLoader;
import common.ObjModel;
import common.ShaderHelper;
import common.TextureHelper;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class StandardModel extends GLES20 {

    public static float attAmbient  = 1f;

    public static float attDiffuse  = 0.4f;

    public static float attSpecular = 0.3f;
    public static float blue    = 1f;

    public static float green   = 1f;
    public static float red     = 1f;
    private Context mContext;

   
    //handles
    private int mMVPHandle      = -1;
    private int mNormalHandle   = -1;
    private int mPositionHandle = -1;
    private int mTex0Handle     = -1;
    private int mTexCoordHandle = -1;
    private int mTextureId      = -1;
    private int mAttAmbientHandle;
    private int mAttDiffuseHandle;
    private int mAttSpecularHandle;
    private int mColorHandle    = -1;
    private int mDyHandle;
    private int mDxHandle;
    
    
    private float mDx;
    private float mDy;
    
    private int mEnvTex0Handle;
    private int mEnvTexId;
    
    ObjModel model = null;
    
    private int mProgram = 0;
    public StandardModel(Context context, Point position, Point size) {
        mContext = context;
    }
    public void draw(float[] mvp) {
        glUseProgram(mProgram);
        
        //bind mvp matrix
        glUniformMatrix4fv(mMVPHandle, 1, false, mvp, 0);

        // Bind uniforms
        glUniform1i(mTex0Handle, 0);
        glUniform1i(mEnvTex0Handle, 1);
        glUniform3f(mColorHandle, red, green, blue);
        glUniform1f(mAttSpecularHandle, attSpecular);
        glUniform1f(mAttDiffuseHandle, attDiffuse);
        glUniform1f(mAttAmbientHandle, attAmbient);
        
        // activate texture and texture unit to render with
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mEnvTexId);

        // enable vertex arrays for vertex texCoord and normal data
        glEnableVertexAttribArray(mPositionHandle);
        glEnableVertexAttribArray(mNormalHandle);
        glEnableVertexAttribArray(mTexCoordHandle);
        glDrawArrays(GL_TRIANGLES, 0, model.getmNFaces() * 3);
        glFlush();
    }
    
    public void draw(float[] m, float[] v, float[] p) {

    }

    public void onCreate() {

    	
        ObjLoader loader = new ObjLoader();
        try {
            InputStream is = mContext.getAssets().open("models/cube.obj");
            //InputStream is = mContext.getAssets().open("models/1.obj");

            model = loader.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        glEnable(GL_TEXTURE_2D);
        // glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        // glCullFace(GL_FRONT);

        if (mProgram == 0) {
            mProgram = glCreateProgram();
        }

        glUseProgram(mProgram);

        // initialize vertex Buffer for triangle
        // (# of coordinate values * 8 bytes per float)

        String mURIFragmentShader = "shaders/simpleFragmentShader.glsl";
        String mURIVertexShader = "shaders/simpleVertexShader.glsl";
        ShaderHelper.compileAndLinkShaders(mContext, mProgram, mURIFragmentShader, mURIVertexShader);

        mTextureId = TextureHelper.loadTextureToOGL(mContext, "textures/jay.png", mProgram, true);
        mEnvTexId = TextureHelper.loadTextureToOGL(mContext, "textures/sphereMap1.jpeg", mProgram, true);

        mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
        mTexCoordHandle = glGetAttribLocation(mProgram, "texCoord");
        mNormalHandle = glGetAttribLocation(mProgram, "aNormal");

        mMVPHandle = glGetUniformLocation(mProgram, "mvp");
        mTex0Handle = glGetUniformLocation(mProgram, "texture0");
        mEnvTex0Handle = glGetUniformLocation(mProgram, "envTex0");
        mColorHandle = glGetUniformLocation(mProgram, "color");

        mDxHandle = glGetUniformLocation(mProgram, "dx");
        mDyHandle = glGetUniformLocation(mProgram, "dy");
        
        mAttSpecularHandle = glGetUniformLocation(mProgram, "attSpecular");
        mAttDiffuseHandle = glGetUniformLocation(mProgram, "attDiffuse");
        mAttAmbientHandle = glGetUniformLocation(mProgram, "attAmbient");

        glUniform1f(mAttSpecularHandle, attSpecular);
        glUniform1f(mAttDiffuseHandle, attDiffuse);
        glUniform1f(mAttAmbientHandle, attAmbient);
        
        
        glUniform1f(mDxHandle, mDx);
        glUniform1f(mDyHandle, mDy);

        glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, false, 0, model.vertices);
        glVertexAttribPointer(mTexCoordHandle, 2, GL_FLOAT, false, 0, model.uvCoords);
        glVertexAttribPointer(mNormalHandle, 3, GL_FLOAT, false, 0, model.normals);

    }

}
