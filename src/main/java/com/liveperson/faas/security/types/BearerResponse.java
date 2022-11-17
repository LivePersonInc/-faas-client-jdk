package com.liveperson.faas.security.types;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BearerResponse {
    private String bearer;
    private BearerConfigResponseObject config;
	private String csrf;
   
    public String getBearer() {
		return bearer;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}

	public String getCsrf() {
		return csrf;
	}

	public void setCsrf(String csrf) {
		this.csrf = csrf;
	}

    public BearerConfigResponseObject getConfig() {
		return config;
	}

	public void setConfig(BearerConfigResponseObject config) {
		this.config = config;
	}

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BearerResponse that = (BearerResponse) o;
		return Objects.equals(bearer, that.bearer) &&
				Objects.equals(config, that.config) &&
				Objects.equals(csrf, that.csrf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bearer, config, csrf);
	}

	@Override
	public String toString() {
		return "BearerResponse{" +
				"bearer='" + bearer + '\'' +
				", config='" + config + '\'' +
				", csrf='" + csrf + '\'' +
				'}';
	}
  

}