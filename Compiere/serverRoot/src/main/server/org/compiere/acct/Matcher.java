/******************************************************************************
 * Product: Compiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 3600 Bridge Parkway #102, Redwood City, CA 94065, USA      *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.*;
import java.sql.*;
import java.util.logging.*;
import org.compiere.util.*;

/**
 *	Automatic Matching.
 *	Inv
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Matcher.java 8778 2010-05-19 19:00:10Z ragrawal $
 */
public class Matcher
{
	/**
	 * 	Constructor
	 *  @param AD_Client_ID Client
	 * 	@param trx transaction
	 */
	public Matcher (int AD_Client_ID, Trx trx)
	{
		m_AD_Client_ID = AD_Client_ID;
		m_trxName = trx;
	}	//	Matcher

	/**	Client							*/
	private int					m_AD_Client_ID;
	/** Transaction						*/
	private Trx				m_trxName = null;
	/**	Logger							*/
	protected CLogger			log = CLogger.getCLogger (getClass());

	/**
	 * 	Matching
	 *  <pre>
	 *  Derive Invoice-Receipt Match from PO-Invoice and PO-Receipt
	 * 	Purchase Order (20)
	 *  - Invoice1 (10)
	 *  - Invoice2 (10)
	 *  - Receipt1 (5)
	 *  - Receipt2 (15)
	 *
	 * 	(a) Creates Directs
	 * 		- Invoice1 - Receipt1 (5)
	 * 		- Invoice2 - Receipt2 (10)
	 *
	 *  (b) Creates Indirects
	 * 		- Invoice1 - Receipt2 (5)
	 *  (Not imlemented)
	 *
	 *
	 *  </pre>
	 *  @return number of records created
	 */
	public int match()
	{
		int counter = 0;
		//	(a) Direct Matches
		String sql = "SELECT m1.AD_Client_ID,m2.AD_Org_ID, "			//	1..2
			+ "m1.C_InvoiceLine_ID,m2.M_InOutLine_ID,m1.M_Product_ID, "	// 	3..5
			+ "m1.DateTrx,m2.DateTrx, m1.Qty, m2.Qty "					//	6..9
			+ "FROM M_MatchPO m1, M_MatchPO m2 "
			+ "WHERE m1.C_OrderLine_ID=m2.C_OrderLine_ID"
			+ " AND m1.M_InOutLine_ID IS NULL"
			+ " AND m2.C_InvoiceLine_ID IS NULL"
			+ " AND m1.M_Product_ID=m2.M_Product_ID"
			+ " AND m1.AD_Client_ID=?"						//	#1
			+ "	AND NOT EXISTS (SELECT * FROM M_MatchInv mi "
			+ "WHERE mi.C_InvoiceLine_ID=m1.C_InvoiceLine_ID AND mi.M_InOutLine_ID=m2.M_InOutLine_ID)";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, (Trx) null);
			pstmt.setInt(1, m_AD_Client_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				BigDecimal qty1 = rs.getBigDecimal(8);
				BigDecimal qty2 = rs.getBigDecimal(9);
				BigDecimal Qty = qty1.min(qty2);
				if  (Qty.equals(Env.ZERO))
					continue;
				Timestamp dateTrx1 = rs.getTimestamp(6);
				Timestamp dateTrx2 = rs.getTimestamp(7);
				Timestamp DateTrx = dateTrx1;
				if (dateTrx1.before(dateTrx2))
					DateTrx = dateTrx2;
				//
				int AD_Client_ID = rs.getInt(1);
				int AD_Org_ID = rs.getInt(2);
				int C_InvoiceLine_ID = rs.getInt(3);
				int M_InOutLine_ID = rs.getInt(4);
				int M_Product_ID = rs.getInt(5);
				//
				if (createMatchInv(AD_Client_ID, AD_Org_ID,
					M_InOutLine_ID, C_InvoiceLine_ID,
					M_Product_ID, DateTrx, Qty))
					counter++;
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "match", e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		log.fine("Matcher.match - Client_ID=" + m_AD_Client_ID
			+ ", Records created=" + counter);
		return counter;
	}	//	match

	/**
	 * 	Create MatchInv record
	 *  @param AD_Client_ID Client
	 *  @param AD_Org_ID Org
	 *  @param M_InOutLine_ID Receipt
	 *  @param C_InvoiceLine_ID Invoice
	 *  @param M_Product_ID Product
	 *  @param DateTrx Date
	 *  @param Qty Qty
	 *  @return true if record created
	 */
	private boolean createMatchInv (int AD_Client_ID, int AD_Org_ID,
		int M_InOutLine_ID, int C_InvoiceLine_ID,
		int M_Product_ID, Timestamp DateTrx, BigDecimal Qty)
	{
		log.fine("InvLine=" + C_InvoiceLine_ID + ",Rec=" + M_InOutLine_ID + ", Qty=" + Qty + ", " + DateTrx);
		
	//	MMatchInv inv = new MMatchInv ();
		int M_MatchInv_ID = DB.getNextID (AD_Client_ID, "M_MatchInv", m_trxName);
		//
		StringBuffer sql = new StringBuffer("INSERT INTO M_MatchInv ("
			+ "M_MatchInv_ID, "
			+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy, "
			+ "M_InOutLine_ID,C_InvoiceLine_ID, "
			+ "M_Product_ID,DateTrx,Qty, "
			+ "Processing,Processed,Posted) VALUES (")
			.append("?,?,? ")
			.append(",'Y',SysDate,0,SysDate,0, ")
			.append(" ?,?,?,?,?")
			.append(", 'N','Y','N')");
		Object[] params = new Object[]{M_MatchInv_ID,AD_Client_ID,AD_Org_ID,M_InOutLine_ID,
										C_InvoiceLine_ID,M_Product_ID,DB.TO_DATE(DateTrx,true),Qty};
		int no = DB.executeUpdate(m_trxName, sql.toString(),params);
		return no == 1;
	}	//	createMatchInv
	
}	//	Matcher