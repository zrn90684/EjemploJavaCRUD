package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ServletControlador", urlPatterns = {"/ServletControlador"})
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion != null && accion.trim().length() > 0) {
            switch (accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    this.eliminarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
        
        // se copia la logia de doPost y en el metodo se utilizara
        
    }
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion != null && accion.trim().length() > 0) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }

    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        int idCliente = 0;
        String strIdCliente = request.getParameter("idCliente");
        if (strIdCliente != null && strIdCliente.trim().length() > 0) {
            idCliente = Integer.parseInt(strIdCliente);
        }
         */
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String strSaldo = request.getParameter("saldo");
        if (strSaldo != null && strSaldo.trim().length() > 0) {
            saldo = Integer.parseInt(strSaldo);
        }
        //Modelo
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);
        // Se inserta el cliente
        int insertados = new ClienteDaoJDBC().insertar(cliente);
        //if()
        this.accionDefault(request, response);
    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = 0;
        String strIdCliente = request.getParameter("idCliente");
        if (strIdCliente != null && strIdCliente.trim().length() > 0) {
            idCliente = Integer.parseInt(strIdCliente);
        }
        
        Cliente cliente=new ClienteDaoJDBC().encontrar(new Cliente(idCliente));
        int borrado=new ClienteDaoJDBC().eliminar(cliente);
        this.accionDefault(request, response);
    }
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = 0;
        String strIdCliente = request.getParameter("idCliente");
        if (strIdCliente != null && strIdCliente.trim().length() > 0) {
            idCliente = Integer.parseInt(strIdCliente);
        }
        
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String strSaldo = request.getParameter("saldo");
        if (strSaldo != null && strSaldo.trim().length() > 0) {
            saldo = Integer.parseInt(strSaldo);
        }
        //Modelo
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);//nombre, apellido, email, telefono, saldo);
        int resul=new ClienteDaoJDBC().actualizar(cliente);
        this.accionDefault(request, response);
    }
    
    private void editarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // encontrar cliente algo asi 
        //Cliente cliente=new ClienteDaoJDB().encontrar(new Cliente(idCliente));
        // se creara un nuevo JSP para editar el cliente
        // este redirigira a editarCliente.jsp con el cliente en request
        
        // se basara en el codigo de insertar, aunque con las anotaciones descritas
        int idCliente = 0;
        String strIdCliente = request.getParameter("idCliente");
        if (strIdCliente != null && strIdCliente.trim().length() > 0) {
            idCliente = Integer.parseInt(strIdCliente);
        }
        
        Cliente cliente=new ClienteDaoJDBC().encontrar(new Cliente(idCliente));
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("/WEB-INF/paginas/cliente/editarCliente.jsp").forward(request, response);
        //response.sendRedirect("clientes.jsp");
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        //request.getRequestDispatcher("clientes.jsp").forward(request, response);    
        response.sendRedirect("clientes.jsp");
    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double suma = 0;
        for (Cliente cliente : clientes) {
            suma += cliente.getSaldo();
        }
        return suma;
    }
}
