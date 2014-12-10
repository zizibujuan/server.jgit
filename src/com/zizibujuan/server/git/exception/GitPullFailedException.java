package com.zizibujuan.server.git.exception;

/**
 * git pull 失败
 * 
 * @author jinzw
 * @since 0.0.1
 */
public class GitPullFailedException extends RuntimeException{

	private static final long serialVersionUID = 6530698557862098928L;
	
	public GitPullFailedException(String msg){
		super(msg);
	}

	public GitPullFailedException(Throwable cause){
		super(cause);
	}

	public GitPullFailedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}