package io.zenandroid.fptest.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.zenandroid.fptest.R;
import io.zenandroid.fptest.accountdetails.AccountProfileActivity;
import io.zenandroid.fptest.base.BaseActivity;
import io.zenandroid.fptest.dagger.Injector;

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

}
