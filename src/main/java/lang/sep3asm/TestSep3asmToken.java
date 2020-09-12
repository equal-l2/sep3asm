package lang.sep3asm;

import lang.FatalErrorException;
import lang.IOContext;

public class TestSep3asmToken {
	private static class TestTokenizer extends Sep3asmParseRule {
//		program  ::= { token } EOF
		public TestTokenizer(Sep3asmParseContext pcx) {}
		public static boolean isFirst(Sep3asmToken tk) { return true; }

		public void parse(Sep3asmParseContext pctx) {
			Sep3asmToken tk = pctx.getTokenizer().getCurrentToken(pctx);
			while (tk.getType() != Sep3asmToken.TK_EOF) {
				if (tk.getType() == Sep3asmToken.TK_NUM) {
					pctx.getIOContext().getOutStream().println("Token=" + tk.toExplainString() + " value=" + tk.getIntValue());
				} else {
					pctx.getIOContext().getOutStream().println("Token=" + tk.toExplainString());
				}
				tk = pctx.getTokenizer().getNextToken(pctx);
			}
		}
		public void pass1(Sep3asmParseContext pctx) throws FatalErrorException {
		}
		public void pass2(Sep3asmParseContext pctx) throws FatalErrorException {
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

