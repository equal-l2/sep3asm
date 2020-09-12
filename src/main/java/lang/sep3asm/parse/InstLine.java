package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class InstLine extends Sep3asmParseRule {
    // instLine ::= inst0 | inst1 | inst2 | instJ

	private Sep3asmParseRule syn;

	public static boolean isFirst(Sep3asmToken tk) {
		return Inst0.isFirst(tk)
			|| Inst1.isFirst(tk)
			|| Inst2.isFirst(tk)
			|| InstJ.isFirst(tk);
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		if (Inst0.isFirst(tk)) {
			syn = new Inst0();
		} else if (Inst1.isFirst(tk)) {
			syn = new Inst1();
		} else if (Inst2.isFirst(tk)) {
			syn = new Inst2();
		} else if (InstJ.isFirst(tk)) {
			syn = new InstJ();
		}
		syn.parse(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		syn.pass1(ctx);
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		syn.pass2(ctx);
	}
}
