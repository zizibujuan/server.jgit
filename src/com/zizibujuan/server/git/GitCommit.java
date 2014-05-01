package com.zizibujuan.server.git;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitCommit {

	public void execute(
			String gitRootPath, 
			String relativePath,
			String fileName,
			String fileContent, 
			String authorName, 
			String authorMail, 
			String commitMessage) throws IOException,  GitAPIException{
		
		Repository repo = FileRepositoryBuilder.create(new File(gitRootPath, Constants.DOT_GIT));
		repo.resolve(Constants.HEAD);
		Git git = new Git(repo);
		
		File folder = new File(gitRootPath, relativePath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		File file = new File(folder, fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStream out = new FileOutputStream(file);
		Reader reader = new StringReader(fileContent);
		IOUtils.copy(reader, out);
		
		git.add().addFilepattern(file.getPath()).call();
		git.commit().setAuthor(authorName, authorMail).setMessage(commitMessage).call();
		repo.close();
	}
}
