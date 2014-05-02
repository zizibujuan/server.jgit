package com.zizibujuan.server.git.exception;

/**
 * git init操作失败
 * 
 * @author jzw
 * @since 0.0.1
 */
public class GitInitFailedException extends RuntimeException{

	private static final long serialVersionUID = 6550094866613141523L;

	public GitInitFailedException(String msg){
		super(msg);
	}

	public GitInitFailedException(Throwable cause){
		super(cause);
	}

	public GitInitFailedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
