package io.zenandroid.fptest.playlist;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import io.zenandroid.fptest.base.BasePresenter;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.model.PlaylistResponse;
import io.zenandroid.fptest.service.BBCService;

/**
 * Created by acristescu on 02/07/2017.
 */

public class PlaylistPresenter extends BasePresenter implements PlaylistContract.Presenter {

	private PlaylistContract.View view;

	@Inject
	BBCService bbcService;

	public PlaylistPresenter(PlaylistContract.View view) {
		super(view);
		this.view = view;
		Injector.get().inject(this);
	}

	@Override
	public void start() {
		view.showProgressDialog();
		bbcService.fetchSongs();
	}

	@Subscribe
	public void onPlaylistReceived(PlaylistResponse response) {
		view.dismissProgressDialog();
		view.displaySongs(response.getPlaylist().getSongs());
	}

}
