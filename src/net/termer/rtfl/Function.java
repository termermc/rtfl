package net.termer.rtfl;

import java.util.HashMap;

import net.termer.rtfl.exceptions.RtflException;

public interface Function {
	public Object run(Object[] args, RtflInterpreter interp, HashMap<String, String> localVars) throws RtflException;
}
