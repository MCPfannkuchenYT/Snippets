package de.pfannekuchen.snippets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.simplenativehooks.NativeHookInitializer;
import org.simplenativehooks.NativeKeyHook;
import org.simplenativehooks.events.NativeKeyEvent;
import org.simplenativehooks.staticResources.BootStrapResources;
import org.simplenativehooks.utilities.Function;

import sun.java2d.SunGraphics2D;

public class Snippets {

	private static final File snippetsDir = new File(System.getenv("AppData") + File.separator + "snippets");
	private static File[] snippets;
	
	private static boolean isWindowsDone = false;
	private static boolean isFDone = false;
	
	/**
	 * Starts the Snippet Daemon.
	 * @param args Program Arguments, not used by code.
	 */
	public static void main(String[] args) {
		loadFiles(); // Load the Snippets
		loadHotkeys(); // Load the Global Hotkeys
		
		while (true) {
			// Fall into endless Loop
		}
	}
	
	/**
	 * Load the snippets or create the root file if non-existant
	 */
	private static void loadFiles() {
		if (!snippetsDir.exists()) snippetsDir.mkdir(); // Check whether the Snippet Directory exists.
		snippets = snippetsDir.listFiles(); // Copy Files into snippets array.
	}
	
	/**
	 * Register Global Hotkeys
	 */
	private static void loadHotkeys() {
		/* Extracting resources */
		try {
			BootStrapResources.extractResources();
		} catch (IOException e) {
			System.out.println("Cannot extract bootstrap resources.");
		}
		
		/* Initializing global hooks */
		NativeHookInitializer.of().start();

		/* Set up callbacks */
		NativeKeyHook key = NativeKeyHook.of();
		key.setKeyPressed(new Function<NativeKeyEvent, Boolean>() {
			@Override
			public Boolean apply(NativeKeyEvent d) {
				switch (d.getKey()) {
					case 524:
						isWindowsDone = true; // When Windows key is pressed, set boolean to true
						break;
					case 70:
						isFDone = true; // When F key is pressed, set boolean to true
						break;
				}
				if (isWindowsDone && isFDone) {
					showGui();
					isWindowsDone = false;
					isFDone = false;
				}
				return true;
			}
		});
		key.setKeyReleased(new Function<NativeKeyEvent, Boolean>() {
			@Override
			public Boolean apply(NativeKeyEvent d) {
				switch (d.getKey()) {
					case 524:
						isWindowsDone = false; // When Windows key is unpressed, set boolean to false
						break;
					case 70:
						isFDone = false; // When F key is unpressed, set boolean to false
						break;
				}
				return true;
			}
		});
		key.startListening();
	}
	
	private static volatile boolean isHoveringOverX = false;
	
	private static void showGui() {
		
		
		JFrame jframe = new JFrame() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				// Enable Antialiasing
				((SunGraphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				
				g.setColor(new Color(0f, 0f, 0f, 1f)); 
				g.fillRect(0, 0, 600, 25); // Draw Background
				
				if (isHoveringOverX) {
					g.setColor(new Color(0.5f, 0.5f, 0.5f, .25f)); 
					g.fillRect(570, 0, 600, 25);
				}
				g.drawImage(new ImageIcon("crosshair.png").getImage(), 578, 6, (ImageObserver) null); // Draw X 
				
				Font bahnschriftFont = new Font("Bahnschrift", Font.BOLD, 14);
				Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
				attributes.put(TextAttribute.TRACKING, -.1f); // Create Font
				
				g.setFont(bahnschriftFont.deriveFont(attributes));
				g.setColor(new Color(1f, 1f, 1f, 1f));
				g.drawString("Code Snippets", 10, 16); // Draw Title
			}
		}; // Create new Canvas
		jframe.setUndecorated(true); // Remove Title Bar
		jframe.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				boolean hover = e.getX() > 575 && e.getY() < 25;
				if (hover != isHoveringOverX) {
					isHoveringOverX = hover;
					jframe.repaint();
				}
				isHoveringOverX = hover;
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		}); // Add Move Listener
		jframe.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getX() > 575 && e.getY() < 25) {
					jframe.dispose();
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		}); // Add Mouse Listener
		
		jframe.setBackground(new Color(.1f, .1f, .1f, 0.99f)); // Set Background transparent
		jframe.setSize(600, 400); // Set Size
		
		jframe.setLocation(MouseInfo.getPointerInfo().getLocation()); // Move to Mouse
		
		jframe.setAlwaysOnTop(true); // Set to Top
		jframe.requestFocus(); // Request Focus
		jframe.setVisible(true); // Make Visible
	}
	
}
