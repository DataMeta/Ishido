package edu.ramapo.dmelniko.ishido;


import java.util.ArrayList;

public class Search
{
    ArrayList<Move> unscoredMoveList = new ArrayList<>();
    ArrayList<Move> moveList = new ArrayList<>();
    int iter = 0;
    Boolean gmlNeeded = true;

    Search()
    {

    }

    // Generates a list of moves without a score heuristic value
    // Accepts a Board object
    // Returns nothing
    public void genUnscoredMoveList(Board board)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                if(board.isMoveValid(i, j))
                {
                    Move Move = new Move(i, j);
                    unscoredMoveList.add(Move);
                }
            }
        }
    }
    // Generates a list of moves with a heuristic value attached
    // Accepts a Board object
    // Returns nothing
    public void genMoveList(Board board)
    {
        int score;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                if(board.isMoveValid(i, j))
                {
                    score = board.calcMovePointVal(i, j);
                    Move Move = new Move(i, j, score);
                    moveList.add(Move);
                }
            }
        }
    }
    // Implement the Depth-First Search algorithm
    // Accepts a Board, Deck, and Computer objects
    // Returns nothing
    public void depthFirst(Board board, Deck deck, Player computer)
    {
        int rowIndex, colIndex;
        unscoredMoveList.clear();
        genUnscoredMoveList(board);
        if(unscoredMoveList.size() > 0)
        {
            rowIndex = unscoredMoveList.get(0).getRowIndex();
            colIndex = unscoredMoveList.get(0).getColIndex();

            board.makeMove(rowIndex, colIndex);
            computer.setScore(board.calcMovePointVal(rowIndex, colIndex));
            board.tileBoard[rowIndex][colIndex].setBlinkable(true);
            if (!deck.tileDeck.isEmpty())
            {
                deck.readyTile(board);
            }
        }

    }
    // Implement the Best-First Search algorithm
    // Accepts a Board, Deck, and Computer objects
    // Returns nothing
    // Implement the Breadth-First Search algorithm
    // Accepts a Board, Deck, and Computer objects
    // Returns nothing
    public void breadthFirst(Board board, Deck deck, Player computer)
    {
        int rowIndex, colIndex;

        if(iter < unscoredMoveList.size())
        {
            if(iter > 0)
            {
                rowIndex = unscoredMoveList.get(iter - 1).getRowIndex();
                colIndex = unscoredMoveList.get(iter - 1).getColIndex();
                board.undoMove(rowIndex, colIndex);
            }
            rowIndex = unscoredMoveList.get(iter).getRowIndex();
            colIndex = unscoredMoveList.get(iter).getColIndex();
            board.simulateMove(rowIndex, colIndex);
            iter++;
            gmlNeeded = false;
        }
        else
        {
            rowIndex = unscoredMoveList.get(iter - 1).getRowIndex();
            colIndex = unscoredMoveList.get(iter - 1).getColIndex();
            board.undoMove(rowIndex, colIndex);

            iter = 0;
            rowIndex = unscoredMoveList.get(iter).getRowIndex();
            colIndex = unscoredMoveList.get(iter).getColIndex();
            board.makeMove(rowIndex, colIndex);
            board.tileBoard[rowIndex][colIndex].setBlinkable(true);
            computer.setScore(board.calcMovePointVal(rowIndex, colIndex));
            if (!deck.tileDeck.isEmpty())
            {
                deck.readyTile(board);
            }
            gmlNeeded = true;
        }
    }
    public void bestFirst(Board board, Deck deck, Player computer)
    {
        int rowIndex, colIndex, score;
        int bestScore = 0, bestMoveIndex = 0;

        moveList.clear();
        genMoveList(board);

        if(moveList.size() > 0)
        {
            for (int i = 0; i < moveList.size(); i++)
            {
                score = moveList.get(i).getScore();
                if (score > bestScore)
                {
                    bestScore = score;
                    bestMoveIndex = i;
                }
            }
            rowIndex = moveList.get(bestMoveIndex).getRowIndex();
            colIndex = moveList.get(bestMoveIndex).getColIndex();
            score = moveList.get(bestMoveIndex).getScore();

            board.makeMove(rowIndex, colIndex);
            //board.tileBoard[rowIndex][colIndex].setBlinkable(true);
            computer.setScore(score);
            if (!deck.tileDeck.isEmpty())
            {
                deck.readyTile(board);
            }
        }
    }

    // Is supposed to implement the Branch & Bound Search algorithm
    // Accepts a Board, Deck, and Computer objects
    // Returns nothing
    //
    // Final Version \/
    // Get all valid moves for next level of nodes; sort move list by score
    // Add each move to a path of moves as the start node
    // Get all valid moves based on the board state of the start node in each path; sort by score
    // [. . .]
    //
    // Beta Version \*/
    // Get all valid moves for next level of nodes, sort move list by score
    // Add best move from move list to a (best start) path as the start node
    // Get all valid moves based on the board state of the start node in the path, sort by score
    // LOOP until no valid moves or deck empty: Add best move from move list to the path as the next node
    //
    public void branchAndBound(Board board, Deck deck, Player computer)
    {
        Path path = new Path(board);
        path.genMoveList(deck, deck.tileDeck.size() - 1);
        path.sortMoveList();
        path.addMove(moveList.get(moveList.size() - 1).getRowIndex(),
                moveList.get(moveList.size() - 1).getColIndex(), moveList.get(moveList.size() - 1).getScore());

        deck.readyTile(board);
        board.makeMove(moveList.get(moveList.size() - 1).getRowIndex(), moveList.get(moveList.size() - 1).getColIndex());
        // [ . . . ]
    }
}
