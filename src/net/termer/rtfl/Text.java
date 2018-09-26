package net.termer.rtfl;

public class Text {
	/**
	 * Array of letters, numbers, and underscores for use with onlyContains()
	 * @since 1.0
	 */
	public static final char[] LETTERS_NUMBERS_AND_UNDERSCORES = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','3','4','5','6','7','8','9','_'};
	
	/**
	 * Returns whether the provided character is a number
	 * @param c the character
	 * @return whether it is a number
	 * @since 1.0
	 */
	public static boolean isNumber(char c) {
		return c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9';
	}
	
	/**
	 * Returns whether the provided String is a number
	 * @param s the String
	 * @return whether it is a number
	 * @since 1.0
	 */
	public static boolean isNumber(String s) {
		boolean num = true;
		String str = s;
		if(s.startsWith("-")) {
			str = s.substring(1);
		}
		for(char c : str.toCharArray()) {
			if(c!='.') {
				if(!isNumber(c)) num=false; break;
			}
		}
		return num;
	}
	
	/**
	 * Returns the substring between to two Strings
	 * @param source the source String
	 * @param opening the opening String
	 * @param closing the closing String
	 * @return the substring between the opening and closing
	 * @since 1.0
	 */
	public static String between(String source, String opening, String closing) {
		String val = null;
		if(source.indexOf(opening) > -1 && source.lastIndexOf(closing) > -1) {
			val = source.substring(source.indexOf(opening)+opening.length(), source.lastIndexOf(closing));
		}
		return val;
	}
	
	/**
	 * Checks whether the character at the specified location is enclosed in quotes or not
	 * @param source the text the character is in
	 * @param location the location of the character
	 * @param ignoreEscapes whether escaped quotes ( \" ) should be ignored
	 * @return whether the character is enclosed by quotes
	 * @since 1.0
	 */
	public static boolean isInQuotes(String source, int location, boolean ignoreEscapes) {
		boolean quoted = false;
		
		for(int i = 0; i < location+1; i++) {
			char c = source.charAt(i);
			if(c=='\"') {
				boolean ok = true;
				if(!ignoreEscapes) {
					if(i>0) {
						if(source.charAt(i-1)=='\\') {
							ok=false;
						}
					}
				}
				if(ok) quoted=!quoted;
			}
		}
		
		return quoted;
	}
	
	/**
	 * Checks whether the character at the specified location is enclosed in parenthesis or not
	 * @param source the text the character is in
	 * @param location the location of the character
	 * @return whether the character is enclosed by parenthesis
	 * @since 1.0
	 */
	public static boolean isInParenthesis(String source, int location) {
		boolean opening = false;
		boolean closing = false;
		for(int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if(c=='(') {
				opening=true;
			} else if(c==')') {
				closing=true;
			}
		}
		
		return opening&&closing;
	}
	
	/**
	 * Checks if the provided String only contains characters from the specified array of chars
	 * @param chars the array of acceptable characters
	 * @param str the String to check
	 * @return whether the String only contains characters from the char array
	 * @since 1.0
	 */
	public static boolean onlyContains(char[] chars, String str) {
		boolean ok = false;
		for(char ch : str.toLowerCase().toCharArray()) {
			for(char acceptableChar : chars) {
				if(ch==acceptableChar) {
					ok = true;
					break;
				}
			}
		}
		return ok;
	}
}
