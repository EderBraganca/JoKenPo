package telas;

import static main.Main.BACKGROUND_COLOR;
import static main.Main.CUSTOMIZED_BLUE;
import static main.Main.fredoka;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.Main;

public class Lobby extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage background;
	private String pathBackground = "/background-lobby.png";
	private ImageIcon selectedArrow = new ImageIcon("res/selected-arrow-right.png");
	private ImageIcon disabledArrow = new ImageIcon("res/disabled-arrow-right.png");

	private JButton btnConfirm;
	private ButtonGroup radioGroup;
	
	public String jogadaArdversario = null;
	public boolean adversarioJogou = false;
	
	public Lobby()
	{	
		try 
		{
			background = ImageIO.read(getClass().getResource(pathBackground));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		setLayout(null);
				
		/**
		 * RadioButtons 
		 */
		radioGroup = new ButtonGroup();
		
		JRadioButton btnTesoura = new JRadioButton(disabledArrow);
		btnTesoura.setName("TESOURA");
		btnTesoura.setSelected(true);
		btnTesoura.setBackground(BACKGROUND_COLOR);
		btnTesoura.setBounds(80, 350, 70, 64);
		btnTesoura.setSelectedIcon(selectedArrow);
		btnTesoura.setDisabledIcon(disabledArrow);
		radioGroup.add(btnTesoura);
		add(btnTesoura);
		
		JRadioButton btnPapel = new JRadioButton(disabledArrow);
		btnPapel.setName("PAPEL");
		btnPapel.setBackground(BACKGROUND_COLOR);
		btnPapel.setBounds(80, 530, 70, 64);
		btnPapel.setSelectedIcon(selectedArrow);
		btnPapel.setDisabledIcon(disabledArrow);
		radioGroup.add(btnPapel);
		add(btnPapel);
		
		JRadioButton btnPedra = new JRadioButton(disabledArrow);
		btnPedra.setName("PEDRA");
		btnPedra.setBackground(BACKGROUND_COLOR);
		btnPedra.setBounds(80, 700, 70, 64);
		btnPedra.setSelectedIcon(selectedArrow);
		btnPedra.setDisabledIcon(disabledArrow);
		radioGroup.add(btnPedra);
		add(btnPedra);
		
		JLabel lblLupa = new JLabel(new ImageIcon(getClass().getResource("/magnifier.gif")));
		lblLupa.setBounds(860, 400, 200, 200);
		add(lblLupa);
		
		btnConfirm = new JButton("Confirmar");
		btnConfirm.setFont(fredoka);
		btnConfirm.setBackground(CUSTOMIZED_BLUE);
		btnConfirm.setBounds(500, 700, 200, 40);
		add(btnConfirm);	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		/**
         * Esconde a jogada do outro jogador
         */
		if(jogadaArdversario == null || !jogadaArdversario.equalsIgnoreCase("TESOURA")) {
			g.setColor(BACKGROUND_COLOR);
			g.fillRect(850, 310, 170, 150);
		}
        
		if(jogadaArdversario == null || !jogadaArdversario.equalsIgnoreCase("PAPEL")) {
			g.setColor(BACKGROUND_COLOR);
			g.fillRect(850, 480, 170, 150);
		}
		
        if(jogadaArdversario == null || !jogadaArdversario.equalsIgnoreCase("PEDRA")) {
        	g.setColor(BACKGROUND_COLOR);
        	g.fillRect(850, 650, 170, 150);
        }
        
        g.setColor(CUSTOMIZED_BLUE);
   	 	g.setFont(fredoka.deriveFont((float) 20));
        if(!adversarioJogou) {
        	 //ALternar para "*nome* escolheu sua jogada!"
        	 g.drawString("Aguardando jogada", 870, 620);
        } else {
        	g.drawString("Jogada Confirmada!", 870, 620);
        }
        
        g.setColor(CUSTOMIZED_BLUE);
        g.setFont(fredoka.deriveFont((float) 16));
        
        g.drawString("Selecione sua jogada clicando", 470, 650);
        g.drawString("sobre a seta correspondente!", 470, 665);
	}

	public JButton getBtnConfirm() {
		return btnConfirm;
	}

	public void setJogadaAdversario(String jogadaAdversario) {
		this.jogadaArdversario = jogadaAdversario;
		validate();
		repaint();
	}
	
	public void setAdversarioJogou(boolean adversarioJogou) {
		this.adversarioJogou = adversarioJogou;
		validate();
		repaint();
	}
	
	public String getJogada() {
		String buttonName = null;
		
		for (Enumeration<AbstractButton> buttons = radioGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setEnabled(false);

            if (button.isSelected())
                buttonName = button.getName();
        }
        return buttonName;
	}		
}

