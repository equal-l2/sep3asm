package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Line extends Sep3asmParseRule {
	// line ::= (labelLine | instLine | pseudoInstLine | comment) NL

	private Sep3asmParseRule syn;

	public static boolean isFirst(Sep3asmToken tk) {
		return LabelLine.isFirst(tk)
			|| InstLine.isFirst(tk)
			|| PseudoInstLine.isFirst(tk)
			|| Comment.isFirst(tk)
			|| tk.getType() == Sep3asmToken.TK_NL; // このとき syn は null
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		if (LabelLine.isFirst(tk)) {
			//System.out.println("Label");
			syn = new LabelLine();
		} else if (InstLine.isFirst(tk)) {
			//System.out.println("Inst");
			syn = new InstLine();
		} else if (PseudoInstLine.isFirst(tk)) {
			//System.out.println("P-Inst");
			syn = new PseudoInstLine();
		} else if (Comment.isFirst(tk)) {
			// TODO: is this really required? (tokenizer remove comments)
			//System.out.println("Comment");
			syn = new Comment();
		}
		if (syn != null) syn.parse(ctx);

		// 読まなかったものは捨てる
		tk = tknz.getCurrentToken(ctx);
		if (tk.getType() != Sep3asmToken.TK_NL) {
			if (tk.getType() != Sep3asmToken.TK_SEMI && !(syn instanceof Comment)) {
				ctx.warning(tk.toExplainString() + " : 行末にゴミがあります");
			}
			tknz.skipToNL(ctx);
		}
		tknz.getNextToken(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		if (syn != null) syn.pass1(ctx);
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (syn != null) syn.pass2(ctx);
	}
}
