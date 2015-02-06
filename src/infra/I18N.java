package infra;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import util.FacesUtil;

@ManagedBean(eager = true)
@ApplicationScoped
public class I18N implements Serializable {

    private static final long serialVersionUID = -3626431200242024826L;

    public static String getKey(String key) {
        key = key == null ? "" : key;
        return FacesUtil.getMensagemI18n(key);
    }
}
