package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class GeneralUtil {
    /**
     * Converts an inputstream object to a java String 
     * 
     * @param in
     * @return
     */
    public static String inputStreamToString(InputStream in){
        StringBuilder sb = new StringBuilder();        
        InputStreamReader reader = new InputStreamReader(in);
        try {            
            int c = 0; 
            while((c = reader.read()) > -1){
                sb.append((char)c);
            }
            in.close();
        } catch (IOException e) {
            Log.e("IO", e.getMessage(), e);
        }        
                
        return sb.toString();
    }
}
