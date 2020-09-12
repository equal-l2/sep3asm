package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst1 extends Sep3asmParseRule {
	// inst2 ::= INST2 operand COMMA operand
	private Sep3asmToken inst;
	private Operand op2;
	Sep3Instruction sep3inst;

	static public boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST1;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		inst = ct.getCurrentToken(ctx);
		Sep3asmToken tk = ct.getNextToken(ctx);
		if (Operand.isFirst(tk)) {
			op2 = new Operand();
			op2.parse(ctx);
			tk = ct.getCurrentToken(ctx);
		} else {
			ctx.warning(tk.toExplainString() + "オペランドが来ます");
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		sep3inst = inst.getInstruction();
		if (op2 != null) {
			op2.pass1(ctx);
			op2.limit(sep3inst.getOp2Info(), ctx, inst, true);
		}
		ctx.addLocationCounter(1);
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (op2 != null) { op2.pass2(ctx); }
		sep3inst.generate(ctx, null, op2);
	}
}
