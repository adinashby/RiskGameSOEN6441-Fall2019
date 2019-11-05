package test;

import org.junit.Test;
import org.junit.Assert;
import model.MapBuilder;

/**
 * This class contain test cases to test map files
 * 
 * @author s_shehna
 * @author f_yazdan

 *
 */
public class TestMapFiles {
	/**
	 * Object of the MapBuilder
	 */
	MapBuilder mapBuild = new MapBuilder();
	/**
	 * String valid_file
	 */
	String VALID_FILE = "test";
	/**
	 * String inValid_file
	 */
	String INVALID_FILE = "test2";

	/**
	 * This is the test method for checking whether the map file is valid
	 * 
	 * @throws Exception
	 */
	@Test
	public void testValidMapFiles() throws Exception {
		Assert.assertEquals(true, mb.loadMap(valid_file));
	}

	/**
	 * This is the test method for checking whether the map file is inValid
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInvalidMapFiles() throws Exception {
		Assert.assertEquals(false, mb.loadMap(invalid_file));
	}

}
