package io.zenandroid.fptest.login;

/**
 * Created by acristescu on 02/07/2017.
 */

public interface LoginContract {
	interface View extends io.zenandroid.fptest.base.View<Presenter> {
		String getEmail();

		String getPassword();

		void navigateToAccountPage();

		void showEmailError(String error);

		void setEmail(String email);

		void setPassword(String password);
	}

	interface Presenter extends io.zenandroid.fptest.base.Presenter {

		void loginClicked();
	}
}
