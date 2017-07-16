package io.zenandroid.fptest.smoke;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.zenandroid.fptest.Application;
import io.zenandroid.fptest.R;
import io.zenandroid.fptest.login.LoginActivity;
import io.zenandroid.fptest.util.EspressoIdlingResource;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by acristescu on 03/07/2017.
 */

@RunWith(AndroidJUnit4.class)
public class UISmokeTests {

	@Rule
	public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<LoginActivity>(LoginActivity.class) {
		@SuppressLint("ApplySharedPref")
		@Override
		protected void beforeActivityLaunched() {
			//
			// Note: doing this in @Before creates a race condition
			//
			Application.getInstance().getSharedPreferences("SESSION", Context.MODE_PRIVATE)
					.edit()
					.clear()
					.commit();
			super.beforeActivityLaunched();
		}
	};

	@Before
	public void registerIdlingResource() {
		Espresso.registerIdlingResources(EspressoIdlingResource.getInstance());
	}

	@After
	public void unregisterIdlingResource() {
		Espresso.unregisterIdlingResources(EspressoIdlingResource.getInstance());
	}

	@Test
	public void testLoginFormIsLoaded() {
		onView(withId(R.id.input_email)).check(matches(isDisplayed()));
		onView(withId(R.id.input_password)).check(matches(isDisplayed()));
		onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
	}

	@Test
	public void testLoginWorks() {
		onView(withId(R.id.input_email)).perform(click(), replaceText("test@gmail.com"));
		onView(withId(R.id.input_password)).perform(click(), replaceText("secret"));
		onView(withId(R.id.btn_login)).perform(click());

		onView(withId(R.id.avatar)).check(matches(isDisplayed()));
	}

	@Test
	public void testProfileIsLoaded() {
		onView(withId(R.id.input_email)).perform(click(), replaceText("test@gmail.com"));
		onView(withId(R.id.input_password)).perform(click(), replaceText("secret"));
		onView(withId(R.id.btn_login)).perform(click());

		onView(withId(R.id.avatar)).check(matches(isDisplayed()));
		onView(withId(R.id.input_email)).check(matches(withText("test@gmail.com")));
		onView(withId(R.id.input_email)).check(matches(not(isEnabled())));
		onView(withId(R.id.input_password)).check(matches(withText("secret")));
		onView(withId(R.id.input_password)).check(matches(not(isEnabled())));
	}
}
