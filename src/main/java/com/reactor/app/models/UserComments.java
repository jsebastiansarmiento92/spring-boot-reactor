package com.reactor.app.models;

public class UserComments {

	private Usuario usuario;
	private Comments comments;
	
	public UserComments(Usuario usuario, Comments comments) {
		super();
		this.usuario = usuario;
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "UserComments [usuario=" + usuario + ", comments=" + comments + "]";
	}
	
}
