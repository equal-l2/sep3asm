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
		final int from_ops = D|I|MI|IP|IMM;
		final int to_ops = D|I|IP;
		put("-",     new TokenAssoc(Sep3asmToken.TK_MINUS, null));
		put("hlt",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("clr",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("asl",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("asr",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("lsl",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("lsr",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("rol",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("ror",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x0000, 0, 0)));
		put("mov",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("jmp",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("ret",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("rit",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("add",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("rjp",   new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put("sub",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("cmp",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("nop",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("or",    new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("xor",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("and",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("bit",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("jsr",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("rjs",   new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put("svc",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("brn",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("brz",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("brv",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("brc",   new TokenAssoc(Sep3asmToken.TK_INST1, new AbsoluteJumpInstruction(0x4000, from_ops, to_ops)));
		put("rbn",   new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put("rbz",   new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put("rbv",   new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put("rbc",   new TokenAssoc(Sep3asmToken.TK_INST1, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put(".word", new TokenAssoc(Sep3asmToken.TK_DOTWD, null));
	}
}
