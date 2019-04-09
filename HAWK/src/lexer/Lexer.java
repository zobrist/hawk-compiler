package lexer;

import java.io.IOException;
import java.util.Hashtable;

import symbol.Type;

public class Lexer {
	private Hashtable<String, Keyword> words = new Hashtable<>();
	public static int line = 1;
	private char peek = ' ';
	
	private void reserve(Keyword kword) {
		words.put(kword.lexeme, kword);
	}
	
	private void reserveKeywords() {
		//conditional statements
		reserve(Keyword.IF);
		reserve(Keyword.ELSE);
		reserve(Keyword.ELSIF);
		reserve(Keyword.INCASE);
		reserve(Keyword.IS);
		
		//loop statements
		reserve(Keyword.FOR);
		reserve(Keyword.DO);
		reserve(Keyword.WHILE);
		reserve(Keyword.REPEAT);
		reserve(Keyword.TIMES);
		reserve(Keyword.BREAK);
		
		//boolean
		reserve(Keyword.FALSE);
		reserve(Keyword.TRUE);
		
		//data types
		reserve(Type.BOOLEAN);
		reserve(Type.CHAR);
		reserve(Type.FLOAT);
		reserve(Type.INT);
		reserve(Keyword.STRING);
		reserve(Keyword.PROGRAM);
		reserve(Keyword.NIL);
		reserve(Keyword.FUNC);
	}
	
	public Lexer() {
		reserveKeywords();
	}
	
	/**
	 * reads a character and it becomes the current value for peek
	 * @throws IOException
	 */
	private void read() throws IOException {
		peek = (char) System.in.read();
	}
	
	/**
	 * checks if the current character being read is equal to c
	 * if not, it returns false
	 * otherwise, it sets peek to ' ' and return true
	 * @param c
	 * @return
	 * @throws IOException
	 */
	private boolean read(char c) throws IOException {
		read();
		
		if(peek != c) {
			return false;
		}
		peek = ' ';
		return true;
		
	}
	
	public Token scan() throws IOException {
		for(;; read()) {
			
			if(peek == ' ' || peek == '\t') {
				//continue
			} else if(peek == '\n' || peek == '\r') {
				
				line = line + 1;
			} else if(peek == '#') {
				/*
				 * comments
				 * skips those inside the "#()#"
				 */
				read();
				if(peek == '(') {                                                                
					// continue
					for(;;read()) {
						if(peek == ')') {
							read();
							if(peek == '#') {
								break;
							}
						}
					}
				} else {
					for(;; read()) {
						if(peek == '\n' || peek == '\r') {
							break;
						}
					}
				}
			} else {
				break;
			}
		}
		
		switch(peek) {
			
			case '&':
				if(read('&')) {
					return Keyword.AND;
				} else {
					return new Token('&', "&");
				}
			
			case '|':
				if(read('|')) {
					return Keyword.OR;
				} else {
					return new Token('|', "||");
				}
			case '>':
				if(read('=')) {
					return Keyword.GE;
				} else {
					return new Token('>', ">");
				}
			case '<':
				if(read('=')) {
					return Keyword.LE;
				} else {
					return new Token('<', "<");
				}
			case '=':
				if(read('=')) {
					return Keyword.EQUAL;
				} else {
					return new Token('=', "=");
				}
		}
		
		//will return a digit or a double
		if(Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				read();
			} while(Character.isDigit(peek));
			
			if(peek != '.') {
				return new Num(v, ""+v);
			}
			
			float x = v, d = 10;
			for(;;) {
				read();
				if(!Character.isDigit(peek)) {
					break;
				}
				x = x + Character.digit(peek, 10) / d;
				d = d*10;
			}
			return new Real(x, ""+x);
		}
		
		//will return keyword for variable name
		if(Character.isLetter((int) peek)) {
			StringBuilder b = new StringBuilder();
			do {
				b.append(peek);
				read();
			} while (Character.isLetterOrDigit((int)peek));
			String s = b.toString();
			Keyword w = (Keyword) words.get(s);
			if(w != null) {
				return w;
			}
			if(s.equals("print")) {
				return new Keyword(s, Tag.PRINT);
			}else if(s.equals("get")) {
				return new Keyword(s, Tag.GET);
			}else {
				return new Keyword(s, Tag.ID);				
			}
		}
		
		if(peek == '\"') {
			StringBuilder b = new StringBuilder();
			do {
				b.append(peek);
				read();
			} while(peek != '\"');
			read();
			String s = b.toString();
			return new Token(Tag.STRING_TYPE, s);
		}
		
		
		Token t = new Token(peek, ""+peek);
		peek = ' ';
		return t;
	}
}
