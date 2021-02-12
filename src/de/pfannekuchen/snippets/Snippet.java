package de.pfannekuchen.snippets;

import java.io.Serializable;

public class Snippet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String type;
	public String title;
	public Object data;
	
	public Snippet(String title, byte[] data) {
		this.type = "data";
		this.title = title;
		this.data = data;
	}
	
	public Snippet(String title, String data) {
		this.type = "text";
		this.title = title;
		this.data = data;
	}
	
}
