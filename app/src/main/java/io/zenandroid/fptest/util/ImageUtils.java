package io.zenandroid.fptest.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by acristescu on 16/07/2017.
 */

public class ImageUtils {

	private static final double TARGET_SIZE = 1024 * 1024;
	private static final double BASE64_OVERHEAD = 1.37; // from https://en.wikipedia.org/wiki/Base64
	private static final double TARGET_SIZE_RAW = TARGET_SIZE / BASE64_OVERHEAD;
	private static final double GOOD_ENOUGH_SIZE = TARGET_SIZE_RAW * .9;
	private static final int JPEG_QUALITY = 80;

	/**
	 * Encodes an image to a base64 string enforcing a max size.
	 * Warning: this is sync operation do NOT do this on the main thread!
	 * @param src
	 * @return
	 */
	public static String base64EncodeImageWithMaxSize(Bitmap src) {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		src.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, stream);

		final int actualSize = stream.size();

		if (actualSize > TARGET_SIZE_RAW) {
			//
			// We will now binary search for a ratio that gets us within GOOD_ENOUGH_SIZE and TARGET_SIZE_RAW
			// This is a tradeoff (more time spent processing the image, better results) but IMHO
			// the user would prefer that his/her avatar looks as good as possible in exchange for
			// an extra few hundreds of millis spent divining the exact ratio. Alternatively,
			// a good one-off guesstimate would be Math.sqrt(actualSize / TARGET_SIZE_RAW) which
			// always seems to produce smaller than needed sizes.
			//
			int currentSize = actualSize;

			//
			// Possible optimisation: start at Math.sqrt(actualSize / TARGET_SIZE_RAW) instead of .5 maybe?
			//
			double tooBigRatio = 1;
			double tooSmallRatio = 0;

			while (currentSize > TARGET_SIZE_RAW || currentSize < GOOD_ENOUGH_SIZE) {

				double ratio = (tooBigRatio + tooSmallRatio) / 2;
				stream.reset();
				int newW = (int) (src.getWidth() * ratio);
				int newH = (int) (src.getHeight() * ratio);
				Bitmap bitmap = Bitmap.createScaledBitmap(src, newW, newH, false);
				bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, stream);
				currentSize = stream.size();
				if (currentSize > TARGET_SIZE_RAW) {
					tooBigRatio = ratio;
				} else {
					tooSmallRatio = ratio;
				}
			}
		}

		//
		// Note: as per the docs, closing a ByteArrayOutputStream is a NO-OP and not required
		//
		return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
	}
}
