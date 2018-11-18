package net.termer.rtfl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.termer.rtfl.exceptions.RtflException;
import net.termer.rtfl.expressions.Expressions;
import net.termer.rtfl.expressions.Functions;
import net.termer.rtfl.expressions.Variables;

public class RtflInterpreter {
	private Variables VARS = new Variables();
	private Functions FUNC = new Functions();
	private Expressions EXP = new Expressions(this);
	private BufferedReader TERM = null;
	
	public RtflInterpreter() {
		// Initialize native functions
		for(int i = 0; i < Native.FUNCTIONS.length; i++) {
			FUNC.register(Native.FUNCTION_NAMES[i], Native.FUNCTIONS[i]);
		}
	}
	
	/**
	 * Opens the system terminal input reader
	 * @since 1.0
	 */
	public void openTerminalInput() {
		if(TERM==null) {
			TERM = new BufferedReader(new InputStreamReader(System.in));
		}
	}
	
	/**
	 * Closes the system terminal input reader
	 * @throws RtflException if closing it fails
	 * @since 1.0
	 */
	public void closeTerminalInput() throws RtflException {
		if(TERM!=null) {
			try {
				TERM.close();
				TERM = null;
			} catch (IOException e) {
				throw new RtflException("failed to close terminal input: "+e.getMessage(), 1);
			}
		}
	}
	
	/**
	 * Returns whether the terminal input reader is open
	 * @return whether the terminal input is open
	 * @since 1.0
	 */
	public boolean isTerminalInputOpen() {
		return TERM!=null;
	}
	
	/**
	 * Reads a line from the terminal input, if open
	 * @return the read line
	 * @throws RtflException if reading a line fails, or input is not open
	 * @since 1.0
	 */
	public String readTerminalInput() throws RtflException {
		String line = null;
		if(TERM==null) {
			throw new RtflException("terminal input is not open", 1);
		} else {
			try {
				line = TERM.readLine();
			} catch (IOException e) {
				throw new RtflException("failed to read terminal input: "+e.getMessage(), 1);
			}
		}
		return line;
	}
	
	/**
	 * Returns the Variables object for this interpreter
	 * @return the Variable object
	 * @since 1.0
	 */
	public Variables getVariables() {
		return VARS;
	}
	
	/**
	 * Returns the Functions object for this interpreter
	 * @return the Functions object
	 * @since 1.0
	 */
	public Functions getFunctions() {
		return FUNC;
	}
	
	/**
	 * Retusn the Expressions object for this interpreter
	 * @return the Expressions object
	 * @since 1.0
	 */
	public Expressions getExpressions() {
		return EXP;
	}
	
	/**
	 * Executes the provided Rtfl code
	 * @param rtfl the code
	 * @throws RtflException 
	 * @since 1.0
	 */
	public Object execute(String rtfl) throws RtflException {
		String[] lines = null;
		Object result = null;
		if(rtfl.contains("\n")) {
			lines = rtfl.split("\n");
		} else {
			lines = new String[] {rtfl};
		}
		
		int index = 0;
		while(index < lines.length) {
			// Trim and remove trailing ";" if present
			String line = lines[index].trim();
			if(line.endsWith(";")) {
				line = line.substring(0, line.length()-1);
			}
			
				if(!line.startsWith("//") && !line.isEmpty()) {
					if(line.startsWith("goto ")) {
						index = EXP.getNumberValue(line.substring(5), index).intValue();
					} else if(line.startsWith("def ")) {
						// Defines a variable
						if(line.contains("=")) {
							String name = line.substring(4).split("=")[0].trim();
							if(Text.onlyContains(Text.LETTERS_NUMBERS_AND_UNDERSCORES, name)) {
								if(name.startsWith("arg")) {
									throw new RtflException("you cannot begin variable names with \"arg\"", index);
								} else {
									if(Text.isNumber(name.charAt(0))) {
										throw new RtflException("variable names may not start with numbers", index);
									} else {
										String exp = line.substring(line.indexOf('=')+1);
										VARS.register(name, EXP.getValue(exp, index));
									}
								}
							} else {
								throw new RtflException("variable names may only contain letters, numbers, and underscores", index);
							}
						} else {
							throw new RtflException("no value specified", index);
						}
					} else if(line.startsWith("undef ")) {
						VARS.clear(line.substring(6));
					} else if(line.startsWith("func ")) {
						String name = line.substring(5, line.substring(5).indexOf(' ')+5);
						if(Text.onlyContains(Text.LETTERS_NUMBERS_AND_UNDERSCORES, name)) {
							if(index<lines.length-2) {
								String body = "";
								int skip = 0;
								boolean searching = true;
								while(searching) {
									if(index<lines.length) {
										index++;
										boolean proceed = true;
										String ln = lines[index].trim();
										for(char c : ln.toCharArray()) {
											if(c=='{') {
												skip++;
											} else if(c=='}') {
												if(skip>0) {
													skip--;
												} else {
													proceed = false;
												}
											}
										}
										if(proceed) {
											if(body.length()>0) body+="\n";
											body+=ln;
										} else {
											searching = false;
										}
									} else {
										break;
									}
								}
								FUNC.register(name, new RtflFunction(this, body));
							} else {
								throw new RtflException("unclosed curly bracket", index);
							}
						} else {
							throw new RtflException("function names may only contain letters, numbers, and underscores", index);
						}
					} else if(line.startsWith("unfunc ")) {
						FUNC.clear(line.substring(7));
					} else if(line.startsWith("return ")) {
						// Sets the return value;
						result = EXP.getValue(line.substring(7), index);
					} else if(line.startsWith("if ")) {
						String exp = line.substring(3, line.indexOf('{'));
						if(index<lines.length-2) {
							String body = "";
							int skip = 0;
							boolean searching = true;
							while(searching) {
								if(index<lines.length) {
									index++;
									boolean proceed = true;
									String ln = lines[index].trim();
									for(char c : ln.toCharArray()) {
										if(c=='{') {
											skip++;
										} else if(c=='}') {
											if(skip>0) {
												skip--;
											} else {
												proceed = false;
											}
										}
									}
									if(proceed) {
										if(body.length()>0) body+="\n";
										body+=ln;
									} else {
										searching = false;
									}
								} else {
									break;
								}
							}
							Object logic = EXP.getValue(exp, index);
							if(logic instanceof Boolean) {
								if(((Boolean)logic).booleanValue()) {
									execute(body);
								}
							} else {
								throw new RtflException("expression in if statement does not return a boolean", index);
							}
						} else {
							throw new RtflException("unclosed curly bracket", index);
						}
					} else if(line.startsWith("while ")) {
						String exp = line.substring(6, line.indexOf('{'));
						if(index<lines.length-2) {
							String body = "";
							int skip = 0;
							boolean searching = true;
							while(searching) {
								if(index<lines.length) {
									index++;
									boolean proceed = true;
									String ln = lines[index].trim();
									for(char c : ln.toCharArray()) {
										if(c=='{') {
											skip++;
										} else if(c=='}') {
											if(skip>0) {
												skip--;
											} else {
												proceed = false;
											}
										}
									}
									if(proceed) {
										if(body.length()>0) body+="\n";
										body+=ln;
									} else {
										searching = false;
									}
								} else {
									break;
								}
							}
							while(true) {
								Object logic = EXP.getValue(exp, index);
								if(logic instanceof Boolean) {
									if(((Boolean)logic).booleanValue()) {
										execute(body);
									} else {
										break;
									}
								} else {
									throw new RtflException("expression in while statement does not return a boolean", index);
								}
							}
						} else {
							throw new RtflException("unclosed curly bracket", index);
						}
					} else if(line.startsWith("error ")) {
						String exp = line.substring(6, line.indexOf('{')).trim();
						if(index<lines.length-2) {
							String body = "";
							int skip = 0;
							boolean searching = true;
							while(searching) {
								if(index<lines.length) {
									index++;
									boolean proceed = true;
									String ln = lines[index].trim();
									for(char c : ln.toCharArray()) {
										if(c=='{') {
											skip++;
										} else if(c=='}') {
											if(skip>0) {
												skip--;
											} else {
												proceed = false;
											}
										}
									}
									if(proceed) {
										if(body.length()>0) body+="\n";
										body+=ln;
									} else {
										searching = false;
									}
								} else {
									break;
								}
							}
							if(Text.onlyContains(Text.LETTERS_NUMBERS_AND_UNDERSCORES, exp)) {
								VARS.register(exp, "ok");
								try {
									execute(body);
								} catch(RtflException ex) {
									VARS.set(exp, "Error on line "+(index)+": "+ex.getMessage());
								}
							} else {
								throw new RtflException("variable names may only contain letters, numbers, and underscores", index);
							}
						} else {
							throw new RtflException("unclosed curly bracket", index);
						}
					} else if(line.length()>2) {
						boolean varSet = false;
						if(line.contains("=")) {
							if(!Text.isInQuotes(line, line.indexOf('='), false)) {
								String name = line.split("=")[0];
								if(EXP.isVar(name)) {
									varSet = true;
								} else {
									throw new RtflException("referenced variable \""+name+"\" is undefined", index);
								}
							}
						}
						if(varSet) {
							VARS.set(line.split("=")[0].trim(), EXP.getValue(line.substring(line.indexOf('=')+1).trim(), index));
						} else if(EXP.isFunc(line)) {
							EXP.getFuncValue(line, index);
						} else {
							throw new RtflException("undefined function", index);
						}
					}
				}
			index++;
		}
		
		return result;
	}
}
