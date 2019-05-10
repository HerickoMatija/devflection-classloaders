package com.devflection;

import com.devflection.persistance.ClassDAO;
import com.devflection.persistance.HSQLDBConnectionManager;
import com.devflection.system.MyAppClass1;
import com.devflection.system.MyAppClass2;
import com.devflection.system.MyAppClass3;
import com.sun.java.accessibility.AccessBridge;
import com.sun.javafx.util.Logging;
import jdk.internal.dynalink.DynamicLinker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            exampleClassLoaderInfo();
        }

        Connection connection = HSQLDBConnectionManager.connect();

        ClassDAO classDAO = new ClassDAO(connection);

        classDAO.createClassTable();

        byte[] bytes = Files.readAllBytes(Paths.get("classes/ClassFromDatabase.java"));

        classDAO.insertClassToDB("ClassFromDatabase", bytes);

        byte[] classFromDatabases = classDAO.getClassFromDB("ClassFromDatabase");

        System.out.println(Arrays.equals(bytes, classFromDatabases));

        System.out.println();
    }

    private static void exampleClassLoaderInfo() {
        System.out.println(ArrayList.class.getClassLoader()); // prints null
        System.out.println(HashMap.class.getClassLoader()); // prints null
        System.out.println(HashSet.class.getClassLoader()); // prints null

        System.out.println(Boolean.class.getClassLoader()); // prints null
        System.out.println(Byte.class.getClassLoader()); // prints null
        System.out.println(Character.class.getClassLoader()); // prints null

        System.out.println(AccessBridge.class.getClassLoader()); // prints sun.misc.Launcher$ExtClassLoader@45ee12a7
        System.out.println(DynamicLinker.class.getClassLoader()); // prints sun.misc.Launcher$ExtClassLoader@45ee12a7
        System.out.println(Logging.class.getClassLoader()); // prints sun.misc.Launcher$ExtClassLoader@45ee12a7

        System.out.println(MyAppClass1.class.getClassLoader()); // prints sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(MyAppClass2.class.getClassLoader()); // prints sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(MyAppClass3.class.getClassLoader()); // prints sun.misc.Launcher$AppClassLoader@18b4aac2
    }
}
