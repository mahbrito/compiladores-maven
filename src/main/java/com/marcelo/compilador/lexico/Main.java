package com.marcelo.compilador.lexico;

import java.util.Scanner;
import javax.script.*;

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Insira a operação: ");
		String input = sc.nextLine();
		
		Lexico lexico = new Lexico(input);
		TabelaDeSimbolos.mostrar();
		Sintatico sintatico = new Sintatico();
		calcular();
		
	}
	public static void calcular() throws Exception{
		StringBuilder expressao = new StringBuilder("");
		int i = 0;
		int j = 0;
		int aux = 0;
		String auxCalc = "";
		while (i < TabelaDeSimbolos.VALOR.size()) {
			if (!TabelaDeSimbolos.VALOR.get(i + 1).equals("^")){
				expressao.append(TabelaDeSimbolos.VALOR.get(i));
				i++;
			}else{
				String antes = TabelaDeSimbolos.VALOR.get(i);
				String depois = TabelaDeSimbolos.VALOR.get(i + 2);
				if (antes.equals(")")){

					aux = i - 1;
					auxCalc = "(";
					while (!TabelaDeSimbolos.VALOR.get(aux).equals("("))
					{
						auxCalc += TabelaDeSimbolos.VALOR.get(aux);
						expressao.deleteCharAt(aux);
						aux = aux - 1;
					}
					expressao.deleteCharAt(aux);
					auxCalc += ")";
					expressao.append("Math.pow(" + auxCalc + "," + depois + ")");
					//System.out.println(auxCalc);
					i = i + 3;
				}else{
					expressao.append("Math.pow(" + antes + "," + depois + ")");
					i = i + 3;
				}
			}
		}
		System.out.println(expressao.toString());
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		Object obj = engine.eval(expressao.toString());
		System.out.println (obj);
	}
	
}