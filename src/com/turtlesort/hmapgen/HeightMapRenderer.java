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

package com.turtlesort.hmapgen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Renders height map data in a window.
 * @author Kevin Sacro
 */
public class HeightMapRenderer {

	private String title;
	private double[][] map;
	private double scale;
	private int roughness;
	
	/**
	 * @param data - A 2D array containing height map data.
	 */
	public HeightMapRenderer(double[][] data){
		
		title = "Height Map Renderer";
		map = data;
		scale = 1;
		roughness = 1;
		
	}
	
	/**
	 * @param scale - Scale of image when displaying it in a window
	 * after show() is called. By default this is 1. Specifying
	 * 2 will double the image size.
	 */
	public void setScale(double scale){
		this.scale = scale;
	}
	
	/**
	 * @param roughness - The higher this number, the more distinct 'levels'
	 * there will be in the render. By default this value is 1, which adds
	 * no roughness to the render.
	 */
	public void setRoughness(int roughness){
		this.roughness = roughness;
	}
	
	/**
	 * @param s - The desired title of the window that is
	 * shown when show() is called.
	 */
	public void setTitle(String s){
		title = s;
	}
	
	/**
	 * Gets a buffered image of the map data.
	 * @return A buffered image of the map data.
	 */
	public BufferedImage render(){
		
		BufferedImage img = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();

		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){

				int val = (int)(map[i][j] * 256);
	
				val = val/roughness * roughness;
				
				if(val > 255) val = 255;
				if(val < 0) val = 0;
				
				g.setColor(new Color(val,val,val));

				g.drawLine(i, j, i, j);

			}
		}
		return img;
		
	}

	/**
	 * Renders the map and displays it in a window.
	 */
	public void show(){

		final BufferedImage img = render();

		final int width = (int)(img.getWidth()*scale);
		final int height = (int)(img.getHeight()*scale);
		
		@SuppressWarnings("serial")
		JPanel panel = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 10, 10, null);
			}
		};

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(width+35,height+55));
		frame.add(panel);
		frame.setTitle(title);
		frame.setVisible(true);
		frame.pack();
	}
	
}
