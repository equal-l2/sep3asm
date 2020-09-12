package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst0 extends Sep3asmParseRule {
	// inst0 ::= INST0

	private Sep3asmToken inst;
	private Sep3Instruction sep3inst;

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST0;
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = pctx.getTokenizer();
		inst = tknz.getCurrentToken(pctx);
		tknz.getNextToken(pctx);
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		sep3inst = inst.getInstruction();
		pctx.addLocationCounter(1);
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		sep3inst.generate(pctx, null, null);
	}
}
