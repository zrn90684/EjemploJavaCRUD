package datos;

//TODO agregar la interfaz

import dominio.Cliente;
import java.sql.*;
import java.util.*;

public class ClienteDaoJDBC {
    //TODO poder pasar por referencia estas queries
    public static final String SQL_SELECT = "SELECT id_cliente, nombre, apellido"
            + ", email , telefono, saldo"
            + " FROM cliente";
    
    //variavlw que busca por id SELECT_BY_ID
    public static final String SQL_SELECT_CLIENTE = "SELECT nombre, apellido"
            + ", email , telefono, saldo"
            + " FROM cliente WHERE id_cliente = ?";
    
    //Query para insert recordar usar ? para parametros de preparedstatement
    public static final String SQL_INSERT = "INSERT INTO cliente (nombre, apellido"
            + ", email , telefono, saldo)"
            + " VALUES (?,?,?,?,?)";
    
    //sql update
    public static final String SQL_UPDATE = "UPDATE cliente SET nombre = ?, apellido = ?"
            + ", email =?, telefono = ?, saldo = ?"
            + " WHERE id_cliente = ?";
    //sql delete
    public static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente = ?";
    
    public List<Cliente> listar(){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");
                cliente = new Cliente(idCliente,nombre,apellido,email, telefono,saldo);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(con);
        }
        return clientes;
    }
    
    public Cliente encontrar(Cliente cliente){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;        
        
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT_CLIENTE);
            stmt.setInt(1, cliente.getIdCliente());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                //int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");
                
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setEmail(email);
                cliente.setTelefono(telefono);
                cliente.setSaldo(saldo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(con);
        }
        return cliente;
    }
    
    //insertar, actualizar, eliminar todos reciben un cliente
    public int insertar(Cliente cliente){
        Connection con = null;
        PreparedStatement stmt = null;
        int resultado=0;
        
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_INSERT);
            //stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            resultado = stmt.executeUpdate();            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(stmt);
            Conexion.close(con);
        }
        return resultado;
    }
    
    public int actualizar(Cliente cliente){
        Connection con = null;
        PreparedStatement stmt = null;
        int resultado=0;
        
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_UPDATE);
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            stmt.setInt(6, cliente.getIdCliente());
            resultado = stmt.executeUpdate();            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(stmt);
            Conexion.close(con);
        }
        return resultado;
    }
    
        public int eliminar(Cliente cliente){
        Connection con = null;
        PreparedStatement stmt = null;
        int resultado=0;
        
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_DELETE);
            stmt.setInt(1, cliente.getIdCliente());
            resultado = stmt.executeUpdate();            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(stmt);
            Conexion.close(con);
        }
        return resultado;
    }

}
