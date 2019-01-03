package net.termer.rtfl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.termer.rtfl.libraries.Library;

public class JarLoader {
	public static Library loadJar(File jar) throws ZipException, IOException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
		Library res = null;
		
		String launch = null;
		if(jar.getName().toLowerCase().endsWith(".jar")) {
			ZipFile zf = new ZipFile(jar.getAbsolutePath());
			if(zf.isValidZipFile()) {
				JarFile jf = new JarFile(jar.getAbsolutePath());
				Enumeration<JarEntry> ent = jf.entries();
				while(ent.hasMoreElements()) {
					String name = ((JarEntry)ent.nextElement()).getName();
					if(name.toLowerCase().endsWith(".class")) {
						String clazz = name.replace("/", ".").replace(".class", "");
						if(clazz.endsWith("Lib")) {
							launch = clazz;
							break;
						}
					}
				}
				jf.close();
			}
			else {
				throw new ZipException("File is not a valid jarfile");
			}
		}
		if(launch != null) {
			@SuppressWarnings({ "resource", "deprecation" })
			URLClassLoader ucl = new URLClassLoader(new URL[] { jar.toURL() });
			try {
				res = (Library)ucl.loadClass(launch).newInstance();
			}
			catch (InstantiationException|IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
