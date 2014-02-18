/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class Monitor {
    DoubleMatrix matrizIncidencia;
    DoubleMatrix tranSencibilizadas;
    DoubleMatrix marcaActual;
    ArrayList<Semaphore> listaSemaforos;
    
    DoubleMatrix transAutomaticas;
    
    public Monitor(String nombreArchivo){
        matrizIncidencia=this.leerMatriz(nombreArchivo);
        //tranSencibilizadas=new DoubleMatrix();
        marcaActual=this.leerMarca();
        //for(int i=0;i<matrizIncidencia.columns;i++){
        //    listaSemaforos.add(new Semaphore(1));
        //}
    }
    private DoubleMatrix leerMatriz(String nombreArchivo){
        BufferedReader in = null;
        int rows = 0;
        int columns = 0;
        double [][] matrix = null;
        try {
            //String filepath = args[0];
            String filepath = nombreArchivo;
            int lineNum = 0;

            int row=0;
            in = new BufferedReader(new FileReader(filepath));
            String line = null;
            while((line = in.readLine()) !=null) {
                lineNum++;
                if(lineNum==1) {
                    rows = Integer.parseInt(line);
                } else if(lineNum==2) {
                    columns = Integer.parseInt(line);
                    matrix = new double[rows][columns];
                } else {
                    String [] tokens = line.split(" ");
                    for (int j=0; j<tokens.length; j++) {                      
                        matrix[row][j] = Double.parseDouble(tokens[j]);
                    }
                    row++;
                }
            }
            try{
                if (in!=null) in.close();
                }
            catch(Exception exc){
                System.out.println("No pude cerrar el archivo abierto.");
                }
        } 
        
        catch (Exception ex) {
            System.out.println("No se pudo abrir el archivo o bien se exedieron los límites de la memoria");
            System.out.println(ex.getMessage());
        }
        return new DoubleMatrix(matrix) ;
    }
    private DoubleMatrix leerMarca(){
        return new DoubleMatrix();
    }
    
    synchronized public void solicitarDisparo(DoubleMatrix disparosRequeridos) throws Exception{
        //DoubleMatrix disparosPosibles=DoubleMatrix.zeros(1, matrizIncidencia.columns);
        
//-----------------------------------------------DETERMINACIÓN DE POSIBLES DISPAROS--------------------------------------------------------------------------------        
        disparosRequeridos=disparosRequeridos.or(transAutomaticas);
        LinkedList<DoubleMatrix> disparosPosibles = new LinkedList<DoubleMatrix>();
        if(disparosRequeridos.columns!=matrizIncidencia.columns)
            throw new Exception("El vector de disparos solicitados no tiene el tamanio correcto"+disparosRequeridos.columns);
        for(int i=0;i<disparosRequeridos.columns;i++){//Para cada una de las posiciones del vector de disparos requeridos...
            if(disparosRequeridos.get(i,0)==1){//Si se requiere el disparo de la transición (i)...
                double[] vectorDisparo=new double[disparosRequeridos.columns];//genero un vector lleno de (0.0)
                vectorDisparo[i]=1.0;//coloco un 1.0 en la posicion que corresponde a la transicion que deseo disparar.
                DoubleMatrix disparo= new DoubleMatrix(vectorDisparo);//genero un vector vertical de disparo con los valores antes cargados.
                //DoubleMatrix columnai=matrizIncidencia.getColumn(i);
                //System.out.println(columnai);
                DoubleMatrix marcaHipotetica=marcaActual.add(matrizIncidencia.mmul(disparo));//Se genera la marca hipotética de la red si se disparara esa transición.
                DoubleMatrix controlHabilitacion= marcaHipotetica.ge(0.0);//Se genera un vector vertical de control de marca mayor o igual a 0.0 por plaza.
                System.out.println(controlHabilitacion);
                //double[] vectorUNOS=new double[disparosRequeridos.columns];
                //Arrays.fill(vectorUNOS,1);
                DoubleMatrix patronUNOS=DoubleMatrix.ones(controlHabilitacion.rows, controlHabilitacion.columns);//Se genera un vector vertical lleno de 1.0 para realizar la comprobación de disparo permitido
                if(controlHabilitacion.equals(patronUNOS)){//Se compara el vector de control con el patron de UNOS para saber si es posible realizar el disparo.
                    System.out.println("Disparo válido para la transicion: "+i);
                    //disparosPosibles=disparosPosibles.add(disparo);
                    disparosPosibles.add(disparo);   
                }
            }
        }
        
//-----------------------------------------------PROTOCOLO DE DISPARO--------------------------------------------------------------------------------
        if(!disparosPosibles.isEmpty()){//Si existe al menos UN disparo posible para este proceso...
            marcaActual=marcaActual.add(matrizIncidencia.mmul(disparosPosibles.get(new Random().nextInt(disparosPosibles.size()))));
            //Se dispara uno aleatorio sobre la lista de disparos posibles para este proceso
            notify();//Se notifica el cambio de marca ya que este puede haber habilitado otros disparos para este u otros procesos.
        }
        else{//Si no es posible ningún disparo para este proceso se lo bloquea en la única cola de espera para ingreso al monitor.
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
        
    }
}