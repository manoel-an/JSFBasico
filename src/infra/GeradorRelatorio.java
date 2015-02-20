package infra;

public class GeradorRelatorio {
    private static String nomeInicialRelatorio;
    private static String nomeFinalrelatorio;
    private static boolean realizaDownload;

    public String getDownload() {
        if (isRealizaDownload()) {
            setRealizaDownload(false);
            String link = "location.href='/JSFBasico/DownloadRelatorioSV?relatorio=/Projeto/JSFBasico/src/relatorio/"
                    + getNomeInicialRelatorio() + "&nome=" + getNomeFinalrelatorio() + "'";
            setNomeInicialRelatorio("");
            setNomeFinalrelatorio("");
            return link;
        }
        return "";
    }

    public static String getNomeInicialRelatorio() {
        return nomeInicialRelatorio;
    }
    
    public static void setNomeInicialRelatorio(String nomeInicialRelatorio) {
        GeradorRelatorio.nomeInicialRelatorio = nomeInicialRelatorio;
    }

    public static String getNomeFinalrelatorio() {
        return nomeFinalrelatorio;
    }

    public static void setNomeFinalrelatorio(String nomeFinalrelatorio) {
        GeradorRelatorio.nomeFinalrelatorio = nomeFinalrelatorio;
    }

    public static boolean isRealizaDownload() {
        return realizaDownload;
    }

    public static void setRealizaDownload(boolean realizaDownload) {
        GeradorRelatorio.realizaDownload = realizaDownload;
    }

}
