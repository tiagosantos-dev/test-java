package br.ce.wcaquino.servicos;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.DividirPorZeroException;



public class CalculadoraTest {
	
	Calculadora calc = null;
	
	@Before
	public void init() {
		 calc = new Calculadora();
		 
	}
	
	@Test
	public void somar() {
		
		double result  = calc.somar(10.0, 10.0);
		Assert.assertEquals(result, 20.0,0.1);
	}
	
	@Test
	public void diminuir() {
		
		double result  = calc.diminuir(10.0, 10.0);
		Assert.assertEquals(result, 0.0,0.1);
	}
	
	@Test
	public void dividir() throws DividirPorZeroException {
		
		double result  = calc.dividir(10.0, 10.0);
		Assert.assertEquals(result, 1.0,0.1);
	}
	
	@Test
	public void multiplicar() {
		
		double result  = calc.multiplicar(10.0, 10.0);
		Assert.assertEquals(result, 100.0,0.1);
	}
}
