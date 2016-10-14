package org.davilj.trademe.filemerge.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import com.google.common.io.Files;

/**
 * Merge all files into a single file. Each line of a file is appended with the
 * original file name (full path)
 * 
 * @author daniev
 *
 */
public class DataFileMerger {
	public static final String ZIP="-Z";

	public File merge(File dir, String[] commands) {
		if (!dir.exists() && !dir.isDirectory()) {
			throw new RuntimeException(dir + " is not a directory");
		}
		
		Set<String> commandsSet = new HashSet<>(Arrays.asList(commands));
		File mergeFile = new File(getMergeFileName(dir, commandsSet));
		List<File> files = listFile(dir);

		try (OutputStream out = buildOutputStream(mergeFile, commandsSet)) {
			for (File file : files) {
				System.out.println("merging: [" + file.getName() + "]");
				copyFileIntoStream(file, out);
			}
		} catch (IOException e) {
			throw new RuntimeException("Merge failure!!!", e);
		}
		return mergeFile;
	}
	
	private OutputStream buildOutputStream(File mergeFile, Set<String> commands) throws FileNotFoundException, IOException {
		if (commands.contains(ZIP)) {
			return new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(mergeFile)));
		} else {
			return new BufferedOutputStream(new FileOutputStream(mergeFile));
		}
	}

	private void copyFileIntoStream(File file, OutputStream out) {
		try (
			RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			for (String line : Files.readLines(file, Charset.defaultCharset())) {
				out.write(line.getBytes());
				out.write(", ".getBytes());
				out.write(file.getAbsolutePath().getBytes());
				out.write("\n".getBytes());
			}
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(file.getAbsolutePath() + " could not be merged!!");
		}
	}

	private List<File> listFile(File dir) {
		if (dir.isFile()) {
			return Arrays.asList(dir);
		} else {
			List<File> fileList = new ArrayList<>();
			for (File file : dir.listFiles()) {
				fileList.addAll(listFile(file));
			}
			return fileList;
		}
	}

	protected String getMergeFileName(File dir, Set<String> commands) {
		String name = dir.getName() + ".merge" + (commands.contains(ZIP)?".zip":"" ); 
		return dir.getParent() + File.separator + name;
	}
}
