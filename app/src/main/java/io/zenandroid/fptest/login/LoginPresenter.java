package io.zenandroid.fptest.login;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.base.BasePresenter;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.model.Session;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.util.CredentialsManager;

/**
 * Created by acristescu on 02/07/2017.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

	private LoginContract.View view;

	@Inject
	UsersService service;

	@Inject
	CredentialsManager credentialsManager;

	public LoginPresenter(LoginContract.View view) {
		super(view);
		this.view = view;
		Injector.get().inject(this);
	}

	@Override
	public void start() {
		if(credentialsManager.getSavedEmail() != null && credentialsManager.getSavedPassword() != null) {
			view.showProgressDialog();
			service.login(credentialsManager.getSavedEmail(), credentialsManager.getSavedPassword());
		}
	}

	@Override
	public void loginClicked() {
		final String email = view.getEmail();
		final String password = view.getPassword();

		view.showProgressDialog();

		service.login(email, password);
	}

	@Subscribe
	public void onLoginResponse(Session session) {
		Application.getBus().unregister(this);
		view.navigateToAccountPage();
	}
}
