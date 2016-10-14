package org.davilj.trademe.filemerge;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.davilj.trademe.filemerge.util.DataFileMerger;

/**
 * Specify the dir to start
 *
 */
public class App {
    public static void main( String[] args ){
        Set<String> argsSet = new HashSet<>(Arrays.asList(args));
        argsSet.remove(DataFileMerger.ZIP);
        for (String arg : argsSet) {
	        File dir = new File(arg);
	        if (dir.exists() && dir.isDirectory()) {
	        	DataFileMerger dataFileMerger = new DataFileMerger();
	        	File file = dataFileMerger.merge(dir, args);
	        } else {
	        	System.err.println(args[0] + " should be a directory and exists");
	        }
        }
        
    }
}
