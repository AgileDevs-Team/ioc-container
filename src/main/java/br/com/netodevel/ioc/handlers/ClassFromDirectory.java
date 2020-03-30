package br.com.netodevel.ioc.handlers;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassFromDirectory implements ClassLocator {

    private final Set<Class<?>> locatedClasses;

    public ClassFromDirectory() {
        this.locatedClasses = new HashSet<>();
    }

    @Override
    public Set<Class<?>> locateClass(String directory) {
        File file = new File(directory);

        if (!file.isDirectory()) {
            System.out.println("directory is invalid: " + directory);
        }

        for (File innerFile : file.listFiles()) {
            try {
                this.scanDir(innerFile, "");
            } catch (ClassNotFoundException e) {
                System.out.println("error: ".concat(e.getMessage()));
            }
        }

        return locatedClasses;
    }

    private void scanDir(File file, String packageName) throws ClassNotFoundException {
        if (file.isDirectory()) {
            packageName += file.getName().concat(".");

            for (File fileItem : file.listFiles()) {
                this.scanDir(fileItem, packageName);
            }
        } else {
            if (!file.getName().endsWith(".class")) {
                return;
            }

            final String className = packageName + file.getName().replace(".class", "");
            this.locatedClasses.add(Class.forName(className));
        }
    }

}
