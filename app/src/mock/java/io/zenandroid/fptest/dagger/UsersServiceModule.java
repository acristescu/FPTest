package io.zenandroid.fptest.dagger;

import dagger.Binds;
import dagger.Module;
import io.zenandroid.fptest.service.MockUsersService;
import io.zenandroid.fptest.service.UsersService;

/**
 * Created by acristescu on 30/06/2017.
 */
@Module
public abstract class UsersServiceModule {

	@Binds
	abstract UsersService provideMockUsersService(MockUsersService mockUsersService);
}
