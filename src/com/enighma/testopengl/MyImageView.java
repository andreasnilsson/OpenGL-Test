package com.enighma.testopengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class MyImageView extends View {

	Bitmap mBMP;
	
	public MyImageView(Context context, Bitmap bmp) {		
		super(context);
		mBMP = bmp;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// called when view is drawn
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		// The image will be scaled so it will fill the width, and the
		// height will preserve the images aspect ration
		double aspectRatio = ((double) mBMP.getWidth()) / mBMP.getHeight();
		Rect dest = new Rect(0, 0, this.getWidth(), (int) (this.getHeight() / aspectRatio));
		canvas.drawBitmap(mBMP, null, dest, paint);
	}
	
}
