package io.zenandroid.fptest.service;

import android.graphics.Bitmap;

/**
 * Created by acristescu on 14/07/2017.
 */

public interface UsersService {

	void login(String email, String password);
	void getUserProfile(String userId);
	void sendAvatar(Bitmap avatar);
}
