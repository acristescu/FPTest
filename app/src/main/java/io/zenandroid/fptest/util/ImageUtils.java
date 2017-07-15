package io.zenandroid.fptest.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import io.zenandroid.fptest.Application;

/**
 * Created by acristescu on 14/07/2017.
 */

public class ImageUtils {

	static Set<Target> activeTargets = new HashSet<>();

	public static void applyInverFilter(final String path) {
		final Target target = new Target() {
			@Override
			public void onBitmapLoaded(final Bitmap bmp, Picasso.LoadedFrom from) {
				Application.getBus().post(bmp);
				activeTargets.remove(this);
			}

			@Override
			public void onBitmapFailed(Drawable errorDrawable) {
				System.out.println();
				activeTargets.remove(this);
			}

			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {
				System.out.println();
			}
		};
		activeTargets.add(target);

		Picasso.with(Application.getInstance()).load(new File(path)).transform(new Transformation() {
			@Override
			public Bitmap transform(Bitmap src) {
				Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
				int A, R, G, B;

				for (int y = 0; y < src.getHeight(); y++) {
					for (int x = 0; x < src.getWidth(); x++) {
						final int pixel = src.getPixel(x, y);
						A = Color.alpha(pixel);
						R = 255 - Color.red(pixel);
						G = 255 - Color.green(pixel);
						B = 255 - Color.blue(pixel);

						dest.setPixel(x, y, Color.argb(A, R, G, B));
					}
				}

				src.recycle();
				return dest;
			}

			@Override
			public String key() {
				return "Invert";
			}
		}).into(target);
	}

}
