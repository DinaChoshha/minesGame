package minesGame;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Mines {
	private int height;
	private int width;
	private Square [][]squares;
	private boolean show;
	
	public Mines(int height,int width,int minesNum)
	//constructor
	{
		this.height=height;
		this.width=width;
		this.show=false;
		if(minesNum>height*width)
		{
			minesNum=height*width;
		}
		squares= new Square[height][width];
		Random rand = new Random();
		int i,j,index;
		for(i=0;i<height;i++)
		{
			for(j=0;j<width;j++)
			{
				squares[i][j]=new Square(i,j);
			}
		}
		for(index=0;index<minesNum;index++)
		{
			i=rand.nextInt(height);
			j=rand.nextInt(width);
			if(squares[i][j].isMine())
			{
				index--;
			}
			else
			{
				squares[i][j].mine();
			}
		}
		
	}
	private class Square
	{
		private int i;
		private int j;
		private boolean mine;
		private boolean opened;
		private boolean flag;
		
		public Square(int i,int j)
		//Constructor
		{
			this.i=i;
			this.j=j;
			this.mine=false;
			this.opened=false;
			this.flag=false;
		}
		
		public boolean isMine()
		//check if square is mine
		{
			return this.mine;
		}
		
		public void mine()
		//turn square to mine
		{
			this.mine=!this.mine;
		}
		
		public boolean isOpened()
		//check if square was opened
		{
			return this.opened;
		}
		
		public void open()
		//open square
		{
			this.opened=true;
		}
		
		public boolean isFlag()
		//check if square is flag
		{
			return this.flag;
		}
		
		public void flag()
		//turn square to flag
		{
			this.flag=!this.flag;
		}
		
		public int getI()
		//return i value
		{
			return this.i;
		}
		
		public int getJ()
		//return j value
		{
			return this.j;
		}
		
		public List<Square> getNeighbors()
		//get square neighbors
		{
			List<Square> neighbors = new LinkedList<Square>();
			if(i<height-1)
			{
				neighbors.add(squares[i+1][j]);
			}
			if(j<width-1)
			{
				neighbors.add(squares[i][j+1]);
			}
			if(i>0)
			{
				neighbors.add(squares[i-1][j]);
			}
			if(j>0)
			{
				neighbors.add(squares[i][j-1]);
			}
			if(i<height-1 && j<width-1)
			{
				neighbors.add(squares[i+1][j+1]);
			}
			if(i>0 && j>0)
			{
				neighbors.add(squares[i-1][j-1]);
			}
			if(i<height-1 &&j>0)
			{
				neighbors.add(squares[i+1][j-1]);
			}
			if( i>0 && j<width-1 )
			{
				neighbors.add(squares[i-1][j+1]);
			}
			return neighbors;
		}
	}
	
	public boolean addMine(int i, int j)
	//add new mine in position i,j
	{
		squares[i][j].mine();
		return squares[i][j].isMine();	
	}
	
	public boolean  open(int i,int j)
	//open mine in position i,j
	{
		if(squares[i][j].isMine())
		{
			return false;
		}
		if(squares[i][j].isOpened())
		{
			return true;
		}
		squares[i][j].open();
		boolean flag=true;
		List<Square>neighbors = squares[i][j].getNeighbors();
		for(Square sq: neighbors)
		{
			if(sq.isMine())
			{
				flag=false;
				break;
			}
		}
			if(flag)
			{
				neighbors=squares[i][j].getNeighbors();
				for(Square sq : neighbors)
				{
					open(sq.getI(),sq.getJ());
				}
			}
		
		return true;
	}
	
	public void toggleFlag(int x,int y)
	//add flag in position i,j
	{
		squares[x][y].flag();
	}
	
	public boolean isDone() 
	//check if game is done
	{

		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				if(!squares[i][j].isMine() && !squares[i][j].isOpened())
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public String get(int i,int j)
	//get squares info as string
	{
		int k=0;
		String s="";
		if(!squares[i][j].isOpened() && !show)
		{
			if(squares[i][j].isFlag())
			{
				s="F";
			}
			else
			{
				s=".";
			}
		}
		else
		{
			if(squares[i][j].isMine())
			{
				s="X";
			}
			else
			{
				List<Square> neighbors= squares[i][j].getNeighbors();
				for(Square sq : neighbors)
				{
					k+=(sq.isMine())?1:0;
				}
				if(k==0)
				{
					s=" ";
				}
				else
				{
					s=""+k;
				}
			}
		}
		return s;
	}
	
	public void setShowAll(boolean show)
	//set show value
	{
		this.show=show;
	}
	
	public boolean winner()
	//check if player won
	{
		return isDone();
	}
	
	public int getHeight()
	//return height
	{
		return height;
	}
	
	public int getWidth()
	//return width
	{
		return width;
	}
	
	public String toString()
	//return info as string
	{
		String s="";
	
	for(int i=0;i<height;i++)
	{
		for(int j=0;j<width;j++)
		{
			s+=get(i,j);
		}
		s+="\n";
	}
	return s;
	}
	
	
}

