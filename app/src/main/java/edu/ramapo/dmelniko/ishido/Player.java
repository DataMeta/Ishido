package edu.ramapo.dmelniko.ishido;

public class Player
{
    int score;
    public Player()
    {
        score = 0;
    }

    public Player(String scoreData)
    {
        score = Integer.parseInt(scoreData);
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score += score;
    }
}
