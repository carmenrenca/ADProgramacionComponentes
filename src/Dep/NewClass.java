/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import Emp.Empleado;
import Emp.EmpleadoDAO;

/**
 *
 * @author Carmen
 */
public class NewClass {

    public static void main(String[] args) {
        DAOFactory factoryNeodaty = new NeodatisDAOFactory();
        DAOFactory factory = new SqlDbDAOFactory();
        DepartamentoDAO dpto = factory.getDepartamentoDAO();
        EmpleadoDAO emp = factory.getEmpleadoDAO();

        DepartamentoDAO dptoN = factoryNeodaty.getDepartamentoDAO();
        EmpleadoDAO empN = factoryNeodaty.getEmpleadoDAO();
        /////////////////////////////SQL/////////////////////////////////
        //INSERTAR EMPLEADO 
        Empleado empAux = new Empleado(6, "Garcia", "Diseñador", 12, "03/05/2000", 400.0, 15);
        emp.InsertarEmp(empAux);
          //System.out.println(emp.ConsultarEmp(2).getApellido());

        //MODIFICAR EMPLEADO
        Empleado empAux2 = new Empleado(6, "Rendon", "Desarrollador", 12, "03/05/2000", 400.0, 15);
        emp.ModificarEmp(15, empAux2);
        System.out.println("\n" + emp.ConsultarEmp(14).getApellido());

            //ELIMINAR EMPLEADO
        emp.EliminarEmp(15);

        //INSERTAR DEPARTAMETNO 
        Departamento dptoAux = new Departamento(7, "Testing", "Huesca");
        dpto.InsertarDep(dptoAux);

       //ELIMINAR DEPARTAMENTO 
        dpto.EliminarDep(7);

         //////////////////////////////////NEODATI////////////////////
        System.out.println("\nNEODATIS\n");

        //INSERTAR DEPARTAMENTO
        Departamento NdepartAux = new Departamento(10, "Maketing", "Huelva");
        dptoN.InsertarDep(NdepartAux);

        Empleado empAuxn = new Empleado(3, "Perez", "Analista", 11, "03/05/1999", 400.0, 29);
        empN.InsertarEmp(empAuxn);

        //MODIFICAR EMPLEADO
        Empleado empAux2n = new Empleado(3, "Rendon", "Analista", 10, "03/12/1999", 420.0, 29);
        empN.ModificarEmp(29, empAux2n);
        System.out.println("\nEMPLEADO MOIDICADO NUEVO APELLIDO: " + empN.ConsultarEmp(24).getApellido() + "\n");

            //ELIMINAR EMPLEADO
        empN.EliminarEmp(29);

         //INSERTAR DEPARTAMENTO SIN EMPLEADO A CARGO 
        Departamento NdepartAux2 = new Departamento(19, "Salesforce", "Sevilla");
        dptoN.InsertarDep(NdepartAux2);

          //ELIMINAR DEPARTAMENTO
        dptoN.EliminarDep(19);

    }
}
