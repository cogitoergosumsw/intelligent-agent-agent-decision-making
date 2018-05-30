package helper;

import entity.ActionUtility;

public class DuplicateArray {
	public static void duplicateArray(ActionUtility[][] src, ActionUtility[][] des) {
		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, des[i], 0, src[i].length);
		}
	}
}
