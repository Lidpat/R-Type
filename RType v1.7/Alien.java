import java.awt.*;

/**
 * Superclase de las naves alien
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public abstract class Alien extends Actor
{
    
    /**
     * Constructor de Alien sobreescribe el de Actor
     * 
     * @param x Posici�n del actor en el eje horizontal, en p�xeles
     * @param y Posicion del actor en el eje vertical, en p�xeles
     * @param anchoPanel Ancho del panel de juego
     * @param altoPanel Alto del panel de juego
     * @param vel Factor velocidad que determina la rapidez de las naves Alien
     */
    public Alien(int x, int y, int anchoPanel, int altoPanel, int vel)
    {
        super(x, y, anchoPanel, altoPanel);
        setColor(Color.blue);
        setValorDesplazamiento(-1,0); // todos los Alien se mueven de dcha a izq.
        setVel(vel);
        setTama�oFig(10, 10); // ancho, alto de la figura nave Aliada
    }
    
      /**
     * Comprobamos si el objeto queda dentro de la pantalla al aplicar el desplazamiento.
     * Si se sale de pantalla, vuelve a introducirlo por el lado opuesto.
     */
    @Override
    protected void dentroPantalla()
    {
        //en nuestro caso (Alien) no debe darse x>anchoPanel.
        if(getX()<0){
            //establece los valores dx y dy de desplazamiento para colocarlo a la derecha de la pantalla 
            //(se divide por la velocidad porque se multiplica al aplicar el desplazamiento).
            setValorDesplazamiento((getAnchoPanel()/getVel()), 0);
            
            //aplicamos desplazamiento para colocarlo en su nueva ubicaci�n (a la derecha del panel)
            aplicarDesplazamiento(); 
            
            //volvemos a dar sus valores por defecto de desplazamiento
            setValorDesplazamiento(-1,0); 
        }
        else if(getY()<0){
            setValorDesplazamiento(-1, (getAltoPanel()/getVel()));
            aplicarDesplazamiento();
            setValorDesplazamiento(-1,-1);
        }
        else if(getY()>=getAltoPanel()){
            setValorDesplazamiento(-1, -(getAltoPanel()/getVel()));   
            aplicarDesplazamiento();
            setValorDesplazamiento(-1,1);
        }
              
        /* si ha llegado hasta aqu� es que no clumplia ninguna de las condiciones anteriores, por 
           tanto est� dentro de pantalla*/
    }
    
   
}
