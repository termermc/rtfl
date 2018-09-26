package net.termer.rtfl.expressions;

import java.util.HashMap;

public class Variables {
	private HashMap<String,Object> VARS = new HashMap<String,Object>();
	
	/**
	 * Registers a variable
	 * @param name the name of the variable
	 * @param value the value of the variable
	 * @since 1.0
	 */
	public void register(String name, Object value) {
		VARS.put(name, value);
	}
	
	/**
	 * Clears the specified variable
	 * @param name the name of variable
	 * @since 1.0
	 */
	public void clear(String name) {
		VARS.remove(name);
	}
	
	/**
	 * Sets the specified variable to the specified value
	 * @param name the name of the variable
	 * @param value the value to set it to
	 * @since 1.0
	 */
	public void set(String name, Object value) {
		VARS.replace(name, value);
	}
	
	/**
	 * Clears all variables
	 * @since 1.0
	 */
	public void clearAll() {
		VARS.clear();
	}
	
	/**
	 * Returns the HashMap that contains the variables
	 * @return the variables
	 * @since 1.0
	 */
	public HashMap<String,Object> getVariableMap() {
		return VARS;
	}
	
	/**
	 * Returns whether the specified variable is defined
	 * @param name the variable's name
	 * @return whether it is defined
	 * @since 1.0
	 */
	public boolean isDefined(String name) {
		return VARS.containsKey(name);
	}
	
	/**
	 * Returns the value of the specified variable
	 * @param name the name of the variable
	 * @return the variable's value
	 * @since 1.0
	 */
	public Object get(String name) {
		return VARS.get(name);
	}
}