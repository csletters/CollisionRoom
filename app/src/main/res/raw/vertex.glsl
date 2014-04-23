attribute vec4 aPosition;
attribute vec3 normal;
attribute vec2 aTexCord;
attribute vec3 tangent;
uniform mat4 projection;
uniform mat4 model;
uniform mat4 view;

varying vec3 viewVector;
varying vec3 light;
varying vec2 vTexCord;



void main() {

    vec3 lightPos = vec3(0.0,0.0,5.0);
    vec4 vPosition = view*model*aPosition;

    vec3 normalizednormal = normalize(mat3(view*model)*normal);
    vec3 normalizedtangent = normalize(mat3(view*model)* tangent);
    vec3 bittangent = cross(normalizednormal,normalizedtangent);

    vec3 vVector = -vPosition.xyz;
    viewVector = normalize(vec3(dot(vVector,normalizedtangent),dot(vVector,bittangent),dot(vVector,normalizednormal)));

    vec3 lightDir = lightPos - vPosition.xyz;
    light = normalize(vec3(dot(lightDir,normalizedtangent),dot(lightDir,bittangent),dot(lightDir,normalizednormal)));
    vTexCord = aTexCord;
	gl_Position = projection*view*model*aPosition;
}