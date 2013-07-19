package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Servlet implementation class CaptchaServlet
 */
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaptchaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    // Captcha code
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	int width = 200;
    	int height = 50;

    	char data[][] = {
    			{ 'z', 'e', 't', 'c', 'o', 'd', 'e' },
    			{ 'l', 'i', 'n', 'u', 'x' },
    			{ 'f', 'r', 'e', 'e', 'b', 's', 'd' },
    			{ 'u', 'b', 'u', 'n', 't', 'u' },
    			{ 'j', 'e', 'e' },
    			{ 'e', 'c', 'l', 'i', 'p', 's', 'e' },
    			{ 'p', 'y', 't', 'h', 'o', 'n' },
    			{ 'a', 'p', 'p', 'l', 'e' },
    			{ 's', 'h', 'e', 'l', 'l' },
    			{ 'a', 's', 'p', 'n', 'e', 't' },
    			// 10
    			{ 'f', 'o', 'o', 'l' },
    			{ 'h', 'e', 'r', 'm', 'i', 't' },
    			{ 'j', 'u', 's', 't', 'i', 'c', 'e' },
    			{ 'h', 'a', 'n', 'g', 'e', 'd' },
    			{ 'd', 'e', 'a', 't', 'h' },
    			{ 'd', 'e', 'v', 'i', 'l' },
    			{ 't', 'o', 'w', 'e', 'r' },
    			{ 's', 't', 'a', 'r' },
    			{ 'm', 'o', 'o', 'n' },
    			{ 's', 'u', 'n' },
    			// 20
    			{ 's', 'i', 'l', 'v', 'e', 'r' },
    			{ 'a', 'm', 'a', 'r', 'a', 't', 'h' },
    			{ 'c', 'y', 'a', 'n' },
    			{ 'g', 'o', 'l', 'd' },
    			{ 'l', 'i', 'm', 'e' },
    			{ 'c', 'o', 'b', 'a', 'l', 't' },
    			{ 'o', 'r', 'a', 'n', 'g', 'e' },
    			{ 'i', 'n', 'd', 'i', 'g', 'o' },
    			{ 'c', 'r', 'i', 'm', 's', 'o', 'n' },
    			{ 'b', 'l', 'a', 'c', 'k' },
    			// 30
    			{ 'd', 'i', 'a', 'm', 'o', 'n', 'd' },
    			{ 'r', 'u', 'b', 'y' },
    			{ 's', 'a', 'p', 'p', 'h', 'i', 'r', 'e' },
    			{ 'e', 'm', 'e', 'r', 'a', 'l', 'd' },
    			{ 't', 'o', 'p', 'a', 'z' },
    			{ 'o', 'p', 'a', 'l' },
    			{ 'z', 'i', 'r', 'c', 'o', 'n' },
    			{ 'a', 'm', 'e', 't', 'h', 'y', 's', 't' },
    			{ 's', 'p', 'i', 'n', 'e', 'l' },
    			{ 'p', 'e', 'r', 'i', 'd', 'o', 't' }
    			// 40
    			};

    	BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    	Graphics2D g2d = bufferedImage.createGraphics();

    	Font font = new Font("Georgia", Font.BOLD, 18);
    	g2d.setFont(font);

    	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    	g2d.setRenderingHints(rh);

    	GradientPaint gp = new GradientPaint(0, 0, Color.cyan, 0, height/2, Color.black, true);

    	g2d.setPaint(gp);
    	g2d.fillRect(0, 0, width, height);

    	g2d.setColor(new Color(255, 153, 0));

    	Random r = new Random();
    	int index = Math.abs(r.nextInt()) % 40;

    	String captcha = String.copyValueOf(data[index]);
    	request.getSession().setAttribute("captcha", captcha );

    	int x = 0; 
    	int y = 0;

    	for (int i=0; i<data[index].length; i++) {
    		x += 10 + (Math.abs(r.nextInt()) % 15);
    		y = 20 + Math.abs(r.nextInt()) % 20;
    		g2d.drawChars(data[index], i, 1, x, y);
    	}

    	g2d.dispose();

    	response.setContentType("image/png");
    	OutputStream os = response.getOutputStream();
    	ImageIO.write(bufferedImage, "png", os);
    	os.close();
    	}
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
