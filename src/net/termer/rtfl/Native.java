package net.termer.rtfl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

import net.termer.rtfl.exceptions.RtflException;

public class Native {
	// Temporary vars
	private static RtflInterpreter INTERP = null;
	private static String CODE = null;
	
	public static final String[] FUNCTION_NAMES = {
			"print",
			"println",
			"inc",
			"dec",
			"equals",
			"more_than",
			"less_than",
			"concat",
			"var",
			"to_string",
			"eval",
			"load",
			"add",
			"sub",
			"mul",
			"div",
			"read_file",
			"write_file",
			"file_exists",
			"not",
			"gc",
			"open_terminal",
			"close_terminal",
			"read_terminal",
			"terminal_open",
			"exit",
			"array",
			"array_add",
			"array_remove",
			"array_get",
			"array_length",
			"and",
			"or",
			"split",
			"starts_with",
			"ends_with",
			"substring",
			"char_at",
			"string_length",
			"array_set",
			"type",
			"to_number",
			"string_replace",
			"library",
			"sleep",
			"read_http",
			"async"
	};
	public static final Function[] FUNCTIONS = {
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(args.length>0) {
						System.out.print(args[0]);
					}
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(args.length>0) {
						System.out.println(args[0]);
					} else {
						System.out.println();
					}
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(args.length>0) {
						if(args[0] instanceof String) {
							Object var = interp.getVariables().get((String)args[0]);
							if(var!=null) {
								if(var instanceof Double) {
									interp.getVariables().set((String)args[0],((Double)var)+1);
								}
							}
						}
					}
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(args.length>0) {
						if(args[0] instanceof String) {
							Object var = interp.getVariables().get((String)args[0]);
							if(var!=null) {
								if(var instanceof Double) {
									interp.getVariables().set((String)args[0],((Double)var)-1);
								}
							}
						}
					}
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Boolean val = false;
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof String) {
							val = ((String)args[0]).equals(args[1]);
						} else if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0]).equals(args[1]);
						} else if(args[0] instanceof Boolean && args[1] instanceof Boolean) {
							val = ((Boolean)args[0]).equals(args[1]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Boolean val = false;
					if(args.length>1) {
						if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0]).doubleValue() > ((Double)args[1]).doubleValue();
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Boolean val = false;
					if(args.length>1) {
						if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0]).doubleValue() <  ((Double)args[1]).doubleValue();
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					String val = null;
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof String) {
							val = ((String)args[0]).concat((String)args[1]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>0) {
						if(args[0] instanceof String) {
							val = interp.getVariables().get((String)args[0]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					String val = null;
					if(args.length>0) {
						if(args[0] == null) {
							val = "null";
						} else {
							val = args[0].toString();
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) throws RtflException {
					Object val = null;
					if(args.length>0) {
						if(args[0] instanceof String) {
							val = interp.execute((String)args[0]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) throws RtflException {
					Object val = null;
					if(args.length>0) {
						if(args[0] instanceof String) {
							try {
								val = interp.execute(Rtfl.readFile((String)args[0]));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>1) {
						if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0])+((Double)args[1]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>1) {
						if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0])-((Double)args[1]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>1) {
						if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0])*((Double)args[1]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>1) {
						if(args[0] instanceof Double && args[1] instanceof Double) {
							val = ((Double)args[0])/((Double)args[1]);
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>0) {
						if(args[0] instanceof String) {
							try {
								val = Rtfl.readFile((String)args[0]);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof String) {
							try {
								Rtfl.writeFile((String)args[0], (String)args[1]);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>0) {
						if(args[0] instanceof String) {
							val = new File((String)args[0]).exists();
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					if(args.length>0) {
						if(args[0] instanceof Boolean) {
							val = !(Boolean)args[0];
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					System.gc();
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(!interp.isTerminalInputOpen()) {
						interp.openTerminalInput();
					}
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(interp.isTerminalInputOpen()) {
						try {
							interp.closeTerminalInput();
						} catch (RtflException e) {
							e.printStackTrace();
						}
					}
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					String val = null;
					if(interp.isTerminalInputOpen()) {
						try {
							val = interp.readTerminalInput();
						} catch (RtflException e) {
							e.printStackTrace();
						}
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Boolean val = null;
					if(interp.isTerminalInputOpen()) {
						val = interp.isTerminalInputOpen();
					}
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					System.exit(0);
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					return new ArrayList<Object>();
				}
			},
			new Function() {
				@SuppressWarnings("unchecked")
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>1) {
						if(args[0] instanceof ArrayList) {
							((ArrayList<Object>)args[0]).add(args[1]);
						}
					}
					
					return val;
				}
			},
			new Function() {
				@SuppressWarnings("unchecked")
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>1) {
						if(args[0] instanceof ArrayList && args[1] instanceof Double) {
							((ArrayList<Object>)args[0]).remove(((Double)args[1]).intValue());
						}
					}
					
					return val;
				}
			},
			new Function() {
				@SuppressWarnings("unchecked")
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>1) {
						if(args[0] instanceof ArrayList && args[1] instanceof Double) {
							val = ((ArrayList<Object>)args[0]).get(((Double)args[1]).intValue());
						}
					}
					
					return val;
				}
			},
			new Function() {
				@SuppressWarnings("unchecked")
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>0) {
						if(args[0] instanceof ArrayList) {
							val = new Double(((ArrayList<Object>)args[0]).size());
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>1) {
						if(args[0] instanceof Boolean && args[1] instanceof Boolean) {
							val = (Boolean)args[0] && (Boolean)args[1];
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>1) {
						if(args[0] instanceof Boolean && args[1] instanceof Boolean) {
							val = (Boolean)args[0] || (Boolean)args[1];
						}
					}
					
					return val;
				}
			},
			new Function() {
				public ArrayList<String> run(Object[] args, RtflInterpreter interp) {
					ArrayList<String> val = null;
					
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof String) {
							val = new ArrayList<String>();
							String[] splitted = ((String)args[0]).split((String)args[1]);
							for(String part : splitted) {
								val.add(part);
							}
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Boolean val = null;
					
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof String) {
							val = ((String)args[0]).startsWith((String)args[1]);
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Boolean val = null;
					
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof String) {
							val = ((String)args[0]).endsWith((String)args[1]);
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					String val = null;
					
					if(args.length>2) {
						if(args[0] instanceof String && args[1] instanceof Double && args[2] instanceof Double) {
							val = ((String)args[0]).substring(((Double)args[1]).intValue(), ((Double)args[2]).intValue());
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>1) {
						if(args[0] instanceof String && args[1] instanceof Double) {
							val = ((String)args[0]).charAt(((Double)args[1]).intValue())+"";
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>0) {
						if(args[0] instanceof String) {
							val = new Double(((String)args[0]).length());
						}
					}
					
					return val;
				}
			},
			new Function() {
				@SuppressWarnings("unchecked")
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>2) {
						if(args[0] instanceof ArrayList && args[1] instanceof Double) {
							val = ((ArrayList<Object>)args[0]).set(((Double)args[1]).intValue(), args[2]);
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					String val = "object";
					
					if(args.length>0) {
						if(args[0] instanceof String) {
							val = "string";
						} else if(args[0] instanceof Double) {
							val = "number";
						} else if(args[0] instanceof ArrayList) {
							val = "array";
						} else if(args[0] instanceof Boolean) {
							val = "boolean";
						} else if(args[0] == null) {
							val = "null";
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>0) {
						if(args[0] instanceof String) {
							try {
								val = Double.parseDouble((String)args[0]);
							} catch(NumberFormatException e) {}
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					Object val = null;
					
					if(args.length>0) {
						if(args[0] instanceof String && args[1] instanceof String && args[2] instanceof String) {
							val = ((String)args[0]).replace((String)args[1], (String)args[2]);
						}
					}
					
					return val;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(args.length>0) {
						if(args[0] instanceof String) {
							try {
								JarLoader.loadJar(new File((String)args[0])).initialize(interp);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) {
					if(args.length > 0) {
						if(args[0] instanceof Double) {
							try {
								Thread.sleep(((Double)args[0]).longValue());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					
					return null;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) throws RtflException {
					Object res = null;
					if(args.length > 0) {
						if(args[0] instanceof String) {
							try {
								Connection conn = Jsoup.connect((String)args[0])
										.followRedirects(true)
										.ignoreContentType(true)
										.ignoreHttpErrors(true);
								
								if(args.length > 1) {
									if(args[1] instanceof String) {
										String mthd = (String)args[1];
										if(mthd.equalsIgnoreCase("POST")) {
											conn.method(Method.POST);
										}
									}
								}
								
								res = conn.execute().body();
							} catch (IOException e) {
								throw new RtflException("Failed to connect to URL ("+(String)args[0]+")", 1);
							}
						}
					}
					return res;
				}
			},
			new Function() {
				public Object run(Object[] args, RtflInterpreter interp) throws RtflException {
					Object res = null;
					if(args.length > 0) {
						if(args[0] instanceof String) {
							INTERP = interp;
							CODE = (String)args[0];
							new Thread() {
								public void run() {
									try {
										INTERP.execute(CODE);
									} catch (RtflException e) {
										e.printStackTrace();
									}
									INTERP = null;
									CODE = null;
								}
							}.start();
						}
					}
					return res;
				}
			}
	};
}
