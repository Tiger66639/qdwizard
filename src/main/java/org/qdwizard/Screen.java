/*
 *  QDWizard
 *  Copyright (C) Bertrand Florat and others

 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *  
 */
package org.qdwizard;

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A wizard screen
 * <ul>
 * <li>For each wizard page, create a public Screen class. You have to implement
 * initUI(), getDescription() and getName() abstract methods.</li>
 * <li>getName() method should return the step name and getDescription() the
 * step description (return null if no description needed).</li>
 * <li>initUI() method contains graphical code for your screen. This method is
 * automatically called from screen constructor so don't call it.</li>
 * </ul>
 */
public abstract class Screen extends JPanel {
	/** Generated serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private final ScreenState state;
	/** Data is shared with wizard scope */
	public Map<Object, Object> data;
	private Wizard wizard;

	/**
	 * Construct a screen.
	 */
	public Screen() {
		state = new ScreenState(true, true, true, false, null);
	}

	/**
	 * Give here the step name.
	 * 
	 * @return screen name
	 */
	@Override
	abstract public String getName();

	/**
	 * Gie here the screen description (can be null)
	 * 
	 * @return screen description
	 */
	abstract public String getDescription();

	/**
	 * Can finish.
	 * 
	 * 
	 * @return true if...
	 */
	boolean canFinish() {
		// Can finish only if none problem
		return state.getCanFinish() && state.getProblem() == null;
	}

	/**
	 * Set whether the finish button should be enabled.
	 * 
	 * @param b
	 */
	public void setCanFinish(boolean b) {
		state.setCanFinish(b);
		notifyGUI();
	}

	/**
	 * Can go next.
	 * 
	 * 
	 * @return true if...
	 */
	boolean canGoNext() {
		// if screen is last one, cannot go further
		return state.getCanGoNext() && !state.getCanFinish() && state.getProblem() == null;
	}

	/**
	 * Can cancel.
	 * 
	 * 
	 * @return true if...
	 */
	public boolean canCancel() {
		return state.getCanCancel();
	}

	/**
	 * Can go previous.
	 * 
	 * 
	 * @return true if...
	 */
	boolean canGoPrevious() {
		return state.getCanGoPrevious();
	}

	/**
	 * Set whether the next button should be enabled.
	 * 
	 * @param b
	 */
	void setCanGoNext(boolean b) {
		state.setCanGoNext(b);
		notifyGUI();
	}

	/**
	 * Set whether the previous button should be enabled.
	 * 
	 * @param b
	 */
	void setCanGoPrevious(boolean b) {
		state.setCanGoPrevious(b);
		notifyGUI();
	}

	/**
	 * Set whether the cancel (or System menu close) button should be enabled.
	 * 
	 * @param b
	 */
	public void setCanCancel(boolean b) {
		state.setCanCancel(b);
		notifyGUI();
	}

	/**
	 * Set a problem (set to null if problem is fixed).
	 * 
	 * @param sProblem
	 *            Problem string or null if no more problem
	 */
	public void setProblem(String sProblem) {
		state.setProblem(sProblem);
		notifyGUI();
	}

	/**
	 * Get current problem.
	 * 
	 * @return the current problem
	 */
	public String getProblem() {
		return state.getProblem();
	}

	/**
	 * UI creation.
	 */
	abstract public void initUI();

	/**
	 * Called by wizard before the screen is displayed. This happens only in
	 * forward mode, which means onEnter won't be called when you return to a
	 * screen via the previous button.
	 */
	public void onEnter() {
		// Do nothing by default
	}

	/**
	 * Called by wizard before the screen is left. This happens only in forward
	 * mode, which means onLeave won't be called when you leave the screen via
	 * the previous button.
	 * <p>
	 * 
	 * @return return true if the Wizard should display the next screen return
	 *         false if the Wizard should stay on the current screen
	 */
	public boolean onNext() {
		// Does nothing by default
		return true;
	}

	/**
	 * Called by wizard when the wizard is being canceled. Override this
	 * function to clean up (like stop any threads that this Screen might have
	 * created)
	 */
	public void onCancelled() {
		// does nothing by default
	}

	/**
	 * Called by wizard when the wizard is closing because the Finish button was
	 * pressed. Override this function to clean up (like stop any threads that
	 * this Screen might have created)
	 */
	public void onFinished() {
		// does nothing by default
	}
	
	/**
     * Gets the associated wizard.
     * 
     * @param the wizard
     */
    public Wizard getWizard() {
        return this.wizard;
    }

	/**
	 * Sets the associated wizard.
	 * 
	 * @param wizard
	 *            the new wizard
	 */
	protected void setWizard(Wizard wizard) {
		this.wizard = wizard;
		// Screen data = wizard data
		this.data = wizard.data;
	}

	/**
	 * Notify gui.
	 * 
	 */
	private void notifyGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Screen.this.wizard.updateGUIState();
			}
		});
	}
}
