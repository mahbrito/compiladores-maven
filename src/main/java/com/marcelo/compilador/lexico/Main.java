package com.marcelo.compilador.lexico;

import java.util.Scanner;
import java.util.function.IntSupplier;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Insira a operação: ");
		String input = sc.nextLine();
		
		Lexico lexico = new Lexico(input);
		TabelaDeSimbolos.mostrar();
		Sintatico sintatico = new Sintatico();
		
		//verificar se é uma potenciação para aplicar a biblioteca Math
			//temos que pergar o número depois do ^
		//verificar se é um exp para aplicar a biblioteca Math
		
		int num = 0;
		for (int i = 0; i < input.length(); i++){
			if(input == "^"){
				num = input.charAt(i + 1);//eu quero pegar a posição + 1
				System.out.println("O resultado da expressão é: " + Math.pow(input.charAt(i), num));
			}
		}
		
	}
}