package org.davilj.trademe.filemerge.util;

import java.io.File;

import org.junit.Test;

public class DataFileMergerTest {

	@Test
	public void test() {
		File file = new File("/Users/daniev/development/google/trademe/FileMerge/filemerge/src/test/resources/mergeDir.merge");
		if (file.exists()) {
			if (!file.delete()) {
				throw new RuntimeException("Could not delete test result file");
			}
		}
		
		DataFileMerger dataFileMerger = new DataFileMerger();
		String[] commands = {};
		dataFileMerger.merge(new File("/Users/daniev/development/google/trademe/FileMerge/filemerge/src/test/resources/mergeDir"), commands);
	}
	
	@Test
	public void testZip() {
		File file = new File("/Users/daniev/development/google/trademe/FileMerge/filemerge/src/test/resources/mergeDir.merge.zip");
		if (file.exists()) {
			if (!file.delete()) {
				throw new RuntimeException("Could not delete test result file");
			}
		}
		
		DataFileMerger dataFileMerger = new DataFileMerger();
		String[] commands = {DataFileMerger.ZIP};
		dataFileMerger.merge(new File("/Users/daniev/development/google/trademe/FileMerge/filemerge/src/test/resources/mergeDir"), commands);
	}

}
