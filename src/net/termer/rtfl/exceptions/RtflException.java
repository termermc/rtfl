package net.termer.rtfl.exceptions;

public class RtflException extends Exception {
	private int ln = 0;
	
	public RtflException(String msg, int line) {
		super(msg);
		ln = line;
	}
	
	public int getLine() {
		return ln;
	}

	private static final long serialVersionUID = 1L;
}
