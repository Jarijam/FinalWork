package com.graphics;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class graphics_test extends Frame{
	int Ptx, Pty; //점의 좌표 ox,oy
	int size = 4; //점의 크기
	
	public graphics_test(String title) {
		super(title);
		setSize(400,400);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					Ptx=e.getX();
					Pty=e.getY();
					System.out.println("x좌표 :" + Ptx + " " + "y좌표" + Pty);
					repaint();
				}
			});
		}
		@Override
		public void update(Graphics g) {
			System.err.println("update 호출");
			paint(g);
		}
		public void paint(Graphics g) {
			System.err.println("paint 호출");
			System.out.println("[그려질좌표] x좌표 :" + Ptx + " " + "y좌표" + Pty);
			g.fillOval(Ptx, Pty, size, size);
		}
		public void up(int x, int y) {
			Ptx = x;
			Pty = y;
			
			repaint();
		}
	
	public static void main(String[] args) {
		graphics_test t = new graphics_test("마우스 포인터에 점찍기");
		
			try {
				Thread.sleep(3000);
				t.up(200, 200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	}

}
