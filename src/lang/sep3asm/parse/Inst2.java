package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst2 extends Sep3asmParseRule {
	// inst2 ::= INST2 operand COMMA operand
	private Sep3asmToken inst;
	private Operand op1, op2;
	Sep3Instruction sep3inst;

	public Inst2(Sep3asmParseContext ctx) {
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		inst = ct.getCurrentToken(ctx);
		Sep3asmToken tk = ct.getNextToken(ctx);
		if (Operand.isFirst(tk)) {
			op1 = new Operand(ctx);
			op1.parse(ctx);
			tk = ct.getCurrentToken(ctx);
			if (tk.getType() == Sep3asmToken.TK_COMMA) {
				tk = ct.getNextToken(ctx);
			} else {
				ctx.warning(tk.toExplainString() + ",が抜けていますので補いました");
			}
			if (Operand.isFirst(tk)) {
				op2 = new Operand(ctx);
				op2.parse(ctx);
			} else {
				ctx.warning(tk.toExplainString() + "オペランドが来ます");
			}
		} else {
			ctx.warning(tk.toExplainString() + "オペランドが来ます");
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		sep3inst = ctx.getTokenizer().getInstruction(inst.getText(), ctx);
		if (op1 != null) {
			op1.pass1(ctx);
			op1.limit(sep3inst.getOp1Info(), ctx, inst, "fromオペランドとして");
			if (op1.needExtraWord()) { ctx.addLocationCounter(2); }
			else					 { ctx.addLocationCounter(1); }
		}
		if (op2 != null) {
			op2.pass1(ctx);
			op2.limit(sep3inst.getOp2Info(), ctx, inst, "toオペランドとして");
		}
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (op1 != null) { op1.pass2(ctx); }
		if (op2 != null) { op2.pass2(ctx); }
		sep3inst.generate(ctx, op1, op2);
	}
}
