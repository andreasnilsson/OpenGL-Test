package common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import android.content.Context;

public class GLModelHelper {

	public static void writeModelToFile(ObjModel model, String filename, Context context){
		System.out.println();
		String FILENAME = "test_model.globj";
		String string = "hello world!";
		
		
		
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream outStream = new ObjectOutputStream(fos);
			
			for(int i=0; i< model.vertices.capacity(); i++){
				float f = model.vertices.get(i);
				System.out.println(f);
			}
			
			
			
			//fos.write
			//fos.write(string.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
