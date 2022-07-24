package com.marcelo.compilador.lexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sintatico {

	private String[][] M = new String[8][13];
	private Map<String, Integer> posicaoNaoTerminais = new HashMap<>();
	private Map<String, Integer> posicaoTerminais = new HashMap<>();
	private List<String> pilha = new ArrayList<>();
	private List<String> entrada = new ArrayList<>();
	private List<String> casamento = new ArrayList<>();
	private List<String> buffer = new ArrayList<>();

	public Sintatico() {

		for (int i = 0; i < TabelaDeSimbolos.CHAVE.size(); i++) {
			if (TabelaDeSimbolos.CHAVE.get(i).equals("id")) {
				entrada.add("id");
			} else {
				entrada.add(TabelaDeSimbolos.VALOR.get(i));
			}

		}
		entrada.add("$");
		posicaoNaoTerminais.put("E", 1);
		posicaoNaoTerminais.put("E'", 2);
		posicaoNaoTerminais.put("T", 3);
		posicaoNaoTerminais.put("T'", 4);
		posicaoNaoTerminais.put("P", 5);
		posicaoNaoTerminais.put("P'", 6);
		posicaoNaoTerminais.put("F", 7);

		posicaoTerminais.put("id", 1);
		posicaoTerminais.put("^", 2);
		posicaoTerminais.put("/", 3);
		posicaoTerminais.put("*", 4);
		posicaoTerminais.put("-", 5);
		posicaoTerminais.put("+", 6);
		posicaoTerminais.put("(", 7);
		posicaoTerminais.put(")", 8);
		posicaoTerminais.put("exp", 9);
		posicaoTerminais.put("[", 10);
		posicaoTerminais.put("]", 11);
		posicaoTerminais.put("$", 12);

		M[0][0] = "";
		M[0][1] = "id";
		M[0][2] = "^";
		M[0][3] = "/";
		M[0][4] = "*";
		M[0][5] = "-";
		M[0][6] = "+";
		M[0][7] = "(";
		M[0][8] = ")";
		M[0][9] = "exp";
		M[0][10] = "[";
		M[0][11] = "]";
		M[0][12] = "$";

		M[1][0] = "E";
		M[2][0] = "E'";
		M[3][0] = "T";
		M[4][0] = "T'";
		M[5][0] = "P";
		M[6][0] = "P'";
		M[7][0] = "F";

		M[1][1] = "E->TE'";
		M[1][7] = "E->TE'";
		M[1][9] = "E->TE'";

		M[2][2] = "E'->-TE'";
		M[2][3] = "E'->-TE'";
		M[2][6] = "E'->+TE'";
		M[2][8] = "E'->&";
		M[2][10] = "E'->&";

		M[3][1] = "T->PT'";
		M[3][7] = "T->PT'";
		M[3][9] = "T->PT'";

		M[4][2] = "T'->&";
		M[4][3] = "T'->/PT'";
		M[4][4] = "T'->*PT'";
		M[4][5] = "T'->&";
		M[4][6] = "T'->&";
		M[4][8] = "T'->&";
		M[4][10] = "T'->&";

		M[5][1] = "P->FP'";
		M[5][7] = "P->FP'";
		M[5][9] = "P->exp[F]";

		M[6][2] = "P'->^FP'";
		M[6][3] = "P'->&";
		M[6][4] = "P'->&";
		M[6][5] = "P'->&";
		M[6][6] = "P'->&";
		M[6][8] = "P'->&";
		M[6][10] = "P'->&";

		M[7][1] = "F->id";
		M[7][7] = "F->(E)";

		pilha.add("$");
		pilha.add("E");
		executar();

	}

	public void executar() {

		while (true) {

			try {

				System.out.println(pilha);

				if (pilha.size() == 1 && pilha.get(0).equals("$")) {
					break;
				}

				// COMPARA SE O ELEMENTO � IGUAL AO TERMINAL
				if (entrada.size() > 0) {
					if (pilha.get(1).equals(entrada.get(0))) {
						pilha.remove(1);
						entrada.remove(0);

					} else {
						// PEGA O ELEMENTO DA MATRIZ
						int linha = posicaoNaoTerminais.get(pilha.get(1));
						int coluna = posicaoTerminais.get(entrada.get(0));
						String regra = M[linha][coluna];

						// PEGA A POSICAO DO ->
						int posicaoPonteiro = regra.indexOf("->");

						// PEGA O ELEMENTO GERADOR. EX: E->PT' NESTE CASO ELE PEGA O E
						String gerador = regra.substring(0, posicaoPonteiro);

						// PEGA OS ELEMENTOS GERADOS. EX: E->PT' NESTE CASO ELE PEGA O PT'
						gerado(posicaoPonteiro, regra);

						if (buffer.get(0).equals("&")) {
							pilha.remove(1);
							buffer.clear();
						} else {
							// INSERE NA PILHA OS ELEMENTOS GERADOS
							int posicaoGerador = pilha.indexOf(gerador);
							inserirNaPilha(posicaoGerador);
						}
					}

				}
			} catch (Exception e) {
				System.out.println("Pilha: " + pilha);
				System.out.println("Entrada: " + entrada);
				System.out.println("Buffer: " + buffer);
			}

		}

	}

	private void inserirNaPilha(int posicao) {
		pilha.remove(posicao);

		if (buffer.get(0).equals("i") && buffer.get(1).equals("d")) {
			pilha.add(posicao, "id");
		} else {
			for (String st : buffer) {
				pilha.add(posicao, st);
				posicao += 1;
			}
		}
		buffer.clear();
	}

	private void gerado(int pos, String regra) {
		String gerado = regra.substring(pos + 2, regra.length());
		for (int i = 0; i < gerado.length(); i++) {

			if (String.valueOf(gerado.charAt(i)).equals("'")) {
				buffer.set(i - 1, buffer.get(i - 1) + "'");
			} else {
				buffer.add(String.valueOf(gerado.charAt(i)));
			}

		}
	}

}
	
	/*private int cont = 0;
	private String casamento = "";
	public Object [] pilha;
	public int posicaoPilha;


	
	public Sintatico() {
		E();
	}

	public void E() {
		//System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		T();
		E_();
	}
	
	public void E_(){
		System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		if(TabelaDeSimbolos.VALOR.get(cont).equals("+")) {
			cont++;
			E_();
		}else if(TabelaDeSimbolos.VALOR.get(cont).equals("-")) {
			cont++;
			E_();
		}else{
			System.out.println("Parou na função E_");
			return;
		}	
	}
	
	public void T() {
		//System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		P();
		T_();
	}
	
	public void T_() {
		//System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		if(TabelaDeSimbolos.VALOR.get(cont).equals("*")) {
			cont++;
			T_();
		}else if(TabelaDeSimbolos.VALOR.get(cont).equals("/")){
			T_();
		}else{
			System.out.println("Parou na função T_");
			return;
		}
				
	}

	public void P(){
		//System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		if(TabelaDeSimbolos.VALOR.get(cont).equals("exp")) {
			cont++;
			if(TabelaDeSimbolos.VALOR.get(cont).equals("[")) {
				cont++;
				F();
				if(TabelaDeSimbolos.VALOR.get(cont).equals("]")){
					cont++;
				}else{
					System.out.println(TabelaDeSimbolos.VALOR.get(cont));
					throw new RuntimeException("Deu ruim! O elemento " + TabelaDeSimbolos.VALOR.get(cont) + " esta em uma posição incorreta");
				}
			}else{
				System.out.println(TabelaDeSimbolos.VALOR.get(cont));
				throw new RuntimeException("Deu ruim! O elemento " + TabelaDeSimbolos.VALOR.get(cont) + " esta em uma posição incorreta");
			}
		}else{
			F();
			P_();
		}
		
	}

	public void P_(){
		//System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		if(TabelaDeSimbolos.VALOR.get(cont).equals("^")) {
			cont++;
			F();
		}else{
			System.out.println("Parou na função P_");
			return;
		}
	}
	
	public void F() {
		System.out.println("Valor da tabela: " + TabelaDeSimbolos.VALOR.get(cont));
		System.out.println("Valor do cont: " + cont);
		if(TabelaDeSimbolos.VALOR.get(cont).equals("(")) {
			cont++;
			E();
			
			if(TabelaDeSimbolos.VALOR.get(cont).equals(")")){
				cont++;
			}
		}else if(TabelaDeSimbolos.CHAVE.get(cont).equals("id")) {
			cont++;
		}else {
			System.out.println(TabelaDeSimbolos.VALOR.get(cont));
			throw new RuntimeException("Deu ruim! O elemento " + TabelaDeSimbolos.VALOR.get(cont) + " esta em uma posição incorreta");
		}
	}*/
