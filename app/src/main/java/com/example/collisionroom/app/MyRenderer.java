package com.example.collisionroom.app;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import loaders.RawResourceReader;
import loaders.ShaderHandles;
import loaders.ShaderHelper;
import loaders.TextureHelper;
import shapes.Cube;
import shapes.NormalSquare;
import shapes.Square;

public class MyRenderer implements Renderer {

	int widthView,heightView;
	Context mActivityContext;

	float[] projection = new float[16];
	float[] view = new float[16];
	float[] model = new float[16];
    float[] rot = new float[16];
	float ratio;
	int[] wallTexture;
    int[] normalTexture;
    int[] mapTexture1;
    float startTime = 0;
    float elapsedTime = 0.0f;
    float totaltime = 0.0f;
    int fps = 0;
    int xTranslation = 0;
    int yTranslation = 0;
	Cube object;
    Square object2;
    NormalSquare object3;
	ShaderHandles shader, scaleShader,lineShader;
    boolean isPressed = false;


    public MyRenderer(final Context activityContext)
	{
		mActivityContext = activityContext;
	}
	@Override
	public void onDrawFrame(GL10 gl) {
        Matrix.setIdentityM(rot,0);
		// TODO Auto-generated method stub
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setLookAtM(view, 0, xTranslation, yTranslation, 5, xTranslation, yTranslation, 0, 0, 1, 0);
//        Matrix.setLookAtM(view, 0, (float)(5*Math.sin(deg2rad((float)angle))), yTranslation, (float)(5*Math.cos(deg2rad((float)angle))), xTranslation, yTranslation, 0, 0, 1, 0);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(shader.programHandle);
        Matrix.setIdentityM(model,0);
        Matrix.rotateM(model, 0, angle, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(model, 0, -0.5f, -0.5f, 0.5f);
        object.updateAABB(model);
        object.draw(projection, view, model, shader, wallTexture[0],normalTexture[0]);
        Matrix.setIdentityM(model,0);

        GLES20.glUseProgram(scaleShader.programHandle);
        Matrix.scaleM(model,0,20,20,80);
        Matrix.translateM(model, 0, -0.5f, -0.5f, 0.5f);
        object.draw(projection, view, model, shader, wallTexture[0],normalTexture[0]);
        Matrix.setIdentityM(model,0);

        GLES20.glUseProgram(lineShader.programHandle);
        object.drawAABB(projection,view,model,shader);
        Matrix.setIdentityM(model,0);

        fps++;

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		ratio = (float)width/((float)height);
		Matrix.perspectiveM(projection, 0, 60f, ratio, 0.01f, 600f);
		Matrix.setLookAtM(view, 0, 1.0f, 1.0f, 40, 0, 0.0f, 0, 0, 1, 0);
		Matrix.setIdentityM(model, 0);
		widthView = width;
		heightView = height;

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClearDepthf(1.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDepthFunc(GLES20.GL_LESS);
		GLES20.glDepthMask(true);

		
		// create shader program for objects with basic textures and lighting
        shader = new ShaderHandles();
        shader.programHandle = createShader(R.raw.vertex, R.raw.fragment);
		initBasicHandles(shader);

        scaleShader = new ShaderHandles();
        scaleShader.programHandle = createShader(R.raw.vertexscale, R.raw.fragmentscale);
        initBasicHandles(scaleShader);

        lineShader = new ShaderHandles();
        lineShader.programHandle = createShader(R.raw.vertexlines, R.raw.fragmentlines);
        initBasicHandles(lineShader);

        wallTexture = TextureHelper.loadTexture(mActivityContext, R.drawable.normalmappic);
        normalTexture = TextureHelper.loadTexture(mActivityContext, R.drawable.normalmap);

        object2 = new Square();
		object = new Cube();
        object3 = new NormalSquare();

	}
	
	
	public int createShader(int vertex, int fragment) {
		String vertexShaderCode = RawResourceReader.readTextFileFromRawResource(mActivityContext, vertex);
		String fragmentShaderCode = RawResourceReader.readTextFileFromRawResource(mActivityContext, fragment);

		int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		int mProgram;

		mProgram = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle);

		return mProgram;
	}

	public void initBasicHandles( ShaderHandles shader) {
		// attributes
		shader.positionHandle = GLES20.glGetAttribLocation(shader.programHandle,"aPosition");
        shader.mTextureCoordinateHandle = GLES20.glGetAttribLocation(shader.programHandle, "aTexCord");
        shader.normalHandle = GLES20.glGetAttribLocation(shader.programHandle,"normal");
        shader.tangentHandle = GLES20.glGetAttribLocation(shader.programHandle,"tangent");

		// uniforms
		shader.modelHandle = GLES20.glGetUniformLocation(shader.programHandle, "model");
		shader.viewHandle = GLES20.glGetUniformLocation(shader.programHandle, "view");
		shader.projectionHandle = GLES20.glGetUniformLocation(shader.programHandle,	"projection");
        shader.mTextureUniformHandle.add(GLES20.glGetUniformLocation(shader.programHandle, "uTexture"));
        shader.mTextureUniformHandle.add(GLES20.glGetUniformLocation(shader.programHandle, "uTextureNormal"));
	}


    public void moveCamera(float x, float y) {

        if(!isPressed)
            new cameraMovement().execute(x,y);
    }

    public class cameraMovement extends AsyncTask<Float, Void, Float>
    {
        @Override
        protected Float doInBackground(Float... params) {
            return move(params[0],params[1]);
        }

        private Float move(float x, float y)
        {
            isPressed = true;
            float startTime = System.nanoTime();
            float diff;
            while(isPressed)
            {
                diff = ((System.nanoTime() - startTime) / 1000000000.0f);
                startTime = System.nanoTime();
                elapsedTime += diff;
                if(elapsedTime > 0.05)
                {
                    if(x < widthView/4)
                        xTranslation -= 1;
                    if(x > 3*widthView/4)
                        xTranslation += 1;
                    if(y < heightView/4)
                        yTranslation += 1;
                    if(y > 3*heightView/4)
                        yTranslation -= 1;
                    elapsedTime = 0;
                }

            }

            return 10.0f;
        }
    }

    public double deg2rad(float deg) {
        return deg * (Math.PI / 180);
    }

}
