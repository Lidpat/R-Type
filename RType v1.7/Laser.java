import java.awt.Color;

/**
 * L�ser disparado por la nave alidada. 
 * Se mueve de de izquierda a derecha y se elimina al salir de la pantalla.
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public class Laser extends Actor
{
    //nos dice si el laser est� visible
    private boolean visible;
   
    /**
     * 
     * @param x Posici�n del actor en el eje horizontal, en p�xeles
     * @param y Posicion del actor en el eje vertical, en p�xeles
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     */
    public Laser(int x, int y, int anchoPanel, int altoPanel)
    {
        super(x, y, anchoPanel, altoPanel);
        setTama�oFig(10,3); //(alto, ancho)
        setColor(Color.green);
        setVel(2); 
        visible = true; //inicialmente es visible
        setValorDesplazamiento(1,0); // se mueve hacia la derecha siempre, y nunca en vert.
    }
   
    /**
     * Comprobamos si el objeto esta dentro del panel
     * Si esta fuera visible = false;
     * 
     */
    @Override
    protected void dentroPantalla()
    {
        if(getX()> getAnchoPanel()){
            visible = false;
        }
    }
    
    /**
     * @return visible True si est� dentro de pantalla o False si est� fuera.
     */
    public boolean getVisible()
    {
        return visible;
    }
}
