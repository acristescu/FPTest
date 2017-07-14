package io.zenandroid.fptest.playlist;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zenandroid.fptest.R;
import io.zenandroid.fptest.base.BaseActivity;
import io.zenandroid.fptest.dagger.Injector;
import io.zenandroid.fptest.model.Song;

public class PlaylistActivity extends BaseActivity implements PlaylistContract.View {

	@BindView(R.id.recycler) RecyclerView recycler;

	private PlaylistContract.Presenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Injector.get().inject(this);
		ButterKnife.bind(this);

		recycler.setLayoutManager(new LinearLayoutManager(this));
		recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		presenter = new PlaylistPresenter(this);
		presenter.start();
	}

	@Override
	public void displaySongs(List<Song> songs) {
		recycler.setAdapter(new SongListAdapter(songs));
	}
}