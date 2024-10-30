/**
 * SOAPController.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package controllers;

public interface SOAPController extends java.rmi.Remote {
    public java.lang.String getAllFilms() throws java.rmi.RemoteException;
    public models.Film updateFilm(int id, java.lang.String title, int year, java.lang.String director, java.lang.String stars, java.lang.String review) throws java.rmi.RemoteException;
    public models.Film deleteFilm(int id) throws java.rmi.RemoteException;
    public models.Film insertFilm(java.lang.String title, int year, java.lang.String director, java.lang.String stars, java.lang.String review) throws java.rmi.RemoteException;
}
