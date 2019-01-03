package net.termer.rtfl;

import java.util.HashMap;

import net.termer.rtfl.exceptions.RtflException;

public class RtflFunction implements Function {
	String CODE = null;
	
	public RtflFunction(RtflInterpreter interpreter, String code) {
		CODE = code;
	}
	
	public Object run(Object[] args, RtflInterpreter interp, HashMap<String, String> localVars) throws RtflException {
		Object result = null;
		for(int i = 0; i < args.length; i++) {
			interp.getVariables().register("arg"+Integer.toString(i+1), args[i]);
		}
		interp.getVariables().register("arglen", args.length);
		result = interp.execute(CODE, localVars);
		for(int i = 0; i < args.length; i++) {
			interp.getVariables().clear("arg"+Integer.toString(i+1));
		}
		interp.getVariables().clear("arglen");
		return result;
	}
}
