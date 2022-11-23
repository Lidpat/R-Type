 import java.awt.*;

/**
 * Abstract class Actor - Escribe los m�todos comunes a las clases Aliada, L�ser y Alien y sus subclases,
 * 
 * @author Lidia Palacios
 * @version v1.7
 */

public abstract class Actor 
{
    private int x, y, dx, dy;
    private int ancho, alto; // tama�o figura que se dibuja
    private int anchoPanel, altoPanel; //tama�o del panel de juego
    private int vel; // factor por el que se multiplica el desplazamiento para aumentar la velocidad de las naves alien
    private Color color; // color de la figura


    /**
     * Constructor para la clase Alien.
     * 
     * @param x Posici�n del actor en el eje horizontal, en p�xeles
     * @param y Posicion del actor en el eje vertical, en p�xeles
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     */
    public Actor(int x, int y, int anchoPanel, int altoPanel)
    {
        this.x = x;
        this.y = y;
        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
        // incializamos la velocidad para poder escribir el m�todo aplicarDesplazamiento() y que compile.
        // lo hacemos con valor 1 porque ser� el valor por defecto para Laser y Aliada.
        vel = 1;
    }
    
    // a continuaci�n se declaran los m�todos de acceso necesarios.
     /**
     * @return        posici�n x del objeto (en pixel)
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * @return        posici�n y del objeto (en pixel)
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * @return n�mero de p�xeles que se desplaza en horizontal el actor cuando se llame a mover().
     */
    protected int getDx()
    {
        return dx;
    }
    
    /**
     * @return n�mero de p�xeles que se desplaza en vertical el actor cuando se llame a mover().
     */
    protected int getDy()
    {
        return dy;
    }
    
    /**
     * @return el valor del factor velocidad
     */
    protected int getVel()
    {
        return vel;
    }
    
    /**
     * @return el ancho del panel de juego en p�xeles
     */
    protected int getAnchoPanel()
    {
        return anchoPanel;
    }
    
    /**
     * @return el alto del panle de juego en p�xeles
     */
    protected int getAltoPanel()
    {
        return altoPanel;
    }
    
    /**
     * @return el color asociado al actor
     */
    public Color getColor()
    {
        return color;
    }
    
    
    /**
     * @return la altura (en p�xeles) de la figura que representa al actor
     */
    public int getAlto()
    {
        return alto;
    }
    
    
    /**
     * @return el ancho (en p�xeles) de la figura que respresenta al actor
     */
    public int getAncho()
    {
        return ancho;
    }
    
    //a continuaci�n est�n los m�todos de acceso y modificaci�n
    
    /**
     * @param color El color que va a representar al actor
     */
    protected void setColor(Color color)
    {
        this.color = color;
    }
    
    /**
     * Establece los valores ancho y alto de la figura.
     * 
     * @param ancho El ancho (en p�xeles) de la figura que representa al actor por pantalla
     * @param alto La altura (en p�xeles) de la figura que representa al actor por pantalla
     */
    protected void setTama�oFig(int ancho, int alto)
    {
        this.ancho = ancho;
        this.alto = alto;
    }
    
    /**
     * Establece el valor del factor velocidad
     * 
     * @param vel Valor del factor velocidad
     */
    protected void setVel(int vel)
    {
        this.vel = vel;
    }
    
    /**
     * Establece cuantos p�xeles, y en qu� direcci�n, se desplaza el actor cuando se llame a mover.
     * 
     * @param dx Valor en p�xels del deplazamiento en el eje horizontal.
     * @param dy Valor en p�xels del deplazamiento en el eje vertical.
     */
    protected void setValorDesplazamiento(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Incrementa el valor de las posiciones 'x' e 'y' el valor correspondiente al desplazamiento. 
     */
    protected void aplicarDesplazamiento()
    {
        x += dx * vel;
        y += dy * vel;
    }
    
    /**
     * Mueve el objeto a la siguiente posici�n y comprueba si est� en pantalla. 
     */
    public void mover()
    {
        aplicarDesplazamiento();
        dentroPantalla();
    }
    
    /**
     * Comprobamos si el objeto queda dentro de la pantalla al aplicar el desplazamiento.
     * En caso de estar fuera vuelve a colocarlo en pantalla seg�n las caracater�sticas de cada actor.
     */
    protected abstract void dentroPantalla();
    
    /**
     * Crea un objeto Rectangle con los datos que la posici�n y tama�o del actor.
     * 
     * @return ojeto de tipo rectangle correspondiente al actor. 
     */
    public Rectangle getRect()
    {
        Rectangle r = new Rectangle(x, y, ancho, alto);
        return r;
    }
    
    
    
    
    
   
    
}
