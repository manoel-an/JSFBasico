package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class FacesUtil {

    public static String getMensagemI18n(String chave) {
        // context.getExternalContext().getRealPath("/");
        // FacesUtil.class.getClassLoader().getResourceAsStream("resources/sistema_pt.properties");
        // String msg = context.getApplication().getResourceBundle(context,
        // "msg").getString(chave);
        // return msg;
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String msg = "";
        try {
            msg = getProp(locale).getProperty(chave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static void adicionarMensagem(Severity tipo, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, msg, msg));
    }

    public static Object getRequestAttribute(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        return request.getAttribute(name);
    }

    public static Properties getProp(Locale locale) throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("/Projeto/JSFBasico/src/resources/sistema_"+locale.getLanguage()+".properties");
        props.load(file);
        return props;
    }

}