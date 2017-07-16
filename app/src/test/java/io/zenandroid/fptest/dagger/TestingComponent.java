package io.zenandroid.fptest.dagger;

import dagger.Component;

/**
 * Created by acristescu on 02/07/2017.
 */
@Component(modules={AppModule.class, MockUsersServiceModule.class})
public interface TestingComponent extends AppComponent {
}
