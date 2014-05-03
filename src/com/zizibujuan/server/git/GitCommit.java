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

/**
 * git commit
 * 
 * @author jzw
 * @since 0.0.1
 */
public class GitCommit {

	/**
	 * git commit操作
	 * 
	 * @param gitRepoPath git仓库根目录
	 * @param relativePath 相对git仓库根目录的文件夹路径
	 * @param fileName 文件名
	 * @param fileContent 内容
	 * @param authorName 作者名称
	 * @param authorMail 作者邮箱
	 * @param commitMessage 提交信息
	 */
	public void execute(
			String gitRepoPath, 
			String relativePath,
			String fileName,
			String fileContent, 
			String authorName, 
			String authorMail, 
			String commitMessage){
		
		Repository repo = null;
		try {
			repo = FileRepositoryBuilder.create(new File(gitRepoPath, Constants.DOT_GIT));
			repo.resolve(Constants.HEAD);
			Git git = new Git(repo);
			File file = saveOrUpdateFile(gitRepoPath, relativePath, fileName, fileContent);
			git.add().addFilepattern(file.getPath()).call();
			git.commit().setAuthor(authorName, authorMail).setMessage(commitMessage).call();
		} catch (GitAPIException e) {
			new GitCommitFailedException(e);
		} catch (IOException e) {
			throw new GitRepoNotFoundException(gitRepoPath, e);
		} finally{
			if(repo != null){
				repo.close();
			}
		}
	}

	private File saveOrUpdateFile(String gitRepoPath, String relativePath,
			String fileName, String fileContent){
		OutputStream out = null;
		Reader reader = null;
		try{
			File folder = new File(gitRepoPath, relativePath);
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
