package io.cyberflux.utils.jar;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.utils.StringUtils;


public final class PackageUtils {

    private final static Logger log = LoggerFactory.getLogger(PackageUtils.class);

    public static String getStartupClassName() {
        StackTraceElement[] stackTraceElements = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                return stackTraceElement.getClassName();
            }
        }
        return "";
    }

    public static String getStartupClassSimpleName() {
        return  StringUtils.lastSplit(PackageUtils.getStartupClassName(), ".");
    }

    public static String getStartupClassPackageName() {
        return StringUtils.cosLastSplit(PackageUtils.getStartupClassName(), ".");
    }

    public static Set<String> scanClassName(Class<?> clasz, boolean isRecursion) {
        return PackageUtils.scanClassName(clasz.getPackageName(), isRecursion);
    }

    public static Set<String> scanClassName(String packageName, boolean isRecursion) {
        Set<String> nameSet = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls;
        try{
            urls = loader.getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url.getProtocol().equals("file")) {
                    try {
                        String filePath = URLDecoder.decode(url.getPath(), "utf-8");
                        nameSet.addAll(scanClassNameFromDir(filePath, packageName, isRecursion));
                    } catch (Exception e) {
                        log.error("{}", e);
                    }
                } else if (url.getProtocol().equals("jar")) {
                    nameSet = scanClassNameFromJar(url);
                }
            }
        } catch (Exception e) {
            log.error("{}", e);
        }

        return nameSet;
    }

    public static Set<String> scanClassNameFromDir(String path, String packageName, boolean isRecursion) {
        Set<String> nameSet = new HashSet<>();
        for (File file :  new File(path).listFiles()) {
            if (file.isDirectory() && isRecursion) {
                nameSet.addAll(scanClassNameFromDir(file.getPath(), packageName + "." + file.getName(), isRecursion));
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    nameSet.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }
        return nameSet;
    }

    public static Set<String> scanClassNameFromJar(URL url) {
        Set<String> nameSet = new HashSet<>();
        try {
            JarURLConnection connection = (JarURLConnection) url.openConnection();
            Enumeration<JarEntry> jarEntrys = connection.getJarFile().entries();
            while (jarEntrys.hasMoreElements()) {
                JarEntry jarEntry = jarEntrys.nextElement();
                if(!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace(".class", "").replace("/", ".");
                    nameSet.add(className);
                }
            }
        } catch (IOException e) {
            log.error("{}", e);
        }
        return nameSet;
    }
}
