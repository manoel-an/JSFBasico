package infra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.FacesUtil;

/**
 * Servlet implementation class DownloadSV
 */
public class DownloadRelatorioSV extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadRelatorioSV() {
        super();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        File file = null;
        String arquivoGet = null;
        String arquivoPost = null;
        String arquivo = null;
        ByteArrayOutputStream arrayOutputStream = null;
        OutputStream out = null;
        String nomeFile = "";
        try {

            arquivoGet = (String) request.getParameter("relatorio");
            arquivoPost = (String) request.getAttribute("relatorio");
            arquivo = "";

            if (arquivoGet == null) {
                arquivo = arquivoPost;
                nomeFile = "Lancamento.pdf";
            } else {
                arquivo = arquivoGet;
                nomeFile = "Lancamento.pdf";
            }
            file = new File(arquivo);
//            String nomeFile = arquivo.substring(arquivo.lastIndexOf(File.separator) + 1, arquivo.length());
            if (nomeFile.endsWith(".pdf")) {
                response.setHeader("Content-Disposition", "attachment;filename=" + nomeFile);
                response.setContentType("application/cbl");
            } else if (nomeFile.endsWith(".xls")) {
                response.setHeader("Content-Disposition", "attachment;filename=" + nomeFile);
                response.setContentType("application/vnd.ms-excel");
            } else if (nomeFile.endsWith(".xlsx")) {
                response.setHeader("Content-Disposition", "attachment;filename=" + nomeFile);
                response.setContentType("application/vnd.openxmlformats");
            } else {
                response.setHeader("Content-Disposition", "filename=" + nomeFile);
                response.setContentType("application/octet-stream");
            }
            //request.getRequestDispatcher(response.encodeRedirectURL(request.getRequestURL().toString().replace("DownloadRelatorioSV","relatorio/"+arquivo))).forward(request, response);

            arrayOutputStream = criarArray(file);
            arrayOutputStream.flush();
            out = response.getOutputStream();
            out.write(arrayOutputStream.toByteArray());
            out.flush();

        } catch (Exception e) {
            throw e;
        } finally {
        	file.delete();
            file = null;
            arquivoGet = null;
            arquivoPost = null;
            arquivo = null;
            if (arrayOutputStream != null) {
                arrayOutputStream.close();
            }
            arrayOutputStream = null;
            nomeFile = null;
            if (out != null) {
                out.close();
            }
            out = null;
        }
    }

    private ByteArrayOutputStream criarArray(File arquivo) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fi = null;
        byte buffer[] = null;
        try {
            buffer = new byte[4096];
            int bytesRead = 0;
            fi = new FileInputStream(arquivo.getAbsolutePath());
            while ((bytesRead = fi.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, bytesRead);
            }
            arrayOutputStream.close();
            fi.close();
            return arrayOutputStream;
        } catch (Exception e) {
            throw e;
        } finally {
            fi = null;
            buffer = null;
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e.getCause());
        }
    }
}
