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
     * @param x Posición del actor en el eje horizontal, en píxeles
     * @param y Posicion del actor en el eje vertical, en píxeles
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
        setTamañoFig(10, 10); // ancho, alto de la figura nave Aliada
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
            
            //aplicamos desplazamiento para colocarlo en su nueva ubicación (a la derecha del panel)
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
              
        /* si ha llegado hasta aquí es que no clumplia ninguna de las condiciones anteriores, por 
           tanto está dentro de pantalla*/
    }
    
   
}
