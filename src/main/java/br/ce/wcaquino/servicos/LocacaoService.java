package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias; 

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FIlmeNullException;
import br.ce.wcaquino.exceptions.SemEstoqueException;
import br.ce.wcaquino.exceptions.UsuarioNegativacaoException;
import br.ce.wcaquino.exceptions.UsuarioNullException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDAO locacaoDAO;

	private SPCService spcService;
	
	private EmailService emailService;

	
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> listFilme) throws SemEstoqueException, UsuarioNullException, FIlmeNullException, UsuarioNegativacaoException  {
		
		for(Filme filme : listFilme) {
			if(filme.getEstoque() == 0) {
				throw new SemEstoqueException("Não há estoque");
			}
			
		}
	
		if(usuario == null) {
			throw new UsuarioNullException("Não há usuario");
			
		}
		
		if(listFilme == null) {
			throw new FIlmeNullException("Não há filme");
		}
			
		Locacao locacao = new Locacao();
		locacao.setFilme(listFilme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(getData());
		
		double valor = calcularValorLocacao(listFilme);
		
		if(spcService.possuiNegativacao(usuario)) {
			throw new UsuarioNegativacaoException("Usuario negativado");
			
		}
		
		locacao.setValor(valor);

		//Entrega no dia seguinte
		Date dataEntrega = getData();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		this.locacaoDAO.salvar(locacao);
		
	
		
		return locacao;
	}



	private double calcularValorLocacao(List<Filme> listFilme) {
		double valor  =  0.0;
		for (int i = 0; i < listFilme.size(); i++) {
			double valorDoFilme  = listFilme.get(i).getPrecoLocacao();
				
			if(i == 2)
				valorDoFilme = valorDoFilme -  (valorDoFilme * 0.25);
			
			if(i == 3)
				valorDoFilme = valorDoFilme - (valorDoFilme *  0.50);
			
			if(i == 4)
				valorDoFilme = valorDoFilme - (valorDoFilme   * 0.75);
			
			if(i == 5)
				valorDoFilme = 0.0;
			
			valor += valorDoFilme;
			
		}
		return valor;
	}
	
	public void notificarAtraso() {
		List<Locacao> locacoes = locacaoDAO.obterLocacoesPendentes();
		for(Locacao loc : locacoes) {
			
			if(loc.getDataRetorno().before(getData())) {
				emailService.notificarAtraso(loc.getUsuario());
			}
			
		}
		
	}
	
	
	public void prorrogarLocacao(Locacao locacao, int dias) {
		Locacao nova = new Locacao();
		nova.setUsuario(locacao.getUsuario());
		nova.setFilme(locacao.getFilmes());
		nova.setDataLocacao(getData());
		nova.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		nova.setValor(locacao.getValor() * dias);
		locacaoDAO.salvar(locacao);
		
	}

	
protected Date getData() {
	return new Date();
}

}