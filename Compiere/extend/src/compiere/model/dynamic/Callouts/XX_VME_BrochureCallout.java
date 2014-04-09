package compiere.model.dynamic.Callouts;

import java.sql.SQLException;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Ctx;

import compiere.model.dynamic.XX_VME_GeneralFunctions;

/**
 * Esta contiene los callouts que se van a ejecutar en la pesta�a folleto, 
 * que se encuentra en la ventana Folleto.
 * 
 * @author Maria Vintimilla
 * @version 1.0
 */

public class XX_VME_BrochureCallout extends CalloutEngine {
	
	/**
	 * Se encarga de desactivar las paginas 
	 * asociadas. Esto se hace cuando se desactiva el
	 * Folleto.
	 * 
	 * @param ctx contexto
	 * @param WindowNo n�mero de la ventana
	 * @param mTab pesta�a de la ventana
	 * @param mField campo al cual se le aplica el callout
	 * @param value valor que se est� introduci�n
	 * @param oldValue valor que hab�a anteriormente
	 * @return String mensaje de error si hubo un problema, nulo si no.
	 * @throws SQLException 
	 */
	public String deactivateBrochure (Ctx ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue) throws SQLException{
		Integer folleto;
		folleto = ctx.getContextAsInt(WindowNo, "XX_VMA_Brochure_ID");
		
		// Se comprueba que el folleto existe
		if(folleto != 0){
			// Se verifica que se haya seleccionado desactivar el folleto
			if(ctx.getContext(WindowNo, "IsActive").equals("N")){
				XX_VME_GeneralFunctions.processBrochure(folleto, false, true);
			} //if active
			else {
				XX_VME_GeneralFunctions.processBrochure(folleto, true, true);
			}
		} // folleto
	
		return "";
	} // fin deactivateBrochure

} // fin XX_VMA_BrochureCallout
