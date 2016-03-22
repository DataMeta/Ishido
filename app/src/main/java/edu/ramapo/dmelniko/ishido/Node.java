package edu.ramapo.dmelniko.ishido;

import java.util.ArrayList;

public class Node
{
    Board boardState;
    ArrayList<Move> moveList;
    Move bestMove;
    int humanScore;
    int compScore;
    int bestValue;
    int rowVal, colVal;

    Node(Board parentBoard, Player human, Player computer)
    {
        boardState = new Board(parentBoard);
        moveList = new ArrayList<>();
        bestMove = new Move();
        humanScore = human.getScore();
        compScore = computer.getScore();
        bestValue = 0;
    }
    Node(Node parentNode)
    {
        boardState = new Board(parentNode.boardState);
        moveList = new ArrayList<>();
        bestMove.setVals(parentNode.bestMove);
        humanScore = parentNode.getHumanScore();
        compScore = parentNode.getCompScore();
        bestValue = parentNode.getBestValue();
        //rowVal = parentNode.rowVal;
        //colVal = parentNode.colVal;
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
                } else
                {
                    deck.unReadyTile(boardState);
                }
            }
        }
    }

    public void setBestValue(int value)
    {
        this.bestValue = value;
    }
    public int getBestValue()
    {
        return bestValue;
    }
    public int getHumanScore()
    {
        return humanScore;
    }
    public int getCompScore()
    {
        return compScore;
    }
    public void updateHumanScore(int score)
    {
        this.humanScore += score;
    }
    public void updateCompScore(int score)
    {
        this.compScore += score;
    }
    public void revertHumanScore(int score)
    {
        this.humanScore -= score;
    }
    public void revertCompScore(int score)
    {
        this.compScore -= score;
    }
}
