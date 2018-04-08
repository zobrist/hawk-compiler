package lexer;

public class Tag {
	//conditional operators
	public final static int AND = 100;
	public final static int OR = 101;
	
	//equality and relational operators
	public final static int ASSIGNMENT = 102;
	public final static int EQUAL = 102;
	public final static int NOTEQUAL = 103;
	public final static int GREATER = 104;
	public final static int LESS = 105;
	public final static int GE = 106;
	public final static int LE = 107;
	
	//conditional statements
	public final static int IF = 107;
	public final static int ELSE = 108;
	public final static int ELSEIF = 109;
	public final static int INCASE = 110;
	public final static int IS = 111;
	
	//loop statements
	public final static int FOR = 112;
	public final static int DO = 113;
	public final static int WHILE = 114;
	public final static int REPEAT = 115;
	public final static int TIMES = 116;
	public final static int BREAK = 117;
	
	//boolean
	public final static int FALSE = 118;
	public final static int TRUE = 119;
	
	//data types
	public final static int NUM = 120;
	public final static int REAL = 121;
	public final static int BASIC_TYPE = 122;
	public final static int ARRAY_TYPE = 123;

	//arithmetic operators
	public final static int DIV = 124;
	public final static int MUL = 125;
	public final static int ADD = 126;
	public final static int SUB = 127;
	public final static int EXP = 128;
	public final static int MOD = 129;
	
	//unary operators
	public final static int POS = 130;
	public final static int NEGATIVE = 131;
	public final static int INC = 132;
	public final static int DEC = 133;
	public final static int NEGATION = 134;
	
	public final static int ID = 135;
	public final static int NULL = 136;
	public final static int VOID = 137;
	public final static int TEMP = 138;
}
