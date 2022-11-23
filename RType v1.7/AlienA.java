
/**
 * La nave AlienA se mueve sólo de derecha a izquierda.
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public class AlienA extends Alien
{
  
    /**
     * Constructor for objects of class AlienA
     * 
     * @param x Posición del actor en el eje horizontal, en píxeles
     * @param y Posicion del actor en el eje vertical, en píxeles
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     * @param vel Factor velocidad que determina la rapidez de las naves Alien
     */
    public AlienA(int x, int y, int anchoPanel, int altoPanel, int vel)
    {
        super(x, y, anchoPanel, altoPanel, vel);
    }

    /*No es necesario sobreescribir ni añadir nada más. Con las caracaterísticas heredadas de sus
      superclases es suficiente.*/
}
