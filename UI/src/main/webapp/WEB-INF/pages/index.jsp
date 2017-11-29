<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>CargoManager</title>
    <c:import url="tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="tiles/sidebar-index.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>CargoManager</h1>

            <div id="login-box">

                <h3 align="center">Sign in</h3>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>

                <form name='loginForm' action="<c:url value='login' />" method='POST'>

                    <table>
                        <tr>
                            <td>User:</td>
                            <td><input type='text' name='username' value=''></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type='password' name='password'/></td>
                        </tr>
                        <tr>
                            <td colspan='2'>
                                <div align="center"><input name="submit" type="submit" value="Login"/></div>
                            </td>
                        </tr>
                    </table>

                    <br/>

                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>

                </form>
            </div>
        </div>
    </div>

</div>

<c:import url="tiles/scripts.jsp"/>

</body>

</html>
