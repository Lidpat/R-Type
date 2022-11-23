import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

/**
 * Controla la ejecuci�n del juego.
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
     * @param numAliens N�mero de naves alienigenas.Debe ser mayor de cero.
     * @param vel Factor velocidad del movimiento de los alien (debe ser mayor que cero).
     */
    public PanelJuego(int numAliens, int vel)
    {
        aliens = new ArrayList<Alien>();
        actores = new ArrayList<Actor>();
        aliada = new Aliada(ANCHO_PANEL, ALTO_PANEL);
        
        //inicializamos la coleci�n aliens con los par�metros pasados al constructor.
        cargarAliens(numAliens, vel);
        //inicializamos la colecci�n actores.
        cargarActores();
        
        setSize(ANCHO_PANEL, ALTO_PANEL);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        timer = new Timer(30, this);
        timer.start();
        
    }

    /**
     * Sobreescribe el m�todo paint(Graphic g)para dibujar los actores en el panel.
     */
    @Override
    public void paint (Graphics g)
    {
        super.paint(g);
        
        //recorremos la colecci�n de actores para dibujarlos en pantalla
        for (Actor actor: actores){
            g.setColor(actor.getColor());
            g.fillRect(actor.getX(), actor.getY(), actor.getAncho(), actor.getAlto());
                  
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    /**
     * Inicializa el ArrayList de los aliens con los par�metros correspondientes al nivel escogido.
     * Aproximadamente la mitad de los alien creados son tipo A y el resto, tipo B
     * 
     * @param numAliens Numero de aliens
     * @param vel Factor velocidad de los aliens
     */
    private void cargarAliens(int numAliens, int vel)
    {
        //definimos cuantos aliens van a ser de tipo A y cu�ntos de tipo B
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
     * Inicializa el arraylist actores, que contendr� todos los elementos para luego pintarlos
     * y trabajar con ellos
     */
    private void cargarActores()
    {
        //a�adimos la nave aliada a la colecci�n de actores
        actores.add(aliada);
        //a�adimos la colecci�n de alines a la de actores
        actores.addAll(aliens);
        //a�adimos los l�seres disparados y en pantalla, si los hay, a la coleci�n de actores
        actores.addAll(aliada.getLaseres());
    }
    
    /**
     * Limpia el arrayList de actores y vuelve a cargarlo con los nuevos elementos
     */
    private void actualizarActores()
    {
        //vaciamos la colecci�n de actores para que est� lista para guardar los nuevos elementos.
        actores.clear();
        //eliminamos los laser que est�n fuera de la pantalla
        eliminarLaseres();
        //a�adimos los elementos actualmente en pantalla a la colecci�n de actores.
        cargarActores();
    }
    
    /**
     * Elimina los laser que est�n fuera de pantalla
     */
    private void eliminarLaseres()
    {
        // creo un arraylist distinto porque si no estar�a trabajando con el mismo y no podr�a modificarlo
        ArrayList<Laser> laseresAux = new ArrayList<Laser>();
        laseresAux.addAll(aliada.getLaseres()); 
        for(Laser laser:laseresAux){
            if(!laser.getVisible()){
                aliada.eliminarLaser(laser);
            }
        }
    }
    
    /**
     * Controla si se ha producido una colisi�n entre laseres y aliens o entre
     * la aliada y un alien y en consecuencia realiza la acci�n que corresponda.
     */
    private void colision(){
        //Pasamos los valores de Alien a otra colecci�n para poder borrar los elementos
        // que colisionan.
        ArrayList<Alien> aliensAux = new ArrayList<Alien>();
        aliensAux.addAll(aliens);
        // recorremos todo la colecci�n de Aliens
        for(Alien alien:aliensAux){
            //comprobamos si colisiona con Aliada => fin juego
            Rectangle rAlien = new Rectangle();
            rAlien = alien.getRect();
            Rectangle rAliada = new Rectangle();
            rAliada = aliada.getRect();

            if(rAliada.intersects(rAlien)){
                //creamos un mensaje para avisar de que se ha perdido y es el final del juego
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "COLISION ALIADA - ALIEN: ��Fin del Juego!!", "R-Type", JOptionPane.PLAIN_MESSAGE);
                //sentencia para cerrar el programa
                System.exit(0);
            }
            
            //comprobamos si colisiona alien con alg�n laser
            //trabajo con una colecci�n de l�seres auxiliar para poder modificar la buena
            ArrayList<Laser> laseresAux = new ArrayList<Laser>();
            laseresAux.addAll(aliada.getLaseres());
            for(Laser laser:laseresAux){
                if(laser.getRect().intersects(alien.getRect())){
                    //en caso de colisi�n elimina los objetos laser y alien implicados
                    aliada.eliminarLaser(laser);
                    aliens.remove(alien);
                }
            }
            
            //comprobamos si despu�s de las colisiones queda alg�n Alien en
            //pantada, sino, partida ganada, volver a jugar.
            if (aliens.size()== 0)
            {
                //Creamos mensaje de salida
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "No quedan aliens, ��HAS GANADO!!", "R-Type", JOptionPane.PLAIN_MESSAGE);
                
                //cerramos el panel de juego.
                this.getRootPane().getParent().setVisible(false);
                //paramos el timer
                timer.stop(); 
            }
            else if (aliens.size()<0){
                // se supone q ser�a imposible llegar a esto.
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "Error: N�mero de aliens negativo", "Error PanelJuego", JOptionPane.ERROR_MESSAGE);
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
     * Sobreescribe el m�todo actionPerformed de la clase Timer.
     * Controla lo que sucede por cada golpe de timer
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //nos deshacemos de los actores que ya no est�n en juego
        actualizarActores();
        //actualizamos las posiciones de los elementos
        for(Actor actor:actores){
            actor.mover();
        }
        //comprobamos las colisiones
        colision();
        //se dibuja la nueva situaci�n
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
