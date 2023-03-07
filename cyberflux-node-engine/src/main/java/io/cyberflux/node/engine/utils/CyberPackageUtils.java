package io.cyberflux.node.engine.utils;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public final class CyberPackageUtils {
    public static String getMainClassName() {
        StackTraceElement[] stackTraceElements = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                return stackTraceElement.getClassName();
            }
        }
        return "";
    }

    public static String getRootPackageName() {
        return CyberStringUtils.cosLastSplit(CyberPackageUtils.getMainClassName(), ".");
    }

    public static Set<String> scanClassName(String packageName, boolean isRecursion) {
        Set<String> nameSet = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(packageName.replace(".", "/"));
        if(url == null) {
            return nameSet;
        } else if(url.getProtocol().equals("file")) {
            try {
                String filePath = URLDecoder.decode(url.getPath(), "utf-8");
                nameSet = getClassNameFromDir(filePath, packageName, isRecursion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nameSet;
    }

    private static Set<String> getClassNameFromDir(String path, String packageName, boolean isRecursion) {
        Set<String> nameSet = new HashSet<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File flle : files) {
            if (flle.isDirectory()) {
                if (isRecursion) {
                    nameSet.addAll(getClassNameFromDir(flle.getPath(), packageName + "." + flle.getName(), isRecursion));
                }
            } else {
                String fileName = flle.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    nameSet.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }
        return nameSet;
    }
}
