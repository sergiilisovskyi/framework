package com.lisovskyi.app.components.interfaces;

/**
 * Interface for checkable components
 *
 */
public interface Checkable {

	
	/**
	 * Verifies if the component is checked
	 * @return
	 */
	boolean isChecked(); 
	
	
	/**
	 * Marks the check box as checked
	 */
	void check();
	
    /**
     * Marks the check box as unchecked
     */
    void uncheck();
	
}
