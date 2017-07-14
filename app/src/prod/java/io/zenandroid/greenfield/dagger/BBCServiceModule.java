package io.zenandroid.fptest.dagger;

import dagger.Module;
import dagger.Provides;
import io.zenandroid.fptest.BuildConfig;
import io.zenandroid.fptest.api.BBCRadioApi;
import io.zenandroid.fptest.service.BBCService;
import io.zenandroid.fptest.service.BBCServiceImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by acristescu on 30/06/2017.
 */
@Module
public class BBCServiceModule {

	@Provides
	BBCRadioApi provideBBCRadioApi() {
		return new Retrofit
				.Builder()
				.baseUrl(BuildConfig.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(BBCRadioApi.class);
	}

	@Provides
	BBCService provideBBCService(BBCServiceImpl bbcService) {
		return bbcService;
	}

}
