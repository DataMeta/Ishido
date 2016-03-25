package edu.ramapo.dmelniko.ishido;

public class Board
{
    public Tile tilePreview;
    public Tile[][] tileBoard;
    String currentPlayer;

    //Default constructor
    public Board()
    {
        tilePreview = new Tile();
        tileBoard = new Tile[8][12];

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                tileBoard[i][j] = new Tile();
            }
        }
    }

    // Copy constructor
    public Board(Board mainBoard)
    {
        tilePreview = new Tile();
        tileBoard = new Tile[8][12];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                this.tileBoard[i][j] = mainBoard.tileBoard[i][j];
            }
        }
    }

    // Custom constructor
    public Board(String boardData)
    {
        tilePreview = new Tile();
        tileBoard = new Tile[8][12];
        String colorVar = "";
        String symbolVar = "";
        int switchColor;
        int switchSymbol;

        String[] tokens = boardData.split("[ |\\n]");
        int k = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                tileBoard[i][j] = new Tile();
                if(k >= tokens.length)
                {
                }
                else
                {
                    switchColor = Character.getNumericValue(tokens[k].charAt(0));
                    switchSymbol = Character.getNumericValue(tokens[k].charAt(1));
                    k++;
                    switch(switchColor)
                    {
                        case 0: colorVar = "green";
                            break;
                        case 1: colorVar = "white";
                            break;
                        case 2: colorVar = "blue";
                            break;
                        case 3: colorVar = "black";
                            break;
                        case 4: colorVar = "yellow";
                            break;
                        case 5: colorVar = "magenta";
                            break;
                        case 6: colorVar = "red";
                            break;
                        default:
                            break;
                    }
                    switch(switchSymbol)
                    {
                        case 0: symbolVar = "";
                            break;
                        case 1: symbolVar = "+";
                            break;
                        case 2: symbolVar = "#";
                            break;
                        case 3: symbolVar = "%";
                            break;
                        case 4: symbolVar = "!";
                            break;
                        case 5: symbolVar = "?";
                            break;
                        case 6: symbolVar = "@";
                            break;
                        default:
                            break;
                    }
                    tileBoard[i][j].setColor(colorVar);
                    tileBoard[i][j].setSymbol(symbolVar);
                }
            }
        }
    }

    // Updates the board state for a path with the latest snapshot of the game board
    public void updateState(Board mainBoard)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                this.tileBoard[i][j] = mainBoard.tileBoard[i][j];
            }
        }
    }

    // Helper functions to minimize redundant code //

    // Below methods return true if the tile in the relative direction is empty
    public Boolean isLeftEmpty(int rowIndex, int colIndex)
    {
        return (tileBoard[rowIndex][colIndex - 1].getSymbol().equals(""));
    }
    public Boolean isRightEmpty(int rowIndex, int colIndex)
    {
        return (tileBoard[rowIndex][colIndex + 1].getSymbol().equals(""));
    }
    public Boolean isAboveEmpty(int rowIndex, int colIndex)
    {
        return (tileBoard[rowIndex - 1][colIndex].getSymbol().equals(""));
    }
    public Boolean isBelowEmpty(int rowIndex, int colIndex)
    {
        return (tileBoard[rowIndex + 1][colIndex].getSymbol().equals(""));
    }

    // Below methods return true if tile is compatible with the tile in the specified relative direction, or it's an empty tile
    public Boolean checkLeftTile(int rowIndex, int colIndex)
    {
        return ((tileBoard[rowIndex][colIndex - 1].getSymbol().equals(""))
                || (tilePreview.getColor().equals(tileBoard[rowIndex][colIndex - 1].getColor()))
                || (tilePreview.getSymbol().equals(tileBoard[rowIndex][colIndex - 1].getSymbol())));
    }
    public Boolean checkRightTile(int rowIndex, int colIndex)
    {
        return ((tileBoard[rowIndex][colIndex + 1].getSymbol().equals(""))
                || (tilePreview.getColor().equals(tileBoard[rowIndex][colIndex + 1].getColor()))
                || (tilePreview.getSymbol().equals(tileBoard[rowIndex][colIndex + 1].getSymbol())));
    }
    public Boolean checkAboveTile(int rowIndex, int colIndex)
    {
        return ((tileBoard[rowIndex - 1][colIndex].getSymbol().equals(""))
                || (tilePreview.getColor().equals(tileBoard[rowIndex - 1][colIndex].getColor()))
                || (tilePreview.getSymbol().equals(tileBoard[rowIndex - 1][colIndex].getSymbol())));
    }
    public Boolean checkBelowTile(int rowIndex, int colIndex)
    {
        return ((tileBoard[rowIndex + 1][colIndex].getSymbol().equals(""))
                || (tilePreview.getColor().equals(tileBoard[rowIndex + 1][colIndex].getColor()))
                || (tilePreview.getSymbol().equals(tileBoard[rowIndex + 1][colIndex].getSymbol())));
    }

    // Validates the pending move
    // Accepts the row and column indexes of the board coordinate
    // A Boolean value signifying a valid or invalid move is returned
    public Boolean isMoveValid(int rowIndex, int colIndex)
    {
        // For all adjacent tiles, each is either empty or of a compatible symbol or color //

        if(!tileBoard[rowIndex][colIndex].getSymbol().equals(""))
        {
            return false;
        }
        else
        {
            // Corner cases //
            // Top left corner
            if(rowIndex == 0 && colIndex == 0)
            {
                return (checkBelowTile(rowIndex, colIndex) && checkRightTile(rowIndex, colIndex));
            }
            // Top right corner
            else if (rowIndex == 0 && colIndex == 11)
            {
                return (checkLeftTile(rowIndex, colIndex) && checkBelowTile(rowIndex, colIndex));
            }
            // Bottom left corner
            else if (rowIndex == 7 && colIndex == 0)
            {
                return (checkAboveTile(rowIndex, colIndex) && checkRightTile(rowIndex, colIndex));
            }
            // Bottom right corner
            else if (rowIndex == 7 && colIndex == 11)
            {
                return (checkLeftTile(rowIndex, colIndex) && checkAboveTile(rowIndex, colIndex));
            }

            // Edge cases //
            // Top edge
            else if (rowIndex == 0 && colIndex > 0 && colIndex < 11)
            {
                return (checkLeftTile(rowIndex, colIndex) && checkRightTile(rowIndex, colIndex)
                        && checkBelowTile(rowIndex, colIndex));
            }
            // Bottom edge
            else if (rowIndex == 7 && colIndex > 0 && colIndex < 11)
            {
                return (checkLeftTile(rowIndex, colIndex) && checkRightTile(rowIndex, colIndex)
                        && checkAboveTile(rowIndex, colIndex));
            }
            // Left edge
            else if (colIndex == 0 && rowIndex > 0 && rowIndex < 7)
            {
                return (checkAboveTile(rowIndex, colIndex) && checkBelowTile(rowIndex, colIndex)
                        && checkRightTile(rowIndex, colIndex));
            }
            // Right edge
            else if (colIndex == 11 && rowIndex > 0 && rowIndex < 7)
            {
                return (checkAboveTile(rowIndex, colIndex) && checkBelowTile(rowIndex, colIndex)
                        && checkLeftTile(rowIndex, colIndex));
            }

            // Body case //
            else
            {
                return (checkAboveTile(rowIndex, colIndex) && checkBelowTile(rowIndex, colIndex)
                        && checkLeftTile(rowIndex, colIndex) && checkRightTile(rowIndex, colIndex));
            }
        }
    }

    // Calculates the point value of a move
    // Accepts the row and column indexes of the board coordinate
    // Returns the calculated point value
    public int calcMovePointVal(int rowIndex, int colIndex)
    {
        int pointVal = 0;

        // Upper left corner
        if(rowIndex == 0 && colIndex == 0)
        {
            if(!isBelowEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isRightEmpty(rowIndex, colIndex)){pointVal++;}
        }
        // Upper right corner
        else if (rowIndex == 0 && colIndex == 11)
        {
            if(!isLeftEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isBelowEmpty(rowIndex, colIndex)){pointVal++;}
        }
        // Lower left corner
        else if (rowIndex == 7 && colIndex == 0)
        {
            if(!isAboveEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isRightEmpty(rowIndex, colIndex)){pointVal++;}
        }
        // Lower right corner
        else if (rowIndex == 7 && colIndex == 11)
        {
            if(!isAboveEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isLeftEmpty(rowIndex, colIndex)){pointVal++;}
        }

        // Edge cases
        // Top edge
        else if (rowIndex == 0 && colIndex > 0 && colIndex < 11)
        {
            if(!isLeftEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isRightEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isBelowEmpty(rowIndex, colIndex)){pointVal++;}
        }
        // Bottom edge
        else if (rowIndex == 7 && colIndex > 0 && colIndex < 11)
        {
            if(!isLeftEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isRightEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isAboveEmpty(rowIndex, colIndex)){pointVal++;}
        }
        // Left edge
        else if (colIndex == 0 && rowIndex > 0 && rowIndex < 7)
        {
            if(!isAboveEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isBelowEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isRightEmpty(rowIndex, colIndex)){pointVal++;}
        }
        // Right edge
        else if (colIndex == 11 && rowIndex > 0 && rowIndex < 7)
        {
            if(!isAboveEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isBelowEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isLeftEmpty(rowIndex, colIndex)){pointVal++;}
        }

        // Body case //
        else
        {
            if(!isAboveEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isBelowEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isLeftEmpty(rowIndex, colIndex)){pointVal++;}
            if(!isRightEmpty(rowIndex, colIndex)){pointVal++;}
        }
        return pointVal;
    }

    // Place the tile on the board and clear tile selection
    // Accepts coordinate variables
    public void makeMove(int rowIndex, int colIndex, Deck deck)
    {
        tileBoard[rowIndex][colIndex].setColor(tilePreview.getColor());
        tileBoard[rowIndex][colIndex].setSymbol(tilePreview.getSymbol());
        tilePreview.setColor("green");
        tilePreview.setSymbol("");
        deck.tileDeck.remove(deck.tileDeck.size() - 1); // added
    }

    // Provides the ability to simulate a move, and get its point value
    // Accept coordinate variables, a Deck index, a Board object, and a Deck object
    // Returns the point value for the simulated move
    public int simulateMove(int rowIndex, int colIndex, int deckIndex, Board board, Deck deck)
    {
        int movePointVal;
        // Store current tile color and symbol
        String previousColor = tileBoard[rowIndex][colIndex].getColor();
        String previousSymbol = tileBoard[rowIndex][colIndex].getSymbol();
        // Ready new active tile to simulate move and calculate its score
        deck.readyTile(board, deckIndex);
        tileBoard[rowIndex][colIndex].setColor(tilePreview.getColor());
        tileBoard[rowIndex][colIndex].setSymbol(tilePreview.getSymbol());
        movePointVal = calcMovePointVal(rowIndex, colIndex);
        // Unload active tile
        deck.unReadyTile(board);
        // Revert tile color and symbol
        tileBoard[rowIndex][colIndex].setColor(previousColor);
        tileBoard[rowIndex][colIndex].setSymbol(previousSymbol);
        return movePointVal;
    }
    public void simulateMove(int rowIndex, int colIndex, Deck deck, int deckIndex)
    {
        tileBoard[rowIndex][colIndex].setColor(deck.tileDeck.get(deckIndex).getColor());
        tileBoard[rowIndex][colIndex].setSymbol(deck.tileDeck.get(deckIndex).getSymbol());
    }
    public void simulateMove(int rowIndex, int colIndex)
    {
        tileBoard[rowIndex][colIndex].setColor(tilePreview.getColor());
        tileBoard[rowIndex][colIndex].setSymbol(tilePreview.getSymbol());
    }
    // Undoes a move by clearing a specified tile
    // Accept the coordinate variables
    // Returns nothing
    public void undoMove(int rowIndex, int colIndex)
    {
        tileBoard[rowIndex][colIndex].setColor("green");
        tileBoard[rowIndex][colIndex].setSymbol("");
    }
}
