import javax.swing.*;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
 
/**
 * Contiene la parte gráfica (creación de ventanas, botones, etc) y 
 * maneja los listener asociados a los componentes gráficos de la pantalla inicial,
 * donde se elige el nivel de juego.
 * 
 * @author Lidia A. Palacios 
 * @version v1.7
 */
public class PantallaInicio extends JFrame
{
    private JFrame ventana;

    /**
     * Constructor de la clase PantallaInicio
     */
    public PantallaInicio()
    {
        ventana = new JFrame();
        //crea los componentes y le asigna los valores iniciales
        panelInicio();
    }

    /**
     * Crea el panel inicial del juego y sus componentes
     * 
     */
    private void panelInicio()
    {
        Container contenedor = ventana.getContentPane();
        contenedor.setLayout(new BorderLayout());
        
        JLabel niveles = new JLabel(" Selecciona el modo de juego ");
        contenedor.add(niveles, BorderLayout.NORTH);
        
        JPanel botonera = new JPanel(new GridLayout(4,1));
        JButton nivelFacil = new JButton ("Fácil");
        botonera.add(nivelFacil);
        nivelFacil.addActionListener(new nivelFacilActionListener());
        
        JButton nivelNormal = new JButton ("Normal");
        botonera.add(nivelNormal);
        nivelNormal.addActionListener(new nivelNormalActionListener());
        
        JButton nivelComplicado = new JButton ("Complicado");
        botonera.add(nivelComplicado);
        nivelComplicado.addActionListener(new nivelComplicadoActionListener());
        
        JButton nivelImposible = new JButton ("Imposible");
        botonera.add(nivelImposible);
        nivelImposible.addActionListener(new nivelImposibleActionListener());
        
        contenedor.add(botonera, BorderLayout.CENTER);
                
        ventana.pack();
        ventana.setVisible(true);
        //cuado se cierra esta ventana se cierra todo el programa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
     /**
     * Inicializa la partida con el nivel escogido. 
     * Deja todo listo para devolver el control a la pantalla de inicio 
     * cuando finalize el juego. 
     * 
     * @param vel Velocidad del juego
     * @param numAlien El número de aliens con que se inicia la partida. 
     */
    private void iniciarNivel (int numAlien, int vel)
    {
        ventana.setVisible(false);
        RType rType = new RType(numAlien, vel); 
        ventana.setVisible(true); //al terminar RType (la partida) devuelve el control a PantallaInicio.
    }
    
    // a continuación declaro las clases internas que reescriben el mÃ©todo actionPerformed del ActionListener
    // y que determinarÃ¡n lo que sucede al seleccionarse los botones.
    
    class nivelFacilActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evento)
        {
            //iniciamos la partida con los valores correspondientes al nivel escogido
            iniciarNivel(10,1); // (numero de aliens, velocidad)
        }
    }
    
    class nivelNormalActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evento)
        {
            iniciarNivel(15,2); // (numero de aliens, velocidad)
        }
    }
    
    class nivelComplicadoActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evento)
        {
            iniciarNivel(20,3); // (numero de aliens, velocidad)
        }
    }
    
    class nivelImposibleActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evento)
        {
             iniciarNivel(30,4);
        }
    }

}
