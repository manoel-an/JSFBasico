package repository;
import java.util.List;

import model.Lancamento;



public interface Lancamentos {

	public List<Lancamento> todos();
	public Lancamento guardar(Lancamento lancamento);
	public Lancamento comDadosIguais(Lancamento lancamento);
	public void remover(Lancamento lancamento);
	public Lancamento porCodigo(Integer codigo);
	
}