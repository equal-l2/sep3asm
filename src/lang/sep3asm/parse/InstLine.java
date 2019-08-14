package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class InstLine extends Sep3asmParseRule {
	private Sep3asmParseRule syn;

	public InstLine(Sep3asmParseContext ctx) {
		syn = null;
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return Inst0.isFirst(tk)
			|| Inst1.isFirst(tk)
			|| Inst2.isFirst(tk)
			|| InstJ.isFirst(tk);
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {

	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
