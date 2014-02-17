/**
 Copyright (c) 2012, Kevin Sacro
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, this
  list of conditions and the following disclaimer in the documentation and/or
  other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 THE POSSIBILITY OF SUCH DAMAGE.
**/

package com.turtlesort.hmapgen.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.turtlesort.hmapgen.HeightMapGenerator;

/**
 * Uses hmapgen library to make random maps.
 * 
 * @author Kevin Sacro
 */
public class WorldMapGenerator {

	private HeightMapGenerator g;

	private double[][] terrain;
	private LinkedList<HeightRange> terrainRanges;

	private LinkedList<Box> cities;
	
	private double[][] forest;
	private Color treeLeafColor;
	private Color treeBarkColor;

	/**
	 * Lone constructor.
	 * @param data - A 2D array containing height map data.
	 */
	public WorldMapGenerator(int n){


		g = new HeightMapGenerator();
		
		initTerrain(n);
		initCities();
		initForests();


	}

	private void initTerrain(int n) {

		terrainRanges = new LinkedList<HeightRange>();

		terrainRanges.add(new HeightRange(-Double.MAX_VALUE, 0.1, new Color(0,0,190)));
		terrainRanges.add(new HeightRange(0.1, 0.2, new Color(0,0,200)));
		terrainRanges.add(new HeightRange(0.2, 0.3, new Color(0,0,210)));
		terrainRanges.add(new HeightRange(0.3, 0.4, new Color(0,0,220)));
		terrainRanges.add(new HeightRange(0.4, 0.5, new Color(0,0,230)));
		terrainRanges.add(new HeightRange(0.50, 0.51, new Color(244, 214, 146)));
		terrainRanges.add(new HeightRange(0.51, 0.52, new Color(244, 224, 156)));
		terrainRanges.add(new HeightRange(0.52, 0.60, new Color(0,80,0)));
		terrainRanges.add(new HeightRange(0.6, 0.7, new Color(0,90,0)));
		terrainRanges.add(new HeightRange(0.7, 0.8, new Color(0,100,0)));
		terrainRanges.add(new HeightRange(0.8, 0.9, new Color(0,110,0)));
		terrainRanges.add(new HeightRange(0.9, Double.MAX_VALUE, new Color(10,110,10)));
		
		
		boolean goodTerrain = false;
		
		g.setGenerationSize(n);
		g.setVariance(0.7);
		
		while(!goodTerrain){
			terrain = g.generate();
			
			int greenland = 0;
			for(double[] i : terrain){
				for(double j : i){
					if(j >= 0.52){
						greenland++;
					}
				}
			}
			
			double greenland_p = greenland / (double)(terrain.length * terrain.length);
			
			System.out.println(greenland_p);
			
			if(greenland_p >= 0.5 && greenland_p <= 9){
				goodTerrain = true;
			}
			
		}/**/

		//HeightMapRenderer hmr  = new HeightMapRenderer(terrain);
		//hmr.setTitle("Terrain HM");
		//hmr.show();
		
	}
	
	private void initCities(){
		
		cities = new LinkedList<Box>();
		
		int cityMax = 2;
		int attempts = 0;
		
		while(attempts < 100){

			attempts++;
			
			int x1 = (int)(Math.random() * terrain.length);
			int y1 = (int)(Math.random() * terrain.length);
			
			if(terrain[x1][y1] < 0.52){
				continue;
			}
			
			int x2 = x1 + 10;
			int y2 = y1 + 10;
				
			if(x2 < terrain.length && y2 < terrain.length && terrain[x2][y2] < 0.52){
				continue;
			}
			
			cities.add(new Box(x1,y1,x2,y2));
			
			if(cities.size() == cityMax){
				break;	
			}
				
		}
		
		
	}
	
	private void initForests() {
		
		g.setVariance(2);
		forest = g.generate();

		//HeightMapRenderer hmr  = new HeightMapRenderer(forest);
		//hmr.setTitle("Forest HM");
		//hmr.show();
		
		for(int i = 0; i < forest.length; i++){
			for(int j = 0; j < forest[i].length; j++){

				if(forest[i][j] > 0.5 && terrain[i][j] > 0.59 && terrain[i][j] < 0.95 && !inCity(i,j)){
					forest[i][j] = 1;
				}
				else{
					forest[i][j] = 0;
				}
			}
		}

		treeLeafColor = new Color(25,185,25);
		treeBarkColor = new Color(101,67,33);
	}

	private boolean inCity(int x, int y){
		for(Box b : cities){
			if(b.contains(x, y)){
				return true;
			}
		}
		return false;
	}

	public void show(final double scale){

		final BufferedImage img = new BufferedImage(terrain.length, terrain.length, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)img.getGraphics();

		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain[i].length; j++){

				g.setColor(Color.RED);

				for(HeightRange range : terrainRanges){
					if(range.contains(terrain[i][j])){
						g.setColor(range.color);
					}
				}

				g.drawLine(i, j, i, j);

			}
		}
		
		
		/*
		for(Box b : cities){
			g.setColor(Color.GRAY);
			g.fillRect(b.x1, b.y1, b.x2 - b.x1, b.y2 - b.y1);
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(b.x1, b.y1, b.x2 - b.x1, b.y2 - b.y1);			
		}/**/

		
		for(int i = 0; i < forest.length; i+=3){
			for(int j = 0; j < forest[i].length; j+=3){

				if(forest[i][j] == 1 && (i+j)%2 == 1 && Math.random() > 0.7){

					g.setColor(treeLeafColor);
					g.drawLine(i, j, i, j);
					g.drawLine(i-1, j+1, i-1, j+1);
					g.drawLine(i+1, j+1, i+1, j+1);
					g.setColor(treeBarkColor);
					g.drawLine(i, j+1, i, j+3);

				}

			}
		}/**/
		
		/*
		g.setColor(Color.BLACK);
		for(int i = 0; i < terrain.length; i++){
			for(int j = 0; j < terrain[i].length; j++){

				
				double distance = Math.sqrt(Math.pow(i - terrain.length/2,2) + Math.pow(j - terrain.length/2,2));
				
				if(distance >= terrain.length/2){
					g.drawLine(i,j,i,j);
				}

			}
		}/**/


		/*// uncomment this if you want to ouput the generated world map
		try {
			
			ImageIO.write(img, "png", new File("test.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**/

		
		@SuppressWarnings("serial")
		JPanel panel = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.drawImage(img.getScaledInstance((int)(img.getWidth()*scale), (int)(img.getHeight()*scale), Image.SCALE_FAST), 0, 0, null);
			}
		};

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension((int)(img.getWidth()*scale),(int)(img.getHeight()*scale)));
		frame.add(panel);
		frame.setTitle("World Map Renderer");
		frame.setVisible(true);
		frame.pack();
	}

	public static void main(String[] args){

		WorldMapGenerator wr = new WorldMapGenerator(8);
		wr.show(2);

	}


	private class HeightRange {
		double min;
		double max;
		Color color;

		public HeightRange(double min, double max, Color color){
			this.min = min;
			this.max = max;
			this.color = color;
		}

		public boolean contains(double val){
			return val >= min && val < max;
		}
	}
	
	private class Box {
		int x1;
		int y1;
		int x2;
		int y2;
		
		public Box(int x1, int y1, int x2, int y2){
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		public boolean contains(int x, int y){
			return x >= x1 && x <= x2 && y >= y1 && y <= y2;
		}
		
	}


}
