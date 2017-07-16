package io.zenandroid.fptest.service;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.zenandroid.fptest.api.ApiCallback;
import io.zenandroid.fptest.api.UsersApi;
import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.model.AvatarResponse;
import io.zenandroid.fptest.model.Session;
import io.zenandroid.fptest.model.requests.AvatarRequest;
import io.zenandroid.fptest.model.requests.LoginRequest;
import io.zenandroid.fptest.util.CredentialsManager;
import io.zenandroid.fptest.util.ImageUtils;

/**
 * Created by acristescu on 14/07/2017.
 */

public class UsersServiceImpl implements UsersService {

	private UsersApi api;
	private CredentialsManager credentialsManager;


	@Inject
	public UsersServiceImpl(UsersApi api, CredentialsManager credentialsManager) {
		this.api = api;
		this.credentialsManager = credentialsManager;
	}

	@Override
	public void login(@NonNull String email, @NonNull String password) {
		final LoginRequest request = new LoginRequest(email, password);
		api.login(request).enqueue(new ApiCallback<Session>(Session.class) {
			@Override
			public Session processResult(Session session) {
				credentialsManager.onSuccessfulLogin(request, session);
				return session;
			}
		});
	}

	@Override
	public void getUserProfile(@NonNull String userId) {
		api.fetchAccountProfile(userId).enqueue(new ApiCallback<>(AccountProfile.class));
	}

	@Override
	public void sendAvatar(final Bitmap avatar) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final AvatarRequest request = new AvatarRequest();
				request.setAvatar(ImageUtils.base64EncodeImageWithMaxSize(avatar));
				api.setAvatar(credentialsManager.getUserId(), request).enqueue(new ApiCallback<>(AvatarResponse.class));
			}
		}).start();
	}
}
