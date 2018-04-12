package parser;

import java.io.IOException;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private final Lexer lexer;
	private Token look;
	
	public Parser(Lexer lexer) throws IOException {
		this.lexer = lexer;
		move();
	}
	
	private void move() throws IOException {
		look = lexer.scan();
		System.out.println(look.lexeme);
	}
	
	private void match(int t) throws IOException {
		if(look.tag == t) {
			move();
		} else {
			error("Syntax Error");
		}
	}
	
	private void error(String s) {
		throw new Error("near line " + lexer.line + " : " + s);
	}
	
	public void start() throws IOException {
		program();
	}
	
	private void program() throws IOException {
		match(Tag.PROGRAM);
		match(Tag.ID);
		block();
	}
	
	private void block() throws IOException {
		match('{');
		match('}');
	}
	
	/*private void decls() throws IOException {
		while(look.tag == Tag.BASIC_TYPE) {
			type();
			match(Tag.ID);
			match(';');
		}
	}
	
	private void type() throws IOException {
		match(Tag.BASIC_TYPE);
		if(look.tag == '[') {
			dims();
		}
	}
	
	private void dims() throws IOException {
		match('[');
		match(Tag.NUM);
		match(']');
		if(look.tag == '[') {
			dims();
		}
	}
	
	private void stmts() throws IOException {
		if(look.tag == '}') {
			
		} else {
			stmt();
			stmts();
		}
	}
	
	private void stmt() throws IOException {
		switch(look.tag) {
			case ';':
				move();
				return;
			case Tag.IF:
				match(Tag.IF);
				match('(');
				bool();
				match(')');
				stmt();
				if(look.tag != Tag.ELSE) {
					return;
				}
				match(Tag.ELSE);
				stmt();
				return;
			case Tag.WHILE:
				match(Tag.WHILE);
				match('(');
				bool();
				match(')');
				stmt();
				return;
			case Tag.DO:
				match(Tag.DO);
				stmt();
				match(Tag.WHILE);
				match('(');
				bool();
				match(')');
				match(';');
				return;
			case Tag.BREAK:
				match(Tag.BREAK);
				match(';');
				return;
			case '{':
				block();
				return;
			default:
				assign();
		}
	}
	
	private void assign() throws IOException {
		match(Tag.ID);
		if(look.tag == '=') {
			move();
			bool();
		} else {
			offset();
			match('=');
			bool();
		}
		match(';');
	}
	
	private void bool() throws IOException {
		join();
		while(look.tag == Tag.OR) {
			move();
			join();
		}
	}
	
	private void join() throws IOException {
		equality();
		while(look.tag == Tag.AND) {
			move();
			equality();
		}
	}
	
	private void equality() throws IOException {
		rel();
		while(look.tag == Tag.EQUAL || look.tag == Tag.NOTEQUAL) {
			move();
			rel();
		}
	}
	
	public void rel() throws IOException {
		expr();
		switch(look.tag) {
			case '<':
			case Tag.LE:
			case Tag.GE:
			case '>':
				move();
				expr();
				return;
			default:
		}
	}
	
	private void expr() throws IOException {
		term();
		while(look.tag == '+' || look.tag == '-') {
			move();
			term();
		}
	}
	
	private void term() throws IOException {
		unary();
		while(look.tag == '*' || look.tag == '/') {
			move();
			unary();
		}
	}
	
	private void unary() throws IOException {
		if(look.tag == '-') {
			move();
			unary();
		} else if(look.tag == '!') {
			move();
			unary();
		} else {
			factor();
		}
	}
	
	private void factor() throws IOException {
		switch(look.tag) {
			case '(':
				move();
				bool();
				match(')');
				return;
			case Tag.NUM:
				move();
				return;
			case Tag.REAL:
				move();
				return;
			case Tag.TRUE:
				move();
				return;
			case Tag.FALSE:
				move();
				return;
			case Tag.ID:
				move();
				if(look.tag != '[') {
					
				} else {
					offset();
				}
				return;
			default:
				error("Syntax error");
		}
	}
	
	private void offset() throws IOException {
		match('[');
		bool();
		match(']');
		while(look.tag == '[') {
			match('[');
			bool();
			match(']');
		}
	}*/
}
