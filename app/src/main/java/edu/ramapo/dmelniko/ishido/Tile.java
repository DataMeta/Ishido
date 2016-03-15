package edu.ramapo.dmelniko.ishido;

public class Tile
{
    private String symbol = "";
    private String color = "green";
    private Boolean blinkable = false;

    public Tile ()
    {
        symbol = "";
        color = "green";
    }

    public void setColor(String color)
    {
        this.color = color;
    }
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    public void setBlinkable(Boolean blinkable)
    {
        this.blinkable = blinkable;
    }
    public String getColor()
    {
        return color;
    }
    public String getSymbol()
    {
        return symbol;
    }
    public Boolean getBlinkable()
    {
        return this.blinkable;
    }


}
