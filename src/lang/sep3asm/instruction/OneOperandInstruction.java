package lang.sep3asm.instruction;

import lang.sep3asm.*;
import lang.sep3asm.parse.Operand;

public class OneOperandInstruction extends Sep3Instruction {
	public OneOperandInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}
	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		ctx.output(opCode | op2.to5bits());
	}
}
