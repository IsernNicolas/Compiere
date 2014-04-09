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
package org.compiere.report.core;

import java.math.*;
import java.sql.*;

import org.compiere.common.*;
import org.compiere.common.constants.*;
import org.compiere.model.*;
import org.compiere.util.*;

/**
 *  Report Column
 *
 *  @author Jorg Janke
 *  @version  $Id: RColumn.java,v 1.3 2006/08/10 01:00:13 jjanke Exp $
 */
public class RColumn
{
	/**
	 * 	Create Report Column
	 *	@param ctx context 
	 *	@param columnName column name
	 *	@param displayType display type
	 */
	public RColumn (Ctx ctx, String columnName, int displayType)
	{
		this (ctx, columnName, displayType, null, 0, null);
	}	//	RColumn

	/**
	 * 	Create Report Column
	 *	@param ctx context 
	 *	@param columnName column name
	 *	@param displayType display type
	 *	@param AD_Reference_Value_ID List/Table Reference
	 */
	public RColumn (Ctx ctx, String columnName, int displayType, int AD_Reference_Value_ID)
	{
		this (ctx, columnName, displayType, null, AD_Reference_Value_ID, null);
	}	//	RColumn

	/**
	 *  Create Report Column
	 *	@param ctx context 
	 *	@param columnName column name
	 *	@param displayType display type
	 *	@param sql sql (if null then columnName is used). 
	 *	Will be overwritten if TableDir or Search 
	 */
	public RColumn (Ctx ctx, String columnName, int displayType, String sql)
	{
		this (ctx, columnName, displayType, sql, 0, null);
	}
	/**
	 *  Create Report Column
	 *	@param ctx context 
	 *	@param columnName column name
	 *	@param displayType display type
	 *	@param sql sql (if null then columnName is used). 
	 *	@param AD_Reference_Value_ID List/Table Reference
	 *	@param refColumnName UserReference column name
	 *	Will be overwritten if TableDir or Search 
	 */
	public RColumn (Ctx ctx, String columnName, int displayType, 
		String sql,	int AD_Reference_Value_ID, String refColumnName)
	{
		m_colHeader = Msg.translate(ctx, columnName);
		if (refColumnName != null)
			m_colHeader = Msg.translate(ctx, refColumnName);
		m_displayType = displayType;
		m_colSQL = sql;
		if (m_colSQL == null || m_colSQL.length() == 0)
			m_colSQL = columnName;

		//  Strings
		if (FieldType.isText(displayType))
			m_colClass = String.class;                  //  default size=30
		//  Amounts
		else if (displayType == DisplayTypeConstants.Amount)
		{
			m_colClass = BigDecimal.class;
			m_colSize = 70;
		}
		//  Boolean
		else if (displayType == DisplayTypeConstants.YesNo)
			m_colClass = Boolean.class;
		//  Date
		else if (FieldType.isDate(displayType))
			m_colClass = Timestamp.class;
		//  Number
		else if (displayType == DisplayTypeConstants.Quantity 
			|| displayType == DisplayTypeConstants.Number  
			|| displayType == DisplayTypeConstants.CostPrice)
		{
			m_colClass = Double.class;
			m_colSize = 70;
		}
		//  Integer
		else if (displayType == DisplayTypeConstants.Integer)
			m_colClass = Integer.class;
		//  List
		else if (displayType == DisplayTypeConstants.List)
		{
			Language language = Language.getLanguage(Env.getAD_Language(ctx));
			m_colSQL = "(" + MLookupFactory.getLookup_ListEmbed(
				language, AD_Reference_Value_ID, columnName) + ")";
			m_colClass = String.class;
			m_isIDcol = false;
		}
		/**  Table
		else if (displayType == DisplayType.Table)
		{
			Language language = Language.getLanguage(Env.getAD_Language(ctx));
			m_colSQL += ",(" + MLookupFactory.getLookup_TableEmbed(
				language, columnName, RModel.TABLE_ALIAS, AD_Reference_Value_ID) + ")";
			m_colClass = String.class;
			m_isIDcol = false;
		}	**/
		//  TableDir, Search,...
		else
		{
			m_colClass = String.class;
			Language language = Language.getLanguage(Env.getAD_Language(ctx));
			if (columnName.equals("Account_ID") 
				|| columnName.equals("User1_ID") || columnName.equals("User2_ID"))
			{
				m_colSQL += ",(" + MLookupFactory.getLookup_TableDirEmbed(
					language, "C_ElementValue_ID", RModel.TABLE_ALIAS, columnName) + ")";
				m_isIDcol = true;
			}
			else if (columnName.startsWith("UserElement") && refColumnName != null)
			{
				m_colSQL += ",(" + MLookupFactory.getLookup_TableDirEmbed(
					language, refColumnName, RModel.TABLE_ALIAS, columnName) + ")";
				m_isIDcol = true;
			}
			else if (columnName.equals("C_LocFrom_ID") || columnName.equals("C_LocTo_ID"))
			{
				m_colSQL += ",(" + MLookupFactory.getLookup_TableDirEmbed(
					language, "C_Location_ID", RModel.TABLE_ALIAS, columnName) + ")";
				m_isIDcol = true;
			}
			else if (columnName.equals("AD_OrgTrx_ID"))
			{
				m_colSQL += ",(" + MLookupFactory.getLookup_TableDirEmbed(
					language, "AD_Org_ID", RModel.TABLE_ALIAS, columnName) + ")";
				m_isIDcol = true;
			}
			else if (displayType == DisplayTypeConstants.TableDir)
			{
				m_colSQL += ",(" + MLookupFactory.getLookup_TableDirEmbed(
					language, columnName, RModel.TABLE_ALIAS) + ")";
				m_isIDcol = true;
			}
		}
	}   //  RColumn

	/**
	 *  Create Info Column (r/o and not color column)
	 *
	 *  @param colHeader Column Header
	 *  @param colSQL    SQL select code for column
	 *  @param colClass  class of column - determines display
	 */
	public RColumn (String colHeader, String colSQL, Class<?> colClass)
	{
		m_colHeader = colHeader;
		m_colSQL = colSQL;
		m_colClass = colClass;
	}   //  RColumn


	/** Column Header               */
	private String      m_colHeader;
	/** Column SQL                  */
	private String      m_colSQL;
	/** Column Display Class        */
	private Class<?>       m_colClass;

	/** Display Type                */
	private int         m_displayType = 0;
	/** Column Size im px           */
	private int         m_colSize = 30;

	private boolean     m_readOnly = true;
	private boolean     m_colorColumn = false;
	private boolean     m_isIDcol = false;


	/**
	 *  Column Header
	 */
	public String getColHeader()
	{
		return m_colHeader;
	}
	public void setColHeader(String colHeader)
	{
		m_colHeader = colHeader;
	}

	/**
	 *  Column SQL
	 */
	public String getColSQL()
	{
		return m_colSQL;
	}
	public void setColSQL(String colSQL)
	{
		m_colSQL = colSQL;
	}
	/**
	 *  This column is an ID Column (i.e. two values - int & String are read)
	 */
	public boolean isIDcol()
	{
		return m_isIDcol;
	}

	/**
	 *  Column Display Class
	 */
	public Class<?> getColClass()
	{
		return m_colClass;
	}
	public void setColClass(Class<?> colClass)
	{
		m_colClass = colClass;
	}

	/**
	 *  Column Size in px
	 */
	public int getColSize()
	{
		return m_colSize;
	}   //  getColumnSize

	/**
	 *  Column Size in px
	 */
	public void setColSize(int colSize)
	{
		m_colSize = colSize;
	}   //  getColumnSize

	/**
	 *  Get DisplayType
	 */
	public int getDisplayType()
	{
		return m_displayType;
	}   //  getDisplayType

	/**
	 *  Column is Readonly
	 */
	public boolean isReadOnly()
	{
		return m_readOnly;
	}
	public void setReadOnly(boolean readOnly)
	{
		m_readOnly = readOnly;
	}

	/**
	 *  This Color Determines the Color of the row
	 */
	public void setColorColumn(boolean colorColumn)
	{
		m_colorColumn = colorColumn;
	}
	public boolean isColorColumn()
	{
		return m_colorColumn;
	}
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("RColumn[");
		sb.append(m_colSQL).append("=").append(m_colHeader)
			.append("]");
		return sb.toString();
	}	//	toString

}   //  RColumn