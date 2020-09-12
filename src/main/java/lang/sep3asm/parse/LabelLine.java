package lang.sep3asm.parse;

import lang.FatalErrorException;
import lang.sep3asm.*;

public class LabelLine extends Sep3asmParseRule {
	// labelLine ::= IDENT (COLON | EQUAL (NUM | TEXT))

	private Sep3asmToken name;
	private Sep3asmToken rhs;

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_IDENT;
	}

	public void parse(Sep3asmParseContext pctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = pctx.getTokenizer();
		name = tknz.getCurrentToken(pctx);
		Sep3asmToken tk = tknz.getNextToken(pctx);
		switch(tk.getType()) {
			case Sep3asmToken.TK_COLON:
				//System.out.println("LABEL DEF");
				tknz.getNextToken(pctx);
				break;
			case Sep3asmToken.TK_EQUAL:
				//System.out.println("LABEL ASSIGN");
				rhs = tknz.getNextToken(pctx);
				tknz.getNextToken(pctx);
				break;
			default:
				pctx.warning(tk.toExplainString() + " : ラベル定義と推定します");
		}
	}
	public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		LabelEntry le = new LabelEntry();
		if (rhs == null) {
			le.setInteger(pctx.getLocationCounter());
		} else if (rhs.getType() == Sep3asmToken.TK_NUM) {
			le.setInteger(rhs.getIntValue());
		} else {
			le.setLabel(rhs.getText());
		}
		pctx.getSymbolTable().register(name.getText(), le);
	}
	public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
		// no-op
	}
}
