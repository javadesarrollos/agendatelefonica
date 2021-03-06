package agenda;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Agenda extends JFrame implements ActionListener{
    //Variables Swing
    private JMenuBar barra;
    private JMenu menu1;
    private JMenuItem mi1, mi2, mi3;
    private JTextField camponuevonombre, camponuevotelefono, campobuscarnombre;
    private JLabel etiquetanuevonombre, etiquetanuevotelefono, imprimenombre, imprimetelefono, nombreprograma, nombreautor, numeroversion, dibusca;
    private JButton boton, botonnuevo, botonbusca;
    
    //Resto de Variables
    File archivo = new File("/home/yo/Escritorio");
    Formatter nuevoarchivo;
    Scanner x;

    public Agenda(){
    setLayout(null);
    
    //Texto de Introduccion
    nombreprograma = new JLabel("Agenda Telefonica");
        nombreprograma.setBounds(150,30,180,30);
        add(nombreprograma);
    nombreautor = new JLabel("Alfredo Bravo Cuero");
        nombreautor.setBounds(145,60,180,30);
        add(nombreautor);
    numeroversion = new JLabel("version 1.0");
        numeroversion.setBounds(170,90,180,30);
        add(numeroversion);
    
    //Menu Superior
    barra = new JMenuBar();
        setJMenuBar(barra);
    menu1 = new JMenu("Archivo");
        barra.add(menu1);
    mi1 = new JMenuItem("Nuevo");
        mi1.addActionListener(this);
        menu1.add(mi1);

    mi2 = new JMenuItem("Buscar");
        mi2.addActionListener(this);
        menu1.add(mi2);

    mi3 = new JMenuItem("Salir");
        mi3.addActionListener(this);
        menu1.add(mi3);
    }
    
    public void actionPerformed(ActionEvent e){
        Container f=this.getContentPane();
        if(e.getSource()==mi1){
            //Ocultar elementos Previos
            nombreprograma.setVisible(false);
            nombreautor.setVisible(false);
            numeroversion.setVisible(false);
            
            
            //Formulario Crear
            etiquetanuevonombre = new JLabel("Nuevo Nombre: ");
                etiquetanuevonombre.setBounds(10,0,180,30);
                add(etiquetanuevonombre);
                etiquetanuevonombre.setVisible(true);
                
            camponuevonombre = new JTextField();
                camponuevonombre.setBounds(200,0,180,30);
                add(camponuevonombre);
                camponuevonombre.setVisible(true);
            
            etiquetanuevotelefono = new JLabel("Nuevo Telefono");
                etiquetanuevotelefono.setBounds(10,50,180,30);
                add(etiquetanuevotelefono);
                etiquetanuevotelefono.setVisible(true);
            
           camponuevotelefono = new JTextField();
                camponuevotelefono.setBounds(200,50,180,30);
                add(camponuevotelefono);
                camponuevotelefono.setVisible(true);
                
            botonnuevo = new JButton("Crear");
                botonnuevo.setBounds(200,100,180,30);
                add(botonnuevo);
                botonnuevo.addActionListener(this);
                botonnuevo.setVisible(true);
                
         
        }
        if(e.getSource()==mi2){
            //Ocultar elementos Previos
            etiquetanuevonombre.setVisible(false);
            camponuevonombre.setVisible(false);
            etiquetanuevotelefono.setVisible(false);
            camponuevotelefono.setVisible(false);
            botonnuevo.setVisible(false);
            
               //Formulario Buscar
            imprimenombre = new JLabel("Buscar por Nombre: ");
                imprimenombre.setBounds(10,0,180,30);
                add(imprimenombre);
                imprimenombre.setVisible(true);
                
            campobuscarnombre = new JTextField();
                campobuscarnombre.setBounds(200,0,180,30);
                add(campobuscarnombre);
                campobuscarnombre.setVisible(true);
            
            botonbusca = new JButton("Buscar");
                botonbusca.setBounds(200,50,180,30);
                add(botonbusca);
                botonbusca.addActionListener(this);
                botonbusca.setVisible(true);
        }
        if(e.getSource()==mi3){
            System.exit(0);
        }
 
        if(e.getSource()==botonnuevo){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/agendatelefonica", "root", "lol12345");
                Statement estado = con.createStatement();
                estado.executeUpdate("INSERT INTO agenda VALUES ('2','"+camponuevonombre.getText()+"','"+camponuevotelefono.getText()+"')");
            }catch(SQLException ex){
                System.out.println("Error de Mysql");
            }catch(ClassNotFoundException err){
                err.printStackTrace();
            }catch(Exception err){
                System.out.println("Se ha encontrado un error inesperado: "+err.getMessage());
            }
        }
        
        if(e.getSource()==botonbusca){
            //Conectar a la Base de Datos
             try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/agendatelefonica", "root", "lol12345");
                Statement estado = con.createStatement();
                ResultSet resultado = estado.executeQuery("SEÑECT * FROM agenda WHERE nombre = '"+campobuscarnombre.getText() +"')");
                
                //Exportar el Resultado
                while(resultado.next()){
                    if(archivo.exists()){
                        if(archivo.canWrite()){
                            nuevoarchivo = new Formatter("/home/yo/Escritorio/archivo.txt");
                            nuevoarchivo.format("%s %s %s", resultado.getString("nombre"),resultado.getString("telefono"),"telefono");
                            nuevoarchivo.close();
                        }else{
                            System.out.println("El Archivo existe pero no puedo usarlo");
                        }                        
                        }else{
                            try{
                                nuevoarchivo = new Formatter("/home/yo/Escritorio/archivo.txt");
                                nuevoarchivo.format("%s %s %s", resultado.getString("nombre"),resultado.getString("telefono"),"telefono");
                                nuevoarchivo.close();
                            }catch(Exception errr){
                                System.out.println("Error: "+ errr);
                            }
                        }
                                   
                    }
                }catch(SQLException ex){
                    System.out.println("Error de Mysql");
                }catch(ClassNotFoundException err){
                    err.printStackTrace();
                }catch(Exception err){
                    System.out.println("Se ha encontrado un error inesperado: "+err.getMessage());
                }
            
        }
    }
    
    public static void main(String[] args) {
        Agenda ventana = new Agenda();
        ventana.setBounds(10,20,450,250);
        ventana.setVisible(true);
    }
    
}
