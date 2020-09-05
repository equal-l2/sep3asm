package lang.sep3asm.parse;

import java.util.ArrayList;

import lang.*;
import lang.sep3asm.*;

public class Program extends Sep3asmParseRule {
	// program ::= { line }  EOF
	private ArrayList<Sep3asmParseRule> list;
	public Program(Sep3asmParseContext ctx) {
		list = new ArrayList<Sep3asmParseRule>();
	}
	public static boolean isFirst(Sep3asmToken tk) {
		return Line.isFirst(tk) || tk.getType() == Sep3asmToken.TK_EOF;
	}
	@Override
	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer ct = ctx.getTokenizer();
		Sep3asmToken tk = ct.getCurrentToken(ctx);
		while (Line.isFirst(tk)) {
			Sep3asmParseRule line = new Line(ctx);
			line.parse(ctx);
			list.add(line);
			if (ctx.hasEnded) break;
			tk = ct.getCurrentToken(ctx);
		}
		if (!ctx.hasEnded && tk.getType() != Sep3asmToken.TK_EOF) {
			ctx.warning(tk.toExplainString() + "ファイルの終わりにゴミがあります");
		}
		ctx.hasEnded = false;
	}
	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		for(Sep3asmParseRule pr : list) {
			pr.pass1(ctx);
			if (ctx.hasEnded) break;
		}
		ctx.hasEnded = false;
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		for(Sep3asmParseRule pr : list) {
			pr.pass2(ctx);
			if (ctx.hasEnded) break;
		}
		ctx.hasEnded = false;
	}
}
