/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mx.edu.cesba.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import Conexion.PacienteDAO;
import Conexion.Paciente;
import jakarta.security.auth.message.callback.PrivateKeyCallback;
import java.util.List;
import org.apache.catalina.connector.Request;
import Conexion.Paciente;
import Conexion.Html;
import Conexion.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import jakarta.servlet.http.HttpSession;
import Conexion.Permisos;
/**
 *
 * @author jessi
 */
@WebServlet("/PacienteServlet")
public class PacienteServlet extends HttpServlet {

    private static final String VISTA_LISTA =
            "/WEB-INF/vistas/pacientes.jsp";

    private static final String VISTA_FORM =
            "/WEB-INF/vistas/paciente_form.jsp";

    private static final Pattern CORREO =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private static final Pattern TELEFONO =
            Pattern.compile("^[0-9+()\\-\\s]{7,20}$");

    private final PacienteDAO dao = new PacienteDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!autorizado(request, response)) {
            return;
        }

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            case "nuevo":
                mostrarFormulario(request, response,
                        new Paciente(), null);
                break;

            case "editar":
                Paciente existente =
                        dao.buscarPorId(leerId(request));

                if (existente == null) {
                    response.sendRedirect(
                            request.getContextPath()
                            + "/PacienteServlet");
                } else {
                    mostrarFormulario(request, response,
                            existente, null);
                }
                break;

            default:
                listar(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (!autorizado(request, response)) {
            return;
        }

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "";
        }

        switch (accion) {

            case "guardar":
                guardar(request, response);
                break;

            case "desactivar":
                cambiarEstado(request, response, false);
                break;

            case "reactivar":
                cambiarEstado(request, response, true);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath()
                        + "/PacienteServlet");
                break;
        }
    }

    private boolean autorizado(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession sesion = request.getSession(false);

        if (sesion == null
                || sesion.getAttribute("usuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");

            return false;
        }

        String rol = (String) sesion.getAttribute("rol");

        if (!Permisos.puede(rol, Permisos.PACIENTES)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    private void listar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String filtro = request.getParameter("q");

        if (filtro == null) {
            filtro = "";
        }

        boolean verInactivos =
                "1".equals(request.getParameter("inactivos"));

        List<Paciente> pacientes =
                dao.listar(filtro, !verInactivos);

        String msg = request.getParameter("msg");

        if ("guardado".equals(msg)) {

            request.setAttribute("mensaje",
                    "Paciente guardado.");

        } else if ("desactivado".equals(msg)) {

            request.setAttribute("mensaje",
                    "Paciente desactivado.");

        } else if ("reactivado".equals(msg)) {

            request.setAttribute("mensaje",
                    "Paciente reactivado.");

        } else if ("error".equals(msg)) {

            request.setAttribute("mensaje",
                    "No se pudo completar la acción. Inténtalo de nuevo.");

            request.setAttribute("mensajeError", true);
        }

        request.setAttribute("pacientes", pacientes);
        request.setAttribute("filtro", filtro);
        request.setAttribute("verInactivos", verInactivos);

        request.getRequestDispatcher(VISTA_LISTA)
                .forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request,
            HttpServletResponse response,
            Paciente paciente, String error)
            throws ServletException, IOException {

        request.setAttribute("paciente", paciente);
        request.setAttribute("error", error);

        request.getRequestDispatcher(VISTA_FORM)
                .forward(request, response);
    }

    private void guardar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Paciente p = new Paciente();

        p.setId(leerId(request));
        p.setNombre(texto(request, "nombre"));
        p.setApellidoPaterno(
                texto(request, "apellidoPaterno"));
        p.setApellidoMaterno(
                texto(request, "apellidoMaterno"));
        p.setSexo(texto(request, "sexo"));
        p.setTelefono(texto(request, "telefono"));
        p.setCorreo(texto(request, "correo"));
        p.setDomicilio(texto(request, "domicilio"));
        p.setContactoEmergencia(
                texto(request, "contactoEmergencia"));
        p.setTelefonoEmergencia(
                texto(request, "telefonoEmergencia"));

        String error = null;

        String fechaTexto =
                texto(request, "fechaNacimiento");

        if (!fechaTexto.isEmpty()) {
            try {
                p.setFechaNacimiento(
                        LocalDate.parse(fechaTexto));

            } catch (DateTimeParseException e) {
                error = "La fecha de nacimiento no es válida.";
            }
        }

        if (error == null) {
            error = validar(p);
        }

        if (error != null) {
            mostrarFormulario(request, response, p, error);
            return;
        }

        boolean ok = (p.getId() == 0)
                ? dao.insertar(p)
                : dao.actualizar(p);

        if (ok) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/PacienteServlet?msg=guardado");

        } else {

            mostrarFormulario(request, response, p,
                    "No se pudo guardar el paciente. Intenta de nuevo.");
        }
    }

    private void cambiarEstado(HttpServletRequest request,
            HttpServletResponse response,
            boolean activo) throws IOException {

        int id = leerId(request);

        boolean ok = id > 0
                && dao.cambiarEstado(id, activo);

        String msg;

        if (!ok) {
            msg = "error";
        } else {
            msg = activo ? "reactivado" : "desactivado";
        }

        response.sendRedirect(
                request.getContextPath()
                + "/PacienteServlet?msg=" + msg);
    }

    private String validar(Paciente p) {

        if (p.getNombre().isEmpty()) {
            return "El nombre es obligatorio.";
        }

        if (p.getApellidoPaterno().isEmpty()) {
            return "El apellido paterno es obligatorio.";
        }

        String e;

        if ((e = revisarLongitud(
                p.getNombre(), 100, "nombre")) != null) {
            return e;
        }

        if ((e = revisarLongitud(
                p.getApellidoPaterno(), 100,
                "apellido paterno")) != null) {
            return e;
        }

        if ((e = revisarLongitud(
                p.getApellidoMaterno(), 100,
                "apellido materno")) != null) {
            return e;
        }

        if ((e = revisarLongitud(
                p.getCorreo(), 150, "correo")) != null) {
            return e;
        }

        if ((e = revisarLongitud(
                p.getDomicilio(), 250, "domicilio")) != null) {
            return e;
        }

        if ((e = revisarLongitud(
                p.getContactoEmergencia(), 150,
                "contacto de emergencia")) != null) {
            return e;
        }

        if (p.getFechaNacimiento() != null
                && p.getFechaNacimiento().isAfter(LocalDate.now())) {

            return "La fecha de nacimiento no puede ser futura.";
        }

        String sexo = p.getSexo();

        if (!sexo.isEmpty()
                && !sexo.equals("Femenino")
                && !sexo.equals("Masculino")
                && !sexo.equals("Otro")) {

            return "El sexo seleccionado no es válido.";
        }

        if (!p.getCorreo().isEmpty()
                && !CORREO.matcher(p.getCorreo()).matches()) {

            return "El correo electrónico no es válido.";
        }

        if (!p.getTelefono().isEmpty()
                && !TELEFONO.matcher(p.getTelefono()).matches()) {

            return "El teléfono no es válido. Usa solo números, espacios,"
                    + " +, - y paréntesis (entre 7 y 20 caracteres).";
        }

        if (!p.getTelefonoEmergencia().isEmpty()
                && !TELEFONO.matcher(
                        p.getTelefonoEmergencia()).matches()) {

            return "El teléfono de emergencia no es válido. Usa solo números, "
                    + "espacios, +, - y paréntesis (entre 7 y 20 caracteres).";
        }

        return null;
    }

    private String revisarLongitud(
            String valor, int max, String campo) {

        if (valor != null && valor.length() > max) {

            return "El campo " + campo
                    + " no puede tener más de "
                    + max + " caracteres.";
        }

        return null;
    }

    private int leerId(HttpServletRequest request) {

        try {
            return Integer.parseInt(
                    request.getParameter("id"));

        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String texto(HttpServletRequest request,
            String nombre) {

        String valor = request.getParameter(nombre);

        return valor == null ? "" : valor.trim();
    }
}

