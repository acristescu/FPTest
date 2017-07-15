package io.zenandroid.fptest.model;

import java.util.Objects;

/**
 * Created by acristescu on 14/07/2017.
 */

public class AccountProfile {
	private String email;
	private String avatar_url;

	public String getAvatarUrl() {
		return avatar_url;
	}

	public void setAvatarUrl(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AccountProfile that = (AccountProfile) o;
		return Objects.equals(email, that.email) &&
				Objects.equals(avatar_url, that.avatar_url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, avatar_url);
	}
}
