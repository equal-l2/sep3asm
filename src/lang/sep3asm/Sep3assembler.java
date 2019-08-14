package lang.sep3asm;

import lang.*;
import lang.sep3asm.parse.*;

public class Sep3assembler {
	public static void main(String[] args) {
		String inFile = args[0]; // 適切なファイルを絶対パスで与えること
		IOContext ioCtx = new IOContext(inFile, System.out, System.err);
		Sep3asmTokenizer tknz = new Sep3asmTokenizer(new Sep3asmTokenRule());
		Sep3asmParseContext pcx = new Sep3asmParseContext(ioCtx, tknz);
		try {
			Sep3asmTokenizer ct = pcx.getTokenizer();
			Sep3asmToken tk = ct.getNextToken(pcx);
			if (Program.isFirst(tk)) {
				Sep3asmParseRule parseTree = new Program(pcx);
				parseTree.parse(pcx);				// 構文解析
				if (pcx.hasNoError()) parseTree.pass1(pcx);			// パス1：ラベルへの割り当て数値の決定
				pcx.getSymbolTable().show();
				if (pcx.hasNoError()) parseTree.pass2(pcx);			// パス2：機械語生成
				pcx.errorReport();
			} else {
				pcx.fatalError(tk.toExplainString() + "プログラムの先頭にゴミがあります");
			}
		} catch (FatalErrorException e) {
			e.printStackTrace();
		}
	}
}
