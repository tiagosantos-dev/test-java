package br.ce.wcaquino.servicos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraTestMock {

	public Calculadora cal ;
	@Before
	public void initialization() {
		cal = Mockito.mock(Calculadora.class); 
				
	}
	
	@Test
	public void somar() {
		
		Mockito.when(cal.somar(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(5.0);
		
		System.out.println(cal.somar(30, 30));
	}
}
