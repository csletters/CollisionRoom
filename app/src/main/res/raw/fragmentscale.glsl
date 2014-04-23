precision mediump float;
uniform sampler2D uTexture;
uniform sampler2D uTextureNormal;
varying vec2 vTexCord;
varying vec3 viewVector;
varying vec3 light;
void main() {
	vec2 flipped_texcoord = vec2(vTexCord.x, 1.0 - vTexCord.y);
	vec3 v = normalize(viewVector);
	
	vec3 l = normalize(light);
	//vec3 N =normalize(normalizednormal);
	vec3 N = normalize(texture2D(uTextureNormal, flipped_texcoord).rgb *2.0 - vec3(1.0));
	vec3 R = normalize(reflect(-l,N));

	vec3 diffuse = max(dot(N,l),0.0)*texture2D(uTexture, flipped_texcoord).rgb;
	vec3 specular = max(pow(dot(R,v),128.0),0.0)*vec3(0.0);
	vec3 ambient = vec3(0.6);
	vec3 phongshading = diffuse +specular+ ambient;

	gl_FragColor = texture2D(uTexture, flipped_texcoord)*vec4(phongshading,1.0);
}