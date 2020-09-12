package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;

public class Comment extends Sep3asmParseRule {
	// comment ::= SEMI
	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_SEMI;
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		//System.err.println("COMMENT");
		Sep3asmTokenizer tknz = pctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(pctx);
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		// no-op
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		// no-op
	}
}
