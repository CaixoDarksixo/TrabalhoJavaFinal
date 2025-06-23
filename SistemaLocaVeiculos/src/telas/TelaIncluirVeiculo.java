package telas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import enums.ModeloMotocicleta;
import enums.ModeloVan;
import mainpackage.Automovel;
import mainpackage.Main;
import mainpackage.Motocicleta;
import mainpackage.Van;
import mainpackage.Veiculo;
import mainpackage.VeiculoRepo;

public class TelaIncluirVeiculo extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private static AbstractTableModel modeloTabela;

    public TelaIncluirVeiculo() throws ParseException {
    	
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());

        JLabel marcaLbl     = new JLabel("Selecione a marca");
        JLabel estadoLbl    = new JLabel("Selecione o estado");
        JLabel categoriaLbl = new JLabel("Selecione a categoria");
        JLabel modeloLbl    = new JLabel("Selecione o modelo");

        JLabel anoLbl   = new JLabel("Ano");
        MaskFormatter mascaraAno = new MaskFormatter("####");
        mascaraAno.setPlaceholderCharacter('_');
        JFormattedTextField tAno = new JFormattedTextField(mascaraAno);

        JLabel valorLbl = new JLabel("Valor de compra");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR")));

        NumberFormatter formatter = new NumberFormatter(df);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        JFormattedTextField TValor = new JFormattedTextField(formatter);
        TValor.setColumns(8);


        JLabel placaLbl = new JLabel("Número da placa");
        MaskFormatter mascaraPlaca = new MaskFormatter("LLL-#A##");
        mascaraPlaca.setPlaceholderCharacter('_');
        JFormattedTextField tPlaca = new JFormattedTextField(mascaraPlaca);

        JComboBox<Marca> comboMarca = new JComboBox<>(Marca.values());
        JComboBox<Estado> comboEstado = new JComboBox<>(Estado.values());
        JComboBox<Categoria> comboCategoria = new JComboBox<>(Categoria.values());

        String[] tipos = {"AUTOMÓVEL", "MOTOCICLETA", "VAN"};
        Map<String, Enum<?>[]> modelosPorTipo = new HashMap<>();
        modelosPorTipo.put("AUTOMÓVEL", ModeloAutomovel.values());
        modelosPorTipo.put("MOTOCICLETA", ModeloMotocicleta.values());
        modelosPorTipo.put("VAN", ModeloVan.values());

        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        JComboBox<Enum<?>> comboModelo = new JComboBox<>();
        comboModelo.setModel(new DefaultComboBoxModel<>(modelosPorTipo.get(tipos[0])));

        comboTipo.addActionListener(e -> {
            String tipoSel = (String) comboTipo.getSelectedItem();
            comboModelo.setModel(new DefaultComboBoxModel<>(modelosPorTipo.get(tipoSel)));
        });

        JButton addVeic = new JButton("Adicionar Veículo");
      
        JPanel titleform = new JPanel(new BorderLayout());
        
        JLabel titulo = new JLabel("CADASTRO DE VEÍCULOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titleform.add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        form.add(marcaLbl);     form.add(comboMarca);
        form.add(estadoLbl);    form.add(comboEstado);
        form.add(categoriaLbl); form.add(comboCategoria);
        form.add(modeloLbl);    form.add(comboTipo); form.add(comboModelo);
        form.add(valorLbl);     form.add(TValor);
        form.add(placaLbl);     form.add(tPlaca);
        form.add(anoLbl);       form.add(tAno);
        form.add(addVeic);    	
        form.setPreferredSize(new Dimension(40,100));
        
        titleform.add("Center",form);
        add("North",titleform);
        JTable tabela = criarTabelaVeiculos();
        add("Center",new JScrollPane(tabela));
        
        addVeic.addActionListener((ActionEvent e) -> {
            String tipo = (String) comboTipo.getSelectedItem();
            try {
                int ano = Integer.parseInt(tAno.getText().replaceAll("[^\\d]", ""));
                double val = Double.parseDouble(TValor.getText().replace(".","").replace(",", "."));
                String placa = tPlaca.getText().replaceAll("[ _]", "");
                
                if(placaExiste(placa)) {
                	JOptionPane.showMessageDialog(this, "Placa já existe.");
                	return;
                }

                switch (tipo) {
                    case "AUTOMÓVEL" -> adicionarAutomovel(
                            comboEstado.getItemAt(comboEstado.getSelectedIndex()),
                            comboMarca.getItemAt(comboMarca.getSelectedIndex()),
                            comboCategoria.getItemAt(comboCategoria.getSelectedIndex()),
                            (ModeloAutomovel) comboModelo.getSelectedItem(),
                            placa, ano, val);

                    case "MOTOCICLETA" -> adicionarMotocicleta(
                            comboEstado.getItemAt(comboEstado.getSelectedIndex()),
                            comboMarca.getItemAt(comboMarca.getSelectedIndex()),
                            comboCategoria.getItemAt(comboCategoria.getSelectedIndex()),
                            (ModeloMotocicleta) comboModelo.getSelectedItem(),
                            placa, ano, val);
                    case "VAN" -> adicionarVan(
                            comboEstado.getItemAt(comboEstado.getSelectedIndex()),
                            comboMarca.getItemAt(comboMarca.getSelectedIndex()),
                            comboCategoria.getItemAt(comboCategoria.getSelectedIndex()),
                            (ModeloVan) comboModelo.getSelectedItem(),
                            placa, ano, val);
                }
                atualizarTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ano ou valor inválido.");
            }
        });
    }
    
    public static JTable criarTabelaVeiculos() {
    	modeloTabela = new AbstractTableModel() {
            private static final long serialVersionUID = 1L;

            private String[] colunas = {"Placa", "Ano", "Marca", "Modelo", "Estado", "Categoria", "Valor"};

            @Override
            public int getRowCount() {
                return Main.veiculos.size();
            }

            @Override
            public int getColumnCount() {
                return colunas.length;
            }

            @Override
            public String getColumnName(int column) {
                return colunas[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Veiculo veiculo = Main.veiculos.get(rowIndex);
                switch (columnIndex) {
                    case 0: return veiculo.getPlaca();
                    case 1: return veiculo.getAno();
                    case 2: return veiculo.getMarca();
                    case 3:
                    	if(veiculo instanceof Automovel)
                    		return ((Automovel) veiculo).getModelo();
                    	else if(veiculo instanceof Motocicleta)
	                    	return ((Motocicleta) veiculo).getModelo();
                    	else
	                    	return ((Van) veiculo).getModelo();
                    case 4: return veiculo.getEstado();
                    case 5: return veiculo.getCategoria();
                    case 6: return veiculo.getValorDeCompra();
                    default: return null;
                }
            }
        };

        return new JTable(modeloTabela);

    }
    
    public static void atualizarTabela() {
        modeloTabela.fireTableDataChanged();
    }
    
    public static void adicionarAutomovel(Estado estado, Marca marca, Categoria categoria, ModeloAutomovel modelo, String placa, int ano, double valorDeCompra) {
		Veiculo veiculo = new Automovel( estado,  marca,  categoria,  modelo,  placa,  ano,  valorDeCompra);
		Main.veiculos.add(veiculo);
		VeiculoRepo.save(Main.veiculos);
    }

    public static void adicionarMotocicleta(Estado estado, Marca marca, Categoria categoria, ModeloMotocicleta modelo, String placa, int ano, double valorDeCompra) {
    	Veiculo veiculo = new Motocicleta( estado,  marca,  categoria,  modelo,  placa,  ano,  valorDeCompra);
    	Main.veiculos.add(veiculo);
    	VeiculoRepo.save(Main.veiculos);
    }

    public static void adicionarVan(Estado estado, Marca marca, Categoria categoria, ModeloVan modelo , String placa, int ano, double valorDeCompra) {
    	Veiculo veiculo = new Van( estado,  marca,  categoria,  modelo,  placa,  ano,  valorDeCompra);
    	Main.veiculos.add(veiculo);
		VeiculoRepo.save(Main.veiculos);
	}

    public static boolean placaExiste(String placa) {
    	for (Veiculo v : Main.veiculos) {
    		if (v.getPlaca().equalsIgnoreCase(placa)) {
    			return true;
    		}
    	}
    	return false;
	}
    
    public void reset() {
        revalidate();
        repaint();
    }
}
