package net.termer.rtfl;

public class RtflFunction implements Function {
	String CODE = null;
	
	public RtflFunction(RtflInterpreter interpreter, String code) {
		CODE = code;
	}
	
	public Object run(Object[] args, RtflInterpreter interp) {
		Object result = null;
		for(int i = 0; i < args.length; i++) {
			interp.getVariables().register("arg"+Integer.toString(i+1), args[i]);
		}
		interp.getVariables().register("arglen", args.length);
		result = interp.execute(CODE);
		for(int i = 0; i < args.length; i++) {
			interp.getVariables().clear("arg"+Integer.toString(i+1));
		}
		interp.getVariables().clear("arglen");
		return result;
	}
}
