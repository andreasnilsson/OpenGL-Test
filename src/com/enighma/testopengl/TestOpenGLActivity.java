package com.enighma.testopengl;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class TestOpenGLActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
	MyRenderer mRenderer;
	private float mPreviousY;
	private float mPreviousX;
	boolean viewTexture = false;

    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    
    boolean isColorChangeMode = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hybrid_view);

		
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);
        
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        
        
        
        int rValue = (int) (TexturedCube.red * 255);
        int gValue = (int) (TexturedCube.green * 255);
        int bValue = (int) (TexturedCube.blue * 255);
        
        redSeekBar.setProgress(rValue);
        greenSeekBar.setProgress(gValue);
        blueSeekBar.setProgress(bValue);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.reload_shaders:
			System.out.println("butt press =)");
			Intent myIntent = new Intent(this, HybridView.class);
			startActivity(myIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float value = progress/255f;
        if(isColorChangeMode){

            if (seekBar == redSeekBar) {
                TexturedCube.red = value;
            }
            if(seekBar == greenSeekBar){
                TexturedCube.green = value;
            }
            if(seekBar == blueSeekBar){
                TexturedCube.blue = value;
            }   
        }else{

            if (seekBar == redSeekBar) {
                TexturedCube.attAmbient = value;
            }
            if(seekBar == greenSeekBar){
                TexturedCube.attDiffuse = value;
            }
            if(seekBar == blueSeekBar){
                TexturedCube.attSpecular = value;
            }
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        
    }
}
