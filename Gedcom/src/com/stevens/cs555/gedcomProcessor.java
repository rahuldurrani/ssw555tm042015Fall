/**
* The gedcomProcessor class implements a class that can read, parse 
* and validate GEDCOM data.
*
* @author  Durrani Rahul
* @version 1.0
* @since   2015-09-14 
* @course  CS555
*/

package com.stevens.cs555;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

public class gedcomProcessor {

	private static gedcomData line = new gedcomData();
	private static ArrayList <gedcomData> file = new ArrayList <gedcomData>();
	public static gedcomData parseline(String currentline)
	{
		gedcomData line= new gedcomData();
		String arg=new String();
		String [] splitted=  currentline.split("\\s+"); 
		
		line.setLevel(splitted[0]);
		if(splitted[0].equalsIgnoreCase("0"))
		{
			if (splitted[1].startsWith("@"))
			{
				line.setTag(splitted[2]);
				line.setXrefid(splitted[1]);
				for(int i=3;i<splitted.length;i++)
				{
					arg = arg+splitted[i]+" ";
				}
			}
			else
			{
				line.setTag(splitted[1]);
				line.setXrefid("");
				for(int i=2;i<splitted.length;i++)
				{
					arg = arg+splitted[i]+" ";
				}
			}
		}else
		{
			line.setTag(splitted[1]);
			line.setXrefid("");
			for(int i=2;i<splitted.length;i++)
			{
				arg = arg+splitted[i]+" ";
			}
		}
		line.setArgument(arg);
		
		return line;
	}
	public static ArrayList<Family> saveFamilyData(String path)
	{
		Family f = new Family();
		ArrayList<Family> a = new ArrayList<Family>();
		gedcomData tline,gline = new gedcomData();
		String tLine,line;
		BufferedReader br= null;
		try {
			br = new BufferedReader(new FileReader(path));
			while((tLine = br.readLine()) != null)
			{
				tline= parseline(tLine);
				if(tline.getLevel().equalsIgnoreCase("0")&& tline.getTag().equalsIgnoreCase("FAM"))
				{
					f=new Family();
					f.setId(tline.getXrefid());
					while((line=br.readLine())!=null)
					{
						gline = parseline(line);
							if(gline.getTag().equalsIgnoreCase("HUSB"))
							{
								f.setHusband(gline.getArgument());
							}
							if(gline.getTag().equalsIgnoreCase("WIFE"))
							{
								f.setWife(gline.getArgument());
								break;
							}
					}
					a.add(f);
					}
					
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
	}
	
	public static Hashtable <String,String> saveIndiData(String path)
	{
		Hashtable <String,String> ind = new Hashtable<String,String>();
		gedcomData tline,gline = new gedcomData();
		String tLine,line;
		String id = new String();
		String name = new String();
		BufferedReader br= null;
		try {
			br = new BufferedReader(new FileReader(path));
			while((tLine = br.readLine()) != null)
			{
				tline= parseline(tLine);
				if(tline.getLevel().equalsIgnoreCase("0")&& tline.getTag().equalsIgnoreCase("INDI"))
				{
					id=tline.getXrefid();
					while((line=br.readLine())!=null)
					{
						gline = parseline(line);
							if(gline.getTag().equalsIgnoreCase("NAME"))
							{
								name=gline.getArgument();
								break;
							}
					}
					ind.put(id, name);
					}
					
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ind;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hashtable <String,String> tags = new Hashtable<String,String>();
		ArrayList<Family> family = new  ArrayList<Family>();
       
		tags.put("INDI", "0");
		tags.put("NAME", "1");
		tags.put("SEX", "2");
		tags.put("BIRT", "3");
		tags.put("DEAT", "4");
		tags.put("FAMC", "5");
		tags.put("FAMS", "6");
		tags.put("FAM", "7");
		tags.put("MARR", "8");
		tags.put("HUSB", "9");
		tags.put("WIFE", "10");
		tags.put("CHIL", "11");
		tags.put("DIV", "12");
		tags.put("DATE", "13");
		tags.put("HEAD", "14");
		tags.put("TRLR", "15");
		tags.put("NOTE", "16");
		
		Hashtable <String,String> Ind = new Hashtable<String,String>();
		

		BufferedReader br= null;
		try
		{
			String sCurrentLine;

			br = new BufferedReader(new FileReader(args[0]));
			while ((sCurrentLine = br.readLine()) != null) {
				line=parseline(sCurrentLine);
				
				//System.out.println("---------------------------------------------");
				//System.out.println(line.getLevel()+" "+line.getXrefid()+" "+line.getTag()+" "+line.getArgument());
				//System.out.println("Level: "+ line.getLevel());
				
				if(tags.containsKey(line.getTag()))
				{
					//System.out.println("Tag: "+ line.getTag());
				}
				else
				{
					//System.out.println("Tag: Invalid");
				}
				
				file.add(line);
				
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			} 
	}
		family = saveFamilyData(args[0]);
		Ind=saveIndiData(args[0]);
		Enumeration items = Ind.keys();

		// Now iterate
		while(items.hasMoreElements()){
			String key = (String)items.nextElement();
		     System.out.println(key);
		     System.out.println(Ind.get(key));
		}
		Ind=saveIndiData(args[0]);
		for(Family str : family) 
		{
			System.out.println(str.getId());
			Enumeration key = Ind.keys();
			while(key.hasMoreElements())
			{
				String x = (String)key.nextElement();
				if(str.getHusband().contains(x))
				{
					System.out.println("Husband:" + Ind.get(x));
				}
				if(str.getWife().contains(x))
				{
					System.out.println("Wife: "+ Ind.get(x));
				}
			}
			
		}

	}
}
