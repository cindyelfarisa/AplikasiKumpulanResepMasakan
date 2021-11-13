package com.semangatbelajar.aplikasikumpulanresepmasakan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results{
	@SerializedName("method")
	private String method;

	@SerializedName("status")
	private String status;

	@SerializedName("results")
	private Result result;

	public String getMethod() {
		return method;
	}

	public String getStatus() {
		return status;
	}

	public Result getResult() {
		return result;
	}
}

class Result{
	@SerializedName("title")
	private String title;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("desc")
	private String desc;

	@SerializedName("ingredient")
	private List<String> bahan;

	@SerializedName("step")
	private List<String> step;

	public String getTitle() {
		return title;
	}

	public List<String> getBahan() {
		return bahan;
	}

	public List<String> getStep() {
		return step;
	}

	public String getDesc() {
		return desc;
	}

	public String getThumb() {
		return thumb;
	}
}

class Author{
	private String user;
	private String datePublished;

	public String getDatePublished() {
		return datePublished;
	}

	public String getUser() {
		return user;
	}
}