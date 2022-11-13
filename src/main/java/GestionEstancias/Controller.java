package GestionEstancias;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private TableColumn<Habitaciones, String> habitacion;

	@FXML
	private TableColumn<Habitaciones, String> hotel;

	@FXML
	private TableColumn<Habitaciones, String> precio;

	@FXML
	private TableView<Habitaciones> table;

	@FXML
	private TextField txt_habitacion;

	@FXML
	private TextField txt_hotel;

	@FXML
	private TextField txt_precio;

	private String selectedHabitacion;

	private String selectedCodHotel;

	
	
	//Estancias
    @FXML
    private TableView<Estancias> tableEstancias;
    
    @FXML
    private TableColumn<Estancias, String> estanciasEntrada;

    @FXML
    private TableColumn<Estancias, String> estanciasHabitacion;

    @FXML
    private TableColumn<Estancias, String> estanciasHotel;

    @FXML
    private TableColumn<Estancias, String> estanciasId;

    @FXML
    private TableColumn<Estancias, String> estanciasNombre;

    @FXML
    private TableColumn<Estancias, String> estanciasSalida;
    
    @FXML
    private DatePicker inputEstanciaEntrada;

    @FXML
    private TextField inputEstanciaHabitacion;

    @FXML
    private TextField inputEstanciaHotel;

    @FXML
    private TextField inputEstanciaId;

    @FXML
    private TextField inputEstanciaNombre;

    @FXML
    private DatePicker inputEstanciaSalida;
    
    
	int index = -1;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	ObservableList<Habitaciones> listHabitaciones;
	ObservableList<Estancias> listEstancias;

	public Controller() throws IOException {
		URL fxml = getClass().getResource("/View.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxml);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	public void Add_habitaciones() {

		conn = Model.ConnectDb();
		String sql = "insert into habitaciones (numHabitacion, codHotel, preciodia) values (?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_habitacion.getText());
			pst.setString(2, txt_hotel.getText());
			pst.setString(3, txt_precio.getText());
			pst.execute();

			listHabitaciones.add(new Habitaciones(txt_habitacion.getText(), txt_hotel.getText(), txt_precio.getText()));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
    @FXML
    void Add_estancias(ActionEvent event) {
		conn = Model.ConnectDb();
		String sql = "insert into estancias (nombre, fechaInicio, fechaFin, numHabitacion, codHotel) values (?, ?, ?, ?, ?)";
		try {
			Date fechaInicioFormat = Date.valueOf(inputEstanciaEntrada.getValue());
			Date fechaSalidaFormat = Date.valueOf(inputEstanciaSalida.getValue());

			
			pst = conn.prepareStatement(sql);
			pst.setString(1, inputEstanciaNombre.getText());
			pst.setDate(2, fechaInicioFormat);
			pst.setDate(3, fechaSalidaFormat);
			pst.setString(4, inputEstanciaHabitacion.getText());
			pst.setString(5, inputEstanciaHotel.getText());
			pst.execute();

			actualizarPantallaEstancias();
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
    @FXML
    void EstanciasEliminar(ActionEvent event) {
    	
    	String id = (inputEstanciaId.getText());
    	
		try {
			conn = Model.ConnectDb();

			String sql = "DELETE FROM estancias WHERE id = '" + id + "' ";

			pst = conn.prepareStatement(sql);
			pst.execute();
			actualizarPantallaEstancias();
		} catch (Exception e) {
			System.out.println(e);
		}
		
    }

	@FXML
	void getSelected(MouseEvent event) {
		index = table.getSelectionModel().getSelectedIndex();

		if (index <= -1) {
			return;
		}

		txt_habitacion.setText(habitacion.getCellData(index).toString());
		txt_hotel.setText(hotel.getCellData(index).toString());
		txt_precio.setText(precio.getCellData(index).toString());

		selectedHabitacion = habitacion.getCellData(index).toString();
		selectedCodHotel = hotel.getCellData(index).toString();
	}
	
	@FXML
	void getSelectedEstancias(MouseEvent event) {
		
		index = tableEstancias.getSelectionModel().getSelectedIndex();
		
		System.out.println(index);
		if (index <= -1) {
			return;
		}
		
		inputEstanciaId.setText(estanciasId.getCellData(index).toString());
		inputEstanciaNombre.setText(estanciasNombre.getCellData(index).toString());
		inputEstanciaEntrada.setValue(LocalDate.parse(estanciasEntrada.getCellData(index).toString()));
		inputEstanciaSalida.setValue(LocalDate.parse(estanciasSalida.getCellData(index).toString()));
		
		inputEstanciaHabitacion.setText(estanciasHabitacion.getCellData(index).toString());
		inputEstanciaHotel.setText(estanciasHotel.getCellData(index).toString());
	}

	@FXML
	public void Edit() {
		try {
			conn = Model.ConnectDb();
			String value1 = txt_habitacion.getText();
			String value2 = txt_hotel.getText();
			String value3 = txt_precio.getText();

			String sql = "UPDATE habitaciones SET preciodia = '" + value3 + "', numHabitacion = '" + value1
					+ "', codHotel = '" + value2 + "'" + "WHERE numHabitacion = '" + selectedHabitacion
					+ "' AND codHotel = '" + selectedCodHotel + "';";

			pst = conn.prepareStatement(sql);
			pst.execute();
			actualizarPantalla();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@FXML
	public void modifyEstancias() {
		try {
			conn = Model.ConnectDb();
			
			Date fechaInicioFormat = Date.valueOf(inputEstanciaEntrada.getValue());
			Date fechaSalidaFormat = Date.valueOf(inputEstanciaSalida.getValue());
			
			String id = inputEstanciaId.getText();
			String value1 = inputEstanciaNombre.getText();
			LocalDate value2 = inputEstanciaEntrada.getValue();
			LocalDate value3 = inputEstanciaSalida.getValue();
			String value4 = inputEstanciaHabitacion.getText();
			String value5 = inputEstanciaHotel.getText();

			String sql = "UPDATE estancias SET nombre = '" + value1 + "', fechaInicio = '" + fechaInicioFormat + "', fechaFin = '"+ fechaSalidaFormat +"', numHabitacion = '" + value4 + "', codHotel = '" + value5 + "' WHERE id = '" + id + "';";

			pst = conn.prepareStatement(sql);
			pst.execute();
			actualizarPantallaEstancias();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	void eliminarHabitacion(ActionEvent event) {

		try {
			conn = Model.ConnectDb();

			String sql = "DELETE FROM habitaciones WHERE numHabitacion = '" + selectedHabitacion + "' AND codHotel =  '"
					+ selectedCodHotel + "' ";

			pst = conn.prepareStatement(sql);
			pst.execute();
			actualizarPantalla();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void resetInputs() {
		
		txt_habitacion.setText("");
		txt_hotel.setText("");
		txt_precio.setText("");
		
	}

	public void actualizarPantalla() {
		habitacion.setCellValueFactory(new PropertyValueFactory<Habitaciones, String>("habitacion"));
		hotel.setCellValueFactory(new PropertyValueFactory<Habitaciones, String>("hotel"));
		precio.setCellValueFactory(new PropertyValueFactory<Habitaciones, String>("precio"));
		listHabitaciones = Model.getDatausers();
		table.setItems(listHabitaciones);
		resetInputs();
	}
	
	public void actualizarPantallaEstancias() {
		estanciasId.setCellValueFactory(new PropertyValueFactory<Estancias, String>("id"));
		estanciasNombre.setCellValueFactory(new PropertyValueFactory<Estancias, String>("nombre"));
		estanciasEntrada.setCellValueFactory(new PropertyValueFactory<Estancias, String>("entrada"));
		estanciasSalida.setCellValueFactory(new PropertyValueFactory<Estancias, String>("salida"));
		estanciasHabitacion.setCellValueFactory(new PropertyValueFactory<Estancias, String>("numHabitacion"));
		estanciasHotel.setCellValueFactory(new PropertyValueFactory<Estancias, String>("codHotel"));

		listEstancias = Model.getDataEstancias();
		tableEstancias.setItems(listEstancias);
	}

	public GridPane getView() {
		return view;
	}

	public void initialize(URL location, ResourceBundle resources) {
		actualizarPantalla();
		actualizarPantallaEstancias();
	}

}
