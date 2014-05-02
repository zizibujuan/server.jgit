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

import com.zizibujuan.server.git.exception.FileCreateOrUpdateFaieldException;
import com.zizibujuan.server.git.exception.GitCommitFailedException;
import com.zizibujuan.server.git.exception.GitRepoNotFoundException;

public class GitCommit {

	public void execute(
			String gitRootPath, 
			String relativePath,
			String fileName,
			String fileContent, 
			String authorName, 
			String authorMail, 
			String commitMessage){
		
		Repository repo = null;
		try {
			repo = FileRepositoryBuilder.create(new File(gitRootPath, Constants.DOT_GIT));
			repo.resolve(Constants.HEAD);
			Git git = new Git(repo);
			File file = saveOrUpdateFile(gitRootPath, relativePath, fileName, fileContent);
			git.add().addFilepattern(file.getPath()).call();
			git.commit().setAuthor(authorName, authorMail).setMessage(commitMessage).call();
		} catch (GitAPIException e) {
			new GitCommitFailedException(e);
		} catch (IOException e) {
			throw new GitRepoNotFoundException(gitRootPath, e);
		} finally{
			if(repo != null){
				repo.close();
			}
		}
	}

	private File saveOrUpdateFile(String gitRootPath, String relativePath,
			String fileName, String fileContent){
		OutputStream out = null;
		Reader reader = null;
		try{
			File folder = new File(gitRootPath, relativePath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			File file = new File(folder, fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			reader = new StringReader(fileContent);
			IOUtils.copy(reader, out);
			return file;
		}catch(IOException e){
			throw new FileCreateOrUpdateFaieldException(e);
		}finally{
			if(out != null){
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
