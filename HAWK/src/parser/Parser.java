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
		if(look.tag == Tag.ID && look.lexeme.length() > 32)
		{
			error("Syntax Error: identifier greater than 32 characters");
		}
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

	private void declaration() throws IOException {
		if(look.tag == Tag.NUM || look.tag == Tag.REAL || look.tag == Tag.BASIC_TYPE || look.tag == Tag.STRING_TYPE){
			move();
			match(Tag.ID);
			match(';');
		}else if(look.tag == ';'){
			move();
		}
	}
	
	private void statements() throws IOException {
		if(look.tag == '}') {
			
		} else {
			statement();
			statements();
		}
	}
	
	private void statement() throws IOException {
		switch(look.tag) {
		case ';':
			move();
			return;
		case Tag.IF:
			if_statement();
			return;
		case Tag.DO:
			do_while_statement();
			return;
		case Tag.WHILE:
			while_statement();
			return;
		case Tag.REPEAT:
			repeat_statement();
			return;
		case Tag.INCASE:
			incase_statement();
			return;
		case Tag.BASIC_TYPE:
		case Tag.NUM:
		case Tag.STRING_TYPE:
		case Tag.REAL:
			declaration();
		case Tag.ASSIGNMENT:
			assignment();
			return;
//		case Tag.ME:
//			repeat_statement();
//			return;
//
//Method call
//print
//scan
//		case Tag.BREAK:
//			match(Tag.BREAK);
//			match(';');
//			return;
//		case '{':
//			block();
//			return;
		default:
//			assign();
		}
		
	}
	
	private void do_while_statement() throws IOException {
		if(look.tag == Tag.DO)
		{
			move();
			block();
			match(Tag.WHILE);
			match('(');
			condition();
			match(')');
			match(';');
		}
	}

	private void if_statements() throws IOException{
		if(look.tag == Tag.IF){
			move();
			match('(');
			condition();
			match(')');
			block();
			if(look.tag == Tag.ELSIF){
				elsif_statements();
			}else if(look.tag == Tag.ELSE){
				else_statements();
			}
		}
	}

	private void elsif_statements() throws IOException{
		if(look.tag == Tag.ELSIF){
			move();
			match('(');
			condition();
			match(')');
			block();
			if(look.tag == Tag.ELSIF){
				elsif_statements();
			}else if(look.tag == Tag.ELSE){
				else_statements();
			}
		}
	}

	private void else_statements() throws IOException{
		if(look.tag == Tag.ELSE){
			move();
			block();
		}
	}

	private void incase_statement() throws IOException{
		if(look.tag == Tag.INCASE){
			match(Tag.INCASE);
			match('(');
			condition();
			match(')');
			match('{');
			case_statements();
			match('}');
		}
	}

	/*private void type() throws IOException {
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

	/*private void stmts() throws IOException {
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
