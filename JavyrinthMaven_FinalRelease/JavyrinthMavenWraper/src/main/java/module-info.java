module wraperModule {

	requires loginModule;
	requires gameModule;
	requires quizModule;
	
	requires java.desktop;
	requires java.sql;
	
    exports wraper;
}
