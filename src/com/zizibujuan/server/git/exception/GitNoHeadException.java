package com.zizibujuan.server.git.exception;

/**
 * 指定的git分支不存在
 * 
 * @author jzw
 * @since 0.0.1
 */
public class GitNoHeadException extends RuntimeException{

	private static final long serialVersionUID = 6065335839523476102L;

	public GitNoHeadException(String msg){
		super(msg);
	}

	public GitNoHeadException(Throwable cause){
		super(cause);
	}

	public GitNoHeadException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
