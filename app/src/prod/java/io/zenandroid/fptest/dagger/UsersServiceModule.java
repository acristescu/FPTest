package io.zenandroid.fptest.dagger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.zenandroid.fptest.BuildConfig;
import io.zenandroid.fptest.api.UsersApi;
import io.zenandroid.fptest.service.UsersService;
import io.zenandroid.fptest.service.UsersServiceImpl;
import io.zenandroid.fptest.util.CredentialsManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by acristescu on 30/06/2017.
 */
@Module
public class UsersServiceModule {

	private static final int TIMEOUT = 15;

	@Provides
	UsersApi provideUsersApi(final CredentialsManager credentialsManager) {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder()
				.addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						Request request = chain.request();
						if(credentialsManager.getToken() != null) {
							request = request
									.newBuilder()
									.addHeader("Authorization", "Bearer " + credentialsManager.getToken())
									.build();
						}
						return chain.proceed(request);
					}
				})
				.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(TIMEOUT, TimeUnit.SECONDS)
				.addInterceptor(logging);

		return new Retrofit
				.Builder()
				.baseUrl(BuildConfig.BASE_URL)
				.client(okClientBuilder.build())
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(UsersApi.class);
	}

	@Provides
	UsersService provideUsersService(UsersServiceImpl service) {
		return service;
	}

}
