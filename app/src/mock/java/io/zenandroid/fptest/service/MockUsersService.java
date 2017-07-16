package io.zenandroid.fptest.service;

import android.graphics.Bitmap;

import javax.inject.Inject;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.model.AvatarResponse;
import io.zenandroid.fptest.model.Session;
import io.zenandroid.fptest.model.requests.LoginRequest;
import io.zenandroid.fptest.util.CredentialsManager;

/**
 * Created by acristescu on 16/07/2017.
 */

public class MockUsersService implements UsersService {

	@Inject
	CredentialsManager credentialsManager;

	@Inject
	public MockUsersService() {

	}
	
	@Override
	public void login(String email, String password) {
		final Session session = new Session();
		session.setUserid("1");
		session.setToken("MockToken");

		final LoginRequest request = new LoginRequest(email, password);
		credentialsManager.onSuccessfulLogin(request, session);

		Application.getBus().post(session);
	}

	@Override
	public void getUserProfile(String userId) {
		final AccountProfile profile = new AccountProfile();
		profile.setEmail("test@gmail.com");
		profile.setAvatarUrl("http://zenandroid.io/assets/img/icons/xandroid-zen1.png.pagespeed.ic.A5v2KenAhE.png");
		Application.getBus().post(profile);
	}

	@Override
	public void sendAvatar(Bitmap avatar) {
		final AvatarResponse response = new AvatarResponse();
		response.setAvatarUrl("http://zenandroid.io/assets/img/icons/xandroid-zen1.png.pagespeed.ic.A5v2KenAhE.png");
		Application.getBus().post(response);
	}
}
