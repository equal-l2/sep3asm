package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class LabelLine extends Sep3asmParseRule {
	// labelLine ::= IDENT (COLON | EQUAL (NUM | TEXT))

	private Sep3asmToken name;
	private Sep3asmToken rhs;

	public static boolean isFirst(Sep3asmToken tk) {
		return tk.getType() == Sep3asmToken.TK_IDENT;
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		name = tknz.getCurrentToken(ctx);
		Sep3asmToken tk = tknz.getNextToken(ctx);
		switch(tk.getType()) {
			case Sep3asmToken.TK_COLON:
				//System.out.println("LABEL DEF");
				tknz.getNextToken(ctx);
				break;
			case Sep3asmToken.TK_EQUAL:
				//System.out.println("LABEL ASSIGN");
				rhs = tknz.getNextToken(ctx);
				tknz.getNextToken(ctx);
				break;
			default:
				ctx.warning(tk.toExplainString() + " : ラベル定義と推定します");
		}
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		LabelEntry le = new LabelEntry();
		if (rhs == null) {
			le.setInteger(ctx.getLocationCounter());
		} else if (rhs.getType() == Sep3asmToken.TK_NUM) {
			le.setInteger(rhs.getIntValue());
		} else {
			le.setLabel(rhs.getText());
		}
		ctx.getSymbolTable().register(name.getText(), le);
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		// no-op
	}
}
