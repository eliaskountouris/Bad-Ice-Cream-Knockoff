/*
 * 
 * Hello Mr Jay!
 * 
 * Hope you like our summative game
 * 
 * If you have errors loading the pictures in from local dirrectories, you can input the directory in directory string
 * 
 * 
 
 */
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import javax.swing.*;
import java.awt.event.*; // Needed for ActionListener
import java.util.Random;

//main method
public class Game extends JFrame implements KeyListener {
	
	//=====================DIRECTORY STRING=================
	//updates all inputs strings for all classes
	public static String directory = "C:\\";
	
	//global variables
	//level counter
	public int n = 1;
	//point counter
	public int p = 0;
	//box size
	public static int b = 28;
	//global booelans
	public boolean gameOver = false;
	public boolean pause = true;
	//image list
	public Image img[] = new Image[18];

	//create levels
	static Level level = new Level(476, 476, b, 1);
	static Level level2 = new Level(476, 476, b, 2);
	static Level level3 = new Level(476, 476, b, 3);
	
//create player
	static Player player = new Player(8, 15, b, level.returnMap());

	//create objects
	static ArrayList<Wall> Walls = new ArrayList<Wall>();
	static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	static ArrayList<Reward> rewards = new ArrayList<Reward>();

	//load images
	private void loadImage() {
		try {
			img[0] = ImageIO.read(new File(directory+"0.png"));
			img[1] = ImageIO.read(new File(directory+"1.png"));
			img[2] = ImageIO.read(new File(directory+"2.png"));
			img[3] = ImageIO.read(new File(directory+"3.png"));
			img[4] = ImageIO.read(new File(directory+"4.png"));
			img[5] = ImageIO.read(new File(directory+"5.png"));
			img[6] = ImageIO.read(new File(directory+"6.png"));
			img[7] = ImageIO.read(new File(directory+"7.png"));
			img[8] = ImageIO.read(new File(directory+"8.png"));
			img[9] = ImageIO.read(new File(directory+"9.png"));
			img[10] = ImageIO.read(new File(directory+"completed.png"));
			img[11] = ImageIO.read(new File(directory+"level21.png"));
			img[12] = ImageIO.read(new File(directory+"level22.png"));
			img[13] = ImageIO.read(new File(directory+"level31.png"));
			img[14] = ImageIO.read(new File(directory+"level32.png"));
			img[15] = ImageIO.read(new File(directory+"level33.png"));
			img[16] = ImageIO.read(new File(directory+"lose.png"));
			img[17] = ImageIO.read(new File(directory+"win.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	//constructor
	public Game() {
		loadImage();
		map = level.returnMap();
		init1();

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		Draw board = new Draw(500, 500);

		add(board);

		pack();
		setTitle("Fat Icecream");
		setSize(650, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		board.addKeyListener(this);
	}
	
	//create map to be used in Game
	static int map[][] = level.returnMap();
	
	//draw, also used for update method
	class Draw extends JPanel {

		public Draw(int width, int height) {
			this.setPreferredSize(new Dimension(width, height)); // size
		}

		public void paintComponent(Graphics g) {
			//checks if the game is going and player not dead
			if (gameOver == false && pause == false) {
				//loads in correct level
				if (n == 1) {
					map = level.returnMap();
					level.draw(g);
					changeMap(map);
				} else if (n == 2) {
					map = level2.returnMap();
					level2.draw(g);
					changeMap(map);
				} else if (n == 3) {
					map = level3.returnMap();
					level3.draw(g);
					changeMap(map);
				}
				//draws juices
				for (int i = 0; i < rewards.size(); i++) {
					rewards.get(i).draw(g);
				}
				//draws players
				player.draw(g);
				//draws walls made by player
				for (int i = 0; i < Walls.size(); i++) {
					Walls.get(i).draw(g, rewards);
					map = Walls.get(i).mapUpdate(map);
				}
				//draws enemies
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).update(g, n);
				}
				//updates "maps"
				if (n == 1) {
					level.mapUpdate(map);
					;
				} else if (n == 2) {
					level2.mapUpdate(map);
				} else if (n == 3) {
					level3.mapUpdate(map);
				}
				//checks if player is dead
				if (!alive()) {
					gameOver = true;
				}
				//checks if levels  1 complete
				if (p == 36) {
					n = 2;
					p = 37;
					init2();
					pause = true;
				//checks if level 2 complete
				} else if (p == 56) {
					n = 3;
					p = 57;
					init3();
					pause = true;
				}
				//checks if level 3 complete
				else if (p == 76) {
					p = 77;
					pause = true;
				}
				//updates points
				point();
			} else {
				//if game paused / dead
				if (gameOver) {
					g.drawImage(img[16], 50, 50, null);
				}
				// does story stuff
				if (p == 0) {
					g.drawImage(img[0], 0, 0, null);
				} else if (p == 1) {
					g.drawImage(img[1], 0, 0, null);
				} else if (p == 2) {
					g.drawImage(img[2], 0, 0, null);
				} else if (p == 3) {
					g.drawImage(img[3], 0, 0, null);
				} else if (p == 4) {
					g.drawImage(img[4], 0, 0, null);
				} else if (p == 5) {
					g.drawImage(img[5], 0, 0, null);
				} else if (p == 6) {
					g.drawImage(img[6], 0, 0, null);
				} else if (p == 7) {
					g.drawImage(img[7], 0, 0, null);
				} else if (p == 8) {
					g.drawImage(img[8], 0, 0, null);
				} else if (p == 9) {
					g.drawImage(img[9], 0, 0, null);
				} else if (p == 10) {
					pause = false;
					p--;
				} else if (p == 37) {
					g.drawImage(img[10], 40, 40, null);
				} else if (p == 38) {
					g.drawImage(img[11], 40, 40, null);
				} else if (p == 39) {
					g.drawImage(img[12], 40, 40, null);
				} else if (p == 40) {
					pause = false;
				} else if (p == 57) {
					g.drawImage(img[10], 40, 40, null);
				} else if (p == 58) {
					g.drawImage(img[14], 40, 40, null);
				} else if (p == 59) {
					g.drawImage(img[15], 40, 40, null);
				} else if (p == 60) {
					pause = false;
				} else if (p == 77) {
					g.drawImage(img[17], 40, 40, null);
				} 
			}
			//updates
			repaint();
		}
	}
	
	//used for changing level
	public void changeMap(int[][] map) {
		player.changeMap(map);
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).changeMap(map);
		}
	}
	
	//main method
	public static void main(String[] args) {
		Game window = new Game();
		window.setVisible(true);
	}

	//key listener
	public void keyTyped(KeyEvent e) {
		// nothing
	}

	public void keyReleased(KeyEvent e) {
		// nothing
	}

	public void keyPressed(KeyEvent e) // handle cursor keys and enter
	{
		//movement
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			player.move(1);
			break;
		case KeyEvent.VK_S:
			player.move(2);
			break;
		case KeyEvent.VK_A:
			player.move(3);
			break;
		case KeyEvent.VK_D:
			player.move(4);
			break;
		//attack key
		case KeyEvent.VK_Q:
			player.attack('W');
			break;
			
		//used for story
		case KeyEvent.VK_X:
			p++;
			break;

		case KeyEvent.VK_ENTER:
			p++;
		}

	}
	// makes lvl 1
	public void init1() {
		Enemy e1 = new Enemy(7, 6, b, map, 1);
		Enemy e2 = new Enemy(9, 6, b, map, 1);
		for (int i = 3; i < 14; i++) {
			Reward j1 = new Reward(2, i, b, 1);
			rewards.add(j1);
		}
		for (int i = 3; i < 14; i++) {
			Reward j1 = new Reward(14, i, b, 1);
			rewards.add(j1);
		}

		Reward j2 = new Reward(2, 1, b, 1);
		Reward j3 = new Reward(2, 15, b, 1);
		Reward j5 = new Reward(14, 1, b, 1);
		Reward j4 = new Reward(14, 15, b, 1);
		Reward j6 = new Reward(8, 8, b, 1);
		enemies.add(e1);
		enemies.add(e2);

		rewards.add(j2);
		rewards.add(j3);
		rewards.add(j4);
		rewards.add(j5);
		rewards.add(j6);
	}
// makes lvl 2
	public void init2() {
		enemies.clear();
		rewards.clear();
		Walls.clear();
		Enemy e4 = new Enemy(7, 7, b, map, 2);
		Enemy e5 = new Enemy(7, 9, b, map, 2);
		Enemy e6 = new Enemy(9, 9, b, map, 2);
		Enemy e7 = new Enemy(9, 7, b, map, 2);
		enemies.add(e5);
		enemies.add(e4);
		enemies.add(e6);
		enemies.add(e7);

		Reward r1 = new Reward(12, 12, b, 2);
		Reward r2 = new Reward(12, 13, b, 2);
		Reward r3 = new Reward(13, 12, b, 2);
		Reward r4 = new Reward(13, 13, b, 2);
		rewards.add(r2);
		rewards.add(r3);
		rewards.add(r4);
		rewards.add(r1);

		Reward r11 = new Reward(3, 3, b, 2);
		Reward r21 = new Reward(3, 4, b, 2);
		Reward r31 = new Reward(4, 3, b, 2);
		Reward r41 = new Reward(4, 4, b, 2);
		rewards.add(r21);
		rewards.add(r31);
		rewards.add(r41);
		rewards.add(r11);

		Reward r12 = new Reward(3, 12, b, 2);
		Reward r22 = new Reward(3, 13, b, 2);
		Reward r32 = new Reward(4, 12, b, 2);
		Reward r42 = new Reward(4, 13, b, 2);
		rewards.add(r22);
		rewards.add(r32);
		rewards.add(r42);
		rewards.add(r12);

		Reward r13 = new Reward(12, 3, b, 2);
		Reward r23 = new Reward(12, 4, b, 2);
		Reward r33 = new Reward(13, 3, b, 2);
		Reward r43 = new Reward(13, 4, b, 2);
		rewards.add(r23);
		rewards.add(r33);
		rewards.add(r43);
		rewards.add(r13);

		player.setPos(8, 15);
	}
// makes lvl 3
	public void init3() {
		enemies.clear();
		rewards.clear();
		Walls.clear();

		Enemy e4 = new Enemy(7, 7, b, map, 3);
		Enemy e5 = new Enemy(7, 9, b, map, 3);
		Enemy e6 = new Enemy(9, 9, b, map, 3);
		Enemy e7 = new Enemy(9, 7, b, map, 3);
		enemies.add(e5);
		enemies.add(e4);
		enemies.add(e6);
		enemies.add(e7);

		Enemy e8 = new Enemy(3, 8, b, map, 3);
		Enemy e9 = new Enemy(13, 8, b, map, 3);
		enemies.add(e5);
		enemies.add(e4);

		Reward r1 = new Reward(1, 1, b, 3);
		Reward r2 = new Reward(1, 15, b, 3);
		Reward r3 = new Reward(15, 15, b, 3);
		Reward r4 = new Reward(15, 1, b, 3);
		rewards.add(r2);
		rewards.add(r3);
		rewards.add(r4);
		rewards.add(r1);

		Reward r12 = new Reward(5, 1, b, 3);
		Reward r22 = new Reward(11, 15, b, 3);
		Reward r32 = new Reward(5, 15, b, 3);
		Reward r42 = new Reward(11, 1, b, 3);
		rewards.add(r22);
		rewards.add(r32);
		rewards.add(r42);
		rewards.add(r12);

		player.setPos(8, 15);
	}

	//used for wall attack
	public static void addWall(int xp, int yp, int b, int dir) {
		//checks direction of player and then builds walls until wall hits something
		if ((dir == 1)) {
			while (map[yp][xp] != -1 && map[yp][xp] != 999) {
				Wall w = new Wall(xp, yp, b);
				Walls.add(w);
				yp--;
			}
		} else if (dir == 2) {
			while (map[yp][xp] != -1 && map[yp][xp] != 999) {
				Wall w = new Wall(xp, yp, b);
				Walls.add(w);
				yp++;
			}
		} else if (dir == 3) {
			while (map[yp][xp] != -1 && map[yp][xp] != 999) {
				Wall w = new Wall(xp, yp, b);
				Walls.add(w);
				xp--;
			}
		} else if (dir == 4) {
			while (map[yp][xp] != -1 && map[yp][xp] != 999) {
				Wall w = new Wall(xp, yp, b);
				Walls.add(w);
				xp++;
			}
		}

	}
	//gives points for player collision with juice
	public void point() {
		int x = player.xp;
		int y = player.yp;
		for (int i = 0; i < rewards.size(); i++) {
			if (rewards.get(i).x == x && rewards.get(i).y == y && rewards.get(i).active) {
				p++;
				rewards.get(i).active = false;
			}
		}
	}

	//checks if playrs dies by colliding with enemies
	public boolean alive() {
		int x = player.xp;
		int y = player.yp;
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).x == x && enemies.get(i).y == y) {
				return false;
			}
		}
		return true;
	}
}

//----------Enemy Class --------------
class Enemy {
	//initlize variables
	public int x, y, b, lvl;
	public int dir = 1;
	public int time = 10;
	public boolean alive = true;
	//images
	public Image img;
	public Random rand = new Random();
	public int[][] map;

	//load images
	private void loadImage() {
		try {
			img = ImageIO.read(new File(Game.directory+"monster.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}

	//constructor
	public Enemy(int x_, int y_, int b_, int[][] mp, int l) {
		x = x_;
		y = y_;
		b = b_;
		lvl = l;
		map = mp;
		loadImage();
	}

	//helper function to update
	public void update(Graphics g, int n) {
		if (n == lvl) {
			move();
			draw(g);
		}
	}

	//draw function if they are in the level
	public void draw(Graphics g) {
		if (alive == true) {
			g.drawImage(img, (x) * b, (y) * b, null);
		}
	}

	//move function if they are alive and in the level
	public void move() {
		//timer to have them move regularly
		if (time == 0) {
			dir = rand.nextInt(4) + 1;
			time = 300;
		} else {
			time--;
			if (time % 20 == 0) {
				if ((dir == 1) && collide(map[y - 1][x - 1])) {
					y -= 1;
				} else if ((dir == 2) && collide(map[y + 1][x])) {
					y += 1;
				} else if ((dir == 3) && collide(map[y][x - 1])) {
					x -= 1;
				} else if ((dir == 4) && collide(map[y][x + 1])) {
					x += 1;
				}
			}
		}
	}
	//helper function to prevent going through walls
	public boolean collide(int x) {
		if (x != 999 && x != -1)
			return true;
		return false;
	}
	
	//helper function to see where they are walk into 
	public boolean collideNext() {
		int c = 0;
		if (dir == 1) {
			c = map[y - 1][x];
		} else if (dir == 2) {
			c = map[y + 1][x];
		} else if (dir == 3) {
			c = map[y][x - 1];
		} else if (dir == 4) {
			c = map[y][x + 1];
		}

		if (c != 999 && c != -1)
			return true;
		return false;
	}

	//helper function to upate the map layout
	public void changeMap(int[][] mp) {
		map = mp;
	}

}

//class for juice
class Reward {
	//variables
	public int x, y, b, lvl, color;
	public boolean active = true;
	private Image img[] = new Image[4];

	//load images
	private void loadImage() {
		try {
			img[0] = ImageIO.read(new File(Game.directory+"juiceBlue.png"));
			img[1] = ImageIO.read(new File(Game.directory+"juiceYellow.png"));
			img[2] = ImageIO.read(new File(Game.directory+"juicePink.png"));
			img[3] = ImageIO.read(new File(Game.directory+"juiceGreen.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}

	//constructor
	public Reward(int x_, int y_, int bs, int l) {
		x = x_;
		y = y_;
		b = bs;
		lvl = l;
		//pick color
		Random rand = new Random();
		color = rand.nextInt(3);
		loadImage();
	}

	//draw
	public void draw(Graphics g) {
		//draw if not picked up by player
		if (active) {
			//randomly select color
			if (color == 0) {
				g.drawImage(img[0], (x) * b, (y) * b, null);
			} else if (color == 1) {
				g.drawImage(img[1], (x) * b, (y) * b, null);
			} else if (color == 2) {
				g.drawImage(img[2], (x) * b, (y) * b, null);
			} else if (color == 3) {
				g.drawImage(img[3], (x) * b, (y) * b, null);
			}
		}
	}
}

//++++++++++++Wall Class+++++++++++
class Wall {
	//variables
	public int x, y, b;
	public int time = 500;
	public boolean active = true;
	public Image img[] = new Image[2];

	//load iamges
	private void loadImage() {
		try {
			img[0] = ImageIO.read(new File(Game.directory+"ice.png"));
			img[1] = ImageIO.read(new File(Game.directory+"iceUnder.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}

	//constructor
	public Wall(int xs, int ys, int bs) {
		x = xs;
		y = ys;
		b = bs;
		loadImage();
	}

	//draw wall and make them disapeer after a while
	public void draw(Graphics g, ArrayList<Reward> juice) {
		if (time > 0 && active == true) {
			for (int i = 0; i < juice.size(); i++) {
				if (juice.get(i).active && juice.get(i).x == x && juice.get(i).y == y) {
					g.drawImage(img[1], (x) * b, (y) * b, null);
				} else {
					g.drawImage(img[0], (x) * b, (y) * b, null);
				}
			}

			time--;
		} else {
			active = false;
		}
	}

	//update where you can walk
	public int[][] mapUpdate(int[][] map) {
		if (active) {
			map[y][x] = -1;
		} else
			map[y][x] = 0;
		return map;
	}

}

//+++++++++++ Player +++++++++++++++++++
class Player {
	//variables
	public int xp, yp;
	public int b, dir;
	private Image img[] = new Image[4];
	public int map[][];

	//change position, helpr function for init()
	public void setPos(int x, int y) {
		xp = x;
		yp = y;
	}

	//change where the player can walk
	public void changeMap(int[][] mp) {
		map = mp;
	}

	//load images
	private void loadImage() {
		try {
			img[0] = ImageIO.read(new File(Game.directory+"front.png"));
			img[1] = ImageIO.read(new File(Game.directory+"back.png"));
			img[2] = ImageIO.read(new File(Game.directory+"left.png"));
			img[3] = ImageIO.read(new File(Game.directory+"right.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}

	//constructor
	public Player(int x, int y, int bs, int[][] mp) {
		xp = x;
		yp = y;
		b = bs;
		dir = 1;
		map = mp;

		loadImage();
	}

	//draw function
	public void draw(Graphics g) {
		//change image depending on direction
		if (dir == 1) {
			g.drawImage(img[1], (xp) * b, (yp) * b, null);
		} else if (dir == 2) {
			g.drawImage(img[0], (xp) * b, (yp) * b, null);
		} else if (dir == 3) {
			g.drawImage(img[2], (xp) * b, (yp) * b, null);
		} else if (dir == 4) {
			g.drawImage(img[3], (xp) * b, (yp) * b, null);
		}
	}

	//check colisions, helper function to guide movement
	public boolean collide(int x) {
		if (x != 999 && x != -1)
			return true;
		return false;
	}

	//move function, called from key listener, updates player position
	public void move(int d) {
		//direction is saved for rest of class
		dir = d;
		if ((d == 1) && collide(map[yp - 1][xp])) {
			yp -= 1;
		} else if ((d == 2) && collide(map[yp + 1][xp])) {
			yp += 1;
		} else if ((d == 3) && collide(map[yp][xp - 1])) {
			xp -= 1;
		} else if ((d == 4) && collide(map[yp][xp + 1])) {
			xp += 1;
		}
	}

	//attack method
	public void attack(char w) {
		//attacks depending on direction and if thre is object in front
		if (w == 'W') {
			if ((dir == 1) && collide(map[yp - 1][xp])) {
				Game.addWall(xp, yp - 1, b, dir);
			} else if ((dir == 2) && collide(map[yp + 1][xp])) {
				Game.addWall(xp, yp + 1, b, dir);
			} else if ((dir == 3) && collide(map[xp][xp - 1])) {
				Game.addWall(xp - 1, yp, b, dir);
			} else if ((dir == 4) && collide(map[yp][xp + 1])) {
				Game.addWall(xp + 1, yp, b, dir);
			}
		}
	}

}

//============== Level =================
class Level {
	//variables
	public int map[][];
	public int w, h, b;
	private Image image[] = new Image[2];
	//load iamges
	private void loadImages() {
		try {
			image[0] = ImageIO.read(new File(Game.directory+"ice.png"));
			image[1] = ImageIO.read(new File(Game.directory+"floor.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	//constructor
	public Level(int width, int height, int bs, int n) {
		w = width;
		h = height;
		b = bs;
		//initialize levels
		map = new int[w / b][h / b];
		init(n);
		
		loadImages();
	}

	//initialize levels
	public void init(int n) {
		//creates map border for every level
		for (int i = 0; i < w / b; i++) {
			for (int j = 0; j < h / b; j++) {
				if (i == 0 || i == ((w / b) - 1) || j == 0 || j == h / b - 1) {
					map[i][j] = 999;
				} else {
					map[i][j] = 0;
				}
			}
		}
		//level one walls 
		if (n == 1) {
			for (int i = 3; i < 14; i++) {
				map[i][3] = 999;
			}
			for (int i = 3; i < 14; i++) {
				map[i][13] = 999;
			}
			map[3][4] = 999;
			map[3][5] = 999;
			map[3][11] = 999;
			map[3][12] = 999;
			map[13][4] = 999;
			map[13][5] = 999;
			map[13][11] = 999;
			map[13][12] = 999;
		}
		//level two walls
		if (n == 2) {
			for (int i = 3; i < 14; i++) {
				map[i][8] = 999;
				map[8][i] = 999;
			}
		}
		//level 3 walls
		if (n == 3) {
			for (int i = 2; i < 15; i += 2) {
				for (int j = 2; j < 15; j += 2) {
					if (j != 8) {
						map[i][j] = 999;
					}
				}
			}
			for (int i = 0; i < 16; i++) {
				if (i != 8) {
					map[i][6] = 999;
					map[i][10] = 999;
				} else {
					map[i][6] = 0;
					map[i][10] = 0;
				}
			}
		}
	}

	//draw method
	public void draw(Graphics g) {
		//loop through all parts of map
		for (int i = 0; i < w / b; i++) {
			for (int j = 0; j < h / b; j++) {
				if (map[i][j] == 999) {
					g.drawImage(image[0], (j) * b, (i) * b, null);
				} else if ((map[i][j] == 0 || map[i][j] == -1)) {
					//draws ground texture
					g.drawImage(image[1], (j) * b, (i) * b, null);
				}

			}
		}
	}

	//returns map, helper function
	public int[][] returnMap() {
		return map;
	}

	//changes map, used for wall attack
	public void mapUpdate(int[][] mp) {
		map = mp;
	}
}
