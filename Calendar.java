/* Complete this class to implement a fully functional sparse table. Read the comments to determine what each aspect of the class is supposed to do.
You must add any additional features (methods, references) which may aid you in your
task BUT you are not allowed to remove or change the names or properties of any of the features you were given.

Note: you are NOT allowed to use any 2D or 3D arrays to simulate the sparse table functionality. Doing so will result in a mark of zero.

Importing Java's built in data structures will result in a mark of zero. Only the use of native 1-dimensional is are allowed. */

public class Calendar
{
	Item themonths[];
	Item thedays[];
	public Calendar()
	{
		/*You may implement this constructor to suit your needs, or you may add additional constructors.  This is the constructor which will be used for marking*/ 
		themonths=new Item[12];
		thedays=new Item[31];
		clearYear();
	}

	/*Insertion*/
	public void addItem(int day, String month, String description, String duration, int priority)
	{
		/* Insert an Item at the given day and month combination according to priority. Intialize the Item with the remainder of the parameters.
		
		Duplicate Items are allowed.*/
		Item newItem=new Item();
		newItem.theDay=day;
		newItem.theMonth=month;
		String[] split=duration.split(":");
		int minutes=Integer.parseInt(split[0]);
		int hours=Integer.parseInt(split[1]);
		if(minutes>0 && hours==24){
			newItem.setDuration("00:00");
		}
		else if(minutes<0 || hours>24 || minutes>59 || hours<0){
			newItem.setDuration("00:00");
		}
		else{
			newItem.setDuration(duration);
		}
		newItem.setDescription(description);
		if(priority>=0){
			newItem.setPriority(priority);
		}
		else{
			newItem.setPriority(0);
		}
		

		Item currD,currM;
		Item prevD=null, prevM=null;
		for(currD=thedays[day-1];currD!=null && validateMonth(currD.theMonth)<validateMonth(month);prevD=currD, currD=currD.right);
		for(currM=themonths[validateMonth(month)];currM!=null && currM.theDay<day;prevM=currM, currM=currM.down);
		
		if(currD==null && currM==null && prevD==null && prevM==null){
			//System.out.println(description+" currD==null && currM==null && prevD==null && prevM==null");
			thedays[day-1]=newItem;
			themonths[validateMonth(month)]=newItem;
		}
		else if(currD!=null && currM!=null && currM.theDay==day && validateMonth(currD.theMonth)==validateMonth(month)){
			//System.out.println(description+" currD!=null && currM!=null && currM.theDay==day && validateMonth(currD.theMonth)==validateMonth(month)");
			Item backTrav;
			Item backPrev=null;
			if(currM.getPriority()==newItem.getPriority() && currD.getPriority()==newItem.getPriority()){
				for(backTrav=currM; backTrav!=null && backTrav.getPriority()==newItem.getPriority();backPrev=backTrav,backTrav=backTrav.back);
				newItem.back=backPrev.back;
				backPrev.back=newItem;
			}
			else if(currM.getPriority()>newItem.getPriority() && currD.getPriority()>newItem.getPriority()){
				for(backTrav=currM; backTrav!=null && backTrav.getPriority()>newItem.getPriority();backPrev=backTrav,backTrav=backTrav.back);
				newItem.back=backPrev.back;
				backPrev.back=newItem;
			}
			else{

				if(prevD!=null && prevM !=null){
					newItem.right=currM.right;
					newItem.down=currM.down;
					newItem.back=currM;
					prevD.right=newItem;
					prevM.down=newItem;
				}
				else if(prevD==null && prevM ==null){
					newItem.right=currD.right;
					newItem.down=currM.down;
					newItem.back=currD;
					themonths[validateMonth(month)]=newItem;
					thedays[day-1]=newItem;
				}

				else if(prevD!=null && prevM ==null){
					prevD.right=newItem;
					newItem.right=currD.right;
					newItem.back=currD;
					newItem.down=currD.down;
					themonths[validateMonth(month)]=newItem;
				}

				else if(prevD==null && prevM !=null){
					prevM.down=newItem;
					newItem.right=currD.right;
					newItem.back=currD;
					newItem.down=currD.down;
					thedays[day-1]=newItem;
				}
			}
		}
		else if(currD==null && currM==null && prevD!=null && prevM==null){
			//System.out.println(description+" currD==null && currM==null && prevD!=null && prevM==null");
			prevD.right=newItem;
			themonths[validateMonth(month)]=newItem;
		}

		else if(currD==null && currM==null && prevD==null && prevM!=null){
			//System.out.println(description+" currD==null && currM==null && prevD==null && prevM!=null");
			prevM.down=newItem;
			thedays[day-1]=newItem;
		}

		else if(currD==null && currM==null && prevD!=null && prevM!=null){
			//System.out.println(description+" currD==null && currM==null && prevD!=null && prevM!=null");
			prevD.right=newItem;
			prevM.down=newItem;
		}

		else if(currD!=null && currM!=null && prevD==null && prevM==null){
			//System.out.println(description+" currD!=null && currM!=null && prevD==null && prevM==null");
			newItem.right=currD;
			thedays[day-1]=newItem;
			newItem.down=currM;
			themonths[validateMonth(month)]=newItem;
		}

		else if(currD!=null && currM==null && prevD!=null && prevM==null){
			//System.out.println(description+" currD!=null && currM==null && prevD!=null && prevM==null");
			newItem.right=prevD.right;
			prevD.right=newItem;
			themonths[validateMonth(month)]=newItem;
		}

		else if(currD==null && currM!=null && prevD==null && prevM!=null && currM.theDay!=day){
			//System.out.println(description+" currD==null && currM!=null && prevD==null && prevM!=null && currM.theDay!=day");
			newItem.down=prevM.down;
			prevM.down=newItem;
			thedays[day-1]=newItem;
		}

		else if(currD==null && currM!=null && prevD==null && prevM==null && currM.theDay!=day){
			//System.out.println(description+" currD==null && currM!=null && prevD==null && prevM==null && currM.theDay!=day");
			newItem.down=currM;
			themonths[validateMonth(month)]=newItem;
			thedays[day-1]=newItem;
		}

		else if(currD!=null && currM==null && prevD==null && prevM==null && validateMonth(currD.theMonth)!=validateMonth(month)){
			//System.out.println(description+" currD!=null && currM==null && prevD==null && prevM==null && validateMonth(currD.theMonth)!=validateMonth(month)");
			newItem.right=currD;
			thedays[day-1]=newItem;
			themonths[validateMonth(month)]=newItem;
		}

		else if(currD!=null && currM!=null && prevD==null && prevM!=null && validateMonth(currD.theMonth)!=validateMonth(month)){
			//System.out.println(description+" currD!=null && currM!=null && prevD==null && prevM!=null && validateMonth(currD.theMonth)!=validateMonth(month)");
			newItem.down=prevM.down;
			prevM.down=newItem;
			newItem.right=currD;
			thedays[day-1]=newItem;
		}

		else if(currD!=null && currM!=null && prevD!=null && prevM==null && currM.theDay!=day){
			//System.out.println(description+" currD!=null && currM!=null && prevD!=null && prevM==null && currM.theDay!=day");
			newItem.right=prevD.right;
			prevD.right=newItem;
			newItem.down=currM;
			themonths[validateMonth(month)]=newItem;
		}

		else if(currD!=null && currM!=null && prevD!=null && prevM!=null && currM.theDay!=day && validateMonth(currD.theMonth)!=validateMonth(month)){
			//System.out.println(description+" currD!=null && currM!=null && prevD!=null && prevM!=null && currM.theDay!=day && validateMonth(currD.theMonth)!=validateMonth(month)");
			newItem.right=prevD.right;
			prevD.right=newItem;
			newItem.down=prevM.down;
			prevM.down=newItem;
		}
		else if(currD!=null && currM==null && prevD==null && prevM!=null){
			//System.out.println(description+" currD!=null && currM==null && prevD==null && prevM!=null");
			newItem.down=prevM.down;
			prevM.down=newItem;
			newItem.right=currD;
			thedays[day-1]=newItem;
		}
		else if(currD==null && currM!=null && prevD!=null && prevM==null){
			//System.out.println(description+" currD==null && currM!=null && prevD!=null && prevM==null");
			newItem.down=currM;
			prevD.right=newItem;
			themonths[validateMonth(month)]=newItem;
		}
		else if(currD==null && currM!=null && prevM!=null && prevD!=null){
			////System.out.println(description+" currD==null && currM!=null && prevM!=null && prevD!=null");
			prevM.down=newItem;
			newItem.down=currM;
			prevD.right=newItem;
		}

		else if(currD!=null && currM==null && prevM!=null && prevD!=null){
			////System.out.println(description+" currD!=null && currM==null && prevM!=null && prevD!=null");
			prevD.right=newItem;
			newItem.right=currD;
			prevM.down=newItem;
		}

		//System.out.println(description);
	}
	
	/*Deletion*/
	public Item deleteItem(int day, String month, String description)
	{
		/*Delete the first Item at the given day and month combination with the given description and return the deleted Item.
		If no such Item exists, return null.*/
		if(getItem(day,month)!=null){
			Item currM=themonths[validateMonth(month)],currD=thedays[day-1],prevM=null,prevD=null;
			for(;currM!=null && currM.theDay!=currD.theDay;prevM=currM,currM=currM.down);
			for(;currD!=null && validateMonth(currD.theMonth)!=validateMonth(month);prevD=currD,currD=currD.right);
			if(prevM!=null && prevD!=null){
				if(currM.getDescription().toUpperCase().compareTo(description.toUpperCase())==0){
					if(currM.back !=null){
						prevM.down=currM.back;
						currM.back.down=currM.down;
						prevD.right=currM.back;
						currM.back.right=currM.right;
					}
					else{
						prevM.down=currM.down;
						prevD.right=currM.right;
					}
					return currM;
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getDescription().toUpperCase().compareTo(description.toUpperCase())!=0;prevB=currB,currB=currB.back);
					if(currB==null)return null;
					prevB.back=currB.back;
					return currB;
				}
			}
			else if(prevM!=null && prevD==null){
				if(currM.getDescription().toUpperCase().compareTo(description.toUpperCase())==0){
					if(currM.back !=null){
						prevM.down=currM.back;
						currM.back.down=currM.down;
						currM.back.right=currM.right;
						thedays[day-1]=currM.back;
					}
					else{
						prevM.down=currM.down;
						thedays[day-1]=currM.right;
					}
					return currM;
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getDescription().toUpperCase().compareTo(description.toUpperCase())!=0;prevB=currB,currB=currB.back);
					if(currB==null)return null;
					prevB.back=currB.back;
					return currB;
				}

			}

			else if(prevM==null && prevD!=null){
				if(currM.getDescription().toUpperCase().compareTo(description.toUpperCase())==0){
					if(currM.back !=null){
						currM.back.down=currM.down;
						prevD.right=currM.back;
						currM.back.right=currM.right;
						themonths[validateMonth(month)]=currM.back;
					}
					else{
						prevD.right=currM.right;
						themonths[validateMonth(month)]=currM.down;
					}
					return currM;
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getDescription().toUpperCase().compareTo(description.toUpperCase())!=0;prevB=currB,currB=currB.back);
					if(currB==null)return null;
					prevB.back=currB.back;
					return currB;
				}
			}

			else if(prevM==null && prevD==null){
				if(currM.getDescription().toUpperCase().compareTo(description.toUpperCase())==0){
					if(currM.back !=null){
						thedays[day-1]=currD.back;
						thedays[day-1].right=currD.right;
						themonths[validateMonth(month)]=currM.back;
						themonths[validateMonth(month)].down=currM.down;	
					}
					else{
						thedays[day-1]=currM.right;
						themonths[validateMonth(month)]=currM.down;
					}
					return currM;
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getDescription().toUpperCase().compareTo(description.toUpperCase())!=0;prevB=currB,currB=currB.back);
					if(currB==null)return null;
					prevB.back=currB.back;
					return currB;
				}
			}
		}	
		return null;
	}
	
	public void deletePriorityItem(int day, String month, int priority)
	{
		/*Delete all Items of a given priority at the given day and month combination.*/
		if(getItem(day,month)!=null){
			Item currM=themonths[validateMonth(month)],currD=thedays[day-1],prevM=null,prevD=null;
			for(;currM!=null && currM.theDay!=currD.theDay;prevM=currM,currM=currM.down);
			for(;currD!=null && validateMonth(currD.theMonth)!=validateMonth(month);prevD=currD,currD=currD.right);
			if(prevM!=null && prevD!=null){
				if(currM.getPriority()==priority){
					if(currM.back !=null){
						Item currB=currM;
						for(;currB!=null && currB.getPriority()==priority;currB=currB.back);
						if(currB!=null){
							prevM.down=currB;
							currB.down=currM.down;
							prevD.right=currB;
							currB.right=currD.right;
							currM=null;
							currD=null;
						}
						else{
							prevM.down=currM.down;
							prevD.right=currD.right;
						}
					}
					else{
						prevM.down=currM.down;
						prevD.right=currD.right;
						currM=null;
						currD=null;
					}
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getPriority()!=priority;prevB=currB,currB=currB.back);
					if(currB!=null){
						Item currBb=currB;
						for(;currBb!=null && currBb.getPriority()==priority;currBb=currBb.back);
						prevB.back=currBb;
					}
				}
			}
			else if(prevM==null && prevD!=null){
				if(currM.getPriority()==priority){
					if(currM.back !=null){
						Item currB=currM;
						for(;currB!=null && currB.getPriority()==priority;currB=currB.back);
						if(currB!=null){
							currB.down=currM.down;
							currB.right=currD.right;
							prevD.right=currB;
							themonths[validateMonth(month)]=currB;
							currM=null;
							currD=null;
						}
						else{
							prevD.right=currD.right;
							themonths[validateMonth(month)]=currM.down;
							currM=null;
							currD=null;
						}
					}
					else{
						prevD.right=currD.right;
						themonths[validateMonth(month)]=currM.down;
						currM=null;
						currD=null;
					}
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getPriority()!=priority;prevB=currB,currB=currB.back);
					if(currB!=null){
						Item currBb=currB;
						for(;currBb!=null && currBb.getPriority()==priority;currBb=currBb.back);
						prevB.back=currBb;
					}
				}
			}
			else if(prevM!=null && prevD==null){
				if(currM.getPriority()==priority){
					if(currM.back !=null){
						Item currB=currM;
						for(;currB!=null && currB.getPriority()==priority;currB=currB.back);
						if(currB!=null){
							prevM.down=currB;
							currB.down=currM.down;
							currB.right=currD.right;
							thedays[day-1]=currB;
							currM=null;
							currD=null;
						}
						else{
							prevM.down=currM.down;
							thedays[day-1]=currD.right;
							currM=null;
							currD=null;
						}
					}
					else{
						prevM.down=currM.down;
						thedays[day-1]=currD.right;
						currM=null;
						currD=null;
					}
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getPriority()!=priority;prevB=currB,currB=currB.back);
					if(currB!=null){
						Item currBb=currB;
						for(;currBb!=null && currBb.getPriority()==priority;currBb=currBb.back);
						prevB.back=currBb;
					}
				}
			}

			else if(prevM==null && prevD==null){
				if(currM.getPriority()==priority){
					if(currM.back !=null){
						Item currB=currM;
						for(;currB!=null && currB.getPriority()==priority;currB=currB.back);
						if(currB!=null){
							currB.down=currM.down;
							currB.right=currD.right;
							themonths[validateMonth(month)]=currB;
							thedays[day-1]=currB;
							currM=null;
							currD=null;
						}
						else{
							themonths[validateMonth(month)]=currM.down;
							thedays[day-1]=currD.right;
							currM=null;
							currD=null;
						}
					}
					else{
						themonths[validateMonth(month)]=currM.down;
						thedays[day-1]=currD.right;
						currM=null;
						currD=null;
					}
				}
				else{
					Item currB=currM,prevB=null;
					for(;currB!=null && currB.getPriority()!=priority;prevB=currB,currB=currB.back);
					if(currB!=null){
						Item currBb=currB;
						for(;currBb!=null && currBb.getPriority()==priority;currBb=currBb.back);
						prevB.back=currBb;
					}
				}
			}
		}
	}
	
	public void deleteItems(int day, String month)
	{
		/*Delete all items at the given day and month combination.*/
		if(getItem(day,month)!=null){
			Item currM=themonths[validateMonth(month)],currD=thedays[day-1],prevM=null,prevD=null;
			for(;currM!=null && currM.theDay!=currD.theDay;prevM=currM,currM=currM.down);
			for(;currD!=null && validateMonth(currD.theMonth)!=validateMonth(month);prevD=currD,currD=currD.right);
			if(prevM!=null && prevD!=null){
				prevM.down=currM.down;
				prevD.right=currD.right;
				currD=null;
				currM=null;
			}
			else if(prevM==null && prevD!=null){
				themonths[validateMonth(month)]=currM.down;
				prevD.right=currD.right;
				currD=null;
				currM=null;
			}
			else if(prevM!=null && prevD==null){
				prevM.down=currM.down;
				thedays[day-1]=currD.right;
				currD=null;
				currM=null;
			}
			else{
				themonths[validateMonth(month)]=currM.down;
				thedays[day-1]=currD.right;
				currD=null;
				currM=null;
			}
		}			
	}
	
	/*Clearing Methods*/
	public void clearMonth(String month)
	{
		/*All items for the given month should be deleted.
		If the month has no Items, simply do nothing.*/
		if(themonths[validateMonth(month)]!=null){
			Item currM=themonths[validateMonth(month)],currD,prevD;
			for(;currM!=null;currM=currM.down){
				prevD=null;
				currD=thedays[currM.theDay-1];
				for(;currD!=null && validateMonth(currD.theMonth)!=validateMonth(month);prevD=currD,currD=currD.right);
				if(prevD==null){
					thedays[currM.theDay-1]=currD.right;
					currD=null;
				}
				else{
					prevD.right=currD.right;
					currD=null;
				}
			}
		}
	}
	
	public void clearDay(int day)
	{
		/*All items for the given day should be deleted.
		If the day has no Items, simply do nothing.*/
		if(thedays[day-1]!=null){
			Item currD=thedays[day-1],currM,prevM;
			for(;currD!=null;currD=currD.right){
				prevM=null;
				currM=themonths[validateMonth(currD.theMonth)];
				for(;currM!=null && currM.theDay!=currD.theDay;prevM=currM,currM=currM.down);
				if(prevM==null){
					themonths[validateMonth(currD.theMonth)]=currM.down;
					currM=null;
				}
				else{
					prevM.down=currM.down;
					currM=null;
				}
			}
		}
	}
	
	public void clearYear()
	{
		/*Delete all Items from the calendar.*/
		int i=0;
		int j=0;
		while(i<12 || j<31){
			if(i<12){
				themonths[i]=null;
			}
			if(j<31){
				thedays[i]=null;
			}
			i++;
			j++;
		}
	}
	
	
	/*Query methods*/
	public Item getItem(int day, String month)
	{
		/*Return the head Item of the day and month. If no such Item exists, return null*/
		Item curr=thedays[day-1];
		for(;curr!=null && validateMonth(curr.theMonth)!=validateMonth(month);curr=curr.right);
		if(curr!=null)return curr;
		
		return null;
	}
	
	public Item getMonthItem(String month)
	{
		/*Return the head Item for the month passed as a parameter.
		If no such Item exists, return null*/
		if(themonths[validateMonth(month)]!=null){
			return themonths[validateMonth(month)];
		}
		
		return null;
	}
	
	public Item getDayItem(int day)
	{
		/*Return the head Item for the day passed as a parameter.
		If no such Item exists, return null*/
		if(thedays[day-1]!=null){
			return thedays[day-1];
		}
		
		return null;
	}

	public int validateMonth(String mon){
		if(mon.toUpperCase().compareTo("JAN")==0){
			return 0;
		}
		else if(mon.toUpperCase().compareTo("FEB")==0){
			return 1;
		}
		else if(mon.toUpperCase().compareTo("MAR")==0){
			return 2;
		}
		else if(mon.toUpperCase().compareTo("APR")==0){
			return 3;
		}
		else if(mon.toUpperCase().compareTo("MAY")==0){
			return 4;
		}
		else if(mon.toUpperCase().compareTo("JUN")==0){
			return 5;
		}
		else if(mon.toUpperCase().compareTo("JUL")==0){
			return 6;
		}
		else if(mon.toUpperCase().compareTo("AUG")==0){
			return 7;
		}
		else if(mon.toUpperCase().compareTo("SEP")==0){
			return 8;
		}
		else if(mon.toUpperCase().compareTo("OCT")==0){
			return 9;
		}
		else if(mon.toUpperCase().compareTo("NOV")==0){
			return 10;
		}
		else if(mon.toUpperCase().compareTo("DEC")==0){
			return 11;
		}
		return 0;
	}	
	
}
