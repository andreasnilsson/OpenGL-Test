package common;

public class ObjHelper {
	enum FaceFormat {
		V, V_VT, V_VN, V_VT_VN
	};

	enum ElementType {
		VERTEX, NORMAL, TEXCOORD, NOT_SUPPORTED, FACE, COMMENT
	};
	
	
	
	static ElementType getElementType(String objLine){
		if(objLine.startsWith("v ")) return ElementType.VERTEX;
		else if(objLine.startsWith("vn ")) return ElementType.NORMAL;
		else if(objLine.startsWith("vt ")) return ElementType.TEXCOORD;
		else if(objLine.startsWith("f ")) return ElementType.FACE;
		else if(objLine.startsWith("# ")) return ElementType.COMMENT;
		else return ElementType.NOT_SUPPORTED;
	}
	
	
	static FaceFormat getFaceFormat(String objLine) {
		//TODO change so that it will split for several spaces as well.
		String[] face_data = objLine.split(" ");
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
}
