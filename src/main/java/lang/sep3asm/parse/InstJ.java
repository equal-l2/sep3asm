package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;
import lang.sep3asm.instruction.Sep3Instruction;

public class InstJ extends Sep3asmParseRule {
    // instJ ::= INSTJ operand

	private Sep3asmToken inst;
	private Operand op1;
	private Sep3Instruction sep3inst;

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INSTJ;
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer ct = pctx.getTokenizer();
		inst = ct.getCurrentToken(pctx);
		Sep3asmToken tk = ct.getNextToken(pctx);
		if (Operand.isFirst(tk)) {
			op1 = new Operand();
			op1.parse(pctx);
		} else {
			pctx.warning(tk.toExplainString() + "オペランドが来ます");
		}
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		sep3inst = inst.getInstruction();
		if (op1 != null) {
			op1.pass1(pctx);
			op1.limit(sep3inst.getOp1Info(), pctx, inst, false);
			if (op1.needsExtraWord()) {
				pctx.addLocationCounter(2);
			} else {
				pctx.addLocationCounter(1);
			}
		}
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		if (op1 != null) { op1.pass2(pctx); }
		sep3inst.generate(pctx, op1, null);
	}
}
