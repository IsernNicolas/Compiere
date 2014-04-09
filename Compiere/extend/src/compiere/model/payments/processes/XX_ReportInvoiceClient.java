package compiere.model.payments.processes;

import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import compiere.model.cds.MInvoice;

/**
 * Actualiza el estado de las cuentas por pagar en la factura al ejecutar el proceso
 * @author Jessica Mendoza
 *
 */
public class XX_ReportInvoiceClient extends SvrProcess{

	@Override
	protected String doIt() throws Exception {

		MInvoice invoice = new MInvoice(Env.getCtx(), getRecord_ID(),get_TrxName());
		int compa�ia = invoice.getAD_Client_ID();
	/*	if (compa�ia == Env.getCtx().getContextAsInt("")) //Compa�ia: Asesoramientos Servicios y Consultas Norte 1965 C.A.
			//Llamar al proceso InvoiceClientASYCN.java y pasarle el id de la factura
		else if (compa�ia == Env.getCtx().getContextAsInt("")) //Compa�ia: Importadora San Pantaleon C.A.
			//Llamar al proceso InvoiceClientISP.java y pasarle el id de la factura
		else if (compa�ia == Env.getCtx().getContextAsInt("")) //Compa�ia: Inversiones Uvalda C.A.
			//Llamar al proceso InvoiceClientIU.java y pasarle el id de la factura
		*/
		return null;
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}
	
}
