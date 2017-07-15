package io.zenandroid.fptest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import io.zenandroid.fptest.accountdetails.AccountProfilePresenter;
import io.zenandroid.fptest.base.BaseActivity;
import io.zenandroid.fptest.login.LoginPresenter;

/**
 * Created by acristescu on 22/06/2017.
 */

@Singleton
@Component(modules={AppModule.class, UsersServiceModule.class})
public interface AppComponent {

	void inject(BaseActivity activity);

	void inject(LoginPresenter presenter);
	void inject(AccountProfilePresenter presenter);

}
