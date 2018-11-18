package net.termer.rtfl;

import net.termer.rtfl.exceptions.RtflException;

public interface Function {
	public Object run(Object[] args, RtflInterpreter interp) throws RtflException;
}
