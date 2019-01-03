package net.termer.rtfl.expressions;

import java.util.ArrayList;
import java.util.HashMap;

import net.termer.rtfl.RtflInterpreter;
import net.termer.rtfl.Text;
import net.termer.rtfl.exceptions.RtflException;

public class Expressions {
	private RtflInterpreter INTERP = null;
	
	public Expressions(RtflInterpreter interpreter) {
		INTERP = interpreter;
	}
	
	public boolean isValid(String expression) {
		boolean valid = true;
		String exp = expression.trim();
		if(exp.isEmpty()) {
			valid = false;
		}
		return valid;
	}
	
	public boolean isString(String expression) {
		String exp = expression.trim();
		boolean string = false;
		if(isValid(exp)) {
			if(exp.startsWith("\"") && exp.endsWith("\"")) {
				if(exp.charAt(exp.lastIndexOf('\"')-1)!='\\') {
					string = true;
				}
			}
		}
		return string;
	}
	
	public boolean isFunc(String expression) {
		String exp = expression.trim();
		boolean func = false;
		if(isValid(exp)) {
			if(!Text.isNumber(exp.charAt(0))) {
				if(exp.contains("(") && exp.contains(")")) {
					if(!(Text.isInQuotes(exp, exp.indexOf('('), false) || Text.isInQuotes(exp, exp.lastIndexOf(')'), false))) {
						String name = exp.substring(0, exp.indexOf('('));
						if(INTERP.getFunctions().isDefined(name)) {
							func = true;
						}
					}
				}
			}
		}
		return func;
	}
	
	public boolean isVar(String expression, HashMap<String, String> localVars) {
		String exp = expression.trim();
		boolean var = false;
		if(isValid(exp)) {
			if(!isString(exp)) {
				if(!Text.isNumber(exp.charAt(0)) && !(exp.contains("(")||exp.contains(")"))) {
					if(!isFunc(exp)) {
						if(INTERP.getVariables().isDefined(exp) || localVars.containsKey(exp)) {
							var = true;
						}
					}
				}
			}
		}
		return var;
	}
	
	public Double getNumberValue(String expression, int line, HashMap<String, String> localVars) throws RtflException {
		String exp = expression.trim();
		Double val = null;
		
		if(isValid(exp)) {
			// Determine if it is a number
			if(Text.isNumber(exp)) {
				val = Double.parseDouble(exp);
			} else {
				if(isVar(exp, localVars)) {
					Object var = INTERP.getVariables().get(exp);
					
					if(var instanceof Double) {
						val = (Double)var;
					} else {
						throw new RtflException("referenced variable is not a number", line);
					}
				} else if(isFunc(exp)) {
					Object func = getFuncValue(exp, line, localVars);
					
					if(func instanceof Double) {
						val = (Double)func;
					} else if(func instanceof Integer) {
						val = ((Integer)func).doubleValue();
					} else {
						throw new RtflException("referenced variable is not a number", line);
					}
				} else {
					throw new RtflException("referenced variable \""+exp+"\" is undefined", line);
				}
			}
		}
		
		return val;
	}
	
	public String getStringValue(String expression, int line, HashMap<String, String> localVars) throws RtflException {
		String exp = expression.trim();
		String val = null;
		if(isValid(exp)) {
			if(!isVar(exp, localVars)) {
				int quotes = 0;
				for(int i = 0; i < exp.length(); i++) {
					boolean escaped = false;
					if(i>0) {
						if(exp.charAt(i-1)=='\\') {
							escaped = true;
						}
					}
					if(!escaped) {
						if(exp.charAt(i)=='\"') quotes++;
					}
				}
				if(quotes<3) {
					if(exp.startsWith("\"")) {
						if(exp.charAt(exp.lastIndexOf('\"')-1) == '\\') {
							throw new RtflException("unclosed quote", line);
						} else {
							if(exp.endsWith("\"")) {
								val = exp.substring(1, exp.length()-1).replace("\\\"", "\"")
										.replace("\\n", "\n")
										.replace("\\t", "\t")
										.replace("\\\\", "\\");
							} else {
								throw new RtflException("unclosed quote", line);
							}
						}
					}
				} else {
					throw new RtflException("unclosed quote", line);
				}
			} else {
				if(INTERP.getVariables().isDefined(exp)) {
					Object var = INTERP.getVariables().get(exp);
					
					if(var instanceof String) {
						val = (String)var;
					} else {
						throw new RtflException("referenced variable is not a String", line);
					}
				} else {
					throw new RtflException("referenced variable \""+exp+"\" is undefined", line);
				}
			}
		}
		return val;
	}
	
	public Object getFuncValue(String expression, int line, HashMap<String, String> localVars) throws RtflException {
		Object val = null;
		String exp = expression.trim();
		
		if(isValid(exp)) {
			if(isFunc(exp)) {
				ArrayList<Object> args = new ArrayList<Object>();
				String name = exp.substring(0, exp.indexOf('('));
				String enclosed = exp.substring(exp.indexOf('(')+1, exp.length()-1).trim();
				if(!enclosed.isEmpty()) {
					boolean multi = false;
					if(enclosed.contains(",")) {
						for(int i = 0; i < enclosed.length(); i++) {
							char c = enclosed.charAt(i);
							if(c==',') {
								if(!Text.isInQuotes(enclosed, i, false) && !Text.isInParenthesis(enclosed, i)) {
									multi = true;
								}
							}
						}
					}
					if(multi) {
						ArrayList<String> expParams = new ArrayList<String>();
						int splitStart = 0;
						for(int i = 0; i < enclosed.length(); i++) {
							char c = enclosed.charAt(i);
							if(c==',') {
								if(!Text.isInQuotes(enclosed, i, false) && !Text.isInParenthesis(enclosed, i)) {
									expParams.add(enclosed.substring(splitStart, i));
									splitStart = i+1;
								}
							}
						}
						expParams.add(enclosed.substring(splitStart, enclosed.length()));
						for(String expParam : expParams) {
							args.add(getValue(expParam, line, localVars));
						}
					} else {
						args.add(getValue(enclosed, line, localVars));
					}
				}
				Object[] argsArray = args.toArray(new Object[0]);
				String[] vars = INTERP.getLocalVariables().getVariableMap().keySet().toArray(new String[0]);
				val = INTERP.getFunctions().get(name).run(argsArray, INTERP, Functions.copyLocalVarList(localVars));
				INTERP.getLocalVariables().purgeAllNew(vars);
			} else {
				throw new RtflException("expression is not a function", line);
			}
		}
		
		return val;
	}
	
	public Object getValue(String expression, int line, HashMap<String, String> localVars) throws RtflException {
		Object val = null;
		String exp = expression.trim();
		
		// Work out reserved phrases
		if(isValid(exp)) {
			if(exp.equalsIgnoreCase("true")) {
				val = true;
			} else if(exp.equalsIgnoreCase("false")) {
				val = false;
			} else if(exp.equalsIgnoreCase("null")) {}
			else if(exp.equalsIgnoreCase("void")) {}
			else if(exp.equalsIgnoreCase("undefined")) {}
			else {
				if(Text.isNumber(exp)) {
					val = getNumberValue(exp, line, localVars);
				} else if(isString(exp)) {
					val = getStringValue(exp, line, localVars);
				} else if(isVar(exp, localVars)) {
					if(localVars.containsKey(exp)) {
						val = INTERP.getLocalVariables().get(localVars.get(exp));
					} else {
						val = INTERP.getVariables().get(exp);
					}
				} else if(isFunc(exp)) {
					val = getFuncValue(exp, line, localVars);
				} else {
					throw new RtflException("referenced variable \""+exp+"\" is undefined", line);
				}
			}
		}
		
		return val;
	}
}
