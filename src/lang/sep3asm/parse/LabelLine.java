package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class LabelLine extends Sep3asmParseRule {
	private enum Kind {
		NONE,
		ADDR_ASSIGN,
		NUM_ASSIGN,
		ALIAS_ASSIGN
	};
	private Kind kind;
	private Sep3asmToken name;
	private Sep3asmToken rhs;

	public LabelLine(Sep3asmParseContext ctx) {
		rhs = null;
		kind = Kind.NONE;
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_IDENT;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		name = tknz.getCurrentToken(ctx);
		Sep3asmToken tk = tknz.getNextToken(ctx);
		switch(tk.getType()) {
			case Sep3asmToken.TK_COLON:
				kind = Kind.ADDR_ASSIGN;
				break;
			case Sep3asmToken.TK_EQUAL:
				tk = tknz.getNextToken(ctx);
				switch (tk.getType()) {
					case Sep3asmToken.TK_NUM:
						kind = Kind.NUM_ASSIGN;
						break;
					case Sep3asmToken.TK_IDENT:
						kind = Kind.ALIAS_ASSIGN;
						break;
					default:
						// 右辺がない
						throw new FatalErrorException();
				}
				rhs = tk;
			default:
				ctx.warning("ラベル定義と推定します");
				kind = Kind.ADDR_ASSIGN;
		}

		// これより後ろは捨てる
		while (tk.getType() != Sep3asmToken.TK_NL && tk.getType() != Sep3asmToken.TK_EOF) {
			tk = tknz.getNextToken(ctx);
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
	}
	public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
	}
}
