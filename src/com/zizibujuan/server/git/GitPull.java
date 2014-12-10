package com.zizibujuan.server.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.zizibujuan.server.git.exception.GitPullFailedException;
import com.zizibujuan.server.git.exception.GitRepoNotFoundException;

public class GitPull {

	public void execute(String gitRepoPath){
		File directory = new File(gitRepoPath, Constants.DOT_GIT);
		Repository repository = null;
		try {
			repository = FileRepositoryBuilder.create(directory);
			Git git = new Git(repository);
			PullCommand pc = git.pull();
			PullResult pullResult = pc.call();
			if(pullResult.isSuccessful()){
				System.out.println("git pull 成功");
			}else{
				System.out.println("git pull 失败");
			}
			
		} catch (GitAPIException e) {
			throw new GitPullFailedException(e);
		} catch (IOException e) {
			throw new GitRepoNotFoundException(gitRepoPath);
		}finally{
			if(repository != null){
				repository.close();
			}
		}
	}
	
}
