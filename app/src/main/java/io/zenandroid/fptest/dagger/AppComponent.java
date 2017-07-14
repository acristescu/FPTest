package io.zenandroid.fptest.dagger;

import dagger.Component;
import io.zenandroid.fptest.base.BaseActivity;
import io.zenandroid.fptest.playlist.PlaylistPresenter;

/**
 * Created by acristescu on 22/06/2017.
 */

@Component(modules={AppModule.class, BBCServiceModule.class})
public interface AppComponent {

	void inject(BaseActivity activity);

	void inject(PlaylistPresenter presenter);

}
