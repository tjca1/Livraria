package br.com.saraiva.exeception;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */

@XmlRootElement
public class RestErrorInfo {
    public final String detail;
    public final String message;

    public RestErrorInfo(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}
