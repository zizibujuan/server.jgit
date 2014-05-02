package com.zizibujuan.server.git.exception;

/**
 * 根据指定的路径，没有找到git仓库时报错
 * 
 * @author jzw
 * @since 0.0.1
 */
public class GitRepoNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -5026335499289061372L;
	private String path;

	public GitRepoNotFoundException(String path) {
		super("没有找到git仓库:" + path);
		this.path = path;
	}
	
	public GitRepoNotFoundException(Throwable cause){
		super(cause);
	}

	public GitRepoNotFoundException(String path, Throwable cause) {
		super("没有找到git仓库:" + path, cause);
		this.path = path;
	}
	
	public String getGitPath(){
		return path;
	}
}
