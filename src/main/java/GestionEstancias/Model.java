package GestionEstancias;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	Connection conn = null;

	public static Connection ConnectDb() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/central_reservas", "root", "");
			return conn;
		} catch (Exception e) {
			return null;
		}

	}

	public static ObservableList<Habitaciones> getDatausers() {
		Connection conn = ConnectDb();

		ObservableList<Habitaciones> list = FXCollections.observableArrayList();

		try {
			PreparedStatement ps = conn.prepareStatement("select * from habitaciones");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Habitaciones(rs.getString("numHabitacion"),
						rs.getString("codHotel"),
						rs.getString("preciodia")));
			}
		} catch (Exception e) {

		}
		return list;

	}
	
	public static ObservableList<Estancias> getDataEstancias() {
		Connection conn = ConnectDb();

		ObservableList<Estancias> listEstancias = FXCollections.observableArrayList();

		try {
			PreparedStatement ps = conn.prepareStatement("select * from estancias");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listEstancias.add(new Estancias(rs.getString("id"),
						rs.getString("nombre"),
						rs.getString("fechaInicio"),
						rs.getString("fechaFin"),
						rs.getString("numHabitacion"),
						rs.getString("codHotel")));
			}
		} catch (Exception e) {

		}
		return listEstancias;

	}

}
