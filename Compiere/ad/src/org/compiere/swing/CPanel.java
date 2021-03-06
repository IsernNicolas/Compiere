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
package org.compiere.swing;

import java.awt.*;

import javax.swing.*;

import org.compiere.plaf.*;
import org.compiere.util.*;

/**
 *  Compiere Panel supporting colored Backgrounds
 *
 *  @author     Jorg Janke
 *  @version    $Id: CPanel.java 8244 2009-12-04 23:25:29Z freyes $
 */
public class CPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new CompierePanel with the specified layout manager
	 * and buffering strategy.
	 * @param layout  the LayoutManager to use
	 * @param isDoubleBuffered  a boolean, true for double-buffering, which
	 *        uses additional memory space to achieve fast, flicker-free updates
	 */
	public CPanel (LayoutManager layout, boolean isDoubleBuffered)
	{
		super (layout, isDoubleBuffered);
		init();
	}   //  CPanel

	/**
	 * Create a new buffered CPanel with the specified layout manager
	 * @param layout  the LayoutManager to use
	 */
	public CPanel (LayoutManager layout)
	{
		super (layout);
		init();
	}   //  CPanel

	/**
	 * Creates a new <code>CPanel</code> with <code>FlowLayout</code>
	 * and the specified buffering strategy.
	 * If <code>isDoubleBuffered</code> is true, the <code>CPanel</code>
	 * will use a double buffer.
	 * @param isDoubleBuffered  a boolean, true for double-buffering, which
	 *        uses additional memory space to achieve fast, flicker-free updates
	 */
	public CPanel (boolean isDoubleBuffered)
	{
		super (isDoubleBuffered);
		init();
	}   //  CPanel

	/**
	 * Creates a new <code>CPanel</code> with a double buffer and a flow layout.
	 */
	public CPanel()
	{
		super ();
		init();
	}   //  CPanel

	/**
	 * Creates a new <code>CPanel</code> with a double buffer and a flow layout.
	 * @param bc Initial Background Color
	 */
	public CPanel(CompiereColor bc)
	{
		this ();
		init();
		setBackgroundColor (bc);
	}   //  CPanel

	/**
	 *  Common init.
	 *  Compiere backround requires that for the base, background is set explictily.
	 *  The additional panels should be transparent.
	 */
	private void init()
	{
		setOpaque(false);	//	transparent
	}   //  init

	
	/**************************************************************************
	 *  Set Background - ignored by UI -
	 *  @param bg ignored
	 */
	@Override
	public void setBackground (Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		super.setBackground (bg);
		//  ignore calls from javax.swing.LookAndFeel.installColors(LookAndFeel.java:61)
		if (!Trace.getCallerClass(1).startsWith("javax"))
			setBackgroundColor (new CompiereColor(bg));
	}   //  setBackground

	/**
	 *  Set Background
	 *  @param bg CompiereColor for Background, if null set standard background
	 */
	public void setBackgroundColor (CompiereColor bg)
	{
		if (bg == null)
			bg = CompierePanelUI.getDefaultBackground();
		setOpaque(true);	//	not transparent
		putClientProperty(CompierePLAF.BACKGROUND, bg);
		super.setBackground (bg.getFlatColor());
	}   //  setBackground

	/**
	 *  Get Background
	 *  @return Color for Background
	 */
	public CompiereColor getBackgroundColor ()
	{
		try
		{
			return (CompiereColor)getClientProperty(CompierePLAF.BACKGROUND);
		}
		catch (Exception e)
		{
			System.err.println("CPanel - ClientProperty: " + e.getMessage());
		}
		return null;
	}   //  getBackgroundColor

	/*************************************************************************/

	/**
	 *  Set Tab Hierarchy Level.
	 *  Has only effect, if tabs are on left or right side
	 *
	 *  @param level
	 */
	public void setTabLevel (int level)
	{
		if (level == 0)
			putClientProperty(CompierePLAF.TABLEVEL, null);
		else
			putClientProperty(CompierePLAF.TABLEVEL, Integer.valueOf(level));
	}   //  setTabLevel

	/**
	 *  Get Tab Hierarchy Level
	 *  @return Tab Level
	 */
	public int getTabLevel()
	{
		try
		{
			Integer ll = (Integer)getClientProperty(CompierePLAF.TABLEVEL);
			if (ll != null)
				return ll.intValue();
		}
		catch (Exception e)
		{
			System.err.println("ClientProperty: " + e.getMessage());
		}
		return 0;
	}   //  getTabLevel

	
	/**************************************************************************
	 *  String representation
	 *  @return String representation
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("CPanel [");
		sb.append(super.toString());
		CompiereColor bg = getBackgroundColor();
		if (bg != null)
			sb.append(bg.toString());
		sb.append("]");
		return sb.toString();
	}   //  toString

}   //  CPanel
