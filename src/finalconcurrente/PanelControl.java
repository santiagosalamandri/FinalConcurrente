/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class PanelControl extends JPanel{
    private ArrayList<Componente> listaComponentes;
    public PanelControl(){//Lo podría hacer desde un archivo para ser más generico, por ahora vamos así.
        listaComponentes=new ArrayList<Componente>();
        listaComponentes.add(new Componente("03","0"));
        listaComponentes.add(new Componente("M3","0"));
        listaComponentes.add(new Componente("02","0"));
        listaComponentes.add(new Componente("M4","0"));
        listaComponentes.add(new Componente("O1","0"));
        listaComponentes.add(new Componente("R1","0"));
        listaComponentes.add(new Componente("",""));
        listaComponentes.add(new Componente("R2","0"));
        listaComponentes.add(new Componente("",""));
        listaComponentes.add(new Componente("R3","0"));
        listaComponentes.add(new Componente("I1","0"));
        listaComponentes.add(new Componente("M1","0"));
        listaComponentes.add(new Componente("I2","0"));
        listaComponentes.add(new Componente("M2","0"));
        listaComponentes.add(new Componente("I3","0"));
        Border borde=BorderFactory.createLineBorder(Color.WHITE,2);
        for(Componente c : listaComponentes){
            c.setBorder(borde);
            c.fijarEstado(new Integer(0));
            this.add(c);
        }
        listaComponentes.get(6).fijarEstado(-1);
        listaComponentes.get(8).fijarEstado(-1);
        
        this.setLayout(new GridLayout(3,5));
        
    }
    public void actualizar(DoubleMatrix marcaActual){
         //Me fijo si las marcas son mayores que cero entonces fijo estado de los componentes correposnidnetes
        /*
         for(int i=0, j=0;j<14;i++){
             if(i==6||i==8||i==12){
                 listaComponentes.get(i).fijarEstado(new Integer(0));
             }
             else{
                 listaComponentes.get(i).fijarEstado(new Integer((int)marcaActual.index(i, 0)));
                 j++;
             }
         }*/
        listaComponentes.get(0).fijarEstado(new Integer((int)marcaActual.get(1, 0)));
        listaComponentes.get(1).fijarEstado(new Integer((int)marcaActual.get(2, 0)));
        listaComponentes.get(2).fijarEstado(new Integer((int)marcaActual.get(3, 0)));
        listaComponentes.get(3).fijarEstado(new Integer((int)marcaActual.get(4, 0)));
        listaComponentes.get(4).fijarEstado(new Integer((int)marcaActual.get(5, 0)));
        listaComponentes.get(5).fijarEstado(new Integer((int)marcaActual.get(10, 0)));
        listaComponentes.get(7).fijarEstado(new Integer((int)marcaActual.get(12, 0)));
        listaComponentes.get(9).fijarEstado(new Integer((int)marcaActual.get(14, 0)));
        listaComponentes.get(10).fijarEstado(new Integer((int)marcaActual.get(20, 0)));
        listaComponentes.get(11).fijarEstado(new Integer((int)marcaActual.get(21, 0)));
        listaComponentes.get(12).fijarEstado(new Integer((int)marcaActual.get(22, 0)));
        listaComponentes.get(13).fijarEstado(new Integer((int)marcaActual.get(23, 0)));
        listaComponentes.get(14).fijarEstado(new Integer((int)marcaActual.get(24, 0)));
        
        for(Componente com : listaComponentes){
            System.out.println(com.obtenerEstado());
        }
    }  
}
