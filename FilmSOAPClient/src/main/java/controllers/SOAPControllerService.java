/**
 * SOAPControllerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package controllers;

public interface SOAPControllerService extends javax.xml.rpc.Service {
    public java.lang.String getSOAPControllerAddress();

    public controllers.SOAPController getSOAPController() throws javax.xml.rpc.ServiceException;

    public controllers.SOAPController getSOAPController(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
