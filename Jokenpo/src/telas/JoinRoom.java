package telas;

import static main.Main.CUSTOMIZED_BLUE;
import static main.Main.fredoka;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import main.Main;

public class JoinRoom extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage background;
	private String pathBackground = "/background.png";
	
	private JFormattedTextField roomCode;
	private JButton btnJoin;
	private JButton btnBack;

	public JoinRoom()
	{	
		try 
		{
			background = ImageIO.read(getClass().getResource(pathBackground));
			paintComponent(background.getGraphics());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		setLayout(null);
		
		roomCode = new JFormattedTextField();
		roomCode.setFont(fredoka);
		roomCode.setBounds(250, 500, 700, 60);
		try {
			MaskFormatter mask = new MaskFormatter();
			mask.setMask("###.###.###.###");
			mask.setPlaceholderCharacter('0');
			mask.install(roomCode);
		} catch (ParseException e) {e.printStackTrace();}
		add(roomCode);
		
		btnJoin = new JButton("Juntar-se");
		btnJoin.setFont(fredoka);
		btnJoin.setBackground(CUSTOMIZED_BLUE);
		btnJoin.setBounds(500, 600, 200, 40);
		add(btnJoin);
		
		btnBack = new JButton("Voltar");
		btnBack.setFont(fredoka);
		btnBack.setBackground(CUSTOMIZED_BLUE);
		btnBack.setBounds(500, 650, 200, 40);
		add(btnBack);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        g.drawImage(background, 0, 0, null);
	        
	        g.setColor(CUSTOMIZED_BLUE);
	        g.setFont(fredoka);
	        g.drawString("Informe o C�digo da Sala", 430, 480);
	}
	
	public JFormattedTextField getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(JFormattedTextField roomCode) {
		this.roomCode = roomCode;
	}

	public JButton getBtnJoin() {
		return btnJoin;
	}

	public void setBtnJoin(JButton btnJoin) {
		this.btnJoin = btnJoin;
	}
	
	public JButton getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(JButton btnBack) {
		this.btnBack = btnBack;
	}
}
