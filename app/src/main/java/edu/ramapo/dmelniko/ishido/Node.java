package edu.ramapo.dmelniko.ishido;

import java.util.ArrayList;

public class Node
{
    Board boardState;
    ArrayList<Move> moveList;
    Move bestMove;
    int humanScore;
    int compScore;
    int parentHeuristicVal;
    Boolean isRootNode;

    Node(Board parentBoard, Player human, Player computer)
    {
        boardState = new Board(parentBoard);
        moveList = new ArrayList<>();
        bestMove = new Move();
        humanScore = human.getScore();
        compScore = computer.getScore();
        isRootNode = true;
    }
    Node(Node parentNode)
    {
        boardState = new Board(parentNode.boardState);
        moveList = new ArrayList<>();
        bestMove = new Move();
        humanScore = parentNode.getHumanScore();
        compScore = parentNode.getCompScore();
        isRootNode = false;
    }
    Node(Node parentNode, Boolean copyBestMove)
    {
        boardState = new Board(parentNode.boardState);
        moveList = new ArrayList<>();
        bestMove = new Move();
        bestMove.setVals(parentNode.bestMove);
        humanScore = parentNode.getHumanScore();
        compScore = parentNode.getCompScore();
        isRootNode = false;
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
                    Move move = new Move(i, j, score);
                    this.moveList.add(move);
                } else
                {
                    deck.unReadyTile(boardState);
                }
            }
        }
    }

    public void setHeuristics(Node parentNode, String turn)
    {
        if(turn.equals("Human"))
        {
            this.bestMove.setHeuristicVal(Integer.MIN_VALUE);
            this.setParentHeuristicVal(parentNode.bestMove.getHeuristicVal());
        }
        else
        {
            this.bestMove.setHeuristicVal(Integer.MAX_VALUE);
            this.setParentHeuristicVal(parentNode.bestMove.getHeuristicVal());
        }
    }


    public Boolean getIsRootNode()
    {
        return isRootNode;
    }
    public void setIsRootNode(Boolean value)
    {
        this.isRootNode = value;
    }
    public void setBestMove(Move bestMove)
    {
        this.bestMove.setVals(bestMove);
    }
    public Move getBestMove()
    {
        return bestMove;
    }
    public int getParentHeuristicVal()
    {
        return parentHeuristicVal;
    }
    public void setParentHeuristicVal(int heuristicVal)
    {
        this.parentHeuristicVal = parentHeuristicVal;
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
