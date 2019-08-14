package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class AddrAssign extends Sep3asmParseRule {
	public AddrAssign(Sep3asmParseContext ctx) {
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_IDENT;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		while (tk.getType() != Sep3asmToken.TK_NL) {
			tk = tknz.getNextToken(ctx);
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
