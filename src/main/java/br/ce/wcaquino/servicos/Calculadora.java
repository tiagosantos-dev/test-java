package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DividirPorZeroException;

public class Calculadora {

	
	public double somar(double valor, double valor2) {
		return valor+valor2;
	}
	
	public double diminuir(double valor, double valor2) {
		return valor-valor2;
	}
	
	public double dividir(double valor, double valor2) throws DividirPorZeroException {
		if(valor == 0 || valor2 == 0)
			throw new DividirPorZeroException("Imposivel");
		
		return valor/valor2;
	}
	
	public double multiplicar(double valor, double valor2) {
		return valor* valor2;
	}
}
