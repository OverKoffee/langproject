# langproject
Java-based Desktop SRS application

<b>TO-DO:</b></br>
- Setup remote database to handle username/pw authentication</br>
- Create UI for Sign Up / sends input info to username/pw database, (later e-mail verification etc.)</br>
- Create UI for Navigation (Select Deck dropdown, Edit Deck, Add Cards, Do reviews)</br>
- Create UI for each navigation item</br>
- Create remote database to store Deck/Card information for each user</br>
- Write class/methods to sync (grab information from remote database and store on local comp in some other formatted text file)</br>
       and sync on close (send updated information, cards reviewed/added etc, back to remote database)
</br></br>
 <b> ** Currently working out how I'm going to switch through each scene.. considering writing a class for each scene with its own controller ** // UPDATE: setting OnAction for Buttons going to new scenes/screens to just setScene as new Parent pointed to the fxml file--creating a class as a controller for each scene, so it will be read automatically once the fxml is loaded as the new scene, because the fxml file will define which controller controls it</b>
