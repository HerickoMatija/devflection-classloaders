package com.devflection.classloader;

import com.devflection.persistence.ClassDAO;

public class DevflectionClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classFromDB = ClassDAO.getInstance().getClassFromDB(name);

        if (classFromDB != null && classFromDB.length > 0) {
            return defineClass(name, classFromDB, 0, classFromDB.length);
        }

        throw new ClassNotFoundException("Could not locate class with name " + name + " in the DB.");
    }
}
