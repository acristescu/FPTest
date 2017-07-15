package io.zenandroid.fptest.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by acristescu on 02/07/2017.
 */

@Module
public class MockBBCServiceModule {

	private BBCService mockService;

	public MockBBCServiceModule(BBCService mockService) {
		this.mockService = mockService;
	}

	@Provides
	public BBCService provideBBCService() {
		return mockService;
	}
}
