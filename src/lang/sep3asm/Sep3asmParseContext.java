package lang.sep3asm;

import lang.*;

public class Sep3asmParseContext extends SimpleParseContext {
	Sep3asmSymbolTable symTbl;
	public boolean hasEnded;
	public Sep3asmParseContext(IOContext ioCtx,  Sep3asmTokenizer tknz) {
		super(ioCtx, tknz);
		symTbl = new Sep3asmSymbolTable();
		hasEnded = false;
	}

	@Override
	public Sep3asmTokenizer getTokenizer()		{ return (Sep3asmTokenizer) super.getTokenizer(); }

	public Sep3asmSymbolTable getSymbolTable()	{ return symTbl; }

	private int locationCounter = 0;
	public int getLocationCounter()			{ return locationCounter; }
	public void addLocationCounter(int n)	{ locationCounter += n; }
	public void setLocationCounter(int n)	{ locationCounter = n; }

	public void output(int data) {
		getIOContext().getOutStream().printf("%04x : %04x\n", 0xFFFF & locationCounter, 0xFFFF & data);
		addLocationCounter(1);
	}
}
