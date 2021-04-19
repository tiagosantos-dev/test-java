package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;



public class AssertTest {

	
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(10, 10);
		//Margem de erro
		Assert.assertEquals(10.33, 10.333, 0.2);
		
		Usuario user1 = new Usuario("Tiago");
		Usuario user2 = new Usuario("Tiago");
		Usuario user3 = null;
		
		assertSame(user1, user1);
		assertNotSame(user1, user2);
		
		assertNull(user3);
		assertNotNull(user1);
		
		
		
	}
}
