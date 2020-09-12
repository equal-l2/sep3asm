package lang.sep3asm;

import lang.SymbolTableEntry;

// 記号表へ登録するための一要素　（数値またはラベルを登録する）
public class LabelEntry extends SymbolTableEntry {
	public static final int E_INT = 0;		// 値で定義されている
	public static final int E_LAB = 1;		// ラベルで定義されている
	private int type;
	private Object obj;

	public void setInteger(Integer i)	{ obj = i; type = E_INT; }	// 値iで登録する
	public void setLabel(String s)		{ obj = s; type = E_LAB; }	// ラベルsで登録する
	public boolean isInteger()			{ return type == E_INT; }	// このエントリは値で登録されているか？
	public boolean isLabel()			{ return type == E_LAB; }	// このエントリはラベルで登録されているか？
	public Integer getInteger()			{ return (Integer) obj; }	// 値で登録されているなら、その値
	public String getLabel()			{ return (String) obj; }	// ラベルで登録されているなら、そのラベル

	// 表示用
	@Override
	public String toExplainString() {
		String s;
		if (isInteger()) {
			s = String.format("%1$04x", getInteger());
		} else if (isLabel()) {
			s = getLabel();
		} else {
			s = null;
		}
		return s;
	}
}
