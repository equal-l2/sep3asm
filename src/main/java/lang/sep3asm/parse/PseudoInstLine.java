package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class PseudoInstLine extends Sep3asmParseRule {
	// pseudoInstLine ::= wordAlloc | blkAlloc | startAddr | ".END"

	private Sep3asmParseRule syn;

	public static boolean isFirst(Sep3asmToken tk) {
		return WordAlloc.isFirst(tk)
			|| BlkAlloc.isFirst(tk)
			|| StartAddr.isFirst(tk)
			|| tk.getType() == Sep3asmToken.TK_DOTED; // このとき syn は null
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		if (WordAlloc.isFirst(tk)) {
			//System.out.println("WordAlloc");
			syn = new WordAlloc();
		} else if (BlkAlloc.isFirst(tk)) {
			//System.out.println("BlkAlloc");
			syn = new BlkAlloc();
		} else if (StartAddr.isFirst(tk)) {
			//System.out.println("StartAddr");
			syn = new StartAddr();
		} else if (tk.getType() == Sep3asmToken.TK_DOTED) {
			ctx.hasEnded = true;
			tknz.getNextToken(ctx); // .endを捨てる
		}
		if (syn != null) syn.parse(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		if (syn == null) {
			ctx.hasEnded = true;
		} else {
			syn.pass1(ctx);
		}
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (syn == null) {
			ctx.hasEnded = true;
		} else {
			syn.pass2(ctx);
		}
	}
}
