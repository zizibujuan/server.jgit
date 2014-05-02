package com.zizibujuan.server.git.exception;

/**
 * 创建文件或更新文件操作失败
 * 
 * @author jzw
 * @since 0.0.1
 */
public class FileCreateOrUpdateFaieldException extends RuntimeException{

	private static final long serialVersionUID = 1880245484732599166L;
	
	public FileCreateOrUpdateFaieldException(String msg){
		super(msg);
	}

	public FileCreateOrUpdateFaieldException(Throwable cause){
		super(cause);
	}

	public FileCreateOrUpdateFaieldException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
