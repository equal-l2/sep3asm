package lang.sep3asm;

import lang.SimpleToken;
import lang.sep3asm.instruction.Sep3Instruction;

public class Sep3asmToken extends SimpleToken {
	public static final int TK_DOTWD		= 2;				// .WORD
	public static final int TK_MINUS		= 3;				// -
	public static final int TK_COMMA		= 4;				// ,
	public static final int TK_NL			= 5;				// '\n'
	public static final int TK_INST0		= 6;				// 命令ニモニック（オペランドなし）
	public static final int TK_INST1		= 7;				// 命令ニモニック（オペランドfromにひとつ）
	public static final int TK_INST1a		= 8;				// 命令ニモニック（オペランドtoにひとつ）
	public static final int TK_INST2		= 9;				// 命令ニモニック（オペランドふたつ）

	public Sep3asmToken(int type, SimpleToken child, TokenAssoc ta) {
		super(type, child.getLineNo(), child.getColumnNo(), child.getText());
		this.info = ta;
	}

	private TokenAssoc info;
	public Sep3Instruction getInstruction()		{ return info.getInstruction(); }
	public int getRegisterNumber()				{ return info.getRegisterNumber(); }
}
