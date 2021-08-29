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
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public static void copyFolder(String src, String dest) throws IOException {
		// skip the first path because it is the folder itself
		List<Path> paths = Files.walk(new File(src).toPath()).skip(1).collect(Collectors.toList());
		Path sourcePath = Paths.get(src);

		for (Path path : paths) {
			copyFromResource(path.toString(), Paths.get(dest).resolve(sourcePath.relativize(path)).toString());
		}
	}
	
	public static void copyFromResource(String sourceLocation, String destinationLocation)
			throws IOException {
		System.out.printf("copy from \"%s\" to \"%s\" %n", sourceLocation, destinationLocation);
		Files.copy(new File(sourceLocation).toPath(), new File(destinationLocation).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
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
		String content = String.join("\n", Files.readAllLines(path, charset));
		content = content.replace(search, replace);
		Files.write(path, content.getBytes(charset));
	}

}