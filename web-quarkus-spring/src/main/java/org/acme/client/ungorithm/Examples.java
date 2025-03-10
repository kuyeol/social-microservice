package org.acme.client.ungorithm;

final class Examples {

  private Examples() {

  }

  static final String VALID_EXAMPLE_FIGHT = """
      {
        "id": "653bea9d188984908cd12429",
        "fightDate": "2075-10-27T16:51:41.787Z",
        "winnerName": "Luke Skywalker",
        "winnerLevel": 10,
        "winnerPowers": "Uses light sabre, The force",
        "winnerPicture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/luke-skywalker-2563509063968639219.jpg",
        "loserName": "Darth Vader",
        "loserLevel": 3,
        "loserPowers": "Uses light sabre, dark side of the force",
        "loserPicture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/anakin-skywalker--8429855148488965479.jpg",
        "location": {
          "name": "Gotham City",
          "description": "An American city rife with corruption and crime, the home of its iconic protector Batman.",
          "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/locations/gotham_city.jpg"
        }
      }
    """;

  static final String create_Entity = """
      {
         "username": "username",
         "timestamp": 9007199254740991,
         "address": "address",
         "id": "usernameid",
         "input": "input",
         "credentials": [
           {
             "type": "type",
             "createdDate": 9007199254740991,
             "secretData": "secretData",
             "credentialData": "credentialData",
             "priority": 1073741824
           }
         ]
       }
  """;


  static final String VALID_EXAMPLE_FIGHT_TO_NARRATE = """
      {
        "winnerTeam": "heroes",
        "winnerName": "Luke Skywalker",
        "winnerLevel": 10,
        "winnerPowers": "Uses light sabre, The force",
        "loserTeam": "villains",
        "loserName": "Darth Vader",
        "loserLevel": 3,
        "loserPowers": "Uses light sabre, dark side of the force",
        "location": {
          "name": "Gotham City",
          "description": "An American city rife with corruption and crime, the home of its iconic protector Batman."
        }
      }
    """;

  static final String VALID_EXAMPLE_FIGHT_LIST = "[" + VALID_EXAMPLE_FIGHT + "]";

  static final String VALID_EXAMPLE_FIGHTERS = """
      {
        "hero": {
          "name": "Luke Skywalker",
          "level": 10,
          "powers": "Uses light sabre, The force",
          "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/luke-skywalker-2563509063968639219.jpg"
        },
        "villain": {
          "name": "Darth Vader",
          "level": 3,
          "powers": "Uses light sabre, dark side of the force",
          "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/anakin-skywalker--8429855148488965479.jpg"
        }
      }
    """;

  static final String VALID_EXAMPLE_FIGHT_REQUEST = """
    {
      "hero": {
        "name": "Luke Skywalker",
        "level": 10,
        "powers": "Uses light sabre, The force",
        "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/luke-skywalker-2563509063968639219.jpg"
      },
      "villain": {
        "name": "Darth Vader",
        "level": 3,
        "powers": "Uses light sabre, dark side of the force",
        "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/anakin-skywalker--8429855148488965479.jpg"
      },
      "location": {
      "name": "Gotham City",
      "description": "An American city rife with corruption and crime, the home of its iconic protector Batman.",
      "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/locations/gotham_city.jpg"
      }
    }
    """;

  static final String VALID_EXAMPLE_LOCATION = """
    {
      "name": "Gotham City",
      "description": "An American city rife with corruption and crime, the home of its iconic protector Batman.",
      "picture": "https://raw.githubusercontent.com/quarkusio/quarkus-super-heroes/characterdata/images/locations/gotham_city.jpg"
    }
    """;

  static final String EXAMPLE_FIGHT_IMAGE = """
    {
      "imageUrl": "https://dummyimage.com/240x320/1e8fff/ffffff&text=Fallback+Image",
      "imageNarration": "This is an alternate narration generated by the model based on the original narration"
    }
    """;
}
