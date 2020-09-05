package lang.sep3asm.instruction;

import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.parse.Operand;

public class TwoOperandInstruction extends Sep3Instruction {
	public TwoOperandInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}
	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		ctx.output(opCode | op1.to5bits() << 5 | op2.to5bits());
		if (op1.needsExtraWord()) {
			ctx.output(op1.getExtraWord());
		}
	}
}
