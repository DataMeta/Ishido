/****************************************************************
* Name:  Daniel Melnikov                                        *
* Project:  Project 3 - Ishido with MiniMax & AlphaBeta Pruning *
* Class:  Artificial Intelligence - CMPS 331                    *
* Date:  2/6/16                                                 *
*****************************************************************/


package edu.ramapo.dmelniko.ishido;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
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

public class MainActivity extends Activity
{
    Board board;
    Deck deck;
    Player computer;
    Player human;
    Search search = new Search();

    Button tilePreviewButton;
    Button moveHelp;

    TextView scoreKeep;
    TextView scoreKeepHuman;
    TextView currentPlayer;

    Switch modeSelectSwitch;

    Spinner searchSpinner;

    String serialFile;
    String boardData;
    String deckData;
    String computerScoreData;
    String humanScoreData;
    String nextPlayerData;
    String firstPlayer;

    String tileColorID;
    String tileSymbolID;

    int tileColor;
    int tileSymbol;
    int randTileMode;
    int rowIndex;
    int colIndex;

    Boolean tileFound;
    Boolean isNewGame;

    TextView[] stockView = new TextView[72];
    Button[][] boardView = new Button[8][12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.ramapo.dmelniko.ishido.R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            isNewGame = extras.getBoolean("isNewGame");
            firstPlayer = extras.getString("firstPlayer");
        }

        boardView[0][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button1);
        boardView[0][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button2);
        boardView[0][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button3);
        boardView[0][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button4);
        boardView[0][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button5);
        boardView[0][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button6);
        boardView[0][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button7);
        boardView[0][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button8);
        boardView[0][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button9);
        boardView[0][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button10);
        boardView[0][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button11);
        boardView[0][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button12);

        boardView[1][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button13);
        boardView[1][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button14);
        boardView[1][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button15);
        boardView[1][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button16);
        boardView[1][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button17);
        boardView[1][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button18);
        boardView[1][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button19);
        boardView[1][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button20);
        boardView[1][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button21);
        boardView[1][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button22);
        boardView[1][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button23);
        boardView[1][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button24);

        boardView[2][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button25);
        boardView[2][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button26);
        boardView[2][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button27);
        boardView[2][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button28);
        boardView[2][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button29);
        boardView[2][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button30);
        boardView[2][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button31);
        boardView[2][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button32);
        boardView[2][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button33);
        boardView[2][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button34);
        boardView[2][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button35);
        boardView[2][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button36);

        boardView[3][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button37);
        boardView[3][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button38);
        boardView[3][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button39);
        boardView[3][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button40);
        boardView[3][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button41);
        boardView[3][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button42);
        boardView[3][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button43);
        boardView[3][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button44);
        boardView[3][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button45);
        boardView[3][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button46);
        boardView[3][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button47);
        boardView[3][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button48);

        boardView[4][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button49);
        boardView[4][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button50);
        boardView[4][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button51);
        boardView[4][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button52);
        boardView[4][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button53);
        boardView[4][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button54);
        boardView[4][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button55);
        boardView[4][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button56);
        boardView[4][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button57);
        boardView[4][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button58);
        boardView[4][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button59);
        boardView[4][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button60);

        boardView[5][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button61);
        boardView[5][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button62);
        boardView[5][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button63);
        boardView[5][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button64);
        boardView[5][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button65);
        boardView[5][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button66);
        boardView[5][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button67);
        boardView[5][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button68);
        boardView[5][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button69);
        boardView[5][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button70);
        boardView[5][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button71);
        boardView[5][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button72);

        boardView[6][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button73);
        boardView[6][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button74);
        boardView[6][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button75);
        boardView[6][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button76);
        boardView[6][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button77);
        boardView[6][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button78);
        boardView[6][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button79);
        boardView[6][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button80);
        boardView[6][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button81);
        boardView[6][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button82);
        boardView[6][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button83);
        boardView[6][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button84);

        boardView[7][0] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button85);
        boardView[7][1] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button86);
        boardView[7][2] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button87);
        boardView[7][3] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button88);
        boardView[7][4] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button89);
        boardView[7][5] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button90);
        boardView[7][6] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button91);
        boardView[7][7] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button92);
        boardView[7][8] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button93);
        boardView[7][9] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button94);
        boardView[7][10] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button95);
        boardView[7][11] = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button96);

        stockView[71] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView1);
        stockView[70] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView2);
        stockView[69] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView3);
        stockView[68] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView4);
        stockView[67] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView5);
        stockView[66] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView6);
        stockView[65] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView7);
        stockView[64] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView8);
        stockView[63] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView9);
        stockView[62] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView10);
        stockView[61] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView11);
        stockView[60] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView12);
        stockView[59] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView13);
        stockView[58] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView14);
        stockView[57] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView15);
        stockView[56] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView16);
        stockView[55] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView17);
        stockView[54] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView18);
        stockView[53] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView19);
        stockView[52] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView20);
        stockView[51] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView21);
        stockView[50] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView22);
        stockView[49] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView23);
        stockView[48] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView24);
        stockView[47] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView25);
        stockView[46] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView26);
        stockView[45] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView27);
        stockView[44] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView28);
        stockView[43] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView29);
        stockView[42] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView30);
        stockView[41] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView31);
        stockView[40] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView32);
        stockView[39] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView33);
        stockView[38] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView34);
        stockView[37] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView35);
        stockView[36] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView36);
        stockView[35] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView37);
        stockView[34] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView38);
        stockView[33] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView39);
        stockView[32] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView40);
        stockView[31] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView41);
        stockView[30] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView42);
        stockView[29] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView43);
        stockView[28] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView44);
        stockView[27] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView45);
        stockView[26] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView46);
        stockView[25] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView47);
        stockView[24] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView48);
        stockView[23] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView49);
        stockView[22] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView50);
        stockView[21] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView51);
        stockView[20] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView52);
        stockView[19] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView53);
        stockView[18] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView54);
        stockView[17] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView55);
        stockView[16] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView56);
        stockView[15] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView57);
        stockView[14] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView58);
        stockView[13] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView59);
        stockView[12] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView60);
        stockView[11] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView61);
        stockView[10] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView62);
        stockView[9] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView63);
        stockView[8] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView64);
        stockView[7] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView65);
        stockView[6] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView66);
        stockView[5] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView67);
        stockView[4] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView68);
        stockView[3] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView69);
        stockView[2] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView70);
        stockView[1] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView71);
        stockView[0] = (TextView)findViewById(edu.ramapo.dmelniko.ishido.R.id.peekView72);

        // Code to programmatically initialize buttons
        // (Theoretically works, but I'm unsure of it being good practice)
        /*int idCount = 0;
        int buttonID = 0x7f0d0050; // the hex identifier of button1
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                buttonID = buttonID + idCount;
                boardView[i][j] = (Button)findViewById(buttonID);
                idCount++;
            }
        }*/

        tilePreviewButton = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button_tile_preview);
        moveHelp = (Button)findViewById(edu.ramapo.dmelniko.ishido.R.id.button_help);

        scoreKeep = (TextView) findViewById(edu.ramapo.dmelniko.ishido.R.id.score_keep);
        scoreKeep.setText("SCORE: ");
        scoreKeepHuman = (TextView) findViewById(edu.ramapo.dmelniko.ishido.R.id.score_keep_human);
        scoreKeepHuman.setText("SCORE: ");
        currentPlayer = (TextView) findViewById(edu.ramapo.dmelniko.ishido.R.id.current_turn);

        // Spinner to select different depth cutoff
        searchSpinner = (Spinner) findViewById(edu.ramapo.dmelniko.ishido.R.id.spinner_cutoff);

        if (isNewGame)
        {
            board = new Board();
            deck = new Deck();
            computer = new Player();
            human = new Player();
            board.currentPlayer = firstPlayer;
        }
        else
        {
            try
            {
                serialFile = readData();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            parseData();
            loadData();
        }

        final List<String> searchList = new ArrayList<>();
        for(int i = 0; i < deck.tileDeck.size(); i++)
        {
            searchList.add(Integer.toString(i));
        }
        // Create ArrayAdapters using the string arrays and a default spinner layout
        ArrayAdapter<String> searchDataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,searchList);

        // Specify the layout to use when the list of choices appears
        searchDataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        searchSpinner.setPrompt("DEPTH CUTOFF");
        searchSpinner.setAdapter(searchDataAdapter);

        currentPlayer.setText(board.currentPlayer);
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(input1));
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
        String delim = "Layout:|Stock:|Human Score:|Computer Score:|Next Player:";
        String[] tokens = serialFile.split(delim);

        boardData = tokens[1];
        deckData = tokens[2];
        humanScoreData = tokens[3];
        computerScoreData = tokens[4];
        nextPlayerData = tokens[5];
    }

    // Initializes the board, deck, and computer objects
    // by calling custom constructors and passing them the appropriate serialized data
    // Accepts no parameters
    // Returns nothing
    public void loadData()
    {
        board = new Board(boardData);
        deck = new Deck(deckData);
        computer = new Player(computerScoreData);
        human = new Player(humanScoreData);
        board.currentPlayer = nextPlayerData;
    }

    // Runs the selected search algorithm
    // Takes the button view as a parameter
    // Returns nothing
    public void aiTurn(View view)
    {
        search.setDepthCutoff(searchSpinner.getSelectedItemPosition());
        // Check if it is the player's turn
        if(board.currentPlayer.equals("HUMAN"))
        {
            Toast.makeText(MainActivity.this, "It is not currently the computer's turn", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(deck.tileDeck.isEmpty() && board.tilePreview.getSymbol().equals(""))
            {
                Toast.makeText(MainActivity.this, "OUT OF TILES", Toast.LENGTH_SHORT).show();
            }
            else
            {
                search.miniMaxWrapper(board, human, computer, deck);
                board.currentPlayer = "HUMAN";
                updateBoardView();
            }
        }
    }

    // Runs the selected search algorithm
    // Takes the button view as a parameter
    // Returns nothing
    public void nextTile(View view)
    {
        String searchType;
        //searchType = searchSpinner.getSelectedItem().toString();
        searchType = "BEST-FIRST";
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
                    search.bestFirstHelp(board, deck, computer);
                    updateBoardView();
                }
                break;
            case "BRANCH&BOUND":
                //search.branchAndBound(board, deck, computer);
                updateBoardView();
                break;
        }
    }

    // Updates all visual elements
    // No parameters are passed
    // Nothing is returned
    public void updateBoardView()
    {
        String scoreMsg;

        tilePreviewButton.setText(board.tilePreview.getSymbol());
        tilePreviewButton.setTextColor(Color.parseColor(board.tilePreview.getColor()));
        tilePreviewButton.setTextSize(42);

        for(int i = 0; i < 72; i++)
        {
            stockView[i].setText("");
        }
        int sviewCount = 71;

        if(!deck.tileDeck.isEmpty())
        {
            for(int iter = deck.tileDeck.size() - 1; iter > 0; iter--)
            {
                stockView[sviewCount].setText(deck.tileDeck.get(iter).getSymbol());
                stockView[sviewCount].setTextColor(Color.parseColor(deck.tileDeck.get(iter).getColor()));
                stockView[sviewCount].setTextSize(42);
                sviewCount--;
            }
        }
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                boardView[i][j].setText(board.tileBoard[i][j].getSymbol());
                boardView[i][j].setTextColor(Color.parseColor(board.tileBoard[i][j].getColor()));
                boardView[i][j].setTextSize(42);
                if(board.tileBoard[i][j].getBlinkable().equals(true))
                {
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(360);
                    animation.setStartOffset(20);
                    animation.setRepeatMode(Animation.REVERSE);
                    animation.setRepeatCount(Animation.INFINITE);
                    boardView[i][j].startAnimation(animation);
                }
            }
        }
        scoreMsg = "COMPUTER SCORE: " + (Integer.toString(computer.getScore()));
        scoreKeep.setText(scoreMsg);
        scoreMsg = "HUMAN SCORE: " + (Integer.toString(human.getScore()));
        scoreKeepHuman.setText(scoreMsg);
        scoreMsg = "CURRENT TURN: " + board.currentPlayer;
        currentPlayer.setText(scoreMsg);
    }

    // Generates a random color and symbol for the tile that is to be placed
    // or accepts the user selections from the drop-down menus
    // A view object is passed, particularly from the Select Tile button
    // Nothing is returned
    // Deprecated for current project state
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
        // Check that the deck is not empty
        if(deck.tileDeck.isEmpty() && board.tilePreview.getSymbol().equals(""))
        {
            Toast.makeText(MainActivity.this, "OUT OF TILES", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Check that it is the player's turn
            if(board.currentPlayer.equals("COMPUTER"))
            {
                Toast.makeText(MainActivity.this, "You have already made your move or it is no longer your turn", Toast.LENGTH_LONG).show();
            }
            else
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 12; j++)
                    {
                        if (view == boardView[i][j])
                        {
                            rowIndex = i;
                            colIndex = j;
                        }
                    }
                }
                //Check move for validity
                if (board.isMoveValid(rowIndex, colIndex))
                {
                    // Place the tile on the board and clear tile selection
                    board.makeMove(rowIndex, colIndex, deck);
                    // Set score for move just made
                    human.setScore(board.calcMovePointVal(rowIndex, colIndex));
                    // Ready next tile and update the board view
                    deck.readyTile(board);
                    board.currentPlayer = "COMPUTER";
                    updateBoardView();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Move is invalid or you are trying to cover an existing tile. Please try again", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

