package lang.sep3asm.instruction;

import lang.sep3asm.*;
import lang.sep3asm.parse.Operand;

public class RelativeJumpInstruction extends Sep3Instruction {
	public RelativeJumpInstruction(int opCode, int from, int to) {
		super(opCode, from, to);
	}
	public void generate(Sep3asmParseContext ctx, Operand op1, Operand op2) {
		ctx.output(opCode | op1.to5bits() << 5);
		if (op1.needsExtraWord()) {
			int loc = ctx.getLocationCounter();
			if (op1.getType() == Sep3asmToken.TK_IDENT) {
				ctx.output(op1.getExtraWord() - loc - 1);
			} else {
				ctx.output(op1.getExtraWord());
			}
		}
	}
}
