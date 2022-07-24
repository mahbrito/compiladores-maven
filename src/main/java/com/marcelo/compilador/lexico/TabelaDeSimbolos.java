package com.marcelo.compilador.lexico;

import java.util.ArrayList;
import java.util.List;

public class TabelaDeSimbolos {
	
	public static final List<String> CHAVE = new ArrayList<>();
	public static final List<String> VALOR = new ArrayList<>();
	
	public static void inserir(String chave, String valor) {
		CHAVE.add(chave);
		VALOR.add(valor);
	}
	
	public static void mostrar() {
		for (int i=0; i<CHAVE.size(); i++) {
			System.out.println(CHAVE.get(i) + " : " + VALOR.get(i));
		}
	}
}