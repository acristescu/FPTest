package io.zenandroid.fptest.accountdetails;

import android.graphics.Bitmap;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import io.zenandroid.fptest.base.BasePresenter;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.model.AvatarResponse;
import io.zenandroid.fptest.service.ImageProcessingService;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.util.CredentialsManager;
import io.zenandroid.fptest.util.GravatarUtils;

/**
 * Created by acristescu on 02/07/2017.
 */

public class AccountProfilePresenter extends BasePresenter implements AccountProfileContract.Presenter {

	private AccountProfileContract.View view;

	@Inject
	UsersService usersService;

	@Inject
	CredentialsManager credentialsManager;

	@Inject
	ImageProcessingService imageProcessingService;

	public AccountProfilePresenter(AccountProfileContract.View view) {
		super(view);
		this.view = view;

		Injector.get().inject(this);
	}

	@Override
	public void start() {
		view.setEmail(credentialsManager.getSavedEmail());
		view.setPassword(credentialsManager.getSavedPassword());
		view.showProgressDialog();

		usersService.getUserProfile(credentialsManager.getUserId());
	}

	@Subscribe
	public void onAccountProfileLoaded(AccountProfile accountProfile) {
		if(accountProfile.getAvatarUrl() != null) {
			view.loadAvatar(accountProfile.getAvatarUrl());
		} else {
			view.loadAvatar(GravatarUtils.getGravatarUrl(accountProfile.getEmail()));
		}
		view.dismissProgressDialog();
	}

	@Override
	public void processImage(String path) {
		imageProcessingService.applyInverFilter(path);
		view.showProgressDialog();
	}

	@Subscribe
	public void imageProcessingDone(Bitmap bitmap) {
		view.loadAvatar(bitmap);
		usersService.sendAvatar(bitmap);
	}

	@Subscribe
	public void onAvatarSet(AvatarResponse response) {
		view.dismissProgressDialog();
	}
}
