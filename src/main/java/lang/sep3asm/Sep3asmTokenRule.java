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
		put("hlt",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x0000, 0, 0)));
		put("clr",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x1000, 0, to_ops)));
		put("asl",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x2000, 0, to_ops)));
		put("asr",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x2400, 0, to_ops)));
		put("lsl",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x3000, 0, to_ops)));
		put("lsr",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x3400, 0, to_ops)));
		put("rol",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x3800, 0, to_ops)));
		put("ror",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x3C00, 0, to_ops)));
		put("mov",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x4000, from_ops, to_ops)));
		put("jmp",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0x4407, from_ops|LABEL, 0)));
		put("ret",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x4AC7, 0, 0)));
		put("rit",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x4EC7, 0, 0)));
		put("add",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x5000, from_ops, to_ops)));
		put("rjp",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new RelativeJumpInstruction(0x5407, from_ops|LABEL, 0)));
		put("sub",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x6000, from_ops, to_ops)));
		put("cmp",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x6C00, from_ops, to_ops)));
		put("nop",   new TokenAssoc(Sep3asmToken.TK_INST0, new ZeroOperandInstruction(0x7000, 0, 0)));
		put("or",    new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x8000, from_ops, to_ops)));
		put("xor",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x8400, from_ops, to_ops)));
		put("and",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x8800, from_ops, to_ops)));
		put("bit",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x8C00, from_ops, to_ops)));
		put("jsr",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0xB01E, from_ops|LABEL, 0)));
		put("rjs",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new RelativeJumpInstruction(0xB41E, from_ops|LABEL, 0)));
		put("svc",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0xB81E, from_ops|LABEL, 0)));
		put("brn",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0xC007, from_ops|LABEL, 0)));
		put("brz",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0xC407, from_ops|LABEL, 0)));
		put("brv",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0xC807, from_ops|LABEL, 0)));
		put("brc",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new AbsoluteJumpInstruction(0xCC07, from_ops|LABEL, 0)));
		put("rbn",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new RelativeJumpInstruction(0xE007, from_ops|LABEL, 0)));
		put("rbz",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new RelativeJumpInstruction(0xE407, from_ops|LABEL, 0)));
		put("rbv",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new RelativeJumpInstruction(0xE807, from_ops|LABEL, 0)));
		put("rbc",   new TokenAssoc(Sep3asmToken.TK_INSTJ, new RelativeJumpInstruction(0xEC07, from_ops|LABEL, 0)));
		put(".word", new TokenAssoc(Sep3asmToken.TK_DOTWD, null));
		put("-",     new TokenAssoc(Sep3asmToken.TK_MINUS, null));
		put(",",     new TokenAssoc(Sep3asmToken.TK_COMMA, null));
		put("\n", new TokenAssoc(Sep3asmToken.TK_NL, null));
		put(".", new TokenAssoc(Sep3asmToken.TK_DOT, null));
		put("=", new TokenAssoc(Sep3asmToken.TK_EQUAL, null));
		put(":", new TokenAssoc(Sep3asmToken.TK_COLON, null));
		put("#", new TokenAssoc(Sep3asmToken.TK_SHARP, null));
		put("(", new TokenAssoc(Sep3asmToken.TK_PA_OP, null));
		put(")", new TokenAssoc(Sep3asmToken.TK_PA_CL, null));
		put("+", new TokenAssoc(Sep3asmToken.TK_PLUS, null));
		put(".blkw", new TokenAssoc(Sep3asmToken.TK_DOTBL, null));
		put(".end", new TokenAssoc(Sep3asmToken.TK_DOTED, null));
		put("r0",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(0)));
		put("r1",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(1)));
		put("r2",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(2)));
		put("r3",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(3)));
		put("r4",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(4)));
		put("r5",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(5)));
		put("r6",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(6)));
		put("r7",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(7)));
		put("psw", new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(5)));
		put("sp",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(6)));
		put("pc",  new TokenAssoc(Sep3asmToken.TK_REG, Integer.valueOf(7)));

		// additional insts
		put("mul",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x9000, D, D)));
		put("div",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x9400, D, D)));
		put("adc",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x5800, from_ops, to_ops)));
		put("sbc",   new TokenAssoc(Sep3asmToken.TK_INST2, new TwoOperandInstruction(0x6800, from_ops, to_ops)));
		put("rlc",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x2800, 0, to_ops)));
		put("rrc",   new TokenAssoc(Sep3asmToken.TK_INST1, new OneOperandInstruction(0x2C00, 0, to_ops)));
	}

	@Override
	public Object get(Object key) {
		if (key instanceof String) {
			key = ((String)key).toLowerCase();
		}
		return super.get(key);
	}
}
