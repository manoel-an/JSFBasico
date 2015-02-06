package services;

import model.Pessoa;
import repository.Pessoas;

public class GestaoPessoas {

    private Pessoas pessoas;

    public GestaoPessoas(Pessoas pessoas) {
        this.pessoas = pessoas;
    }

    public void salvar(Pessoa pessoa) throws RegraNegocioException {

        this.pessoas.guardar(pessoa);
    }

}