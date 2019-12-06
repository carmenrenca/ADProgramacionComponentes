/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emp;

import Dep.Departamento;
import Dep.SqlDbDAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author Carmen
 */
public class SqlDbEmpleadoImp implements EmpleadoDAO {

    Connection conexion;

    public SqlDbEmpleadoImp() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarEmp(Empleado emp) {
        int NDEmpleado = 0;
        int NDepar = 0;
        int NDepartamento = 0;
        boolean valor = false;
        String sql = "INSERT INTO empleado VALUES(?,?,?,?,?,?,?)";

        String existeempleado = "SELECT COUNT(*) FROM empleado WHERE emp_no=" + emp.getEmp_no();
        String exitDepartamento = "SELECT EXISTS(SELECT * FROM departamentos WHERE deptno=" + emp.getDep_no() + ")";
        PreparedStatement sentencia;
        PreparedStatement exitEmpleado;
        PreparedStatement exitDepartemento;

        try {

            exitDepartemento = conexion.prepareStatement(exitDepartamento);
            ResultSet rsD = exitDepartemento.executeQuery();
            exitEmpleado = conexion.prepareStatement(existeempleado);
            ResultSet rs = exitEmpleado.executeQuery();

            if (rs.next()) {
                NDEmpleado = rs.getInt(1);
            }
            if (rsD.next()) {
                NDepar = rsD.getInt(1);
            }
            exitEmpleado.close();
            exitDepartemento.close();
            System.out.print(NDEmpleado + "Empleado");
            System.out.print(emp.getDir() + "Director");
            System.out.print(emp.getDep_no() + "departamento");

            if (NDEmpleado == 0 && emp.getDir() != 0 && NDepar != 0) {
                // NDEmpleado.close();
                sentencia = conexion.prepareStatement(sql);
                sentencia.setInt(1, emp.getDep_no());
                sentencia.setString(2, emp.getApellido());
                sentencia.setInt(4, emp.getDir());
                sentencia.setString(3, emp.getOficio());
                sentencia.setString(5, emp.getFecha_alt());
                sentencia.setDouble(6, emp.getSalario());
                sentencia.setInt(7, emp.getEmp_no());

                int filas = sentencia.executeUpdate();
                //System.out.printf("Filas insertadas: %d%n", filas);
                if (filas > 0) {
                    valor = true;
                    System.out.printf("\nEmpleado %d insertado%n", emp.getEmp_no());
                }
                sentencia.close();
            } else {
                System.out.print("\nNo se puede insertar ese empleado");
            }

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean EliminarEmp(int empno) {
        boolean valor = false;
        int n = 0;
        int nE = 0;
        String exitDepartamento = "SELECT EXISTS(SELECT * FROM empleado WHERE emp_no=" + empno + ")";

        String subembpleados = "SELECT COUNT(*) FROM empleado WHERE dir=" + empno;
        String sql = "DELETE FROM empleado WHERE emp_no = ? ";
        PreparedStatement sentencia;
        PreparedStatement cuentasubempleado;
        PreparedStatement exitEmpleado;
        try {

            cuentasubempleado = conexion.prepareStatement(subembpleados);
            exitEmpleado = conexion.prepareStatement(exitDepartamento);
            ResultSet rs = cuentasubempleado.executeQuery();

            if (rs.next()) {
                n = rs.getInt(1);
            }
            rs = exitEmpleado.executeQuery();
            if (rs.next()) {
                nE = rs.getInt(1);
            }
            if (n == 0) {
                if (nE != 0) {

                    sentencia = conexion.prepareStatement(sql);
                    sentencia.setInt(1, empno);
                    int filas = sentencia.executeUpdate();
                    //System.out.printf("Filas eliminadas: %d%n", filas);
                    if (filas > 0) {
                        valor = true;
                        System.out.printf("\nEmpleado eliminado\n");
                    }
                    sentencia.close();
                } else {
                    System.out.printf("\n No existe ese empleado");

                }
            } else {
                System.out.print("\nNo es posible eliminar, el empleado tiene subempleados");
            }
            cuentasubempleado.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean ModificarEmp(int empno, Empleado emp) {
        int NDepartamento = 0;

        boolean valor = false;
        String sql = "UPDATE empleado SET emp_no= ?, apellido = ?, oficio= ?, dir= ?, fecha_alt= ?, salario= ?, deptno= ? WHERE emp_no = ? ";
        String exitDepartamento = "SELECT EXISTS(SELECT * FROM departamentos WHERE deptno=" + emp.getDep_no() + ")";

        PreparedStatement sentencia;
        PreparedStatement exitDepartemento;

        try {
            exitDepartemento = conexion.prepareStatement(exitDepartamento);
            ResultSet rsD = exitDepartemento.executeQuery();
            if (rsD.next()) {
                NDepartamento = rsD.getInt(1);
            }
            if (NDepartamento != 0 && emp.getDir() != 0) {
                sentencia = conexion.prepareStatement(sql);
                sentencia.setInt(7, emp.getDep_no());
                sentencia.setInt(8, emp.getEmp_no());
                sentencia.setInt(1, emp.getEmp_no());
                sentencia.setString(2, emp.getApellido());
                sentencia.setString(3, emp.getOficio());
                sentencia.setInt(4, emp.getDir());
                sentencia.setString(5, emp.getFecha_alt());
                sentencia.setDouble(6, emp.getSalario());

                int filas = sentencia.executeUpdate();
                //System.out.printf("Filas modificadas: %d%n", filas);
                if (filas > 0) {
                    valor = true;
                    System.out.printf("\nEmpleado %d modificado%n", empno);
                }
                sentencia.close();
            } else {
                System.out.print(("\nNo es posible modifica este empleado"));
            }

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;

    }

    private void MensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

    @Override
    public Empleado ConsultarEmp(int empno) {
        String sql = "SELECT emp_no, apellido, oficio, dir , fecha_alt, salario , deptno FROM Empleado WHERE emp_no =  ?";
        PreparedStatement sentencia;
        Empleado emp = new Empleado();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, empno);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                emp.setEmp_no(rs.getInt("emp_no"));
                emp.setDep_no(rs.getInt("deptno"));
                emp.setApellido(rs.getString("apellido"));
                emp.setDir(rs.getInt("dir"));
                emp.setFecha_alt(rs.getString("fecha_alt"));
                emp.setOficio(rs.getString("oficio"));

            } else {
                System.out.printf("Empleado: %d No existe%n", empno);
            }

            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return emp;
    }
}
