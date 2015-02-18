package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import model.Lancamento;
import model.Pessoa;
import model.TipoLancamento;
import repository.Pessoas;
import services.GestaoLancamentos;
import services.RegraNegocioException;
import util.FacesUtil;
import util.Repositorios;

@ManagedBean
@ViewScoped
public class CadastroLancamentoBean implements Serializable {

    private Repositorios repositorios = new Repositorios();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private Lancamento lancamento = new Lancamento();

    @PostConstruct
    public void init() {
        Pessoas pessoas = this.repositorios.getPessoas();
        this.pessoas = pessoas.todas();
    }
    
    public void lancamentoPagoModificado(ValueChangeEvent event) {
        this.lancamento.setPago((Boolean) event.getNewValue());
        this.lancamento.setDataPagamento(null);
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void salvar() {
        GestaoLancamentos gestaoLancamentos = new GestaoLancamentos(this.repositorios.getLancamentos());
        try {
            gestaoLancamentos.salvar(lancamento);
            
            this.lancamento = new Lancamento();
            
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, FacesUtil.getMensagemI18n("entry_saved"));
        } catch (RegraNegocioException e) {
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, FacesUtil.getMensagemI18n(e.getMessage()));
        }
    }
    
    public boolean isEditando() {
        return this.lancamento.getCodigo() != null;
    }
    
    public TipoLancamento[] getTiposLancamentos() {
        return TipoLancamento.values();
    }

    public Lancamento getLancamento() {
        return lancamento;
    }
    
    public void setLancamento(Lancamento lancamento) throws CloneNotSupportedException {
        this.lancamento = lancamento;
        if (this.lancamento == null) {
            this.lancamento = new Lancamento();
        } else {
            this.lancamento = (Lancamento) lancamento.clone();
        }
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    
}