import static java.lang.System.*;
import javax.swing.*;
import java.util.*;
import java.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.lang.*;
import java.net.*;

public class Bejixed extends JFrame implements ActionListener
{
	private static BePanel bp;
	private Container win, subWin;
	private JMenuBar menubar;
	private JMenu statMenu, optionMenu;
	private JMenuItem soundItem, tutItem, statItem;
	private ImageIcon background;
	private JButton ngbut, newsong;
	public JRadioButton mute,invis, invis2, invis3;
	private static JLabel back,board,scoreboard,transition,songc;
	public JFrame soundFrame, statFrame, tutFrame;
	public JTextArea scoretext= new JTextArea();
	public static String songn, name;
	public AePlayWave song, se, phase;
	private ArrayList<ImageIcon> backlist= new ArrayList<ImageIcon>();
	private ArrayList<String> songlist= new ArrayList<String>();
	private ArrayList<Integer> highscores= new ArrayList<Integer>();
	private static int b, sn, score=0, move=0, comboer=1, comcount=0;
	public static boolean goer=false,playing=true, scoreChanged=true;
	public float time=0;
	public static InetAddress host;
	public static Socket socket;
	public static ObjectInputStream ois;
	public static ObjectOutputStream oos;
	
	public static int jray[][]=new int[8][8];

	public Bejixed()
	{
		super ("Bejixed");
		win = getContentPane();
		win.setLayout(null);
		
		try
		{
			host = InetAddress.getLocalHost();
			System.out.println(InetAddress.getLocalHost());
			System.out.println(host.getHostName());
		
			socket = new Socket(InetAddress.getLocalHost(), 7777);
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (UnknownHostException e) {
		
		e.printStackTrace();
		
		} catch (IOException e) {
		
		e.printStackTrace();
		
		}
		
		try
		{
			Scanner sc = new Scanner(new File("highscore.txt"));
    		while (sc.hasNextInt())
      			highscores.add(sc.nextInt());
		}
		catch(IOException e)
			{
				System.out.println("Error in file" + e.toString());
			}
		
		phase=new AePlayWave("glass_shatter_c.wav");
		
		soundFrame=new JFrame("Audio");
		soundFrame.setSize(300,300);
		soundFrame.setLocation(410,670);
		
		statFrame=new JFrame("Highscores");
		statFrame.setSize(300,300);
		statFrame.setLocation(100,670);
		
		tutFrame=new JFrame("Instructions");
		tutFrame.setSize(400,600);
		tutFrame.setLocation(100,55);
		
		JLabel tutty=new JLabel(new ImageIcon("instructions.jpg"));
		tutty.setSize(400,600);
		tutFrame.add(tutty);
		
		scoretext.setText("1. \t"+highscores.get(0));
		for(int i=1;i<10;i++)
		scoretext.append("\n"+(i+1)+". \t"+highscores.get(i));
		statFrame.add(scoretext);
		scoretext.setSize(175,175);
		scoretext.setLocation(50,50);
		scoretext.setEditable(false);
		
		transition=new JLabel(new ImageIcon("transition2.gif"));
		transition.setSize(1500,1000);
		transition.setLocation(0,0);
		transition.setVisible(false);
		win.add(transition);
		
		scoreboard=new JLabel(""+score+"\tx"+comboer);
		scoreboard.setFont(new Font("Rockwell", Font.CENTER_BASELINE,22));
		scoreboard.setForeground(Color.YELLOW);
		scoreboard.setSize(100,50);
		scoreboard.setLocation(475,110);
		win.add(scoreboard);
		
		receive();
		
		bp = new BePanel();

		bp.setSize(538,539);
		bp.setLocation(600, 50);
		win.add(bp);

		ngbut=new JButton("New Game");
		ngbut.setSize(100,50);
		ngbut.setLocation(450,400);
		ngbut.setVisible(true);
		ngbut.addActionListener(this);
		win.add(ngbut);		
		
		board=new JLabel(new ImageIcon("background2.jpg"));
		board.setSize(200,539);
		board.setLocation(400,50);
		win.add(board);

		reader();

		sn=(int)(Math.random()*songlist.size());
		song=new AePlayWave(songlist.get(sn));
		song.start();

		JLabel j=new JLabel("Current Song:");
		j.setSize(300,20);
		j.setLocation(95,5);
		soundFrame.add(j);

		mute=new JRadioButton();
		mute.setText("Mute");
		mute.setSize(75,20);
		mute.setLocation(90,150);
		soundFrame.add(mute);
		mute.addActionListener(this);

		newsong= new JButton("New Song");
		newsong.setSize(100,30);
		newsong.setLocation(80,75);
		soundFrame.add(newsong);
		newsong.addActionListener(this);

		songn=songlist.get(sn).substring(songlist.get(sn).lastIndexOf("\\")+1,songlist.get(sn).lastIndexOf("."));

		songc= new JLabel(songn);
		songc.setSize(300,20);
		songc.setLocation((130-(songn.length()*3)),25);
		soundFrame.add(songc);

		se=new AePlayWave("space1.wav");

		menubar=new JMenuBar();
		menubar.setSize(1500,20);
		menubar.setLocation(0,0);
		menubar.setVisible(true);

		optionMenu=new JMenu();
		optionMenu.setText("Options");
		optionMenu.setVisible(true);
		optionMenu.addActionListener(this);

		tutItem=new JMenuItem();
		tutItem.setText("Tutorial");
		tutItem.setVisible(true);
		tutItem.addActionListener(this);
		optionMenu.add(tutItem);
		
		statItem=new JMenuItem();
		statItem.setText("Statistics");
		statItem.setVisible(true);
		statItem.addActionListener(this);
		optionMenu.add(statItem);

		soundItem=new JMenuItem();
		soundItem.setText("Sound");
		soundItem.setVisible(true);
		soundItem.addActionListener(this);
		optionMenu.add(soundItem);

		menubar.add(optionMenu);
		win.add(menubar);

		b=(int)(Math.random()*backlist.size());
		back=new JLabel(backlist.get(b));
		back.setSize(1500,1000);
		back.setLocation(0,0);
		win.add(back);

		invis=new JRadioButton();
		invis.setText("invis");
		invis.setSize(20,20);
		invis.setLocation(100,250);
		invis.setVisible(false);
		soundFrame.add(invis);
		
		invis2=new JRadioButton();
		invis2.setText("invis");
		invis2.setSize(20,20);
		invis2.setLocation(100,250);
		invis2.setVisible(false);
		statFrame.add(invis2);
		
		invis3=new JRadioButton();
		invis3.setText("invis");
		invis3.setSize(20,20);
		invis3.setLocation(100,250);
		invis3.setVisible(false);
		tutFrame.add(invis3);

		win.setVisible(true);
		setSize(1500,1000);
		setLocation(75,0);
		try{setIconImage(ImageIO.read(new File("logo2.jpg")));}
		catch(Exception ex){}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void actionPerformed (ActionEvent e)
		{
			if(e.getSource()==ngbut)
			{
					soundFrame.setVisible(false);
					tutFrame.setVisible(false);
					statFrame.setVisible(false);
					song.stop();
					playing=false;	
					transition.setVisible(true);
					ngbut.setVisible(false);
					
					goer=true;
					se=new AePlayWave("space1.wav");
					se.start();
					win.remove(back);
					
					int b4=b;
					int s4=sn;
					while(b==b4||sn==s4)
					{
						b=(int)(Math.random()*backlist.size());
						sn=(int)(Math.random()*songlist.size());
					}
					
					boolean boo=true;
					for(int i=0;i<highscores.size();i++)
						if(boo&&score>highscores.get(i))
						{
							boo=false;
							highscores.add(i,score);
							if(highscores.size()>10)
								highscores.remove(10);
						}
						
					score=0;
					scoreboard.setText(""+score+"\tx"+comboer);
					comboer=1;
					
					try
					{
						Scanner sc = new Scanner(new File("highscore.txt"));
						FileOutputStream Scores= new FileOutputStream("highscore.txt");
						PrintStream pe = new PrintStream(Scores);
						
						for(int j=0;j<10;j++)
							pe.println(highscores.get(j));
							
						pe.close();
					}
					catch(IOException x)
						{
							System.out.println("Error in file" + x.toString());
						}
			
					win.remove(bp);
					bp = new BePanel();
					bp.setSize(538,539);
					bp.setLocation(600, 50);
					win.add(bp);
					bp.repaint();

					back=new JLabel(backlist.get(b));
					back.setSize(1500,1000);
					back.setLocation(0,0);
					win.add(back);
					repaint();
			}

			if(e.getSource()==soundItem)
			{
				soundFrame.setVisible(true);
			}
			
			if(e.getSource()==tutItem)
			{
				tutFrame.setVisible(true);
			}
			
			if(e.getSource()==statItem)
			{
				scoretext.setText("1. \t"+highscores.get(0));
				for(int i=1;i<10;i++)
				scoretext.append("\n"+(i+1)+". \t"+highscores.get(i));
				statFrame.setVisible(true);
			}
			
			if(e.getSource()==mute)
			{
				if(mute.isSelected())
				{
					song.suspend();
				}
				else
				{
					song.resume();
				}

			}
			if(e.getSource()==newsong)
			{
				int s4=sn;
					while(sn==s4)
					{
						sn=(int)(Math.random()*songlist.size());
					}
				song.stop();
				song=new AePlayWave(songlist.get(sn));
				song.start();
				songn=songlist.get(sn).substring(songlist.get(sn).lastIndexOf("\\")+1,songlist.get(sn).lastIndexOf("."));
				songc.setText(songn);
				songc.setLocation((130-(songn.length()*3)),25);
				if(mute.isSelected())
					song.suspend();
			}

		}

	//READS IN FILES FROM FOLDER
	public void reader()
	{
		File file = new File("backgrounds");
        File[] filelist = file.listFiles();

        for (int i = 0; i < filelist.length; i++)
				try
				{
					BufferedImage image = ImageIO.read(filelist[i]);
					ImageIcon icon=new ImageIcon(image);
					backlist.add(icon);
				}
				catch (IOException e)
				{
					out.println("BACKGROUND FILES NOT FOUND");
				}

		file = new File("music");
        filelist = file.listFiles();

		for (int i = 0; i < filelist.length; i++)
		{
			songlist.add(filelist[i].toString());
		}
	}


	public static void main(String[]arg)
	{
		Scanner sc=new Scanner(System.in);
		out.print("Enter Name: ");
		name=sc.nextLine();
		
		new Bejixed();	
		
		while(true)
		{
			while(playing)
			{
				scoreboard.setText(""+score+"\tx"+comboer);
				send();
				bp.step();
				bp.associate();
				bp.repaint();
				try
				{
					Thread.sleep(60);
				}   
				catch (Exception e) {}
			}	
		}
					
	}
	
	public static void receive()
	{
		Scanner sc = new Scanner(System.in);
		
		try 
		{
		System.out.println("Waiting For Response: ");		
		
		//ois = new ObjectInputStream(socket.getInputStream());
		
		jray = (int[][]) ois.readObject();
		
		System.out.println("Message Recieved");
		
		//ois.close();
		
		}
		 catch (UnknownHostException e) {
		
		e.printStackTrace();
		
		} catch (IOException e) {
		
		e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
		
		}

	}
	
	public static void send()
	{
		if(scoreChanged)
		try 
		{					
			//oos = new ObjectOutputStream(socket.getOutputStream());
			
			oos.writeObject(name+" "+score);
				
			System.out.println("Sent Score "+score);
			scoreChanged=false;
			
		} catch (UnknownHostException e) {
		
		e.printStackTrace();
		
		} catch (IOException e) {
		
		e.printStackTrace();
		
		} /*catch (ClassNotFoundException e) {
		
		e.printStackTrace();
		
		}*/
	}


	/*****************************************************************   I N T E R N A L     C L A S S   	*****************************************************************/

	public class BePanel extends JPanel implements MouseListener
	{
		private ArrayList <Integer> bombList=new  ArrayList<Integer>();
		private ArrayList <BufferedImage> imageList = new ArrayList <BufferedImage>();
		public  Insets insets = new Insets(-5,-5,-5,-5);
		private BufferedImage image, image2;
		public BufferedImage purplej,redj,orangej,bluej,whitej,greenj,yellowj;
		public BufferedImage lpurplej,lredj,lorangej,lbluej,lwhitej,lgreenj,lyellowj, blueback;
		private Insets in = this.getInsets();
		private Point mouse = new Point(0,0);
		public jewel[][] board= new jewel[8][8];
		public boolean selected;
		public int selr,selc,wrongr,wrongc;
		private BufferedImage buffer = new BufferedImage(800, 900, BufferedImage.TYPE_INT_RGB);
		public int m,n,o,p;
		public int typeOfMove=0;
		
	 	public BePanel()
	   	{
	   		setOpaque(true);
	   		setBorder(BorderFactory.createLineBorder(new Color(120,30,100),5));
			addMouseListener(this);
	
	 		try
			{
	    		image = ImageIO.read(new File("images3.gif"));
			}
			catch (IOException e)
				{
					out.println("Images NOT FOUND");
				}
	
			for (int r = 0; r < 3; r++)
				for (int c = 0; c < 5; c++)
				{
					BufferedImage temp = image.getSubimage(c*100,r*100,100,100);
					imageList.add(temp);
				}
	
			purplej = imageList.get(1);
			redj = imageList.get(3);
			orangej = imageList.get(4);
			bluej = imageList.get(5);
			whitej=imageList.get(0);
			greenj = imageList.get(2);
			yellowj = imageList.get(6);
	
			lpurplej = imageList.get(8);
			lredj = imageList.get(10);
			lorangej = imageList.get(11);
			lbluej = imageList.get(12);
			lwhitej=imageList.get(7);
			lgreenj = imageList.get(9);
			lyellowj = imageList.get(13);
	
			blueback = imageList.get(14);
	
			//GRID BUILDER
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
				{
					int x= (int) (Math.random()*7);
					board[i][j]=new jewel(5+i*66,5+j*66,66,66,jray[i][j],blueback);
				}
			associate();
			
			while(notGood())
			{
				fixit();
			}
			repaint();
		}
	
		//Makes sure there are no triplets at start
		public boolean notGood()
		{
			for(int r=0;r<8;r++)
				for(int c=0;c<6;c++)
					if(board[r][c].getValue()==board[r][c+1].getValue()&&board[r][c].getValue()==board[r][c+2].getValue())
						return true;
	
			for(int r=0;r<6;r++)
				for(int c=0;c<8;c++)
					if(board[r][c].getValue()==board[r+1][c].getValue()&&board[r][c].getValue()==board[r+2][c].getValue())
						return true;
	
			return false;
		}
	
		//Assigns random jewel values to beginning game triplets
		public void fixit()
		{
			for(int r=0;r<8;r++)
				for(int c=0;c<6;c++)
				{
					if(board[r][c].getValue()==board[r][c+1].getValue()&&board[r][c].getValue()==board[r][c+2].getValue())
					{
						int fix=board[r][c+1].getValue();
	
						while(fix==board[r][c+1].getValue())
							fix=(int) (Math.random()*7);
	
						board[r][c+1].setValue(fix);
						board[r][c+1].setImage(imageList.get(fix));
					}
				}
	
	
			for(int r=0;r<6;r++)
				for(int c=0;c<8;c++)
				{
					if(board[r][c].getValue()==board[r+1][c].getValue()&&board[r][c].getValue()==board[r+2][c].getValue())
					{
						int fix=board[r+1][c].getValue();
						
						while(fix==board[r+1][c].getValue())
							fix=(int) (Math.random()*7);
							
						board[r+1][c].setValue(fix);
						board[r+1][c].setImage(imageList.get(fix));
					}
				}
		}
	
		//Processes Moves
		public void step()
		{
			/******************************  T I M E    I N C R E M E N T  ******************************/
			time+=0.09;
			if(time>1)
			{
			if(comboer>1&&typeOfMove!=1)
			comboer--;
			time=0;
			}
			if(comboer>10)
			comboer=10;

			move=typeOfMove;
			switch(typeOfMove)
			{
				case 1:
				{
					clear();
				}
					break;
				case 2:
				{
					dig();
				}
				break;
				case 3:
				{
					addToBlanks();
				}
				case 4:
				{
					wronga();
				}
				break;
				case 5:
				{
					wrongb();
				}
				break;
			}
		}
	
	
		//Assigns correct image to jewel values
		public void associate()
		{
		for(int h=0;h<8;h++)
			{
				for(int k=0;k<8;k++)
				{
					int val=board[h][k].getValue();
					if(val==1)
					board[h][k].setImage(purplej);
	
					if(val==3)
					board[h][k].setImage(redj);
	
					if(val==4)
					board[h][k].setImage(orangej);
	
					if(val==5)
					board[h][k].setImage(bluej);
	
					if(val==0)
					board[h][k].setImage(whitej);
	
					if(val==2)
					board[h][k].setImage(greenj);
	
					if(val==6)
					board[h][k].setImage(yellowj);
	
					if (val==8)
					board[h][k].setImage(blueback);
				}
			}
		}
	
		public void mouseReleased (MouseEvent e)
		{
		}
		
		public void mousePressed(MouseEvent e)
		{
			mouse = e.getPoint();
			int x= (int) mouse.getX();
			int y= (int) mouse.getY();
	
			for(int r=0;r<8;r++)
			{
				for(int c=0;c<8;c++)
				{
					if(board[r][c].getRectangle().contains(y,x))
					{
						if(!selected&&board[r][c].getValue()!=8)
						{
							selected=true;
							repaint();
							selr=r;
							selc=c;
						}
						else
						{
							swap(r,c);
							selected=false;
						}
					}
				}
			}
		}
	
		public void mouseEntered (MouseEvent e)	{}
	
		public void mouseExited(MouseEvent e)  {}
	
		public void mouseClicked(MouseEvent e)	{}
	
		//Swaps selected jewels
		public void swap(int r,int c)
		{
			time=0;
			if((selc==c+1||selc==c-1)&&(selr==r)&&isValid(r,c))
			{
				int temp=board[selr][selc].getValue();
				board[selr][selc].setValue(board[r][c].getValue());
				board[r][c].setValue(temp);
				typeOfMove=1;
			}
	
			else if((selr==r+1||selr==r-1)&&(selc==c)&&isValid(r,c))
			{
				int temp=board[selr][selc].getValue();
				board[selr][selc].setValue(board[r][c].getValue());
				board[r][c].setValue(temp);
				typeOfMove=1;
			}
			else if(!isValid(r,c))
			{
				m=r;
				n=c;
				o=selr;
				p=selc;
				typeOfMove=4;
				new AePlayWave("crunch.wav").start();
			}
			selr=0;
			selc=0;
		}
		
		//Incorrect Move Repaint Method
		public void wronga()
		{
			int temp=board[o][p].getValue();
				board[o][p].setValue(board[m][n].getValue());
				board[m][n].setValue(temp);
				typeOfMove=5;
		}
		
		//Second Incorrect Move Repaint Method
		public void wrongb()
		{
			int temp=board[o][p].getValue();
			board[o][p].setValue(board[m][n].getValue());
			board[m][n].setValue(temp);
			m=0;
			n=0;
			o=0;
			p=0;
			typeOfMove=1;
		}
		
		//Temporarily swaps jewels to check for triplets
		public boolean isValid(int h, int k)
		{
			jewel temp=new jewel(board[selr][selc]);
			board[selr][selc]=board[h][k];
			board[h][k]=temp;
	
			for(int r=0;r<8;r++)
				for(int c=0;c<6;c++)
				{
					if(board[r][c].getValue()!=8&&board[r][c].getValue()==board[r][c+1].getValue()&&board[r][c].getValue()==board[r][c+2].getValue())
					{
						board[h][k]=board[selr][selc];
						board[selr][selc]=temp;
						return true;
					}
				}
	
	
			for(int r=0;r<6;r++)
				for(int c=0;c<8;c++)
				{
					if(board[r][c].getValue()!=8&&board[r][c].getValue()==board[r+1][c].getValue()&&board[r][c].getValue()==board[r+2][c].getValue())
					{
						board[h][k]=board[selr][selc];
						board[selr][selc]=temp;
						return true;
					}
				}
				board[h][k]=board[selr][selc];
				board[selr][selc]=temp;
				return false;
		}
	
		//Sets triplet values to 8 (void number)
		public void clear()
		{
			int ct=0;
			
			for(int r=0;r<8;r++)
				for(int c=0;c<6;c++)
				{
					if(board[r][c].getValue()!=8&&board[r][c].getValue()==board[r][c+1].getValue()&&board[r][c].getValue()==board[r][c+2].getValue())
					{
						int check=board[r][c].getValue();
						int b=c;
						while(b<8&&board[r][b].getValue()==check)
						{
							board[r][b].setValue(8);
							score+=(10*comboer);
							scoreChanged=true;
							b++;
						}
						time=0;
						for(int bo=0;bo<comboer;bo++)
							time+=.05;
						
						comboer++;
						player();
						ct++;
					}
				}
	
	
			for(int r=0;r<6;r++)
				for(int c=0;c<8;c++)
				{
					if(board[r][c].getValue()!=8&&board[r][c].getValue()==board[r+1][c].getValue()&&board[r][c].getValue()==board[r+2][c].getValue())
					{
						int check=board[r][c].getValue();
						int b=r;
						while(b<8&&board[b][c].getValue()==check)
						{
							board[b][c].setValue(8);
							score+=(10*comboer);
							scoreChanged=true;
							b++;
						}
						time=0;
						for(int bo=0;bo<comboer;bo++)
							time+=.05;
						
						comboer++;
						player();
						ct++;
					}
				}
			typeOfMove=2;
		}
	
		//Shifts jewels down if empty space below
		public void dig()
		{
			boolean callclear=true;
			for(int r=1;r<8;r++)
				for(int c=0;c<8;c++)
				{
					if(board[r][c].getValue()==8&&board[r-1][c].getValue()!=8)
					{
						int b=r;
						board[b][c].setValue(board[b-1][c].getValue());
						board[b-1][c].setValue(8);
						b--;
					}
				}
				for(int r=0;r<8;r++)
							{
								for(int c=0;c<8;c++)
								{
									if(board[r][c].getValue()==8)
										callclear=false;
								}
							}
			if(callclear)
				typeOfMove=1;
			else
			typeOfMove=3;
		}
		
		public void player()
		{
			switch(comboer)
			{
				case 4:		new AePlayWave("grenade.wav").start();		break;
				case 5:		new AePlayWave("shotgun.wav").start();		break;
				case 6:		new AePlayWave("witch.wav").start();		break;
				case 7:		new AePlayWave("martian.wav").start();		break;
				case 8:		new AePlayWave("sick.wav").start();		break;
				case 9:		new AePlayWave("sniper.wav").start();		break;
				case 10:	new AePlayWave("rampage.wav").start();		break;
				default:	new AePlayWave("phasers3.wav").start();		break;	
			}
		}
		
		//Adds jewels to blanks on top row
		public void addToBlanks()
		{
			boolean callDig = false;
				for(int c=0;c<8;c++)
				{
					if(board[0][c].getValue()==8)
					{
						int val=(int)(Math.random()*7);
						board[0][c].setValue(val);
						callDig=true;
					}
				}
				if(callDig)
				typeOfMove=2;
				else
				typeOfMove=1;
		}
	
		
	 	public void paintComponent(Graphics g2)
		{
			super.paintComponent(g2);
			Graphics2D g = (Graphics2D)buffer.getGraphics();
			g.setColor(new Color(0,38,205));
			g.fillRect(0,0,540,540);
	
			boolean go=true;
			if(!se.isAlive()&&goer)
			{	
				transition.setVisible(false);		
				ngbut.setVisible(true);
				song.stop();
				go=false;
			}
			if(!song.isAlive()&&!goer&&!mute.isSelected())
			{
				song=new AePlayWave(songlist.get(sn));
				song.start();
				songn=songlist.get(sn).substring(songlist.get(sn).lastIndexOf("\\")+1,songlist.get(sn).lastIndexOf("."));
				songc.setText(songn);
				songc.setLocation((130-(songn.length()*3)),25);
			}
			if(!go&&!mute.isSelected())
			{
				song=new AePlayWave(songlist.get(sn));
				song.start();
				songn=songlist.get(sn).substring(songlist.get(sn).lastIndexOf("\\")+1,songlist.get(sn).lastIndexOf("."));
				songc.setText(songn);
				songc.setLocation((130-(songn.length()*3)),25);
				goer=false;
			}
			else if(!go)
			{
				song=new AePlayWave(songlist.get(sn));
				song.start();
				song.suspend();
				songn=songlist.get(sn).substring(songlist.get(sn).lastIndexOf("\\")+1,songlist.get(sn).lastIndexOf("."));
				songc.setText(songn);
				songc.setLocation((130-(songn.length()*3)),25);
				goer=false;
			}
	
	
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
				{
					g.drawImage(board[i][j].getImage(),5+j*66,5+i*66,66,66,this);
				}
	
			if(selected)
				g.drawImage(imageList.get(board[selr][selc].getValue()+7),5+selc*66,5+selr*66,66,66,this);
	
			g2.drawImage(buffer, in.left, in.top, this);
			if(!goer)
			playing=true;
		}
	}
}