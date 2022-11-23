import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Nave aliada. Se puede mover en todas los sentidos dentro del tablero. Dispara láseres.
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public class Aliada extends Actor
{
    //creamos una colección donde se almacenarán los láseres disparados
    private ArrayList<Laser> laseres;
  
    /**
     * Constructor de la clase aliada Aliada, sobreescribe al de Actor
     * 
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     */
    public Aliada(int anchoPanel, int altoPanel)
    {
        /*la nave aliada se crea en el lado izquierdo del panel */
        super(0, (altoPanel/2), anchoPanel, altoPanel); 
        setTamañoFig(10,10); //alto y ancho de la figura
        setColor(Color.red);
        setValorDesplazamiento(0,0); //inicialmente está parada
        laseres = new ArrayList<Laser>();
        
    }
    
    
    /**
     * Comprueba que está dentro del panel y no le permite moverse más allá de los bordes del panel.
     */
    @Override
    protected void dentroPantalla()
    {
        int x = getX();
        int y = getY();
        
        // En el límite derecho e inferior tenemos en cuenta el tamaño de la figura
        if (x<0 || x>=(getAnchoPanel() - getAncho()) || y<0 || y>=(getAltoPanel() - getAlto())){
            
            /* Si entra en el if es porque al aplicar el último desplazamiento se sale de pantalla.
               Restamos el último desplazamiento para volver a la posición anterior a salirse*/
            setValorDesplazamiento(-getDx(), -getDy()); 
            aplicarDesplazamiento();
            
            //después de volver a su posición anterior dentro de pantalla, se queda quieto.
            setValorDesplazamiento(0,0);
        }
    }
    
    /**
    * Método de acceso a la colección de láseres
    *
    *@return arrayList con los laseres que hay en pantalla
    */
    public ArrayList<Laser> getLaseres()
    {
        return laseres;
    }
    
    /**
     * Genera un objeto de la clase Laser que comienza su movimiento desde el extremo derecho de la
     * nave aliada, aproximadamente de su centro verticalmente.
     * 
     */
    private void disparar()
    {
        laseres.add(new Laser(getX()+getAncho(), getY()+(getAlto()/2), getAnchoPanel(), getAltoPanel()));
    }
    
    /**
     * Actualiza la colección de laseres eliminando el seleccionado.
     * 
     * @param laser El objeto laser a elmininar 
     */
    public void eliminarLaser(Laser laser)
    {
         //controlamos que laser que no sea un objeto vacio,aunque es muy poco probable que esto ocurra, pero
         //por si alguien modificase el código.
         if (laser != null){
         laseres.remove(laser);
         }
         else{
            JOptionPane avisoError = new JOptionPane();
             avisoError.showMessageDialog(null, "No se ha podido eliminar el laser", "Aviso Aliada",JOptionPane.ERROR_MESSAGE);
         }
        
    }
    
    // A continuación están los métodos que manejan qué sucede cuando se llama a un keyListener
    
    /**
     * Maneja lo que hace aliada cuando se presiona las teclas elegidas.
     * 
     */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        //usamos los métodos de accesso getDy() y getDx() para que se pueda mover en las dos dimensiones 
        //simultaneamente.
        
        if(key == KeyEvent.VK_LEFT){setValorDesplazamiento(-1, getDy());}
        if(key == KeyEvent.VK_RIGHT){setValorDesplazamiento(1, getDy());}
        if(key == KeyEvent.VK_DOWN){setValorDesplazamiento(getDx(), 1);}
        if(key == KeyEvent.VK_UP){setValorDesplazamiento(getDx(), -1);}
        if(key == KeyEvent.VK_SPACE){disparar();}
        
    }
    
    /**
     * Devuelve a valores de reposo cuando se suelta la tecla.
     * 
     */
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){setValorDesplazamiento(0, getDy());}
        if(key == KeyEvent.VK_RIGHT){setValorDesplazamiento(0, getDy());}
        if(key == KeyEvent.VK_DOWN){setValorDesplazamiento(getDx(), 0);}
        if(key == KeyEvent.VK_UP){setValorDesplazamiento(getDx(), 0);}
    }
    
}
