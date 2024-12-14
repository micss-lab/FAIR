![screenshot](system.png)

The system emulates the behaviors and processes typical of industrial CPS classes but simplifies their cyber and physical components to enable experimentation with fuzzy logic and agent-based approaches. The case study models a sauce production process, focusing on using red tomatoes as the primary ingredient. While a limited number of spoiled tomatoes can be tolerated, an excessive amount negatively impacts product quality. Green peppers can also be included, creating a blend of red tomatoes and green peppers for various sauce types. Additionally, peppers of different green shades may be added.

Before incorporating ingredients, the system must identify and remove spoiled tomatoes. However, the colour sensor operates under uncertainty due to its limited ability to process colour information. To address this challenge, a fuzzy-logic-based method effectively manages the inherent ambiguities.

The fuzzification process was made on the Sort Agent. It should correctly distinguish and label the colours, such as red, spoiled red, light green, green and dark green, shown below:

![screenshot](bricks.png)


**Fuzzy Membership Functions and Rules are given below:** 

![screenshot](fuzzies.png)

```

PLANBLOCK SortAgent
    IF red IS high AND green IS high AND blue IS low THEN
        saveResult("Red");

    IF red IS medium AND green IS high AND blue IS low THEN
        saveResult("Red");

    IF red IS medium AND green IS medium AND blue IS low THEN
        saveResult("Red");

    IF red IS medium AND green IS veryhigh AND blue IS low THEN
        saveResult("Red");

    IF red IS high AND green IS veryhigh AND blue IS low THEN
        saveResult("Red");

    IF red IS medium AND green IS medium AND blue IS medium THEN
        saveResult("SpoiledRed");

    IF red IS medium AND green IS high AND blue IS medium THEN
        saveResult("SpoiledRed");

    IF red IS high AND green IS high AND blue IS medium THEN
        saveResult("SpoiledRed");

    IF red IS medium AND green IS medium AND blue IS high THEN
        saveResult("SpoiledRed");

    IF red IS high AND green IS medium AND blue IS low THEN
        saveResult("SpoiledRed");

    IF red IS medium AND green IS veryhigh AND blue IS medium THEN
        saveResult("SpoiledRed");

    IF red IS medium AND green IS veryhigh AND blue IS high THEN
        saveResult("SpoiledRed");

    IF red IS high AND green IS veryhigh AND blue IS high THEN
        saveResult("SpoiledRed");

    IF red IS medium AND green IS ultramedium AND blue IS medium THEN
        saveResult("Light Green");

    IF red IS medium AND green IS ultralow AND blue IS medium THEN
        saveResult("Light Green");

    IF red IS medium AND green IS ultrahigh AND blue IS medium THEN
        saveResult("Light Green");

    IF red IS high AND green IS ultramedium AND blue IS medium THEN
        saveResult("Light Green");

    IF red IS high AND green IS ultramedium AND blue IS high THEN
        saveResult("Light Green");

    IF red IS high AND green IS ultrahigh AND blue IS medium THEN
        saveResult("Light Green");

    IF red IS high AND green IS ultrahigh AND blue IS high THEN
        saveResult("Light Green");

    IF red IS low AND green IS ultralow AND blue IS medium THEN
        saveResult("Middle Green");

    IF red IS low AND green IS ultramedium AND blue IS low THEN
        saveResult("Middle Green");

    IF red IS low AND green IS ultralow AND blue IS low THEN
        saveResult("Middle Green");

    IF red IS low AND green IS ultramedium AND blue IS medium THEN
        saveResult("Middle Green");

    IF red IS low AND green IS high AND blue IS low THEN
        saveResult("Dark Green");

    IF red IS low AND green IS high AND blue IS medium THEN
        saveResult("Dark Green");

    IF red IS low AND green IS veryhigh AND blue IS low THEN
        saveResult("Dark Green");
END_PLANBLOCK

```


