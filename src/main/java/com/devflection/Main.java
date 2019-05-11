package com.devflection;

import com.devflection.classloader.DevflectionClassLoader;
import com.devflection.persistance.ClassDAO;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static final String FULL_CLASS_NAME = "com.devflection.example.db.ClassFromDatabase";

    public static void main(String[] args) throws IOException {
        ClassDAO.getInstance().setup();
        boolean result = loadWithClassLoader(ClassLoader.getSystemClassLoader());
        System.out.println("Result of loading with the system class loader is " + result);

        result = loadWithClassLoader(new DevflectionClassLoader());
        System.out.println("Result of loading with our class loader is " + result);
    }

    private static boolean loadWithClassLoader(ClassLoader classLoader) {
        try {
            // we try to load the example class using the passed in loader
            Class<?> classFromDatabase = Class.forName(FULL_CLASS_NAME, true, classLoader);

            // we create a new instance of the class
            Object instance = classFromDatabase.newInstance();

            // we retrieve the first method, which is the #getMessage method and invoke it
            Method[] declaredMethods = classFromDatabase.getDeclaredMethods();
            Object result = declaredMethods[0].invoke(instance);

            // we print out the result of the method invocation
            System.out.println("-----------------------");
            System.out.println("Printing result of method invocation:");
            System.out.println(result);
            System.out.println("-----------------------");

            return true;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
            System.out.println("Could not load class " + FULL_CLASS_NAME + " with classloader " + classLoader.toString());
            return false;
        }
    }
}
