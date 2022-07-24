package com.marcelo.compilador.lexico;
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class Lexico {
	
	private String[] arrayNumeros = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private String[] arrayOperacoes = { "+", "-", "*", "/", "^",};
	private String[] arraySimbolos = { "(", ")", ".", "[", "]" };
	private String[] arrayExp = {"exp"};

	private List<String> bufferNumeros = new ArrayList<>();
	private List<String> bufferExp = new ArrayList<>();
	
	public Lexico(String input) {
		executar(input);
	}

	public String[] getArrayNumeros() {
		return arrayNumeros;
	}

	public String[] getArrayOperacoes() {
		return arrayOperacoes;
	}

	public String[] getArraySimbolos() {
		return arraySimbolos;
	}

	public String[] getArrayExp() {
		return arrayExp;
	}

	public List<String> getBufferNumeros() {
		return bufferNumeros;
	}

	public List<String> getBufferExp() {
		return bufferExp;
	}

	private boolean verificaSeExiste(String elemento, String[] array) {

		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(elemento)) {
				return true;
			}
		}
		return false;
	}
	
	private int verificaNumeroDeVezesRepetido(String elemento, List<String> list) {
		int cont = 0;
		for (String e : list) {
			if (e.equals(elemento)) {
				cont += 1;
			}
		}
		return cont;
	}
	
	
	private void verificaBufferNumero() {
		int cont = 0;
		if (bufferNumeros.size() > 0) {
			cont = verificaNumeroDeVezesRepetido(".", bufferNumeros);
			if (cont > 1) {
				throw new RuntimeException("O elemento " + bufferNumeros.toString() + " é invalido.");
			}
			String numero = "";
			for (String e : bufferNumeros) {
				numero += e;
			}
			TabelaDeSimbolos.inserir("id", numero);
			bufferNumeros.clear();
		}
	}
	
	private void verificaBufferExp() {
		if (bufferExp.size() > 0) {
			String elemento = "";
			for (String e : bufferExp) {
				elemento += e;
			}
			if (!elemento.equals("exp")) {
				throw new RuntimeException("O elemento " + elemento + " não existe na linguaem do compilador.");
			}
			TabelaDeSimbolos.inserir("op", "exp");
			bufferExp.clear();
		}
	}

	public void executar(String input) {
		
		for (int i=0; i<input.length(); i++) {
			String elemento = String.valueOf(input.charAt(i));
			
			if ((!verificaSeExiste(elemento, arrayNumeros)) && (!verificaSeExiste(elemento, arrayOperacoes)) && 
					(!verificaSeExiste(elemento, arraySimbolos)) && (!verificaSeExiste(elemento, arrayExp)) ){
				throw new RuntimeException("Elemento " + elemento + " não existe na linguagem do compilador.");
			}
			
			if (verificaSeExiste(elemento, arrayExp)) {
				verificaBufferNumero();
				bufferExp.add(elemento);
			}
			
			if (verificaSeExiste(elemento, arrayNumeros)) {
				bufferNumeros.add(elemento);
			}
			
			if (verificaSeExiste(elemento, arraySimbolos)) {
				if (elemento.equals(".")) {
					bufferNumeros.add(elemento);
				}
				else {
					verificaBufferNumero();
					TabelaDeSimbolos.inserir("si", elemento);
				}
			}
			
			if (verificaSeExiste(elemento, arrayOperacoes)) {
				verificaBufferNumero();
				TabelaDeSimbolos.inserir("op", elemento);
			}
			
			if (bufferExp.size() == 3) {
				verificaBufferExp();
			}
			
		}
		
		verificaBufferNumero();
		
	}

}
	
	
	
	
/*	private char conteudo[];
	private int estado;
	private int pos;

	
	public Lexico(String arq) {
		conteudo =arq.toCharArray();
		
	}
	
	public Token proximoToken () {
		
		if(isEOF()) {
		return null;
		}
		
		estado = 0;
		char c;
		Token token = null;
		String operacao = "";
		while(true) {
			c = proximoCaracter();
			switch(estado) {
				case 0:
					if(isDigito(c)) {
						operacao += c;
						estado = 1;
					}else if(isLetra(c)) {
						estado = 0;
					}else if(isEspaco(c)) {
						estado=0;
					}else if(conteudo[c] == 'E') {
						operacao += c;
						estado = 6;
					}else if (conteudo[c] == '(') {
						operacao += c;
						estado = 5;
					}
					else {
						throw new RuntimeException("Token não reconhecido!");
					}
					break;
					
				case 1://FALTA ESSA MERDA AQUI
					
					
				case 2:
					if(isDigito(c)) {
						operacao += c;
						estado = 3;
					}
					break;
				
				case 3:
					if(isEOF() || (conteudo[c] == ')')) {
						operacao += c;
						estado = 4;
					}
					break;
					
				case 4:
					//como fica nesse caso, já que ele é o caso final e tbm pode ir pro estado q2?
					token = new Token();
					token.setTipo(Token.NUMERO);
					token.setVal(c);//ver se isso ta certo
					//estado = 2;
					voltar();
					return token;//não teria de ir pro estado onde ficam as operações?
					
					
				case 5:
					if(isDigito(c)) {
						estado = 1;
					}
					break;
					
				case 6:
					if(conteudo[c] == 'X') {
						estado = 7;
					}
					break;
					
				case 7:
					if(conteudo[c] == 'P') {
						estado = 8;
					}
					break;
					
				case 8:
					if(conteudo[c] == '[') {
						estado = 9;
					}
					break;

				case 9:
					if(isDigito(c)) {
						estado = 10;
					}
					break;
				
				case 10:
					if (conteudo[c] == ']') {
						estado = 11;
					}
					break;
					
				case 11: 
					if(isEOF()) {
						estado = 4;
					}
					break;
			}
		}						
	}
	
	
	private boolean isDigito (char c) {
		return c>='0' && c<='9';
	}
	
	private boolean isLetra (char c) {
		return ((c>= 'a' && c<= 'z') || (c>='A' && c<='Z'));
	}
	
	private boolean isEspaco (char c) {
		return (c == ' ' || c =='\n' || c == '\t' );
	}
	
	private boolean isEOF () {
		return pos == conteudo.length;
	}
	
	private char proximoCaracter() {
		if(isEOF()) {
			return 0;
		}
		return conteudo[pos++];
	}
	
	private void voltar() {
		if(!isEOF()) {
			pos--;
		}
		
	}
}*/
