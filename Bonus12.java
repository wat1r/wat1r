package Algorithm;

import java.util.Scanner;

/**
 * ������12��   
��Ŀ����ҵ���ŵĽ������������ɡ�
����(I)���ڻ����10��Ԫʱ���������10%��
�������10��Ԫ������20��Ԫʱ������10��Ԫ�Ĳ��ְ�10%��ɣ�����10��Ԫ�Ĳ��֣��ɿ����7.5%��
20��40��֮��ʱ������20��Ԫ�Ĳ��֣������5%��
40��60��֮��ʱ����40��Ԫ�Ĳ��֣������3%��
60��100��֮��ʱ������60��Ԫ�Ĳ��֣������1.5%������100��Ԫʱ������100��Ԫ�Ĳ��ְ�1%��ɣ��Ӽ������뵱��������Ӧ���Ž���������

 * @author Administrator
 *
 */
public class Bonus12 {

	public static void main(String[] args) {
		double bonus=0;
		Scanner console = new Scanner(System.in);
		System.out.println("��������");
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
		System.out.println("��õĽ���Ϊ��"+bonus);
	}

}
