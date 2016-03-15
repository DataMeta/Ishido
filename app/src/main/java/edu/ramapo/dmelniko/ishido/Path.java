package edu.ramapo.dmelniko.ishido;

import java.util.ArrayList;

public class Path
{
    Board boardState;
    ArrayList<Move> movePath;
    ArrayList<Move> moveList;
    int pathScore;

    Path(Board MainBoard)
    {
        boardState = new Board(MainBoard);
        movePath = new ArrayList<>();
        moveList = new ArrayList<>();
        pathScore = 0;
    }

    // Generates a list of moves
    // Accepts a deck object, and a deck index
    // Returns nothing
    public void genMoveList(Deck deck, int deckIndex)
    {
        int score;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                deck.readyTile(boardState, deckIndex);
                if(boardState.isMoveValid(i, j))
                {
                    deck.unReadyTile(boardState);
                    score = boardState.simulateMove(i, j, deckIndex, boardState, deck);
                    Move Move = new Move(i, j, score);
                    moveList.add(Move);
                }
            }
        }
    }
    // Sorts the list of moves
    // Accepts no parameters
    // Returns nothing
    public void sortMoveList()
    {
        int tempRowIndex, tempColIndex, tempScore;
        for (int i = 1; i < moveList.size(); i++)
        {
            for(int j = i ; j > 0 ; j--)
            {
                if(moveList.get(j).getScore() < moveList.get(j - 1).getScore())
                {
                    tempScore = moveList.get(j).getScore();
                    tempRowIndex = moveList.get(j).getRowIndex();
                    tempColIndex = moveList.get(j).getColIndex();

                    moveList.get(j).setScore(moveList.get(j - 1).getScore());
                    moveList.get(j).setRowIndex(moveList.get(j - 1).getRowIndex());
                    moveList.get(j).setColIndex(moveList.get(j - 1).getColIndex());

                    moveList.get(j - 1).setScore(tempScore);
                    moveList.get(j - 1).setRowIndex(tempRowIndex);
                    moveList.get(j - 1).setColIndex(tempColIndex);
                }
            }
        }
    }

    public int compareMove(Move move1, Move move2)
    {
        return move2.getScore() - move1.getScore();
    }

    // Adds a move to the move list
    // Accepts coordinate variables and the score
    // Returns nothing
    public void addMove(int rowIndex, int colIndex, int score)
    {
        Move move = new Move(rowIndex, colIndex, score);
        movePath.add(move);
        boardState.makeMove(rowIndex, colIndex);
        updatePathScore(score);
    }

    // Updates the total score of the path (consisting of a list of moves)
    // Accepts a score integer variable
    // Returns nothing
    public void updatePathScore(int score)
    {
        pathScore += score;
    }
}
