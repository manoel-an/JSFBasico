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

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroLancamentoBean implements Serializable {

    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private Lancamento lancamento = new Lancamento();
    private String teste = "Teste";

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        Session session = (Session) FacesUtil.getRequestAttribute("session");  
        
        this.pessoas = session.createCriteria(Pessoa.class)
                .addOrder(Order.asc("nome"))
                .list();
        System.out.println("TESTE JREBEL " + teste);
        teste();
    }
    
    public void teste() {
        if (teste.equals("Teste")) {
            //JOptionPane.showMessageDialog(null, teste);
        }
    }
    
    public void lancamentoPagoModificado(ValueChangeEvent event) {
        this.lancamento.setPago((Boolean) event.getNewValue());
        this.lancamento.setDataPagamento(null);
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void cadastrar() {
        Session session = (Session) FacesUtil.getRequestAttribute("session");
        session.merge(this.lancamento);
        
        this.lancamento = new Lancamento();
        
        String msg = "Cadastro efetuado com sucesso!";
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
        //System.out.println("Passei aqui");
    }
    
    public TipoLancamento[] getTiposLancamentos() {
        return TipoLancamento.values();
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    
}