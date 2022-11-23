import java.util.Random;
import java.awt.*;

/**
 * Las naves tipo AlienB se mueven de derecha a izquierda y aleatoriamente en vertical.
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public class AlienB extends Alien
{
    private int i; //contador para movimiento aleatorio 
    
    /**
     * Constructor for objects of class AlienB
     * 
     * @param x Posición del actor en el eje horizontal, en píxeles
     * @param y Posicion del actor en el eje vertical, en píxeles
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     * @param vel Factor velocidad que determina la rapidez de las naves Alien
     */
    public AlienB(int x, int y, int anchoPanel, int altoPanel, int vel)
    {
        super(x, y, anchoPanel, altoPanel, vel);
        //inicializamos el contador a 0, así la primera vez que se llame al método mover
        //este llamará a setMovimientoVertical para comenzar aplicando movimiento vertical aleatorio.
        i = 0; 
    }

 
    /**
     * Asigna el sentido del desplazamiento en el eje 'y' de forma aleatoria
     */
    private void setMovimientoVertical()
    {
        Random random = new Random();
        if (random.nextInt(2) == 0){
            setValorDesplazamiento(getDx(), -1);
        }
        else{
            setValorDesplazamiento(getDx(), 1);
        }
    }
    
    /**
     * Sobreescribe el método para aplicar el movimiento aleatorio
     */
    @Override
    public void mover()
    {
        if(i%50 == 0){  //el número entre el que se divide (en este caso 50)indica cada cuantas llamadas se pregunta cambio dirección
            setMovimientoVertical(); //reasigna valor para la dirección de desplazamiento vertical.
        }
        super.mover();
        i++; // incrementa el valor cada vez que se llame a mover (es decir, cada golpe de timer).
    }

}
    