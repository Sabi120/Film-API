<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleSOAPControllerProxyid" scope="session" class="controllers.SOAPControllerProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleSOAPControllerProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleSOAPControllerProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        sampleSOAPControllerProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        controllers.SOAPController getSOAPController10mtemp = sampleSOAPControllerProxyid.getSOAPController();
if(getSOAPController10mtemp == null){
%>
<%=getSOAPController10mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
</TABLE>
<%
}
break;
case 15:
        gotMethod = true;
        java.lang.String getAllFilms15mtemp = sampleSOAPControllerProxyid.getAllFilms();
if(getAllFilms15mtemp == null){
%>
<%=getAllFilms15mtemp %>
<%
}else{
        String tempResultreturnp16 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getAllFilms15mtemp));
        %>
        <%= tempResultreturnp16 %>
        <%
}
break;
case 18:
        gotMethod = true;
        String id_1id=  request.getParameter("id33");
        int id_1idTemp  = Integer.parseInt(id_1id);
        String title_2id=  request.getParameter("title35");
            java.lang.String title_2idTemp = null;
        if(!title_2id.equals("")){
         title_2idTemp  = title_2id;
        }
        String year_3id=  request.getParameter("year37");
        int year_3idTemp  = Integer.parseInt(year_3id);
        String director_4id=  request.getParameter("director39");
            java.lang.String director_4idTemp = null;
        if(!director_4id.equals("")){
         director_4idTemp  = director_4id;
        }
        String stars_5id=  request.getParameter("stars41");
            java.lang.String stars_5idTemp = null;
        if(!stars_5id.equals("")){
         stars_5idTemp  = stars_5id;
        }
        String review_6id=  request.getParameter("review43");
            java.lang.String review_6idTemp = null;
        if(!review_6id.equals("")){
         review_6idTemp  = review_6id;
        }
        models.Film updateFilm18mtemp = sampleSOAPControllerProxyid.updateFilm(id_1idTemp,title_2idTemp,year_3idTemp,director_4idTemp,stars_5idTemp,review_6idTemp);
if(updateFilm18mtemp == null){
%>
<%=updateFilm18mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">stars:</TD>
<TD>
<%
if(updateFilm18mtemp != null){
java.lang.String typestars21 = updateFilm18mtemp.getStars();
        String tempResultstars21 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typestars21));
        %>
        <%= tempResultstars21 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">review:</TD>
<TD>
<%
if(updateFilm18mtemp != null){
java.lang.String typereview23 = updateFilm18mtemp.getReview();
        String tempResultreview23 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typereview23));
        %>
        <%= tempResultreview23 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">director:</TD>
<TD>
<%
if(updateFilm18mtemp != null){
java.lang.String typedirector25 = updateFilm18mtemp.getDirector();
        String tempResultdirector25 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedirector25));
        %>
        <%= tempResultdirector25 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">year:</TD>
<TD>
<%
if(updateFilm18mtemp != null){
%>
<%=updateFilm18mtemp.getYear()
%><%}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">title:</TD>
<TD>
<%
if(updateFilm18mtemp != null){
java.lang.String typetitle29 = updateFilm18mtemp.getTitle();
        String tempResulttitle29 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetitle29));
        %>
        <%= tempResulttitle29 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">id:</TD>
<TD>
<%
if(updateFilm18mtemp != null){
%>
<%=updateFilm18mtemp.getId()
%><%}%>
</TD>
</TABLE>
<%
}
break;
case 45:
        gotMethod = true;
        String id_7id=  request.getParameter("id60");
        int id_7idTemp  = Integer.parseInt(id_7id);
        models.Film deleteFilm45mtemp = sampleSOAPControllerProxyid.deleteFilm(id_7idTemp);
if(deleteFilm45mtemp == null){
%>
<%=deleteFilm45mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">stars:</TD>
<TD>
<%
if(deleteFilm45mtemp != null){
java.lang.String typestars48 = deleteFilm45mtemp.getStars();
        String tempResultstars48 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typestars48));
        %>
        <%= tempResultstars48 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">review:</TD>
<TD>
<%
if(deleteFilm45mtemp != null){
java.lang.String typereview50 = deleteFilm45mtemp.getReview();
        String tempResultreview50 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typereview50));
        %>
        <%= tempResultreview50 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">director:</TD>
<TD>
<%
if(deleteFilm45mtemp != null){
java.lang.String typedirector52 = deleteFilm45mtemp.getDirector();
        String tempResultdirector52 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedirector52));
        %>
        <%= tempResultdirector52 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">year:</TD>
<TD>
<%
if(deleteFilm45mtemp != null){
%>
<%=deleteFilm45mtemp.getYear()
%><%}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">title:</TD>
<TD>
<%
if(deleteFilm45mtemp != null){
java.lang.String typetitle56 = deleteFilm45mtemp.getTitle();
        String tempResulttitle56 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetitle56));
        %>
        <%= tempResulttitle56 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">id:</TD>
<TD>
<%
if(deleteFilm45mtemp != null){
%>
<%=deleteFilm45mtemp.getId()
%><%}%>
</TD>
</TABLE>
<%
}
break;
case 62:
        gotMethod = true;
        String title_8id=  request.getParameter("title77");
            java.lang.String title_8idTemp = null;
        if(!title_8id.equals("")){
         title_8idTemp  = title_8id;
        }
        String year_9id=  request.getParameter("year79");
        int year_9idTemp  = Integer.parseInt(year_9id);
        String director_10id=  request.getParameter("director81");
            java.lang.String director_10idTemp = null;
        if(!director_10id.equals("")){
         director_10idTemp  = director_10id;
        }
        String stars_11id=  request.getParameter("stars83");
            java.lang.String stars_11idTemp = null;
        if(!stars_11id.equals("")){
         stars_11idTemp  = stars_11id;
        }
        String review_12id=  request.getParameter("review85");
            java.lang.String review_12idTemp = null;
        if(!review_12id.equals("")){
         review_12idTemp  = review_12id;
        }
        models.Film insertFilm62mtemp = sampleSOAPControllerProxyid.insertFilm(title_8idTemp,year_9idTemp,director_10idTemp,stars_11idTemp,review_12idTemp);
if(insertFilm62mtemp == null){
%>
<%=insertFilm62mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">stars:</TD>
<TD>
<%
if(insertFilm62mtemp != null){
java.lang.String typestars65 = insertFilm62mtemp.getStars();
        String tempResultstars65 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typestars65));
        %>
        <%= tempResultstars65 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">review:</TD>
<TD>
<%
if(insertFilm62mtemp != null){
java.lang.String typereview67 = insertFilm62mtemp.getReview();
        String tempResultreview67 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typereview67));
        %>
        <%= tempResultreview67 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">director:</TD>
<TD>
<%
if(insertFilm62mtemp != null){
java.lang.String typedirector69 = insertFilm62mtemp.getDirector();
        String tempResultdirector69 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typedirector69));
        %>
        <%= tempResultdirector69 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">year:</TD>
<TD>
<%
if(insertFilm62mtemp != null){
%>
<%=insertFilm62mtemp.getYear()
%><%}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">title:</TD>
<TD>
<%
if(insertFilm62mtemp != null){
java.lang.String typetitle73 = insertFilm62mtemp.getTitle();
        String tempResulttitle73 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetitle73));
        %>
        <%= tempResulttitle73 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">id:</TD>
<TD>
<%
if(insertFilm62mtemp != null){
%>
<%=insertFilm62mtemp.getId()
%><%}%>
</TD>
</TABLE>
<%
}
break;
}
} catch (Exception e) { 
%>
Exception: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.toString()) %>
Message: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage()) %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>