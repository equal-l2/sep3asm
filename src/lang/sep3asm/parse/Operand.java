package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Operand extends Sep3asmParseRule {
	// operand ::= REG | LPAR REG RPAR | LPAR REG RPAR [PLUS] | MINUS LPAR REG RPAR | SHARP numOrIdent | numOrIdent
	public static final int REGISTER	= 001;
	public static final int INDIRECT	= 002;
	public static final int PREDEC		= 004;
	public static final int POSTINC		= 010;
	public static final int IMM		= 020;
	public static final int LABEL		= 040;

	public Operand(Sep3asmParseContext ctx) {
	}
	public static boolean isFirst(Sep3asmToken tk) {
		return false;
	}
	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
	}

	private int fivebits;
	private boolean needExtraWord;
	private int extraWord;

	public boolean needExtraWord()	{ return needExtraWord; }
	public int to5bits()			{ return fivebits; }
	public int getExtraWord()		{ return extraWord; }

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void limit(int info, Sep3asmParseContext ctx, Sep3asmToken inst, final String s) {
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
	}
}
