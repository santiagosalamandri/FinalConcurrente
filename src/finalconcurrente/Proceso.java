/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class Proceso extends Thread{
    private String nombre;
    private DoubleMatrix transiciones;
    private Monitor monitor;
    public Proceso(String nombre, DoubleMatrix transiciones, Monitor monitor){
        this.nombre=nombre;
        this.transiciones=transiciones;
        this.monitor=monitor;
    }
    public void ejecutarAccion(DoubleMatrix disparoAutorizado){
        if(disparoAutorizado.isEmpty()){
            
        }
    }
    
    @Override
    public void run(){
        super.run();
        while(true){
            try{
                DoubleMatrix dispAutorizado;
                monitor.solicitarDisparo(transiciones);
                //dispAutorizado=monitor.solicitarDisparo(transiciones);
                //this.ejecutarAccion(dispAutorizado);
                
            }
           catch(Exception e){
               e.printStackTrace();
               e.getLocalizedMessage();
           }
        }
    }
}