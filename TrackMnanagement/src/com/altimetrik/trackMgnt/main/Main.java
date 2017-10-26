package com.altimetrik.trackMgnt.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.altimetrik.trackMgnt.Business.ManageTalks;

public class Main {
	public static void main(String[] args) {
		
		
		
		Tree<String> ceo=new Tree<String>("CEO");
		
		Tree<String> vpTechnology=new Tree<String>("VP-TECH");
		Tree<String> SM1=new Tree<String>("SM-WingA");
		Tree<String> SM2=new Tree<String>("SM-WingB");
		vpTechnology.addCild(SM1);
		vpTechnology.addCild(SM2);
		
		Tree<String> vpSales=new Tree<String>("VP-Sales");
		Tree<String> SalesM1=new Tree<String>("SM-Marketing");
		Tree<String> SsalesM2=new Tree<String>("SM-Advertising");
		vpSales.addCild(SalesM1);
		vpSales.addCild(SsalesM2);
		
		ceo.addCild(vpTechnology);
		ceo.addCild(vpSales);
		ceo.displayTree();
		
		/*
		
		System.out.println(new Date().getTime());
		ManageTalks manageTalks=new ManageTalks("D://talks.txt"); //Pass the input file of talks details.
		manageTalks.scheduleTalks();
		System.out.println(new Date().getTime());*/
	}
	
	
/*	public static boolean checkForPair(int[] arr, int sum){
		HashSet<Integer> hs=new HashSet<Integer>();
		for(int val:arr){
			int com=sum-val;
			if(hs.contains(val)){
					return true;
			}
			hs.add(com);
		}
		return false;
	}*/
}
class Tree<T>{
	T data;
	List<Tree<T>> childern;
	
	public Tree(T data){
		this.data=data;
		this.childern=(List<Tree<T>>) new ArrayList<Tree<T>>();
	}
	
	public void addCild(Tree<T> childern){
		this.childern.add(childern);
	}
	
	public void displayTree(){
		System.out.println("Root::"+data);
		if(!childern.isEmpty())
			iterate(childern);
	}
	
	public void iterate(List<Tree<T>> tree){
			for(Tree<T> child:tree){
				System.out.println("Child::"+child.data);
				if(!child.childern.isEmpty()){
					iterate(child.childern);
				}
			}
	}
}

