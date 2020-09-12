package lang.sep3asm.parse;

import lang.*;
import lang.sep3asm.*;

public class Operand extends Sep3asmParseRule {
	// operand ::= REG | LPAR REG RPAR | LPAR REG RPAR [PLUS] | MINUS LPAR REG RPAR | SHARP numOrIdent | numOrIdent

	public static final int REGISTER	= 001;
	public static final int INDIRECT	= 002;
	public static final int PREDEC		= 004;
	public static final int POSTINC		= 010;
	public static final int IMM		= 020;
	public static final int LABEL		= 040;

	private int type;
	private Sep3asmToken ref;

	public static boolean isFirst(Sep3asmToken tk) {
		switch (tk.getType()) {
			case Sep3asmToken.TK_NUM:
			case Sep3asmToken.TK_IDENT:
			case Sep3asmToken.TK_SHARP:
			case Sep3asmToken.TK_REG:
			case Sep3asmToken.TK_PA_OP:
			case Sep3asmToken.TK_MINUS:
				return true;
			default:
				return false;
		}
	}

	public void parse(Sep3asmParseContext ctx) throws FatalErrorException {
		Sep3asmTokenizer tknz = ctx.getTokenizer();
		Sep3asmToken tk = tknz.getCurrentToken(ctx);
		switch (tk.getType()) {
			case Sep3asmToken.TK_NUM:
			case Sep3asmToken.TK_IDENT:
				//System.out.printf("ラベル \"%s\"\n", tk.getText());
				type = LABEL;
				ref = tk;
				break;
			case Sep3asmToken.TK_SHARP:
				tk = tknz.getNextToken(ctx);
				//System.out.printf("即値 \"%s\"\n", tk.getText());
				switch (tk.getType()) {
					case Sep3asmToken.TK_NUM:
					case Sep3asmToken.TK_IDENT:
						type = IMM;
						ref = tk;
						break;
					default:
						ctx.error(tk.toExplainString() + " : 不正な即値です");
				}
				break;
			case Sep3asmToken.TK_REG:
				//System.out.printf("レジスタ \"%s\"\n", tk.getText());
				type = REGISTER;
				ref = tk;
				break;
			case Sep3asmToken.TK_PA_OP:
				tk = tknz.getNextToken(ctx);
				if (tk.getType() == Sep3asmToken.TK_REG) {
					ref = tk;
					tk = tknz.getNextToken(ctx); // ')' を捨てる
					tk = tknz.getNextToken(ctx);
					if (tk.getType() == Sep3asmToken.TK_PLUS) {
						//System.out.printf("ポスンク \"%s\"\n", ref.getText());
						type = POSTINC;
					} else {
						//System.out.printf("インダイ \"%s\"\n", ref.getText());
						type = INDIRECT;
						return;
					}
				}
				break;
			case Sep3asmToken.TK_MINUS:
				type = PREDEC;
				tk = tknz.getNextToken(ctx);
				if (tk.getType() == Sep3asmToken.TK_PA_OP) {
					tk = tknz.getNextToken(ctx);
					if (tk.getType() == Sep3asmToken.TK_REG) {
						ref = tk;
						//System.out.printf("プリデク \"%s\"\n", ref.getText());
						tknz.getNextToken(ctx); // ')'を捨てる
					} else {
						ctx.error(tk.toExplainString() + " : レジスタ指定が見つかりません");
					}
				} else {
					ctx.error(tk.toExplainString() + "予期しない'-'が見つかりました");
				}
				break;
			default:
				// impossible
		}
		tknz.getNextToken(ctx);
	}

	private int fivebits;
	private boolean needsExtraWord;
	private int extraWord;
	private int regNum = -1;

	public boolean needsExtraWord()	{ return needsExtraWord; }
	public int to5bits()			{ return fivebits; }
	public int getExtraWord()		{ return extraWord; }
	public int getType()			{ return ref.getType(); }

	public void pass1(Sep3asmParseContext ctx) throws FatalErrorException {
		if (ref.getType() == Sep3asmToken.TK_REG) {
			regNum = ref.getRegisterNumber();
		}
		if (type == IMM || type == LABEL) {
			needsExtraWord = true;
			regNum = 7;
		}
	}
	public void limit(int info, Sep3asmParseContext ctx, Sep3asmToken inst, boolean isTo) {
		if ((info & type) == 0) {
			ctx.error(ref.toExplainString() + " 使用できないアドレスモードです");
		}
		if (type == PREDEC && regNum != 6) {
			ctx.error(ref.toExplainString() + " プレデクリメント・レジスタ間接モードはR6(SP)にのみ使用できます");
		}
		if ((type == INDIRECT || type == POSTINC) && isTo && regNum == 7) {
			ctx.error(ref.toExplainString() + " R7(PC)は間接モードでToオペランドとして使用することはできません");
		}
	}
	public void pass2(Sep3asmParseContext ctx) throws FatalErrorException {
		if (needsExtraWord) {
			if (ref.getType() == Sep3asmToken.TK_IDENT) {
				LabelEntry le = ctx.getSymbolTable().search(ref.getText());
				if (le == null || le.isLabel()) {
					ctx.error(ref.toExplainString() + " ラベルが解決できません");
					return;
				}
				extraWord = le.getInteger();
			} else {
				extraWord = ref.getIntValue();
			}
		}

		fivebits = 0;
		switch (type) {
			case REGISTER:
				fivebits = 0;
				break;
			case INDIRECT:
				fivebits = 1;
				break;
			case PREDEC:
				fivebits = 2;
				break;
			case IMM:
			case LABEL:
			case POSTINC:
				fivebits = 3;
				break;
		}
		fivebits = (fivebits << 3) | regNum;
	}
}
