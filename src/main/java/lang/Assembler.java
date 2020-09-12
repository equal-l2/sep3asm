package lang;


public interface Assembler<Pctx> {
	void pass1(Pctx pctx) throws FatalErrorException;
	void pass2(Pctx pctx) throws FatalErrorException;
}

