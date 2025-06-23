package telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

import enums.Estado;
import mainpackage.Automovel;
import mainpackage.Main;
import mainpackage.Motocicleta;
import mainpackage.Van;
import mainpackage.Veiculo;
import mainpackage.VeiculoRepo;

@SuppressWarnings("serial")
public class TelaDevolverVeiculos extends JPanel {

    private JTable tabelaVeiculos;
    private DefaultTableModel tabelaModel;
    private JButton botaoDevolver;

    public TelaDevolverVeiculos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("DEVOLVER VEÍCULOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.add(criarPainelTabela(), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoDevolver = new JButton("Devolver Veículo");
        botaoDevolver.setPreferredSize(new Dimension(120, 30));
        botaoDevolver.setEnabled(false);
        painelBotoes.add(botaoDevolver);
        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

        add(painelPrincipal, BorderLayout.CENTER);

        configurarEventos();
        atualizarTabela();
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"Cliente", "Placa", "Marca", "Modelo", "Ano", "Data Locação", "Preço Diária", "Quantidade de Dias Locado", "Valor Total"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaVeiculos = new JTable(tabelaModel);
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(tabelaVeiculos);
    }

    private void configurarEventos() {
        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            botaoDevolver.setEnabled(tabelaVeiculos.getSelectedRow() != -1);
        });
        botaoDevolver.addActionListener(e -> realizarDevolucao());
    }
    private void realizarDevolucao() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();
        if (linhaSelecionada == -1) return;

        String placa = (String) tabelaModel.getValueAt(linhaSelecionada, 1);
        Veiculo veiculoSelecionado = null;

        for (Veiculo v : Main.veiculos) {
            if (v.getPlaca().equals(placa)) {
                veiculoSelecionado = v;
                break;
            }
        }

        if (veiculoSelecionado != null) {
	        veiculoSelecionado.devolver();
	        VeiculoRepo.save(Main.veiculos);
	        JOptionPane.showMessageDialog(this, "Devolução realizada com sucesso!");
	        atualizarTabela();
        } 
    }

    private void atualizarTabela() {
        tabelaModel.setRowCount(0);

        for (Veiculo v : Main.veiculos) {
            if (v.getEstado() != Estado.LOCADO) continue;

            String modelo = "";
            if (v instanceof Automovel) modelo = ((Automovel) v).getModelo().name();
            else if (v instanceof Motocicleta) modelo = ((Motocicleta) v).getModelo().name();
            else if (v instanceof Van) modelo = ((Van) v).getModelo().name();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = v.getLocacao().getData().getTime();
            String dateString = sdf.format(date);

            tabelaModel.addRow(new Object[]{
                v.getLocacao().getCliente().getNome() + " " + v.getLocacao().getCliente().getSobrenome(), v.getPlaca(), v.getMarca(), modelo, v.getAno(), 
                dateString,  String.format("R$ %.2f", v.getValorDiariaLocacao()), v.getLocacao().getDias(),  String.format("R$ %.2f", v.getLocacao().getValor())
            });
        }
    }
}