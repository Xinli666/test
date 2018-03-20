package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WordCount {      
	public static int CountCharacter(File f){    //返回字符总数
		if (!f.exists())
			return -1;
		int result=0;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new FileReader(f));
			String str=null;
			while((str=reader.readLine())!=null)      
				result+=str.length();          //字符总数就是读到每一行的字符串的长度
		}
		catch (FileNotFoundException e0){
			e0.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static int CountWord(File f){     //返回单词总数（不含-s的情况）
		if(!f.exists())
			return -1;
		int result=0;
		BufferedReader reader=null;
		try{
			reader=new BufferedReader(new FileReader(f));
			int temp,flag=0;
			while((temp=reader.read())!=-1){
				if((char)temp!='\n'&&(char)temp!='\t'&&(char)temp!=','&&(char)temp!=' '&&(char)temp!='\r'){
					flag=1;
				}
				else if(flag==1&&((char)temp=='\n'||(char)temp=='\t'||(char)temp==','||(char)temp==' '||(char)temp=='\r')){
					flag=0;
					result++;
				}
			}
			result++;
		}
		catch (FileNotFoundException e0){
			e0.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static int CountWord(File f,ArrayList <String> arr){     //重载返回单词总数（含停用词）
		if(!f.exists())
			return -1;
		int result=0;
		BufferedReader reader=null;
		try{
			reader=new BufferedReader(new FileReader(f));
			int temp,flag=0;
			String str="";
			while((temp=reader.read())!=-1){
				if((char)temp!='\n'&&(char)temp!='\t'&&(char)temp!=','&&(char)temp!=' '&&(char)temp!='\r'){
					flag=1;
					str+=(char)temp;
				}
				else if(flag==1&&((char)temp=='\n'||(char)temp=='\t'||(char)temp==','||(char)temp==' '||(char)temp=='\r')){
					flag=0;
					if(!arr.contains(str)){               //在这边将与停用词相同的排除
						result++;
					}
					str="";
				}
			}
			result++;
		}
		catch (FileNotFoundException e0){
			e0.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static int CountLine(File f){     //返回总行数
		if (!f.exists())
			return -1;
		int result=0;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new FileReader(f));
			while(reader.readLine()!=null)
				result++;
		}
		catch (FileNotFoundException e0){
			e0.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static int[] CountComplex(File f){       //返回代码行，注释行，空行个数
		int[] arr=new int[3];
		int emptyline=0,codeline=0,noteline=0;
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(f));
			String str=null;
			int flag=0;
			while((str=reader.readLine())!=null){
				str=str.trim();  //去掉两边的空格，这样可以直接通过判断str长度判断是不是空行
				if(str.length()==0||str.equals("{"))  
					emptyline++;
				else if(str.startsWith("//")||str.startsWith("}//"))     //如果该行是//开头，那么是注释行，排除了先是代码然后是//的情况。
					noteline++;
				else if(str.contains("/*")){       //如果代码行中存在/* 如果是开头出现，判断是不是在该行结束，如果在中间出现，该行是代码行
					if(str.startsWith("/*")||str.startsWith("}/*")){   
						if(str.contains("*/")){    //如果在本行中存在*/，判断是不是结尾
							if(str.endsWith("*/"))
								noteline++;
							else 
								codeline++;
						}
						else{    //本行不存在*/就把flag置为1，然后找下一行，直到一行包含*/
							flag=1;
							noteline++;
						}
					}
					else{           //不是以/*开头但是包含/*，判断是不是该行结束
						if(str.contains("*/")){    //如果本行结束，那么就是代码行
							codeline++;
						}
						else{    //如果本行没结束，flag置1，本行是代码行
							codeline++;
							flag=1;
						}
					}
				}
				else if(flag==1){         //如果flag是1，判断本行有没有*/
					if(str.contains("*/")){    //如果本行包含*/且flag=1那么表示注释结束，判断是不是结尾
						if(str.endsWith("*/")){        //如果*/在结尾，是注释行，不在结尾，是代码行
							flag=0;
							noteline++;
						}
						else{
							flag=0;
							codeline++;
						}
					}
					else{
						noteline++;
					}
				}
				else{
					codeline++;
				}
			}
		}
		catch(FileNotFoundException e0){
			e0.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		arr[0]=codeline;
		arr[1]=emptyline;
		arr[2]=noteline;
		return arr;
	}
	
	public static void Output(File f,String s){     //输出到指定文件
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(f));
			writer.write(s);       //将内容写入
		}
		catch (FileNotFoundException e0){
			e0.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				writer.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<String> readstopword(File f){     //停用词读取
		ArrayList <String> StopWordList=new ArrayList<String>();
		BufferedReader reader=null;
		try{
			int temp,flag=0;
			String str="";
			reader=new BufferedReader(new FileReader(f));
			while((temp=reader.read())!=-1){
				if((char)temp!='\n'&&(char)temp!='\t'&&(char)temp!=','&&(char)temp!=' '&&(char)temp!='\r'){
					flag=1;
					str+=(char)temp;
				}
				else if(flag==1&&((char)temp=='\n'||(char)temp=='\t'||(char)temp==','||(char)temp==' '||(char)temp=='\r')){
					flag=0;
					StopWordList.add(str);
					str="";
				}
			}
			StopWordList.add(str);
		}
		catch (FileNotFoundException e0){
			e0.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return StopWordList;
	}
	
	public static ArrayList<String> traverse(String filepath){     //递归显示路径下所有文件名
		File root = new File(filepath);  
	    File[] files = root.listFiles();
	    ArrayList <String> filelist=new ArrayList<String>();
	    for(File file:files){       
	    	if(file.isDirectory()){         //如果是一个文件夹，就继续递归
	    		traverse(file.getAbsolutePath());      
	    	}
	    	else{                       //如果不是一个文件夹，就吧文件名加到数组中。
	    		filelist.add(file.getName()); 
	    		//System.out.println(file.getName());  
	    	}       
	    }  
	    return filelist;
	}
	
	public static ArrayList<String> choose(ArrayList<String> filename,String filefeature){   //根据文件特征符号筛选文件
		ArrayList <String> filelist=new ArrayList<String>();
		for(int i=0;i<filename.size();i++){
			if(filename.get(i).contains(filefeature)){     //如果包含特征符号，那么就是需要的文件
				filelist.add(filename.get(i));
			}
		}
		return filelist;
	}
	
	public static void main(String[] args){	
		boolean is_c=false,is_w=false,is_l=false,is_o=false,is_a=false,is_e=false,is_s=false;
		File outputfilename=null,stopwordfile=null;
		String path="",feature="",str="";
		ArrayList <String> str1=new ArrayList <String>();
		ArrayList <File> filename=new ArrayList<File>();
		if(args.length==0){
			System.out.println("你没有传入任何参数");
		}
		else{
			int i=0;
			for(i=0;i<args.length;++i){             //遍历args，用标志记录出现的运算
				if(args[i].equals("-c")){
					is_c=true;
				}
				else if(args[i].equals("-w")){
					is_w=true;
				}
				else if(args[i].equals("-l")){
					is_l=true;
				}
				else if(args[i].equals("-o")){
					is_o=true;
				}
				else if(args[i].equals("-a")){
					is_a=true;
				}
				else if(args[i].equals("-e")){
					is_e=true;
				}
				else if(args[i].equals("-s")){
					is_s=true;
					//traverse("D://workspace/test");
				}
				else{                //根据在出现文件名之前有没有出现-o判断是输出文件还是读文件
					if(is_o){
						outputfilename=new File(args[i]);
					}
					else if(is_e){      //根据在出现文件名之前有没有出现-e判断是停用词文件还是读文件
						stopwordfile=new File(args[i]);
					//	readstopword(stopwordfile);
					}
					else if(!is_s){          //不包含-s只需要将该参数当作文件名
						filename.add(new File(args[i]));
						str1.add(args[i]);
					}
					else{               //包含-s需要分析目录下符合条件的文件 
						String [] s=args[i].split("\\*");                //将该参数按*分开，前面是路径，后面是文件的特征符号
						path=s[0];
						feature=s[1];
						for(int j=0;j<choose(traverse(path),feature).size();++j){
							filename.add(new File(choose(traverse(path),feature).get(j)));
							str1.add(choose(traverse(path),feature).get(j));
						}
					}
				}
			}
		}
		//System.out.println(is_o);
		for(int i=0;i<filename.size();++i){           //分两种情况，一种是含-s，那么将由多个文件倍计算，不含-s只有单一文件
			if(is_c){
				System.out.println(str1.get(i)+",字符数: "+CountCharacter(filename.get(i)));
			}
			if(is_w&&(!is_e)){
				System.out.println(str1.get(i)+",单词数: "+CountWord(filename.get(i)));
			}
			else if(is_w&&is_e){
				System.out.println(str1.get(i)+",单词数: "+CountWord(filename.get(i),readstopword(stopwordfile)));
			}
			if(is_l){
				System.out.println(str1.get(i)+",行数: "+CountLine(filename.get(i)));
			}
			if(is_a){
				System.out.println(str1.get(i)+",代码行/空行/注释行: "+CountComplex(filename.get(i))[0]+"/"+CountComplex(filename.get(i))[1]+"/"+CountComplex(filename.get(i))[2]);
			}
			if(is_o){
				if(is_c){
					str+=str1.get(i)+",字符数: "+CountCharacter(filename.get(i))+"\r\n";
				}
				if(is_w){
					str+=str1.get(i)+",单词数: "+CountWord(filename.get(i))+"\r\n";
				}
				if(is_l){
					str+=str1.get(i)+",行数: "+CountLine(filename.get(i))+"\r\n";
				}
				if(is_a){
					str+=str1.get(i)+",代码行/空行/注释行: "+CountComplex(filename.get(i))[0]+"/"+CountComplex(filename.get(i))[1]+"/"+CountComplex(filename.get(i))[2]+"\r\n";
				}
				//Output(outputfilename,str);
			}
		}
		if(is_o){
			Output(outputfilename,str);
		}
		//File filename=new File("1.txt");
		
		
		//System.out.println(CountComplex(filename)[0]+"  "+CountComplex(filename)[1]+"  "+CountComplex(filename)[2]);
		//System.out.println(CountCharacter(filename));
		//System.out.println(CountWord(filename));
		//System.out.println(CountLine(filename));
	}
}
