package de.tubs.skeditor.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
	public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
			throws IOException {
		Files.walk(Paths.get(sourceDirectoryLocation)).forEach(source -> {
			Path destination = Paths.get(destinationDirectoryLocation,
					source.toString().substring(sourceDirectoryLocation.length()));
			try {
				Files.copy(source, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void copyFromResource(String sourceDirectoryLocation, String destinationDirectoryLocation)
			throws IOException {
		try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(sourceDirectoryLocation);
				OutputStream os = new FileOutputStream(destinationDirectoryLocation);) {
			is.transferTo(os);
		}
	}

	public static boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}

	public static void replaceInFile(String pathStr, String search, String replace) throws IOException {
		Path path = Paths.get(pathStr);
		Charset charset = StandardCharsets.UTF_8;
		String content = Files.readString(path, charset);
		content = content.replaceAll(search, replace);
		Files.write(path, content.getBytes(charset));
	}

}