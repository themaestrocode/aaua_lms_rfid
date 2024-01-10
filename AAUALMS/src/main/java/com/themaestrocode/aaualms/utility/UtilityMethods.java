package com.themaestrocode.aaualms.utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilityMethods {

    public File normalizeFile(String filePath) {
        if(filePath == null) {
            return null;
        }
        // Normalize the path using Paths.get()
        Path normalizedPath = Paths.get(filePath);

        return normalizedPath.toFile();
    }


}
