package common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

import common.ObjHelper.FaceFormat;

import android.graphics.Canvas.VertexMode;
import android.os.Debug;
import android.util.Log;

public class ObjModelBuilder {
	private ArrayList<String> mV;
	private ArrayList<String> mVn;
	private ArrayList<String> mVt;
	private ArrayList<String> mF;

	private int mNoVertices;

	private int mVertexStride = 3;
	private int mNormalStride = 3;
	private int mTexCoordStride = 2;
	private int mFaceStride = 3;

	public ObjModelBuilder() {
		// pre-allocate space
	}

	public ObjModel buildObjModel() {
		Debug.startMethodTracing("buildModel");
		long start = Calendar.getInstance().getTimeInMillis();
		Log.i(ObjModelBuilder.class.toString(), "model build start");

		ObjModel model = new ObjModel(mF.size());
		// for each face, unwrap and add data.
		int noElements = 0;
		if (!mV.isEmpty())
			noElements++;
		if (!mVt.isEmpty())
			noElements++;
		if (!mVn.isEmpty())
			noElements++;

		int processingFace = 0;
		for (String line : mF) {
			if (++processingFace % 1000 == 0)
				System.out.println("processing face: " + processingFace);

			StringTokenizer tokenizer = new StringTokenizer(line, " /");
			ArrayList<String> faceData = new ArrayList<String>();
			// String type = tokenizer.nextToken();

			while (tokenizer.hasMoreElements()) {
				faceData.add(tokenizer.nextToken());
			}

			int nValuesPerFace = faceData.size() / mFaceStride;

			

			for (int i = 1; i < faceData.size(); i += nValuesPerFace) {
				int indexV = -1, indexVt = -1, indexVn = -1;
				switch (nValuesPerFace) {
				case 3:
					indexVn = Integer.parseInt(faceData.get(i + 2));
				case 2:
					if (!mVt.isEmpty()) {
						indexVt = Integer.parseInt(faceData.get(i + 1));
					} else {
						indexVn = Integer.parseInt(faceData.get(i + 1));
					}
				case 1:
					indexV = Integer.parseInt(faceData.get(i));
					break;

				default:
					break;
				}

				if (indexV != -1) {
					model.addLine(mV.get(indexV - 1));
				}
				if (indexVt != -1) {
					model.addLine(mVt.get(indexVt - 1));
				}
				if (indexVn != -1) {
					model.addLine(mVn.get(indexVn - 1));
				}
			}
		}

		model.resetPointerPositions();

		long end = Calendar.getInstance().getTimeInMillis();
		Log.i(ObjModelBuilder.class.toString(), "Model build finished: " + (end - start) + " ms");

		Debug.stopMethodTracing();

		return model;
	}

	public void linkData(ArrayList<String> v, ArrayList<String> vt, ArrayList<String> vn, ArrayList<String> f) {
		mV = v;
		mVt = vt;
		mVn = vn;
		mF = f;
	}

}
