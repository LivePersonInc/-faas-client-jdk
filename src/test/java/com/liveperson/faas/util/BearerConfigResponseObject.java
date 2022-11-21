package com.liveperson.faas.util;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BearerConfigResponseObject {
	private String userId;
	private String loginName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return userId;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BearerConfigResponseObject that = (BearerConfigResponseObject) o;
		return Objects.equals(loginName, that.loginName) &&
				Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(loginName, userId);
	}

	@Override
	public String toString() {
		return "BearerConfigResponseObject{" +
				"loginName='" + loginName + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}