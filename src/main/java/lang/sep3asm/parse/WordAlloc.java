package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;
import java.util.ArrayList;

public class WordAlloc extends Sep3asmParseRule {
	private java.util.ArrayList<Sep3asmToken> tks = new ArrayList<>();

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_DOTWD;
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
					ctx.warning(tk.toExplainString() + " : .WORDはこのトークンを解釈できません");
			}
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		for(Sep3asmToken tk : tks) {
			if (tk.getType() == Sep3asmToken.TK_IDENT) {
				ctx.getSymbolTable().register(tk.getText(), null);
			}
			ctx.addLocationCounter(1);
		}
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		int i = 0;
		for(Sep3asmToken tk : tks) {
			int data;
			if (tk.getType() == Sep3asmToken.TK_IDENT) {
				LabelEntry le = ctx.getSymbolTable().search(tk.getText());
				if (le == null || le.isLabel()) {
					ctx.error(tk.toExplainString() + " ラベルが解決できません");
					return;
				}
				data = le.getInteger();
			} else {
				data = tk.getIntValue();
			}
			ctx.output(data);
		}
	}
}
