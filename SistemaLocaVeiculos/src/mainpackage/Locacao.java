package mainpackage;

import java.util.Calendar;

public class Locacao {
	private int dias;
	private double valor;
	private Calendar data;
	private Cliente cliente;
	
	public Locacao(int dias, double valor, Calendar data, Cliente cliente) {
		this.dias = dias;
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
	}
	
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
