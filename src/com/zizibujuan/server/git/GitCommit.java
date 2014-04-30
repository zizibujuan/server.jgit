package com.zizibujuan.server.git;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitCommit {

	public void execute(
			String gitRootPath, 
			String relativeFilePath,
			String fileName,
			String fileContent, 
			String authorName, 
			String authorMail, 
			String commitMessage) throws IOException, CoreException, URISyntaxException, NoFilepatternException, GitAPIException{
		
		Repository repo = FileRepositoryBuilder.create(new File(gitRootPath, Constants.DOT_GIT));
		repo.resolve(Constants.HEAD);
		Git git = new Git(repo);
		
		URI folder = new URI(gitRootPath + relativeFilePath);
		IFileStore dirStore = EFS.getLocalFileSystem().getStore(folder);
		IFileStore fileStore = dirStore.getChild(fileName);
		IOUtils.write(fileContent, fileStore.openOutputStream(EFS.NONE, null));
		
		git.add().addFilepattern(relativeFilePath + fileName).call();
		git.commit().setAuthor(authorName, authorMail).setMessage(commitMessage).call();
	}
}
