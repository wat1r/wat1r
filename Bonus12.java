package Algorithm;

import java.util.Scanner;

/**
 * 【程序12】   
题目：企业发放的奖金根据利润提成。
利润(I)低于或等于10万元时，奖金可提10%；
利润高于10万元，低于20万元时，低于10万元的部分按10%提成，高于10万元的部分，可可提成7.5%；
20万到40万之间时，高于20万元的部分，可提成5%；
40万到60万之间时高于40万元的部分，可提成3%；
60万到100万之间时，高于60万元的部分，可提成1.5%，高于100万元时，超过100万元的部分按1%提成，从键盘输入当月利润，求应发放奖金总数？

 * @author Administrator
 *
 */
public class Bonus12 {

	public static void main(String[] args) {
		double bonus=0;
		Scanner console = new Scanner(System.in);
		System.out.println("输入利润：");
		double profit=console.nextInt();
		if(profit<=100000){
			bonus = profit*0.1;
		}else if(profit>100000&&profit<=200000){
			bonus = 10000*0.1+(profit-100000)*0.075;
		}else if(profit>200000&&profit<=400000){
			bonus = 10000*0.1+100000*0.075+(profit-200000)*0.05;
		}else if(profit>400000&&profit<=600000){
			bonus = 10000*0.1+100000*0.075+200000*0.05+(profit-400000)*0.03;
		}else if(profit>600000&&profit<=1000000){
			bonus = 10000*0.1+100000*0.075+200000*0.05+200000*0.03+(profit-600000)*0.015;
		}else if(profit>1000000){
			bonus = 10000*0.1+100000*0.075+200000*0.05+200000*0.03+400000*0.015+(profit-1000000)*0.01;
		}
		System.out.println("获得的奖金为："+bonus);
	}

}
