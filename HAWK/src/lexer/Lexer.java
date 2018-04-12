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
	}
	
	public Lexer() {
		reserveKeywords();
	}
	
	private void read() throws IOException {
		peek = (char) System.in.read();
	}
	
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
			} else if(peek == '\n') {
				line = line + 1;
			} else {
				break;
			}
		}
		
		switch(peek) {
			case '&':
				if(read('&')) {
					return Keyword.AND;
				} else {
					return new Token('&');
				}
			case '|':
				if(read('|')) {
					return Keyword.OR;
				} else {
					return new Token('|');
				}
			case '>':
				if(read('=')) {
					return Keyword.GE;
				} else {
					return new Token('>');
				}
			case '<':
				if(read('=')) {
					return Keyword.LE;
				} else {
					return new Token('<');
				}
			case '=':
				if(read('=')) {
					return Keyword.EQUAL;
				} else {
					return new Token('=');
				}
		}
		
		if(Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				read();
			} while(Character.isDigit(peek));
			
			if(peek != '.') {
				return new Num(v);
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
			return new Real(x);
		}
		
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
			return new Keyword(s, Tag.ID);
		}
		
		Token t = new Token(peek);
		peek = ' ';
		return t;
	}
}
