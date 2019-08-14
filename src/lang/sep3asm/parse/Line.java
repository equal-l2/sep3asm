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
			|| Comment.isFirst(tk)
			|| tk.getType() == Sep3asmToken.TK_NL;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		if (LabelLine.isFirst(tk)) {
			//System.out.println("Label");
			syn = new LabelLine(ctx);
		} else if (InstLine.isFirst(tk)) {
			//System.out.println("Inst");
			syn = new InstLine(ctx);
		} else if (PseudoInstLine.isFirst(tk)) {
			//System.out.println("P-Inst");
			syn = new PseudoInstLine(ctx);
		} else if (Comment.isFirst(tk)) {
			//System.out.println("Comment");
			syn = new Comment(ctx);
		}
		if (syn != null) syn.parse(ctx);

		// 読まなかったものは捨てる
		if (tknz.getCurrentToken(ctx).getType() != Sep3asmToken.TK_NL) {
			tknz.skipToNL(ctx);
		}
		tknz.getNextToken(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
