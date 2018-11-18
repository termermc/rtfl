package net.termer.rtfl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.termer.rtfl.exceptions.RtflException;

/**
 * Rtfl Programming Language
 * @author termer
 */

public class Rtfl {
	public static void main(String[] args) throws RtflException {
		if(args.length>0) {
			RtflInterpreter interp = new RtflInterpreter();
			try {
				System.out.print(interp.execute(readFile(args[0])));
			} catch (IOException e) {
				e.printStackTrace();
			} catch(RtflException e) {
				System.out.println("Error on line "+e.getLine()+": "+e.getMessage());
			}
		} else {
			System.out.println("Please enter the path to an Rtfl file");
		}
	}
	
	public static String readFile(String path) throws IOException {
		StringBuilder sb = new StringBuilder();
		FileInputStream fin = new FileInputStream(new File(path));
		while(fin.available()>0) {
			sb.append((char)fin.read());
		}
		fin.close();
		return sb.toString();
	}
	public static void writeFile(String path, String str) throws IOException {
		FileOutputStream fout = new FileOutputStream(path);
		for(int i = 0; i < str.length(); i++) {
			fout.write((int)str.charAt(i));
		}
		fout.close();
	}
}