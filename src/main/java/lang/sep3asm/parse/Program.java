package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.Sep3asmParseContext;
import lang.sep3asm.Sep3asmParseRule;
import lang.sep3asm.Sep3asmToken;
import lang.sep3asm.Sep3asmTokenizer;

import java.util.ArrayList;

public class Program extends Sep3asmParseRule {
	// program ::= { line } EOF

	private ArrayList<Sep3asmParseRule> list = new ArrayList<>();
	public static boolean isFirst(Sep3asmToken tk) {
		return Line.isFirst(tk) || tk.getType() == Sep3asmToken.TK_EOF;
	}
	@Override
	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer ct = pctx.getTokenizer();
		Sep3asmToken tk = ct.getCurrentToken(pctx);
		while (Line.isFirst(tk)) {
			Sep3asmParseRule line = new Line();
			line.parse(pctx);
			list.add(line);
			if (pctx.hasEnded) break;
			tk = ct.getCurrentToken(pctx);
		}
		if (!pctx.hasEnded && tk.getType() != Sep3asmToken.TK_EOF) {
			pctx.warning(tk.toExplainString() + "ファイルの終わりにゴミがあります");
		}
		pctx.hasEnded = false;
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		for(Sep3asmParseRule pr : list) {
			pr.pass1(pctx);
			if (pctx.hasEnded) break;
		}
		pctx.hasEnded = false;
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		for(Sep3asmParseRule pr : list) {
			pr.pass2(pctx);
			if (pctx.hasEnded) break;
		}
		pctx.hasEnded = false;
	}
}
