package lexer;

public class Keyword extends Token {
	public final String lexeme;
	
	public Keyword(String lexeme, int tag) {
		super(tag);
		this.lexeme = lexeme;
	}
	
	//conditional operators
	public static final Keyword AND = new Keyword("&&", Tag.AND),
			OR = new Keyword("||", Tag.OR);
			
	//equality and relational operators
	public static final Keyword LESS = new Keyword("<", Tag.LESS),
			LE = new Keyword("<=", Tag.LE),
			GREATER = new Keyword(">", Tag.GREATER),
			GE = new Keyword(">=", Tag.GE),
			ASSIGNMENT = new Keyword("=", Tag.ASSIGNMENT),
			EQUAL = new Keyword("==", Tag.EQUAL),
			NOTEQUAL = new Keyword("!=", Tag.NOTEQUAL);
	
	//conditional statements
	public static final Keyword IF = new Keyword("if", Tag.IF),
			ELSE = new Keyword("else", Tag.ELSE),
			ELSEIF = new Keyword("elseif", Tag.ELSEIF),
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
	
	//arithmetic operators
	public static final Keyword ADD = new Keyword("+", Tag.ADD),
			SUB = new Keyword("-", Tag.SUB),
			MUL = new Keyword("*", Tag.MUL),
			DIV = new Keyword("/", Tag.DIV),
			EXP = new Keyword("**", Tag.EXP),
			MOD = new Keyword("%", Tag.MOD);
	
	//unary operators
	public static final Keyword POS = new Keyword("+", Tag.POS),
			NEGATIVE = new Keyword("-", Tag.NEGATIVE),
			INC = new Keyword("++", Tag.INC),
			DEC = new Keyword("--", Tag.DEC),
			NEGATION = new Keyword("!", Tag.NEGATION);
	
	//others
	public static final Keyword NULL = new Keyword("null", Tag.NULL),
			VOID = new Keyword("void", Tag.VOID);

}
