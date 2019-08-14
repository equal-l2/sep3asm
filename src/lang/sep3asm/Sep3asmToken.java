package lang.sep3asm;

import lang.SimpleToken;
import lang.sep3asm.instruction.Sep3Instruction;

public class Sep3asmToken extends SimpleToken {
	public static final int TK_DOTWD		= 2;	// .WORD
	public static final int TK_MINUS		= 3;	// -
	public static final int TK_COMMA		= 4;	// ,
	public static final int TK_NL			= 5;	// '\n'
	public static final int TK_INST0		= 6;	// 命令ニモニック（オペランドなし）
	public static final int TK_INST1		= 7;	// 命令ニモニック（オペランドfromにひとつ）
	public static final int TK_INSTJ		= 8;	// 命令ニモニック（オペランドtoにひとつ）
	public static final int TK_INST2		= 9;	// 命令ニモニック（オペランドふたつ）
	public static final int TK_DOT			= 10;	// .
	public static final int TK_EQUAL		= 11;	// =
	public static final int TK_COLON		= 12;	// :
	public static final int TK_SHARP		= 13;	// #
	public static final int TK_PA_OP		= 14;	// (
	public static final int TK_PA_CL		= 15;	// )
	public static final int TK_PLUS			= 16;	// +
	public static final int TK_DOTBL		= 17;	// .BLKW
	public static final int TK_DOTED		= 18;	// .END
	public static final int TK_REG			= 19;	// R*
	public static final int TK_SEMI			= 20;	// ;

	public Sep3asmToken(int type, SimpleToken child, TokenAssoc ta) {
		super(type, child.getLineNo(), child.getColumnNo(), child.getText());
		this.info = ta;
	}

	private TokenAssoc info;
	public Sep3Instruction getInstruction()		{ return info.getInstruction(); }
	public int getRegisterNumber()				{ return info.getRegisterNumber(); }
}
