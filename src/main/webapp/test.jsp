<%-- 
    Document   : test
    Created on : May 10, 2025, 1:37:21 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
                <form id="loginForm" action="<%= request.getContextPath() %>/test" method="GET">
                    <!-- Nút submit -->
                   <div>
                       <input type="submit" value="test">
                   </div>
               </form>
            
    </body>
</html>
