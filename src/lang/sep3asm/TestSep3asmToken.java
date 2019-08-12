package lang.sep3asm;

import lang.*;

public class TestSep3asmToken {
	private static class TestTokenizer extends Sep3asmParseRule {
//		program  ::= { token } EOF
		public TestTokenizer(Sep3asmParseContext pcx) {}
		public static boolean isFirst(Sep3asmToken tk) { return true; }

		public void parse(Sep3asmParseContext ctx) {
			Sep3asmToken tk = ctx.getTokenizer().getCurrentToken(ctx);
			while (tk.getType() != Sep3asmToken.TK_EOF) {
				if (tk.getType() == Sep3asmToken.TK_NUM) {
					ctx.getIOContext().getOutStream().println("Token=" + tk.toExplainString() + " valule=" + tk.getIntValue());
				} else {
					ctx.getIOContext().getOutStream().println("Token=" + tk.toExplainString());
				}
				tk = ctx.getTokenizer().getNextToken(ctx);
			}
		}
		public void pass1(Sep3asmParseContext pcx) throws FatalErrorException {
		}
		public void pass2(Sep3asmParseContext pcx) throws FatalErrorException {
		}
	}

	public static void main(String[] args) {
		String inFile = args[0]; // 適切なファイルを絶対パスで与えること
		IOContext ioCtx = new IOContext(inFile, System.out, System.err);
		Sep3asmTokenizer tknz = new Sep3asmTokenizer(new Sep3asmTokenRule());
		Sep3asmParseContext pcx = new Sep3asmParseContext(ioCtx, tknz);
		try {
			Sep3asmTokenizer ct = pcx.getTokenizer();
			Sep3asmToken tk = ct.getNextToken(pcx);
			if (TestTokenizer.isFirst(tk)) {
				Sep3asmParseRule program = new TestTokenizer(pcx);
				program.parse(pcx);
//				program.pass1(pcx);
//				program.pass2(pcx);
			}
		} catch (FatalErrorException e) {
			e.printStackTrace();
		}
	}
}

