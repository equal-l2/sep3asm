package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;

public class PseudoInstLine extends Sep3asmParseRule {
	// pseudoInstLine ::= wordAlloc | blkAlloc | startAddr | ".END"

	private Sep3asmParseRule syn;

	public static boolean isFirst(Sep3asmToken tk) {
		return WordAlloc.isFirst(tk)
			|| BlkAlloc.isFirst(tk)
			|| StartAddr.isFirst(tk)
			|| tk.getType() == Sep3asmToken.TK_DOTED; // このとき syn は null
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = pctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(pctx);
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
			pctx.hasEnded = true;
			tknz.getNextToken(pctx); // .endを捨てる
		}
		if (syn != null) syn.parse(pctx);
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		if (syn == null) {
			pctx.hasEnded = true;
		} else {
			syn.pass1(pctx);
		}
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		if (syn == null) {
			pctx.hasEnded = true;
		} else {
			syn.pass2(pctx);
		}
	}
}
