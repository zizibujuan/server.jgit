package com.zizibujuan.server.git.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Test;

import com.zizibujuan.server.git.GitUtils;

/**
 * git测试用例
 * 
 * @author jzw
 * @since 0.0.1
 */
public class GitUtilsTests {

	private String gitRepoPath = "c:/gitTests";
	private String gitUserName = "user";
	private String gitUserMail = "user@email.com";
	
	@After
	public void tearDown(){
		try {
			GitUtils.delete(gitRepoPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInit() throws GitAPIException, IOException, URISyntaxException, CoreException{
		assertFalse(GitUtils.isGitRepo(gitRepoPath));
		GitUtils.init(gitRepoPath, gitUserName, gitUserMail);
		assertTrue(GitUtils.isGitRepo(gitRepoPath));
	}
	
	@Test
	public void testCommit() throws GitAPIException, IOException{
		GitUtils.init(gitRepoPath, gitUserName, gitUserMail);
		assertEquals(1, GitUtils.getLogCount(gitRepoPath));
		GitUtils.commit(gitRepoPath, "/a/b", "/c.txt", "hello", "usera", "usera@email.com", "firstCommit");
		assertEquals(2, GitUtils.getLogCount(gitRepoPath));
		StringWriter writer = new StringWriter();
		InputStream in = new FileInputStream(gitRepoPath + "/a/b" + "/c.txt");
		IOUtils.copy(in, writer);
		assertEquals("hello", writer.toString());
		
		GitUtils.commit(gitRepoPath, "/a/b", "/c.txt", "hello world", "usera", "usera@email.com", "secondCommit");
		assertEquals(3, GitUtils.getLogCount(gitRepoPath));
		writer = new StringWriter();
		in = new FileInputStream(gitRepoPath + "/a/b" + "/c.txt");
		IOUtils.copy(in, writer);
		assertEquals("hello world", writer.toString());
	}
}
