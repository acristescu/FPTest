package io.zenandroid.fptest.dagger;

import dagger.Binds;
import dagger.Module;
import io.zenandroid.fptest.service.BBCService;
import io.zenandroid.fptest.service.MockBBCService;

/**
 * Created by acristescu on 30/06/2017.
 */
@Module
public abstract class BBCServiceModule {

	@Binds
	abstract BBCService provideBBCService(MockBBCService bbcService);
}
