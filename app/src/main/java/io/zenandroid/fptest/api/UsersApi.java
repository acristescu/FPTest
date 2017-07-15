package io.zenandroid.fptest.api;

import io.zenandroid.fptest.model.AccountProfile;
import io.zenandroid.fptest.model.AvatarResponse;
import io.zenandroid.fptest.model.Session;
import io.zenandroid.fptest.model.requests.AvatarRequest;
import io.zenandroid.fptest.model.requests.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by acristescu on 14/07/2017.
 */

public interface UsersApi {

	@POST("sessions/new")
	Call<Session> login(@Body LoginRequest request);

	@GET("users/{userid}")
	Call<AccountProfile> fetchAccountProfile(@Path("userid") String userid);

	@POST("/users/{userid}/avatar")
	Call<AvatarResponse> setAvatar(@Path("userid") String userid, @Body AvatarRequest request);
}
