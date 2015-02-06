package repository;
import java.util.List;

import model.Lancamento;
import model.Pessoa;

public interface Pessoas {

	public List<Pessoa> todas();
	public Pessoa porCodigo(Integer codigo);
    public Pessoa guardar(Pessoa pessoa);
	
}