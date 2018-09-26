package net.termer.rtfl.expressions;

import java.util.HashMap;

import net.termer.rtfl.Function;

public class Functions {
private HashMap<String,Function> FUNC = new HashMap<String,Function>();
	
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
