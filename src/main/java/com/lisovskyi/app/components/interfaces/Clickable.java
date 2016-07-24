package com.lisovskyi.app.components.interfaces;

/**
 * Interface for clickable components
 *
 */
public interface Clickable {

	/**
	 * Click action
	 */
	void click();
	
	/**
	 * Double click action
	 */
	void doubleClick();
	
	/**
	 * In case the action redirects to a new page, methods override to return the redirected page.
	 * @return
	 * @throws Exception
	 */
	Object getLandingPage() throws Exception;
}
