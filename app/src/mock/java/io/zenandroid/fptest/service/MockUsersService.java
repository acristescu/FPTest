package io.zenandroid.fptest.service;

import android.graphics.Bitmap;

import javax.inject.Inject;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.model.AvatarResponse;
import io.zenandroid.fptest.model.Session;

/**
 * Created by acristescu on 16/07/2017.
 */

public class MockUsersService implements UsersService {

	@Inject
	public MockUsersService() {

	}
	
	@Override
	public void login(String email, String password) {
		final Session session = new Session();
		session.setUserid("1");
		session.setToken("MockToken");
		Application.getBus().post(session);
	}

	@Override
	public void getUserProfile(String userId) {
		final AccountProfile profile = new AccountProfile();
		profile.setEmail("test@gmail.com");
		profile.setAvatarUrl("https://scontent-lhr3-1.xx.fbcdn.net/v/t31.0-8/19957016_10207417852724094_3301085894174597354_o.jpg?oh=f7a79546ff76659306f8085dc3493434&oe=59C5269D");
		Application.getBus().post(profile);
	}

	@Override
	public void sendAvatar(Bitmap avatar) {
		final AvatarResponse response = new AvatarResponse();
		response.setAvatarUrl("https://scontent-lhr3-1.xx.fbcdn.net/v/t31.0-8/19957016_10207417852724094_3301085894174597354_o.jpg?oh=f7a79546ff76659306f8085dc3493434&oe=59C5269D");
		Application.getBus().post(response);
	}
}
