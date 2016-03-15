package edu.ramapo.dmelniko.ishido;

public class Move
{
    private int rowIndex;
    private int colIndex;
    private int score;

    Move()
    {

    }

    Move (int rowIndex, int colIndex)
    {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    Move (int rowIndex, int colIndex, int score)
    {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.score = score;
    }

    public int getRowIndex()
    {
        return this.rowIndex;
    }
    public void setRowIndex(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }
    public int getColIndex()
    {
        return this.colIndex;
    }
    public void setColIndex(int colIndex)
    {
        this.colIndex = colIndex;
    }
    public int getScore()
    {
       return this.score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }

}
