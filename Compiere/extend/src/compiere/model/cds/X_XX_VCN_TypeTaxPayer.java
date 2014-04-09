/******************************************************************************
 * Product: Compiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2008 Compiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us at *
 * Compiere, Inc., 3600 Bridge Parkway #102, Redwood City, CA 94065, USA      *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package compiere.model.cds;

/** Generated Model - DO NOT CHANGE */
import java.sql.*;
import org.compiere.framework.*;
import org.compiere.util.*;
/** Generated Model for XX_VCN_TypeTaxPayer
 *  @author Jorg Janke (generated) 
 *  @version Release 3.6.2 - $Id: GenerateModel.java 8952 2010-06-16 07:52:26Z ragrawal $ */
public class X_XX_VCN_TypeTaxPayer extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_VCN_TypeTaxPayer_ID id
    @param trx transaction
    */
    public X_XX_VCN_TypeTaxPayer (Ctx ctx, int XX_VCN_TypeTaxPayer_ID, Trx trx)
    {
        super (ctx, XX_VCN_TypeTaxPayer_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_VCN_TypeTaxPayer_ID == 0)
        {
            setName (null);
            setXX_RetentionIndicator (false);
            setXX_VCN_TYPETAXPAYER_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_VCN_TypeTaxPayer (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27546906098789L;
    /** Last Updated Timestamp 2010-01-29 11:29:42.0 */
    public static final long updatedMS = 1264780782000L;
    /** AD_Table_ID=1000076 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_VCN_TypeTaxPayer");
        
    }
    ;
    
    /** TableName=XX_VCN_TypeTaxPayer */
    public static final String Table_Name="XX_VCN_TypeTaxPayer";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Description.
    @param Description Optional short description of the record */
    public void setDescription (String Description)
    {
        set_Value ("Description", Description);
        
    }
    
    /** Get Description.
    @return Optional short description of the record */
    public String getDescription() 
    {
        return (String)get_Value("Description");
        
    }
    
    /** Set Name.
    @param Name Alphanumeric identifier of the entity */
    public void setName (String Name)
    {
        if (Name == null) throw new IllegalArgumentException ("Name is mandatory.");
        set_Value ("Name", Name);
        
    }
    
    /** Get Name.
    @return Alphanumeric identifier of the entity */
    public String getName() 
    {
        return (String)get_Value("Name");
        
    }
    
    /** Get Record ID/ColumnName
    @return ID/ColumnName pair */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
        
    }
    
    /** Set Search Key.
    @param Value Search key for the record in the format required - must be unique */
    public void setValue (String Value)
    {
        set_Value ("Value", Value);
        
    }
    
    /** Get Search Key.
    @return Search key for the record in the format required - must be unique */
    public String getValue() 
    {
        return (String)get_Value("Value");
        
    }
    
    /** Set Retention Payment Indicator.
    @param XX_RetentionIndicator Indicador de Retenci�n de Pago */
    public void setXX_RetentionIndicator (boolean XX_RetentionIndicator)
    {
        set_Value ("XX_RetentionIndicator", Boolean.valueOf(XX_RetentionIndicator));
        
    }
    
    /** Get Retention Payment Indicator.
    @return Indicador de Retenci�n de Pago */
    public boolean isXX_RetentionIndicator() 
    {
        return get_ValueAsBoolean("XX_RetentionIndicator");
        
    }
    
    /** Set XX_VCN_TYPETAXPAYER_ID.
    @param XX_VCN_TYPETAXPAYER_ID Id de la Tabla Tipo de Contribuyente  */
    public void setXX_VCN_TYPETAXPAYER_ID (int XX_VCN_TYPETAXPAYER_ID)
    {
        if (XX_VCN_TYPETAXPAYER_ID < 1) throw new IllegalArgumentException ("XX_VCN_TYPETAXPAYER_ID is mandatory.");
        set_ValueNoCheck ("XX_VCN_TYPETAXPAYER_ID", Integer.valueOf(XX_VCN_TYPETAXPAYER_ID));
        
    }
    
    /** Get XX_VCN_TYPETAXPAYER_ID.
    @return Id de la Tabla Tipo de Contribuyente  */
    public int getXX_VCN_TYPETAXPAYER_ID() 
    {
        return get_ValueAsInt("XX_VCN_TYPETAXPAYER_ID");
        
    }
    
    
}
