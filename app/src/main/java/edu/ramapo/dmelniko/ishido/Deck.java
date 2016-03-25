package edu.ramapo.dmelniko.ishido;

import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
    private String colorVar;
    private String symbolVar;

    public ArrayList<Tile> tileDeck = new ArrayList<>();

    public Deck()
    {
        int indexCount = 0;
        for(int i = 0; i < 72; i++)
        {
            tileDeck.add(new Tile());
        }

        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j < 6; j++)
            {
                switch(i)
                {
                    case 0: colorVar = "white";
                        break;
                    case 1: colorVar = "blue";
                        break;
                    case 2: colorVar = "black";
                        break;
                    case 3: colorVar = "yellow";
                        break;
                    case 4: colorVar = "magenta";
                        break;
                    case 5: colorVar = "red";
                        break;
                }
                switch(j)
                {
                    case 0: symbolVar = "+";
                        break;
                    case 1: symbolVar = "#";
                        break;
                    case 2: symbolVar = "%";
                        break;
                    case 3: symbolVar = "!";
                        break;
                    case 4: symbolVar = "?";
                        break;
                    case 5: symbolVar = "@";
                        break;
                }

                Tile tempTile = new Tile();
                tempTile.setColor(colorVar);
                tempTile.setSymbol(symbolVar);
                tileDeck.set(indexCount, tempTile);
                indexCount++;

                Tile tempTile2 = new Tile();
                tempTile2.setColor(colorVar);
                tempTile2.setSymbol(symbolVar);
                tileDeck.set(indexCount, tempTile2);
                indexCount++;
            }
        }
        Collections.shuffle(tileDeck);
    }

    public Deck (String deckData)
    {
        int switchColor;
        int switchSymbol;
        String[] tokens = deckData.split("[ ]");
        int k = 0;

        for(int i = 0; i < tokens.length; i++)
        {
            tileDeck.add(new Tile());
            switchColor = Character.getNumericValue(tokens[k].charAt(0));
            switchSymbol = Character.getNumericValue(tokens[k].charAt(1));

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
            Tile tempTile = new Tile();
            tempTile.setColor(colorVar);
            tempTile.setSymbol(symbolVar);
            tileDeck.set(i, tempTile);
            k++;
        }
        Collections.reverse(tileDeck);
    }

    // Places a tile to the active tile index, which is used for all search operations
    // Accepts a Board object
    // Returns nothing
    public void readyTile(Board board)
    {
        int lastIndex = tileDeck.size() - 1;
        board.tilePreview.setColor(tileDeck.get(lastIndex).getColor());
        board.tilePreview.setSymbol(tileDeck.get(lastIndex).getSymbol());
        //tileDeck.remove(lastIndex);
    }
    // Places a tile to the active tile index, which is used for all search operations
    // Accepts a Board object and a deck index
    // Returns nothing
    public void readyTile(Board board, int deckIndex)
    {
        colorVar = board.tilePreview.getColor();
        symbolVar = board.tilePreview.getSymbol();
        board.tilePreview.setColor(tileDeck.get(deckIndex).getColor());
        board.tilePreview.setSymbol(tileDeck.get(deckIndex).getSymbol());
    }
    // Replaces the active tile with the previous
    // Accepts a Board object
    // Returns nothing
    public void unReadyTile(Board board)
    {
        board.tilePreview.setColor(colorVar);
        board.tilePreview.setSymbol(symbolVar);
    }
    public void clearTile(Board board)
    {
        board.tilePreview.setColor("green");
        board.tilePreview.setSymbol("");
    }
}
