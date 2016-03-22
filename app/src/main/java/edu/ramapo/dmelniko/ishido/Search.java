package edu.ramapo.dmelniko.ishido;

import java.util.ArrayList;

public class Search
{
    ArrayList<Move> unscoredMoveList = new ArrayList<>();
    ArrayList<Move> moveList = new ArrayList<>();
    int iter = 0;
    Boolean gmlNeeded = true;

    int depth;
    int depthCutoff;

    Search()
    {
        depth = 0;
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

    public Node min2(Node parentNode, Deck deck)
    {
        depth++;
        Node node = new Node(parentNode);
        node.genMoveList(deck, deck.tileDeck.size() - (depth));
        int heuristicVal;
        for (Move move : node.moveList)
        {
            node.rowVal = move.getRowIndex();
            node.colVal = move.getColIndex();
            node.boardState.simulateMove(node.rowVal, node.colVal, deck, deck.tileDeck.size() - (depth));
            node.updateHumanScore(node.boardState.calcMovePointVal(node.rowVal, node.colVal));
            if(depth == depthCutoff)
            {
                heuristicVal = evaluate(node);
                if(node.bestValue > heuristicVal)
                {
                    node.bestValue = heuristicVal;
                }
            } else
            {
                Node childNode = new Node(max2(node, deck));
                if(node.bestValue > childNode.bestValue)
                {
                    node.bestValue = childNode.bestValue;
                    //node.rowVal = childNode.rowVal;
                    //node.colVal = childNode.colVal;
                }
            }
            node.boardState.undoMove(node.rowVal, node.colVal);
            node.revertHumanScore(node.boardState.calcMovePointVal(node.rowVal, node.rowVal));
        }
        depth--;
        return node;
    }

    public Node max2(Node parentNode, Deck deck)
    {
        depth++;
        Node node = new Node(parentNode);
        node.genMoveList(deck, deck.tileDeck.size() - (depth));
        int heuristicVal;
        for (Move move : node.moveList)
        {
            node.rowVal = move.getRowIndex();
            node.colVal = move.getColIndex();
            node.boardState.simulateMove(node.rowVal, node.colVal, deck, deck.tileDeck.size() - (depth));
            node.updateCompScore(node.boardState.calcMovePointVal(node.rowVal, node.colVal));
            if(depth == depthCutoff)
            {
                heuristicVal = evaluate(node);
                if(node.bestValue < heuristicVal)
                {
                    node.bestValue = heuristicVal;
                }
            } else
            {
                Node childNode = new Node(min2(node, deck));
                if(node.bestValue < childNode.bestValue)
                {
                    node.bestValue = childNode.bestValue;
                    //node.rowVal = childNode.rowVal;
                    //node.colVal = childNode.colVal;
                }
            }
            node.boardState.undoMove(node.rowVal, node.colVal);
            node.revertCompScore(node.boardState.calcMovePointVal(node.rowVal, node.rowVal));
        }
        depth--;
        return node;
    }

    public void miniMax2(Board board, Player human, Player computer, Deck deck)
    {
        Node rootNode = new Node(board, human, computer);
        Node solutionNode = new Node(max2(rootNode, deck));
        board.makeMove(solutionNode.rowVal, solutionNode.colVal);
        computer.setScore(board.calcMovePointVal(solutionNode.rowVal, solutionNode.colVal));
        deck.readyTile(board);
    }

    public int evaluate(Node node)
    {
        return node.getCompScore() - node.getHumanScore();
    }

    public Node min(Node node, Deck deck)
    {
        Move bestMove = new Move();
        bestMove.setVals(node.moveList.get(0));
        for (Move move : node.moveList)
        {
            if(bestMove.getHeuristicVal() > move.getHeuristicVal())
            {
                bestMove.setVals(move);
            }
        }
        node.bestMove.setVals(bestMove);
        return node;
    }

    public Node max(Node node, Deck deck)
    {
        Move bestMove = new Move();
        bestMove.setVals(node.moveList.get(0));
        for (Move move : node.moveList)
        {
            if(bestMove.getHeuristicVal() < move.getHeuristicVal())
            {
                bestMove.setVals(move);
            }
        }
        node.bestMove.setVals(bestMove);
        return node;
    }

    // Implements the MiniMax algorithm
    public Node miniMax(Node parentNode, Deck deck, String turn, int depth)
    {
        Node node = new Node(parentNode);
        node.genMoveList(deck, deck.tileDeck.size() - depth);
        if(depth == depthCutoff || node.moveList.isEmpty())
        {
            parentNode.setBestValue(evaluate(parentNode));
            return parentNode;
        }
        else
        {
            // Computer turn
            if(turn.equals("Computer"))
            {
                for (Move move : node.moveList)
                {
                    // Simulate move and update score for the move
                    node.boardState.simulateMove(move.getRowIndex(), move.getColIndex(), deck, deck.tileDeck.size() - (depth));
                    node.updateCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                    // Set the move's heuristic value to the best possible value that can result from it
                    move.setHeuristicVal(miniMax(node, deck, "Human", depth + 1).bestValue);
                    // Revert the previous board state and score
                    node.boardState.undoMove(move.getRowIndex(), move.getColIndex());
                    node.revertCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                }
                return new Node(max(node, deck));
            }
            // Player turn
            else
            {
                for (Move move : node.moveList)
                {
                    // Simulate move and update score for the move
                    node.boardState.simulateMove(move.getRowIndex(), move.getColIndex(), deck, deck.tileDeck.size() - (depth));
                    node.updateCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                    // Set the move's heuristic value to the best possible value that can result from it
                    move.setHeuristicVal(miniMax(node, deck, "Computer", depth + 1).bestValue);
                    // Revert the previous board state and score
                    node.boardState.undoMove(move.getRowIndex(), move.getColIndex());
                    node.revertCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                }
                return new Node(min(node, deck));
            }
        }
    }

    public void miniMaxWrapper(Board board, Player human, Player computer, Deck deck)
    {
        Node rootNode = new Node(board, human, computer);
        Node solutionNode = new Node(miniMax(rootNode, deck, "Computer", depth + 1));
        board.makeMove(solutionNode.bestMove.getRowIndex(), solutionNode.bestMove.getColIndex());
        computer.setScore(board.calcMovePointVal(solutionNode.bestMove.getRowIndex(), solutionNode.bestMove.getColIndex()));
        deck.readyTile(board);
    }

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
    // Implement the Best-First Search algorithm
    // Accepts a Board, Deck, and Computer objects
    // Returns nothing
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

    /*
01 function minimax(node, depth, maximizingPlayer)
02      if depth = 0 or node is a terminal node
03           return the heuristic value of node

04      if maximizingPlayer
05           bestValue := −∞
06           for each child of node
07                 v := minimax(child, depth − 1, FALSE)
08                 bestValue := max(bestValue, v)
09           return bestValue

10       else    (* minimizing player *)
11           bestValue := +∞
12           for each child of node
13               v := minimax(child, depth − 1, TRUE)
14               bestValue := min(bestValue, v)
15           return bestValue
*/
}
