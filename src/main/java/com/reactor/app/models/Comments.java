package com.reactor.app.models;

import java.util.ArrayList;
import java.util.List;

public class Comments {
	
	private List<String> comments;
	
	public Comments() {
		// TODO Auto-generated constructor stub
		this.comments= new ArrayList<>();
	}

	

	public void addComments(String comments) {
		this.comments.add(comments);
	}



	@Override
	public String toString() {
		return "Comments [comments=" + comments + "]";
	}
	
	

}
