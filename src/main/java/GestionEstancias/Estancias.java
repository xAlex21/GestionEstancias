package GestionEstancias;

public class Estancias {
	
	private String id;
	private String nombre;
	private String entrada;
	private String salida;
	private String numHabitacion;
	private String codHotel;
		
	
	public Estancias(String id, String nombre, String entrada, String salida, String numHabitacion, String codHotel) {
		this.id = id;
		this.nombre = nombre;
		this.entrada = entrada;
		this.salida = salida;
		this.numHabitacion = numHabitacion;
		this.codHotel = codHotel;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}
	public String getSalida() {
		return salida;
	}
	public void setSalida(String salida) {
		this.salida = salida;
	}
	public String getNumHabitacion() {
		return numHabitacion;
	}
	public void setNumHabitacion(String numHabitacion) {
		this.numHabitacion = numHabitacion;
	}
	public String getCodHotel() {
		return codHotel;
	}
	public void setCodHotel(String codHotel) {
		this.codHotel = codHotel;
	}
	
	
}
