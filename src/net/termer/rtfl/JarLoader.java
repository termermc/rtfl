package net.termer.rtfl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.termer.rtfl.libraries.Library;

/**
 * Utility class for loading classes from a jarfile
 * @author termer
 * @since 0.1
 */
public class JarLoader {
	/**
	 * Returns classes from a jarfile
	 * @param jar the File containing the jarfile
	 * @return the classes contained in the jarfile
	 * @throws ZipException if reading the jarfile fails
	 * @throws IOException if loading the jarfile fails
	 * @throws ClassNotFoundException if loading a class from the jarfile fails
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @since 0.1
	 */
	@SuppressWarnings("deprecation")
	public static Library loadJar(File jar) throws ZipException, IOException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
		Library res = null;
		
		ArrayList<URL> urls = new ArrayList<URL>();
		ArrayList<String> classes = new ArrayList<String>();
		ArrayList<String> launchClasses = new ArrayList<String>();
		
		if(jar.getName().toLowerCase().endsWith(".jar")) {
			ZipFile zf = new ZipFile(jar.getAbsolutePath());
			if(zf.isValidZipFile()) {
				urls.add(new URL("file:"+jar.getAbsolutePath()));
				JarFile jf = new JarFile(jar.getAbsolutePath());
				Enumeration<JarEntry> ent = jf.entries();
				while(ent.hasMoreElements()) {
					String name = ent.nextElement().getName();
					if(name.toLowerCase().endsWith(".class")) {
						String clazz = name.replace("/", ".").replace(".class", "");
						if(clazz.endsWith("Lib")) {
							launchClasses.add(clazz);
						}
						if(!clazz.endsWith("Library")) {
							classes.add(clazz);
						}
					}
				}
				jf.close();
			} else {
				throw new ZipException("File is not a valid jarfile");
			}
		}
		
		URLClassLoader ucl = new URLClassLoader(urls.toArray(new URL[0]));
		for(String clazz : classes) {
			try {
				Class<?> cls = ucl.loadClass(clazz);
				
				for(String launchClass : launchClasses) {
					if(launchClass.equals(clazz)) {
						for(Class<?> inter : cls.getInterfaces()) {
							if(inter.getTypeName().equalsIgnoreCase("net.termer.rtfl.libraries.Library")) {
								res = (Library)cls.newInstance();
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ucl.close();
		
		return res;
	}
}
