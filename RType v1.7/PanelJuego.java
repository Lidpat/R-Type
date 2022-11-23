import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

/**
 * Game execution control.
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
     * @param numAliens greater than 0
     * @param vel speed ships factor (greater than 0).
     */
    public PanelJuego(int numAliens, int vel)
    {
        aliens = new ArrayList<Alien>();
        actores = new ArrayList<Actor>();
        aliada = new Aliada(ANCHO_PANEL, ALTO_PANEL);
        
        //Init aliens and actors collection
        cargarAliens(numAliens, vel);
        cargarActores();
        
        setSize(ANCHO_PANEL, ALTO_PANEL);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        timer = new Timer(30, this);
        timer.start();
        
    }

    /**
     * Paint actors 
     */
    @Override
    public void paint (Graphics g)
    {
        super.paint(g);
        
        for (Actor actor: actores){
            g.setColor(actor.getColor());
            g.fillRect(actor.getX(), actor.getY(), actor.getAncho(), actor.getAlto());
                  
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    /**
     * Almost 50% of aliens are A type, and the rest type B
     */
    private void cargarAliens(int numAliens, int vel)
    {
        int numAliensB = Math.abs(numAliens/2);
        int numAliensA = numAliens - numAliensB;
        for(int i=0 ; i<numAliensA ; i++){
            aliens.add(new AlienA( ANCHO_PANEL+40*i, (ALTO_PANEL/3)*(1+i%2), ANCHO_PANEL, ALTO_PANEL, vel));
        }
        
        for(int i=0; i< numAliensB ; i++){
            aliens.add(new AlienB( ANCHO_PANEL+20+40*i, ALTO_PANEL/6 + (ALTO_PANEL/3)*(i%3) , ANCHO_PANEL, ALTO_PANEL, vel));
        }
    }
    
    private void cargarActores()
    {
        //Add allied ship to actors collection
        actores.add(aliada);
        //Add aliens collection to actors collection
        actores.addAll(aliens);
        //Add lasers in screen to actor collection
        actores.addAll(aliada.getLaseres());
    }
    
    /**
     * Clean actors arrayList and charge new elements
     */
    private void actualizarActores()
    {
        actores.clear();
        eliminarLaseres();
        cargarActores();
    }
    
    /**
     * clean out-of-screen lasers
     */
    private void eliminarLaseres()
    {
        ArrayList<Laser> laseresAux = new ArrayList<Laser>();
        laseresAux.addAll(aliada.getLaseres()); 
        for(Laser laser:laseresAux){
            if(!laser.getVisible()){
                aliada.eliminarLaser(laser);
            }
        }
    }
    
    /**
     * Control collisions and trigger the corresponding action
     */
    private void colision(){
        ArrayList<Alien> aliensAux = new ArrayList<Alien>();
        aliensAux.addAll(aliens);
        for(Alien alien:aliensAux){
            //check allied collision => game over
            Rectangle rAlien = new Rectangle();
            rAlien = alien.getRect();
            Rectangle rAliada = new Rectangle();
            rAliada = aliada.getRect();

            if(rAliada.intersects(rAlien)){
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "COLISION ALIADA - ALIEN: ¡¡Fin del Juego!!", "R-Type", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            
            // Check alien-laser collision => delete alien ship and laser
            ArrayList<Laser> laseresAux = new ArrayList<Laser>();
            laseresAux.addAll(aliada.getLaseres());
            for(Laser laser:laseresAux){
                if(laser.getRect().intersects(alien.getRect())){
                    aliada.eliminarLaser(laser);
                    aliens.remove(alien);
                }
            }
            
            if (aliens.size()== 0)
            {
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "No quedan aliens, ¡¡HAS GANADO!!", "R-Type", JOptionPane.PLAIN_MESSAGE);
                this.getRootPane().getParent().setVisible(false);
                timer.stop(); 
            }
            else if (aliens.size()<0){
                JOptionPane pantallaFinal = new JOptionPane();
                pantallaFinal.showMessageDialog(null, "Error: Número de aliens negativo", "Error PanelJuego", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    
    public Timer getTimer()
    {
        return timer;
    }
    
    /**
     * Override actionPerformed method from Timer class.
     * Control action in every time-tick
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        actualizarActores();
        for(Actor actor:actores){
            actor.mover();
        }
        colision();
        repaint();
    }
    
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
