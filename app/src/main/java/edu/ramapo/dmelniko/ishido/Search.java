package edu.ramapo.dmelniko.ishido;

public class Search
{
    int depth;
    int depthCutoff;

    int blinkRow, blinkCol;
    int lastMoveRow, lastMoveCol;
    int moveRow, moveCol;

    Search()
    {
        depth = 0;
        blinkRow = 0;
        blinkCol = 0;
    }

    // Sets the depth cutoff
    // Accepts an integer for cutoff
    // Returns nothing
    public void setDepthCutoff(int depthCutoff)
    {
        this.depthCutoff = depthCutoff;
    }

    // Calculates the heuristic function of a leaf node
    // Accepts a Node object
    // Returns an integer
    public int evaluate(Node node)
    {
        return node.getCompScore() - node.getHumanScore();
    }

    // Determines the move with the minimum heuristic value
    // Takes a Node object
    // Returns the node with the best move saved
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

    // Determines the move with the maximum heuristic value
    // Takes a Node object
    // Returns the node with the best move saved
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
    // Takes a Node object, Deck object, a String, and an integer depth
    // Returns a node object
    public Node miniMax(Node parentNode, Deck deck, String turn, int depth)
    {
        if(depth == depthCutoff)
        {
            parentNode.bestMove.setHeuristicVal(evaluate(parentNode));
            return parentNode;
        }
        else
        {
            // Maximizing
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
            // Minimizing
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

    // Implements the MiniMax w/ Alpha-Beta Pruning algorithm
    // Takes a Node object, Deck object, a String, and an integer depth
    // Returns a node object
    public Node alphaBeta(Node parentNode, Deck deck, String turn, int depth)
    {
        if(depth == depthCutoff)
        {
            parentNode.bestMove.setHeuristicVal(evaluate(parentNode));
            return parentNode;
        }
        else
        {
            // Maximizing
            if(turn.equals("Computer"))
            {
                Node node = new Node(parentNode);
                node.genMoveList(deck, deck.tileDeck.size() - (depth + 1));
                node.setHeuristics(parentNode, turn);
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

                    // If this move's heuristic is greater than the parent, overwrite the parent's heuristic value
                    if (node.getParentHeuristicVal() < move.getHeuristicVal() )
                    {
                        node.setParentHeuristicVal(move.getHeuristicVal());
                    }
                    // If the parent and grandparent heuristic value don't intersect, prune and return heuristic value
                    if (!parentNode.getIsRootNode() && (parentNode.getParentHeuristicVal() != Integer.MAX_VALUE)
                            && (parentNode.getParentHeuristicVal() < move.getHeuristicVal()))
                    {
                        node.bestMove.setHeuristicVal(node.getParentHeuristicVal());
                        return node;
                    }
                }
                // If the parent is a root node and all the nodes have been evaluated, pass the row/col to the root
                if(parentNode.getIsRootNode())
                {
                    node.setBestMove(max(node).getBestMove());
                }
                // Otherwise send the heuristic value to the parent
                else
                {
                    node.bestMove.setHeuristicVal(node.getParentHeuristicVal());
                }
                return node;
            }
            // Minimizing
            else
            {
                Node node = new Node(parentNode);
                node.genMoveList(deck, deck.tileDeck.size() - (depth + 1));
                node.setHeuristics(parentNode, turn);
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

                    // If this move's heuristic is greater than the parent, overwrite the parent's heuristic value
                    if (node.getParentHeuristicVal() > move.getHeuristicVal() )
                    {
                        node.setParentHeuristicVal(move.getHeuristicVal());
                    }
                    // If the parent and grandparent heuristic value don't intersect, prune and return heuristic value
                    if (!parentNode.getIsRootNode() && (parentNode.getParentHeuristicVal() != Integer.MIN_VALUE)
                            && (parentNode.getParentHeuristicVal() > move.getHeuristicVal()))
                    {
                        node.bestMove.setHeuristicVal(node.getParentHeuristicVal());
                        return node;
                    }
                }
                // Send the heuristic value up to the parent
                node.bestMove.setHeuristicVal(node.getParentHeuristicVal());
                return node;
            }
        }
    }

    // Handler function for the Minimax and Minimax w/ Alpha-Beta Pruning algorithms
    // Takes a Board object, two Player objects, a Deck object, and a Boolean
    // Returns an integer, the point value of the move made
    public int miniMaxWrapper(Board board, Player human, Player computer, Deck deck, Boolean usePruning)
    {
        if (!usePruning)
        {
            Node rootNode = new Node(board, human, computer);
            Node solutionNode = new Node(miniMax(rootNode, deck, "Computer", depth), true);
            moveRow = solutionNode.bestMove.getRowIndex();
            moveCol = solutionNode.bestMove.getColIndex();
        }
        else
        {
            Node rootNode = new Node(board, human, computer);
            rootNode.bestMove.setHeuristicVal(Integer.MIN_VALUE);
            Node solutionNode = new Node(alphaBeta(rootNode, deck, "Computer", depth), true);
            moveRow = solutionNode.bestMove.getRowIndex();
            moveCol = solutionNode.bestMove.getColIndex();
        }

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

    // Player help version of the handler
    // Takes a Board object, two Player objects, a Deck object, and a Boolean
    // Returns nothing
    public void miniMaxHelp(Board board, Player human, Player computer, Deck deck, Boolean usePruning)
    {
        if (!usePruning)
        {
            Node rootNode = new Node(board, human, computer);
            Node solutionNode = new Node(miniMax(rootNode, deck, "Computer", depth), true);
            moveRow = solutionNode.bestMove.getRowIndex();
            moveCol = solutionNode.bestMove.getColIndex();
        }
        else
        {
            Node rootNode = new Node(board, human, computer);
            rootNode.bestMove.setHeuristicVal(Integer.MIN_VALUE);
            Node solutionNode = new Node(alphaBeta(rootNode, deck, "Computer", depth), true);
            moveRow = solutionNode.bestMove.getRowIndex();
            moveCol = solutionNode.bestMove.getColIndex();
        }

        board.tileBoard[moveRow][moveCol].setColor(board.tilePreview.getColor());
        board.tileBoard[moveRow][moveCol].setSymbol(board.tilePreview.getSymbol());
        board.tileBoard[lastMoveRow][lastMoveCol].setBlinkable(false);
        board.tileBoard[moveRow][moveCol].setBlinkable(true);
        lastMoveRow = moveRow;
        lastMoveCol = moveCol;
    }
}
