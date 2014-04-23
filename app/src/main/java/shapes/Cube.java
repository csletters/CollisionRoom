package shapes;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import loaders.ShaderHandles;

public class Cube {
    float largestX, largestY, largestZ;
    float smallestX, smallestY, smallestZ;


    float vertices[] = {
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,

            0.0f, 1.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, 0.0f,

            0.0f, 1.0f, -1.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            0.0f, 1.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            1.0f, 0.0f, -1.0f,

            0.0f, 1.0f, -1.0f,
            1.0f, 0.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            1.0f, 1.0f, -1.0f,
            1.0f, 0.0f, -1.0f,
            1.0f, 0.0f, 0.0f,

            1.0f, 1.0f, -1.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,

            0.0f, 1.0f, -1.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,

            0.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, -1.0f,

            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 0.0f, -1.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, -1.0f


    };

    float verticesAABB[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 1.0f, -1.0f, 1.0f,
            0.0f, 0.0f, -1.0f, 1.0f,

            0.0f, 0.0f, -1.0f, 1.0f,
            1.0f, 0.0f, -1.0f, 1.0f,

            1.0f, 0.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f, 1.0f,

            1.0f, 1.0f, -1.0f, 1.0f,
            0.0f, 1.0f, -1.0f, 1.0f,

            0.0f, 1.0f, -1.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, -1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 0.0f, -1.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f

    };

    float normals[] = {

            -0.33f, 0.33f, 0.33f,
            -0.33f, -0.33f, 0.33f,
            0.33f, -0.33f, 0.33f,

            -0.33f, 0.33f, 0.33f,
            0.33f, -0.33f, 0.33f,
            0.33f, 0.33f, 0.33f,

            -0.33f, 0.33f, -0.33f,
            -0.33f, -0.33f, -0.33f,
            -0.33f, -0.33f, 0.33f,

            -0.33f, 0.33f, -0.33f,
            -0.33f, -0.33f, 0.33f,
            -0.33f, 0.33f, 0.33f,


            -0.33f, 0.33f, -0.33f,
            -0.33f, -0.33f, -0.33f,
            0.33f, -0.33f, -0.33f,

            -0.33f, 0.33f, -0.33f,
            0.33f, -0.33f, -0.33f,
            0.33f, 0.33f, -0.33f,

            0.33f, 0.33f, -0.33f,
            0.33f, -0.33f, -0.33f,
            0.33f, -0.33f, 0.33f,

            0.33f, 0.33f, -0.33f,
            0.33f, -0.33f, 0.33f,
            0.33f, 0.33f, 0.33f,

            -0.33f, 0.33f, -0.33f,
            -0.33f, 0.33f, 0.33f,
            0.33f, 0.33f, 0.33f,

            -0.33f, 0.33f, -0.33f,
            0.33f, 0.33f, 0.33f,
            0.33f, 0.33f, -0.33f,

            -0.33f, -0.33f, -0.33f,
            -0.33f, -0.33f, 0.33f,
            0.33f, -0.33f, 0.33f,

            -0.33f, -0.33f, -0.33f,
            0.33f, -0.33f, 0.33f,
            0.33f, -0.33f, -0.33f


    };


    float uvCords[] = {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,

            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f


    };

    float tangents[] = {
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

    };

    public float color[] = new float[4];
    int buffers[] = new int[5];
    int positions, normalValues, texcords, tanbuffidx, aBuff;

    FloatBuffer vertexBuffer, normalBuffer, texBuffer, tanBuffer, AAbuffer;

    public Cube() {
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

        // buffer for cube vertices
        ByteBuffer AABBbuffer = ByteBuffer.allocateDirect(4 * verticesAABB.length);
        AABBbuffer.order(ByteOrder.nativeOrder());

        AAbuffer = AABBbuffer.asFloatBuffer();
        AAbuffer.put(verticesAABB);
        AAbuffer.position(0);

        //create VBO map
        GLES20.glGenBuffers(5, buffers, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, texBuffer.capacity() * 4, texBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalBuffer.capacity() * 4, normalBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[3]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, tanBuffer.capacity() * 4, tanBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[4]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, AAbuffer.capacity() * 4, AAbuffer, GLES20.GL_DYNAMIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        positions = buffers[0];
        texcords = buffers[1];
        normalValues = buffers[2];
        tanbuffidx = buffers[3];
        aBuff = buffers[4];
        vertexBuffer = null;
        texBuffer = null;
        normalBuffer = null;
        AAbuffer = null;
    }


    public void updateAABB(float[] rotatedAABB) {
        float[] tempAABB = {
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,

                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,

                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,

                1.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,

                0.0f, 1.0f, -1.0f, 1.0f,
                0.0f, 0.0f, -1.0f, 1.0f,

                0.0f, 0.0f, -1.0f, 1.0f,
                1.0f, 0.0f, -1.0f, 1.0f,

                1.0f, 0.0f, -1.0f, 1.0f,
                1.0f, 1.0f, -1.0f, 1.0f,

                1.0f, 1.0f, -1.0f, 1.0f,
                0.0f, 1.0f, -1.0f, 1.0f,

                0.0f, 1.0f, -1.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,

                0.0f, 0.0f, -1.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,

                1.0f, 1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,

                1.0f, 0.0f, -1.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f

        };

        for (int x = 0; x < 24; x++) {
            tempAABB[x * 4] = (rotatedAABB[0] * tempAABB[x * 4]) + (rotatedAABB[4] * tempAABB[(x * 4) + 1]) + (rotatedAABB[8] * tempAABB[(x * 4) + 2]) + (rotatedAABB[12] * tempAABB[(x * 4) + 3]);
            tempAABB[(x * 4) + 1] = (rotatedAABB[1] * tempAABB[x * 4]) + (rotatedAABB[5] * tempAABB[(x * 4) + 1]) + (rotatedAABB[9] * tempAABB[(x * 4) + 2]) + (rotatedAABB[13] * tempAABB[(x * 4) + 3]);
            tempAABB[(x * 4) + 2] = (rotatedAABB[2] * tempAABB[x * 4]) + (rotatedAABB[6] * tempAABB[(x * 4) + 1]) + (rotatedAABB[10] * tempAABB[(x * 4) + 2]) + (rotatedAABB[14] * tempAABB[(x * 4) + 3]);
            tempAABB[(x * 4) + 3] = (rotatedAABB[3] * tempAABB[x * 4]) + (rotatedAABB[7] * tempAABB[(x * 4) + 1]) + (rotatedAABB[11] * tempAABB[(x * 4) + 2]) + (rotatedAABB[15] * tempAABB[(x * 4) + 3]);

        }
        //determine largest and smallest X,Y,Z values

//        for(int x =0; x < 24;x++)
//            Log.w("x,y,z,w", Float.toString(tempAABB[x * 4]) + "," + Float.toString(tempAABB[(x * 4) + 1]) + "," + Float.toString(tempAABB[(x * 4) + 2]) + "," + Float.toString(tempAABB[(x * 4) + 3]));
//        Log.w("asdas","asdsad");
//        for(int x =0; x < rot.length/4;x++)
//            Log.w("x,y,z,w", Float.toString(rot[x * 4]) + "," + Float.toString(rot[(x * 4) + 1]) + "," + Float.toString(rot[(x * 4) + 2]) + "," + Float.toString(rot[(x * 4) + 3]));
//        Log.w("asdas","asdsad");
        largestX = tempAABB[0];
        largestY = tempAABB[1];
        largestZ = tempAABB[2];
        smallestX = tempAABB[0];
        smallestY = tempAABB[1];
        smallestZ = tempAABB[2];

        compareVertex(1, tempAABB);
        compareVertex(3, tempAABB);
        compareVertex(5, tempAABB);
        compareVertex(8, tempAABB);
        compareVertex(9, tempAABB);
        compareVertex(11, tempAABB);
        compareVertex(13, tempAABB);

        float[] newAABB = {
                smallestX, largestY, largestZ,
                smallestX, smallestY, largestZ,

                smallestX, smallestY, largestZ,
                largestX, smallestY, largestZ,

                largestX, smallestY, largestZ,
                largestX, largestY, largestZ,

                largestX, largestY, largestZ,
                smallestX, largestY, largestZ,

                smallestX, largestY, smallestZ,
                smallestX, smallestY, smallestZ,

                smallestX, smallestY, smallestZ,
                largestX, smallestY, smallestZ,

                largestX, smallestY, smallestZ,
                largestX, largestY, smallestZ,

                largestX, largestY, smallestZ,
                smallestX, largestY, smallestZ,

                smallestX, largestY, smallestZ,
                smallestX, largestY, largestZ,

                smallestX, smallestY, smallestZ,
                smallestX, smallestY, largestZ,

                largestX, largestY, smallestZ,
                largestX, largestY, largestZ,

                largestX, smallestY, smallestZ,
                largestX, smallestY, largestZ

        };
//        Log.w("smallestZ",Float.toString(smallestZ));

        // buffer for cube vertices
        ByteBuffer AABBbuffer = ByteBuffer.allocateDirect(4 * tempAABB.length);
        AABBbuffer.order(ByteOrder.nativeOrder());

        AAbuffer = AABBbuffer.asFloatBuffer();
        AAbuffer.put(tempAABB);
        AAbuffer.position(0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[4]);

        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, AAbuffer.capacity() * 4, AAbuffer);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        aBuff = buffers[4];
        AAbuffer = null;

    }

    public void compareVertex(int vertIndex, float[] tempAABB) {
//        Log.w("valx", Float.toString(tempAABB[vertIndex*3]));
//        Log.w("valy", Float.toString(tempAABB[(vertIndex*3)+1]));
//        Log.w("valz", Float.toString(tempAABB[(vertIndex*3)+2]));
        if (largestX < tempAABB[vertIndex * 3])
            largestX = tempAABB[vertIndex * 3];
        if (largestY < tempAABB[(vertIndex * 3) + 1])
            largestY = tempAABB[(vertIndex * 3) + 1];
        if (largestZ < tempAABB[(vertIndex * 3) + 2])
            largestZ = tempAABB[(vertIndex * 3) + 2];
        if (smallestX > tempAABB[vertIndex * 3])
            smallestX = tempAABB[vertIndex * 3];
        if (smallestY > tempAABB[(vertIndex * 3) + 1])
            smallestY = tempAABB[(vertIndex * 3) + 1];
        if (smallestZ > tempAABB[(vertIndex * 3) + 2])
            smallestZ = tempAABB[(vertIndex * 3) + 2];

    }

    //texture draw
    public void draw(float[] projection, float[] view, float[] model, ShaderHandles Shader, int textures) {

        //position
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positions);
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);
        GLES20.glVertexAttribPointer(Shader.positionHandle, 3, GLES20.GL_FLOAT, false, 0, 0);

        //normals
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalValues);
        GLES20.glEnableVertexAttribArray(Shader.normalHandle);
        GLES20.glVertexAttribPointer(Shader.normalHandle, 3, GLES20.GL_FLOAT, false, 0, 0);

        // Bind the texture to this unit.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures);
        GLES20.glUniform1i(Shader.mTextureUniformHandle.get(0), 0);

        //texture
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, texcords);
        GLES20.glEnableVertexAttribArray(Shader.mTextureCoordinateHandle);
        GLES20.glVertexAttribPointer(Shader.mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, 0);

        //send uniforms
        GLES20.glUniformMatrix4fv(Shader.projectionHandle, 1, false, projection, 0);
        GLES20.glUniformMatrix4fv(Shader.viewHandle, 1, false, view, 0);
        GLES20.glUniformMatrix4fv(Shader.modelHandle, 1, false, model, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length / 3);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    //texture draw
    public void draw(float[] projection, float[] view, float[] model, ShaderHandles Shader, int textures, int textures2) {

        //position
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positions);
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);
        GLES20.glVertexAttribPointer(Shader.positionHandle, 3, GLES20.GL_FLOAT, false, 0, 0);

        //normals
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalValues);
        GLES20.glEnableVertexAttribArray(Shader.normalHandle);
        GLES20.glVertexAttribPointer(Shader.normalHandle, 3, GLES20.GL_FLOAT, false, 0, 0);

        //tangent
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, tanbuffidx);
        GLES20.glEnableVertexAttribArray(Shader.tangentHandle);
        GLES20.glVertexAttribPointer(Shader.tangentHandle, 3, GLES20.GL_FLOAT, false, 0, 0);

        // Bind the texture to this unit.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures);
        GLES20.glUniform1i(Shader.mTextureUniformHandle.get(0), 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures2);
        GLES20.glUniform1i(Shader.mTextureUniformHandle.get(1), 1);

        //texture
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, texcords);
        GLES20.glEnableVertexAttribArray(Shader.mTextureCoordinateHandle);
        GLES20.glVertexAttribPointer(Shader.mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, 0);

        //send uniforms
        GLES20.glUniformMatrix4fv(Shader.projectionHandle, 1, false, projection, 0);
        GLES20.glUniformMatrix4fv(Shader.viewHandle, 1, false, view, 0);
        GLES20.glUniformMatrix4fv(Shader.modelHandle, 1, false, model, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length / 3);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void draw(float[] projection, float[] view, float[] model, ShaderHandles Shader) {

        //position
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positions);
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);
        GLES20.glVertexAttribPointer(Shader.positionHandle, 3, GLES20.GL_FLOAT, false, 0, 0);

        //normals
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalValues);
        GLES20.glEnableVertexAttribArray(Shader.normalHandle);
        GLES20.glVertexAttribPointer(Shader.normalHandle, 3, GLES20.GL_FLOAT, false, 0, 0);


        //send uniforms
        GLES20.glUniformMatrix4fv(Shader.projectionHandle, 1, false, projection, 0);
        GLES20.glUniformMatrix4fv(Shader.viewHandle, 1, false, view, 0);
        GLES20.glUniformMatrix4fv(Shader.modelHandle, 1, false, model, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length / 3);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void drawAABB(float[] projection, float[] view, float[] model, ShaderHandles Shader) {
        //position
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, aBuff);
        GLES20.glEnableVertexAttribArray(Shader.positionHandle);
        GLES20.glVertexAttribPointer(Shader.positionHandle, 4, GLES20.GL_FLOAT, false, 0, 0);

        //send uniforms
        GLES20.glUniformMatrix4fv(Shader.projectionHandle, 1, false, projection, 0);
        GLES20.glUniformMatrix4fv(Shader.viewHandle, 1, false, view, 0);
        GLES20.glUniformMatrix4fv(Shader.modelHandle, 1, false, model, 0);


        GLES20.glDrawArrays(GLES20.GL_LINES, 0, verticesAABB.length / 4);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

    }


}
