package compiere.model.dynamic.processes;

import java.awt.Container;
import java.util.logging.Level;

import org.compiere.apps.ADialog;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import compiere.model.dynamic.X_XX_VMA_RelSeason;

/**
 * 
 * Esta clase contiene el proceso que se va a ejecutar al momento de agregar 
 * una temporada a una colecci�n.
 * 
 * @author Mar�a Vintimilla
 * @version 1.0
 */

public class XX_VMA_AddSeason extends SvrProcess{

	private int Season_ID = 0;
	private int Collection_ID = 0;
	
	protected void prepare() {	
		
		ProcessInfoParameter[] para = getParameter();
		for(int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;			
			else if (name.equals("XX_VMA_Season_ID"))
				Season_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		Collection_ID = getRecord_ID();
		
	}//prepare
	
	/**
	 * Funci�n que se encarga de asociar una colecci�n a una campa�a
	 */
	protected String doIt() throws Exception {
		X_XX_VMA_RelSeason cc = 
			new X_XX_VMA_RelSeason(Env.getCtx(),0,null);

		cc.setXX_VMR_Collection_ID(Collection_ID);
		cc.setXX_VMA_Season_ID(Season_ID);
		
		//se verifica si se salv� correctamente
		if(cc.save()){
			ADialog.info(1, new Container(),
					Msg.translate(Env.getCtx(), "XX_RelSeason"));
			return "";
		}
		else{
			ADialog.error(Env.WINDOW_INFO, null, "Error", 
					Msg.translate(Env.getCtx(), "XX_ErrorSeason"));
			throw new Exception("No se agreg� la temporada");
		}	
		
	} // doIt
	
} // XX_VMA_AddSeason
