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
	public static int CountCharacter(File f){    //�����ַ�����
		if (!f.exists())
			return -1;
		int result=0;
		BufferedReader reader=null;
		try{
			reader = new BufferedReader(new FileReader(f));
			String str=null;
			while((str=reader.readLine())!=null)      
				result+=str.length();          //�ַ��������Ƕ���ÿһ�е��ַ����ĳ���
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
	
	public static int CountWord(File f){     //���ص�������������-s�������
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
	
	public static int CountWord(File f,ArrayList <String> arr){     //���ط��ص�����������ͣ�ôʣ�
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
					if(!arr.contains(str)){               //����߽���ͣ�ô���ͬ���ų�
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
	
	public static int CountLine(File f){     //����������
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
	
	public static int[] CountComplex(File f){       //���ش����У�ע���У����и���
		int[] arr=new int[3];
		int emptyline=0,codeline=0,noteline=0;
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(f));
			String str=null;
			int flag=0;
			while((str=reader.readLine())!=null){
				str=str.trim();  //ȥ�����ߵĿո���������ֱ��ͨ���ж�str�����ж��ǲ��ǿ���
				if(str.length()==0||str.equals("{"))  
					emptyline++;
				else if(str.startsWith("//")||str.startsWith("}//"))     //���������//��ͷ����ô��ע���У��ų������Ǵ���Ȼ����//�������
					noteline++;
				else if(str.contains("/*")){       //����������д���/* ����ǿ�ͷ���֣��ж��ǲ����ڸ��н�����������м���֣������Ǵ�����
					if(str.startsWith("/*")||str.startsWith("}/*")){   
						if(str.contains("*/")){    //����ڱ����д���*/���ж��ǲ��ǽ�β
							if(str.endsWith("*/"))
								noteline++;
							else 
								codeline++;
						}
						else{    //���в�����*/�Ͱ�flag��Ϊ1��Ȼ������һ�У�ֱ��һ�а���*/
							flag=1;
							noteline++;
						}
					}
					else{           //������/*��ͷ���ǰ���/*���ж��ǲ��Ǹ��н���
						if(str.contains("*/")){    //������н�������ô���Ǵ�����
							codeline++;
						}
						else{    //�������û������flag��1�������Ǵ�����
							codeline++;
							flag=1;
						}
					}
				}
				else if(flag==1){         //���flag��1���жϱ�����û��*/
					if(str.contains("*/")){    //������а���*/��flag=1��ô��ʾע�ͽ������ж��ǲ��ǽ�β
						if(str.endsWith("*/")){        //���*/�ڽ�β����ע���У����ڽ�β���Ǵ�����
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
	
	public static void Output(File f,String s){     //�����ָ���ļ�
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(f));
			writer.write(s);       //������д��
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
	
	public static ArrayList<String> readstopword(File f){     //ͣ�ôʶ�ȡ
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
	
	public static ArrayList<String> traverse(String filepath){     //�ݹ���ʾ·���������ļ���
		File root = new File(filepath);  
	    File[] files = root.listFiles();
	    ArrayList <String> filelist=new ArrayList<String>();
	    for(File file:files){       
	    	if(file.isDirectory()){         //�����һ���ļ��У��ͼ����ݹ�
	    		traverse(file.getAbsolutePath());      
	    	}
	    	else{                       //�������һ���ļ��У��Ͱ��ļ����ӵ������С�
	    		filelist.add(file.getName()); 
	    		//System.out.println(file.getName());  
	    	}       
	    }  
	    return filelist;
	}
	
	public static ArrayList<String> choose(ArrayList<String> filename,String filefeature){   //�����ļ���������ɸѡ�ļ�
		ArrayList <String> filelist=new ArrayList<String>();
		for(int i=0;i<filename.size();i++){
			if(filename.get(i).contains(filefeature)){     //��������������ţ���ô������Ҫ���ļ�
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
			System.out.println("��û�д����κβ���");
		}
		else{
			int i=0;
			for(i=0;i<args.length;++i){             //����args���ñ�־��¼���ֵ�����
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
				else{                //�����ڳ����ļ���֮ǰ��û�г���-o�ж�������ļ����Ƕ��ļ�
					if(is_o){
						outputfilename=new File(args[i]);
					}
					else if(is_e){      //�����ڳ����ļ���֮ǰ��û�г���-e�ж���ͣ�ô��ļ����Ƕ��ļ�
						stopwordfile=new File(args[i]);
					//	readstopword(stopwordfile);
					}
					else if(!is_s){          //������-sֻ��Ҫ���ò��������ļ���
						filename.add(new File(args[i]));
						str1.add(args[i]);
					}
					else{               //����-s��Ҫ����Ŀ¼�·����������ļ� 
						String [] s=args[i].split("\\*");                //���ò�����*�ֿ���ǰ����·�����������ļ�����������
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
		for(int i=0;i<filename.size();++i){           //�����������һ���Ǻ�-s����ô���ɶ���ļ������㣬����-sֻ�е�һ�ļ�
			if(is_c){
				System.out.println(str1.get(i)+",�ַ���: "+CountCharacter(filename.get(i)));
			}
			if(is_w&&(!is_e)){
				System.out.println(str1.get(i)+",������: "+CountWord(filename.get(i)));
			}
			else if(is_w&&is_e){
				System.out.println(str1.get(i)+",������: "+CountWord(filename.get(i),readstopword(stopwordfile)));
			}
			if(is_l){
				System.out.println(str1.get(i)+",����: "+CountLine(filename.get(i)));
			}
			if(is_a){
				System.out.println(str1.get(i)+",������/����/ע����: "+CountComplex(filename.get(i))[0]+"/"+CountComplex(filename.get(i))[1]+"/"+CountComplex(filename.get(i))[2]);
			}
			if(is_o){
				if(is_c){
					str+=str1.get(i)+",�ַ���: "+CountCharacter(filename.get(i))+"\r\n";
				}
				if(is_w){
					str+=str1.get(i)+",������: "+CountWord(filename.get(i))+"\r\n";
				}
				if(is_l){
					str+=str1.get(i)+",����: "+CountLine(filename.get(i))+"\r\n";
				}
				if(is_a){
					str+=str1.get(i)+",������/����/ע����: "+CountComplex(filename.get(i))[0]+"/"+CountComplex(filename.get(i))[1]+"/"+CountComplex(filename.get(i))[2]+"\r\n";
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
