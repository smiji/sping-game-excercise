### Bet resolution approach
     The spin is common and happens only once for a bet request.Bet resolving is split into two categories
    based on the number of players participating in the betting

#### SinglePlaySinglePocket
        - Only one player is playing
        - Bet resolving only required for one player
        - Bet resolve include , 
            - Find out the bet outcome - win or loose
            - Find out the win amount - 0 for loosing , 36 * x for win
##### MultiplayerSinglePocket
        - Many players can participate for the same betting
        - Bet resolve required for each player
        - Bet resolve include , 
            - Find out the bet outcome - win or loose for each player
            - Find out the win amount - 0 for loosing , 36 * x for win - for each player
#### Reason for the Split approach
        No matter in SinglePlaySinglePocket or in MultiplayerSinglePocket, the same operation is performed for bet resolving.The only 
    difference is that,in SinglePlaySinglePocket bet resolving happens only for one person where as in MultiplayerSinglePocket , 
    it happen for n number of players it can be argued that there is no need to split the betting into two categories.

       Bet resolving is split into two categories considering the extensibility such as ,
         a) Single player can bet through more than one pocket. 
         b) In multiplayer category any player can bet through more than one pocket.

        So keeping the logic for Bet resolution at single place can increase the complexity.
    The code readability will reduce and the cost of maintainability will all increase. So it is decided to split into two category.
#### How with point a) or b) can be achieved using the current strategy?
        - For the new Betting mode , we can add more implementations without changing the existing behaviour
    of SinglePlaySinglePocket or MultiplayerSinglePocket
        - For a) New bet resolver can be added , called SpinBetSinglePlayerMultiPocketResolver
        - For b) New bet resolver can be added , called SpinBetMultiplayerMultiPocketResolver
#### How the service know , which bet resolver to use during the bet request processing
        - Configuration changes, 
            - A new enum is added - BetMode, with betmodes 
                - SINGLE_PLAYER_SINGLE_POCKET, 
                - MULTI_PLAYER_SINGLE_POCKET
            - A new map configuration is added, 
                -> Map<String, BetResolver> betResolver()  
                -> The map will have both resolvers ready against their betmode names , while application starts
            - RouletteService is applied a constructed injection , passing the betResolver map
        - During request execution
            - A new Util method is added - public static String getBetMode(SpinDTO spinDTO)
            - Based on the size of list/ numbers of players , 
                it returns BetMode -
                                - SINGLE_PLAYER_SINGLE_POCKET
                                - MULTI_PLAYER_SINGLE_POCKET
            - The service (RouletteService) uses the util method getBetMode 
                resolve which bet mode to use
            - Since all the bet resolvers are implemeting the - BetResolver base interface,
                calling the resolve method will be sufficent enough to resolve the bet and
                compose the result

### Validation approach
        - Used the Spring provided validations - @Size,@NotBlank,@NotBlank
        - Validation for each fields
            - SpinDTO
                - String forcedPocket
                    1) @Size(max = 0) - This field has to be empty
                - List<PlayerBetDTO> playerBets
                    1) @Size(min = 1) - This list cannot be empty, minimum one player required
            - PlayerBetDTO
                - String playerName 
                    1) @NotBlank - Cannot be blank
                    2) @NotNull  - Cannot be null
                -String betType
                    1) @NotBlank - Cannot be blank
                    2) @NotNull  - Cannot be null
                -String pocket
                    1) @Max(value = 36) - max value allowed - 36
                    2) @Min(value = 0)  - min value allowed - 0
                -String betAmount
                    1) @NotBlank - Cannot be blank
                    2) @NotNull  - Cannot be null

### Reporting validation failure
        - ErrorResp, A new Pojo is added with the following fields
            -String type
            -String title
            -int status
            -String detail
            -String instance
        - handleMethodArgumentNotValid method is overriden in RestResponseEntityExceptionHandler
          to prepare the error response

            


