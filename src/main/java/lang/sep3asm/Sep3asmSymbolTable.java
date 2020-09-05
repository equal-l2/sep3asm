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
		LabelEntry le = get(name);
		if (le != null && le.isLabel()) {
			if (isCyclic(le)) {
				return null;
			} else {
				while(le != null && le.isLabel()) {
					le = nextEntry(le);
				}
			}
		}
		return le;
	}

	LabelEntry nextEntry(LabelEntry p) {
		if (p.isLabel()) {
			return get(p.getLabel());
		} else {
			return null;
		}
	}

	// https://codingfreak.blogspot.com/2012/09/detecting-loop-in-singly-linked-list_22.html
	boolean isCyclic(LabelEntry p){
		LabelEntry tortoise = p;
		LabelEntry hare = p;

		while (true) {
			if (hare == null) return false;
			hare = nextEntry(hare);
			if (hare == null) return false;
			hare = nextEntry(hare);
			tortoise = nextEntry(tortoise);
			if (hare == tortoise) return true;
		}
	}
}
