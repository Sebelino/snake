/*

LOG:


AIn aktiveras genom att taga bort "//"-tecknet p� rad 45 och 496




*/

import java.applet.*;
import java.awt.*;

public class SebastianOlssonsSnake extends Applet implements Runnable
{
    private Image dbImage;               //Beh�vs f�r att f�rhindra bildblinkning.
    private Graphics dbg;               //Beh�vs f�r att f�rhindra bildblinkning.
    LLista ormPL = new LLista();               //Spelarens orm.
    LLista ormPL2 = new LLista();               //Andra spelarens orm.
    LLista ormAI = new LLista();               //AI:ns orm.
    int amoebaPLX;               //Ormens huvud str�cker ut en "am�baarm" som kontollerar om det finns n�got framf�r ormen.
    int amoebaPLY;               //Ormens huvud str�cker ut en "am�baarm" som kontollerar om det finns n�got framf�r ormen.
    int amoebaPL2X;               //Ormens huvud str�cker ut en "am�baarm" som kontollerar om det finns n�got framf�r ormen.
    int amoebaPL2Y;               //Ormens huvud str�cker ut en "am�baarm" som kontollerar om det finns n�got framf�r ormen.
    int amoebaAIX;               //Ormens huvud str�cker ut en "am�baarm" som kontollerar om det finns n�got framf�r ormen.
    int amoebaAIY;               //Ormens huvud str�cker ut en "am�baarm" som kontollerar om det finns n�got framf�r ormen.
    int ormlangdPL;
    int ormbreddPL = 3;
    int ormlangdPL2;
    int ormbreddPL2 = 3;
    int ormlangdAI = 0;
    int ormbreddAI = 3;
    int appleX = (20+(int)(Math.random()*280));               //Placeras slumpvis p� plan.
    int appleY = (20+(int)(Math.random()*280));               //Placeras slumpvis p� plan.
    int radieApple = 7;
    int tillvaxt = 20;
    int tillstand = 1;               //1="speltillst�nd", 2="forlusttillst�nd f�r PL1", 3="f�rlusttillst�nd f�r PL2", 4="f�rlusttillst�nd f�r AI".

    public void init()               //Utf�r alla n�dv�ndiga startkommandon.
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

    public void start()               //Startar tr�den.
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
            if(amoebaPLX != 1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPLX = -1;
                amoebaPLY = 0;
            }
        }
        else if(key == Event.RIGHT)
        {
            if(amoebaPLX != -1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPLX = 1;
                amoebaPLY = 0;
            }
        }
        else if(key == Event.UP)
        {
            if(amoebaPLY != 1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPLX = 0;
                amoebaPLY = -1;
            }
        }
        else if(key == Event.DOWN)
        {
            if(amoebaPLY != -1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPLX = 0;
                amoebaPLY = 1;
            }
        }
        else if(key == 65 ||
                key == 97)
        {
            if(amoebaPL2X != 1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPL2X = -1;
                amoebaPL2Y = 0;
            }
        }
        else if(key == 68 ||
                key == 100)
        {
            if(amoebaPL2X != -1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPL2X = 1;
                amoebaPL2Y = 0;
            }
        }
        else if(key == 87 ||
                key == 119)
        {
            if(amoebaPL2Y != 1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPL2X = 0;
                amoebaPL2Y = -1;
            }
        }
        else if(key == 83 ||
                key == 115)
        {
            if(amoebaPL2Y != -1)               //F�rhindrar en 180graders sv�ng.
            {
                amoebaPL2X = 0;
                amoebaPL2Y = 1;
            }
        }
        return true;
    }

    public void spelare()
    {
        for(int i = (ormlangdPL-2);i >= 0;i--)               //Sektionerna bakom ormhuvudet flyttas ett steg fram�t.
        {
            ormPL.push(ormPL.peekX(i),
                       ormPL.peekY(i),
                       (i+1));
            ormPL.radera(i+2);
        }
        ormPL.push((ormPL.peekX(0)+amoebaPLX),               //Flyttar ormhuvudet ett steg fram�t.
                   (ormPL.peekY(0)+amoebaPLY),
                   0);
        ormPL.radera(1);
        for(int i = 1;i < ormlangdPL;i++)
        {
            if(ormPL.peekX(0) == ormPL.peekX(i) &&
               ormPL.peekY(0) == ormPL.peekY(i))
            {
                ormPL.klipp(i);               //"Klipper av" svansen p� ormen fr�n st�llet d�r den "biter".
                ormlangdPL = ormPL.listlangd();
            }
        }
        if(((int)(ormPL.peekX(0)-ormbreddPL*0.5)) <= (appleX+radieApple) &&               //F�rl�nger ormen om den �ter ett �pple.
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
            appleX = (20+(int)(Math.random()*280));               //�pplet byter plats efter slump.
            appleY = (20+(int)(Math.random()*280));               //�pplet byter plats efter slump.
        }
        if(ormPL.peekX(0) > 320)
        {
            ormPL.push(0,               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                       ormPL.peekY(0),
                       0);
            ormPL.radera(1);
        }
        else if(ormPL.peekX(0) < 0)
        {
            ormPL.push(320,               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                       ormPL.peekY(0),
                       0);
            ormPL.radera(1);
        }
        else if(ormPL.peekY(0) > 320)
        {
            ormPL.push(ormPL.peekX(0),               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                       0,
                       0);
            ormPL.radera(1);
        }
        else if(ormPL.peekY(0) < 0)
        {
            ormPL.push(ormPL.peekX(0),               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                       320,
                       0);
            ormPL.radera(1);
        }
        for(int i = 0;i < ormlangdAI;i++)
        {
            if(ormPL.peekX(0) == ormAI.peekX(i) &&
               ormPL.peekY(0) == ormAI.peekY(i))
            {
                forlustPL1();               //Spelare1 f�rlorar om ormhuvudet vidr�r AI's orm.
            }
        }
        for(int i = 0;i < ormlangdPL2;i++)
        {
            if(ormPL.peekX(0) == ormPL2.peekX(i) &&
               ormPL.peekY(0) == ormPL2.peekY(i))
            {
                forlustPL1();               //Spelare1 f�rlorar om ormhuvudet vidr�r PL2's orm.
            }
        }
    }

    public void spelare2()
    {
        for(int i = (ormlangdPL2-2);i >= 0;i--)               //Sektionerna bakom ormhuvudet flyttas ett steg fram�t.
        {
            ormPL2.push(ormPL2.peekX(i),
                        ormPL2.peekY(i),
                        (i+1));
            ormPL2.radera(i+2);
        }
        ormPL2.push((ormPL2.peekX(0)+amoebaPL2X),               //Flyttar ormhuvudet ett steg fram�t.
                    (ormPL2.peekY(0)+amoebaPL2Y),
                    0);
        ormPL2.radera(1);
        for(int i = 1;i < ormlangdPL2;i++)
        {
            if(ormPL2.peekX(0) == ormPL2.peekX(i) &&
               ormPL2.peekY(0) == ormPL2.peekY(i))
            {
                ormPL2.klipp(i);               //"Klipper av" svansen p� ormen fr�n st�llet d�r den "biter".
                ormlangdPL2 = ormPL2.listlangd();
            }
        }
        if(((int)(ormPL2.peekX(0)-ormbreddPL2*0.5)) <= (appleX+radieApple) &&               //F�rl�nger ormen om den �ter ett �pple.
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
            appleX = (20+(int)(Math.random()*280));               //�pplet byter plats efter slump.
            appleY = (20+(int)(Math.random()*280));               //�pplet byter plats efter slump.
        }
        if(ormPL2.peekX(0) > 320)
        {
            ormPL2.push(0,               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                        ormPL2.peekY(0),
                        0);
            ormPL2.radera(1);
        }
        else if(ormPL2.peekX(0) < 0)
        {
            ormPL2.push(320,               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                        ormPL2.peekY(0),
                        0);
            ormPL2.radera(1);
        }
        else if(ormPL2.peekY(0) > 320)
        {
            ormPL2.push(ormPL2.peekX(0),               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                        0,
                        0);
            ormPL2.radera(1);
        }
        else if(ormPL2.peekY(0) < 0)
        {
            ormPL2.push(ormPL2.peekX(0),               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
                        320,
                        0);
            ormPL2.radera(1);
        }
        for(int i = 0;i < ormlangdPL;i++)
        {
            if(ormPL2.peekX(0) == ormPL.peekX(i) &&
               ormPL2.peekY(0) == ormPL.peekY(i))
            {
                forlustPL2();               //Spelare2 f�rlorar om ormhuvudet vidr�r PL's orm.
            }
        }
        for(int i = 0;i < ormlangdAI;i++)
        {
            if(ormPL2.peekX(0) == ormAI.peekX(i) &&
               ormPL2.peekY(0) == ormAI.peekY(i))
            {
                forlustPL2();               //Spelare2 f�rlorar om ormhuvudet vidr�r AI's orm.
            }
        }
    }

    public void AI()
    {
        amoebaAIX = ormAI.peekX(0);
        amoebaAIY = ormAI.peekY(0);
        if(appleX < ormAI.peekX(0))               //Str�var efter att n� �pplet.
        {
            amoebaAIX = -1;
            amoebaAIY = 0;
        }
        else if(appleX > ormAI.peekX(0))               //Str�var efter att n� �pplet.
        {
            amoebaAIX = 1;
            amoebaAIY = 0;
        }
        else if(appleY < ormAI.peekY(0))               //Str�var efter att n� �pplet.
        {
            amoebaAIX = 0;
            amoebaAIY = -1;
        }
        else if(appleY > ormAI.peekY(0))               //Str�var efter att n� �pplet.
        {
            amoebaAIX = 0;
            amoebaAIY = 1;
        }
        for(int i = 1;i < ormlangdAI;i++)               //F�rhindrar till viss del ormen att styra in i sig sj�lv.
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
        for(int i = 0;i < ormlangdPL;i++)               //F�rhindrar till viss del ormen att styra in i spelarormen.
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
        for(int i = (ormlangdAI-2);i >= 0;i--)               //Sektionerna bakom ormhuvudet flyttas ett steg fram�t.
        {
            ormAI.push(ormAI.peekX(i),
                       ormAI.peekY(i),
                       (i+1));
            ormAI.radera(i+2);
        }
        ormAI.push((ormAI.peekX(0)+amoebaAIX),               //Flyttar ormhuvudet ett steg fram�t.
                   (ormAI.peekY(0)+amoebaAIY),
                   0);
        ormAI.radera(1);
        for(int i = 1;i < ormlangdAI;i++)               //"Klipper av" svansen p� ormen fr�n st�llet d�r den "biter".
        {
            if(ormAI.peekX(0) == ormAI.peekX(i) &&
               ormAI.peekY(0) == ormAI.peekY(i))
            {
                ormAI.klipp(i);
                ormlangdAI = ormAI.listlangd();
            }
        }
        if(((int)(ormAI.peekX(0)-ormbreddAI*0.5)) <= (appleX+radieApple) &&               //F�rl�nger ormen om den �ter ett �pple.
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
            appleX = (20+(int)(Math.random()*280));               //�pplet byter plats efter slump.
            appleY = (20+(int)(Math.random()*280));               //�pplet byter plats efter slump.
        }
        if(ormAI.peekX(0) > 320)               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
        {
            ormAI.push(0,
                       ormAI.peekY(0),
                       0);
            ormAI.radera(1);
        }
        else if(ormAI.peekX(0) < 0)               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
        {
            ormAI.push(320,
                       ormAI.peekY(0),
                       0);
            ormAI.radera(1);
        }
        else if(ormAI.peekY(0) > 320)               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
        {
            ormAI.push(ormAI.peekX(0),
                       0,
                       0);
            ormAI.radera(1);
        }
        else if(ormAI.peekY(0) < 0)               //Ifall ormen l�mnar spelplanen dyker den upp p� motsatt kant.
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
                forlustAI();               //AI f�rlorar om ormhuvudet vidr�r PL's orm.
            }
        }
        for(int i = 0;i < ormlangdPL2;i++)
        {
            if(ormAI.peekX(0) == ormPL2.peekX(i) &&
               ormAI.peekY(0) == ormPL2.peekY(i))
            {
                forlustAI();               //AI f�rlorar om ormhuvudet vidr�r PL2's orm.
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
                Thread.sleep(10);               //Programmet v�ntar i en hundradels sekund.
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
        for(int i = 0;i < ormlangdPL;i++)               //Raderar ormarna fullst�ndigt.
        {
            ormPL.radera(0);
        }
        for(int i = 0;i < ormlangdPL2;i++)               //Raderar ormarna fullst�ndigt.
        {
            ormPL2.radera(0);
        }
        for(int i = 0;i < ormlangdAI;i++)               //Raderar ormarna fullst�ndigt.
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
        for(int i = 0;i < ormlangdPL;i++)               //Raderar ormarna fullst�ndigt.
        {
            ormPL.radera(0);
        }
        for(int i = 0;i < ormlangdPL2;i++)               //Raderar ormarna fullst�ndigt.
        {
            ormPL2.radera(0);
        }
        for(int i = 0;i < ormlangdAI;i++)               //Raderar ormarna fullst�ndigt.
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
        for(int i = 0;i < ormlangdPL;i++)               //Raderar ormarna fullst�ndigt.
        {
            ormPL.radera(0);
        }
        for(int i = 0;i < ormlangdPL2;i++)               //Raderar ormarna fullst�ndigt.
        {
            ormPL2.radera(0);
        }
        for(int i = 0;i < ormlangdAI;i++)               //Raderar ormarna fullst�ndigt.
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
            g.drawString("SPELARE 1 F�RLORADE!",20,20);
            g.drawString("SPELARE 1 F�RLORADE!",250,20);
            g.drawString("SPELARE 1 F�RLORADE!",20,280);
            g.drawString("SPELARE 1 F�RLORADE!",250,280);
        }
        else if(tillstand == 3)
        {
            g.drawString("SPELARE 2 F�RLORADE!",20,20);
            g.drawString("SPELARE 2 F�RLORADE!",210,20);
            g.drawString("SPELARE 2 F�RLORADE!",20,280);
            g.drawString("SPELARE 2 F�RLORADE!",210,280);
        }
        else if(tillstand == 4)
        {
            g.drawString("DATORN F�RLORADE!",20,20);
            g.drawString("DATORN F�RLORADE!",210,20);
            g.drawString("DATORN F�RLORADE!",20,280);
            g.drawString("DATORN F�RLORADE!",210,280);
        }
    }
    public void update(Graphics g)                      //Beh�vs f�r att f�rhindra bildblinkning.
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