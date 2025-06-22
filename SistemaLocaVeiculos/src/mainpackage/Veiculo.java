package mainpackage;

import java.io.Serializable;
import java.time.Year;
import java.util.Calendar;

import enums.Categoria;
import enums.Estado;
import enums.Marca;

public abstract class Veiculo implements VeiculoI, Serializable {
    private static final long serialVersionUID = 1L;
	private Estado estado;
	private Marca marca;
	private Categoria categoria;
	private Locacao locacao;
	private String placa;
	private int ano;
	private double valorDeCompra;
	
	public Veiculo(Estado estado, Marca marca, Categoria categoria, String placa, int ano, double valorDeCompra) {
		this.estado = estado; this.marca = marca; this.categoria = categoria;
		this.placa = placa; this.ano = ano; this.valorDeCompra = valorDeCompra;
	}
	
	public void locar(int dias, Calendar data, Cliente cliente) {
		this.estado = Estado.LOCADO;
		this.locacao = new Locacao(dias, this.getValorDiariaLocacao(),data,cliente);
	}
	public void vender() {
		this.estado = Estado.VENDIDO;
	}
	public void devolver() {
		this.estado = Estado.DISPONIVEL;
		this.locacao = null;
	}
	public Estado getEstado() {
		return this.estado;
	}
	public Marca getMarca() {
		return this.marca;
	}
	public Categoria getCategoria() {
		return this.categoria;
	}
	public Locacao getLocacao() {
		return this.locacao;
	}
	public String getPlaca() {
		return this.placa;
	}
	public int getAno() {
		return this.ano;
	}
	public double getValorDeCompra() {
		return this.valorDeCompra;
	}
	
	public double getValorParaVenda() {
		double valor = this.valorDeCompra - (Year.now().getValue() - this.ano) * 0.15 * this.valorDeCompra;
		if(valor < this.valorDeCompra * 0.1) valor = this.valorDeCompra*0.1;
		return valor;
	}
	public abstract double getValorDiariaLocacao();
}