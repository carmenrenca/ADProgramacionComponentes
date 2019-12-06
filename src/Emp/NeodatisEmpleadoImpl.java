/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emp;

import Dep.Departamento;
import Dep.NeodatisDAOFactory;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author Carmen
 */
public class NeodatisEmpleadoImpl implements EmpleadoDAO{
        
    static ODB bd;

    public NeodatisEmpleadoImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }
    @Override
    public boolean InsertarEmp(Empleado emp) {
  
   Empleado e=   this.ConsultarEmp(emp.getEmp_no());

   IQuery Departamentos = new CriteriaQuery(Departamento.class, Where.equal("deptno", emp.getDep_no()));
         Objects<Departamento> objetosDepar = bd.getObjects(Departamentos);
   
   
        if(!objetosDepar.isEmpty() && emp.getDir()!=0 ){
            
            if(e.getApellido().equals("No existe")){
                
                if(emp.getDir()!=0 && objetosDepar!=null ){
                    bd.store(emp);
        bd.commit();
        System.out.printf("EMPLEADO INSERTADO "+ emp.getEmp_no());
        return true;
                }else{
                    System.out.print("No existe ese director o el departamento");
                    return false;
                }
                 
            }else{
               System.out.print(("Ya exite el empleado "+emp.getEmp_no()));
               return false;
            }
            
          
        }else{
            System.out.print("Error al insertar, ese director o departamento no existe");
            return false;
        }
    
    }

    @Override
    public boolean EliminarEmp(int empno) {
               boolean valor = false;
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", empno));
        Objects<Empleado> objetos = bd.getObjects(query);
       
        try {
            if(objetos.isEmpty()){
                Empleado emp = (Empleado) objetos.getFirst();
            bd.delete(emp);
            System.out.print("empleado "+emp.getEmp_no()+" ha sido eliminado\n");
            bd.commit();
            valor = true;
            }else{
            System.out.printf("No se puede eliminar un empleado con subempleados");
        }
            
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Empleado a eliminar: %d No existe%n", empno);
        }

        return valor;
    }

    @Override
    public boolean ModificarEmp(int empno, Empleado emp) {
                boolean valor = false;
      
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", empno));
        Objects<Empleado> objetos = bd.getObjects(query);
           IQuery queryDepartamentos = new CriteriaQuery(Departamento.class, Where.equal("deptno", emp.getDep_no()));
        Objects<Departamento> departa = bd.getObjects(queryDepartamentos);
        try {
           if(emp.getDir()!=0 && !departa.isEmpty()){
                 Empleado e = (Empleado) objetos.getFirst();
            e.setEmp_no(emp.getEmp_no());
             e.setDep_no(emp.getDep_no());
             e.setApellido(emp.getApellido());
             e.setDir(emp.getDir());
             e.setFecha_alt(emp.getFecha_alt());
             e.setOficio((emp.getOficio()));
            bd.store(e); // actualiza el objeto 
            valor = true;
            bd.commit();
           }else{
          System.out.printf("No existe el departamento o director");

           }
           
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Empleado: %d No existe%n", empno);
        }

        return valor;
    }

   
    @Override

        public Empleado ConsultarEmp(int Emp) {
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", Emp));
        Objects<Empleado> objetos = bd.getObjects(query);
        Empleado e = new Empleado();
        if (objetos != null) {
            try {
                e = (Empleado) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
              
                e.setApellido("No existe");
                e.setDep_no(Emp);
                e.setDir(0);
            }
        }
        return e;
    }
}
