package controllers;

public class SOAPControllerProxy implements controllers.SOAPController {
  private String _endpoint = null;
  private controllers.SOAPController sOAPController = null;
  
  public SOAPControllerProxy() {
    _initSOAPControllerProxy();
  }
  
  public SOAPControllerProxy(String endpoint) {
    _endpoint = endpoint;
    _initSOAPControllerProxy();
  }
  
  private void _initSOAPControllerProxy() {
    try {
      sOAPController = (new controllers.SOAPControllerServiceLocator()).getSOAPController();
      if (sOAPController != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sOAPController)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sOAPController)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sOAPController != null)
      ((javax.xml.rpc.Stub)sOAPController)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public controllers.SOAPController getSOAPController() {
    if (sOAPController == null)
      _initSOAPControllerProxy();
    return sOAPController;
  }
  
  public java.lang.String getAllFilms() throws java.rmi.RemoteException{
    if (sOAPController == null)
      _initSOAPControllerProxy();
    return sOAPController.getAllFilms();
  }
  
  public models.Film updateFilm(int id, java.lang.String title, int year, java.lang.String director, java.lang.String stars, java.lang.String review) throws java.rmi.RemoteException{
    if (sOAPController == null)
      _initSOAPControllerProxy();
    return sOAPController.updateFilm(id, title, year, director, stars, review);
  }
  
  public models.Film deleteFilm(int id) throws java.rmi.RemoteException{
    if (sOAPController == null)
      _initSOAPControllerProxy();
    return sOAPController.deleteFilm(id);
  }
  
  public models.Film insertFilm(java.lang.String title, int year, java.lang.String director, java.lang.String stars, java.lang.String review) throws java.rmi.RemoteException{
    if (sOAPController == null)
      _initSOAPControllerProxy();
    return sOAPController.insertFilm(title, year, director, stars, review);
  }
  
  
}