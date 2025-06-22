package mainpackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.*;
import java.util.*;

import enums.Marca;
import enums.Categoria;
import enums.Estado;

public class TelaLocarVeiculos extends JPanel {

    private JComboBox<String> tipoCombo;
    private JComboBox<Marca> marcaCombo;
    private JComboBox<Categoria> categoriaCombo;
    private JComboBox<Cliente> clientesCombo;
    private JTextField buscaClienteField;
    private JTable tabelaVeiculos;
    private DefaultTableModel tabelaModel;
    private JButton botaoLocar;
    private JButton botaoBuscarCliente;
    private JFormattedTextField campoData;
    private JSpinner spinnerDias;

    public TelaLocarVeiculos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("LOCAÇÃO DE VEÍCULOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.add(criarPainelBuscaCliente(), BorderLayout.NORTH);
        painelSuperior.add(criarPainelFiltros(), BorderLayout.CENTER);
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);

        painelPrincipal.add(criarPainelTabela(), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));
        painelInferior.add(criarPainelDadosLocacao(), BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoLocar = new JButton("Locar Veículo");
        botaoLocar.setPreferredSize(new Dimension(120, 30));
        botaoLocar.setEnabled(false);
        painelBotoes.add(botaoLocar);
        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

        add(painelPrincipal, BorderLayout.CENTER);

        configurarEventos();
        atualizarTabela();
    }

    private JPanel criarPainelBuscaCliente() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscaClienteField = new JTextField(20);
        botaoBuscarCliente = new JButton("Buscar");
        clientesCombo = new JComboBox<>();
        clientesCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente c = (Cliente) value;
                    setText(c.getNome() + " " + c.getSobrenome() + " - " + c.getCPF());
                }
                return this;
            }
        });
        painel.add(new JLabel("Buscar Cliente (Nome/CPF):"));
        painel.add(buscaClienteField);
        painel.add(botaoBuscarCliente);
        painel.add(clientesCombo);
        return painel;
    }

    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridLayout(1, 6, 5, 5));
        tipoCombo = new JComboBox<>(new String[]{"Todos", "Automóvel", "Motocicleta", "Van"});
        marcaCombo = new JComboBox<>(Marca.values());
        marcaCombo.insertItemAt(null, 0);
        categoriaCombo = new JComboBox<>(Categoria.values());
        categoriaCombo.insertItemAt(null, 0);

        painel.add(new JLabel("Tipo:"));
        painel.add(tipoCombo);
        painel.add(new JLabel("Marca:"));
        painel.add(marcaCombo);
        painel.add(new JLabel("Categoria:"));
        painel.add(categoriaCombo);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Categoria", "Diária"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaVeiculos = new JTable(tabelaModel);
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(tabelaVeiculos);
    }

    private JPanel criarPainelDadosLocacao() {
        JPanel painel = new JPanel(new GridLayout(2, 2, 5, 5));
        spinnerDias = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        campoData = criarCampoData();

        painel.add(new JLabel("Dias de Locação:"));
        painel.add(spinnerDias);
        painel.add(new JLabel("Data Início (DD/MM/AAAA):"));
        painel.add(campoData);

        return painel;
    }

    private void configurarEventos() {
        botaoBuscarCliente.addActionListener(e -> {
            String termo = buscaClienteField.getText().toLowerCase();
            clientesCombo.removeAllItems();
            for (Cliente c : Main.clientes) {
                if (c.getNome().toLowerCase().contains(termo) || c.getCPF().contains(termo)) {
                    clientesCombo.addItem(c);
                }
            }
        });

        tipoCombo.addActionListener(e -> atualizarTabela());
        marcaCombo.addActionListener(e -> atualizarTabela());
        categoriaCombo.addActionListener(e -> atualizarTabela());

        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            botaoLocar.setEnabled(tabelaVeiculos.getSelectedRow() != -1);
        });

        botaoLocar.addActionListener(e -> realizarLocacao());
    }

    private void realizarLocacao() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();
        if (linhaSelecionada == -1) return;

        String placa = (String) tabelaModel.getValueAt(linhaSelecionada, 0);
        Veiculo veiculoSelecionado = null;

        for (Veiculo v : Main.veiculos) {
            if (v.getPlaca().equals(placa)) {
                veiculoSelecionado = v;
                break;
            }
        }

        if (veiculoSelecionado != null && clientesCombo.getSelectedItem() != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data = sdf.parse(campoData.getText());
                Calendar dataLocacao = Calendar.getInstance();
                dataLocacao.setTime(data);

                veiculoSelecionado.locar((Integer) spinnerDias.getValue(), dataLocacao, (Cliente) clientesCombo.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Locação realizada com sucesso!");
                atualizarTabela();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida! Use o formato DD/MM/AAAA");
            }
        }
    }

    private void atualizarTabela() {
        tabelaModel.setRowCount(0);
        String tipoSelecionado = (String) tipoCombo.getSelectedItem();
        Marca marcaSelecionada = (Marca) marcaCombo.getSelectedItem();
        Categoria categoriaSelecionada = (Categoria) categoriaCombo.getSelectedItem();

        for (Veiculo v : Main.veiculos) {
            if (v.getEstado() != Estado.DISPONIVEL) continue;

            if (tipoSelecionado != null && !tipoSelecionado.equals("Todos")) {
                if (tipoSelecionado.equals("Automóvel") && !(v instanceof Automovel)) continue;
                if (tipoSelecionado.equals("Motocicleta") && !(v instanceof Motocicleta)) continue;
                if (tipoSelecionado.equals("Van") && !(v instanceof Van)) continue;
            }

            if (marcaSelecionada != null && v.getMarca() != marcaSelecionada) continue;
            if (categoriaSelecionada != null && v.getCategoria() != categoriaSelecionada) continue;

            String modelo = "";
            if (v instanceof Automovel) modelo = ((Automovel) v).getModelo().name();
            else if (v instanceof Motocicleta) modelo = ((Motocicleta) v).getModelo().name();
            else if (v instanceof Van) modelo = ((Van) v).getModelo().name();

            tabelaModel.addRow(new Object[]{
                v.getPlaca(), v.getMarca(), modelo, v.getAno(), v.getCategoria(),
                String.format("R$ %.2f", v.getValorDiariaLocacao())
            });
        }
    }

    private JFormattedTextField criarCampoData() {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
            return new JFormattedTextField(mascara);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }
}
