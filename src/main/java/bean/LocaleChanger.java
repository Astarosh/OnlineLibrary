package bean;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(eager=true)
@javax.faces.bean.SessionScoped
public class LocaleChanger implements Serializable {

    private Locale currentLocale;

    public LocaleChanger() {
        this.currentLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public void changeLocale(String localeCode) {
        currentLocale = new Locale(localeCode);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }


}