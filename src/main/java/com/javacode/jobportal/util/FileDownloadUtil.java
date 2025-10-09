package com.javacode.jobportal.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    public Resource getFileAsResource(String downloadDir, String fileName) throws IOException {
        Path dirPath = Paths.get(downloadDir);

        if (!Files.exists(dirPath)) {
            throw new IOException("Directory not found: " + downloadDir);
        }

        Path filePath = dirPath.resolve(fileName);
        if (!Files.exists(filePath)) {
            return null; // File not found
        }

        return new UrlResource(filePath.toUri());
    }
}
