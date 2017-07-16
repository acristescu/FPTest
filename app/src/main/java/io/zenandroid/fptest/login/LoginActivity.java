package io.zenandroid.fptest.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.zenandroid.fptest.R;
import io.zenandroid.fptest.accountdetails.AccountProfileActivity;
import io.zenandroid.fptest.base.BaseActivity;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.util.Utils;

public class LoginActivity extends BaseActivity implements LoginContract.View {

	@BindView(R.id.input_email) EditText emailText;
	@BindView(R.id.input_password) EditText passwordText;
	@BindView(R.id.btn_login) Button loginButton;

	private LoginContract.Presenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Injector.get().inject(this);
		ButterKnife.bind(this);

		if(getSupportActionBar() != null) {
			getSupportActionBar().setTitle(getString(R.string.login));
		}


		Utils.setEnabled(loginButton, false);

		presenter = new LoginPresenter(this);
	}

	@OnClick(R.id.btn_login)
	void onLoginClicked() {
		presenter.loginClicked();
	}

	@Override
	protected void onStart() {
		super.onStart();
		presenter.start();
	}

	@OnTextChanged({R.id.input_email, R.id.input_password})
	public void onTextChanged() {
		Utils.setEnabled(loginButton,
				emailText.getText().length() > 0 &&
				passwordText.getText().length() > 0
		);
	}

	@Override
	public String getEmail() {
		return emailText.getText().toString();
	}

	@Override
	public String getPassword() {
		return passwordText.getText().toString();
	}

	@Override
	public void navigateToAccountPage() {
		finish();
		startActivity(new Intent(this, AccountProfileActivity.class));
	}

	@Override
	public void showEmailError(String error) {
		emailText.setError(error);
		emailText.requestFocus();
	}

	@Override
	public void setEmail(String email) {
		emailText.setText(email);
	}

	@Override
	public void setPassword(String password) {
		passwordText.setText(password);
	}

}