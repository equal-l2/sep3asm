package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Comment extends Sep3asmParseRule {
	// comment ::= SEMI
	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_SEMI;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		System.err.println("COMMENT");
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		// no-op
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		// no-op
	}
}
