package io.zenandroid.fptest.playlist;

import java.util.List;

import io.zenandroid.fptest.model.Song;

/**
 * Created by acristescu on 02/07/2017.
 */

public interface PlaylistContract {
	interface View extends io.zenandroid.fptest.base.View<Presenter> {
		void displaySongs(List<Song> songs);
	}

	interface Presenter extends io.zenandroid.fptest.base.Presenter {

	}
}
