package view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import model.Lancamento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

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
            
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Lan�amento excluido com sucesso!");
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
    
    public void imprimirPDF(){
    	JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(this.lancamentos);
    	File arquivoIReport = new File("/Projeto/JSFBasico/src/relatorio/LancamentoRel.jasper");
    	JasperReport jasperReport = null;
        try {
        	jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
		} catch (JRException e) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Deu Merda");
		} 
        Map parametros = new HashMap();
        parametros.put("tituloRelatorio", "Lan�amentos");
        parametros.put("logoPadraoRelatorio", "/Projeto/JSFBasico/WebContent/imagens/logo.png");
        JasperPrint printer = null;
		try {
			printer = JasperFillManager.fillReport(jasperReport, parametros, jrds);
		} catch (JRException e1) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro");
		}       
        File pdfFile = new File("/Projeto/JSFBasico/src/relatorio/"+ new Date().getTime() + "pdf");
            if (pdfFile.exists()) {
                try {
                    pdfFile.delete();
                } catch (Exception e) {
                	FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao exportar arquivo em PDF");
                }
            }
		JRPdfExporter jrpdfexporter = new JRPdfExporter();
		jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
		jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
		try{
            jrpdfexporter.exportReport();
        }catch(Exception erro){
        	FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage());
        	
        }        
    }
    
}