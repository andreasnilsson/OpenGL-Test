package common;

import java.nio.ByteBuffer;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import common.ObjHelper.ElementType;
import common.ObjHelper;

import android.graphics.Canvas.VertexMode;
import android.os.Debug;
import android.sax.StartElementListener;

import common.MathHelper;

public class ObjModel {

	private static int SIZEOF_FLOAT = 4; // in bytesM

	
	float data[] = null;
	private int faceStride = 3;

	int mNFaces;

	public FloatBuffer normals = null;
	int normalStride = 3;
	private String tmpData[] = new String[9];
	public FloatBuffer uvCoords = null;
	
	int uvStride = 2;
	
	int vertexStride = 3;

	public FloatBuffer vertices = null;

	public ObjModel(int nFaces) {
		int allocatedVerts = nFaces * vertexStride * faceStride;
		
		mNFaces = nFaces;
		ByteBuffer bb1 = ByteBuffer.allocateDirect(SIZEOF_FLOAT * allocatedVerts );
		bb1.order(ByteOrder.nativeOrder());
		vertices = bb1.asFloatBuffer();

		ByteBuffer bb2 = ByteBuffer.allocateDirect(SIZEOF_FLOAT * nFaces * normalStride * faceStride);
		bb2.order(ByteOrder.nativeOrder());
		normals = bb2.asFloatBuffer();

		ByteBuffer bb3 = ByteBuffer.allocateDirect(SIZEOF_FLOAT * nFaces * uvStride * faceStride);
		bb3.order(ByteOrder.nativeOrder());
		uvCoords = bb3.asFloatBuffer();

	}

	
	

	/**
	 * Assumes line to be a single line from an obj file
	 * 
	 * @param line
	 */
	void addLine(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line, " ");
		
		for(int i=0; tokenizer.hasMoreElements(); i++){
			tmpData[i] = tokenizer.nextToken();
		}
		
		try {
			switch (ObjHelper.getElementType(line)) {
			case VERTEX:
				addVertex(MathHelper.parseFloat(tmpData[1]), MathHelper.parseFloat(tmpData[2]), MathHelper.parseFloat(tmpData[3]));
				break;
			case NORMAL:
				addNormal(MathHelper.parseFloat(tmpData[1]), MathHelper.parseFloat(tmpData[2]), MathHelper.parseFloat(tmpData[3]));
				break;
			case TEXCOORD:
				addUV(MathHelper.parseFloat(tmpData[1]), MathHelper.parseFloat(tmpData[2]));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	void addNormal(float x, float y, float z) {
		normals.put(new float[] { x, y, z });
	}	
	
	/**
	 * @param u
	 * @param v
	 */
	void addUV(float u, float v) {
		uvCoords.put(new float[] { u, v });
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	void addVertex(float x, float y, float z) {
		vertices.put(new float[] { x, y, z });
	}

	public float[] getData() {
		return data;
	}

	public int getmNFaces() {
		return mNFaces;
	}

	public void resetPointerPositions() {
		vertices.position(0);
		uvCoords.position(0);
		normals.position(0);
	}

	public void setData(float[] data) {
		this.data = data;
	}

	public void setmNFaces(int mNFaces) {
		this.mNFaces = mNFaces;
	}
	
}
