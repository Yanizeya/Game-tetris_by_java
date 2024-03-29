package tetrominos;

import java.awt.Color;
import java.util.Random;

import screen.TetrisScreen.Gameboard;


public class Operate_Tetrominos {
	Check_OperatingCondition checkOpCondition;
	Gameboard gameboard;
	int sizeOfBlock;
	Random rand = new Random();
	Tetrominos_I tetroI;
	Tetrominos_L tetroL;
	Tetrominos_R tetroR;
	Tetrominos_S tetroS;
	Tetrominos_T tetroT;
	private final int I = 1;
	private final int L = 2;
	private final int R = 3;
	private final int S = 4;
	private final int T = 5;
	int currentBlockType;
	int nextBlockType;
	
	
	public Operate_Tetrominos(Gameboard gameboard) {
		this.gameboard = gameboard;
		sizeOfBlock = gameboard.sizeOfBlock;
		checkOpCondition = new Check_OperatingCondition(gameboard, this);
	}
	
	public void setNullTetrominos(Tetrominos block[][]) {
		for(int i =0; i<20; i++)	{
			for(int j=0; j<10; j++){
				block[i][j] = new Tetrominos();
			}
		}
	}
	public void setCurrentTetrominos (Tetrominos block[][]) {
		System.out.println("setCurrentTetrominos");
		currentBlockType = rand.nextInt(5)+1;
		setTetrominos(block, currentBlockType);
	}
	public void setNextTetrominos (Tetrominos block[][]) {
		System.out.println("setNextTetrominos");
		nextBlockType = rand.nextInt(5)+1;
		setTetrominos(block, nextBlockType);
	}
	private void setTetrominos (Tetrominos block[][], int blockType){
		tetroI = new Tetrominos_I();
		tetroL = new Tetrominos_L();
		tetroR = new Tetrominos_R();
		tetroS = new Tetrominos_S();
		tetroT = new Tetrominos_T();
		Color color;
		int currentRotation;
		gameboard.currentXnum = 2;
		gameboard.currentYnum = 0;
		gameboard.currentRotation = 0;
		currentRotation = gameboard.currentRotation;
		
		switch(rand.nextInt(5)+1) {
		case 1:
			color = Color.YELLOW;
			break;
		case 2:
			color = Color.GRAY;
			break;
		case 3:
			color = Color.GREEN;
			break;
		case 4:
			color = Color.PINK;
			break;
		case 5:
			color = Color.WHITE;
			break;
		default:
			color = Color.DARK_GRAY;
		}
		
		for(int i =0; i<4; i++)	{
			for(int j=0; j<4; j++){
				
				switch(blockType) {
				case I:
					block[i][j] = new Tetrominos_I();
					block[i][j].use = false;
					if(tetroI.block[currentRotation][i][j] == 1) {
						block[i][j].use = true;}
					break;
				case L:
					block[i][j] = new Tetrominos_L();
					block[i][j].use = false;
					if(tetroL.block[currentRotation][i][j] == 1) {
					block[i][j].use = true;}
					break;
				case R:
					block[i][j] = new Tetrominos_R();
					block[i][j].use = false;
					if(tetroR.block[currentRotation][i][j] == 1) {
					block[i][j].use = true;}
					break;
				case S:
					block[i][j] = new Tetrominos_S();
					block[i][j].use = false;
					if(tetroS.block[currentRotation][i][j] == 1) {
						block[i][j].use = true;}
					break;
				case T:
					block[i][j] = new Tetrominos_T();
					block[i][j].use = false;
					if(tetroT.block[currentRotation][i][j] == 1) {
					block[i][j].use = true;}
					break;
				}
				block[i][j].color = color;
			}
		}
		
	}
	
	public void moveDown (Tetrominos block[][]){
		System.out.println("moveDown");
		if(checkOpCondition.moveDown(block))
			gameboard.currentYnum+=1;
		
		
	}
	
	public void moveLeft (Tetrominos block[][]){
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++)
					if(block[j][i].use)	//j와 i를 반대로 사용 -> 왼쪽 위부터 차례대로 조사
						if((gameboard.currentXnum + i) <= 0)
							return;
						else {
							gameboard.currentXnum -= 1;
							return;
						}
	}
	
	public void moveRight (Tetrominos block[][]){
		for(int i=3; i>=0; i--)
			for(int j=0; j<4; j++)
				if(block[j][i].use)	//j와 i를 반대로 사용 -> 오른쪽 위부터 차례대로 조사
					if((gameboard.currentXnum + i) >= gameboard.numOfWidthblock-1) {
						return;
					}
					else {
						System.out.println("curXnum= "+ gameboard.currentXnum + "  i= " + i +"  j= " + j);
						gameboard.currentXnum += 1;
						return;
					}
	}
	
	public void turnBlock (Tetrominos block[][]){
		System.out.println("turnBlock");
		boolean subblock[][] = new boolean[4][4];
		
		if(block[0][0].numOfRotation-1 > gameboard.currentRotation) {
			gameboard.currentRotation += 1;
		}
		else
			gameboard.currentRotation = 0;
		
		System.out.println("blockType = "+currentBlockType);
		System.out.println("currentRotation="+gameboard.currentRotation);
		for(int i =0; i<4; i++)	
			for(int j=0; j<4; j++) {
				subblock[i][j] = false;
				switch(currentBlockType) {
				case I:
					if(tetroI.block[gameboard.currentRotation][i][j] == 1) {
						subblock[i][j] = true;}
					break;
				case L:
					if(tetroL.block[gameboard.currentRotation][i][j] == 1) {
						subblock[i][j] = true;}
					break;
				case R:
					if(tetroR.block[gameboard.currentRotation][i][j] == 1) {
						subblock[i][j] = true;}
					break;
				case S:
					if(tetroS.block[gameboard.currentRotation][i][j] == 1) {
						subblock[i][j] = true;}
					break;
				case T:
					if(tetroT.block[gameboard.currentRotation][i][j] == 1) {
						subblock[i][j] = true;}
					break;
				}
		}
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(subblock[i][j])
					if(j + gameboard.currentXnum >= gameboard.numOfWidthblock)
						return;
					else if(i + gameboard.currentYnum >= gameboard.numOfHeightblock)
						return;
			}
		}
					
		for(int i=0; i<4; i++) 
			for(int j=0; j<4; j++) 
				if(subblock[i][j])
					block[i][j].use = true;
				else
					block[i][j].use = false;
	}
	
	
	
	void changeBlockToStack(Tetrominos block[][]) {
		int higherY=0;
		System.out.println("changeBlockToStack");
		for(int i =3; i>=0; i--)
			for(int j=0; j<4; j++) 
				if(block[i][j].use) {
					higherY = i;
					gameboard.stackblock[gameboard.currentYnum+i][gameboard.currentXnum+j] = block[i][j];
				}
		
		if(checkOpCondition.gameOver(higherY) != true) {
			checkOpCondition.clearLine(higherY);
			
		}
		else
			gameOver();
		
		for(int i=0; i<4;i++)
			for(int j=0; j<4; j++) {
				block[i][j] = gameboard.nextblock[i][j];
			}
		currentBlockType = nextBlockType;     
		setNextTetrominos(gameboard.nextblock);
		
	}
	
	
	
	void gameOver(){
		gameboard.getTetris().timer.gameOver = true;
	}
	void ClearLine(int lineToClear[]) {
		int currentYnum = gameboard.currentYnum;
		int numOfSpace = 0;
		System.out.println("Run clear line method!");
		for(int k = 3; k >= 0 ; k--) {
			
			if(lineToClear[k] == 1) {
				numOfSpace = 1;
				while((k-numOfSpace) >= 0) {
					if(lineToClear[k-numOfSpace] == 1)
						numOfSpace++;
					else
						break;
				}
				System.out.println("Run clear line2!");
				for(int clearLine = currentYnum+k; clearLine-numOfSpace>=0; clearLine--) {
					for(int j = 0; j < gameboard.numOfWidthblock; j++) {
						gameboard.stackblock[clearLine][j] = gameboard.stackblock[clearLine-numOfSpace][j];
					}
				}
			}
		}
	}
}
	
