package compiere.model.cds.distribution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;


/**
 *  Clase concreta que almacena los m�todos de distribucion para el tipo de distribucion por presupuesto
 *  @author Javier Pino
 */
public class XX_SalesBudget extends XX_Distribution  {
	
	//Almacenan los porcentajes y ventas por tienda
	private XX_Budget presupuesto = null;
	private XX_Sales ventas = null;
	private boolean usarVentasA�oPasado = false;
	
	/** Constructor por defecto */
	public XX_SalesBudget(int cabeceraID, Ctx contexto, Trx trxNombre, int mes, int a�o, boolean ventasPasadas) {
		super(cabeceraID, contexto, trxNombre, mes, a�o);		
		usarVentasA�oPasado = ventasPasadas;
	}
	
	@Override
	public void finalizar() {
		finalizado = true;
	}

	/** Metodo heredado que indica los pasos necesarios para inicializar esta clase
	 * */
	@Override	
	public void inicializar()  {
		
		int a�oVentas, mesVentas ;
		
		if (inicializado)
			return;
		if (usarVentasA�oPasado) {
			//Usar las venats del mismo mes del a�o pasado
			mesVentas = mes;
			a�oVentas = a�o - 1;
		} else {						
			//Entonces usar las ventas del mes pasado 
			if (mes == 1) {
				a�oVentas = a�o - 1; 
				mesVentas = 12;
			} else {
				a�oVentas = a�o;
				mesVentas = mes - 1;
			}
		}
		//a�oVentas = 2010; //TODO ELIMINAR
		//mesVentas = 11; //TODO ELIMINAR
		
		//Inicializar presupuesto usando clase hermana XX_Budget
		presupuesto = new XX_Budget(cabecera.get_ID(), ctx, nombreTrx, mes, a�o);
		presupuesto.inicializar();
		if (!presupuesto.inicializado) {
			inicializado = false;
			return;
		}		
		//Inicializar presupuesto usando clase hermana XX_Budget
		ventas = new XX_Sales(cabecera.get_ID(), ctx, nombreTrx, mesVentas, a�oVentas);
		ventas.inicializar();
		if (!ventas.inicializado) {
			inicializado = false;
			return;
		}				
		inicializado = true;	
	}

	/**
	 * Este metodo obtiene los procentajes asociados
	 * Calcula los porcentajes por presupuesto ventas
	 * El result set debe incluir las columnas (asi sean nulas) XX_VMR_DEPARTMENT_ID, XX_VMR_LINE_ID, XX_VMR_SECTION_ID
	 */
	@Override
	public XX_StoreAmounts obtenerPorcentaje(ResultSet rsDatosProducto) throws SQLException {
		
		//Calcular el presupuesto del producto de acuerdo a las reglas de seccion, linea, depto
		XX_StoreAmounts presupuestoProducto = presupuesto.obtenerPorcentaje(rsDatosProducto);
		if (presupuestoProducto == null) 
			return null;
		//Calcular las ventas
		XX_StoreAmounts ventasProducto = ventas.obtenerPorcentaje(rsDatosProducto);
		if (ventasProducto == null) 
			return null;
	
		//Aqui almacenar� el resultado mientras realizo el calculo
		Hashtable<Integer, Float> porcentajesTienda = new Hashtable<Integer, Float>(10) ;
		
		//Agregar presupuesto al hash
		for (int i = 0; i < presupuestoProducto.tiendas.size() ; i++) {									
			porcentajesTienda.put(presupuestoProducto.tiendas.elementAt(i), 
				presupuestoProducto.cantidades.elementAt(i));		
		}		
		//Agregar las ventas al hash
		Float porcentaje = 0.0f;
		int tienda = 0;
		for (int i = 0; i < ventasProducto.tiendas.size() ; i++) {
			tienda = ventasProducto.tiendas.elementAt(i);
			porcentaje = ventasProducto.cantidades.elementAt(i);
			if (porcentajesTienda.containsKey(tienda)) { 
				porcentaje += porcentajesTienda.get(tienda);				
			}
			porcentajesTienda.put(tienda, porcentaje/2);			
		}
		
		//Iteramos sobre la tabla de hash y creamos el resultado 
		XX_StoreAmounts resultado = new XX_StoreAmounts();
		for (Entry<Integer, Float> elemento:porcentajesTienda.entrySet()) {
			resultado.agregarTienda(elemento.getKey(), elemento.getValue());			
		}
		return resultado;
	}

	/** Modificado en caso de que queramos ver la informacion de la distribucion */
	@Override
	public String toString() {
		return "Presupuesto " + presupuesto.toString() + 
			" \n Ventas " + ventas.toString();
	}
		
		
	
}

	


