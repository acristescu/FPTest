package io.zenandroid.fptest.model;

import java.util.Objects;

/**
 * Created by acristescu on 14/07/2017.
 */

public class Session {
	private String userid;
	private String token;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Session session = (Session) o;
		return Objects.equals(userid, session.userid) &&
				Objects.equals(token, session.token);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userid, token);
	}
}
