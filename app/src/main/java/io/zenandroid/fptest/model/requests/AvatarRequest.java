package io.zenandroid.fptest.model.requests;

import java.util.Objects;

/**
 * Created by acristescu on 15/07/2017.
 */

public class AvatarRequest {
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AvatarRequest that = (AvatarRequest) o;
		return Objects.equals(avatar, that.avatar);
	}

	@Override
	public int hashCode() {
		return Objects.hash(avatar);
	}
}
