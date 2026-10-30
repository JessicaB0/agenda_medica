<%-- 
    Document   : index
    Created on : 16 oct 2026, 01:34:21
    Author     : jessi
--%>

<%@page import="Conexion.Permisos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = (String) session.getAttribute("usuario");

    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String nombre = (String) session.getAttribute("nombre");
    String rol = (String) session.getAttribute("rol");

    if (nombre == null) {
        nombre = usuario;
    }
    if (rol == null) {
        rol = "USUARIO";
    }
%>
<html lang="es">
    <head>
        <meta charset="UFT-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agenda Médica | Panel principal</title>

        <style>
            * {
                box-sizing: border-box;
            }

            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f1f5f9;
                color: #1e293b;
            }

            header {
                background: #ffffff;
                padding: 20px 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 8px #00000012;
            }

            .marca {
                color: #0f4c81;
                font-size: 24px;
                font-weight: bold;
            }

            .usuario {
                text-align: right;
                font-size: 14px;
                line-height: 1.5;
            }

            .contenedor {
                max-width: 1200px;
                margin: 35px auto;
                padding: 0 20px;
            }

            .bienvenida {
                margin-bottom: 28px;
            }

            .bienvenida h1 {
                margin-top: 0;
                margin-bottom: 8px;
                font-size: 30px;
            }

            .bienvenida p {
                color: #64748b;
                line-height: 1.6;
                margin-top: 0;
                margin-bottom: 15px;
            }

            /* TARJETAS */
            .tarjetas {
                display: grid;
                grid-template-columns: repeat(
                    auto-fill,
                    minmax(220px, 1fr)
                    );
                gap: 20px;
                align-items: stretch;
            }

            .tarjeta {
                background: white;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 3px 12px #0000000c;
                border-top: 4px solid #1677ff;

                display: flex;
                flex-direction: column;
                min-width: 0;
            }

            .tarjeta h2 {
                font-size: 19px;
                margin-top: 0;
                margin-bottom: 12px;
                line-height: 1.4;
            }

            .tarjeta p {
                color: #64748b;
                font-size: 14px;
                line-height: 1.6;
                margin-top: 0;
                margin-bottom: 20px;
            }

            .boton {
                display: inline-block;
                background: #1677ff;
                color: white;
                padding: 10px 16px;
                border-radius: 7px;
                text-decoration: none;
                font-size: 14px;
                text-align: center;
                margin-top: auto;
                align-self: flex-start;
            }

            .boton:hover {
                background: #095dcc;
            }

            .expediente {
                border-top-color: #16a085;
            }

            .expediente .boton {
                background: #12836c;
            }

            .expediente .boton:hover {
                background: #0d6957;
            }

            footer {
                text-align: center;
                padding: 25px;
                color: #64748b;
                font-size: 13px;
                line-height: 1.5;
            }

            @media (max-width: 600px) {
                header {
                    padding: 18px;
                    gap: 15px;
                    flex-wrap: wrap;
                }

                .usuario {
                    text-align: left;
                }

                .contenedor {
                    margin: 25px auto;
                    padding: 0 15px;
                }

                .bienvenida h1 {
                    font-size: 25px;
                }

                .tarjetas {
                    grid-template-columns: 1fr;
                    gap: 15px;
                }

                .tarjeta {
                    padding: 20px;
                }
            </style>
        </head>
        <body>
            <header>
                <div class="marca">
                    &#9764; Agenda Médica
                </div>
                <div class="usuario">
                    <strong><%= nombre%></strong><br>
                    Usuario: <%= usuario%> | Rol: <%= rol%>
                    <br><br>
                    <a class="boton" href="LogoutServlet">Cerrar sesión</a>
                </div>
            </header>      
            <main class="contenedor">
                <section class="bienvenida">
                    <h1>Panel principal</h1>
                    <p>Bienvenido al sistema de gestion del consultorio médico</p>
                </section>

                <section class="tarjetas">
                    <% if (Permisos.puede(rol, Permisos.PACIENTES)) { %>
                    <article class="tarjeta">
                        <h2>Pacientes</h2>
                        <p>Registro y administración de los datos de los pacientes.</p>
                        <a class="boton" href="PacienteServlet">Abrir</a>
                    </article>
                    <% }%>
                    <article class="tarjeta">
                        <h2>Médicos</h2>
                        <p>Informacón de los médicos y sus datos profesionales</p>
                        <span class="boton">Próximamente</span>
                    </article>

                    <article class="tarjeta">
                        <h2>Agenda de citas</h2>
                        <p>Organización de horarios, citas y disponibilidad médica</p>
                        <span class="boton">Próximamente</span>
                    </article>

                    <article class="tarjeta">
                        <h2>Consultas</h2>
                        <p>Registro de consultas y notas médicas</p>
                        <span class="boton">Próximamente</span>
                    </article>

                    <article class="tarjeta expediente">
                        <h2>Expedientes médicos</h2>
                        <p>Acceso controlado a la información clínica confidencial</p>
                        <span class="boton">Próximamente</span>
                    </article>

                    <article class="tarjeta">
                        <h2>Usuarios</h2>
                        <p>Administración de las cuentas y permisos del sistema</p>
                        <span class="boton">Próximamente</span>
                    </article>
                </section>
            </main>
            <footer>
                Agenda Médica &copy; 2026 | Sistema de gestión del consultorio
            </footer>
        </body>
    </html>
