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
package org.compiere.model;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.util.*;

/**
 *	RfQ Line Qty Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRfQLineQty.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRfQLineQty extends X_C_RfQLineQty
{
    /** Logger for class MRfQLineQty */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MRfQLineQty.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 	Get MRfQLineQty from Cache
	 *	@param ctx context
	 *	@param C_RfQLineQty_ID id
	 *	@return MRfQLineQty
	 */
	public static MRfQLineQty get (Ctx ctx, int C_RfQLineQty_ID)
	{
		Integer key = Integer.valueOf (C_RfQLineQty_ID);
		MRfQLineQty retValue = s_cache.get (ctx, key);
		if (retValue != null)
			return retValue;
		retValue = new MRfQLineQty (ctx, C_RfQLineQty_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static final CCache<Integer,MRfQLineQty>	s_cache	= new CCache<Integer,MRfQLineQty>("C_RfQLineQty", 20);
	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_RfQLineQty_ID id
	 *	@param trx transaction
	 */
	public MRfQLineQty (Ctx ctx, int C_RfQLineQty_ID, Trx trx)
	{
		super (ctx, C_RfQLineQty_ID, trx);
		if (C_RfQLineQty_ID == 0)
		{
		//	setC_RfQLine_ID (0);
		//	setC_UOM_ID (0);
			setIsOfferQty (false);
			setIsPurchaseQty (false);
			setQty (Env.ONE);	// 1
		}
	}	//	MRfQLineQty

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trx transaction
	 */
	public MRfQLineQty (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
		if (get_ID() > 0)
			s_cache.put (Integer.valueOf (get_ID()), this);
	}	//	MRfQLineQty
	
	/**
	 * 	Parent Constructor
	 *	@param line RfQ line
	 */
	public MRfQLineQty (MRfQLine line)
	{
		this (line.getCtx(), 0, line.get_Trx());
		setClientOrg(line);
		setC_RfQLine_ID (line.getC_RfQLine_ID());
	}	//	MRfQLineQty
	
	/**	Unit of Measure		*/
	private MUOM	m_uom = null;
	
	/**
	 * 	Get Uom Name
	 *	@return UOM
	 */
	public String getUomName()
	{
		if (m_uom == null)
			m_uom = MUOM.get(getCtx(), getC_UOM_ID());
		return m_uom.getName();
	}	//	getUomText
	
	/**
	 * 	Get active Response Qtys of this RfQ Qty
	 * 	@param onlyValidAmounts only valid amounts
	 *	@return array of response line qtys
	 */
	public MRfQResponseLineQty[] getResponseQtys (boolean onlyValidAmounts)
	{
		ArrayList<MRfQResponseLineQty> list = new ArrayList<MRfQResponseLineQty>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM C_RfQResponseLineQty WHERE C_RfQLineQty_ID=? AND IsActive='Y'";
		try
		{
			pstmt = DB.prepareStatement (sql, get_Trx());
			pstmt.setInt (1, getC_RfQLineQty_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MRfQResponseLineQty qty = new MRfQResponseLineQty(getCtx(), rs, get_Trx());
				if (onlyValidAmounts && !qty.isValidAmt())
					;
				else
					list.add (qty);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		MRfQResponseLineQty[] retValue = new MRfQResponseLineQty[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getResponseQtys
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MRfQLineQty[");
		sb.append(get_ID()).append(",Qty=").append(getQty())
			.append(",Offer=").append(isOfferQty())
			.append(",Purchase=").append(isPurchaseQty())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MRfQLineQty
