package io.zenandroid.fptest.login;

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
import io.zenandroid.fptest.model.Session;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.util.CredentialsManager;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by acristescu on 16/07/2017.
 */

public class LoginPresenterTest {

	@Mock
	LoginContract.View view;

	@Mock
	UsersService service;

	@Mock
	CredentialsManager credentialsManager;

	private LoginPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Injector.setComponent(
				DaggerTestingComponent.builder().
						mockUsersServiceModule(new MockUsersServiceModule(service, credentialsManager)).build());
		Application.setBus(new Bus(ThreadEnforcer.ANY));

		presenter = new LoginPresenter(view);
	}

	@Test
	public void whenNoCredentialsAreSaved_thenNoLoginIsPerformed() {
		Mockito.when(credentialsManager.getSavedEmail()).thenReturn(null);
		Mockito.when(credentialsManager.getSavedPassword()).thenReturn(null);

		presenter.start();

		verifyNoMoreInteractions(service);
		verifyNoMoreInteractions(view);
	}

	@Test
	public void whenCredentialsAreSaved_thenLoginIsPerformed() {
		Mockito.when(credentialsManager.getSavedEmail()).thenReturn("test@gmail.com");
		Mockito.when(credentialsManager.getSavedPassword()).thenReturn("secret");

		presenter.start();


		verify(view).setEmail("test@gmail.com");
		verify(view).setPassword("secret");
		verify(view).showProgressDialog();
		verify(service).login("test@gmail.com", "secret");

		verifyNoMoreInteractions(service);
		verifyNoMoreInteractions(view);
	}

	@Test
	public void whenLoginIsClicked_thenLoginIsPerformed() {
		final Session session = new Session();
		session.setUserid("234");
		session.setToken("sdfvsdhtsrt");

		Mockito.when(credentialsManager.getSavedEmail()).thenReturn(null);
		Mockito.when(credentialsManager.getSavedPassword()).thenReturn(null);

		Mockito.when(view.getEmail()).thenReturn("test@gmail.com");
		Mockito.when(view.getPassword()).thenReturn("secret");

		presenter.start();

		presenter.loginClicked();

		verify(view).showProgressDialog();
		verify(service).login("test@gmail.com", "secret");

		Application.getBus().post(session);

		verify(view).navigateToAccountPage();
	}

	@Test
	public void whenEmailIsInvalid_thenErrorIsShown() {
		Mockito.when(credentialsManager.getSavedEmail()).thenReturn(null);
		Mockito.when(credentialsManager.getSavedPassword()).thenReturn(null);

		Mockito.when(view.getEmail()).thenReturn("INVALID");
		Mockito.when(view.getPassword()).thenReturn("secret");

		presenter.start();

		presenter.loginClicked();

		verify(view).showEmailError(anyString());

		verify(service, never()).login(anyString(), anyString());
		verify(view, never()).showProgressDialog();
	}
}
