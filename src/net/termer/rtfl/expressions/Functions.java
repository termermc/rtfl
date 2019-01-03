package net.termer.rtfl.expressions;

import java.util.HashMap;
import java.util.Random;

import net.termer.rtfl.Function;

public class Functions {
private HashMap<String,Function> FUNC = new HashMap<String,Function>();
	
	/**
	 * Copies a HashMap of local variable names
	 * @param localVars the variable names HashMap to copy
	 * @return a copy of the list of variables
	 * @since 1.2
	 */
	public static HashMap<String, String> copyLocalVarList(HashMap<String, String> localVars) {
		HashMap<String, String> tmp = new HashMap<String, String>();
		tmp.putAll(localVars);
		return tmp;
	}
	
	/**
	 * Generates an internal name for a local variable
	 * @return a random internal name for a local variable
	 * @since 1.2
	 */
	public static String generateLocalVarName() {
		char[] acceptableChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','3','4','5','6','7','8','9','_'};
		String str = "";
		for(int i = 0; i <= 10; i++) {
			Random rand = new Random();
			str+=acceptableChars[rand.nextInt(acceptableChars.length-1)];
		}
		return str;
	}
	
	/**
	 * Registers a function
	 * @param name the name of the function
	 * @param value the function
	 * @since 1.0
	 */
	public void register(String name, Function value) {
		FUNC.put(name, value);
	}
	
	/**
	 * Clears the specified function
	 * @param name the name of function
	 * @since 1.0
	 */
	public void clear(String name) {
		FUNC.remove(name);
	}
	
	/**
	 * Sets the specified function
	 * @param name the name of the function
	 * @param value the new function
	 * @since 1.0
	 */
	public void set(String name, Function value) {
		FUNC.replace(name, value);
	}
	
	/**
	 * Clears all functions
	 * @since 1.0
	 */
	public void clearAll() {
		FUNC.clear();
	}
	
	/**
	 * Returns the HashMap that contains the functions
	 * @return the functions
	 * @since 1.0
	 */
	public HashMap<String,Function> getFunctionMap() {
		return FUNC;
	}
	
	/**
	 * Returns whether the specified function is defined
	 * @param name the function's name
	 * @return whether it is defined
	 * @since 1.0
	 */
	public boolean isDefined(String name) {
		return FUNC.containsKey(name);
	}
	
	/**
	 * Returns the specified function
	 * @param name the name of the function
	 * @return the function
	 * @since 1.0
	 */
	public Function get(String name) {
		return FUNC.get(name);
	}
}
