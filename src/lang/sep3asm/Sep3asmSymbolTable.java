package lang.sep3asm;

import lang.SymbolTable;

public class Sep3asmSymbolTable extends SymbolTable<LabelEntry> {
	private static final long serialVersionUID = 2434424863156998459L;

	@Override
	public LabelEntry register(String name, LabelEntry e) {
		return put(name, e);
	}

	@Override
	public LabelEntry search(String name) {
		return get(name);
	}
}
