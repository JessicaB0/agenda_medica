<%-- 
    Document   : login
    Created on : 16 oct 2026, 07:36:42
    Author     : jessi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">

        <meta name="viewport"
              content="Width=device-width, initial-scale=1.0">

        <title>Agenda Médica - Iniciar sesion</title>

        <style>
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(135deg, #0d6efd, #084298);
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 20px;
            }

            .login-container {
                width: 100%;
                max-width: 400px;
                background: #ffffff;
                padding: 40px;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.25);
            }

            .logo {
                text-align: center;
                margin-bottom: 20px;
            }

            .logo-icon {
                font-size: 60px;
            }

            h1 {
                text-align: center;
                color: #222222;
                margin-bottom: 8px;
            }

            .subtitulo {
                text-align: center;
                color: #666666;
                margin-bottom: 30px;
            }

            label {
                display: block;
                margin-bottom: 8px;
                color: #333333;
                font-weight: bold;
            }

            input {
                width: 100%;
                padding: 12px;
                margin-bottom: 20px;
                border: 1px solid #cccccc;
                border-radius: 8px;
                font-size: 16px;
            }

            input:focus {
                outline: none;
                border-color: #0d6efd;
                box-shadow: 0 0 5px rgba(13, 110, 253, 0.3);
            }

            button {
                width: 100%;
                padding: 13px;
                border: none;
                border-radius: 8px;
                background: #0d6efd;
                color: #ffffff;
                font-size: 17px;
                cursor: pointer;
            }

            button:hover {
                background: #084298;
            }

            .mensaje-error {
                background: #f8d7da;
                color: #842029;
                border: 1px solid #f5c2c7;
                padding: 12px;
                border-radius: 8px;
                margin-bottom: 20px;
                text-align: center;
            }

            .pie {
                text-align: center;
                margin-top: 25px;
                color: #777777;
                font-size: 13px;
            }

            @media (max-width: 480px) {
                .login-container {
                    padding: 30px 25px;
                }
            }</style>
    </head>

    <body>
        <div class="login-container">
            <div class="logo">
                <div class="logo-icon"></div>

            </div>
            <h1>Agenda Médica</h1>
            <div class="subtitulo">
                Sistema de gestión para consultorio
            </div>
            <%
                String error = request.getParameter("error");
                if ("1".equals(error)) {
            %>
            <div class="mensaje-error">
                Usuario o contraseña incorrectos.
            </div>
            <%
                }
            %>
            <form action="LoginServlet" method="post">
                <label for="usuario">
                    Usuario
                </label>

                <input
                    type="text"
                    id="usuario"
                    name="usuario"
                    placeholder="Ingresa tu usuario"
                    required
                    autocomplere="username"
                    >
                <label for="password">
                    Contraseña
                </label>

                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Ingresa tucontraseña"
                    required
                    autocomplete="current-password"
                    >
                <button type="submit">
                    Ingresar
                </button>

            </form>

            <div class="pie">
                Agenda Médica 2026
            </div>
        </div>
    </body>

</html>

