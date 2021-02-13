package de.pfannekuchen.snippets;

import java.io.Serializable;

public class Snip implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String type;
	public String title;
	public Object data;
	
	public Snip(String title, byte[] data) {
		this.type = "data";
		this.title = title;
		this.data = data;
	}
	
	public Snip(String title, String data) {
		this.type = "text";
		this.title = title;
		this.data = data;
	}
	
}
