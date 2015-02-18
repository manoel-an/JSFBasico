package infra;

public class GeradorRelatorio {
	private static double nomeRel;
	
	public static double getNomeRel() {
		return nomeRel;
	}
	
	public static void setNomeRel(double nomeRel) {
		GeradorRelatorio.nomeRel = nomeRel;
	}

    public String getDownload(){
    	System.out.println(getNomeRel());
    	System.out.println(getNomeRel());
    	//return "location.href='../DownloadRelatorioSV?relatorio=" + getCaminhoRelatorio() + "'";
    	return "location.href='/JSFBasico/DownloadRelatorioSV?relatorio=/Projeto/JSFBasico/src/relatorio/"+ getNomeRel() + ".pdf'";
    }	
	
}
