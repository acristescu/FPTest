package io.zenandroid.fptest.dagger;

import dagger.Module;
import dagger.Provides;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.util.CredentialsManager;

/**
 * Created by acristescu on 02/07/2017.
 */

@Module
public class MockUsersServiceModule {

	private UsersService mockService;
	private CredentialsManager mockCredentialsManager;

	public MockUsersServiceModule(UsersService mockService, CredentialsManager mockCredentialsManager) {
		this.mockService = mockService;
		this.mockCredentialsManager = mockCredentialsManager;
	}

	@Provides
	public UsersService provideMockUsersService() {
		return mockService;
	}

	@Provides
	public CredentialsManager provideMockCredentialsManager() {
		return mockCredentialsManager;
	}
}
