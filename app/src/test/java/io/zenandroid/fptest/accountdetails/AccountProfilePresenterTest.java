package io.zenandroid.fptest.accountdetails;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.dagger.DaggerTestingComponent;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.dagger.MockUsersServiceModule;
import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.util.CredentialsManager;
import io.zenandroid.fptest.util.GravatarUtils;

import static org.mockito.Mockito.verify;

/**
 * Created by acristescu on 16/07/2017.
 */

public class AccountProfilePresenterTest {
	@Mock
	AccountProfileContract.View view;

	@Mock
	UsersService service;

	@Mock
	CredentialsManager credentialsManager;

	private AccountProfilePresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Injector.setComponent(
				DaggerTestingComponent.builder().
						mockUsersServiceModule(new MockUsersServiceModule(service, credentialsManager)).build());
		Application.setBus(new Bus(ThreadEnforcer.ANY));

		Mockito.when(credentialsManager.getSavedEmail()).thenReturn("test@gmail.com");
		Mockito.when(credentialsManager.getSavedPassword()).thenReturn("secret");
		Mockito.when(credentialsManager.getUserId()).thenReturn("123");

		presenter = new AccountProfilePresenter(view);
	}

	@Test
	public void whenPresenterIsStarted_thenViewIsPopulatedAndRequestIsFired() {

		presenter.start();

		verify(view).setEmail("test@gmail.com");
		verify(view).setPassword("secret");
		verify(view).showProgressDialog();

		verify(service).getUserProfile("123");
	}

	@Test
	public void whenResponseIsReceived_thenAvatarIsSet() {
		presenter.start();

		final AccountProfile profile = new AccountProfile();
		profile.setEmail("test@gmail.com");
		profile.setAvatarUrl("testURL");

		Application.getBus().post(profile);

		verify(view).dismissProgressDialog();
		verify(view).loadAvatar("testURL");
	}

	@Test
	public void whenNoAvatarIsSet_thenGravatarURLIsSet() {
		presenter.start();

		final AccountProfile profile = new AccountProfile();
		profile.setEmail("test@gmail.com");

		Application.getBus().post(profile);

		verify(view).dismissProgressDialog();
		verify(view).loadAvatar(GravatarUtils.getGravatarUrl("test@gmail.com"));
	}
}
