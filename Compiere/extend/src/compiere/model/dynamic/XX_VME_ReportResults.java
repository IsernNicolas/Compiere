package compiere.model.dynamic;

import java.util.Vector;

/**  XX_VMA_ReportResults
 * Clase que permite crear los items de un reporte de an�lisis de Resultados
 * 
 * @author mvintimilla
 * */

public class XX_VME_ReportResults {
	public Integer Season_ID = 0;
	public Integer Campaign_ID = 0;
	public Integer Activity_ID = 0;
	public Integer Brochure_ID = 0;
	public Integer BrochurePage_ID = 0;
	public Integer Element_ID = 0;
	public Integer Product_ID = 0;
	public Integer Category_ID = 0;
	public Integer Department_ID = 0;
	public Integer Line_ID = 0;
	public Integer Section_ID = 0;
	public Double VentasB = 0.0; 
	public Integer VentasP = 0;
	public Double InvIniB = 0.0;
	public Integer InvIniP = 0;
	public Double InvFinB = 0.0;
	public Integer InvFinP = 0;
	public Double PVP = 0.0;
	public Double Costo = 0.0;
	public String FechaInicio = "";
	public String FechaFinal = "";
	public String NombreTemporada = "";
	public String NombreCampana = "";
	public String NombreActividad = "";
	public String NombreFolleto = "";
	public String NombrePagina = "";
	public String NombreElemento = "";
	public String NombreProducto = "";
	public String NombreCategoria = "";
	public String NombreDepartamento = "";
	public String NombreLinea = "";
	public String NombreSeccion = "";
	public String Tipo = "";
	
	/** XX_VMA_ReportResults
	 * Resultados del Reporte de An�lisis
	 * @param Season_ID Temporada
	 * @param Campaign_ID Colecci�n
	 * @param Activity_ID Actividad de Mercadeo
	 * @param Brochure_ID Folleto
	 * @param BrochurePage_ID Pagina de Folleto
	 * @param Element_ID Elemento
	 * @param Product_ID Producto
	 * @param Category_ID Categor�a
	 * @param Department_ID Departamento
	 * @param Line_ID L�nea
	 * @param Section_ID Secci�n
	 * @param VentasB Ventas en BsF
	 * @param VentasP Ventas en Piezas
	 * @param InvIniB Inventario Inicial en BsF
	 * @param InvIniP Inventario Inicial en Piezas
	 * @param InvIniB Inventario Final en BsF
	 * @param InvFinP Inventario Final en Piezas
	 * @param PVP PVP
	 * @param Costo Costo
	 * @param FechaInicio Fecha de inicio de la AM
	 * @param FechaFinal Fecha de finalizaci�n de la AM
	 * @param NombreTemporada Nombre de Temporada
	 * @param NombreCampana Nombre de Campa�a
	 * @param NombreActividad Nombre de actividad de Mercadeo
	 * @param NombreFolleto Nombre de folleto
	 * @param NombrePagina Nombre de Pagina
	 * @param NombreElemento Nombre de elemento
	 * @param NombreCategoria Nombre de cartegor�a
	 * @param NombreDepartamento Nombre de departamento
	 * @param NombreLinea Nombre de l�nea
	 * @param NombreSeccion Nombre de Secci�n
	 **/
	public XX_VME_ReportResults(Integer PSeason_ID, Integer PCampaign_ID,
			Integer PActivity_ID, Integer PBrochure_ID, Integer PBrochurePage_ID,
			Integer PElement_ID, Integer PProduct_ID, Integer PCategory_ID, 
			Integer PDepartment_ID, Integer PLine_ID, Integer PSection_ID, 
			Double PVentasB,Integer PVentasP, Double PInvIniB,Integer PInvIniP, 
			Double PInvFinB,Integer PInvFinP, Double PPVP, Double PCosto, String 
			timestamp, String timestamp2,String PNombreTemporada,
			String PNombreCampana, String PNombreActividad,String PNombreFolleto,
			String PNombrePagina, String PNombreElemento, String PNombreProducto, 
			String PNombreCategoria, String PNombreDepartamento,String PNombreLinea,	
			String PNombreSeccion, String PTipo){
		Season_ID = PSeason_ID;
		Campaign_ID = PCampaign_ID;
		Activity_ID = PActivity_ID;
		Brochure_ID = PBrochure_ID;
		BrochurePage_ID = PBrochurePage_ID;
		Element_ID = PElement_ID;
		Product_ID = PProduct_ID;
		Category_ID = PCategory_ID;
		Department_ID = PDepartment_ID;
		Line_ID = PLine_ID;
		Section_ID = PSection_ID;
		VentasB = PVentasB; 
		VentasP = PVentasP;
		InvIniB = PInvIniB;
		InvIniP = PInvIniP;
		InvFinB = PInvFinB;
		InvFinP = PInvFinP;
		PVP = PPVP;
		Costo = PCosto;
		FechaInicio = timestamp;
		FechaInicio = timestamp;
		NombreTemporada = PNombreTemporada;
		NombreCampana = PNombreCampana;
		NombreActividad = PNombreActividad;
		NombreFolleto = PNombreFolleto;
		NombrePagina = PNombrePagina;
		NombreElemento = PNombreElemento;
		NombreProducto = PNombreProducto;
		NombreCategoria = PNombreCategoria;
		NombreDepartamento = PNombreDepartamento;
		NombreLinea = PNombreLinea;
		NombreSeccion = PNombreSeccion;
		Tipo = PTipo;
		
	}//constructor
	
	/** obtainValues
	 * Obtiene los valores iniciales para poder calcular los valores
	 * (Inventario inicial en Bsf, Inventario inicial en Piezas, 
	 * Inventario final en Bsf, Inventario final en Piezas,
	 * Ventas en BsF, Ventas en Piezas, PVP y Costo) 
	 * para el reporte de resultados por temporada 
	 * @param Report Items del reporte
	 * @param Type Tipo de calculo para hacer
	 * @param ID Identificador dependiendo del calculo
	 * @return Item con las cantidades calculadas 
	 * */
	//public XX_VMA_ReportResults obtainValues (Vector <XX_VMA_ReportResults> vectorReport,
	//Integer Type, XX_VMA_ReportResults item){
	public static Vector obtainValues (Vector <XX_VME_ReportResults> vectorReport,
			Integer Type, Integer ID, XX_VME_ReportResults item){
		// Variable que se devuelve con los resultados calculados
		Vector results = new Vector();
		Double InvIniB = 0.0;
		Integer InvIniP = 0;
		Double InvFinB = 0.0;
		Integer InvFinP = 0;
		Double VentasB = 0.0; 
		Integer VentasP = 0;
		Double PVP = 0.0;
		Double Costo = 0.0;
		switch (Type) {
		// Calcular Por Temporada
		case 1:
			for(int j = 0; j < vectorReport.size(); j++){
//				System.out.println("Reporte Temporada\nElemento: "+vectorReport.get(j).Element_ID+
//						" Producto: "+vectorReport.get(j).Product_ID+ " InvIniB: "+vectorReport.get(j).InvIniB+
//						" InvIniP: "+vectorReport.get(j).InvIniP+
//						" VentasB: "+vectorReport.get(j).VentasB+" VentasP: "+vectorReport.get(j).VentasP+
//						" PVP: "+vectorReport.get(j).PVP +" Costo: "+vectorReport.get(j).Costo);
				if(vectorReport.get(j).Season_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
					
				} // if Season 
			} // for season
			break;
		// Calcular Por Campa�a
		case 2:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Campaign_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Campa�a 
			} // for Campa�a
			break;
		// Calcular Por Actividad de Mercadeo
		case 3:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Activity_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Actividad de Mercadeo 
			} // for Actividad de Mercadeo
			break;
		// Calcular Por Folleto
		case 4:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Brochure_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Folleto
			} // for Folleto
			break;
		// Calcular Por Pagina de Folleto
		case 5:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).BrochurePage_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Pagina de Folleto
			} // for Pagina de Folleto
			break;
		// Calcular Por Elemento
		case 6:
			break;
		// Calcular Por Categoria
		case 7:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Category_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Categoria
			} // for Categoria
			break;
		// Calcular Por Departmento
		case 8:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Department_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Departmento
			} // for Departmento
			break;
		// Calcular Por Linea
		case 9:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Line_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Linea
			} // for Linea
			break;
		// Calcular Por Seccion
		case 10:
			for(int j = 0; j < vectorReport.size(); j++){
				if(vectorReport.get(j).Section_ID.equals(ID)) {
					InvIniB = InvIniB + vectorReport.get(j).InvIniB;
					InvIniP = InvIniP + vectorReport.get(j).InvIniP;
					InvFinB = InvFinB + vectorReport.get(j).InvFinB;
					InvFinP = InvFinP + vectorReport.get(j).InvFinP;
					VentasB = VentasB + vectorReport.get(j).VentasB;
					VentasP = VentasP + vectorReport.get(j).VentasP;
					PVP = PVP + vectorReport.get(j).PVP;
					Costo = Costo + vectorReport.get(j).Costo;
				} // if Seccion
			} // for Seccion
			break;

		default:
			break;
		}
		results.add(InvIniB);
		results.add(InvIniP);
		results.add(InvFinB);
		results.add(InvFinP); 
		results.add(PVP);
		results.add(Costo);
		results.add(VentasB);
		results.add(VentasP);
		return results;
	} // obtainSeason

}//Fin XX_VMA_ReportResults
