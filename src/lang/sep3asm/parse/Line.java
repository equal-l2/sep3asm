package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Line extends Sep3asmParseRule {
	private Sep3asmParseRule syn;

	public Line(Sep3asmParseContext ctx) {
		syn = null;
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return LabelLine.isFirst(tk)
			|| InstLine.isFirst(tk)
			|| PseudoInstLine.isFirst(tk)
			|| Comment.isFirst(tk);
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {

	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
