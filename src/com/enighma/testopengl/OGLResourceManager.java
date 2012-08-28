package com.enighma.testopengl;

import java.io.IOException;
import java.io.InputStream;

import common.ShaderHelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class OGLResourceManager {
    
    public static int shaderStandardTextureVS = -1;
    public static int shaderStandardTextureFS = -1;
    
    public static String uriFragmentShader = "shaders/simpleFragmentShader.glsl";
    public static String uriVertexShader   = "shaders/simpleVertexShader.glsl";  
    
    public static void compileShaders(Context context, int program){
        
        System.out.println("shaders compiled with program: " + program);
        //STANDARD TEXTURE SHADERS
        GLES20.glUseProgram(program);
        shaderStandardTextureVS = ShaderHelper.compileShader(context, GLES20.GL_VERTEX_SHADER, uriVertexShader);
        shaderStandardTextureFS = ShaderHelper.compileShader(context, GLES20.GL_FRAGMENT_SHADER, uriFragmentShader);
        
    }
    
    
    public static int loadTextures(Context context, int program){
    	
    	Bitmap logo = null;
    	InputStream is = null; 
    	try {
			is = context.getAssets().open("textures/jay.png");
			logo = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

                
        
        
        
    	GLES20.glEnable(GLES20.GL_TEXTURE_2D);
    	int textureIDs[] = {-1};
    	
    	GLES20.glGenTextures(1, textureIDs, 0);
    	 
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIDs[0]);       
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
    	GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, logo, 0);
    	
    	GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
    	
    	System.out.println("Texture Handle: " + textureIDs[0]);
    	
    	return textureIDs[0];
    }

}
