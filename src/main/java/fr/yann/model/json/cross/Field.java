package fr.yann.model.json.cross;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field {

	@SerializedName("info")
	@Expose
	private String	info;
	@SerializedName("name")
	@Expose
	private String	name;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}