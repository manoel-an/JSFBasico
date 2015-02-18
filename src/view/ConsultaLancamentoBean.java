package view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Lancamento;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import util.FacesUtil;
import util.HibernateUtil;

@ManagedBean
@ViewScoped
public class ConsultaLancamentoBean implements Serializable {

    private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
    private Lancamento lancamentoSelecionado;
    private static double nomeRel;

    
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
            
            FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_INFO, "Lançamento excluido com sucesso!");
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
        parametros.put("tituloRelatorio", "Lançamentos");
        parametros.put("logoPadraoRelatorio", "/Projeto/JSFBasico/WebContent/imagens/logo.png");
        JasperPrint printer = null;
		try {
			printer = JasperFillManager.fillReport(jasperReport, parametros, jrds);
		} catch (JRException e1) {
			FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro");
		} 
		nomeRel = (new Date().getTime());
        File pdfFile = new File("/Projeto/JSFBasico/src/relatorio/"+ nomeRel + ".pdf");
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
    
	private void ImprimirDOC() throws Exception {
    	JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(this.lancamentos);
    	File arquivoIReport = new File("/Projeto/JSFBasico/src/relatorio/LancamentoRel.jasper");
    	JasperReport jasperReport = null;
        Map parametros = new HashMap();
        parametros.put("tituloRelatorio", "Lançamentos");
        parametros.put("logoPadraoRelatorio", "/Projeto/JSFBasico/WebContent/imagens/logo.png");        
        JasperPrint printer = JasperFillManager.fillReport(jasperReport, parametros, jrds);
        nomeRel = (new Date().getTime());
        File docFile = new File("/Projeto/JSFBasico/src/relatorio/"+ nomeRel + ".rtf");
		if (docFile.exists()) {
			try {
				docFile.delete();
			} catch (Exception e) {
				FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro");
			}
		}
		JRRtfExporter jrRtfExporter = new JRRtfExporter();
		jrRtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
		jrRtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE, docFile);
		try{
            jrRtfExporter.exportReport();
        }catch(Exception erro){
    		FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro");
        }
		jrRtfExporter = null;        
	}
	
	private void ImprimirExcel() throws Exception {
    	JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(this.lancamentos);
    	File arquivoIReport = new File("/Projeto/JSFBasico/src/relatorio/LancamentoRel.jasper");
    	JasperReport jasperReport = null;
        Map parametros = new HashMap();
        parametros.put("tituloRelatorio", "Lançamentos");
        parametros.put("logoPadraoRelatorio", "/Projeto/JSFBasico/WebContent/imagens/logo.png");        
        JasperPrint printer = JasperFillManager.fillReport(jasperReport, parametros, jrds);
        nomeRel = (new Date().getTime());
        File excelFile = new File("/Projeto/JSFBasico/src/relatorio/"+ nomeRel + ".xls");
        if (excelFile.exists()) {
			try {
				excelFile.delete();
			} catch (Exception e) {
	    		FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao exportar para XLS");
			}
		}
		JRXlsExporter jrpdfexporter = new JRXlsExporter();
		jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, this.gerarRelatorioJasperPrintObjeto(parametros, this.lancamentos));		
        jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, excelFile);
		jrpdfexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		jrpdfexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		jrpdfexporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		try{
            jrpdfexporter.exportReport();
        }catch(Exception erro){
    		FacesUtil.adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro");
        }
		jrpdfexporter = null;       
	}
    
    public String getDownload(){
    	if(nomeRel != 0.0) {
    		String link = "location.href='/JSFBasico/DownloadRelatorioSV?relatorio=/Projeto/JSFBasico/src/relatorio/"+ nomeRel + ".pdf'";
    		nomeRel = 0.0;
    		return link;   	
    	} else {
    		return "";
    	}
    }
    
	public JasperPrint gerarRelatorioJasperPrintObjeto( Map parametros, Collection listaObjetos) throws Exception {
		try {
			JRDataSource jr = new JRBeanArrayDataSource(listaObjetos.toArray());
			//String nomeJasperReportDesignIReport = nomeRelatorio + PUGConstants.TIPO_ARQUIVO_JASPER;
			//File arquivoIReport = new File(msgs.getString("repositorioRelatorio") + nomeJasperReportDesignIReport);
	    	File arquivoIReport = new File("/Projeto/JSFBasico/src/relatorio/LancamentoRel.jasper");
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, jr);
			jr = null;
			//nomeJasperReportDesignIReport = null;
			arquivoIReport = null;
			jasperReport = null;
			return print;
		} catch (Exception e) {
			throw e;
		}
	}     
    
}