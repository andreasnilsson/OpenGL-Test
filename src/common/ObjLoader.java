package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import common.ObjHelper.ElementType;
import common.ObjHelper.FaceFormat;

import android.graphics.Canvas.VertexMode;
import android.util.Log;

public class ObjLoader {

	ArrayList<String> v;
	ArrayList<String> vt;
	ArrayList<String> vn;
	ArrayList<String> f;

	private static final String LOG_TAG = "OBJLOADER";

	private int vertexStride = 3;
	private int normalStride = 3;
	private int uvStride = 2;
	private int faceStride = 3;

	public ObjLoader() {

	}

	FaceFormat getFaceFormat(String line) {
		String[] face_data = line.split(" ");
		String face1 = face_data[1];
		String face_arrangement[] = face1.split("/", 0);

		// len 1 >> vertex only
		// len 2 >> vertex and texture
		// len 3 >> pos 2 empy >> vertex normal
		// len 3 >> pos 2 not empty >> vertex texture normal
		switch (face_arrangement.length) {
		case 1:
			return FaceFormat.V;
		case 2:
			return FaceFormat.V_VT;
		case 3:
			if (face_arrangement[1].isEmpty()) {
				return FaceFormat.V_VN;
			} else {
				return FaceFormat.V_VT_VN;
			}
		default:
			return FaceFormat.V;
		}
	}

	private ElementType getElementType(String line) {

		if (line.startsWith("v ")) {
			return ElementType.VERTEX;
		} else if (line.startsWith("vt ")) {
			return ElementType.TEXCOORD;
		} else if (line.startsWith("vn ")) {
			return ElementType.NORMAL;
		} else if (line.startsWith("f ")) {
			return ElementType.FACE;
		} else if (line.startsWith("#")) {
			return ElementType.COMMENT;
		} else {
			String stripped_line = line.trim();

			char c = stripped_line.charAt(0);

			byte c_Val = (byte) c;

			if (stripped_line.equalsIgnoreCase("\n")) {
				System.out.println("yes");
			}

			Log.i(LOG_TAG, "Line: " + line);

			return ElementType.NOT_SUPPORTED;
		}
	}

	private int countFaces(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		int nFaces = 0;
		while (true) {
			int c = is.read();
			sb.append((char) c);
			if (c == '\n' || c == -1) {
				String line = sb.toString().trim();
				// if line is only length 1 disregard it.
				if (line.startsWith("f ")) {
					nFaces++;
				}
				sb.setLength(0);
			}
			if (c == -1) {
				break;
			}
		}

		is.reset();
		return nFaces;
	}

	private void parseObjFile(InputStream is, ObjModelBuilder builder) throws IOException {
		StringBuilder sb = new StringBuilder();

		long start = Calendar.getInstance().getTimeInMillis();
		Log.i(LOG_TAG, "Load start");

		while (true) {
			int c = is.read();
			sb.append((char) c);
			if (c == '\n' || c == -1) {
				String line = sb.toString().trim();
				// if line is only length 1 disregard it.
				if (line.length() > 1) {
					switch (getElementType(line)) {
					case VERTEX:
						v.add(line);
						break;
					case NORMAL:
						vn.add(line);
						break;
					case TEXCOORD:
						vt.add(line);
						break;
					case FACE:
						f.add(line);
						break;
					default:
						break;
					}
				}

				sb.setLength(0);
			}
			if (c == -1) {
				break;

			}
		}

		builder.linkData(v, vt, vn, f);

		long end = Calendar.getInstance().getTimeInMillis();
		Log.i(LOG_TAG, "Load finished: " + (end - start) + " ms");
	}

	public ObjModel loadObject(InputStream is) throws IOException {
		int nFaces = countFaces(is);
		preAllocateData(nFaces);
		
		ObjModelBuilder builder = new ObjModelBuilder();
		
		parseObjFile(is, builder);
		return builder.buildObjModel();
	}

	private void preAllocateData(int i) {
		v = new ArrayList<String>(i * vertexStride);
		vn = new ArrayList<String>(i * normalStride);
		vt = new ArrayList<String>(i * uvStride);
		f = new ArrayList<String>(i * faceStride);
	}

}
