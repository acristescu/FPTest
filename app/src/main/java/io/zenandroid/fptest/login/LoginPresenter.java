package io.zenandroid.fptest.login;

import com.squareup.otto.Subscribe;

import java.util.regex.Pattern;

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
	private static final Pattern EMAIL_ADDRESS
			= Pattern.compile(
			"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
					"\\@" +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
					"(" +
					"\\." +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
					")+"
	);

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
			view.setEmail(credentialsManager.getSavedEmail());
			view.setPassword(credentialsManager.getSavedPassword());
			view.showProgressDialog();
			service.login(credentialsManager.getSavedEmail(), credentialsManager.getSavedPassword());
		}
	}

	@Override
	public void loginClicked() {
		final String email = view.getEmail();
		final String password = view.getPassword();

		if(!EMAIL_ADDRESS.matcher(email).matches()) {
			view.showEmailError("Invalid email address");
			return;
		}

		view.showProgressDialog();

		service.login(email, password);
	}

	@Subscribe
	public void onLoginResponse(Session session) {
		Application.getBus().unregister(this);
		view.navigateToAccountPage();
	}
}
