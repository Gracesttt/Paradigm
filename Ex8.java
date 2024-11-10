// Thitirat Kulpornpaisarn 6580871
package Ex8_6580871;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainApplication extends JFrame implements KeyListener
{
    private JLabel          contentpane;
    private CharacterLabel  []petLabels;
    private CharacterLabel  activeLabel;
    private ItemLabel       wingLabel;
    
    private int framewidth   = MyConstants.FRAMEWIDTH;
    private int frameheight  = MyConstants.FRAMEHEIGHT;
    private int groundY      = MyConstants.GROUND_Y;
    private int skyY         = MyConstants.SKY_Y;
    private int bridgeLeftX  = MyConstants.BRIDGE_LEFT;
    private int bridgeRightX = MyConstants.BRIDGE_RIGHT;

    public static void main(String[] args) 
    {
	new MainApplication();   
    }
       
    //constrctor
    public MainApplication()
    {      
	setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

	// set background image by using JLabel as contentpane
	setContentPane(contentpane = new JLabel());
	MyImageIcon background = new MyImageIcon(MyConstants.FILE_BG).resize(framewidth, frameheight);
	contentpane.setIcon( background );
	contentpane.setLayout( null );
        
        petLabels = new CharacterLabel[2];
	petLabels[0] = new CharacterLabel(MyConstants.FILE_DOG_1, MyConstants.FILE_DOG_2, 
                                          120, 100, this); 
        petLabels[0].setMoveConditions(bridgeLeftX-120, groundY, true, false);
        
        petLabels[1] = new CharacterLabel(MyConstants.FILE_CAT_1, MyConstants.FILE_CAT_2, 
                                          120, 100, this); 
        petLabels[1].setMoveConditions(bridgeRightX, groundY, true, false);
        
        wingLabel = new ItemLabel(MyConstants.FILE_WING, 100, 80, this);
        wingLabel.setMoveConditions(bridgeRightX+300, skyY, true, true);        

        
        // first added label is at the front, last added label is at the back
        contentpane.add( wingLabel );
        contentpane.add( petLabels[0] );
        contentpane.add( petLabels[1] );
        addKeyListener(this);
        setDog();
	repaint();
        
    }
    
        
    public CharacterLabel getActiveLabel()  { return activeLabel; }    
    public void setDog()                    { activeLabel = petLabels[0]; setTitle("Dog is active"); }
    public void setCat()                    { activeLabel = petLabels[1]; setTitle("Cat is active"); }
    
    //implement KeyListener
    public void keyTyped(KeyEvent e){ }
    public void keyReleased(KeyEvent e){ }

    @Override
    public void keyPressed(KeyEvent e){ 
	if(e.getKeyChar()== 'd'){
            setDog();
        }
        else if(e.getKeyChar()== 'c'){
            setCat();
        }
        else if(e.getKeyChar()== 'j'){  
            getActiveLabel().jump();
        }
        else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            getActiveLabel().setMainIcon();
            wingLabel.setVisible(true);
            int wingX =activeLabel.curX+activeLabel.width;
            int wingY =MyConstants.GROUND_Y-wingLabel.height;
            wingLabel.setMoveConditions(wingX, wingY, true, true);
            activeLabel.curY =MyConstants.GROUND_Y -activeLabel.height+105;
            activeLabel.updateLocation();
        }
        else if(e.getKeyCode()==KeyEvent.VK_UP){ 
            getActiveLabel().moveUp();
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){  
            getActiveLabel().moveDown();
        } 
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            getActiveLabel().moveRight();
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            getActiveLabel().moveLeft();
        } 
        
    }
}


////////////////////////////////////////////////////////////////////////////////


abstract class BaseLabel extends JLabel
{
    protected MyImageIcon      iconMain, iconAlt;
    protected int              curX, curY, width, height;
    protected boolean          horizontalMove, verticalMove;
    protected MainApplication  parentFrame;   
    
    // Constructors
    public BaseLabel() { }    
    public BaseLabel(String file1, int w, int h, MainApplication pf)
    {
        width = w; height = h;
        iconMain = new MyImageIcon(file1).resize(width, height);  
	setHorizontalAlignment(JLabel.CENTER);
	setIcon(iconMain);
        parentFrame = pf;          
        iconAlt = null;
    }
    public BaseLabel(String file1, String file2, int w, int h, MainApplication pf)				
    { 
        this(file1, w, h, pf);
        iconAlt = new MyImageIcon(file2).resize(width, height);
    }

    // Common methods
    public void setMoveConditions(boolean hm, boolean vm)
    {
        horizontalMove = hm; 
        verticalMove   = vm;
    }    
    public void setMoveConditions(int x, int y, boolean hm, boolean vm)
    {
        curX = x; curY = y; 
        setBounds(curX, curY, width, height);
        setMoveConditions(hm, vm);
    } 
    
    abstract public void updateLocation(); 
}


////////////////////////////////////////////////////////////////////////////////


class CharacterLabel extends BaseLabel 
{
    public CharacterLabel(String file1, String file2, int w, int h, MainApplication pf)				
    { 
        // Main icon without wings, alternative icon with wings
        super(file1, file2, w, h, pf);
    }
    
    public void updateLocation(){setBounds(curX,curY,width,height);}    
    public void moveUp(){
        if (getIcon()==iconAlt && curY>0) {
            curY-=15; 
            if(curY<0) curY=0; 
            updateLocation();
        }
    }

    public void moveDown(){
        if (getIcon()==iconAlt){
            curY+=15;
            if(curY+height >MyConstants.FRAMEHEIGHT){
                curY=MyConstants.FRAMEHEIGHT-height;
            }
            updateLocation();
        }
    }

    public void moveLeft(){
        curX-=15; 
        if(curX<0) curX=MyConstants.FRAMEWIDTH-width; 
        updateLocation();
    }

    public void moveRight(){
        curX+=15; 
        if(curX+width >MyConstants.FRAMEWIDTH) curX=0; 
        updateLocation();
    }
    
    public void jump(){ 
        if(getIcon()!=iconAlt){
        if(this.curX+10 >MyConstants.BRIDGE_RIGHT)
            this.curX =MyConstants.BRIDGE_LEFT-115;
        else if(this.curX <MyConstants.BRIDGE_LEFT-105)
            this.curX =MyConstants.BRIDGE_RIGHT+25;
        updateLocation();
    }
    }
    
    public void setMainIcon(){ 
        setIcon(iconMain); 
        setMoveConditions(horizontalMove, false); 
    }
    
    public void setAltIcon(){ 
        setIcon(iconAlt); 
        setMoveConditions(horizontalMove, true); 
    }
    
}



////////////////////////////////////////////////////////////////////////////////


class ItemLabel extends BaseLabel implements MouseMotionListener,MouseListener
{
    public ItemLabel(String file, int w, int h, MainApplication pf)				
    { 
        // Alternative icon = null
        super(file, w, h, pf);
        addMouseMotionListener(this);
        addMouseListener(this);
    }   
    
    public boolean drag=false;

    //implement MouseLis
    public void mouseEntered(MouseEvent e){ }	
    public void mouseExited(MouseEvent e){ }
    public void mouseClicked(MouseEvent e){ }
    
    //implement MouseMotionLis
    public void mouseMoved(MouseEvent e){ }
    
    @Override
    public void mouseDragged(MouseEvent e){ 
	if(drag){
            curX = curX +e.getX();
            curY = curY +e.getY();            
            Container p = getParent();
            if (curX<0) curX=0; 
            if (curY<0) curY=0;           
            if (curX+width > p.getWidth()) curX = p.getWidth()-width;
            if (curY+height > p.getHeight()) curY = p.getHeight()-height;            
            setLocation(curX,curY);
            
            if (this.getBounds().intersects(parentFrame.getActiveLabel().getBounds())) {
                parentFrame.getActiveLabel().setAltIcon();
                this.setVisible(false);
            }
        }  
        
    }
    
    @Override
    public void mousePressed(MouseEvent e){drag=true;}
    
    @Override
    public void mouseReleased(MouseEvent e){drag=false;}
    
    public void updateLocation()    { }
    public void setMainIcon()       { setIcon(iconMain); }    
    public void setAltIcon()        { setIcon(iconAlt); }
     
}
    
