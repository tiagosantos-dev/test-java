package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;


//TESTE ORIENTADO A DADOS 
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	public LocacaoService service = null;
	public Usuario user = null;
	
	//PARAMETROS
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double total;
	
	@Parameter(value = 2)
	public String title;
	
	@Before
	public void initialization() {
		service = new LocacaoService();
		user = new Usuario("Tiago");
	}
	
	public  static Filme filme1 = new Filme("Deu a louca", 22,5.0);
	public  static Filme filme2 = new Filme("Até que alguma merda nos separe",2, 15.0);
	public  static Filme filme3 = new Filme("Get out", 1,10.0);
	public  static Filme filme4 = new Filme("Get out 2", 1,10.0);
	public  static Filme filme5 = new Filme("Get out 3", 1,10.0);
	public  static Filme filme6 = new Filme("Get out 3", 1,10.0);
	
	@Parameters(name = "Teste {2}")
	public static Collection<Object[]> getParametros(){
			return Arrays.asList(new Object[][] {
				{Arrays.asList(filme1, filme2), 20.00, "2 FILMES 0%"},
				{Arrays.asList(filme1, filme2, filme3), 27.5, "3 FILMES 25%"},
				{Arrays.asList(filme1, filme2, filme3,filme4 ), 32.5, "4 FILMES 50%"},
				{Arrays.asList(filme1, filme2, filme3,filme4, filme5 ), 35.0, "5 FILMES 75%"},
				{Arrays.asList(filme1, filme2, filme3, filme4,filme5, filme6 ), 35.0, "6 FILMES 100%"},
			
			});
		
	}
	
	@Test
	public void checkDiscount() throws Exception {
		
		Locacao locaccao = service.alugarFilme(user, filmes);
		
		Assert.assertThat(locaccao.getValor(), CoreMatchers.is( CoreMatchers.equalTo(total)));
	}

}
