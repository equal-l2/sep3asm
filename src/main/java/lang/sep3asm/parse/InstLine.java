package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;

public class InstLine extends Sep3asmParseRule {
    // instLine ::= inst0 | inst1 | inst2 | instJ

	private Sep3asmParseRule syn;

	public static boolean isFirst(Sep3asmToken tk) {
		return Inst0.isFirst(tk)
			|| Inst1.isFirst(tk)
			|| Inst2.isFirst(tk)
			|| InstJ.isFirst(tk);
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = pctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(pctx);
		if (Inst0.isFirst(tk)) {
			syn = new Inst0();
		} else if (Inst1.isFirst(tk)) {
			syn = new Inst1();
		} else if (Inst2.isFirst(tk)) {
			syn = new Inst2();
		} else if (InstJ.isFirst(tk)) {
			syn = new InstJ();
		}
		syn.parse(pctx);
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		syn.pass1(pctx);
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		syn.pass2(pctx);
	}
}
