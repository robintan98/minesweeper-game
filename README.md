# minesweeper-game
========================================================================
Minesweeper Game - Final Project
University of Pennsylvania CIS120: Programming Languages and Techniques
Date: December 2017
========================================================================

=: Description:
  A fully-functioning Minesweeper game based on the popular Microsoft Minesweeper game (1992) 
  that I coded as the final project for my Programming Languages and Techniques class.

=: Implementation Design and Details:
  My Game class creates a new game, establishes the GUI JPanels (i.e. control panel), and
  initializes my Grid object. My Grid class contains the current Board object being display
  by the GUI, as well as a LinkedList containing every instance when the Board object is altered
  by the User. The Grid Class also listens to whenever the User clicks a mouse button and performs
  the appropriate method that changes the current Board object. My Board class establishes a Board
  object; each Board object stores both the displayed and actual state of the board. The Board
  Class contains multiple methods that can be called in Grid to alter Board objects, including
  initializing Board objects, updating Board objects, and notifying whenever the user has won
  or lost. Finally, the Pair object is a helper class whose pure function is to combine two
  integers into one object. This was useful when storing the coordinates of a Grid cell into one
  object for comparison.

  I designed my game in a way such that each class can interact with each others with constructors 
  and methods without having to know the inner workings of the other classes. In addition, each 
  variable that is class-specific is stored as a private instance variable so they can not be 
  altered without a proper method. This way, each class can communicate with each other without 
  adversely affecting the private variables stored in the classes. If given the chance, I would 
  perhaps refactor longer methods (i.e. updateCBoard) into clearer if-else statements or smaller
  helper methods.

=: Core Concepts:
  1. 2-D Arrays: I used 2-D arrays of Strings that store the displayed state and the 
  	 actual state of the Grid. The positions of each element of the 2-D arrays represent 
  	 the position on the Grid. I implemented a 2-D array because the format of the 2-D array 
  	 closely represented the architecture of the rectangular Grid, and the arrays were capable 
  	 of storing data (String) that can map to specific images to draw in the GUI.

  2. Collections: I used Collections, specifically, a LinkedList, to store 2-D String arrays 
  	 depicting the visual state of the Grid every time a new cell is pressed. I used a LinkedList 
  	 in order to implement my undo() function, where I remove the last element of the LinkedList 
  	 and revert the visual state of the Grid to the second-to-last element of the LinkedList 
  	 whenever the undo() function is called. I use LinkedList over other collections and arrays 
  	 because LinkedLists are helpful when performing multiple insertions/deletions and when the 
  	 size of the Collection is dynamic.

  3. Testable Component: I incorporated JUnit Testing with my game to test multiple methods 
     that interact with the 2-D arrays in my game. My JUnit tests implement the Assert 
     Class in order to determine whether the outputs of some important methods are correct or not. 
     Each of my JUnit tests use this class to cover singleton, multiple, and edge cases of these 
     methods in order to ensure that the methods work in all cases when they are being called on 
     when the User interacts with the GUI.

  4. Recursion: I used recursion when updating the visual state in my game in order to find 
     neighboring 0 cells in the Grid. One aspect of the rules of MineSweeper is the allowance of 
     all consecutive 0 cells to be revealed when one is clicked. To search for all these neighbors, 
     I implemented recursion, in which the function updateCBoard calls upon itself in order to 
     search for all surrounding neighbors. Recursion is appropriate to use in this context because 
     the consecutive 0 cells vary per game, and recursion offers a simpler way to search for them 
     as opposed to iterating over the entire board many, many times.

=: External Resources:  
  I used the image taken from the following URL to draw the images in my GUI: 
  https://i1.wp.com/www.crisgdwrites.com/wp-content/uploads/2016/06/
  minesweeper_tiles.jpg?w=512.
  
  
