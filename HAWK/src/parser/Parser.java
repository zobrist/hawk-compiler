package parser;

import java.io.IOException;

import javax.swing.JTextArea;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private final Lexer lexer;
	private Token look;

	private boolean EOFEncountered = false;
	private boolean mainEncountered = false;
	
	private JTextArea terminal = null;
	
	public Parser(Lexer lexer, JTextArea terminal) throws IOException {
		this.lexer = lexer;
		this.terminal = terminal;
		move();
//		System.out.println(look.lexeme);
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
		//System.out.println(look.lexeme);
	}

	private void error(String s) {
		if(terminal != null) {
			terminal.append("Compiler error near line " + lexer.line + " : " + s + "\n");
		}
		throw new Error("Compiler error near line " + lexer.line + " : " + s);
	}

	public void start() throws IOException {
		program();
	}

	private void program() throws IOException {
		match(Tag.PROGRAM);
		match(Tag.ID);
		body();
	}
	
	private void body() throws IOException{
		boolean isExit = false;
		match('{');
		while(true)
		{
			if(look.tag == Tag.NUM || look.tag == Tag.REAL || look.tag == Tag.BASIC_TYPE || look.tag == Tag.STRING_TYPE)
				declarations();
				
			else if(look.tag == Tag.VOID) {
				method_declaration();
			}
			else {
				break;
			}
			if(EOFEncountered || isExit) {
				break;
			}
		}
		match('}');
	}
	
	private void method_declaration() throws IOException{
		match('(');
		method_params();
		match(')');
		block();
	}
	
	private void block() throws IOException {
		if(look.tag == '{') {
			match('{');
			block();
		} else if(look.tag == '}') {
			return;
		} else {
			statements();
		}
		//match('}');
	}
	
	private void method_params() throws IOException {
		if(look.tag == Tag.NUM || look.tag == Tag.REAL || look.tag == Tag.BASIC_TYPE || look.tag == Tag.STRING_TYPE){
			move(); //to skip the data_type symbol
			match(Tag.ID);
			if(look.tag == ','){
				move();
				method_params();
			}
		}
	}
	
	
	private void declarations() throws IOException{
		if(look.tag == Tag.NUM || look.tag == Tag.REAL || look.tag == Tag.BASIC_TYPE || look.tag == Tag.STRING_TYPE){
			int type = look.tag;
			//System.out.println("Type Lexeme: " + look.lexeme);
			move(); //should be something that records datatypes
			String prevLexeme = look.lexeme;
			match(Tag.ID); //should be something that records names
			if(prevLexeme.equals("main")) {
				if(type == Tag.BASIC_TYPE && mainEncountered){
					error("int main method already invoked!");
				} else {
					mainEncountered = true;
				}
			}
		}//else if (look.tag == Tag.ID) {
		//	match(Tag.ID);
		//} this should not be possible
		
		if(look.tag == '='){
			assignment();
		}else if(look.tag == ','){
			declaration();
		}else if(look.tag == '(') {
			method_declaration();
		}else if(look.tag == ';'){
			move();
		}else{
			EOFEncountered = true;
			return;
		}
	}
	
	private void declaration() throws IOException {
		move(); //to skip the , symbol
		match(Tag.ID);
		
		if(look.tag == ','){
			declaration();
		}else if(look.tag == ';'){
			move();
			declaration();
		}else if(look.tag == '=') {
			assignment();
		}
	}
	
	private void statements() throws IOException {
		
		if(look.tag == '}') {
			match('}');
			return;
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
			if_statements();
			return;
		case Tag.FOR:
			for_loop();
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
			declarations();
			return;
		case Tag.PRINT:
			print();
			return;
		case Tag.GET:
			get();
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
			return;
//			assign();
		}
		
	}
	
	//needs to be checked pa
	private void method_call() throws IOException{
		if(look.tag == Tag.ID) {
			move();
			match('(');
			if(look.tag == Tag.ID) {
				param_ids();
			}
			match(')');
		}
	}
	
	private void print_params() throws IOException{
		if(look.tag == Tag.STRING_TYPE) {
			move();
			if(look.tag == '+') {
				print_params();
			}
		}else {
			expression();
			if(look.tag == '+') {
				print_params();
			}
		}
	}
	
	private void print() throws IOException{
		if(look.tag == Tag.PRINT) {
			move();
			print_params();
			match(';');
		}
	}
	
	private void get() throws IOException{
		if(look.tag == Tag.GET) {
			move();
			if(look.tag == Tag.NUM || look.tag == Tag.REAL || look.tag == Tag.STRING_TYPE) {
				match(';');
			}else {
				error("Syntax Error");
			}
		}
	}
	
	private void for_loop() throws IOException {
		if(look.tag == Tag.FOR) {
			move();
			match('(');
			assignment();
			match(';');
			condition();
			match(';');
			expression();
			match(')');
			block();
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
	
	public void while_statement() throws IOException {
		if(look.tag == Tag.WHILE){
			move();
			match('(');
			condition();
			match(')');
			block();
		}
	}

	private void if_statements() throws IOException{
		if(look.tag == Tag.IF){
			move();
			match('(');
			condition();
			match(')');  //here!!!!
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
		//	match(')');
			block();
//			match('}');
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
	
	public void case_statements() throws IOException {
		if(look.tag == Tag.IS){
			match('(');
			condition();
			match(')');
			match(Tag.THEN);
			statements();
			
			move();
			if(look.tag == Tag.BREAK){
				match(Tag.BREAK);
				match(';');
			}
			
			move();
			if(look.tag == Tag.DEFAULT){
				match(Tag.DEFAULT);
				match(';');
				statements();
			}else{
				case_statements();
			}
		}
	}
	
	public void condition() throws IOException{
		boolean parenthesis;
		if(look.tag == '('){
			parenthesis = true;
			move();
			expression();
			if(look.tag == ')'){
				move();
				parenthesis = false;
			}
			equality_relational();
			expression();
			if(look.tag == Tag.AND ||look.tag == Tag.OR){
				move();
				condition();
			}																	//kulang pa hin kun boolean an iya gininput
			if(parenthesis){
				match(')');
				parenthesis = false;
			}
		}else{
			expression();
			equality_relational();
			expression();
			if(look.tag != ')'){
				conditional_operators();
				condition();
			}
		}																//kulang pa hin kun boolean an iya gininput
	}
	
	public void conditional_operators() throws IOException{
		if(look.tag == Tag.AND){
			match(Tag.AND);
		}else if(look.tag == Tag.OR){
			match(Tag.OR);
		}
	}
	
	public void equality_relational() throws IOException{
		if(look.tag == Tag.EQUAL){
			match(Tag.EQUAL);
		}else if(look.tag == Tag.NOTEQUAL){
			match(Tag.NOTEQUAL);
		}else if(look.tag == Tag.GREATER){
			match(Tag.GREATER);
		}else if(look.tag == Tag.LESS){
			match(Tag.LESS);
		}else if(look.tag == Tag.GE){
			match(Tag.GE);
		}else if(look.tag == Tag.LE){
			match(Tag.LE);
		}
	}
	
	public void assignment() throws IOException{
		if(look.tag == Tag.STRING_TYPE || look.tag == Tag.BASIC_TYPE){
			type();
			match(Tag.ID);
			match('=');
			expression();
			match(';');
		}else{
//			match(Tag.ID);
			match('=');
			expression();
			match(';');
		}
		
	}
	
	public void repeat_statement() throws IOException{
		match(Tag.REPEAT);
		match('(');
		expression();
		match(')');
		match(Tag.TIMES);
		block();
	}
	
	public void expression() throws IOException{
		
		if(look.tag == '\'') {
			move();
			match(Tag.ID);
			move();
		}else if(look.tag == '\"'){
			//System.out.println("qwertyui");
			match('\"');
			match(Tag.ID);
			match('\"');
		} else if(look.tag == '('){
			move();
			expression();
			match(')');
			if(look.tag == '+' ||look.tag == '-'||look.tag == '*'||look.tag == '/'||look.tag == '%'){
				move();
				expression();
			}
		} else {
			move();
			if(look.tag == '+' ||look.tag == '-'||look.tag == '*'||look.tag == '/'||look.tag == '%'){
				move();
				expression();
			}
		}
		
	}
	
	public void type() throws IOException {
		if(look.tag == Tag.NUM){
			match(Tag.NUM);			
		}else if(look.tag == Tag.REAL){
			match(Tag.REAL);
		}else if(look.tag == Tag.STRING_TYPE){
			match(Tag.STRING_TYPE);
		}else{
			match(Tag.BASIC_TYPE);
		}
		
	}
	
	
	public void param_ids() throws IOException {
		match(Tag.ID);
		if(look.tag == ',')
		{
			param_ids();
		} else {
			return;
		}
	}
	
	public void scan_statement() throws IOException {
		match(Tag.GET);
		switch(look.tag)
		{
			case Tag.BASIC_TYPE:
				return;
			case Tag.NUM:
				return;
			case Tag.REAL:
				return;
			case Tag.STRING_TYPE:
				return;
			default:
				error("Syntax error");
				
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
