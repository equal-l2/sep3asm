package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import java.util.ArrayList;

public class BlkAlloc extends Sep3asmParseRule {
	private ArrayList<Sep3asmToken> tks;

	public BlkAlloc(Sep3asmParseContext ctx) {
		tks = new ArrayList<>();
	}

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_DOTBL;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		while (tk.getType() != Sep3asmToken.TK_NL || tk.getType() != Sep3asmToken.TK_EOF) {
			tk = tknz.getNextToken(ctx);
			switch (tk.getType()) {
				case Sep3asmToken.TK_COMMA:
					// no-op
					break;
				case Sep3asmToken.TK_NUM:
				case Sep3asmToken.TK_IDENT:
					tks.add(tk);
					break;
				case Sep3asmToken.TK_NL:
				case Sep3asmToken.TK_EOF:
					return;
				default:
					ctx.warning(tk.toExplainString() + " : .BLKWはこのトークンを解釈できません");
			}
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		for(Sep3asmToken tk : tks) {
			if (tk.getType() == Sep3asmToken.TK_NUM) {
				ctx.addLocationCounter(tk.getIntValue());
			} else {
				// BlkAllocのオペランドは現時点で値が決まっていなければならない
				// (命令配置の際のアドレスに関わるため)
				Sep3asmSymbolTable tbl = ctx.getSymbolTable();
				LabelEntry le = tbl.search(tk.getText());
				if (le == null || le.isLabel()) {
					ctx.error(tk.toExplainString() + " ラベルが解決できません");
					return;
				}
				ctx.addLocationCounter(le.getInteger());
			}
		}
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		pass1(ctx);
	}
}
