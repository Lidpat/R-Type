import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Ventana de Juego.
 * Lanza el juego.
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public class RType extends JDialog
{
    private PanelJuego juego;
    
    /**
     * Establece parámetros para comenzar la partida. Tanto numAliens y vel debeben ser mayor
     * de cero.
     * 
     * @param numAliens Numero de aliens
     * @param vel Factor velocidad de los alien
     */
    public RType(int numAliens, int vel)
    {
       setTitle("RType");
       setResizable(false);
       juego = new PanelJuego(numAliens, vel);
//        add(juego);
//        setSize(juego.getWidth()+15, juego.getHeight()+37);
       getContentPane().setPreferredSize(new Dimension(juego.getWidth(),juego.getHeight()));
       pack();
       setContentPane(juego);
         
       setModal(true);
       setVisible(true);
              
        //las siguientes líneas controlan que si el usuario cierra la ventana de juego
        //se pare el juego y devuelva el control a la pantalla de inicio.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowDeactivated(WindowEvent we)
            {
                //para el timer de PanelJuego
                juego.getTimer().stop();
                remove(juego);
            }
        });
    }

   
    public static void main(String[] args){
        PantallaInicio p = new PantallaInicio();
    }
    
    
}