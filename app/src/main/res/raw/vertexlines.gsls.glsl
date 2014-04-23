attribute vec4 aPosition;

uniform mat4 projection;
uniform mat4 model;
uniform mat4 view;

void main() {
    gl_Position = projection*view*model*aPosition;
}