package mainpackage;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import enums.ModeloMotocicleta;
import enums.ModeloVan;

public class sistema {

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

	    private static AbstractTableModel modeloTabela;
	    
	    public static JTable criarTabelaVeiculos() {
	    	modeloTabela = new AbstractTableModel() {
	            private static final long serialVersionUID = 1L;

	            private String[] colunas = {"Placa", "Ano", "Marca", "Estado", "Categoria", "Valor"};

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
	                    case 3: return veiculo.getEstado();
	                    case 4: return veiculo.getCategoria();
	                    case 5: return veiculo.getValorDeCompra();
	                    default: return null;
	                }
	            }
	        };

	        return new JTable(modeloTabela);

	    }
	    
	    public static void atualizarTabela() {
	        modeloTabela.fireTableDataChanged();
	    }
}

