Overview
My Othello game implementation has 13 classes:
1. App: Launches the application, sets up the scene.
2. PaneOrganizer: sets up and manages the UI components of the game, such as the root borderpane.
3. SetupGame: instantiates the game, creates board and referee and players.
4. Referee: handles player logic, and asks them to take turns to make moves. 
5. Board: represents the game's board and manages the pieces, board state, and also other helpful logic such as
evaluating the board's state
6. Grid: smaller parts that make up the board, each representing a square in the game board
7. Piece: a piece is contained in a grid, and is represented by either a black or white circle
8. Player: superclass that represents the players
9. HumanPlayer: player subclass that represents a human player and looks for a manual input for the move
10. ComputerPlayer: player subclass that represents a computer player. Also handles the logic for deciding where
to move
11. Move: A helper class that represents a move location and the value associated with it
12. Constants: Defines constants for grid size, colors, and other game settings
13. Controls: prebuilt class that handles the control pane and user input there.

Design Choices:
I let board and referee classes handle a substantial part of the game logic, so I had a lot of setter methods that 
allowed them to work with almost every aspect of the game from other classes (such as passing instructionLabel into 
referee). Referee handles everything to do with the players, and then asks the board to handle everything logically 
related to evaluating the board and making moves show up.

Hours spent:
20