 import java.awt.*;

/**
 * Abstract class Actor - Escribe los métodos comunes a las clases Aliada, Láser y Alien y sus subclases,
 * 
 * @author Lidia Palacios
 * @version v1.7
 */

public abstract class Actor 
{
    private int x, y, dx, dy;
    private int ancho, alto; // tamaño figura que se dibuja
    private int anchoPanel, altoPanel; //tamaño del panel de juego
    private int vel; // factor por el que se multiplica el desplazamiento para aumentar la velocidad de las naves alien
    private Color color; // color de la figura


    /**
     * Constructor para la clase Alien.
     * 
     * @param x Posición del actor en el eje horizontal, en píxeles
     * @param y Posicion del actor en el eje vertical, en píxeles
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     */
    public Actor(int x, int y, int anchoPanel, int altoPanel)
    {
        this.x = x;
        this.y = y;
        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
        // incializamos la velocidad para poder escribir el método aplicarDesplazamiento() y que compile.
        // lo hacemos con valor 1 porque será el valor por defecto para Laser y Aliada.
        vel = 1;
    }
    
    // a continuación se declaran los métodos de acceso necesarios.
     /**
     * @return        posición x del objeto (en pixel)
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * @return        posición y del objeto (en pixel)
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * @return número de píxeles que se desplaza en horizontal el actor cuando se llame a mover().
     */
    protected int getDx()
    {
        return dx;
    }
    
    /**
     * @return número de píxeles que se desplaza en vertical el actor cuando se llame a mover().
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
     * @return el ancho del panel de juego en píxeles
     */
    protected int getAnchoPanel()
    {
        return anchoPanel;
    }
    
    /**
     * @return el alto del panle de juego en píxeles
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
     * @return la altura (en píxeles) de la figura que representa al actor
     */
    public int getAlto()
    {
        return alto;
    }
    
    
    /**
     * @return el ancho (en píxeles) de la figura que respresenta al actor
     */
    public int getAncho()
    {
        return ancho;
    }
    
    //a continuación están los métodos de acceso y modificación
    
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
     * @param ancho El ancho (en píxeles) de la figura que representa al actor por pantalla
     * @param alto La altura (en píxeles) de la figura que representa al actor por pantalla
     */
    protected void setTamañoFig(int ancho, int alto)
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
     * Establece cuantos píxeles, y en qué dirección, se desplaza el actor cuando se llame a mover.
     * 
     * @param dx Valor en píxels del deplazamiento en el eje horizontal.
     * @param dy Valor en píxels del deplazamiento en el eje vertical.
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
     * Mueve el objeto a la siguiente posición y comprueba si está en pantalla. 
     */
    public void mover()
    {
        aplicarDesplazamiento();
        dentroPantalla();
    }
    
    /**
     * Comprobamos si el objeto queda dentro de la pantalla al aplicar el desplazamiento.
     * En caso de estar fuera vuelve a colocarlo en pantalla según las caracaterísticas de cada actor.
     */
    protected abstract void dentroPantalla();
    
    /**
     * Crea un objeto Rectangle con los datos que la posición y tamaño del actor.
     * 
     * @return ojeto de tipo rectangle correspondiente al actor. 
     */
    public Rectangle getRect()
    {
        Rectangle r = new Rectangle(x, y, ancho, alto);
        return r;
    }
    
    
    
    
    
   
    
}
