/***********************************************************
* Name:  Daniel Melnikov                                   *
* Project:  Project 1 - Ishido for One                     *
* Class:  Artificial Intelligence - CMPS 331               *
* Date:  2/6/16                                            *
************************************************************/


package edu.ramapo.dmelniko.ishido;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Board board;
    Deck deck;
    Player computer;
    Search search = new Search();

    Button tilePreviewButton;

    TextView scoreKeep;

    Switch modeSelectSwitch;

    Spinner searchSpinner;

    String serialFile;
    String boardData;
    String deckData;
    String scoreData;

    String tileColorID;
    String tileSymbolID;

    int tileColor;
    int tileSymbol;
    int randTileMode;
    int rowIndex;
    int colIndex;

    Boolean tileFound;

    Button[][] BoardView = new Button[8][12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.ramapo.dmelniko.ishido.R.layout.activity_main);

        BoardView[0][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button1);
        BoardView[0][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button2);
        BoardView[0][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button3);
        BoardView[0][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button4);
        BoardView[0][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button5);
        BoardView[0][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button6);
        BoardView[0][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button7);
        BoardView[0][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button8);
        BoardView[0][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button9);
        BoardView[0][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button10);
        BoardView[0][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button11);
        BoardView[0][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button12);

        BoardView[1][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button13);
        BoardView[1][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button14);
        BoardView[1][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button15);
        BoardView[1][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button16);
        BoardView[1][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button17);
        BoardView[1][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button18);
        BoardView[1][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button19);
        BoardView[1][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button20);
        BoardView[1][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button21);
        BoardView[1][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button22);
        BoardView[1][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button23);
        BoardView[1][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button24);

        BoardView[2][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button25);
        BoardView[2][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button26);
        BoardView[2][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button27);
        BoardView[2][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button28);
        BoardView[2][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button29);
        BoardView[2][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button30);
        BoardView[2][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button31);
        BoardView[2][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button32);
        BoardView[2][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button33);
        BoardView[2][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button34);
        BoardView[2][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button35);
        BoardView[2][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button36);

        BoardView[3][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button37);
        BoardView[3][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button38);
        BoardView[3][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button39);
        BoardView[3][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button40);
        BoardView[3][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button41);
        BoardView[3][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button42);
        BoardView[3][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button43);
        BoardView[3][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button44);
        BoardView[3][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button45);
        BoardView[3][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button46);
        BoardView[3][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button47);
        BoardView[3][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button48);

        BoardView[4][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button49);
        BoardView[4][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button50);
        BoardView[4][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button51);
        BoardView[4][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button52);
        BoardView[4][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button53);
        BoardView[4][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button54);
        BoardView[4][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button55);
        BoardView[4][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button56);
        BoardView[4][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button57);
        BoardView[4][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button58);
        BoardView[4][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button59);
        BoardView[4][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button60);

        BoardView[5][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button61);
        BoardView[5][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button62);
        BoardView[5][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button63);
        BoardView[5][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button64);
        BoardView[5][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button65);
        BoardView[5][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button66);
        BoardView[5][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button67);
        BoardView[5][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button68);
        BoardView[5][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button69);
        BoardView[5][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button70);
        BoardView[5][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button71);
        BoardView[5][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button72);

        BoardView[6][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button73);
        BoardView[6][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button74);
        BoardView[6][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button75);
        BoardView[6][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button76);
        BoardView[6][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button77);
        BoardView[6][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button78);
        BoardView[6][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button79);
        BoardView[6][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button80);
        BoardView[6][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button81);
        BoardView[6][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button82);
        BoardView[6][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button83);
        BoardView[6][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button84);

        BoardView[7][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button85);
        BoardView[7][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button86);
        BoardView[7][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button87);
        BoardView[7][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button88);
        BoardView[7][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button89);
        BoardView[7][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button90);
        BoardView[7][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button91);
        BoardView[7][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button92);
        BoardView[7][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button93);
        BoardView[7][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button94);
        BoardView[7][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button95);
        BoardView[7][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button96);


        // Code to programmatically initialize buttons
        // (Theoretically works, but I'm unsure of it being good practice)
        /*int idCount = 0;
        int buttonID = 0x7f0d0050; // the hex identifier of button1
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                buttonID = buttonID + idCount;
                BoardView[i][j] = (Button)findViewById(buttonID);
                idCount++;
            }
        }*/

        tilePreviewButton = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button_tile_preview);

        scoreKeep = (TextView) findViewById(edu.ramapo.dmelniko.ishido.R.id.score_keep);
        scoreKeep.setText("SCORE: 0");

        // Spinner to select different search algorithms
        searchSpinner = (Spinner) findViewById(edu.ramapo.dmelniko.ishido.R.id.spinner_search);

        final List<String> searchList = new ArrayList<>();
        searchList.add("DEPTH-FIRST");
        searchList.add("BREADTH-FIRST");
        searchList.add("BEST-FIRST");
        searchList.add("BRANCH&BOUND");

        // Create ArrayAdapters using the string arrays and a default spinner layout
        ArrayAdapter<String> searchDataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,searchList);

        // Specify the layout to use when the list of choices appears
        searchDataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        searchSpinner.setPrompt("SEARCH ALGORITHMS");
        searchSpinner.setAdapter(searchDataAdapter);

        try
        {
            serialFile = readData();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        parseData();
        loadData();
        deck.readyTile(board);
        updateBoardView();
    }

    // Reads from the file asset
    // Accepts no parameters
    // Returns the file in string form
    public String readData() throws IOException
    {
        InputStream input1 = getAssets().open("case1.txt");
        InputStream input2 = getAssets().open("case2.txt");
        InputStream input3 = getAssets().open("case3.txt");
        InputStream input4 = getAssets().open("case4.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input3));
        String output = new String();
        String line;
        while ((line = reader.readLine()) != null) {
            output += line;
        }
        reader.close();
        return output;
    }

    // Parses the serialization data into separate sections
    // Has no parameters
    // Returns nothing
    public void parseData()
    {
        String delim = "Layout:|Stock:|Score:";
        String[] tokens = serialFile.split(delim);

        boardData = tokens[1];
        deckData = tokens[2];
        scoreData = tokens[3];
    }

    // Initializes the board, deck, and computer objects
    // by calling custom constructors and passing them the appropriate serialized data
    // Accepts no parameters
    // Returns nothing
    public void loadData()
    {
        board = new Board(boardData);
        deck = new Deck(deckData);
        computer = new Player(scoreData);
    }

    // Runs the selected search algorithm
    // Takes the button view as a parameter
    // Returns nothing
    public void nextTile(View view)
    {
        String searchType;
        searchType = searchSpinner.getSelectedItem().toString();
        switch (searchType)
        {
            case "DEPTH-FIRST":
                if(deck.tileDeck.isEmpty() && board.tilePreview.getSymbol().equals(""))
                {
                    Toast.makeText(MainActivity.this, "OUT OF TILES", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    search.depthFirst(board, deck, computer);
                    updateBoardView();
                }
                break;
            case "BREADTH-FIRST":

                if(deck.tileDeck.isEmpty() && board.tilePreview.getSymbol().equals(""))
                {
                    Toast.makeText(MainActivity.this, "OUT OF TILES", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(search.gmlNeeded)
                    {
                        search.unscoredMoveList.clear();
                        search.genUnscoredMoveList(board);
                        if(search.unscoredMoveList.size() > 0)
                        {
                            search.breadthFirst(board, deck, computer);
                            updateBoardView();
                        }
                        //Toast.makeText(MainActivity.this, "OUT OF MOVES", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(search.unscoredMoveList.size() > 0)
                        {
                            search.breadthFirst(board, deck, computer);
                            updateBoardView();
                        }
                        //Toast.makeText(MainActivity.this, "OUT OF MOVES", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case "BEST-FIRST":
                if(deck.tileDeck.isEmpty() && board.tilePreview.getSymbol().equals(""))
                {
                    Toast.makeText(MainActivity.this, "OUT OF TILES", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    search.bestFirst(board, deck, computer);
                    updateBoardView();
                }
                break;
            case "BRANCH&BOUND":
                search.branchAndBound(board, deck, computer);
                updateBoardView();
                break;
        }
    }

    // Updates all visual elements
    // No parameters are passed
    // Nothing is returned
    public void updateBoardView()
    {
        String scoreMsg = "SCORE: ";

        tilePreviewButton.setText(board.tilePreview.getSymbol());
        tilePreviewButton.setTextColor(Color.parseColor(board.tilePreview.getColor()));
        tilePreviewButton.setTextSize(42);

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                BoardView[i][j].setText(board.tileBoard[i][j].getSymbol());
                BoardView[i][j].setTextColor(Color.parseColor(board.tileBoard[i][j].getColor()));
                BoardView[i][j].setTextSize(42);
                if(board.tileBoard[i][j].getBlinkable().equals(true))
                {
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(360);
                    animation.setStartOffset(20);
                    animation.setRepeatMode(Animation.REVERSE);
                    animation.setRepeatCount(Animation.INFINITE);
                    BoardView[i][j].startAnimation(animation);
                }
            }
        }
        scoreMsg = scoreMsg + (Integer.toString(computer.getScore()));
        scoreKeep.setText(scoreMsg);
    }

    // Generates a random color and symbol for the tile that is to be placed
    // or accepts the user selections from the drop-down menus
    // A view object is passed, particularly from the Select Tile button
    // Nothing is returned
    public void selectTile(View view)
    {
        // Check if a tile is already on standby
        if(!(board.tilePreview.getColor() == "green" && board.tilePreview.getSymbol() == ""))
        {
            Toast.makeText(MainActivity.this, "You have already selected a tile, please place it before selecting another one", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(modeSelectSwitch.isChecked())
            {
                randTileMode = 1;
            }
            else if(!modeSelectSwitch.isChecked())
            {
                randTileMode = 0;
            }

            switch(randTileMode)
            {
                // Manual selection is <ON>
                // Random generation is <OFF>
                case 0:
                    //tileSymbol = symbolSpinner.getSelectedItemPosition();
                    //tileColor = colorSpinner.getSelectedItemPosition();

                    switch (tileColor)
                    {
                        case 0: tileColorID = "white";
                            break;
                        case 1: tileColorID = "blue";
                            break;
                        case 2: tileColorID = "black";
                            break;
                        case 3: tileColorID = "yellow";
                            break;
                        case 4: tileColorID = "magenta";
                            break;
                        case 5: tileColorID = "red";
                            break;
                        default:
                            break;
                    }
                    switch (tileSymbol)
                    {
                        case 0: tileSymbolID = "+";
                            break;
                        case 1: tileSymbolID = "#";
                            break;
                        case 2: tileSymbolID = "%";
                            break;
                        case 3: tileSymbolID = "!";
                            break;
                        case 4: tileSymbolID = "?";
                            break;
                        case 5: tileSymbolID = "@";
                            break;
                        default:
                            break;
                    }
                    if(!deck.tileDeck.isEmpty())
                    {
                        tileFound = false;
                        for(int i = 0; i < deck.tileDeck.size(); i++)
                        {
                            if(deck.tileDeck.get(i).getColor() == tileColorID
                                    && deck.tileDeck.get(i).getSymbol() == tileSymbolID)
                            {
                                board.tilePreview.setColor(deck.tileDeck.get(i).getColor());
                                board.tilePreview.setSymbol(deck.tileDeck.get(i).getSymbol());
                                deck.tileDeck.remove(i);
                                tileFound = true;
                                break;
                            }
                        }
                        if(!tileFound)
                        {
                            Toast.makeText(MainActivity.this, "No more tiles of this kind - please select another", Toast.LENGTH_SHORT).show();
                            tileFound = false;
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Good game! Try for a better score next time!", Toast.LENGTH_LONG).show();
                    }
                    break;

                // Manual selection is <OFF>
                // Random generation is <ON>
                case 1:
                    if(!deck.tileDeck.isEmpty())
                    {
                        int lastIndex = deck.tileDeck.size() - 1;
                        board.tilePreview.setColor(deck.tileDeck.get(lastIndex).getColor());
                        board.tilePreview.setSymbol(deck.tileDeck.get(lastIndex).getSymbol());
                        deck.tileDeck.remove(lastIndex);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Good game! Try for a better score next time!", Toast.LENGTH_LONG).show();
                    }

                    break;
                default:
                    break;
            }
        }
        updateBoardView();
    }

    // Handles the user's actions on the UI
    // A view object is passed, particularly from one of the buttons part of the board display
    // Nothing is returned
    public void onClick(View view)
    {
        /*if(board.tilePreview.getColor() == "green" && board.tilePreview.getSymbol() == "")
        {
            Toast.makeText(MainActivity.this, "Please select a tile to place", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 12; j++)
                {
                    if (view == BoardView[i][j])
                    {
                        rowIndex = i;
                        colIndex = j;
                    }
                }
            }

            //Check for move validity
            if (board.isMoveValid(rowIndex, colIndex))
            {
                // Place the tile on the board and clear tile selection
                board.makeMove(rowIndex, colIndex);

                // Set score for move just made
                computer.setScore(board.calcMovePointVal(rowIndex, colIndex));

                // Update the board view
                BoardView[rowIndex][colIndex].setClickable(false);
                updateBoardView();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Invalid move. Please try again", Toast.LENGTH_SHORT).show();
            }
        }*/
    }
}

