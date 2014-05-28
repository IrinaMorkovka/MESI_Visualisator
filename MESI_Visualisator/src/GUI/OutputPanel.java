/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Interfaces.MESI_Operation_Descriptor;
import Interfaces.MESI_Transitions;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 *
 * @author Vasiliy
 */
public class OutputPanel extends JPanel{
    
    final int XOFFSET = 35;
    final int YOFFSET = 35;
    final int DIAMETR = 80;
    final int DIST    = 100;
    int IX,EX,SX,MX,IY,EY,SY,MY;
    int ARRWINGSIZE = 10;
    int ARCDIAMETR = 40;
    
    Color I,E,S,M;
    Color IE,IM,IS,EI,ES,EM,MI,MS,SI,SM,EERH,MMRH,MMWH,SSRH,SSSHR;
    Color SigMI, SigMO, SigI, SigRTS;
    String Cache;
    String CacheString;
     public OutputPanel() {
         this.Reset();
     }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300,300);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        IX = XOFFSET+DIAMETR/2;
        IY = YOFFSET+DIAMETR/2;
        EX = XOFFSET+DIAMETR/2 + DIST + DIAMETR;
        EY = YOFFSET+DIAMETR/2;
        MX = XOFFSET+DIAMETR/2;
        MY = YOFFSET+DIAMETR/2 + DIST + DIAMETR;
        SX = XOFFSET+DIAMETR/2 + DIST + DIAMETR;
        SY = YOFFSET+DIAMETR/2 + DIST + DIAMETR;
        
        super.paintComponent(g);       
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        g.setColor(Color.BLACK);
        this.DrawMESI(g);
    }  
    
    public void Reset()
    {
        I=E=S=M = Color.BLACK;
        EERH = MMRH = SSRH = MMWH = IE=IS=IM=SM=EM = Color.BLUE;
        SSSHR = EI=SI=MI=MS=ES = Color.GREEN.darker();
        SigMI = SigMO = SigI = SigRTS = Color.MAGENTA.darker();
        Cache = "";
        CacheString = "";
    }
    
    
    public void SetState(MESI_Operation_Descriptor O)
    {
       this.Reset();
       Cache = "Кэш №"+String.valueOf(O.Current_Cache_Num+1);
       CacheString =  "Строка кэша №"+String.valueOf(O.Current_Cache_Num+1);;
       switch (O.Operation) {
           case EXCLUSIVE_TO_EXCLUSIVE_READ: 
                EERH = Color.red.brighter();
                E = Color.red.brighter();
                break;
            case SHARED_TO_SHARED_READ: 
                 SSRH = Color.red.brighter();
                 S = Color.red.brighter();
                break;
            case MODIFIED_TO_MODIFIED_READ: 
                MMRH = Color.red.brighter();
                M = Color.red.brighter();
                break;
            case READ_REQUEST: 
                SigRTS = Color.red.brighter();
                break;
            case READING_FROM_MEMORY:
                SigMI = Color.red.brighter();
                break;
            case INVALID_TO_EXCLUSIVE:
                IE = Color.red.brighter();
                I = Color.red.brighter();
                E = Color.red.brighter();
                break;
            case INVALID_TO_SHARED:
                IS = Color.red.brighter();
                I = Color.red.brighter();
                S = Color.red.brighter();
                break;
            case EXCLUSIVE_TO_SHARED:
                ES = Color.MAGENTA.brighter();
                E = Color.MAGENTA.brighter();
                S = Color.MAGENTA.brighter();
                break;
            case SHARED_TO_SHARED: 
                SSRH = Color.MAGENTA.brighter();
                S = Color.MAGENTA.brighter();
                break;
            case WRITE_TO_MEMORY: 
                SigMO = Color.red.brighter();
                break;
            case MODIFIED_TO_SHARED:
                MS = Color.MAGENTA.brighter();
                M = Color.MAGENTA.brighter();
                S = Color.MAGENTA.brighter();
                break;
            case MODIFIED_TO_MODIFIED_WRITE: 
                MMWH = Color.red.brighter();
                M = Color.red.brighter();
                break;
            case EXCLUSIVE_TO_MODIFIED: 
                EM = Color.red.brighter();
                E = Color.red.brighter();
                M = Color.red.brighter();
                break;
            case INVALIDATE_REQUSET: 
                SigI = Color.red.brighter();
                break;
            case INVALID_TO_MODIFIED: 
                IM = Color.red.brighter();
                I = Color.red.brighter();
                M = Color.red.brighter();
                break;
            case SHARED_TO_MODIFIED:
                SM = Color.red.brighter();
                S = Color.red.brighter();
                M = Color.red.brighter();
                break;
            case EXCLUSIVE_TO_INVALID: 
                EI = Color.MAGENTA.brighter();
                E = Color.MAGENTA.brighter();
                I = Color.MAGENTA.brighter();
                break;
            case SHARED_TO_INVALID:
                SI = Color.MAGENTA.brighter();
                S = Color.MAGENTA.brighter();
                I = Color.MAGENTA.brighter();
                break;
            case MODIFIED_TO_INVALID: 
                MI = Color.MAGENTA.brighter();
                M = Color.MAGENTA.brighter();
                I = Color.MAGENTA.brighter();
                break;
       }
    }
   
    private void DrawMESI(Graphics g)
    {
        g.setFont(g.getFont().deriveFont(Font.BOLD).deriveFont(16.0f));
        g.drawString(this.Cache, XOFFSET, YOFFSET/2);
        g.drawString(this.CacheString, XOFFSET + DIST, YOFFSET/2);
         g.setFont(g.getFont().deriveFont(Font.BOLD).deriveFont(12.0f));
        DrawArrow(g,IX,IY,EX,EY,10,"RME",IE, new MemoryIn(),new RequestToShare());
        DrawArrow(g,IX,IY,SX,SY,10,"RMS",IS,new MemoryIn(),new RequestToShare());
        DrawArrow(g,IX,IY,MX,MY,10,"WM",IM,new Invalidate(),null);
        
        DrawArrow(g,EX,EY,IX,IY,10,"SHW",EI,null,null);
        DrawArrow(g,EX,EY,SX,SY,0,"SHR",ES,null,null);
        DrawArrow(g,EX,EY,MX,MY,0,"WH",EM,null,null);
        DrawSelfArrow(g,EX,EY,0,45,"RH",EERH);
        
        DrawArrow(g,SX,SY,IX,IY,10,"SHW",SI,null,null);
        DrawArrow(g,SX,SY,MX,MY,10,"WH",SM,new Invalidate(),null);
        DrawSelfArrow(g,SX,SY,5,50,"RH",SSRH);
        DrawSelfArrow(g,SX,SY,-5,-50,"SRH",SSSHR);
        
        DrawArrow(g,MX,MY,IX,IY,10,"SHW",MI,new MemoryOut(),null);
        DrawArrow(g,MX,MY,SX,SY,10,"SHR",MS,new MemoryOut(),null);
        DrawSelfArrow(g,MX,MY,140,185,"RH",MMRH);
        DrawSelfArrow(g,MX,MY,130,85,"WH",MMWH);
        
        
        g.setFont(g.getFont().deriveFont(Font.BOLD).deriveFont(15.0f));
        DrawState(g, IX - DIAMETR/2,  IY - DIAMETR/2, "Invalid",I);
        DrawState(g, MX - DIAMETR/2,  MY - DIAMETR/2, "Modified",M);
        DrawState(g, EX - DIAMETR/2,  EY - DIAMETR/2, "Exclusive",E);
        DrawState(g, SX - DIAMETR/2,  SY - DIAMETR/2, "Shared",S);
        
        DrawSigils(g);

    }
    
    private void DrawSigils(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        Sigil MI  = new MemoryIn();
        Sigil MO  = new MemoryOut();
        Sigil RTS = new RequestToShare();
        Sigil INV = new Invalidate();
        
        int YOff = MY + DIAMETR/2 + DIAMETR/2;
        int XOff = MX + DIAMETR/2;
        
        MI.Draw(g2d, XOff, YOff, SigMI, DIAMETR/8);
        XOff+=DIAMETR/4+5;
        g2d.setColor(SigMI);
        g2d.drawString("Чтение из памяти", XOff, YOff);
        YOff += DIAMETR/4 + DIAMETR/16;
        
        XOff = MX + DIAMETR/2;
        MO.Draw(g2d, XOff, YOff, SigMO, DIAMETR/8);
        XOff+=DIAMETR/4+5;
        g2d.setColor(SigMO);
        g2d.drawString("Запись в память", XOff, YOff);
        YOff += DIAMETR/4 +  DIAMETR/16;
        
        XOff = MX + DIAMETR/2;
        RTS.Draw(g2d, XOff, YOff, SigRTS, DIAMETR/8);
        XOff+=DIAMETR/4+5;
        g2d.setColor(SigRTS);
        g2d.drawString("Запрос на разделение", XOff, YOff);
        YOff += DIAMETR/4 +  DIAMETR/16;
        
        XOff = MX + DIAMETR/2;
        INV.Draw(g2d, XOff, YOff, SigI, DIAMETR/8);
        XOff+=DIAMETR/4+5;
        g2d.setColor(SigI);
        g2d.drawString("Запрос инвалидации", XOff, YOff);
    }
    
    private void DrawSelfArrow(Graphics g, int CentX, int CentY, int StAngle, int EndAngle, String Name, Color C) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(C);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        AffineTransform at = AffineTransform.getTranslateInstance(CentX, CentY);
        at.concatenate(AffineTransform.getRotateInstance(StAngle*Math.PI/180));
        g2d.transform(at);
        
        double Angle = (EndAngle-StAngle) * Math.PI/180;
        double StX = DIAMETR/2, StY = 0;        
        double EndX = DIAMETR/2*Math.cos(Angle), EndY = DIAMETR/2*Math.sin(Angle);
        double MidX = (StX+EndX)/2, MidY = (StY+EndY)/2;
        
        double radius = ARCDIAMETR/2;
        double MidDist = Math.sqrt((StX-MidX)*(StX-MidX) + (StY-MidY)*(StY-MidY));
        double DistToCenter = radius*Math.sin(Math.acos(MidDist/radius));
        double MidAngle = Math.atan2(EndY-StY,EndX-StX);
        double CentAngle;
        if (EndY>0)
            CentAngle = MidAngle-Math.PI/2;
        else
            CentAngle = MidAngle+Math.PI/2;
        double ArcCentX = MidX + DistToCenter*Math.cos(CentAngle);
        double ArcCentY = MidY + DistToCenter*Math.sin(CentAngle);
        
        double AngleSt = Math.atan2(StY-ArcCentY,StX-ArcCentX);
        double AngleEnd = Math.atan2(EndY-ArcCentY,EndX-ArcCentX);
        g2d.drawArc((int)(ArcCentX-radius), (int)(ArcCentY-radius), 
                2*(int)radius,  2*(int)radius, (int)((-AngleSt/Math.PI)*180),(int)(((-AngleEnd+AngleSt)/Math.PI)*180));
        
       double LW_ArcX = ArcCentX+ARRWINGSIZE/2*Math.cos(AngleEnd);
       double RW_ArcX = ArcCentX-2*ARRWINGSIZE*Math.cos(AngleEnd);
       double LW_ArcY = ArcCentY+ARRWINGSIZE/2*Math.sin(AngleEnd);
       double RW_ArcY = ArcCentY-2*ARRWINGSIZE*Math.sin(AngleEnd);
        
       double LW_radius = radius -ARRWINGSIZE/2; 
       double RW_radius = radius +2*ARRWINGSIZE;
       int Sign;
       if (AngleEnd > AngleSt)
           Sign = 1;
       else Sign = -1;
      g2d.drawArc((int)(LW_ArcX-LW_radius), (int)(LW_ArcY-LW_radius), 
                2*(int)LW_radius,  2*(int)LW_radius, (int)(((-AngleEnd)/Math.PI)*180),Sign*90);
       g2d.drawArc((int)(RW_ArcX-RW_radius), (int)(RW_ArcY-RW_radius), 
                2*(int)RW_radius,  2*(int)RW_radius, (int)(((-AngleEnd)/Math.PI)*180),Sign*30);
       g2d.translate(ArcCentX, ArcCentY);
       g2d.rotate(-StAngle*Math.PI/180);
       drawCenteredText(g2d, 3, 0, Name);
    }
    
    
    
    private void DrawArrow(Graphics g, int StX, int StY, int EndX, int EndY, int Shift, String Name,
            Color C, Sigil S1, Sigil S2)
    {
        
       
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(C);
        double dx = EndX - StX;
        double dy = EndY - StY;
        double len = Math.sqrt(dx * dx + dy * dy);
        double OutOfCircle = -1*Math.sqrt(DIAMETR/2*DIAMETR/2 - Shift*Shift);
        AffineTransform at = AffineTransform.getTranslateInstance(StX, StY);
        at.concatenate(AffineTransform.getRotateInstance(Math.atan2(dy, dx)));
        at.concatenate(AffineTransform.getTranslateInstance(0, Shift));
        at.concatenate(AffineTransform.getTranslateInstance(OutOfCircle, 0));
        g2d.transform(at);

        
        g2d.drawLine(0, 0, (int) len, 0);
        g2d.drawLine((int) len, 0, (int) len - ARRWINGSIZE, ARRWINGSIZE/2);
        g2d.drawLine((int) len, 0, (int) len - ARRWINGSIZE, -ARRWINGSIZE/2);
        
         FontMetrics fm = g2d.getFontMetrics();
         java.awt.geom.Rectangle2D rect = fm.getStringBounds(Name, g2d);
         int textHeight = (int) (rect.getHeight());
         int textWidth = (int) (rect.getWidth());
         
         if (S1 != null)
         {
            Graphics2D g2dSigil = (Graphics2D) g2d.create();
            g2dSigil.translate(4 * len / 5, 0);
            g2dSigil.rotate(-Math.atan2(dy, dx));
            S1.Draw(g2dSigil, 0, 0, C, 10);
        }
         
         if (S2 != null)
         {
            Graphics2D g2dSigil = (Graphics2D) g2d.create();
            g2dSigil.translate(3 * len / 5, 0);
            g2dSigil.rotate(-Math.atan2(dy, dx));
            S2.Draw(g2dSigil, 0, 0, C, 10);
        }
          
         
         if ((Math.atan2(dy, dx)) <= Math.PI/2 && (Math.atan2(dy, dx)) > -Math.PI/2)
             g2d.drawString(Name, (int)len/2-textWidth/2-(int)OutOfCircle/2, 3*textHeight/2);
         else {
             at = AffineTransform.getRotateInstance(Math.PI);
             g2d.transform(at);
             g2d.drawString(Name, -((int)len/2+textWidth/2-(int)OutOfCircle/2), -textHeight/2);
         }
    }
    
    private void DrawState(Graphics g, int OffsetX, int OffsetY, String Name, Color C)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.WHITE);
        g2d.fillOval(OffsetX,   OffsetY,   DIAMETR, DIAMETR);
        g2d.setColor(C);
        g2d.drawOval(OffsetX,   OffsetY,   DIAMETR, DIAMETR);        
        drawCenteredText(g2d, OffsetX + DIAMETR/2, OffsetY + DIAMETR/2,Name);
        g2d.setColor(Color.BLACK);
    }
    
    public static void drawCenteredText(Graphics g, int x, int y, String text)
    {
        FontMetrics fm = g.getFontMetrics();
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());
        
        int cornerX = x - (textWidth / 2);
        int cornerY = y - (textHeight / 2) + fm.getAscent();

        g.drawString(text, cornerX, cornerY);  // Draw the string.
    }
    
    
    abstract class Sigil {
        
        public void Draw(Graphics g,int X, int Y, Color C, int radius)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.translate(X, Y);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(-radius, -radius,   2*radius, 2*radius);
            g2d.setColor(C);
            g2d.drawOval(-radius, -radius,   2*radius, 2*radius); 
            DrawInside(g2d,C,radius);
        }
        
        abstract void DrawInside(Graphics g, Color C, int radius);
    }
    
    class MemoryIn extends Sigil {

        @Override
        void DrawInside(Graphics g, Color C, int radius)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(C);
            g2d.rotate(Math.PI / 2);
            g2d.drawLine(-radius + 1, 0, radius - 1, 0);
            g2d.drawLine(radius - 1, 0, 0, radius/2);
            g2d.drawLine(radius - 1, 0, 0, -radius/2);
        }

    }

    class MemoryOut extends Sigil {

        @Override
        void DrawInside(Graphics g, Color C, int radius)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(C);
            g2d.rotate(-Math.PI / 2);
            g2d.drawLine(-radius + 1, 0, radius - 1, 0);
            g2d.drawLine(radius - 1, 0, 0, radius/2);
            g2d.drawLine(radius - 1, 0, 0, -radius/2);
        }

    }

    class Invalidate extends Sigil {

        @Override
        void DrawInside(Graphics g, Color C, int radius)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(C);
            g2d.rotate(Math.PI / 4);
            g2d.drawLine(-radius + 1, 0, radius - 1, 0);
            g2d.drawLine(0, -radius + 1, 0, radius - 1);

        }
    }
    
     class RequestToShare extends Sigil {

        @Override
        void DrawInside(Graphics g, Color C, int radius)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(C);
            g2d.drawLine(-radius + 1, 0, radius - 1, 0);
            g2d.drawLine(0, -radius + 1, 0, radius - 1);

        }
    }
}
