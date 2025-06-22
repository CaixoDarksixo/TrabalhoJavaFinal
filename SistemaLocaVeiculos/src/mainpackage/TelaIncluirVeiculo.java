package mainpackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

public class TelaIncluirVeiculo extends JPanel {

    private static final long serialVersionUID = 1L;

    public TelaIncluirVeiculo() throws ParseException {

        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new GridLayout(0, 1, 4, 4));

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
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));

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

        addVeic.addActionListener((ActionEvent e) -> {
            String tipo = (String) comboTipo.getSelectedItem();
            try {
                int ano = Integer.parseInt(tAno.getText().replaceAll("[^\\d]", ""));
                double val = Double.parseDouble(TValor.getText().replace(",", "."));
                String placa = tPlaca.getText().replaceAll("[ _]", "");

                switch (tipo) {
                    case "AUTOMÓVEL" -> sistema.adicionarAutomovel(
                            comboEstado.getItemAt(comboEstado.getSelectedIndex()),
                            comboMarca.getItemAt(comboMarca.getSelectedIndex()),
                            comboCategoria.getItemAt(comboCategoria.getSelectedIndex()),
                            (ModeloAutomovel) comboModelo.getSelectedItem(),
                            placa, ano, val);

                    case "MOTOCICLETA" -> sistema.adicionarMotocicleta(
                            comboEstado.getItemAt(comboEstado.getSelectedIndex()),
                            comboMarca.getItemAt(comboMarca.getSelectedIndex()),
                            comboCategoria.getItemAt(comboCategoria.getSelectedIndex()),
                            (ModeloMotocicleta) comboModelo.getSelectedItem(),
                            placa, ano, val);
                    case "VAN" -> sistema.adicionarVan(
                            comboEstado.getItemAt(comboEstado.getSelectedIndex()),
                            comboMarca.getItemAt(comboMarca.getSelectedIndex()),
                            comboCategoria.getItemAt(comboCategoria.getSelectedIndex()),
                            (ModeloVan) comboModelo.getSelectedItem(),
                            placa, ano, val);
                }
                sistema.atualizarTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ano ou valor inválido.");
            }
        });

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        form.add(marcaLbl);     form.add(comboMarca);
        form.add(estadoLbl);    form.add(comboEstado);
        form.add(categoriaLbl); form.add(comboCategoria);
        form.add(modeloLbl);    form.add(comboTipo); form.add(comboModelo);
        form.add(valorLbl);     form.add(TValor);
        form.add(placaLbl);     form.add(tPlaca);
        form.add(anoLbl);       form.add(tAno);
        add(form);
        add(addVeic);

        JTable tabela = sistema.criarTabelaVeiculos();
        add(new JScrollPane(tabela));
    }

    public void reset() {
        revalidate();
        repaint();
    }
}
