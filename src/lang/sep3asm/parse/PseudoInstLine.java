package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class PseudoInstLine extends Sep3asmParseRule {
	private boolean isEnd;
	private Sep3asmParseRule syn;

	public PseudoInstLine(Sep3asmParseContext ctx) {
		isEnd = false;
		syn = null;
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return WordAlloc.isFirst(tk)
			|| BlkAlloc.isFirst(tk)
			|| StartAddr.isFirst(tk)
			|| tk.getType() == Sep3asmToken.TK_DOTED;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		if (WordAlloc.isFirst(tk)) {
			//System.out.println("WordAlloc");
			syn = new WordAlloc(ctx);
		} else if (BlkAlloc.isFirst(tk)) {
			//System.out.println("BlkAlloc");
			syn = new BlkAlloc(ctx);
		} else if (StartAddr.isFirst(tk)) {
			//System.out.println("StartAddr");
			syn = new StartAddr(ctx);
		} else if (tk.getType() == Sep3asmToken.TK_DOTED) {
			isEnd = true;
			tknz.getNextToken(ctx); // .endを捨てる
		}
		if (!isEnd) syn.parse(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
