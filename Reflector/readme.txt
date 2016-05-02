Reflector Project
Turned in 2 May 2016
Project for Data Structures and Elementary Algorithms

James Fulford
james.patrick.fulford @ gmail.com

The contents of this directory, unless otherwise specified, are 
copyright 2016 James Fulford. Please see license file.


========================= INFORMATIONS =========================

General Explanation Jing: http://screencast.com/t/1t5XPOfo9E

Packages:
	commons
	listsandsorts (listsandsorts.java --> Big O experiment)
	reflector (MAIN.java)

Shortcut of awesomeness in NetBeans:
	ctrl + shift + b ==> opens source code of token selected


========================= REQUIREMENTS =========================

Week 1: Building a Reflector
- 2D Array of characters
	=> Deflector.java
		>reflector package
- Border of '*'
	=> "aCursorInstance.border('*', aDeflector)"
		>reflector package



Week 2: Dividing up a Reflector
- Separate areas
	=> Partition.slaves
		>reflector package



Week 3: Levels
- Levels of tables for memory
	=> Pylon.java
		>reflector package



Week 4: Searching
- For a character
	=> "aPylonInstance.serialSearch(a)"
		>reflector package
	=> "aPylonInstance.indexSearch(a)"
		>reflector package
- For a word in a file
	=> "aPylonInstance.allocationSearch(s)"
		>reflector package



Week 5: Writing to Memory
- A file
	=> Allocation (see constructor)
		>reflector package
- Managing files
	=> AllocationList.java
		>reflector package
	=> aPylonInstance.philes
		>reflector package



Week 6: Parsing
- Lines in a file
	=> "aStringListInstance.extractLines(file)"
		>listsandsorts package
- Words in a file
	=> "aStringListInstance.extractWords(file)"
		>listsandsorts package

//To specify a new line, use (char) 182, pilcrow sign: ¶
- Words in a String
	=> "StringList.parseByWord(s)"
		>listsandsorts package
- Lines in a String (delimited by ¶)
	=> "StringList.parseByLine(s)"
		>listsandsorts package
- Character Stack
	=> CharList.java //use only push() and pop(), and any list
			 //can act like a stack.
		>listsandsorts package



Week 7: Lists
- Dynamic Arrays
	=> AList.java //abstraction of lists
		>listsandsorts package
- Unique Lists
	=> "anAListSubclassInstance.uniqueize()"
		>listsandsorts package



Week 8: Hashing
- Make a hashtable
	=> "anAListSubclassInstance.uniqueize(); anAListSubclassInstance.frequencySort()"
		>listsandsorts package
- Hash objects, get integers
	=> "anAListSubclassInstance.findPutIn(Object a)" //adds a to this list if a isn't already in list.
		>listsandsorts package
- Hash integers, get objects
	=> "anAListSubclassInstance.lookUp(int v)"
		>listsandsorts package



Week 9 and 10: Sorting
- Bubble Sort
	=> "anAListSubclassInstance.bubbleSort(boolean leastToGreatest?)"
		>listsandsorts package
- Frequency Sort
	=> "anAListSubclassInstance.frequencySort(boolean leastToGreatest?)"
		>listsandsorts package
- Bin/Bucket Sort
	=> "aStringListInstance.bucketSort(boolean leastToGreatest?)"
		>listsandsorts package
- Big O graphs
	=> allSortOfRandomNumbers.xlsx
- Prime Number Experiment
	=> listsandsorts.java
		>listsandsorts package




