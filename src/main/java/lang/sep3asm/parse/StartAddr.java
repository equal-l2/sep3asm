package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.*;

public class StartAddr extends Sep3asmParseRule {
	// startAddr ::= "." "=" numOrIdent

	private Sep3asmToken rhs;

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_DOT;
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = pctx.getTokenizer();
		Sep3asmToken tk = tknz.getNextToken(pctx);
		if (tk.getType() != Sep3asmToken.TK_EQUAL) {
			pctx.warning(tk.toExplainString() + " : .= の '=' が見つかりません");
		}

		tk = tknz.getNextToken(pctx);
		switch (tk.getType()) {
			case Sep3asmToken.TK_NUM:
			case Sep3asmToken.TK_IDENT:
				rhs = tk;
				break;
			default:
				pctx.warning(tk.toExplainString() + " : .= はこのトークンを解釈できません");
		}
		tknz.getNextToken(pctx);
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		if (rhs.getType() == Sep3asmToken.TK_NUM) {
			pctx.setLocationCounter(rhs.getIntValue());
		} else {
			// StartAddrのオペランドは現時点で値が決まっていなければならない
			// (命令配置の際のアドレスに関わるため)
			Sep3asmSymbolTable tbl = pctx.getSymbolTable();
			LabelEntry le = tbl.search(rhs.getText());
			if (le == null || le.isLabel()) {
				pctx.error(rhs.toExplainString() + " ラベルが解決できません");
			}
			pctx.setLocationCounter(le.getInteger());
		}
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		pass1(pctx);
	}
}
