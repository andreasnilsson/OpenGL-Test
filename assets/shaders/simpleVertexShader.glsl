attribute vec4 vPosition;
attribute vec2 texCoord;
attribute vec3 aNormal;

uniform mat4 mvp;

varying vec2 tc;
varying vec4 n;
varying vec3 posW;
 
void main()
{  
	
  	//interpolate texture coordinates
 	tc = texCoord; 	
 	
 	posW = (mvp * vPosition).xyz;
 	
 	
 		 	
 	n = normalize( mvp * vec4(aNormal, 0.0)); 	 	
 	
 	
 	
 	
 	//transform position with Model View Projection Matrixes
 	gl_Position = mvp * vPosition;
}
 