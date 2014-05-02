package com.zizibujuan.server.git;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.zizibujuan.server.git.exception.GitLogFailedException;
import com.zizibujuan.server.git.exception.GitNoHeadException;
import com.zizibujuan.server.git.exception.GitRepoNotFoundException;

public class GitLog {
	

	public int getCount(String gitRepoPath){
		Repository repo = null;
		try{
			File gitDir = new File(gitRepoPath, Constants.DOT_GIT);
			repo = FileRepositoryBuilder.create(gitDir);
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
			return count;
		} catch (IOException e) {
			throw new GitRepoNotFoundException(gitRepoPath, e);
		} catch (NoHeadException e) {
			throw new GitNoHeadException(e);
		} catch (GitAPIException e) {
			throw new GitLogFailedException(e);
		} finally{
			if(repo != null){
				repo.close();
			}
		}
	}

}

