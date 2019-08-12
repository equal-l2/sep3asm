package lang.sep3asm;

import java.util.HashMap;

import lang.sep3asm.instruction.*;
import lang.sep3asm.parse.Operand;

public class Sep3asmTokenRule extends HashMap<String, Object> {
	private static final long serialVersionUID = 6928902799089728690L;
	private static final int D		= Operand.REGISTER;		// レジスタアドレシングを許す
	private static final int I		= Operand.INDIRECT;		// レジスタ間接アドレシングを許す
	private static final int MI		= Operand.PREDEC;		// プレデクリメント・レジスタ間接アドレシングを許す
	private static final int IP		= Operand.POSTINC;		// ポストインクリメント・レジスタ間接アドレシングを許す
	private static final int IMM	= Operand.IMM;			// 即値（イミディエイト）を許す
	private static final int LABEL	= Operand.LABEL;		// 解析時のみ、ラベルを＃なしに書いてある

	// toオペランドに -(R?)を使うのは禁止
	// fromオペランドに -(R?) を使うときは、R6,SPに限定される
	// toオペランドに (R7), (R7)+ を使うのは禁止

	public Sep3asmTokenRule() {
		put("-",  new TokenAssoc(Sep3asmToken.TK_MINUS, null));
		put("hlt", new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("mov", new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, D|I|MI|IP|IMM, D|I|IP)));
		put("rjs", new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, D|I|MI|IP|IMM|LABEL, 0)));
		put(".word", new TokenAssoc(Sep3asmToken.TK_DOTWD, null));
	}
}
