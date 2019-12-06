/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author
 */
public class SqlDbDepartamentoImpl implements DepartamentoDAO {

    Connection conexion;

    public SqlDbDepartamentoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    public boolean InsertarDep(Departamento dep) {

        boolean valor = false;
        int NDDperatamento = 0;
        System.out.print(dep.deptno + " " + dep.getDnombre());
        String sql = "INSERT INTO departamentos VALUES(?, ?, ?)";
        String existeDepartamento = "SELECT EXISTS(SELECT * FROM departamentos WHERE deptno=" + dep.getDeptno() + ")";
        PreparedStatement exitDepartamento;

        PreparedStatement sentencia;
        try {

            exitDepartamento = conexion.prepareStatement(existeDepartamento);
            ResultSet rs = exitDepartamento.executeQuery();
            if (rs.next()) {
                NDDperatamento = rs.getInt(1);
            }
            exitDepartamento.close();

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dep.getDeptno());
            sentencia.setString(2, dep.getDnombre());
            sentencia.setString(3, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0 && NDDperatamento == 0) {
                valor = true;
                System.out.printf("Departamento %d insertado%n", dep.getDeptno());
            } else {
                System.out.printf("No se puede insertar ese departamento\n");
            }
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        boolean valor = false;

        String subembpleados = "SELECT COUNT(*) FROM empleado WHERE deptno=" + deptno;
        String sql = "DELETE FROM departamentos WHERE deptno = ? ";
        PreparedStatement sentencia;
        PreparedStatement cuentasubempleado;
        try {

            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            int n = 0;
            cuentasubempleado = conexion.prepareStatement(subembpleados);
            ResultSet rs = cuentasubempleado.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
            if (n == 0) {
                int filas = sentencia.executeUpdate();
                if (filas > 0) {
                    valor = true;
                    System.out.printf("Departamento %d eliminado%n", deptno);
                }
            } else {
                System.out.printf("No puede eliminar ese departamento\n");
            }

            //System.out.printf("Filas eliminadas: %d%n", filas);
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
            System.out.printf("No existe ese departamento\n");
        }
        return valor;
    }

    @Override
    public boolean ModificarDep(int num, Departamento dep) {
        boolean valor = false;
        String sql = "UPDATE departamentos SET dnombre= ?, loc = ? WHERE deptno = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(3, num);
            sentencia.setString(1, dep.getDnombre());
            sentencia.setString(2, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d modificado%n", num + "\n");
            }
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        String sql = "SELECT deptno, dnombre, loc FROM departamentos WHERE deptno =  ?";
        PreparedStatement sentencia;
        Departamento dep = new Departamento();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                dep.setDeptno(rs.getInt("deptno"));
                dep.setDnombre(rs.getString("dnombre"));
                dep.setLoc(rs.getString("loc"));
            } else {
                System.out.printf("Departamento: %d No existe%n", deptno + "\n");
            }

            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return dep;
    }

    private void MensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }
}
