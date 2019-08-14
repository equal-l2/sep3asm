package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class StartAddr extends Sep3asmParseRule {
	private Sep3asmToken rhs;

	public StartAddr(Sep3asmParseContext ctx) {
		rhs = null;
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_DOT;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getNextToken(ctx);
		if (tk.getType() != Sep3asmToken.TK_EQUAL) {
			ctx.warning("まあ許したるわ");
		}

		tk = tknz.getNextToken(ctx);
		switch (tk.getType()) {
			case Sep3asmToken.TK_NUM:
			case Sep3asmToken.TK_IDENT:
				rhs = tk;
				break;
			default:
				ctx.warning("ほげ〜");
		}
		tknz.getNextToken(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
