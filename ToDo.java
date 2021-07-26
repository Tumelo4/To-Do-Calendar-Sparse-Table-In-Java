public class ToDo
{
	/*Use this class to test your implementation.  This file will be overwritten for marking purposes.*/
	
	public static void main(String[] args)
	{
		//Write code to test your implementation here.
		Calendar first=new Calendar();
		first.addItem(5, "jun", "A", "00:12",4);
		first.addItem(5, "MaR", "BB","13:47",5);
		first.addItem(5, "dEc", "C","40:47",-5);
		first.addItem(4, "dEc", "D","10:38",7);
		first.addItem(8, "jUn", "F","24:38",0);
		first.addItem(5, "MaR", "B","13:17",7);
		first.addItem(5, "MaR", "BBB","13:17",-7);
		first.addItem(8, "JUn", "FF","25:38",1);
		first.addItem(6, "jun", "G","12:38",6);

	}

	public static void rightTrav(Item curr){
		for(;curr!=null;curr=curr.right){
			System.out.println(curr.getDescription()+" "+curr.getDuration()+" "+curr.getPriority());
		}
	}

	public static void backTrav(Item curr){
		for(;curr!=null;curr=curr.back){
			System.out.println(curr.getDescription()+" "+curr.getDuration()+" "+curr.getPriority());
		}
	}
	
}
