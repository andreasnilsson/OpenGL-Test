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

public class TexturedCube extends GLES20 {

    ObjModel teapot = null;

    private int mProgram = 0;

    private float mDx;
    private float mDy;

    private int mPositionHandle = -1;
    private int mTexCoordHandle = -1;
    private int mMVPHandle      = -1;
    private int mNormalHandle   = -1;
    private int mTextureId      = -1;
    private int mTex0Handle     = -1;
    private int mColorHandle    = -1;

    
    
    private Context mContext;

    public static float red     = 1f;
    public static float green   = 1f;
    public static float blue    = 1f;

    public static float attSpecular = 0.3f;
    public static float attDiffuse  = 0.4f;
    public static float attAmbient  = 1f;

    public TexturedCube(Context context, Point position, Point size) {
        mContext = context;
    }

    private int mEnvTex0Handle;

    private int mEnvTexId;

    private int mDxHandle;

    private int mDyHandle;

    private int mAttSpecularHandle;

    private int mAttDiffuseHandle;

    private int mAttAmbientHandle;

    public void onCreate() {
        // test loading obj file

        ObjLoader loader = new ObjLoader();
        try {
            InputStream is = mContext.getAssets().open("models/cube.obj");
            //InputStream is = mContext.getAssets().open("models/1.obj");

            teapot = loader.loadObject(is);
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

        glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, false, 0, teapot.vertices);
        glVertexAttribPointer(mTexCoordHandle, 2, GL_FLOAT, false, 0, teapot.uvCoords);
        glVertexAttribPointer(mNormalHandle, 3, GL_FLOAT, false, 0, teapot.normals);

    }

    public void draw(float[] mvp) {
        glUseProgram(mProgram);

        glUniformMatrix4fv(mMVPHandle, 1, false, mvp, 0);

        // Bind uniforms
        glUniform1i(mTex0Handle, 0);
        glUniform1i(mEnvTex0Handle, 1);

        glUniform3f(mColorHandle, red, green, blue);
        
        glUniform1f(mAttSpecularHandle, attSpecular);
        glUniform1f(mAttDiffuseHandle, attDiffuse);
        glUniform1f(mAttAmbientHandle, attAmbient);
        
        
        // glFrontFace(GL_CCW );
        // activate texture and texture unit to render with
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mEnvTexId);

        // enable vertex arrays for vertex/texture coordinates
        glEnableVertexAttribArray(mPositionHandle);
        glEnableVertexAttribArray(mNormalHandle);
        glEnableVertexAttribArray(mTexCoordHandle);
        glDrawArrays(GL_TRIANGLES, 0, teapot.getmNFaces() * 3);

        glFlush();
    }

}
