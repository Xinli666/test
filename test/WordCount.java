package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
				result+=str.length();
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
	
	public static int CountWord(File f){     //���ص�������
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
					if(!judge(str)){
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
	
	public static void Output(File f){
		
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
			writer.write(s);
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
	
	public static boolean judge(String s){     //ͣ�ô��ж�
		ArrayList <String> StopWordList=new ArrayList();
		StopWordList.add("while");
		StopWordList.add("if");
		StopWordList.add("switch");
		if(StopWordList.contains(s)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){	
		boolean is_c=false,is_w=false,is_l=false,is_o=false,is_a=false;
		File filename=null,outputfilename=null;
		String str1=null;
		if(args.length==0){
			System.out.println("��û�д����κβ���");
		}
		else{
			int i=0;
			for(i=0;i<args.length;++i){             //����args�����ĸ���־��¼���ֵ�����
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
				else{                //�����ڳ����ļ���֮ǰ��û�г���-o�ж�������ļ����Ƕ��ļ�
					if(is_o){
						outputfilename=new File(args[i]);
					}
					else{
						filename=new File(args[i]);
						str1=args[i];
					}
				}
			}
		}
		if(is_c){
			System.out.println(str1+",�ַ���: "+CountCharacter(filename));
		}
		if(is_w){
			System.out.println(str1+",������: "+CountWord(filename));
		}
		if(is_l){
			System.out.println(str1+",����: "+CountLine(filename));
		}
		if(is_a){
			System.out.println(str1+",������/����/ע����: "+CountComplex(filename)[0]+"/"+CountComplex(filename)[1]+"/"+CountComplex(filename)[2]);
		}
		if(is_o){
			String str="";
			if(is_c){
				str+=str1+",�ַ���: "+CountCharacter(filename)+"\r\n";
			}
			if(is_w){
				str+=str1+",������: "+CountWord(filename)+"\r\n";
			}
			if(is_l){
				str+=str1+",����: "+CountLine(filename)+"\r\n";
			}
			if(is_a){
				str+=str1+",������/����/ע����: "+CountComplex(filename)[0]+"/"+CountComplex(filename)[1]+"/"+CountComplex(filename)[2];
			}
			Output(outputfilename,str);
		}
		
		
		//File filename=new File("1.txt");
		
		
		//System.out.println(CountComplex(filename)[0]+"  "+CountComplex(filename)[1]+"  "+CountComplex(filename)[2]);
		//System.out.println(CountCharacter(filename));
		//System.out.println(CountWord(filename));
		//System.out.println(CountLine(filename));
	}
}
