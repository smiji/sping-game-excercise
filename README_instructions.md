# Exercise Specification

## Requirements

- Java 17+
- Maven 3+

## Goal

The goal of the exercise is to produce a roulette game which responds to "player" REST requests.

## Roulette

Roulette is a popular casino game where players bet on a small ball landing in a numbered 'pocket' (or group of pockets) of a spinning wheel.

You will be implementing European roulette, which means the wheel has 37 pockets numbered 0-36.

Every spin of the wheel results in a random pick of the numbers 0-36.

### Pocket bet

A pocket bet is one placed on a single number.

If that number lands, the player receives `x36` their bet value.

For example, if a player bets `10.00` on `5`, and it lands, they will win `360.00`. If the landed number _isn't_ `5`, they lose.

## Current implementation

The  classes defining the request and response API are already implemented in the [model](model) module - we don't expect you to have make changes to this module.

There is already an implementation of a roulette wheel available here [RouletteWheel.java](api%2Fsrc%2Fmain%2Fjava%2Froxor%2Fgames%2Froulette%2Fgame%2FRouletteWheel.java)

Each spin **request** can contain multiple player bets as well as an **optional** forcedPocket number which can be used for testing. See [PlayerBetDTO.java](model%2Fsrc%2Fmain%2Fjava%2Froxor%2Fgames%2Froulette%2Fmodel%2Frequest%2FPlayerBetDTO.java)

Every spin **response** contains the landed pocket, as well as a list of resolved player bets. See [SpinResultDTO.java](model%2Fsrc%2Fmain%2Fjava%2Froxor%2Fgames%2Froulette%2Fmodel%2Fresponse%2FSpinResultDTO.java)

The business logic for validating the request, spinning the wheel and resolving the bets is **missing**.


## TODO

Implement the business logic for 

1. Validating request parameters
2. Spinning the wheel
3. Resolving all player the bets based on the wheel result and responding

See the [RouletteService](api/src/main/java/roxor/games/roulette/service/RouletteService.java) class for a good starting point.

The **only** [bet type](https://www.gamblingsites.com/online-casino/games/roulette/bets/) we want you to implement now is

- Pocket bet (pays `x36` as described above)

## Notes

Pay attention to implementing a solution which is **designed with extensibility in mind** and which has **good test coverage**.

Please provide a README to explain any assumptions you have made or any further updates you would have liked to have made if you had more time.


If you have any further questions you have about the spec **please let us know** (if it would be helpful, we can arrange a short meeting to answer any of your queries)

## How To Run

First, from the root of the project, build both the required modules: 

```
mvn clean install
```

Then, start the Roulette REST service:

```
mvn -f api/pom.xml spring-boot:run
```

You then access its [Swagger UI](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config)

[//]: # (or [API docs]&#40;http://localhost:8080/api-docs/&#41;)

## Roulette Service REST API examples

### Single Player bet (no forcing)

#### Request

```
POST  http://localhost:8080/v1/roulette/spin
```

```json
{
  "playerBets": [
    {
      "playerName": "Sam",
      "betType": "number",
      "pocket": "4",
      "betAmount": "1.00"
    }
  ]
}
```

#### Response

```json
{
 "pocket": 30,
 "betResults": [
  {
   "playerBet": {
    "playerName": "Sam",
    "betType": "number",
    "pocket": "4",
    "betAmount": "1.00"
   },
   "outcome": "lose",
   "winAmount": "0.00"
  }
 ]
}
```


### Multiple player bets (with forcing included)

#### Request

```
POST  http://localhost:8080/v1/roulette/spin
```

```json
{
  "forcedPocket": "36", // This field is *optional* and should *only* be used for testing
  "playerBets": [
    {
      "playerName": "Nick",
      "betType": "number",
      "pocket": "7",
      "betAmount": "1.00"
    },
    {
      "playerName": "Jules",
      "betType": "number",
      "pocket": "36",
      "betAmount": "2.00"
    }
  ]
}
```

#### Response


```json
{
 "pocket": 36,
 "betResults": [
  {
   "playerBet": {
    "playerName": "Nick",
    "betType": "number",
    "pocket": "7",
    "betAmount": "1.00"
   },
   "outcome": "lose",
   "winAmount": "0.00"
  },
  {
   "playerBet": {
    "playerName": "Jules",
    "betType": "number",
    "pocket": "36",
    "betAmount": "2.00"
   },
   "outcome": "win",
   "winAmount": "72.00"
  }
 ]
}
```

### Validation Errors

If there was an issue validating the request please return a `BAD_REQUEST` 



