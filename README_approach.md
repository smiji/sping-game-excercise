
TODO
1. Make a branch for development - home/dev_smiji   :: done
2. Core logic 
to implement the 
Resolving all player the bets based on the wheel result and responding
Resolve all player bet
Resolve single player bet
Resolve multiple player bet
Validation 
-> Player cannot be empty
-> Number cannot exceed 36
-> Number cannot be less than zero
-> Maxmum number of players can play Assuming 30 percentage of the 36
TestClass for ResolveBets
==============================================================================
What is the result? of the resolver?
-> Resolver takes the Result object
-> In single player , it checks for his scror and resolve the result
-> In multi plyer , it checkes for each and resolve the result
==============================================================================  
==============================================================================
-> Rename the files for Single pocket/Multipocket       :: done
-> Validation                                           :: done
-> Dynamic Bean injection                               :: done
-> Test cases for the service                           :: done
-> Prepare Object from the json                         :: done
==============================================================================
15-May-2024
Wednesday
-> Validation                                           :: done
-> Rest End point testing using MocMvc                  :: done
-> Run the sonar checking                               :: done  
-> Run the test coverage                                :: done
-> Review the requirement
-> Commit with confidence
-> Inform the team that you have complete the assignment
===============================================================================   
===============================================================================
Validation 
Player name - Cannot be blank,miminmum length - 1, no max length
betType     - Cannot be blank,cannot be null,minimum length - 1 , no max length 
pocket      - Cannot be blank,cannot be null,cannot be less than 0 , not greater than 36
betAmount   - Cannot be blank,cannot be null, cannot be String
============================================================================================================   

=============================================================================================================
Rest End point testing using MocMvc                  :: done
Test for win                                         :: done   
Test for multiplayer                                 :: done
=============================================================================================================
Tomorrow , make the MD file review and inform the team,
Add the Approach document .md file 
Review the approch  - BetResolution,
    -> The spin is common and happens once for a bet
    -> The best resolving is spilted into two category
            Based on the number of players participating in the betting,
            -> SinglePlaySinglePocket
                    -> Only one player is playing 
                    -> Need to only resolve his betting
            -> MultiplayerSinglePocket
                    -> Many players can participate for the same betting
                    -> Bet resolve should happen to each player

            To Enable option multi-pocket betting 
            Here the same operation is happening for either cases, it can be argued 
            that there is no need to split the betting into two categories. 
            But it is spliited into two considering the future extensibility such as , 
            a Single player can bet through more than one pocket. Similary in multiplayer category any player can bet through more than one pocket.
            So keeping the logic for Bet resolution at single place can increase the complexity.
            The code readability maintainability will all increase. So it decided to split into two category.
            For the new Betting mode , we can add more implementations without changing the existing behaviour
            Resolving the SinglePlaySinglePocket vs  MultiplayerSinglePocket
                -> A new map is added 
                -> the size of the list or the number of players is used for choosing one over the other                
Validation approach,
        Spring validation annotations are used for the input field validations               
Validation for each input fields in SpinDTO
Validation failure reporting,
    Error Resp is added   
    With fields
Add Exception for the Spin
==================================================================================================
Thursday
16 May 2024
                1 . API documentation  : Already present
                2 . Fix the servlet missing problem - either test run with 6.0.0 or code run with 5.0.0

        Linking the Java files inthe MD file
        
