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

    int blinkRow, blinkCol;
    int moveRow, moveCol;

    Search()
    {
        depth = 0;
        blinkRow = 0;
        blinkCol = 0;
    }

    public void setDepthCutoff(int depthCutoff)
    {
        this.depthCutoff = depthCutoff;
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

    public int evaluate(Node node)
    {
        return node.getCompScore() - node.getHumanScore();
    }

    public Node min(Node node)
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
        node.setBestMove(bestMove);
        return node;
    }

    public Node max(Node node)
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
        node.setBestMove(bestMove);
        return node;
    }

    // Implements the MiniMax algorithm
    public Node miniMax(Node parentNode, Deck deck, String turn, int depth)
    {
        if(depth == depthCutoff)
        {
            parentNode.bestMove.setHeuristicVal(evaluate(parentNode));
            return parentNode;
        }
        else
        {
            // Computer turn
            if(turn.equals("Computer"))
            {
                Node node = new Node(parentNode);
                node.genMoveList(deck, deck.tileDeck.size() - (depth + 1));
                for (Move move : node.moveList)
                {
                    // Simulate move and update score for the move
                    node.boardState.simulateMove(move.getRowIndex(), move.getColIndex(), deck, deck.tileDeck.size() - (depth + 1));
                    node.updateCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                    // Set the move's heuristic value to the best possible value that can result from it
                    move.setHeuristicVal(miniMax(node, deck, "Human", depth + 1).getBestMove().getHeuristicVal());
                    // Revert the previous board state and score
                    node.boardState.undoMove(move.getRowIndex(), move.getColIndex());
                    node.revertCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                }
                return max(node);
            }
            // Player turn
            else
            {
                Node node = new Node(parentNode);
                node.genMoveList(deck, deck.tileDeck.size() - (depth + 1));
                for (Move move : node.moveList)
                {
                    // Simulate move and update score for the move
                    node.boardState.simulateMove(move.getRowIndex(), move.getColIndex(), deck, deck.tileDeck.size() - (depth + 1));
                    node.updateHumanScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                    // Set the move's heuristic value to the best possible value that can result from it
                    move.setHeuristicVal(miniMax(node, deck, "Computer", depth + 1).getBestMove().getHeuristicVal());
                    // Revert the previous board state and score
                    node.boardState.undoMove(move.getRowIndex(), move.getColIndex());
                    node.revertHumanScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                }
                return min(node);
            }
        }
    }

    public Node alphaBeta(Node parentNode, Deck deck, String turn, int depth)
    {
        if(depth == depthCutoff)
        {
            parentNode.bestMove.setHeuristicVal(evaluate(parentNode));
            return parentNode;
        }
        else
        {
            // Computer turn
            if(turn.equals("Computer"))
            {
                Node node = new Node(parentNode);
                node.genMoveList(deck, deck.tileDeck.size() - (depth + 1));
                for (Move move : node.moveList)
                {
                    // Simulate move and update score for the move
                    node.boardState.simulateMove(move.getRowIndex(), move.getColIndex(), deck, deck.tileDeck.size() - (depth + 1));
                    node.updateCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                    // Set the move's heuristic value to the best possible value that can result from it
                    move.setHeuristicVal(alphaBeta(node, deck, "Human", depth + 1).getBestMove().getHeuristicVal());
                    // Revert the previous board state and score
                    node.boardState.undoMove(move.getRowIndex(), move.getColIndex());
                    node.revertCompScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                }
                return max(node);
            }
            // Player turn
            else
            {
                Node node = new Node(parentNode);
                node.genMoveList(deck, deck.tileDeck.size() - (depth + 1));
                for (Move move : node.moveList)
                {
                    // Simulate move and update score for the move
                    node.boardState.simulateMove(move.getRowIndex(), move.getColIndex(), deck, deck.tileDeck.size() - (depth + 1));
                    node.updateHumanScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                    // Set the move's heuristic value to the best possible value that can result from it
                    move.setHeuristicVal(alphaBeta(node, deck, "Computer", depth + 1).getBestMove().getHeuristicVal());
                    // Revert the previous board state and score
                    node.boardState.undoMove(move.getRowIndex(), move.getColIndex());
                    node.revertHumanScore(node.boardState.calcMovePointVal(move.getRowIndex(), move.getColIndex()));
                }
                return min(node);
            }
        }
    }

    public int miniMaxWrapper(Board board, Player human, Player computer, Deck deck)
    {
        Node rootNode = new Node(board, human, computer);
        Node solutionNode = new Node(miniMax(rootNode, deck, "Computer", depth), true);

        moveRow = solutionNode.bestMove.getRowIndex();
        moveCol = solutionNode.bestMove.getColIndex();
        board.makeMove(moveRow, moveCol, deck);
        computer.setScore(board.calcMovePointVal(moveRow, moveCol));

        board.tileBoard[blinkRow][blinkCol].setBlinkable(false);
        board.tileBoard[moveRow][moveCol].setBlinkable(true);
        blinkRow = moveRow;
        blinkCol = moveCol;
        if (!deck.tileDeck.isEmpty())
        {
            deck.readyTile(board);
        }
        return board.calcMovePointVal(moveRow, moveCol);
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

            board.makeMove(rowIndex, colIndex, deck);
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
            board.makeMove(rowIndex, colIndex, deck);
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

            board.makeMove(rowIndex, colIndex, deck);
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
