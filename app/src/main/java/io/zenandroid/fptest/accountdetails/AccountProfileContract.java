package io.zenandroid.fptest.accountdetails;

import android.graphics.Bitmap;

/**
 * Created by acristescu on 02/07/2017.
 */

public interface AccountProfileContract {
	interface View extends io.zenandroid.fptest.base.View<Presenter> {
		void setEmail(String savedEmail);

		void setPassword(String savedPassword);

		void loadAvatar(String url);

		void loadAvatar(Bitmap bitmap);
	}

	interface Presenter extends io.zenandroid.fptest.base.Presenter {

		void processImage(String path);
	}
}
