package io.zenandroid.fptest.service;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

import javax.inject.Inject;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.event.ApiError;

/**
 * Created by acristescu on 14/07/2017.
 */

public class ImageProcessingService implements Target {

	@Inject
	public ImageProcessingService() {

	}

	public void applyInverFilter(final String path) {
		Picasso
				.with(Application.getInstance())
				.load(new File(path))
				.transform(new InvertTransformation())
				.into(this); // <- this should not be changed to an anonymous class due to a Picasso bug with WeakReferences
	}

	@Override
	public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
		Application.getBus().post(bitmap);
	}

	@Override
	public void onBitmapFailed(Drawable errorDrawable) {
		Application.getBus().post(new ApiError("Error loading bitmap", null, Bitmap.class));
	}

	@Override
	public void onPrepareLoad(Drawable placeHolderDrawable) {
		// do nothing
	}

	private class InvertTransformation implements Transformation {
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
	}
}
