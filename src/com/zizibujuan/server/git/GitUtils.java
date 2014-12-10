package com.zizibujuan.server.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;

/**
 * git帮助类
 * 
 * @author jzw
 * @since 0.0.1
 */
public class GitUtils {

	/**
	 * 判断指定路径对应的文件夹是不是git仓库的根目录。
	 * 
	 * @param gitRepoPath 文件夹路径
	 * @return 如果文件夹下是git仓库，则返回<code>true</code>;否则返回<code>false</code>
	 */
	public static boolean isGitRepo(String gitRepoPath){
		File file = new File(gitRepoPath);
		if(!file.exists()){
			return false;
		}
		return RepositoryCache.FileKey.isGitRepository(new File(file, Constants.DOT_GIT), FS.DETECTED);
	}
	
	public static int getLogCount(String gitRepoPath){
		GitLog gitLog = new GitLog();
		return gitLog.getCount(gitRepoPath);
	}

	/**
	 * 初始化git仓库
	 * 
	 * @param gitRepoPath 文件夹路径
	 * @param gitUserName 用户名
	 * @param gitUserMail 邮箱
	 */
	public static void init(String gitRepoPath, String gitUserName, String gitUserMail){
		GitInit gitInit = new GitInit();
		gitInit.execute(gitRepoPath, gitUserName, gitUserMail);
	}
	
	public static void commit(
			String gitRootPath, 
			String relativePath,
			String fileName,
			String fileContent, 
			String authorName, 
			String authorMail, 
			String commitMessage){
		GitCommit gitCommit = new GitCommit();
		gitCommit.execute(gitRootPath, relativePath, fileName, fileContent, authorName, authorMail, commitMessage);
	}
	
	/**
	 * git pull
	 * 
	 * @param gitRepoPath 仓库的根目录
	 */
	public static void pull(String gitRepoPath){
		GitPull gitPull = new GitPull();
		gitPull.execute(gitRepoPath);
	}

	/**
	 * 删除git仓库
	 * 
	 * @param gitRepoPath 文件夹路径
	 * @throws IOException 
	 */
	public static void delete(String gitRepoPath) throws IOException {
		File file = new File(gitRepoPath);
		FileUtils.delete(file, FileUtils.RECURSIVE);
	}
}
