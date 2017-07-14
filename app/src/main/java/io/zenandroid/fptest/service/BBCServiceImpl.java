package io.zenandroid.fptest.service;

import javax.inject.Inject;

import io.zenandroid.fptest.api.ApiCallback;
import io.zenandroid.fptest.api.BBCRadioApi;
import io.zenandroid.fptest.model.PlaylistResponse;

public class BBCServiceImpl implements BBCService{

	private final static String TAG = BBCServiceImpl.class.getSimpleName();

	private final BBCRadioApi bbcRadioApi;

	@Inject
	public BBCServiceImpl(BBCRadioApi api) {
		bbcRadioApi = api;
	}

	@Override
	public void fetchSongs() {
		bbcRadioApi.getPlaylistResponse().enqueue(new ApiCallback<>(PlaylistResponse.class));
	}
}
