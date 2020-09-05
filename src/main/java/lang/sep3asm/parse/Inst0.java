package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import lang.sep3asm.instruction.Sep3Instruction;

public class Inst0 extends Sep3asmParseRule {
	private Sep3asmToken inst;
	Sep3Instruction sep3inst;

	public Inst0(Sep3asmParseContext ctx) {
	}

	static public boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_INST0;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		inst = tknz.getCurrentToken(ctx);
		tknz.getNextToken(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		sep3inst = inst.getInstruction();
		ctx.addLocationCounter(1);
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		sep3inst.generate(ctx, null, null);
	}
}
