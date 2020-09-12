package lang.sep3asm;

import lang.sep3asm.instruction.Sep3Instruction;

public class TokenAssoc {
	private int type;
	private Object info;
	public int getType()						{ return type; }
	public Sep3Instruction getInstruction()		{ return (Sep3Instruction) info; }
	public int getRegisterNumber()				{ return (Integer) info; }
	public TokenAssoc(int type, Object info) {
		this.type = type;
		this.info = info;
	}
}
