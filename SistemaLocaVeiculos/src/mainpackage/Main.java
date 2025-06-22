package mainpackage;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.List;

import javax.swing.*;

public class Main extends JFrame{
	private static final long serialVersionUID = -9151783484566916823L;
	public static int fh = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.56), fw = (int)( Toolkit.getDefaultToolkit().getScreenSize().height * 1.08);
	public static List<Cliente> clientes = ClienteRepo.load();
    public static List<Veiculo> veiculos = VeiculoRepo.load();
	
	private static Container mainc;
	private static JPanel telas, main;
	private static JLabel mainlabel;
	
	//botões para ir para as telas
	private JButton ManterClientes, IncluirVeiculos, LocarVeiculos, DevolverVeiculos, VenderVeiculos;
	//public static JButton Voltar = new JButton("Voltar");
	
	public static void main(String args[]) {
	    new Main().setVisible(true);
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
		/*Voltar.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            main.removeAll();
		            main.add(mainlabel);
		            main.revalidate();
		            main.repaint();
		        }
	    });
	    */
		telas.add(ManterClientes);
		telas.add(IncluirVeiculos);
		telas.add(LocarVeiculos);
		telas.add(DevolverVeiculos);
		telas.add(VenderVeiculos);
		
		mainlabel = new JLabel("");
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
				main.removeAll();
				main.setLayout(new BorderLayout());
				try {
					main.add(new TelaIncluirVeiculo(), BorderLayout.CENTER);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				main.revalidate();
				main.repaint();
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
				main.removeAll();
				main.setLayout(new BorderLayout());
				main.add(new TelaVenderVeiculos(), BorderLayout.CENTER);
				main.revalidate();
				main.repaint();
			}
		});
		float hsb[] = Color.RGBtoHSB(255, 192, 203, null);
		telas.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		main.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
	}
	
	public static void LocarVeiculos() {
	    main.removeAll();
	    main.setLayout(new BorderLayout());
	    main.add(new TelaLocarVeiculos(), BorderLayout.CENTER);
	    main.revalidate();
	    main.repaint();
	}
	
	public static void DevolverVeiculos() {
		main.removeAll();
		
	}
	public static void VenderVeiculos() {
		main.removeAll();
		
	}
}
