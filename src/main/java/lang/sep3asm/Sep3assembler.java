package lang.sep3asm;

import lang.*;
import lang.sep3asm.parse.*;
import java.io.*;
import java.nio.file.*;

public class Sep3assembler {
	public static void main(String[] args) {
		String inFile = args[0]; // 適切なファイルを絶対パスで与えること
		String outFile = "out.bin";
		if (args.length > 1) {
			outFile = args[1];
		}

		try (
			OutputStream os = Files.newOutputStream(Paths.get(outFile));
			PrintStream ps = new PrintStream(os)
		) {
			IOContext ioCtx = new IOContext(inFile, ps, System.err);
			Sep3asmTokenizer tknz = new Sep3asmTokenizer(new Sep3asmTokenRule());
			Sep3asmParseContext pcx = new Sep3asmParseContext(ioCtx, tknz);
			try {
				Sep3asmTokenizer ct = pcx.getTokenizer();
				Sep3asmToken tk = ct.getNextToken(pcx);
				if (Program.isFirst(tk)) {
					Sep3asmParseRule parseTree = new Program();

					System.out.println("parse");
					pcx.setLocationCounter(0);
					parseTree.parse(pcx);				// 構文解析

					System.out.println("pass1");
					pcx.setLocationCounter(0);
					if (pcx.hasNoError()) parseTree.pass1(pcx);			// パス1：ラベルへの割り当て数値の決定
					pcx.getSymbolTable().show();

					System.out.println("pass2");
					pcx.setLocationCounter(0);
					if (pcx.hasNoError()) parseTree.pass2(pcx);			// パス2：機械語生成

					pcx.errorReport();
				} else {
					pcx.fatalError(tk.toExplainString() + "プログラムの先頭にゴミがあります");
				}
			} catch (FatalErrorException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println(outFile + " : 出力ファイルを開けません");
			e.printStackTrace();
		}
	}
}
