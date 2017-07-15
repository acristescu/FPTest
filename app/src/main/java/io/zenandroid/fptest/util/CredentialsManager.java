package io.zenandroid.fptest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.model.Session;
import io.zenandroid.fptest.model.requests.LoginRequest;

/**
 * Created by acristescu on 14/07/2017.
 */

@Singleton
public class CredentialsManager {

	private static final String PREF_NAME = "SESSION";
	private static final String EMAIL_KEY = "EMAIL";
	private static final String PASSWORD_KEY = "PASSWORD";

	private String userId;
	private String token;

	private SharedPreferences preferences = Application.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

	@Inject
	public CredentialsManager() {

	}

	public void onSuccessfulLogin(@NonNull LoginRequest request, @NonNull Session session) {
		preferences
				.edit()
				.putString(EMAIL_KEY, request.getEmail())
				.putString(PASSWORD_KEY, request.getPassword())
				.apply();
		userId = session.getUserid();
		token = session.getToken();
	}

	public String getSavedEmail() {
		return preferences.getString(EMAIL_KEY, null);
	}

	public String getSavedPassword() {
		return preferences.getString(PASSWORD_KEY, null);
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}
}
