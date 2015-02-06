package view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Pessoa;
import services.GestaoPessoas;
import services.RegraNegocioException;
import util.FacesUtil;
import util.Repositorios;

@ManagedBean
@ViewScoped
public class CadastroPessoaBean {
    private Pessoa pessoa = new Pessoa();
    private Repositorios repositorios = new Repositorios();

    public void salvar() {
        GestaoPessoas gestaoPessoas = new GestaoPessoas(this.repositorios.getPessoas());
        try {
            gestaoPessoas.salvar(pessoa);
            this.pessoa = new Pessoa();
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, FacesUtil.getMensagemI18n("entry_saved_people"));
        } catch (RegraNegocioException e) {
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, FacesUtil.getMensagemI18n(e.getMessage()));
        }
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

}
