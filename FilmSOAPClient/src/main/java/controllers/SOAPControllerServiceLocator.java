/**
 * SOAPControllerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package controllers;

public class SOAPControllerServiceLocator extends org.apache.axis.client.Service implements controllers.SOAPControllerService {

    public SOAPControllerServiceLocator() {
    }


    public SOAPControllerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SOAPControllerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SOAPController
    private java.lang.String SOAPController_address = "http://localhost:8080/FilmSOAP/services/SOAPController";

    public java.lang.String getSOAPControllerAddress() {
        return SOAPController_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SOAPControllerWSDDServiceName = "SOAPController";

    public java.lang.String getSOAPControllerWSDDServiceName() {
        return SOAPControllerWSDDServiceName;
    }

    public void setSOAPControllerWSDDServiceName(java.lang.String name) {
        SOAPControllerWSDDServiceName = name;
    }

    public controllers.SOAPController getSOAPController() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SOAPController_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSOAPController(endpoint);
    }

    public controllers.SOAPController getSOAPController(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            controllers.SOAPControllerSoapBindingStub _stub = new controllers.SOAPControllerSoapBindingStub(portAddress, this);
            _stub.setPortName(getSOAPControllerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSOAPControllerEndpointAddress(java.lang.String address) {
        SOAPController_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (controllers.SOAPController.class.isAssignableFrom(serviceEndpointInterface)) {
                controllers.SOAPControllerSoapBindingStub _stub = new controllers.SOAPControllerSoapBindingStub(new java.net.URL(SOAPController_address), this);
                _stub.setPortName(getSOAPControllerWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SOAPController".equals(inputPortName)) {
            return getSOAPController();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://controllers", "SOAPControllerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://controllers", "SOAPController"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SOAPController".equals(portName)) {
            setSOAPControllerEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
