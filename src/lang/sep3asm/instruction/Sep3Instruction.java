package lang.sep3asm.instruction;

import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.parse.Operand;

public abstract class Sep3Instruction {
	int opCode, from, to;
	public Sep3Instruction(int opCode, int from, int to) {
		this.opCode = opCode; this.from = from; this.to = to;
	}
	public int getOp1Info() {
		return from;
	}
	public int getOp2Info() {
		return to;
	}
	public abstract void generate(Sep3asmParseContext ctx, Operand op1, Operand op2);
}
