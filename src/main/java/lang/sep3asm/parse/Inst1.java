package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst1 extends Sep3asmParseRule {
	// inst2 ::= INST2 operand COMMA operand
	private Sep3asmToken inst;
	private Operand op2;
	private Sep3Instruction sep3inst;

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST1;
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer ct = pctx.getTokenizer();
		inst = ct.getCurrentToken(pctx);
		Sep3asmToken tk = ct.getNextToken(pctx);
		if (Operand.isFirst(tk)) {
			op2 = new Operand();
			op2.parse(pctx);
		} else {
			pctx.warning(tk.toExplainString() + "オペランドが来ます");
		}
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		sep3inst = inst.getInstruction();
		if (op2 != null) {
			op2.pass1(pctx);
			op2.limit(sep3inst.getOp2Info(), pctx, inst, true);
		}
		pctx.addLocationCounter(1);
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		if (op2 != null) { op2.pass2(pctx); }
		sep3inst.generate(pctx, null, op2);
	}
}
