public class LLista
{
    private Element forsta;

    public void push(int x,int y,int plats)
    {
        if(forsta == null)
        {
            forsta = new Element(x,y);
        }
        else if(plats == 0)
        {
            Element e = new Element(x,y);
            e.nasta = forsta;
            forsta = e;
        }
        else
        {
            Element e = new Element(x,y);
            Element e1 = forsta;
            Element e2 = forsta.nasta;
            for(int i = 1;i < plats;i++)
            {
                if(e2 == null)
                {
                    break;
                }
                e1 = e1.nasta;
                e2 = e2.nasta;
            }
            e1.nasta = e;
            e.nasta = e2;
        }
    }
    public void radera(int plats)
    {
        Element e = forsta;
        if(forsta == null){}
        else if(plats == 0)
        {
            forsta = e.nasta;
        }
        else
        {
            for(int i = 1;i < plats;i++)
            {
                e = e.nasta;
            }
            e.nasta = e.nasta.nasta;
        }
    }
    public void klipp(int plats)
    {
        Element e = forsta;
        if(forsta == null){}
        else if(plats == 0)
        {
            forsta = e.nasta;
        }
        else
        {
            for(int i = 1;i < plats;i++)
            {
                e = e.nasta;
            }
            while(e.nasta != null)
            {
                e.nasta = e.nasta.nasta;
            }
        }
    }
    public int listlangd()
    {
        int langd = 0;
        Element e = forsta;
        while(e != null)
        {
            e = e.nasta;
            langd++;
        }
        return langd;
    }
    public int peekX(int plats)
    {
        if(forsta == null)
        {
            return -1;
        }
        else
        {
            Element e = forsta;
            while(e.nasta != null &&
                  plats > 0)
            {
                e = e.nasta;
                plats--;
            }
            return e.xPos;
        }
    }
    public int peekY(int plats)
    {
        if(forsta == null)
        {
            return -1;
        }
        else
        {
            Element e = forsta;
            while(e.nasta != null &&
                  plats > 0)
            {
                e = e.nasta;
                plats--;
            }
            return e.yPos;
        }
    }
}
