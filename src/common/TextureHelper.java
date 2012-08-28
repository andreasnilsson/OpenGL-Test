package common;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class TextureHelper extends GLES20 {

	private static int mMinFilter = GL_LINEAR;
	private static int mMaxFilter = GL_LINEAR;
	
	
	public static void setMinMaxFilters(int min, int max){
		mMaxFilter = max;
		mMinFilter = min;		
	}
	
	public static int loadTextureToOGL(Bitmap bitmap, int program, boolean createMipMap) {
		int texIds[] = {-1};    	
    	GLES20.glGenTextures(1, texIds, 0);
    	int textureID = texIds[0];
		
   	 
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);       
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, mMinFilter);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, mMaxFilter);
    	GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    	
    	if(createMipMap){
    		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);	
    	}    		
		bitmap.recycle();
		return textureID;
	}
	
	
	

	public static int loadTextureToOGL(Context context, String uri, int program, boolean createMipMap) {
		return loadTextureToOGL(loadFromAssets(context, uri), program, createMipMap);

	}
	/**
	 * Loads bitmap with resource uri.
	 * 
	 * @param context
	 * @param uri
	 * @param opts
	 * @return
	 */
	public static Bitmap loadFromAssets(Context context, String uri){		
		return loadFromAssets(context, uri, null);		
	}

	/**
	 * Loads bitmap with resource uri and provided options
	 * 
	 * @param context
	 * @param uri path to asset
	 * @param opts can be null
	 * @return
	 */
	public static Bitmap loadFromAssets(Context context, String uri, Options opts){		
		Bitmap bitmap  = null;		
    	InputStream is = null; 
    	try {
			is = context.getAssets().open(uri);
			
	    	if(opts == null){
	    		bitmap = BitmapFactory.decodeStream(is);	
	    	}else{
	    		bitmap = BitmapFactory.decodeStream(is, null, opts);
	    	}
	    	is.close();
		} catch (IOException e) {
			Log.e("IO", "Could not load bitmap properly", e);
			e.printStackTrace();
		}
    	
    	Log.i("INFO", "bitmap w: " + bitmap.getWidth() + " h: " + bitmap.getHeight());
    	
        return bitmap;		
	}
	
}
