package shapes;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import loaders.ShaderHandles;

/**
 * Created by Ken on 11/04/2014.
 */
public class NormalSquare {

    float vertices[] = {
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f
    };

    float normals[] = {
//            0.0f,0.0f,1.0f,
//            0.0f,0.0f,1.0f,
//            0.0f,0.0f,1.0f,
//
//            0.0f,0.0f,1.0f,
//            0.0f,0.0f,1.0f,
//            0.0f,0.0f,1.0f
            -0.33f,0.33f,0.33f,
            -0.33f,-0.33f,0.33f,
            0.33f,-0.33f,0.33f,

            -0.33f,0.33f,0.33f,
            0.33f,-0.33f,0.33f,
            0.33f,0.33f,0.33f
    };


    float uvCords[] = {
            0.0f,1.0f,
            0.0f,0.0f,
            1.0f,0.0f,

            0.0f,1.0f,
            1.0f,0.0f,
            1.0f,1.0f
    };

    float tangents[] = {
            1.0f,0.0f,0.0f,
            1.0f,0.0f,0.0f,
            1.0f,0.0f,0.0f,

            1.0f,0.0f,0.0f,
            1.0f,0.0f,0.0f,
            1.0f,0.0f,0.0f,

    };

    public float color[] = new float[4];
    int buffers[] = new int[4];
    int positions,normalValues,texcords,tanbuffidx;

    FloatBuffer vertexBuffer,normalBuffer,texBuffer,tanBuffer;

    public NormalSquare()
    {
        // buffer for cube vertices
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * vertices.length);
        buffer.order(ByteOrder.nativeOrder());

        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // buffer for cube normals
        ByteBuffer nBuffer = ByteBuffer.allocateDirect(4 * normals.length);
        nBuffer.order(ByteOrder.nativeOrder());

        normalBuffer = nBuffer.asFloatBuffer();
        normalBuffer.put(normals);
        normalBuffer.position(0);

        //buffer for texture cords
        ByteBuffer tBuffer = ByteBuffer.allocateDirect(4 * uvCords.length);
        tBuffer.order(ByteOrder.nativeOrder());

        texBuffer = tBuffer.asFloatBuffer();
        texBuffer.put(uvCords);
        texBuffer.position(0);

        // buffer for texture cords
        ByteBuffer taBuffer = ByteBuffer.allocateDirect(4 * tangents.length);
        taBuffer.order(ByteOrder.nativeOrder());

        tanBuffer = taBuffer.asFloatBuffer();
        tanBuffer.put(tangents);
        tanBuffer.position(0);


        //create VBO map
        GLES20.glGenBuffers(4, buffers, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, texBuffer.capacity() * 4, texBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalBuffer.capacity() * 4, normalBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[3]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, tanBuffer.capacity() * 4, tanBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        positions = buffers[0];
        texcords = buffers[1];
        normalValues = buffers[2];
        tanbuffidx = buffers[3];
        vertexBuffer = null;
        texBuffer = null;
        normalBuffer = null;
    }

    //texture draw
    public void draw( float[] projection, float[] view, float[] model, ShaderHandles Shader, int textures,int textures2)
    {

        //position
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positions);
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);
        GLES20.glVertexAttribPointer(Shader.positionHandle, 3, GLES20.GL_FLOAT, false,	0, 0);

        //normals
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalValues);
        GLES20.glEnableVertexAttribArray(Shader.normalHandle);
        GLES20.glVertexAttribPointer(Shader.normalHandle, 3, GLES20.GL_FLOAT, false,0, 0);

        //tangent
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, tanbuffidx);
        GLES20.glEnableVertexAttribArray(Shader.tangentHandle);
        GLES20.glVertexAttribPointer(Shader.tangentHandle, 3, GLES20.GL_FLOAT, false,	0, 0);

        // Bind the texture to this unit.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures);
        GLES20.glUniform1i(Shader.mTextureUniformHandle.get(0), 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures2);
        GLES20.glUniform1i(Shader.mTextureUniformHandle.get(1),1);

        //texture
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, texcords);
        GLES20.glEnableVertexAttribArray(Shader.mTextureCoordinateHandle);
        GLES20.glVertexAttribPointer(Shader.mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,0, 0);

        //send uniforms
        GLES20.glUniformMatrix4fv(Shader.projectionHandle, 1, false, projection, 0);
        GLES20.glUniformMatrix4fv(Shader.viewHandle, 1, false, view, 0);
        GLES20.glUniformMatrix4fv(Shader.modelHandle, 1, false, model, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length/3);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
