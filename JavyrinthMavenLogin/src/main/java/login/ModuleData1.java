package login;

public final class ModuleData1 {
	/*
	 * Singleton Design Pattern.
	 * Other modules from outside can acquire this module's selected data.
	 */
	
	// Fields shouldn't be accesible from outside
	private ModuleData1 loggedUser = null;
	private String userName = null;
	
	// We prevent existing the 'external' constructor
	private ModuleData1() {}
	
	// The one and only LoggedUser instance can be acquired from this method
	public ModuleData1 getLoggedUser() {
		if(loggedUser==null) {
			this.loggedUser = new ModuleData1();
		}
		return this.loggedUser;
	}
	
	// Shouldn't be accessible from outside the packet
	void setUserName (String loggedUserName) {
		getLoggedUser().userName = loggedUserName;
	}
	
	public String getUserName() {
		return getLoggedUser().userName;
	}
}
