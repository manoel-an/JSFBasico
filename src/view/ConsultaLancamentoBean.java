package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import model.Lancamento;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import util.FacesUtil;
import util.HibernateUtil;

@ManagedBean
public class ConsultaLancamentoBean implements Serializable {

    private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
    private Lancamento lancamentoSelecionado;
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void inicializar() {
        Session session = HibernateUtil.getSession();
        
        this.lancamentos = session.createCriteria(Lancamento.class)
                .addOrder(Order.desc("dataVencimento"))
                .list();
        
        session.close();
    }

    public void excluir() {
        if (this.lancamentoSelecionado.isPago()) {
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Lançamento já foi pago e não pode ser excluído.");
        } else {
            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();
            
            session.delete(this.lancamentoSelecionado);
            
            trx.commit();
            session.close();
            
            this.inicializar();
            
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Lançamento excluído com sucesso!");
        }
    }
    
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public Lancamento getLancamentoSelecionado() {
        return lancamentoSelecionado;
    }

    public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
        this.lancamentoSelecionado = lancamentoSelecionado;
    }
    
}