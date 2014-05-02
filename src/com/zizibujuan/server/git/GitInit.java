package com.zizibujuan.server.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

import com.zizibujuan.server.git.exception.GitInitFailedException;
import com.zizibujuan.server.git.exception.GitRepoNotFoundException;

public class GitInit {

	private static final String MSG_FIRST_COMMIT = "第一次提交";
	
	// 仓库地址
	/**
	 * 初始化git仓库
	 * 
	 * @param gitRepoPath 仓库路径，绝对路径
	 */
	public void execute(String gitRepoPath, String gitUserName, String gitUserMail){
		InitCommand command = new InitCommand();
		File directory = new File(gitRepoPath);
		command.setDirectory(directory);
		
		Repository repository = null;
		try {
			repository = command.call().getRepository();
			Git git = new Git(repository);
			// 配置仓库
			config(git, gitUserName, gitUserMail);
			git.commit().setMessage(MSG_FIRST_COMMIT).call();
		} catch (GitAPIException e) {
			throw new GitInitFailedException(e);
		} catch (IOException e) {
			throw new GitRepoNotFoundException(gitRepoPath);
		}finally{
			if(repository != null){
				repository.close();
			}
		}
	}
	
	private void config(Git git, String gitUserName, String gitUserMail) throws IOException{
		StoredConfig config = git.getRepository().getConfig();
		if(gitUserName != null && !gitUserName.isEmpty()){
			config.setString(ConfigConstants.CONFIG_USER_SECTION, null, ConfigConstants.CONFIG_KEY_NAME, gitUserName);
		}
		if(gitUserMail != null && !gitUserMail.isEmpty()){
			config.setString(ConfigConstants.CONFIG_USER_SECTION, null, ConfigConstants.CONFIG_KEY_EMAIL, gitUserMail);
		}
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_FILEMODE, false);
		config.save();
	}
}
