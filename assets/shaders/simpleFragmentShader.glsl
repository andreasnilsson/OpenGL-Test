precision mediump float;

uniform float dx;
uniform float dy;
uniform vec3 color;

uniform float attAmbient;
uniform float attDiffuse;
uniform float attSpecular;


varying vec2 tc;
varying vec4 n;
varying vec3 posW;


//texture
uniform sampler2D texture0;
//specular
uniform sampler2D envTex0;

void main()
{
	vec3 eye = vec3(0.0,0.0,-1.0);
	vec4 normal = normalize(n);
	vec3 i = posW - eye;
	vec3 r = normalize(reflect(i,n.xyz));

	float m = 2.0* sqrt(r.x*r.x + r.y*r.y + (r.z +1.0)*(r.z +1.0));
	float u = r.x/m + 0.5;
	float v = r.y/m + 0.5;
	
	float att = max(dot(normal.xyz, eye), 0.0);
	
	
	//gl_FragColor = (1.0-ambient)*texture2D(texture0, tc) * att +  0.1*ambient;
	//gl_FragColor = att*0.3*texture2D(texture0, tc) + 0.6*texture2D(envTex0, vec2(u, -v)) + 0.1*ambient;
	//env
	
	vec4 ambient = attAmbient * vec4(color, 1.0);
	vec4 diffuse = attDiffuse * att*vec4(color, 1.0);
	vec4 specular = attSpecular * texture2D(envTex0, vec2(u, -v));
	
	
	gl_FragColor = specular + diffuse + ambient;
	
	//test normals
	//gl_FragColor = vec4(abs(n.x), abs(n.y), abs(n.z), 0.0);
	
	//test tc
	//gl_FragColor = vec4(abs(tc.x), abs(tc.x), abs(tc.x), 0.0);
	//gl_FragColor = vec4(abs(tc.y), abs(tc.y), abs(tc.y), 0.0);
	
}
