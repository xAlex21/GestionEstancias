package GestionEstancias;

public class Habitaciones {

	private String habitacion;
	private String hotel;
	private String precio;
	
	
	public Habitaciones(String habitacion, String hotel, String precio) {
		this.habitacion = habitacion;
		this.hotel = hotel;
		this.precio = precio;
	}


	public String getHabitacion() {
		return habitacion;
	}


	public void setHabitacion(String habitacion) {
		this.habitacion = habitacion;
	}


	public String getHotel() {
		return hotel;
	}


	public void setHotel(String hotel) {
		this.hotel = hotel;
	}


	public String getPrecio() {
		return precio;
	}


	public void setPrecio(String precio) {
		this.precio = precio;
	}


	
	
	
}
