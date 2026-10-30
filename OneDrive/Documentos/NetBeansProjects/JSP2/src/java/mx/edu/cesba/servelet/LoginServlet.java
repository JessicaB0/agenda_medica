/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mx.edu.cesba.servelet;

import Conexion.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Conexion.UsuarioDAO;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author jessi
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.buscarPorUsuario(usuario);

        if (u != null
                && u.isActivo()
                && BCrypt.checkpw(password, u.getPassword())) {

            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarioId", u.getId());
            sesion.setAttribute("usuario", u.getUsuario());
            sesion.setAttribute("nombre", u.getNombres());
            sesion.setAttribute("rol", u.getRol());

            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );

        } else {
            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp?error=1"
            );
        }
    }
}

