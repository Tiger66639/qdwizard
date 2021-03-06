/*
 *  QDWizard
 *  Copyright (C) Bertrand Florat and others
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *  
 */
package org.qdwizard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;

/**
 * Action panel
 * <p>
 * Contains a message area where problems are displayed and a buttons area
 * (Previous, Next, Finish, Cancel).
 * </p>
 */
class ActionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	/** Problem text area. */
	private JLabel jlProblem;
	private JButton jbPrevious;
	private JButton jbNext;
	private JButton jbFinish;
	private JButton jbCancel;
	private Color backgroundColor;
	private Color backgroundColorProblem;

	/**
	 * The Constructor.
	 * 
	 * @param al
	 *            associated action listener
	 */
	public ActionsPanel(ActionListener al) {
		backgroundColor = Color.WHITE;
		backgroundColorProblem = Color.WHITE;
		// Problem panel
		jlProblem = new JLabel();
		jlProblem.setForeground(Color.RED);
		jlProblem.setFont(new Font("Dialog", Font.BOLD, 12));
		jlProblem.setHorizontalAlignment(SwingConstants.CENTER);
		// Action buttons
		JPanel jpButtons = new JPanel();
		jpButtons.setLayout(new BoxLayout(jpButtons, BoxLayout.X_AXIS));
		Dimension dimButtons = new Dimension(150, 20);
		jbPrevious = new JButton("< " + Langpack.getMessage("Previous"));
		jbPrevious.setPreferredSize(dimButtons);
		jbPrevious.addActionListener(al);
		jbPrevious.setActionCommand("Prev");
		jbNext = new JButton(Langpack.getMessage("Next") + " >");
		jbNext.addActionListener(al);
		jbNext.setActionCommand("Next");
		jbNext.setPreferredSize(dimButtons);
		jbFinish = new JButton(Langpack.getMessage("Finish"));
		jbFinish.addActionListener(al);
		jbFinish.setActionCommand("Finish");
		jbFinish.setPreferredSize(dimButtons);
		jbCancel = new JButton(Langpack.getMessage("Cancel"));
		jbCancel.addActionListener(al);
		jbCancel.setActionCommand("Cancel");
		jbCancel.setPreferredSize(dimButtons);
		jpButtons.add(Box.createHorizontalStrut(10));
		jpButtons.add(Box.createHorizontalGlue());
		jpButtons.add(jbPrevious);
		jpButtons.add(Box.createHorizontalStrut(5));
		jpButtons.add(jbNext);
		jpButtons.add(Box.createHorizontalStrut(10));
		jpButtons.add(jbFinish);
		jpButtons.add(Box.createHorizontalStrut(20));
		jpButtons.add(jbCancel);
		jpButtons.add(Box.createHorizontalStrut(10));
		jpButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		jpButtons.setOpaque(false);
		jlProblem.setOpaque(false);
		setOpaque(false);
		// Main panel
		setLayout(new GridLayout(2, 1));
		add(jlProblem);
		add(jpButtons);
	}

	/**
	 * Set buttons states.
	 * 
	 * @param bPrevious
	 * @param bNext
	 * @param bFinish
	 * @param bCancel
	 */
	void setState(boolean bPrevious, boolean bNext, boolean bFinish, boolean bCancel) {
		jbPrevious.setEnabled(bPrevious);
		jbFinish.setEnabled(bFinish);
		jbNext.setEnabled(bNext);
		jbCancel.setEnabled(bCancel);
	}

	/**
	 * Sets the problem.
	 * 
	 * @param problem
	 *            the new problem
	 */
	void setProblem(String problem) {
		String sProblem = problem;
		jlProblem.setText(sProblem);
	}

	/**
	 * Set the background color of the ActionPanel.
	 * 
	 * @param color
	 */
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

	/**
	 * Set the background color of the ActionPanel Problem notification area.
	 * 
	 * @param color
	 */
	public void setProblemBackgroundColor(Color color) {
		this.backgroundColorProblem = color;
	}

	/**
	 * Set the "next" button as default button
	 * 
	 * @param rootPane
	 *            the wizard root pane
	 */
	public void setNextAsDefaultButtonInPanel(JRootPane rootPane) {
		rootPane.setDefaultButton(jbNext);
	}

	/**
	 * Set the background color of the ActionPanel Problem notification area.
	 * 
	 * @param color
	 */
	public void setProblemTextColor(Color color) {
		jlProblem.setForeground(color);
	}

	@Override
	public void paint(java.awt.Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		java.awt.Rectangle rect = getBounds();
		g2D.setColor(backgroundColor);
		g2D.fillRect(0, 0, rect.width, rect.height);
		if (jlProblem != null && jlProblem.getText() != null && jlProblem.getText().length() > 0) {
			rect = jlProblem.getBounds();
			g2D.setColor(backgroundColorProblem);
			g2D.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		super.paint(g);
		g2D.setColor(Color.LIGHT_GRAY);
		g2D.drawLine(rect.x, 0, rect.width, 0);
	}
}
