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
	
	private JLabel lblLupa;

	private JButton btnConfirm;
	private ButtonGroup radioGroup;
	
	private String jogadaArdversario = null;
	private boolean adversarioJogou = false;
	private boolean fimRound = false;
	private String nomeVencedor = null;
	
	private String[] placar;
	
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
		
		lblLupa = new JLabel(new ImageIcon(getClass().getResource("/magnifier.gif")));
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
        
        /**
         * Placar
         */
        //TODO Placar redimensionável
        g.setColor(CUSTOMIZED_BLUE);
   	 	g.setFont(fredoka.deriveFont((float) 30));
   	 	g.drawString(placar[0], 340, 250);
	 	g.drawString(placar[1], 550, 250);
	 	g.drawString("X", 595, 250);
	 	g.drawString(placar[3], 640, 250);
	 	g.drawString(placar[2], 680, 250);
        
        g.setColor(CUSTOMIZED_BLUE);
   	 	g.setFont(fredoka.deriveFont((float) 20));
        if(!adversarioJogou) {
        	 //ALternar para "*nome* escolheu sua jogada!"
        	 g.drawString("Aguardando jogada", 870, 620);
        	 g.drawString("   adversária", 870, 640);
        } else {
        	g.drawString("Jogada Confirmada!", 870, 520);
        }
        
        if(!fimRound) {
        	g.setColor(CUSTOMIZED_BLUE);
            g.setFont(fredoka.deriveFont((float) 16));
             
        	g.drawString("Selecione sua jogada clicando", 470, 650);
        	g.drawString("sobre a seta correspondente!", 470, 665);        	
        }
        else {
        	if(nomeVencedor == null){
        		g.setColor(CUSTOMIZED_BLUE);
                g.setFont(fredoka.deriveFont((float) 50));
                 
            	g.drawString("Empate!", 500, 500);
        	}
        	
        	else {
        		g.setColor(CUSTOMIZED_BLUE);
                g.setFont(fredoka.deriveFont((float) 40));
                 
            	g.drawString(nomeVencedor + " é o vencedor!", 340, 500);
        	}
        }
	}

	public JButton getBtnConfirm() {
		return btnConfirm;
	}

	public void setJogadas(String[] jogadas) {
		this.jogadaArdversario = (getJogada().equals(jogadas[0])) ? (jogadas[1]) : (jogadas[0]);
		validate();
		repaint();
	}
	
	public void setAdversarioJogou(boolean adversarioJogou) {
		if(this.adversarioJogou)
			return;
		
		this.adversarioJogou = adversarioJogou;
		lblLupa.setVisible(false); //TODO Lupa sumindo nas duas telas
		validate();
		repaint();
	}
	
	public void setFimRound(boolean fimRound) {
		this.fimRound = fimRound;
		validate();
		repaint();
	}
	
	public void setPlacar(String[] placar) {
		this.placar = placar;
		validate();
		repaint();
	}
	
	public void setNomeVencedorRound(String nomeVencedor) {
		fimRound = true;
		this.nomeVencedor = nomeVencedor;
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

