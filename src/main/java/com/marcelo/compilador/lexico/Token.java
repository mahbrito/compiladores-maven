package com.marcelo.compilador.lexico;

public class Token{
	//teria de ficar um pra operação e um pra número
	public static final int IDENT = 0;//acho que vai ter que mudar isso
	public static final int NUMERO = 1;
	
	private int tipo;
	private int val;

	
	public Token(int t) {

		this.tipo=tipo;
		this.val=val;
	}
	
	public Token() {
		super();
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "Token [tipo=" + tipo + ", val=" + val + "]";
	}

}
