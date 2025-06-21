package mainpackage;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import enums.ModeloMotocicleta;
import enums.ModeloVan;

public class Main extends JFrame{
	private static final long serialVersionUID = -9151783484566916823L;
	public static int fh = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.56), fw = (int)( Toolkit.getDefaultToolkit().getScreenSize().height * 1.08);
	public static ArrayList<Cliente> clientes;
	public static ArrayList<Veiculo> veiculos;
	
	private static Container mainc;
	private static JPanel telas, main;
	private static JLabel mainlabel;
	
	//botões para ir para as telas
	private JButton ManterClientes, IncluirVeiculos, LocarVeiculos, DevolverVeiculos, VenderVeiculos;
	public static JButton Voltar;
	private JComboBox comboBox;
	
	public static void main(String args[]) {
		Main main = new Main();
		main.setVisible(true);
	}
	
	public Main() {
		super("Sistema Empresarial");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(643, 418);
		
		mainc = getContentPane();
		mainc.setLayout(new BorderLayout());
		
		telas = new JPanel(new GridLayout(1,0,1,1));
		ManterClientes = new JButton("Manter Clientes");
		IncluirVeiculos = new JButton("Incluir Veículos");
		LocarVeiculos = new JButton("Locar Veículos");
		DevolverVeiculos = new JButton("Devolver Veículos");
		VenderVeiculos = new JButton("Vender Veículos");
		Voltar = new JButton("Voltar");
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
		
		Voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				main.removeAll();
		        main.add(mainlabel);
		        main.revalidate();
		        main.repaint();
			}
		});
		
		ManterClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManterClientes();
				setVisible(true);
			}
		});
		IncluirVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					IncluirVeiculos();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
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
	
	public static void ManterClientes() {
		main.removeAll();
		
		// se cria os componentes como objetos temporários, depois os adiciona no jpanel main, e no fim da função coloca os comportamentos dos botões
	}
	public static void IncluirVeiculos() throws ParseException {
		main.removeAll();
		JLabel título = new JLabel("Incluir veículos");
		JLabel marca = new JLabel("Selecione a marca");
		JLabel estado = new JLabel("Selecione o estado");
		JLabel categoria = new JLabel("Selecione a categoria");
		JLabel modelo = new JLabel("Selecione o modelo");
		
		JTable tabela = sistema.criarTabelaVeiculos();
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		JButton addVeic = new JButton("Adicionar Veículo");
		
		JLabel ano = new JLabel("Ano");
        MaskFormatter mascaraAno = new MaskFormatter("####");
        mascaraAno.setPlaceholderCharacter('_');  // Preenche os espaços vazios com "_"
		JFormattedTextField TAno = new JFormattedTextField(mascaraAno);
		
		JLabel valor = new JLabel("Valor de compra");
		JTextField TValor = new JTextField(6);
		
		JLabel placa = new JLabel("Número da placa");
        MaskFormatter mascaraPlaca = new MaskFormatter("LLL-#L##");
        mascaraPlaca.setPlaceholderCharacter('_');  // Preenche os espaços vazios com "_"
		JFormattedTextField TPlaca = new JFormattedTextField(mascaraPlaca);
		
		JComboBox<Marca> comboMarca = new JComboBox<>(Marca.values());
		JComboBox<Estado> comboEstado = new JComboBox<>(Estado.values());
		JComboBox<Categoria> comboCategoria = new JComboBox<>(Categoria.values());
		String[] tipos =  {"AUTOMÓVEL", "MOTOCICLETA", "VAN"};
		
		Map<String, Enum<?>[]> modelosPorTipo = new HashMap<>();
		modelosPorTipo.put("AUTOMÓVEL", ModeloAutomovel.values());
		modelosPorTipo.put("MOTOCICLETA", ModeloMotocicleta.values());
		modelosPorTipo.put("VAN", ModeloVan.values());

        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        JComboBox<Enum<?>> comboModelo = new JComboBox<>();

        String tipoInicial = (String) comboTipo.getSelectedItem();
        comboModelo.setModel(new DefaultComboBoxModel<>(modelosPorTipo.get(tipoInicial)));

        comboTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoSelecionado = (String) comboTipo.getSelectedItem();
                Enum<?>[] modelos = modelosPorTipo.get(tipoSelecionado);
                comboModelo.setModel(new DefaultComboBoxModel<>(modelos));
            }
        });
        
		addVeic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboTipo.getSelectedItem().equals("AUTOMÓVEL")) {
					sistema.adicionarAutomovel(
						    (Estado) comboEstado.getSelectedItem(),
						    (Marca) comboMarca.getSelectedItem(),
						    (Categoria) comboCategoria.getSelectedItem(),
						    (ModeloAutomovel) comboModelo.getSelectedItem(),
						    TPlaca.getText(),
						    Integer.parseInt(TAno.getText()),
						    Double.parseDouble(TValor.getText())
						);		
				}
				sistema.atualizarTabela();
			}
		});
		
        
		JPanel PC = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		PC.add(marca);
		PC.add(comboMarca);
		PC.add(estado);		
		PC.add(comboEstado);
		PC.add(categoria);
		PC.add(comboCategoria);
		PC.add(modelo);
		PC.add(comboTipo);
		PC.add(comboModelo);
		PC.add(valor);
		PC.add(TValor);
		PC.add(placa);
		PC.add(TPlaca);
		PC.add(ano);
		PC.add(TAno);
		main.add(PC);
		main.add(título);
		main.add(scrollPane);
		main.add(addVeic);
		main.add(Voltar);
		main.revalidate();
		main.repaint();
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

