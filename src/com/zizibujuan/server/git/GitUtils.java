package com.zizibujuan.server.git;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FS;

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
	
	public static int getLogCount(String path) throws IOException, GitAPIException{
		Repository repo = FileRepositoryBuilder.create(new File(path, Constants.DOT_GIT));
		Git git = new Git(repo);
		LogCommand logCommand = git.log();
		logCommand.all();
		Iterable<RevCommit> commits = logCommand.call();
		Iterator<RevCommit> iterator = commits.iterator();
		
		int count = 0;
		while(iterator.hasNext()){
			count++;
			iterator.next();
		}
		repo.close();
		return count;
	}

	/**
	 * 初始化git仓库
	 * 
	 * @param gitRepoPath 文件夹路径
	 * @param gitUserName 用户名
	 * @param gitUserMail 邮箱
	 * @throws IOException 
	 * @throws GitAPIException 
	 */
	public static void init(String gitRepoPath, String gitUserName, String gitUserMail) throws GitAPIException, IOException {
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
			String commitMessage) throws IOException, GitAPIException{
		GitCommit gitCommit = new GitCommit();
		gitCommit.execute(gitRootPath, relativePath, fileName, fileContent, authorName, authorMail, commitMessage);
	}

	/**
	 * 删除git仓库
	 * 
	 * @param gitRepoPath 文件夹路径
	 * @throws IOException 
	 */
	public static void delete(String gitRepoPath) throws IOException {
		File file = new File(gitRepoPath);
		FileUtils.deleteDirectory(file);
	}
}
