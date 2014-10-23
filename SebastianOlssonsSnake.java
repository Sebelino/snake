/*

LOG:


AIn aktiveras genom att taga bort "//"-tecknet på rad 45 och 496




*/

import java.applet.*;
import java.awt.*;

public class SebastianOlssonsSnake extends Applet implements Runnable
{
    private Image dbImage;               //Behövs för att förhindra bildblinkning.
    private Graphics dbg;               //Behövs för att förhindra bildblinkning.
    LLista ormPL = new LLista();               //Spelarens orm.
    LLista ormPL2 = new LLista();               //Andra spelarens orm.
    LLista ormAI = new LLista();               //AI:ns orm.
    int amoebaPLX;               //Ormens huvud sträcker ut en "amöbaarm" som kontollerar om det finns något framför ormen.
    int amoebaPLY;               //Ormens huvud sträcker ut en "amöbaarm" som kontollerar om det finns något framför ormen.
    int amoebaPL2X;               //Ormens huvud sträcker ut en "amöbaarm" som kontollerar om det finns något framför ormen.
    int amoebaPL2Y;               //Ormens huvud sträcker ut en "amöbaarm" som kontollerar om det finns något framför ormen.
    int amoebaAIX;               //Ormens huvud sträcker ut en "amöbaarm" som kontollerar om det finns något framför ormen.
    int amoebaAIY;               //Ormens huvud sträcker ut en "amöbaarm" som kontollerar om det finns något framför ormen.
    int ormlangdPL;
    int ormbreddPL = 3;
    int ormlangdPL2;
    int ormbreddPL2 = 3;
    int ormlangdAI = 0;
    int ormbreddAI = 3;
    int appleX = (20+(int)(Math.random()*280));               //Placeras slumpvis på plan.
    int appleY = (20+(int)(Math.random()*280));               //Placeras slumpvis på plan.
    int radieApple = 7;
    int tillvaxt = 20;
    int tillstand = 1;               //1="speltillstånd", 2="forlusttillstånd för PL1", 3="förlusttillstånd för PL2", 4="förlusttillstånd för AI".

    public void init()               //Utför alla nödvändiga startkommandon.
    {
        ormlangdPL = 20;
        ormlangdPL2 = 20;
//        ormlangdAI = 20;
        amoebaPLX = 1;
        amoebaPLY = 0;
        amoebaPL2X = 1;
        amoebaPL2Y = 0;
        amoebaAIX = ormAI.peekX(0);
        amoebaAIY = ormAI.peekY(0);
        for(int i = 0;i < ormlangdPL;i++)
        {
            ormPL.push(200,
                       100,
                       0);
        }
        for(int i = 0;i < ormlangdPL2;i++)
        {
            ormPL2.push(100,
                        50,
                        0);
        }
        for(int i = 0;i < ormlangdAI;i++)
        {
            ormAI.push(200,
                       200,
                       0);
        }
        setBackground(Color.blue);
    }

    public void start()               //Startar tråden.
    {
        Thread th = new Thread(this);
        th.start();
    }

    public void stop(){}

    public void destroy(){}

    public boolean keyDown(Event e,               //Knapptryckningar
                           int key)
    {
        if(key == Event.LEFT)
        {
            if(amoebaPLX != 1)               //Förhindrar en 180graders sväng.
            {
                amoebaPLX = -1;
                amoebaPLY = 0;
            }
        }
        else if(key == Event.RIGHT)
        {
            if(amoebaPLX != -1)               //Förhindrar en 180graders sväng.
            {
                amoebaPLX = 1;
                amoebaPLY = 0;
            }
        }
        else if(key == Event.UP)
        {
            if(amoebaPLY != 1)               //Förhindrar en 180graders sväng.
            {
                amoebaPLX = 0;
                amoebaPLY = -1;
            }
        }
        else if(key == Event.DOWN)
        {
            if(amoebaPLY != -1)               //Förhindrar en 180graders sväng.
            {
                amoebaPLX = 0;
                amoebaPLY = 1;
            }
        }
        else if(key == 65 ||
                key == 97)
        {
            if(amoebaPL2X != 1)               //Förhindrar en 180graders sväng.
            {
                amoebaPL2X = -1;
                amoebaPL2Y = 0;
            }
        }
        else if(key == 68 ||
                key == 100)
        {
            if(amoebaPL2X != -1)               //Förhindrar en 180graders sväng.
            {
                amoebaPL2X = 1;
                amoebaPL2Y = 0;
            }
        }
        else if(key == 87 ||
                key == 119)
        {
            if(amoebaPL2Y != 1)               //Förhindrar en 180graders sväng.
            {
                amoebaPL2X = 0;
                amoebaPL2Y = -1;
            }
        }
        else if(key == 83 ||
                key == 115)
        {
            if(amoebaPL2Y != -1)               //Förhindrar en 180graders sväng.
            {
                amoebaPL2X = 0;
                amoebaPL2Y = 1;
            }
        }
        return true;
    }

    public void spelare()
    {
        for(int i = (ormlangdPL-2);i >= 0;i--)               //Sektionerna bakom ormhuvudet flyttas ett steg framåt.
        {
            ormPL.push(ormPL.peekX(i),
                       ormPL.peekY(i),
                       (i+1));
            ormPL.radera(i+2);
        }
        ormPL.push((ormPL.peekX(0)+amoebaPLX),               //Flyttar ormhuvudet ett steg framåt.
                   (ormPL.peekY(0)+amoebaPLY),
                   0);
        ormPL.radera(1);
        for(int i = 1;i < ormlangdPL;i++)
        {
            if(ormPL.peekX(0) == ormPL.peekX(i) &&
               ormPL.peekY(0) == ormPL.peekY(i))
            {
                ormPL.klipp(i);               //"Klipper av" svansen på ormen från stället där den "biter".
                ormlangdPL = ormPL.listlangd();
            }
        }
        if(((int)(ormPL.peekX(0)-ormbreddPL*0.5)) <= (appleX+radieApple) &&               //Förlänger ormen om den äter ett äpple.
           ((int)(ormPL.peekX(0)+ormbreddPL*0.5)) >= (appleX-radieApple) &&
           ((int)(ormPL.peekY(0)-ormbreddPL*0.5)) <= (appleY+radieApple) &&
           ((int)(ormPL.peekY(0)+ormbreddPL*0.5)) >= (appleY-radieApple))
        {
            for(int i = 0;i < tillvaxt;i++)
            {
                ormPL.push(ormPL.peekX(ormlangdPL-1),
                           ormPL.peekY(ormlangdPL-1),
                           ormlangdPL);
                ormlangdPL++;
            }
            appleX = (20+(int)(Math.random()*280));               //Äpplet byter plats efter slump.
            appleY = (20+(int)(Math.random()*280));               //Äpplet byter plats efter slump.
        }
        if(ormPL.peekX(0) > 320)
        {
            ormPL.push(0,               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                       ormPL.peekY(0),
                       0);
            ormPL.radera(1);
        }
        else if(ormPL.peekX(0) < 0)
        {
            ormPL.push(320,               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                       ormPL.peekY(0),
                       0);
            ormPL.radera(1);
        }
        else if(ormPL.peekY(0) > 320)
        {
            ormPL.push(ormPL.peekX(0),               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                       0,
                       0);
            ormPL.radera(1);
        }
        else if(ormPL.peekY(0) < 0)
        {
            ormPL.push(ormPL.peekX(0),               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                       320,
                       0);
            ormPL.radera(1);
        }
        for(int i = 0;i < ormlangdAI;i++)
        {
            if(ormPL.peekX(0) == ormAI.peekX(i) &&
               ormPL.peekY(0) == ormAI.peekY(i))
            {
                forlustPL1();               //Spelare1 förlorar om ormhuvudet vidrör AI's orm.
            }
        }
        for(int i = 0;i < ormlangdPL2;i++)
        {
            if(ormPL.peekX(0) == ormPL2.peekX(i) &&
               ormPL.peekY(0) == ormPL2.peekY(i))
            {
                forlustPL1();               //Spelare1 förlorar om ormhuvudet vidrör PL2's orm.
            }
        }
    }

    public void spelare2()
    {
        for(int i = (ormlangdPL2-2);i >= 0;i--)               //Sektionerna bakom ormhuvudet flyttas ett steg framåt.
        {
            ormPL2.push(ormPL2.peekX(i),
                        ormPL2.peekY(i),
                        (i+1));
            ormPL2.radera(i+2);
        }
        ormPL2.push((ormPL2.peekX(0)+amoebaPL2X),               //Flyttar ormhuvudet ett steg framåt.
                    (ormPL2.peekY(0)+amoebaPL2Y),
                    0);
        ormPL2.radera(1);
        for(int i = 1;i < ormlangdPL2;i++)
        {
            if(ormPL2.peekX(0) == ormPL2.peekX(i) &&
               ormPL2.peekY(0) == ormPL2.peekY(i))
            {
                ormPL2.klipp(i);               //"Klipper av" svansen på ormen från stället där den "biter".
                ormlangdPL2 = ormPL2.listlangd();
            }
        }
        if(((int)(ormPL2.peekX(0)-ormbreddPL2*0.5)) <= (appleX+radieApple) &&               //Förlänger ormen om den äter ett äpple.
           ((int)(ormPL2.peekX(0)+ormbreddPL2*0.5)) >= (appleX-radieApple) &&
           ((int)(ormPL2.peekY(0)-ormbreddPL2*0.5)) <= (appleY+radieApple) &&
           ((int)(ormPL2.peekY(0)+ormbreddPL2*0.5)) >= (appleY-radieApple))
        {
            for(int i = 0;i < tillvaxt;i++)
            {
                ormPL2.push(ormPL2.peekX(ormlangdPL2-1),
                            ormPL2.peekY(ormlangdPL2-1),
                            ormlangdPL2);
                ormlangdPL2++;
            }
            appleX = (20+(int)(Math.random()*280));               //Äpplet byter plats efter slump.
            appleY = (20+(int)(Math.random()*280));               //Äpplet byter plats efter slump.
        }
        if(ormPL2.peekX(0) > 320)
        {
            ormPL2.push(0,               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                        ormPL2.peekY(0),
                        0);
            ormPL2.radera(1);
        }
        else if(ormPL2.peekX(0) < 0)
        {
            ormPL2.push(320,               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                        ormPL2.peekY(0),
                        0);
            ormPL2.radera(1);
        }
        else if(ormPL2.peekY(0) > 320)
        {
            ormPL2.push(ormPL2.peekX(0),               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                        0,
                        0);
            ormPL2.radera(1);
        }
        else if(ormPL2.peekY(0) < 0)
        {
            ormPL2.push(ormPL2.peekX(0),               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
                        320,
                        0);
            ormPL2.radera(1);
        }
        for(int i = 0;i < ormlangdPL;i++)
        {
            if(ormPL2.peekX(0) == ormPL.peekX(i) &&
               ormPL2.peekY(0) == ormPL.peekY(i))
            {
                forlustPL2();               //Spelare2 förlorar om ormhuvudet vidrör PL's orm.
            }
        }
        for(int i = 0;i < ormlangdAI;i++)
        {
            if(ormPL2.peekX(0) == ormAI.peekX(i) &&
               ormPL2.peekY(0) == ormAI.peekY(i))
            {
                forlustPL2();               //Spelare2 förlorar om ormhuvudet vidrör AI's orm.
            }
        }
    }

    public void AI()
    {
        amoebaAIX = ormAI.peekX(0);
        amoebaAIY = ormAI.peekY(0);
        if(appleX < ormAI.peekX(0))               //Strävar efter att nå äpplet.
        {
            amoebaAIX = -1;
            amoebaAIY = 0;
        }
        else if(appleX > ormAI.peekX(0))               //Strävar efter att nå äpplet.
        {
            amoebaAIX = 1;
            amoebaAIY = 0;
        }
        else if(appleY < ormAI.peekY(0))               //Strävar efter att nå äpplet.
        {
            amoebaAIX = 0;
            amoebaAIY = -1;
        }
        else if(appleY > ormAI.peekY(0))               //Strävar efter att nå äpplet.
        {
            amoebaAIX = 0;
            amoebaAIY = 1;
        }
        for(int i = 1;i < ormlangdAI;i++)               //Förhindrar till viss del ormen att styra in i sig själv.
        {
            if((ormAI.peekX(0)+amoebaAIX) == ormAI.peekX(i) &&
               (ormAI.peekY(0)+amoebaAIY) == ormAI.peekY(i))
            {
                if(amoebaAIX == 0 &&
                   amoebaAIY == -1)
                {
                    amoebaAIX = 1;
                    amoebaAIY = 0;
                }
                else if(amoebaAIX == 1 &&
                        amoebaAIY == 0)
                {
                    amoebaAIX = 0;
                    amoebaAIY = 1;
                }
                else if(amoebaAIX == 0 &&
                        amoebaAIY == 1)
                {
                    amoebaAIX = -1;
                    amoebaAIY = 0;
                }
                else if(amoebaAIX == -1 &&
                        amoebaAIY == 0)
                {
                    amoebaAIX = 0;
                    amoebaAIY = -1;
                }
            }
        }
        for(int i = 0;i < ormlangdPL;i++)               //Förhindrar till viss del ormen att styra in i spelarormen.
        {
            if((ormAI.peekX(0)+amoebaAIX) == ormPL.peekX(i) &&
               (ormAI.peekY(0)+amoebaAIY) == ormPL.peekY(i))
            {
                if(amoebaAIX == 0 &&
                   amoebaAIY == -1)
                {
                    amoebaAIX = 1;
                    amoebaAIY = 0;
                }
                else if(amoebaAIX == 1 &&
                        amoebaAIY == 0)
                {
                    amoebaAIX = 0;
                    amoebaAIY = 1;
                }
                else if(amoebaAIX == 0 &&
                        amoebaAIY == 1)
                {
                    amoebaAIX = -1;
                    amoebaAIY = 0;
                }
                else if(amoebaAIX == -1 &&
                        amoebaAIY == 0)
                {
                    amoebaAIX = 0;
                    amoebaAIY = -1;
                }
            }
        }
        for(int i = (ormlangdAI-2);i >= 0;i--)               //Sektionerna bakom ormhuvudet flyttas ett steg framåt.
        {
            ormAI.push(ormAI.peekX(i),
                       ormAI.peekY(i),
                       (i+1));
            ormAI.radera(i+2);
        }
        ormAI.push((ormAI.peekX(0)+amoebaAIX),               //Flyttar ormhuvudet ett steg framåt.
                   (ormAI.peekY(0)+amoebaAIY),
                   0);
        ormAI.radera(1);
        for(int i = 1;i < ormlangdAI;i++)               //"Klipper av" svansen på ormen från stället där den "biter".
        {
            if(ormAI.peekX(0) == ormAI.peekX(i) &&
               ormAI.peekY(0) == ormAI.peekY(i))
            {
                ormAI.klipp(i);
                ormlangdAI = ormAI.listlangd();
            }
        }
        if(((int)(ormAI.peekX(0)-ormbreddAI*0.5)) <= (appleX+radieApple) &&               //Förlänger ormen om den äter ett äpple.
           ((int)(ormAI.peekX(0)+ormbreddAI*0.5)) >= (appleX-radieApple) &&
           ((int)(ormAI.peekY(0)-ormbreddAI*0.5)) <= (appleY+radieApple) &&
           ((int)(ormAI.peekY(0)+ormbreddAI*0.5)) >= (appleY-radieApple))
        {
            for(int i = 0;i < tillvaxt;i++)
            {
                ormAI.push(ormAI.peekX(ormlangdAI-1),
                           ormAI.peekY(ormlangdAI-1),
                           ormlangdAI);
                ormlangdAI++;
            }
            appleX = (20+(int)(Math.random()*280));               //Äpplet byter plats efter slump.
            appleY = (20+(int)(Math.random()*280));               //Äpplet byter plats efter slump.
        }
        if(ormAI.peekX(0) > 320)               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
        {
            ormAI.push(0,
                       ormAI.peekY(0),
                       0);
            ormAI.radera(1);
        }
        else if(ormAI.peekX(0) < 0)               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
        {
            ormAI.push(320,
                       ormAI.peekY(0),
                       0);
            ormAI.radera(1);
        }
        else if(ormAI.peekY(0) > 320)               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
        {
            ormAI.push(ormAI.peekX(0),
                       0,
                       0);
            ormAI.radera(1);
        }
        else if(ormAI.peekY(0) < 0)               //Ifall ormen lämnar spelplanen dyker den upp på motsatt kant.
        {
            ormAI.push(ormAI.peekX(0),
                       320,
                       0);
            ormAI.radera(1);
        }
        for(int i = 0;i < ormlangdPL;i++)
        {
            if(ormAI.peekX(0) == ormPL.peekX(i) &&
               ormAI.peekY(0) == ormPL.peekY(i))
            {
                forlustAI();               //AI förlorar om ormhuvudet vidrör PL's orm.
            }
        }
        for(int i = 0;i < ormlangdPL2;i++)
        {
            if(ormAI.peekX(0) == ormPL2.peekX(i) &&
               ormAI.peekY(0) == ormPL2.peekY(i))
            {
                forlustAI();               //AI förlorar om ormhuvudet vidrör PL2's orm.
            }
        }
    }

    public void run()
    {
        while(true)
        {
            spelare();
            spelare2();
//            AI();
            repaint();
            try
            {
                Thread.sleep(10);               //Programmet väntar i en hundradels sekund.
            }
            catch(InterruptedException ex){}
        }
    }

    public void forlustPL1()
    {
        tillstand = 2;
        repaint();
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex){}
        for(int i = 0;i < ormlangdPL;i++)               //Raderar ormarna fullständigt.
        {
            ormPL.radera(0);
        }
        for(int i = 0;i < ormlangdPL2;i++)               //Raderar ormarna fullständigt.
        {
            ormPL2.radera(0);
        }
        for(int i = 0;i < ormlangdAI;i++)               //Raderar ormarna fullständigt.
        {
            ormAI.radera(0);
        }
        tillstand = 1;
        init();
    }

    public void forlustPL2()
    {
        tillstand = 3;
        repaint();
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex){}
        for(int i = 0;i < ormlangdPL;i++)               //Raderar ormarna fullständigt.
        {
            ormPL.radera(0);
        }
        for(int i = 0;i < ormlangdPL2;i++)               //Raderar ormarna fullständigt.
        {
            ormPL2.radera(0);
        }
        for(int i = 0;i < ormlangdAI;i++)               //Raderar ormarna fullständigt.
        {
            ormAI.radera(0);
        }
        tillstand = 1;
        init();
    }

    public void forlustAI()
    {
        tillstand = 4;
        repaint();
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex){}
        for(int i = 0;i < ormlangdPL;i++)               //Raderar ormarna fullständigt.
        {
            ormPL.radera(0);
        }
        for(int i = 0;i < ormlangdPL2;i++)               //Raderar ormarna fullständigt.
        {
            ormPL2.radera(0);
        }
        for(int i = 0;i < ormlangdAI;i++)               //Raderar ormarna fullständigt.
        {
            ormAI.radera(0);
        }
        tillstand = 1;
        init();
          
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.orange);
        for(int i = 0;i < ormlangdPL;i++)
        {
            g.fillRect(((int)(ormPL.peekX(i)-ormbreddPL*0.5)),
                       ((int)(ormPL.peekY(i)-ormbreddPL*0.5)),
                       ormbreddPL,ormbreddPL);
        }
        g.setColor(Color.black);
        for(int i = 0;i < ormlangdPL2;i++)
        {
            g.fillRect(((int)(ormPL2.peekX(i)-ormbreddPL2*0.5)),
                       ((int)(ormPL2.peekY(i)-ormbreddPL2*0.5)),
                       ormbreddPL2,ormbreddPL2);
        }
        g.setColor(Color.cyan);
        for(int i = 0;i < ormlangdAI;i++)
        {
            g.fillRect(((int)(ormAI.peekX(i)-ormbreddAI*0.5)),
                       ((int)(ormAI.peekY(i)-ormbreddAI*0.5)),
                       ormbreddAI,ormbreddAI);
        }
        g.setColor(Color.red);
        g.fillOval((appleX-radieApple),
                   (appleY-radieApple),
                   (2*radieApple),
                   (2*radieApple));
        if(tillstand == 2)
        {
            g.drawString("SPELARE 1 FÖRLORADE!",20,20);
            g.drawString("SPELARE 1 FÖRLORADE!",250,20);
            g.drawString("SPELARE 1 FÖRLORADE!",20,280);
            g.drawString("SPELARE 1 FÖRLORADE!",250,280);
        }
        else if(tillstand == 3)
        {
            g.drawString("SPELARE 2 FÖRLORADE!",20,20);
            g.drawString("SPELARE 2 FÖRLORADE!",210,20);
            g.drawString("SPELARE 2 FÖRLORADE!",20,280);
            g.drawString("SPELARE 2 FÖRLORADE!",210,280);
        }
        else if(tillstand == 4)
        {
            g.drawString("DATORN FÖRLORADE!",20,20);
            g.drawString("DATORN FÖRLORADE!",210,20);
            g.drawString("DATORN FÖRLORADE!",20,280);
            g.drawString("DATORN FÖRLORADE!",210,280);
        }
    }
    public void update(Graphics g)                      //Behövs för att förhindra bildblinkning.
    {
        if(dbImage == null)
        {
            dbImage = createImage(this.getSize().width,
                                  this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        dbg.setColor(getBackground());
        dbg.fillRect(0,
                     0,
                     this.getSize().width,
                     this.getSize().height);
        dbg.setColor(getForeground());
        paint(dbg);
        g.drawImage(dbImage,
                    0,
                    0,
                    this);
    }
}