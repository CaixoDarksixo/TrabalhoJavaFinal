package mainpackage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame{
	private static final long serialVersionUID = -9151783484566916823L;
	public static int fh = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.56), fw = (int)( Toolkit.getDefaultToolkit().getScreenSize().height * 1.08);
	public static ArrayList<Cliente> clientes = new ArrayList<>();
	public static ArrayList<Veiculo> veiculos;
	
	private static Container mainc;
	private static JPanel telas, main;
	private static JLabel mainlabel;
	
	//botões para ir para as telas
	private JButton ManterClientes, IncluirVeiculos, LocarVeiculos, DevolverVeiculos, VenderVeiculos;
	
	public static void main(String args[]) {
		Main main = new Main();
		main.setVisible(true);
	}
	
	public Main() {
		super("Sistema Empresarial");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(fw, fh);
		
		mainc = getContentPane();
		mainc.setLayout(new BorderLayout());
		
		telas = new JPanel(new GridLayout(1,0,1,1));
		ManterClientes = new JButton("Manter Clientes");
		IncluirVeiculos = new JButton("Incluir Veículos");
		LocarVeiculos = new JButton("Locar Veículos");
		DevolverVeiculos = new JButton("Devolver Veículos");
		VenderVeiculos = new JButton("Vender Veículos");
		telas.add(ManterClientes);
		telas.add(IncluirVeiculos);
		telas.add(LocarVeiculos);
		telas.add(DevolverVeiculos);
		telas.add(VenderVeiculos);
		
		mainlabel = new JLabel("nenhuma tela selecionada");
		main = new JPanel(new GridLayout(0,1,4,4));
		main.add(mainlabel);
		
		mainc.add("North", telas);
		mainc.add("Center", main);
		
		ManterClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.removeAll();
				main.setLayout(new BorderLayout());
				main.add(new TelaManterClientes(), BorderLayout.CENTER);
				main.revalidate();
				main.repaint();
			}
		});
		IncluirVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IncluirVeiculos();
				setVisible(true);
			}
		});
		LocarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocarVeiculos();
				setVisible(true);
			}
		});
		DevolverVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DevolverVeiculos();
				setVisible(true);
			}
		});
		VenderVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VenderVeiculos();
				setVisible(true);
			}
		});
	}
	
	public static void IncluirVeiculos() {
		main.removeAll();
		// se cria os componentes como objetos temporários, depois os adiciona no jpanel main, e no fim da função coloca os comportamentos dos botões
	}
	public static void LocarVeiculos() {
		main.removeAll();
		
	}
	public static void DevolverVeiculos() {
		main.removeAll();
		
	}
	public static void VenderVeiculos() {
		main.removeAll();
		
	}
}