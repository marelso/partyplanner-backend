package com.marelso.partyplanner.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.lang3.StringUtils.stripAccents;

public class FileUtil {
    public static InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException exception) {
            throw new RuntimeException("Error while trying to get file input stream");
        }
    }

    public static String normalizeFileName(String target) {
        return stripAccents(target).replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
}
