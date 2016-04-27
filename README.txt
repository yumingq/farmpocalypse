=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _yumingq______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. File I/O - 
  I wanted to keep a high score sheet with username and scores. The user should be 
  able to add their name and score at the end of a game, and see the list of previous
    high scores. Only the top 10 ever scores are shown. It made sense to use File I/O for this
    because we need to save high scores to a separate file from the single game
    because scores persist across all plays, and we need to read it in for each game
    to display them at the end.

  2. Interitance/Subtyping- 
  I want to use inheritance (abstract classes) to model the different crops to grow.
  All plants are used in similar ways and have certain properties- time to full growth,
    time to rot, state of growing, full grown, or rotting, cost to buy, profit from harvest, and
    visual appearance. However, they each have different values for each of these properties,
    so it makes sense to have an abstract class "Plant" and then have different classes for each
    plant. It also made sense to use something like GameObj for the moving parts of the
    game, the farmer and zombie(s), as they are very different objects but have some 
    fundamental similarities when it comes to movement and positioning, and even the nonmoving
    ones because it was easy to set their velocities to zero and not move them.

  3. Data Structure - 2D Array- 
  I needed to keep track of plots to plant in some manner. Plots are unique, identified by their 
  location, and keep track of the Plant they contain. 
  It makes sense for a 2D array to be used because plants have to be associated with the plot 
    they are growing in for the basis of the game to work- farmers should be able to walk around 
    to the different plots and then change the associated plant through their interactions
    with it. Arrays inherently have a label for positioning so they can keep track of the fixed
    plots. A more complex implementation that I would have considered trying out if I had enough 
    time would have been the ability to plant anywhere on the screen, in which case maps might 
    make more sense to create an almost infinite number of potential plots. In this case however,
    I just needed a set grid of plots so a 2D array was easiest to utilize.

  4. Complex Collision-
  Something that I realized while implementing the game was that because my sprites were created 
  with transparent corners and sides, it was possible with square intersection for them to touch 
  without looking like they are touching- with a weird space in between them. This was a problem
  because when a zombie intersects the farmer it ends the game, and it is maddening to have the 
  game be over when you didn't think it was actually intersecting. To fix this, I implemented a
  more complex form of intersection. I created a 2D array of booleans to figure out if a pixel is
  transparent or not (this uses a little bit of code I found online via Piazza- see below for the 
  link). Then I detected the left and right edges of this 2D array, creating a class Point to keep
  track of the X and Y coordinate. This left me with a collection of left and right edge points.
  I also added all the top and bottom edge points. This gave me a collection of all the points
  around the edge of a sprite. I could then test if any of these edge points intersect, overriding
  the intersect method of GameObj. I use this for my zombie and farmer. For faster implementation,
  I use box detection before complex collision so I can hopefully run faster- it doesn't check if
  all the points intersect unless the box first intersects, and for other objects that don't need
  complex intersection (i.e. plots) it uses a simpler version.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
    Farmer- a game object that you control, checks intersection with this for most functions 
    (implements complex intersection)
    FarmLand (GameCourt)- the main playing area, has most of the game logic
    Game- the first file to open, has a lot of labels/buttons/interface stuff. Runs the game.
    GameObj- a baseline "game object." This is pretty much the same as it was in the mushroom
    of doom, with an extra collection of points for complex collision objects.
    HighScores - has methods for tracking high scores, reading in and writing out, sorting, etc.
    HSTest - does basically two simple tests for my file i/o. Not a complete set of tests.
    Plant - abstract class for plants! Has many key requirements for a plant (time to grow,
    cost, profit, time to rot, etc)
    Point - class created just to track x and y positions of edge points on complex images
    Pumpkin - type of plant (pumpkin!), most profitable but rots easily and is expensive
    Scores - a class with a name and a score for file i/o tracking of high scores. Overrides
    compareTo for sorting.
    SinglePlot - a single plot of land (within a 2D array grid later on) - can contain a plant.
    Strawberry - type of plant
    Wheat - type of plant- cheapest and takes a lont time to rot, but not much profit
    Zombie - game object that chases you! (chase is in Farmland though, not in zombie.) also uses
    complex intersection.

- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?
  
    I ended up not using JUnit testing as a core concept. This is because I mostly was able to
    debug using the GUI (outside of the File I/O tests in HSTest.java, but those are kind of 
    limited). A lot of my methods weren't "modular" enough to be tested very easily.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
    File I/O took me about ten hours to figure out (despite having done it in HW 8) because of 
    some really stupid stumbling blocks. I think now that there might have been an easier way to
    accomplish it, but I didn't think of it at the time. Complex collision also created a lot of
    issues for me, mostly because the sizing and pixel numbers are off, and I didn't want to use
    entire 2D arrays because of the time it would take to iterate through entire 2D arrays for 
    intersection (which needs to be checked a lot). 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
    I know my private state isn't very well encapsulated- I ran out of time to make that better
    because I was confused about that at first. I would definitely fix that, given time. I would
    probably be able to implement high scores easier (without creating a new class) - just with 
    for loops to detect max scores or something. I would try to make collision detection less
    size-dependent (try to make it more flexible for increasing image sizes). I would create a 
    better zombie spawning system (more random) with more types of enemies, and be able to kill 
    them in some way.
    
    I also realized that I could have made an extended GameObj interface for the complex intersect
    objects rather than have the same methods in them but I didn't think of that early enough.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
    
    This was for transparent pixel detection:
    http://stackoverflow.com/questions/8978228/
    java-bufferedimage-how-to-know-if-a-pixel-is-transparent
    
    In addition I used a bunch of java documentation, and here are the links to my images:
    Zombie
    http://fightingferret.deviantart.com/art/Pixel-Zombie-315810056
    Farmer
    http://vignette4.wikia.nocookie.net/scribblenauts/images/3/3d/Farmer.png/
    revision/latest?cb=20130203141947
    Strawberry
    https://upload.wikimedia.org/wikipedia/commons/2/29/PerfectStrawberry.jpg
    Pumpkin
    http://vegoutwithrfsorg.r.worldssl.net/wp-content/uploads/2015/02/Pumpkin.jpg
    Wheat
    http://www.quickanddirtytips.com/sites/default/files/images/4236/wheat_stalk.jpg
    http://74211.com/wallpaper/picture_big/Flowers-Wallpaper-Green-Wheat-Fresh-and-Clean-Scene.jpg
    Dead Plant
    http://www.globalresearch.ca/wp-content/uploads/2015/01/Dead-Crops-Barren-Dessert-400x225.jpg
    Game Over Screen
    http://1u88jj3r4db2x4txp44yqfj1.wpengine.netdna-cdn.com/wp-content/uploads/2014/11/game-over.jpg
    Grass
    http://opengameart.org/sites/default/files/styles/watermarked/public/grass_14.png
    