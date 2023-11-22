
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
 
public class Main extends Application {
	
	private double width = 1000;
	private double height = 500;
	
	private SQLHandler sql = new SQLHandler();
	
	private Stage stage;
	
	private TableView<Data> table1 = new TableView<>();
	private TableView<SimData> table2 = new TableView<>();
	private TableView<ResData> table3 = new TableView<>();
	private TableView<DagtData> table4 = new TableView<>();
	private TableView<KalenderData> table5 = new TableView<>();
	private TableView<DefaulterData> table7 = new TableView<>();
	
	//menu
	private HBox hbox;
	private Button dagBtn;
	private Button simBtn;
	private Button resBtn;
	private Button dtypBtn;
	private Button kalBtn;
	private Button parBtn;
	private Button defBtn;
	private Button grafBtn;
	
	private Scene scene1;
	private BorderPane root1;
	
	private Scene scene2;
	private BorderPane root2;
	
	private Scene scene3;
	private BorderPane root3;
	
	private Scene scene4;
	private BorderPane root4;
	
	private Scene scene5;
	private BorderPane root5;
	
	private Scene scene6;
	private BorderPane root6;
	
	private Scene scene7;
	private BorderPane root7;
	
	private Scene scene8;
	private BorderPane root8;

	
	@Override
    public void start(Stage primaryStage) {
    	
    	stage = primaryStage;
    	stage.setTitle("El Pogram");
        
        scene1 = createScene1();
        scene2 = createScene2();
        scene3 = createScene3();
        scene4 = createScene4();
        scene5 = createScene5();
        scene6 = createScene6();
        scene7 = createScene7();
        scene8 = createScene8();
        
        
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	@Override
            public void handle(WindowEvent e) {
        		
        		sql.closeConnection();
        		System.out.println("exit!");
        	}
        });
        
       /*stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.width = stage.getWidth();
            System.out.println(this.width);
       });

       stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.height = stage.getHeight();
            System.out.println(this.height);
       });*/
        
        stage.setScene(scene1); 
        stage.setResizable(true);
        stage.show();
        
    }
	
	private Scene createScene1() {
				
		//huvudroten
		root1 = new BorderPane();
				
		//HBox för meny knappar
		/*hbox1 = new HBox(10);
		hbox1.setPadding(new Insets(10,10,10,10));
				
		//menyknappar
		dagBtn1 = new Button("Dagdata");
		dagBtn1.setDisable(true);
		simBtn1 = new Button("Simulering");
		simBtn1.setOnAction(e -> switchScenes(scene2));
		resBtn1 = new Button("Resultat");
		resBtn1.setOnAction(e -> switchScenes(scene3));
		dtypBtn1 = new Button("Dagtyp");
		dtypBtn1.setOnAction(e -> switchScenes(scene4));
		parBtn1 = new Button("Parametrar");
		parBtn1.setOnAction(e -> switchScenes(scene5));
				
		hbox1.getChildren().add(dagBtn1);
		hbox1.getChildren().add(simBtn1);
		hbox1.getChildren().add(resBtn1);
		hbox1.getChildren().add(dtypBtn1);
		hbox1.getChildren().add(parBtn1);

		root1.setTop(hbox1);*/
		
		root1.setTop(createMenu(true, false, false, false, false, false, false, false));
		
		//TableView
		/*ObservableList<Data> dataLista = FXCollections.observableArrayList(
				new Data (null, 0, 0, 0, 0),
				new Data (null, 0, 0, 0, 0));*/
		
		//ObservableList<Data> dataLista = FXCollections.observableArrayList();
		
		//table = new TableView<>(dataLista);
		//table.setItems(dataLista);
		
		root1.setCenter(table1);
		
		TableColumn<Data, Date> c1 = new TableColumn<>("date");
		c1.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<Data, Integer> c2 = new TableColumn<>("timme");
		c2.setCellValueFactory(new PropertyValueFactory<>("timme"));
		
		TableColumn<Data, Double> c3 = new TableColumn<>("SMHIpris");
		c3.setCellValueFactory(new PropertyValueFactory<>("kostnad"));
		
		TableColumn<Data, Double> c4 = new TableColumn<>("SMHItemp");
		c4.setCellValueFactory(new PropertyValueFactory<>("temp"));
		
		TableColumn<Data, Integer> c5 = new TableColumn<>("tmin");
		c5.setCellValueFactory(new PropertyValueFactory<>("tmin"));
		
		TableColumn<Data, Integer> c6 = new TableColumn<>("tmax");
		c6.setCellValueFactory(new PropertyValueFactory<>("tmax"));

		
		table1.getColumns().addAll(c1, c2, c3, c4, c5, c6);
		
		//TextFields datum
		HBox textFields1 = new HBox();
		textFields1.setPadding(new Insets(10,10,10,10));
		textFields1.setStyle("-fx-spacing: 10");
		TextField fromDate = new TextField();
		fromDate.setPromptText("YYMMDDHH");
		fromDate.setStyle("-fx-font-size: 18");
		TextField toDate = new TextField();
		toDate.setPromptText("YYMMDDHH");
		toDate.setStyle("-fx-font-size: 18");
		Label from = new Label("From: ");
		Label to = new Label(" To: ");
		
		//ButtonUpdate
		Button updateBtn1 = new Button("Update");
		updateBtn1.setOnAction(e -> {
			String dateFrom = fromDate.getText().trim();
			String dateTo = toDate.getText().trim();
			ObservableList<Data> datesDataLista = FXCollections.observableArrayList(sql.getBetweenDatesData(dateFrom, dateTo));
			table1.setItems(datesDataLista);
		});
		
		textFields1.setAlignment(Pos.CENTER);
		textFields1.getChildren().addAll(from, fromDate, to, toDate, updateBtn1);
		
		root1.setBottom(textFields1);
				
		scene1 = new Scene(root1, this.width, this.height); 
		return scene1;
	}
	
	private Scene createScene2() {
		//huvudroten
		root2 = new BorderPane();
				
		root2.setTop(createMenu(false, true, false, false, false, false, false, false));

		//tableview
		root2.setCenter(table2);
		
		TableColumn<SimData, Integer> c1 = new TableColumn<>("ordning");
		c1.setCellValueFactory(new PropertyValueFactory<>("ordning"));
		
		TableColumn<SimData, Date> c2 = new TableColumn<>("date");
		c2.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<SimData, Integer> c3 = new TableColumn<>("timme");
		c3.setCellValueFactory(new PropertyValueFactory<>("timme"));
		
		TableColumn<SimData, Integer> c4 = new TableColumn<>("min");
		c4.setCellValueFactory(new PropertyValueFactory<>("min"));
		
		TableColumn<SimData, Double> c5 = new TableColumn<>("pris");
		c5.setCellValueFactory(new PropertyValueFactory<>("kostnad"));
		
		TableColumn<SimData, Double> c6 = new TableColumn<>("tmin");
		c6.setCellValueFactory(new PropertyValueFactory<>("tmin"));
		
		TableColumn<SimData, Double> c7 = new TableColumn<>("tmax");
		c7.setCellValueFactory(new PropertyValueFactory<>("tmax"));
		
		TableColumn<SimData, Integer> c8 = new TableColumn<>("tempNed");
		c8.setCellValueFactory(new PropertyValueFactory<>("tempNed"));
		
		TableColumn<SimData, Integer> c9 = new TableColumn<>("tempUpp");
		c9.setCellValueFactory(new PropertyValueFactory<>("tempUpp"));
		
		TableColumn<SimData, Integer> c10 = new TableColumn<>("paav");
		c10.setCellValueFactory(new PropertyValueFactory<>("paav"));
		
		TableColumn<SimData, Integer> c11 = new TableColumn<>("Kostnad");
		c11.setCellValueFactory(new PropertyValueFactory<>("kostnadTotal"));
		
		TableColumn<SimData, Integer> c12 = new TableColumn<>("Temperatur");
		c12.setCellValueFactory(new PropertyValueFactory<>("temperaturTotal"));
		
		table2.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12);
		
		
		HBox textFields2 = new HBox();
		textFields2.setPadding(new Insets(10,10,10,10));
		textFields2.setStyle("-fx-spacing: 5");
		TextField dateField = new TextField();
		dateField.setPromptText("YYYYMMDD");
		//dateField.setStyle("-fx-font-size: 18");
		TextField tidField = new TextField();
		tidField.setPromptText("HH");
		//tidField.setStyle("-fx-font-size: 18");
		TextField idField = new TextField();
		idField.setPromptText("0000");
		//idField.setStyle("-fx-font-size: 18");
		TextField tempField = new TextField();
		tempField.setPromptText("00");
		//tempField.setStyle("-fx-font-size: 18");
		Label date = new Label("Date:");
		Label tid = new Label("Tid:");
		Label altid = new Label("Alt_id:");
		Label temp = new Label("Temp:");
		
		Button updateBtn2 = new Button("Update");
		updateBtn2.setOnAction(e -> {
			String datestr = dateField.getText().trim();
			String tidstr = tidField.getText().trim();
			String altidstr = idField.getText().trim();
			String tempstr = tempField.getText().trim();

			ObservableList<SimData> dataLista = FXCollections.observableArrayList(
					sql.getSimData(datestr, tidstr, altidstr, tempstr));
			table2.setItems(dataLista);
			
		});		
		textFields2.setAlignment(Pos.CENTER);
		textFields2.getChildren().addAll(date, dateField, tid, tidField, altid, idField, temp, tempField, updateBtn2);
		
		root2.setBottom(textFields2);
				
		scene2 = new Scene(root2, this.width, this.height);		
		return scene2;
	}
	
	private Scene createScene3() { //RESULTAT
		root3 = new BorderPane();

		root3.setTop(createMenu(false, false, true, false, false, false, false, false));
		
		root3.setCenter(table3);
		
		TableColumn<ResData, String> c1 = new TableColumn<>("bertid");
		c1.setCellValueFactory(new PropertyValueFactory<>("bertid"));
		
		TableColumn<ResData, String> c2 = new TableColumn<>("typ");
		c2.setCellValueFactory(new PropertyValueFactory<>("typ"));
		
		TableColumn<ResData, Integer> c3 = new TableColumn<>("alt_id");
		c3.setCellValueFactory(new PropertyValueFactory<>("alt_id"));
		
		TableColumn<ResData, Integer> c4 = new TableColumn<>("date");
		c4.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<ResData, Integer> c5 = new TableColumn<>("timme");
		c5.setCellValueFactory(new PropertyValueFactory<>("timme"));
		
		TableColumn<ResData, Double> c6 = new TableColumn<>("temp");
		c6.setCellValueFactory(new PropertyValueFactory<>("temp"));
		
		table3.getColumns().addAll(c1, c2, c3, c4, c5, c6);
		
		HBox textFields3 = new HBox();
		textFields3.setPadding(new Insets(10,10,10,10));
		textFields3.setStyle("-fx-spacing: 10");
		TextField dateField = new TextField();
		dateField.setPromptText("YYYYMMDD"); //TEST
		dateField.setStyle("-fx-font-size: 18");
		TextField tidField = new TextField();
		tidField.setPromptText("HH");
		tidField.setStyle("-fx-font-size: 18");
		Label dateFrom = new Label("Datum: ");
		Label hour = new Label(" Timme: ");
		
		Button updateBtn3 = new Button("Update");
		updateBtn3.setOnAction(e -> {
			String datestr = dateField.getText().trim();
			String tidstr = tidField.getText().trim();

			ObservableList<ResData> dataLista = FXCollections.observableArrayList(
					sql.getResultatData(datestr, tidstr));
			table3.setItems(dataLista);
			
		});
		
		textFields3.setAlignment(Pos.CENTER);
		textFields3.getChildren().addAll(dateFrom, dateField, hour, tidField, updateBtn3);
		
		root3.setBottom(textFields3);
		
		scene3 = new Scene(root3, this.width, this.height);		
		return scene3;
	}
	
	private Scene createScene4() { //DAGTYP
		root4 = new BorderPane();
		root4.setTop(createMenu(false, false, false, true, false, false , false, false));
		
		root4.setCenter(table4);
		ObservableList<DagtData> dataLista = FXCollections.observableArrayList(
				sql.getDagtData());
		table4.setItems(dataLista);
		
		TableColumn<DagtData, String> c1 = new TableColumn<>("dagtyp");
		c1.setCellValueFactory(new PropertyValueFactory<>("dagtyp"));
		
		TableColumn<DagtData, Integer> c2 = new TableColumn<>("timme");
		c2.setCellValueFactory(new PropertyValueFactory<>("timme"));
		
		TableColumn<DagtData, Integer> c3 = new TableColumn<>("tmin");
		c3.setCellValueFactory(new PropertyValueFactory<>("tmin"));
		
		TableColumn<DagtData, Integer> c4 = new TableColumn<>("tmax");
		c4.setCellValueFactory(new PropertyValueFactory<>("tmax"));
		
		table4.getColumns().addAll(c1, c2, c3, c4);
		
		HBox textFields4 = new HBox();
		textFields4.setPadding(new Insets(10,10,10,10));
		textFields4.setStyle("-fx-spacing: 5");
		TextField typField = new TextField();
		typField.setPromptText("dagtyp");
		//dateField.setStyle("-fx-font-size: 18");
		TextField timmeField = new TextField();
		timmeField.setPromptText("timme");
		//tidField.setStyle("-fx-font-size: 18");
		TextField minField = new TextField();
		minField.setPromptText("tmin");
		//idField.setStyle("-fx-font-size: 18");
		TextField maxField = new TextField();
		maxField.setPromptText("tmax");
		//tempField.setStyle("-fx-font-size: 18");
		
		Button updateBtn4 = new Button("Update");
		//FUNKTION
		
		updateBtn4.setOnAction(e -> {
			String typ = typField.getText().trim();
			String timme = timmeField.getText().trim();
			String tmin = minField.getText().trim();
			String tmax = maxField.getText().trim();
			
			if(sql.ifKeyDagtExists(typ, timme)) { //REFACT Anv samma lista
				//System.out.println("Finns");
				if(tmin.isEmpty() && tmax.isEmpty()) { 
					//delete
					sql.deleteDagtData(typ, timme);
					ObservableList<DagtData> newDataLista = FXCollections.observableArrayList(
							sql.getDagtData());
					table4.setItems(newDataLista);

				} else {
					//update
					sql.updateDagtData(typ, timme, tmin, tmax);
					ObservableList<DagtData> newDataLista = FXCollections.observableArrayList(
							sql.getDagtData());
					table4.setItems(newDataLista);
				}
			} else {
				//insert
				sql.insertDagtData(typ, timme, tmin, tmax);
				ObservableList<DagtData> newDataLista = FXCollections.observableArrayList(
						sql.getDagtData());
				table4.setItems(newDataLista);
			}
		});
		
		textFields4.setAlignment(Pos.CENTER);
		textFields4.getChildren().addAll(typField, timmeField, minField, maxField, updateBtn4);
		
		root4.setBottom(textFields4);
		
		scene4 = new Scene(root4, this.width, this.height);		
		return scene4;
	}
	
	private Scene createScene5() {
		root5 = new BorderPane();

		root5.setTop(createMenu(false, false, false, false, true, false ,false, false));
		
		root5.setCenter(table5);
		ObservableList<KalenderData> dataLista = FXCollections.observableArrayList(
				sql.getKalenderData());
		table5.setItems(dataLista);
		
		table5.setEditable(true);
		
		TableColumn<KalenderData, Date> c1 = new TableColumn<>("date");
		c1.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<KalenderData, Integer> c2 = new TableColumn<>("dagtyp");
		c2.setCellValueFactory(new PropertyValueFactory<>("dagTyp"));
		c2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		c2.setOnEditCommit(e -> { 
			KalenderData v = e.getRowValue(); 
			v.setDagTyp(e.getNewValue());
			sql.updateKalender(v.getDate(), v.getDagTyp());

		});
		
		table5.getColumns().addAll(c1, c2);
		
		scene5 = new Scene(root5, this.width, this.height);		
		return scene5;
	}
	
	private Scene createScene6() {
		root6 = new BorderPane();
		
		root6.setTop(createMenu(false, false, false, false, false, true, false, false));
		
		HBox textFields6 = new HBox();
		textFields6.setPadding(new Insets(10,10,10,10));
		textFields6.setStyle("-fx-spacing: 10");
		TextField alfaField = new TextField();
		alfaField.setText(String.valueOf(sql.getpaaAlfa())); 
		alfaField.setStyle("-fx-font-size: 18");
		TextField betaField = new TextField();
		betaField.setText(String.valueOf(sql.getavBeta())); 
		betaField.setStyle("-fx-font-size: 18");
		Label alfa = new Label("paaAlfa: ");
		Label beta = new Label(" avBeta: ");
		
		Button updateBtn6 = new Button("Update");
		updateBtn6.setOnAction(e -> {
			String alfastr = alfaField.getText().trim();
			String betastr = betaField.getText().trim();
			
			sql.updateAlfaBeta(alfastr, betastr);
			alfaField.setText(String.valueOf(sql.getpaaAlfa())); 
			betaField.setText(String.valueOf(sql.getavBeta())); 
	
		});
		
		textFields6.setAlignment(Pos.CENTER);
		textFields6.getChildren().addAll(alfa, alfaField, beta, betaField, updateBtn6);

		root6.setCenter(textFields6);
		
		scene6 = new Scene(root6, this.width, this.height);		
		return scene6;
	}
	
	private Scene createScene7() {
		root7 = new BorderPane();
		
		root7.setTop(createMenu(false, false, false, false, false, false, true, false));
		
		root7.setCenter(table7);
		ObservableList<DefaulterData> dataLista = FXCollections.observableArrayList(
				sql.getDefaulterData());
		table7.setItems(dataLista);
		
		TableColumn<DefaulterData, String> c1 = new TableColumn<>("xdefaulter");
		c1.setCellValueFactory(new PropertyValueFactory<>("xdefaulter"));
		TableColumn<DefaulterData, String> c2 = new TableColumn<>("typ");
		c2.setCellValueFactory(new PropertyValueFactory<>("typ"));
		TableColumn<DefaulterData, Integer> c3 = new TableColumn<>("varde");
		c3.setCellValueFactory(new PropertyValueFactory<>("varde"));

		table7.getColumns().addAll(c1, c2, c3);
		
		HBox textFields7 = new HBox();
		textFields7.setPadding(new Insets(10,10,10,10));
		textFields7.setStyle("-fx-spacing: 5");
		TextField defaultField = new TextField();
		//defaultField.setPromptText("default");
		//dateField.setStyle("-fx-font-size: 18");
		TextField typField = new TextField();
		//typField.setPromptText("typ");
		//tidField.setStyle("-fx-font-size: 18");
		TextField vardeField = new TextField();
		vardeField.setPromptText("0.0");
		//idField.setStyle("-fx-font-size: 18");
		Label defaulter = new Label("xdefault:");
		Label typ = new Label("typ:");
		Label varde = new Label("varde:");
		
		Button updateBtn7 = new Button("Update");
		//funktion
		updateBtn7.setOnAction(e -> {
			String typstr = typField.getText().trim();
			String defaultstr = defaultField.getText().trim();
			String vardestr = vardeField.getText().trim();
			
			if(sql.ifKeyDefaulterExists(typstr)) {
				if(defaultstr.isEmpty() && vardestr.isEmpty()) {
					//delete
					sql.deleteDefaulterData(typstr);
					ObservableList<DefaulterData> newDataLista = FXCollections.observableArrayList(
							sql.getDefaulterData());
					table7.setItems(newDataLista);
				} else {
					//update
					sql.updateDefaulterData(defaultstr, typstr, vardestr);
					ObservableList<DefaulterData> newDataLista = FXCollections.observableArrayList(
							sql.getDefaulterData());
					table7.setItems(newDataLista);
				}
			} else {
				//insert
				sql.insertDefaulterData(defaultstr, typstr, vardestr);
				ObservableList<DefaulterData> newDataLista = FXCollections.observableArrayList(
						sql.getDefaulterData());
				table7.setItems(newDataLista);
			}
			
		});
		
		textFields7.setAlignment(Pos.CENTER);
		textFields7.getChildren().addAll(defaulter, defaultField, typ, typField, varde, vardeField, updateBtn7);
		
		root7.setBottom(textFields7);
		
		scene7 = new Scene(root7, this.width, this.height);		
		return scene7;
	}
	
	private Scene createScene8() {
		root8 = new BorderPane();
		root8.setTop(createMenu(false, false, false, false, false, false, false, true));
		
		//Graph
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Datum");
	    NumberAxis yAxis = new NumberAxis();
	    yAxis.setLabel("Temperatur C°");
	    
	    LineChart<String, Number> graph = new LineChart<String, Number>(xAxis, yAxis);
	    
	    //HDMIGRAFEN
	    XYChart.Series SMHIgraph = new XYChart.Series();
	    Map<String, Integer> HSMHIdata = sql.getSMHIBetweenDates(getOneWeekAgoDateTEST(), getCurrentDateTEST()); //ändra 
	    
	    for (Map.Entry<String,Integer> entry : HSMHIdata.entrySet()) {
	    	 SMHIgraph.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
	    }
	    
	    //OPTGRAF
	    XYChart.Series OptGraph = new XYChart.Series();
	    Map<String, Integer> OptData = sql.getOptBetweenDates(getOneWeekAgoDateTEST(), getCurrentDateTEST());
	    for (Map.Entry<String,Integer> entry : OptData.entrySet()) {
	    	 OptGraph.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
	    }
	    
	    //UTEMPGRAF
	    XYChart.Series utempGraph = new XYChart.Series();
	    Map<String, Integer> utempData = sql.getUtempBetweenDates(getOneWeekAgoDateTEST(), getCurrentDateTEST());
	    for (Map.Entry<String,Integer> entry : utempData.entrySet()) {
	    	 utempGraph.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
	    }
	    
	    //INNETEMPGRAF
	    XYChart.Series itempGraph = new XYChart.Series();
	    Map<String, Integer> itempData = sql.getItempBetweenDates(getOneWeekAgoDateTEST(), getCurrentDateTEST());
	    for (Map.Entry<String,Integer> entry : itempData.entrySet()) {
	    	 itempGraph.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
	    }
	    
	    //namnge grafer
	    SMHIgraph.setName("SMHI");
	    OptGraph.setName("Optimering");
	    utempGraph.setName("UteTemp");
	    itempGraph.setName("InneTemp");
	    graph.getData().addAll(SMHIgraph, OptGraph, utempGraph, itempGraph); 
	    
	    StackPane pane = new StackPane(graph);
	    pane.setPadding(new Insets(15, 15, 15, 15));
	    //graph.setStyle("-fx-background-color: WHITE");
		
		Label label = new Label("Graf");
		root8.setCenter(pane);
		
		//text fält
		HBox textFields8 = new HBox();
		textFields8.setPadding(new Insets(10,10,10,10));
		textFields8.setStyle("-fx-spacing: 5");
		
		TextField fromField = new TextField( getOneWeekAgoDateTEST()); //ändra
		fromField.setStyle("-fx-font-size: 18");
		TextField toField = new TextField(getCurrentDateTEST()); //ändra
		toField.setStyle("-fx-font-size: 18");
		Label dash = new Label("—");
		
		//Update Button
		Button updateBtn8 = new Button("Update");
		//Funktion
		
		textFields8.setAlignment(Pos.CENTER);
		textFields8.getChildren().addAll(fromField, dash, toField, updateBtn8 );
		
		root8.setBottom(textFields8);
		
		scene8 = new Scene(root8, this.width, this.height);
		return scene8;
	}
	
	private String getCurrentDateTEST() { 
		//System.out.println(getCurrentDate()); //TEST
		return "20230811";
	}
	
	private String getOneWeekAgoDateTEST() {
		//System.out.println(getOneWeekAgoDate()); //TEST
		return "20230810";
	}
	
	private String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		 String strDate = "";
		
		 Date todayDate = new Date();
		 strDate = formatter.format(todayDate);
		 return strDate;
	}
	
	private String getOneWeekAgoDate() { 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = "";
		
		Date todayDate = new Date(); //NU TID

		final int DAYS = -7; 
				
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(todayDate);
		calendar2.add(Calendar.DAY_OF_MONTH, DAYS); //add one hour	    
		Date newDate = calendar2.getTime();
		
		strDate = formatter.format(newDate);
				
		return strDate;
	}
	
	private HBox createMenu(Boolean dag, Boolean sim, Boolean res, Boolean dtyp, Boolean kal, Boolean par, Boolean def, Boolean graf) {
		hbox = new HBox(10);
		hbox.setPadding(new Insets(10,10,10,10));
						
		dagBtn = new Button("Dagdata");
		dagBtn.setOnAction(e -> switchScenes(scene1));
		dagBtn.setDisable(dag);
		simBtn = new Button("Simulering");
		simBtn.setOnAction(e -> switchScenes(scene2));
		simBtn.setDisable(sim);
		resBtn = new Button("Resultat");
		resBtn.setOnAction(e -> switchScenes(scene3));
		resBtn.setDisable(res);
		dtypBtn = new Button("Dagtyp");
		dtypBtn.setOnAction(e -> switchScenes(scene4));
		dtypBtn.setDisable(dtyp);
		kalBtn = new Button("Kalender");
		kalBtn.setOnAction(e -> switchScenes(scene5));
		kalBtn.setDisable(kal);
		parBtn = new Button("Parametrar");
		parBtn.setOnAction(e -> switchScenes(scene6));
		parBtn.setDisable(par);
		defBtn = new Button("Defaulter");
		defBtn.setOnAction(e -> switchScenes(scene7));
		defBtn.setDisable(def);
		grafBtn = new Button("Graf");
		grafBtn.setOnAction(e -> switchScenes(scene8));
		grafBtn.setDisable(graf);
		
		
		hbox.getChildren().add(dagBtn);
		hbox.getChildren().add(simBtn);
		hbox.getChildren().add(resBtn);
		hbox.getChildren().add(dtypBtn);
		hbox.getChildren().add(kalBtn);
		hbox.getChildren().add(parBtn);
		hbox.getChildren().add(defBtn);
		hbox.getChildren().add(grafBtn);
		
		return hbox;
	}
		
	private void switchScenes(Scene scene) {
		stage.setScene(scene);
	}
	
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
