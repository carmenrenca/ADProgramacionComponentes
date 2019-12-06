/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emp;

import java.sql.Date;

/**
 *
 * @author Carmen
 */
public class Empleado {
    int emp_no;
    String apellido;
    String oficio;
    int dir;
    String fecha_alt;
    Double  salario;
    int Dep_no;

    public Empleado (int Dep_no,String apellido, String oficio, int dir, String fecha_alt, Double salario,  int emp_no ){
        this.emp_no=emp_no;
        this.apellido=apellido;
        this.oficio=oficio;
        this.dir=dir;
        this.fecha_alt=fecha_alt;
        this.salario=salario;
        this.Dep_no=Dep_no;
    }
    
    public Empleado(){
        
    }
    public int getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getFecha_alt() {
        return fecha_alt;
    }

    public void setFecha_alt(String fecha_alt) {
        this.fecha_alt = fecha_alt;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public int getDep_no() {
        return Dep_no;
    }

    public void setDep_no(int Dep_no) {
        this.Dep_no = Dep_no;
    }

    
    
    
}
