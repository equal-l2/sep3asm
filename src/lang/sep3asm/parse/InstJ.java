package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class InstJ extends Sep3asmParseRule {
	private Sep3asmToken inst;
	private Operand op1;
	Sep3Instruction sep3inst;

	public InstJ(Sep3asmParseContext ctx) {
	}

	static public boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INSTJ;
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
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (op1 != null) { op1.pass2(ctx); }
		sep3inst.generate(ctx, op1, null);
	}
}
