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
			ctx.warning(tknz.toExplainString() + " : .= の '=' が見つかりません");
		}

		tk = tknz.getNextToken(ctx);
		switch (tk.getType()) {
			case Sep3asmToken.TK_NUM:
			case Sep3asmToken.TK_IDENT:
				rhs = tk;
				break;
			default:
				ctx.warning(tknz.toExplainString() + " : .= はこのトークンを解釈できません");
		}
		tknz.getNextToken(ctx);
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		if (rhs.getType() == Sep3asmToken.TK_NUM) {
			ctx.setLocationCounter(rhs.getIntValue());
		} else {
			// StartAddrのオペランドは現時点で値が決まっていなければならない
			// (命令配置の際のアドレスに関わるため)
			Sep3asmSymbolTable tbl = ctx.getSymbolTable();
			LabelEntry le = tbl.search(rhs.getText());
			if (le == null || le.isLabel()) {
				ctx.error(rhs.toExplainString() + " ラベルが解決できません");
			}
			ctx.setLocationCounter(le.getInteger());
		}
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		pass1(ctx);
	}
}
