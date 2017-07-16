package io.zenandroid.fptest.util;

import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by acristescu on 16/07/2017.
 */

public class GravatarUtils {

	private static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i]
					& 0xFF) | 0x100).substring(1,3));
		}
		return sb.toString();
	}

	private static String md5Hex (String message) {
		try {
			MessageDigest md =
					MessageDigest.getInstance("MD5");
			return hex (md.digest(message.getBytes("CP1252")));
		} catch (Exception e) {
			Log.e("GravatarUtils", e.getMessage(), e);
		}
		return null;
	}

	public static String getGravatarUrl(String email) {
		//
		// Note: a future improvement would be to set the size parameter based on actual view size
		//
		return String.format("http://www.gravatar.com/avatar/%s?d=404&s=400", md5Hex(email.toLowerCase()));
	}
}
