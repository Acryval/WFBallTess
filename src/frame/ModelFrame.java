package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import frame.math.Vec2;
import frame.math.Vec3;
import frame.math.Vec4;

@SuppressWarnings("serial")
public class ModelFrame extends JFrame
{
	private static final String TITLE = "Model Frame";
	
	public ModelFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new Content());
		
		//setResizable(false);
		pack();
		
		setLocationRelativeTo(null);
		setTitle(TITLE);
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				ModelFrame frame = new ModelFrame();
				frame.setVisible(true);
			}
		});
	}
}

@SuppressWarnings("serial")
class Content extends JPanel implements Runnable, MouseMotionListener, MouseListener
{
	private Thread thread;
	private int width, height;
	private long lastTime;
	private boolean running, pause, press;
	private double FPS;
	private InputMap im;
	private ActionMap am;
	@SuppressWarnings("unused")
	private Random rand;
	private Vec2 off, dm;
	
	public Content()
	{
		width = 800;
		height = 600;
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
		
		im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		am = getActionMap();
		
		loadActions();
		initValues();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		draw(g2d);
		
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
		g.dispose();
	}
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		
		if(thread == null)
		{
			thread = new Thread(this);
			addMouseMotionListener(this);
			addMouseListener(this);
			thread.start();
		}
	}
	
	@Override
	public void run()
	{
		long start, elapsed, wait;
		
		while(running)
		{
			start = System.nanoTime();
			
			width = getWidth();
			height = getHeight();
			
			if(lw != width || lh != height)
			{
				lw = width;
				lh = height;
				cam.updateProjection(width, height, fov, near, far, clipd);
			}
			
			update((double)(start - lastTime) / 1000000000);
			lastTime = start;
			repaint();
			
			elapsed = System.nanoTime() - start;
			
			wait = (long) (1000 / FPS - elapsed / 1000000);
			if(wait < 0)
			{
				wait = 0 - wait;
				System.out.println("Frame slowed by: " + wait + " ms, eqivalent to " + (wait * FPS / 1000) + " frames at " + FPS + " fps");
				wait = 0;
			}
			
			try
			{
				Thread.sleep(wait);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		System.exit(0);
	}
	
	
	//Here add your variables
	Entity ent;
	Ball ball;
	Camera cam;
	Vec4[] points;
	Vec3 dpos;
	int[][] lines;
	double da, v, fov, near, far, clipd;
	int lw, lh;
	boolean[] move;
	
	public void initValues()
	{
		FPS = 60;
		lastTime = System.nanoTime();
		running = true;
		rand = new Random();
		off = new Vec2();
		dm = new Vec2();
		press = false;
		pause = false;
		lw = 0;
		lh = 0;
		move = new boolean[] {false, false, false, false, false, false};
		
		fov = Math.PI/2;
		near = 0.01;
		far = 100;
		clipd = 0;
		
		cam = Camera.getProjectionCamera(width, height, fov, near, far, clipd);
		
		points = new Vec4[] {new Vec4(-0.25, -0.25, -0.25), new Vec4( 0.25, -0.25, -0.25),
							 new Vec4( 0.25, -0.25,  0.25), new Vec4(-0.25, -0.25,  0.25),
							 new Vec4(-0.25,  0.25, -0.25), new Vec4( 0.25,  0.25, -0.25),
							 new Vec4( 0.25,  0.25,  0.25), new Vec4(-0.25,  0.25,  0.25)};
		
		lines = new int[][] {{0, 1}, {1, 2}, {2, 3}, {3, 0},
							 {4, 5}, {5, 6}, {6, 7}, {7, 4},
							 {0, 4}, {1, 5}, {2, 6}, {3, 7}};
		
		//ent = new Entity(new Vec3(0, 0, -1), new Model3d(points, lines));
		ball = new Ball(new Vec3(0, 0, -1), 35, 1);
		dpos = new Vec3();
		da = Math.PI/2;
		v = 5;
	}
	
	public void loadActions()
	{
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.SHIFT_DOWN_MASK), "exit");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK), "reload");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
		
		im.put(KeyStroke.getKeyStroke("W"), "move_front");
		im.put(KeyStroke.getKeyStroke("S"), "move_back");
		im.put(KeyStroke.getKeyStroke("A"), "move_left");
		im.put(KeyStroke.getKeyStroke("D"), "move_right");
		im.put(KeyStroke.getKeyStroke("SPACE"), "move_up");
		im.put(KeyStroke.getKeyStroke("shift SHIFT"), "move_down");
		
		im.put(KeyStroke.getKeyStroke("released W"), "move_front_stop");
		im.put(KeyStroke.getKeyStroke("released S"), "move_back_stop");
		im.put(KeyStroke.getKeyStroke("released A"), "move_left_stop");
		im.put(KeyStroke.getKeyStroke("released D"), "move_right_stop");
		im.put(KeyStroke.getKeyStroke("released SPACE"), "move_up_stop");
		im.put(KeyStroke.getKeyStroke("shift released W"), "move_front_stop");
		im.put(KeyStroke.getKeyStroke("shift released S"), "move_back_stop");
		im.put(KeyStroke.getKeyStroke("shift released A"), "move_left_stop");
		im.put(KeyStroke.getKeyStroke("shift released D"), "move_right_stop");
		im.put(KeyStroke.getKeyStroke("shift released SPACE"), "move_up_stop");
		im.put(KeyStroke.getKeyStroke("released SHIFT"), "move_down_stop");
		
		am.put("exit", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{running = false;}});
		am.put("reload", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{initValues();}});
		am.put("pause", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{pause = !pause;}});
		
		am.put("move_front", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[0] = true;}});
		am.put("move_back", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[1] = true;}});
		am.put("move_right", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[2] = true;}});
		am.put("move_left", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[3] = true;}});
		am.put("move_up", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[4] = true;}});
		am.put("move_down", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[5] = true;}});
		
		am.put("move_front_stop", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[0] = false;}});
		am.put("move_back_stop", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[1] = false;}});
		am.put("move_right_stop", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[2] = false;}});
		am.put("move_left_stop", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[3] = false;}});
		am.put("move_up_stop", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[4] = false;}});
		am.put("move_down_stop", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{move[5] = false;}});
	}
	
	public void update(double dt)
	{
		if(pause)
			return;
		
		//ent.rotate(new Vec3(da*dt/20, da*dt/10, da*dt/4));
		
		if(move[0])
			dpos.add(cam.front());
		if(move[1])
			dpos.sub(cam.front());
		
		if(move[2])
			dpos.sub(cam.right());
		if(move[3])
			dpos.add(cam.right());
		
		if(move[4])
			dpos.add(new Vec3(0, 1, 0));
		if(move[5])
			dpos.sub(new Vec3(0, 1, 0));
		
		off.mul(da);
		
		cam.move(dpos.mul(v*dt));
		cam.rotate(new Vec3(0, 1, 0), off.x);
		cam.rotate(cam.right(), off.y);
		
		dpos.zero();
		off.zero();
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 11));
		cam.readyForDrawing();
		ball.draw(g, cam);
		//ent.draw(g, cam);
	}
	
	@Override
	public void mouseMoved(MouseEvent e){}
	
	@Override
	public void mousePressed(MouseEvent e)
	{press = true;}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		Vec2 p = new Vec2(-(double)e.getX()/height, -(double)e.getY()/height);
		if(press)
		{
			dm.set(p);
			press = false;
		}
		off.add(p).sub(dm);
		dm.set(p);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}