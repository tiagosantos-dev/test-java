package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FIlmeNullException;
import br.ce.wcaquino.exceptions.SemEstoqueException;
import br.ce.wcaquino.exceptions.UsuarioNegativacaoException;
import br.ce.wcaquino.exceptions.UsuarioNullException;
import br.ce.wcaquino.utils.DataUtils;
import matches.MyMatcher;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocacaoService.class)
public class LocacaoServiceTest_powerMock {
	
	
	
	@Rule
	
	public ErrorCollector error = new ErrorCollector();
	
	//DECLARACAO
	@InjectMocks()
	public LocacaoService service;
	
	@Mock
	public Usuario user;
	
	@Mock
	public LocacaoDAO dao;
	
	@Mock
	public SPCService spcService;
	
	@Mock
	public EmailService emailService;
	
	
	@Rule
	public ExpectedException expException = ExpectedException.none();
	
	@Before
	public void before() {
		//INICIALIZACAO
		
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void teste() throws Exception {
		
		//CENARIO
	
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 22,222.0));
		
		//ACAO
		
		Locacao  result = service.alugarFilme(user, filmes);
		
		//VERIFICACAO
		Assert.assertTrue(result.getFilmes().equals(filmes));
		Assert.assertTrue(result.getUsuario().equals(user));
		Assert.assertTrue(result.getValor().equals(222.0));
		
		Assert.assertThat(result.getValor(),CoreMatchers.is(CoreMatchers.equalTo(222.0)));
		
		Assert.assertThat(result.getValor(),CoreMatchers.is(CoreMatchers.not(5.0)));
		//ALT-SIFHT-Z -- Envolver o codigo com algo
		//CTR- D -- APAGA UMA LINHA
		//CODE MINIANS
		
	}
	
	@Test
	public void test2() throws Exception {
		
		//CENARIO
	
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 22,222.0));
		
		//ACAO
	
		Locacao  result = service.alugarFilme(user, filmes);
		Assert.assertNotNull(result);
		error.checkThat(result.getFilmes(), CoreMatchers.is(CoreMatchers.equalTo(filmes)));
		Assert.assertTrue(result.getFilmes().equals(filmes));
		
	}
	
	@Test(expected = Exception.class)
	public void testExceptionElegant() throws Exception  {
		
		//CENARIO
		
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 0,222.0));
		
		//ACAO

		Locacao  result = service.alugarFilme(user, filmes);
		
	}
	
	@Test()
	public void test()  {
		
		//CENARIO
	
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 0,222.0));
		
		//ACAO
	
		try {
			service.alugarFilme(user, filmes);
			
		} catch (Exception e) {
			
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Não há estoque"));
			
		}
		
	}
	
	
	@Test
	public void testExceptionNew() throws Exception  {
		
		//CENARIO
	
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 0,222.0));
		
		
		expException.expect(Exception.class);
		expException.expectMessage("Não há estoque");
		
		//ACAO
		 service.alugarFilme(user, filmes);
		
		
	}
	
	
	@Test(expected = UsuarioNullException.class)
	public void semUsuario() throws Exception {
		//CENARIO
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 22,222.0));
	
		
		
		service.alugarFilme(null, filmes);
		
	}
	
	@Test
	public void semFilme() throws Exception {
		//CENARIO
		List<Filme> filmes = Arrays.asList(new Filme("American pie", 22,222.0));
		
		
		try {
			service.alugarFilme(user, filmes);
			
		} catch (FIlmeNullException e) {

			Assert.assertThat(e.getMessage(), CoreMatchers.is(CoreMatchers.equalTo("Não há filme")));
			
			
		}
		
	}
	
	@Test
	public void closeMonday() throws Exception {
		//Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		PowerMockito.whenNew(Date.class).withAnyArguments().thenReturn(DataUtils.obterData(2021,4,	18));
		
		
		Filme filme5 = new Filme("Get out 3", 1,10.0);
		Filme filme6 = new Filme("Get out 3", 1,10.0);
		
		List<Filme> listFilme = Arrays.asList( filme5, filme6);
		
		Locacao locaccao = service.alugarFilme(user, listFilme);
		Date dateR = locaccao.getDataRetorno();
		
		Assert.assertThat(dateR, CoreMatchers.is(DataUtils.adicionarDias(new Date(),1)) );
		
		
	}
	
	@Test
	public void testMatcher() throws Exception {
		@SuppressWarnings("deprecation")
		Date date = new Date(2021, 3, 18);
		
		Assert.assertThat(date, MyMatcher.caiNoDomingo());
		
		
		
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws UsuarioNegativacaoException, FIlmeNullException, UsuarioNullException, SemEstoqueException {
		
		//CENARIO
		List<Filme> filmes = Arrays.asList(new Filme("deu a loca na voovo", 2, 200.0));
		
		Mockito.when(spcService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		
		expException.expect(UsuarioNegativacaoException.class);
		
		expException.expectMessage("Usuario negativado");
		
		
		//ACAO
		service.alugarFilme(user, filmes);
		
		//VERIFICACAO
		Mockito.verify(spcService).possuiNegativacao(user);
		
	}
	

	@Test
	public void deveEnviarEmailParaLocacoesAtrasada() {
	
		//cenario
			Usuario user2 = new Usuario("Lucas");
			
			Locacao lo = new Locacao();
			lo.setUsuario(user2);
			
			Date retorno = DataUtils.obterDataComDiferencaDias(-3);
			Locacao loc = new Locacao();
			loc.setUsuario(user);
			loc.setDataRetorno(retorno);
			List<Locacao> listLocacoes = Arrays.asList(loc, lo);
			
			
			Mockito.when(dao.obterLocacoesPendentes()).thenReturn(listLocacoes);
			
			
		//acao
		
		service.notificarAtraso();
			
		//verificacao
		Mockito.verify(emailService).notificarAtraso(user);
		Mockito.verify(emailService, Mockito.atLeastOnce()).notificarAtraso(user);
		Mockito.verify(emailService, Mockito.never()).notificarAtraso(user2);
		
		Mockito.verify(emailService, Mockito.times(3)).notificarAtraso(Mockito.any(Usuario.class));
	
	}
	
	
//	@Test
//	public void deveTratarErrornoSpc() {
//		//cenario
//		Usuario usuario = new Usuario();
//		
//		//acao
//		
//		//verificacao
//		
//	}
	
	@Test
	public void deveProrrogarLocacao() {
		//cenario
		Locacao loc = new Locacao();
		loc.setFilme(Arrays.asList(new Filme("Tiago", 2 , 2.0)));
		loc.setUsuario(user);
	
		//acao
		service.prorrogarLocacao(loc,3);
		
		//verificacao
		ArgumentCaptor<Locacao> argument = ArgumentCaptor.forClass(Locacao.class);
		
		Mockito.verify(dao).salvar(argument.capture());
		Locacao locacaoRetorno = argument.getValue();
		
		Assert.assertThat(locacaoRetorno.getValor(), CoreMatchers.is(6.0));
		
	}
	
	@Test
	public void mockarMetodosEstaticos() {
		
		Calendar calandar = getCalendar();
		
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calandar);
		
		Assert.assertThat(Calendar.getInstance(),	CoreMatchers.is(calandar));
		
		PowerMockito.verifyStatic(Calendar.class);
		Calendar.getInstance();
		
	}
	
	@Test
	public void deveAlugarFilmeSemCalcularValor() throws Exception {
		List<Filme> filmes = Arrays.asList(new Filme("Tiago", 2 , 2.0));
	
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);
	
		//ACAO
		Locacao locacao = service.alugarFilme(user, filmes);
		
		//VERIFICACAO
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(1.0));
		//PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
		
	}
	
	@Test
	public void deveCalcularValorLocaco_metodoPrivado() throws Exception {
		List<Filme> filmes = Arrays.asList(new Filme("Tiago", 2 , 2.0));
		
		Double result = Whitebox.invokeMethod(service,	"calcularValorLocacao", filmes);
		
		Assert.assertThat(result, CoreMatchers.is(2.0));
	}

	private Calendar getCalendar() {
		Calendar calandar = Calendar.getInstance();
		calandar.set(Calendar.DAY_OF_MONTH,	 18);
		calandar.set(Calendar.MONTH, Calendar.APRIL);
		calandar.set(Calendar.YEAR, 2021);
		return calandar;
	}
	
	
	
}
