package enums;

import java.util.Arrays;

public enum Sexo {
	MACHO("M"),
	HEMBRA("H"),
	OTRO("O");
	
	private String valor;
	
	private Sexo(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
	
	public static Sexo getByValor(String valor) {
		return Arrays.stream(Sexo.values())
		.filter(s -> s.getValor().equals(valor))
		.findFirst().orElse(null);
	}
	
}
