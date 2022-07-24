package com.marcelo.compilador.lexico;

import java.util.Scanner;
import java.util.function.IntSupplier;
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
		
		int cont = 0;
		String expressao = "";
		String aux = "";

		for(int i = 0; i < TabelaDeSimbolos.VALOR.size(); i++){

			System.out.println(TabelaDeSimbolos.VALOR.get(i));

			if(i == TabelaDeSimbolos.VALOR.size() - 1){
				continue;
			}
			if(i == 0 && !TabelaDeSimbolos.VALOR.get(i + 1).equals("^")){
				expressao += TabelaDeSimbolos.VALOR.get(i);
				continue;
			}
			
			if(TabelaDeSimbolos.VALOR.get(i + 1).equals("^")){
				continue;
			}

			if(TabelaDeSimbolos.VALOR.get(i - 1).equals("^")){
				continue;
			}

			if(TabelaDeSimbolos.VALOR.get(i).equals("^")){
				String antes = TabelaDeSimbolos.VALOR.get(i - 1);
				String depois = TabelaDeSimbolos.VALOR.get(i + 1);
				//expressao += " Math.pow("+antes+","+depois+")";
				aux = "Math.pow("+antes+","+depois+")";
				expressao += aux;
				continue;
			}
			System.out.println(expressao);
			
		}

			
		// create a script engine manager
		ScriptEngineManager factory = new ScriptEngineManager();
		// create a JavaScript engine
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		// evaluate Ja/vaScript code from String
		//Object obj = engine.eval(expressao);
		Object test = engine.eval(aux);
		//System.out.println (obj); 
		//System.out.println (obj.getClass()); 
		System.out.println(test);
		
	}
	
}