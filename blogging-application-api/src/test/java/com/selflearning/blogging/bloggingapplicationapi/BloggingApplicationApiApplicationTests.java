package com.selflearning.blogging.bloggingapplicationapi;

import com.selflearning.blogging.bloggingapplicationapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BloggingApplicationApiApplicationTests
{
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads()
	{
	}

	@Test
	public void RepoTest()
	{
		String className = userRepository.getClass().getName();
		String packageName = userRepository.getClass().getPackageName();
		System.out.println("Class : " + className + "\nPackage : " + packageName);
	}

}
