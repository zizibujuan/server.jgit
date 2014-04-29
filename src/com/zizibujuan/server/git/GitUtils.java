package com.zizibujuan.server.git;

import java.io.File;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RepositoryCache;
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
	 * @param path 文件夹路径
	 * @return 如果文件夹下是git仓库，则返回<code>true</code>;否则返回<code>false</code>
	 */
	public static boolean isGitRepo(String path){
		File file = new File(path);
		if(!file.exists()){
			return false;
		}
		return RepositoryCache.FileKey.isGitRepository(new File(file, Constants.DOT_GIT), FS.DETECTED);
	}
}
