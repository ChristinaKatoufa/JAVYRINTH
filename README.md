"Welcome, everybody!"

Our final project is located in main branch inside the JavyrinthMaven_FinalProject folder
This folder contains 4 Javyrinth Maven Projects, that is our game.
3 of these Projects (LogIn,Game,Quiz) can also run independently.
JavyrinthMavenWraper is the Director who controls and brings them all together in one game.

Check Github release and run our jar
  java -jar Javyrinth.jar

How to Run the project from your Eclipse-Workspace
  1) Copy the contents of file 'JavyrinthMaven_FinalRelease' to your Eclipse-Workspace:
     JavyrinthMavenGame
     JavyrinthMavenLogin
     JavyrinthMavenQuiz
     JavyrinthMavenWraper
  2) Start Eclipse
  3) Go to File > Import > Maven > Existing Maven Projects and click Next
      Browse your root Directory and select one by one the MavenFiles
  4) From Eclipse toolbar Go to Project > Clean 
                       Make sure 'clean all projects' is ticked and then Clean
                       (for fresh rebuilt purposes)
  5) Right-Click on EACH project 
                                > Run as > Maven install
    Maven will start building the projects
    Carefull!
    JavyrinthMavenWraper must be the last one to built (has everything else as dependencies)
    
    Now you see every project has it's own runnable jar
    
    To Run the Game Go TO:
                          JavyrinthMavenWraper
                          src/main/java > package wraper > Main.java run as java Application
                          
                          or
                          
                          JavyrinthMavenWraper > target file > wraper-0.0.1-SNAPSHOT-shaded.jar (executable jar)
               
                          Enjoy!
Instructions of the Game
   Welcome to Javyrinth!
      LogIn to play the game (if you already have an account)
      Else click the sign Up button and join us
      Click on the Instructions before you play
      If you think you're ready, go to the main menu and hit the Play Now button
      
      Enjoy the game!
      At the end, you'll know if you did good compared to our team!
