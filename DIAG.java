public class DIAG {
	//just a collection of simple tools to use for diagnosing problems.
	
        public static void ln(){
            System.out.println();
        }
        public static void ln(int skip){
            if(skip > 0)
            for(int i = 0; i < skip; i++)
                ln();
        }
    
                //      PING TOOLS
	public static void ping(){
		System.out.println("Ping!");
	}
	public static void ping(int a){
		System.out.println("Ping! Ln " + a);
	}
	
	
	public static void pong(){
		System.out.println("Pong!");
	}
	public static void pong(int a){
		System.out.println("Pong! Ln " + a);
	}
	
	
	public static void pang(){
		System.out.println("Pang!");
	}
	public static void pang(int a){
		System.out.println("Pang! Ln " + a);
	}

	
	public static void peng(){
		System.out.println("Pong!");
	}
	public static void peng(int a){
		System.out.println("Pong! Ln " + a);
	}
	
	
	public static void pung(){
		System.out.println("Pong!");
	}
	public static void pung(int a){
		System.out.println("Pong! Ln " + a);
	}
	
	
        
		//      VAL statements
	
	public static void val(boolean bool){
		if(bool) System.out.println("is true!");
		else System.out.println("is FALSE!");
	}
	public static void val(boolean bool, String name){
		if(bool) System.out.println(name + " is true!");
		else System.out.println(name + " is FALSE!");
	}
	
	public static void val(int inte, String name){
		System.out.println(name + " is " + inte);
	}
	public static void val(int inte){
		System.out.println("is " + inte);
	}
	
	public static void val(char chara, String name){
		System.out.println(name + " is " + chara);		
	}
	public static void val(char chara){
		System.out.println("is " + chara);		
	}
	
	public static void val(char[] chararray, String name){
		System.out.println(name + " is:");
		for(int i = 0; i<chararray.length; i++)
			System.out.print(chararray[i] + ",  ");
	}
	public static void val(char[] chararray){
		System.out.println("is:");
		for(int i = 0; i<chararray.length; i++)
			System.out.print(chararray[i] + ",  ");
	}
        
        public static void val(int[] intarray){
		System.out.println("is:");
		for(int i = 0; i<intarray.length; i++)
			System.out.print(intarray[i] + ",  ");
	}        
        public static void val(int[] intarray, String name){
		System.out.println(name + " is:");
		for(int i = 0; i<intarray.length; i++)
			System.out.print(intarray[i] + ",  ");
	}
	
	public static void val(char[][] chartable, String name){
		System.out.println(name + " is:");
		for(int i=0; i<chartable.length; i++){
			for(int j=0; j<chartable[i].length; j++)
				System.out.print(chartable[i][j] + ", ");
			System.out.println("");
		}
	}
	public static void val(char[][] chartable){
		System.out.println("is:");
		for(int i=0; i<chartable.length; i++){
			for(int j=0; j<chartable[i].length; j++)
				System.out.print(chartable[i][j] + ", ");
			System.out.println("");
		}
	}
        
        public static void val(long[] longIsland){
            System.out.println();
            for(int i = 0; i<longIsland.length; i++)
                System.out.print(" " + longIsland[i] + " ");
        }
        
        public static void val(Object word){
            System.out.print((String)word);
        }
        public static void val(Object[] as){
            
            for(int i = 0; i < as.length; i++){
                val((String) as[i] + ", ");
            }
            System.out.println();
        }
        
        public static long avg(long[] longIsland){
        long average = 0;
            for(int i=0; i<longIsland.length; i++)
                average += longIsland[i];
            return average/longIsland.length;
        }

} //end DIAG class
