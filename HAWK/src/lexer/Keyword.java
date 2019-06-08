package lexer;

public class Keyword extends Token {
	public final String lexeme;
	/**
	 * creates an object of the keyword (lexeme) with its associated numerical tag
	 * @param lexeme
	 * @param tag
	 */
	public Keyword(String lexeme, int tag) {
		super(tag, lexeme);
		this.lexeme = lexeme;
	}
	
	//start
	public static final Keyword PROGRAM = new Keyword("program", Tag.PROGRAM);
	
	//conditional operators
	public static final Keyword AND = new Keyword("&&", Tag.AND),
			OR = new Keyword("||", Tag.OR);
			
	//equality and relational operators
	public static final Keyword LE = new Keyword("<=", Tag.LE),
			GE = new Keyword(">=", Tag.GE),
			EQUAL = new Keyword("==", Tag.EQUAL),
			NOTEQUAL = new Keyword("!=", Tag.NOTEQUAL),
			LESS = new Keyword("<", Tag.LESS),
			GREATER = new Keyword(">", Tag.GREATER);
	
	//conditional statements
	public static final Keyword IF = new Keyword("if", Tag.IF),
			ELSE = new Keyword("else", Tag.ELSE),
			ELSIF = new Keyword("elsif", Tag.ELSIF),
			INCASE = new Keyword("incase", Tag.INCASE),
			IS = new Keyword("is", Tag.IS);
	
	//loop statements
	public static final Keyword FOR = new Keyword("for", Tag.FOR),
			DO = new Keyword("do", Tag.DO),
			WHILE = new Keyword("while", Tag.WHILE),
			REPEAT = new Keyword("repeat", Tag.REPEAT),
			TIMES = new Keyword("times", Tag.TIMES),
			BREAK = new Keyword("break", Tag.BREAK);
			
	//boolean
	public static final Keyword TRUE = new Keyword("true", Tag.TRUE),
			FALSE = new Keyword("false", Tag.FALSE);
	
	//null
	public static final Keyword NIL = new Keyword("nil", Tag.NIL);
	public static final Keyword STRING = new Keyword("string", Tag.STRING_TYPE);
	public static final Keyword FUNC = new Keyword("func", Tag.FUNC);
	public static final Keyword VOID = new Keyword("void", Tag.VOID);
	public static final Keyword THEN = new Keyword("then", Tag.THEN);
	public static final Keyword DEFAULT = new Keyword("default", Tag.DEFAULT);
	public static final Keyword RETURN = new Keyword("return", Tag.RETURN);
	public static final Keyword EXIT = new Keyword("exit", Tag.EXIT);
	public static final Keyword GET = new Keyword("get", Tag.GET);
	public static final Keyword PRINT = new Keyword("print", Tag.PRINT);
	public static final Keyword NUM = new Keyword("int", Tag.NUM);
}
