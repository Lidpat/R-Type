import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

/**
 * Controla la ejecución del juego.
 * 
 * @author Lidia Palacios
 * @version v1.7
 */
public class PanelJuego extends JPanel implements ActionListener
{
    private static final int ANCHO_PANEL = 600, ALTO_PANEL = 400;
    private ArrayList<Alien> aliens;
    private ArrayList<Actor> actores;
    private Aliada aliada;
    private Timer timer;
    

    /**
     * @param numAliens Número de naves alienigenas.Debe ser mayor de cero.
     * @param vel Factor velocidad del movimiento de los alien (debe ser mayor que cero).
     */
    public PanelJuego(int numAliens, int vel)
    {
        aliens = new ArrayList<Alien>();
        actores = new ArrayList<Actor>();
        aliada = new Aliada(ANCHO_PANEL, ALTO_PANEL);
        
        //inicializamos la coleción aliens con los parámetros pasados al constructor.
        cargarAliens(numAliens, vel);
        //inicializamos la colección actores.
        cargarActores();
        
        setSize(ANCHO_PANEL, ALTO_PANEL);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        timer = new Timer(30, this);
        timer.start();
        
    }

    /**
     * Sobreescribe el método paint(Graphic g)para dibujar los actores en el panel.
     */
    @Override
    public void paint (Graphics g)
    {
        super.paint(g);
        
        //recorremos la colección de actores para dibujarlos en pantalla
        for (Actor actor: actores){
            g.setColor(actor.getColor());
            g.fillRect(actor.getX(), actor.getY(), actor.getAncho(), actor.getAlto());
                  
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    /**
     * Inicializa el ArrayList de los aliens con los parámetros correspondientes al nivel escogido.
     * Aproximadamente la mitad de los alien creados son tipo A y el resto, tipo B
     * 
     * @param numAliens Numero de aliens
     * @param vel Factor velocidad de los aliens
     */
    private void cargarAliens(int numAliens, int vel)
    {
        //definimos cuantos aliens van a ser de tipo A y cuántos de tipo B
        int numAliensB = Math.abs(numAliens/2);
        int numAliensA = numAliens - numAliensB;
        for(int i=0 ; i<numAliensA ; i++){
            aliens.add(new AlienA( ANCHO_PANEL+40*i, (ALTO_PANEL/3)*(1+i%2), ANCHO_PANEL, ALTO_PANEL, vel));
        }
        
        for(int i=0; i< numAliensB ; i++){
            aliens.add(new AlienB( ANCHO_PANEL+20+40*i, ALTO_PANEL/6 + (ALTO_PANEL/3)*(i%3) , ANCHO_PANEL, ALTO_PANEL, vel));
        }
    }
    
    /**
     * Inicializa el arraylist actores, que contendrá todos los elementos para luego pintarlos
     * y trabajar con ellos
     */
    private void cargarActores()
    {
        //añadimos la nave aliada a la colección de actores
        actores.add(aliada);
        //añadimos la colección de alines a la de actores
        actores.addAll(aliens);
        //añadimos los láseres disparados y en pantalla, si los hay, a la coleción de actores
        actores.addAll(aliada.getLaseres());
    }
    
    /**
     * Limpia el arrayList de actores y vuelve a cargarlo con los nuevos elementos
     */
    private void actualizarActores()
    {
        //vaciamos la colección de actores para que esté lista para guardar los nuevos elementos.
        actores.clear();
        //eliminamos los laser que estén fuera de la pantalla
        eliminarLaseres();
        //añadimos los elementos actualmente en pantalla a la colección de actores.
        cargarActores();
    }
    
    /**
     * Elimina los laser que estén fuera de pantalla
     */
    private void eliminarLaseres()
    {
        // creo un arraylist distinto porque si no estaría trabajando con el mismo y no podría modificarlo
        ArrayList<Laser> laseresAux = new ArrayList<Laser>();
        laseresAux.addAll(aliada.getLaseres()); 
        for(Laser laser:laseresAux){
            if(!laser.getVisible()){
                aliada.eliminarLaser(laser);
            }
        }
    }
    
    /**
     * Controla si se ha producido una colisión entre laseres y aliens o entre
     * la aliada y un alien y en consecuencia realiza la acción que corresponda.
     */
    private void colision(){
        //Pasamos los valores de Alien a otra colección para poder borrar los elementos
        // que colisionan.
        ArrayList<Alien> aliensAux = new ArrayList<Alien>();
        aliensAux.addAll(aliens);
        // recorremos todo la colección de Aliens
        for(Alien alien:aliensAux){
            //comprobamos si colisiona con Aliada => fin juego
            Rectangle rAlien = new Rectangle();
            rAlien = alien.getRect();
            Rectangle rAliada = new Rectangle();
            rAliada = aliada.getRect();

            if(rAliada.intersects(rAlien)){
                //creamos un mensaje para avisar de que se ha perdido y es el final del juego
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "COLISION ALIADA - ALIEN: ¡¡Fin del Juego!!", "R-Type", JOptionPane.PLAIN_MESSAGE);
                //sentencia para cerrar el programa
                System.exit(0);
            }
            
            //comprobamos si colisiona alien con algún laser
            //trabajo con una colección de láseres auxiliar para poder modificar la buena
            ArrayList<Laser> laseresAux = new ArrayList<Laser>();
            laseresAux.addAll(aliada.getLaseres());
            for(Laser laser:laseresAux){
                if(laser.getRect().intersects(alien.getRect())){
                    //en caso de colisión elimina los objetos laser y alien implicados
                    aliada.eliminarLaser(laser);
                    aliens.remove(alien);
                }
            }
            
            //comprobamos si después de las colisiones queda algún Alien en
            //pantada, sino, partida ganada, volver a jugar.
            if (aliens.size()== 0)
            {
                //Creamos mensaje de salida
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "No quedan aliens, ¡¡HAS GANADO!!", "R-Type", JOptionPane.PLAIN_MESSAGE);
                
                //cerramos el panel de juego.
                this.getRootPane().getParent().setVisible(false);
                //paramos el timer
                timer.stop(); 
            }
            else if (aliens.size()<0){
                // se supone q sería imposible llegar a esto.
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "Error: Número de aliens negativo", "Error PanelJuego", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    
    /**
    * @return Timer
    */
    public Timer getTimer()
    {
     return timer;
     }
    
    /**
     * Sobreescribe el método actionPerformed de la clase Timer.
     * Controla lo que sucede por cada golpe de timer
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //nos deshacemos de los actores que ya no están en juego
        actualizarActores();
        //actualizamos las posiciones de los elementos
        for(Actor actor:actores){
            actor.mover();
        }
        //comprobamos las colisiones
        colision();
        //se dibuja la nueva situación
        repaint();
        
    }
    
    //Clase interna para los keyListeners
    private class TAdapter extends KeyAdapter
    {
        public void keyReleased (KeyEvent e)
        {
            aliada.keyReleased(e);
            aliada.mover();
        }
        
        public void keyPressed(KeyEvent e)
        {
            aliada.keyPressed(e);
            aliada.mover();
        }
    }
    
}
