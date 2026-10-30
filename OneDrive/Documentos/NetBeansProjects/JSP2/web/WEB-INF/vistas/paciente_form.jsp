<%-- 
    Document   : paciente_form
    Created on : 20 oct 2026, 08:04:37
    Author     : jessi
--%>

<%@page import="Conexion.Html"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Conexion.Paciente"%>
<%@page import="Conexion.PacienteDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String ctx = request.getContextPath();

    String usuarioSesion = (String) session.getAttribute("usuario");
    String nombreSesion = (String) session.getAttribute("nombre");
    String rolSesion = (String) session.getAttribute("rol");

    if (nombreSesion == null) {
        nombreSesion = usuarioSesion;
    }

    Paciente p = (Paciente) request.getAttribute("paciente");

    // Evitar error cuando se abre un paciente nuevo
    if (p == null) {
        p = new Paciente();
    }

    String error = (String) request.getAttribute("error");

    boolean esNuevo = p.getId()== 0;

    String fechaValor = p.getFechaNacimiento() != null
            ? p.getFechaNacimiento().toString() : "";

    String sexo = p.getSexo() == null ? "" : p.getSexo();
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Agenda Médica | <%= esNuevo ? "Nuevo paciente" : "Editar paciente"%></title>

        <link rel="stylesheet" href="<%= ctx%>/css/estilos.css">
    </head>

    <body>

        <!-- ENCABEZADO -->
        <header>
            <div class="marca">
                <a href="<%= ctx%>/index.jsp">&#9764; Agenda Médica</a>
            </div>

            <div class="usuario">
                <strong><%= Html.esc(nombreSesion)%></strong><br>

                Usuario: <%= Html.esc(usuarioSesion)%> |
                Rol: <%= Html.esc(rolSesion)%>

                <br><br>

                <a class="boton" href="<%= ctx%>/LogoutServlet">
                    Cerrar Sesión
                </a>
            </div>
        </header>

        <!-- CONTENIDO PRINCIPAL -->
        <main class="contenedor">

            <div class="encabezado-seccion">
                <h1>
                    <%= esNuevo ? "Nuevo paciente" : "Editar paciente"%>
                </h1>
            </div>

            <!-- MENSAJE DE ERROR -->
            <% if (error != null) {%>
            <div class="mensaje error" role="alert">
                <%= Html.esc(error)%>
            </div>
            <% }%>

            <!-- FORMULARIO -->
            <section class="panel">

                <form class="formulario"
                      method="post"
                      action="<%= ctx%>/PacienteServlet">

                    <input type="hidden" name="accion" value="guardar">

                    <input type="hidden" name="id"
                           value="<%= p.getId()%>">

                    <!-- NOMBRE -->
                    <div class="campo">
                        <label for="nombre">
                            Nombre <span class="requerido">*</span>
                        </label>

                        <input type="text"
                               id="nombre"
                               name="nombre"
                               maxlength="100"
                               required
                               value="<%= Html.esc(p.getNombre())%>">
                    </div>

                    <!-- APELLIDO PATERNO -->
                    <div class="campo">
                        <label for="apellidoPaterno">
                            Apellido Paterno <span class="requerido">*</span>
                        </label>

                        <input type="text"
                               id="apellidoPaterno"
                               name="apellidoPaterno"
                               maxlength="100"
                               required
                               value="<%= Html.esc(p.getApellidoPaterno())%>">
                    </div>

                    <!-- APELLIDO MATERNO -->
                    <div class="campo">
                        <label for="apellidoMaterno">
                            Apellido Materno <span class="requerido">*</span>
                        </label>

                        <input type="text"
                               id="apellidoMaterno"
                               name="apellidoMaterno"
                               maxlength="100"
                               required
                               value="<%= Html.esc(p.getApellidoMaterno())%>">
                    </div>

                    <!-- FECHA DE NACIMIENTO -->
                    <div class="campo">
                        <label for="fechaNacimiento">
                            Fecha de Nacimiento
                        </label>

                        <input type="date"
                               id="fechaNacimiento"
                               name="fechaNacimiento"
                               max="<%= LocalDate.now()%>"
                               value="<%= fechaValor%>">
                    </div>

                    <!-- SEXO -->
                    <div class="campo">
                        <label for="sexo">Sexo</label>

                        <select id="sexo" name="sexo">

                            <option value=""
                                    <%= sexo.isEmpty() ? "selected" : ""%>>
                                Sin especificar
                            </option>

                            <option value="Femenino"
                                    <%= "Femenino".equals(sexo) ? "selected" : ""%>>
                                Femenino
                            </option>

                            <option value="Masculino"
                                    <%= "Masculino".equals(sexo) ? "selected" : ""%>>
                                Masculino
                            </option>

                            <option value="Otro"
                                    <%= "Otro".equals(sexo) ? "selected" : ""%>>
                                Otro
                            </option>

                        </select>
                    </div>

                    <!-- TELÉFONO -->
                    <div class="campo">
                        <label for="telefono">Teléfono</label>

                        <input type="tel"
                               id="telefono"
                               name="telefono"
                               maxlength="20"
                               value="<%= Html.esc(p.getTelefono())%>">
                    </div>

                    <!-- CORREO -->
                    <div class="campo completo">
                        <label for="correo">Correo electrónico</label>

                        <input type="email"
                               id="correo"
                               name="correo"
                               maxlength="150"
                               required
                               value="<%= Html.esc(p.getCorreo())%>">
                    </div>

                    <!-- DOMICILIO -->
                    <div class="campo completo">
                        <label for="domicilio">Domicilio</label>

                        <input type="text"
                               id="domicilio"
                               name="domicilio"
                               maxlength="250"
                               required
                               value="<%= Html.esc(p.getDomicilio())%>">
                    </div>

                    <!-- CONTACTO DE EMERGENCIA -->
                    <div class="campo">
                        <label for="contactoEmergencia">
                            Contacto de Emergencia
                        </label>

                        <input type="text"
                               id="contactoEmergencia"
                               name="contactoEmergencia"
                               maxlength="150"
                               required
                               value="<%= Html.esc(p.getContactoEmergencia())%>">
                    </div>

                    <!-- TELÉFONO DE EMERGENCIA -->
                    <div class="campo">
                        <label for="telefonoEmergencia">
                            Teléfono de Emergencia
                        </label>

                        <input type="tel"
                               id="telefonoEmergencia"
                               name="telefonoEmergencia"
                               maxlength="20"
                               required
                               value="<%= Html.esc(p.getTelefonoEmergencia())%>">
                    </div>

                    <!-- BOTONES -->
                    <div class="botones-form">

                        <button type="submit" class="boton">
                            Guardar paciente
                        </button>

                        <a class="boton secundario"
                           href="<%= ctx%>/PacienteServlet">
                            Cancelar
                        </a>

                    </div>

                </form>

            </section>

        </main>

        <!-- PIE DE PÁGINA -->
        <footer>
            Agenda Médica &copy; 2026 |
            Sistema de gestión del consultorio
        </footer>

    </body>
</html>