package com.qait.automation.test;

import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test
public class tatoc {
	WebDriver chrome;
	
	@BeforeClass
	public void set_chrome_driver()
	{
		chrome=new ChromeDriver();
		chrome.get("http://10.0.1.86/tatoc");
		
		
	}
		
	
	public void basiccourse()
	{
		List<WebElement> myelements =chrome.findElements(By.cssSelector("a"));
		myelements.get(0).click();
		List<WebElement> content1= chrome.findElements(By.className("greenbox"));
		Assert.assertTrue(content1.size()>0);
		
	}
	
	@Test(dependsOnMethods= {"basiccourse"})
	public void Click_on_green_block()
	{
		chrome.findElement(By.className("greenbox")).click();
		Assert.assertTrue(chrome.findElement(By.cssSelector("h1")).getText().contains("Frame Dungeon"));
		
	}
	
	@Test(dependsOnMethods= {"Click_on_green_block"})
	public void Match_colour_of_box()
	{
		chrome.switchTo().frame("main");
		WebElement colourclass1=chrome.findElement(By.id("answer")); 
		String resultcolour = colourclass1.getAttribute("class");
		
		int flag=1;
		String comparecolour;
		
		while(flag>0)
			{
			chrome.switchTo().frame("child");
			WebElement colourclass2=chrome.findElement(By.id("answer"));
			comparecolour=colourclass2.getAttribute("class");
			
			
			if(comparecolour.equals(resultcolour))
			{
				chrome.switchTo().defaultContent();
				chrome.switchTo().frame("main");
				
				flag=0;
				chrome.findElement(By.linkText("Proceed")).click();
				break;
			}
			else {
				chrome.switchTo().defaultContent();
				chrome.switchTo().frame("main");
				chrome.findElement(By.linkText("Repaint Box 2")).click();
			} 
			
			}
		Assert.assertTrue(chrome.findElement(By.cssSelector("h1")).getText().contains("Drag Around"));
	}
	
	@Test(dependsOnMethods= {"Match_colour_of_box"})
	public void Dragbox() {
		WebElement dragbox = chrome.findElement(By.id("dragbox"));
		 WebElement dropbox = chrome.findElement(By.id("dropbox"));

		 Actions action = new Actions(chrome);
		 Action dragAndDrop = action.clickAndHold(dragbox).moveToElement(dropbox).release(dragbox).build();
		 dragAndDrop.perform();
		 chrome.findElement(By.linkText("Proceed")).click();
		 Assert.assertTrue(chrome.findElement(By.cssSelector("h1")).getText().contains("Popup Windows"));
	}
	
	@Test(dependsOnMethods= {"Dragbox"})
	public void launchpopupwindow()
	{
		List<WebElement> myele =chrome.findElements(By.cssSelector("a"));
		 myele.get(0).click();
		 
		  String MainWindow=chrome.getWindowHandle();		//main window object
		  Set<String> s1=chrome.getWindowHandles(); //list of strings		
	      Iterator<String> i1=s1.iterator();		
	      		
	      while(i1.hasNext())			
	      {		
	          String ChildWindow=i1.next();		
	          		
	          if(!MainWindow.equalsIgnoreCase(ChildWindow))			
	          {    		
	                  chrome.switchTo().window(ChildWindow);	                                                                                                           
	                  chrome.findElement(By.id("name")).sendKeys("Nikhil");                			
	                  chrome.findElement(By.id("submit")).click();			
	                  		
	          }		
	      }		
	      chrome.switchTo().window(MainWindow);
	      myele.get(1).click();
	      Assert.assertTrue(chrome.findElement(By.cssSelector("h1")).getText().contains("Cookie Handling"));
	}
	@Test(dependsOnMethods= {"launchpopupwindow"})
	public void set_token_to_cookie()
	{
		List<WebElement> tokenanchors =chrome.findElements(By.cssSelector("a"));
	      tokenanchors.get(0).click();
	      String Token=chrome.findElement(By.id("token")).getText().substring(7);
		    System.out.println(Token);
		    Cookie ck = new Cookie("Token", Token);
		    chrome.manage().addCookie(ck);
		    tokenanchors.get(1).click();
		    
		    Assert.assertTrue(chrome.findElement(By.className("finish")).getText().contains("End"));
		   // chrome.quit();
	}

}
