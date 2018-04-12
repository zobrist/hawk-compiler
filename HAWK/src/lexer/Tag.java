package lexer;

public class Tag {
	//conditional operators
	public final static int AND = 300;
	public final static int OR = 301;
	
	//equality and relational operators
	public final static int ASSIGNMENT = 302;
	public final static int EQUAL = 303;
	public final static int NOTEQUAL = 304;
	public final static int GREATER = 305;
	public final static int LESS = 306;
	public final static int GE = 307;
	public final static int LE = 308;
	
	//conditional statements
	public final static int IF = 309;
	public final static int ELSE = 310;
	public final static int ELSIF = 311;
	public final static int INCASE = 312;
	public final static int IS = 313;
	
	//loop statements
	public final static int FOR = 314;
	public final static int DO = 315;
	public final static int WHILE = 316;
	public final static int REPEAT = 317;
	public final static int TIMES = 318;
	public final static int BREAK = 319;
	
	//boolean
	public final static int FALSE = 400;
	public final static int TRUE = 401;
	
	//data types
	public final static int NUM = 402;
	public final static int REAL = 403;
	public final static int BASIC_TYPE = 404;
	public final static int ARRAY_TYPE = 405;
	public final static int STRING_TYPE = 406;

	//arithmetic operators
	public final static int DIV = 407;
	public final static int MUL = 408;
	public final static int ADD = 409;
	public final static int SUB = 410;
	public final static int EXP = 411;
	public final static int MOD = 412;
	
	//unary operators
	public final static int POS = 413;
	public final static int NEGATIVE = 414;
	public final static int INC = 415;
	public final static int DEC = 416;
	public final static int NEGATION = 417;
	
	public final static int ID = 418;
	public final static int NIL = 419;
	public final static int VOID = 420;
	public final static int PROGRAM = 421;
	public final static int FUNC = 422;
	public final static int THEN = 423;
	public final static int DEFAULT = 424;
	public final static int RETURN = 425;
	public final static int EXIT = 426;
}
