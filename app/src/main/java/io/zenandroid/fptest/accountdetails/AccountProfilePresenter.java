package io.zenandroid.fptest.accountdetails;

import android.graphics.Bitmap;
import android.net.Uri;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import io.zenandroid.fptest.base.BasePresenter;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.model.AvatarResponse;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.util.CredentialsManager;
import io.zenandroid.fptest.util.ImageUtils;

/**
 * Created by acristescu on 02/07/2017.
 */

public class AccountProfilePresenter extends BasePresenter implements AccountProfileContract.Presenter {

	private AccountProfileContract.View view;

	@Inject
	UsersService service;

	@Inject
	CredentialsManager credentialsManager;

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

		service.getUserProfile(credentialsManager.getUserId());
	}

	@Subscribe
	public void onAccountProfileLoaded(AccountProfile accountProfile) {
		if(accountProfile.getAvatarUrl() != null) {
			view.loadAvatar(Uri.parse(accountProfile.getAvatarUrl()));
		}
		view.dismissProgressDialog();
	}

	@Override
	public void processImage(String path) {
		ImageUtils.applyInverFilter(path);
		view.showProgressDialog();
	}

	@Subscribe
	void imageProcessingDone(Bitmap bitmap) {
		view.loadAvatar(bitmap);
		service.sendAvatar(bitmap);
	}

	@Subscribe
	void onAvatarSet(AvatarResponse response) {
		view.dismissProgressDialog();
	}
}
