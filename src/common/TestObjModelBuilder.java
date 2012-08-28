package common;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestObjModelBuilder {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String pathTestFile = "assets/models/cube.obj";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathTestFile));
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("here");
		
		
		fail("Not yet implemented");
	}

}
