package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringTO {

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}