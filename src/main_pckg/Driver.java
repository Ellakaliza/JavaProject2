package main_pckg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import exceptions_a2.*;
/*
 * Name(s) and ID(s) (Ange Akaliza 40270048)
	COMP249
	Assignment # (2)
	Due Date (March 27th 2024)
 */
public class Driver {

	public static void main(String[] args) {
		// part 1’s manifest file
		String part1_manifest = "part1_manifest.txt";
		
		// part 2’s manifest file
		String part2_manifest = do_part1(part1_manifest);
		
		// part 3’s manifest file
		String part3_manifest = do_part2(part2_manifest);
		Movie[][] display=do_part3(part3_manifest);
		/*for(int j=0; j<display.length;j++) {
			for(int i=0;i<display[j].length;i++) {
				System.out.print(display[j][i]+" | ");
			}
			System.out.println();
		}*/
		movieChoice(do_part3(part3_manifest));
		return;
	}

	private static String do_part1(String part1_manifest) {
		//create array so that it is easier for me to access the csv files in the manifest file
				String[] p1_manifest_array = null;
				BufferedReader read1;
				try {
					read1=new BufferedReader(new FileReader(part1_manifest));
					String line1;
					int index_p1_a=0;
					//counting how many lines are in the file to determine the index of the array i want to create
					while((line1=read1.readLine())!=null) {
						index_p1_a++;
					}
					read1.close();
					read1=new BufferedReader(new FileReader(part1_manifest));
					p1_manifest_array=new String[index_p1_a];
					for(int i=0;i<p1_manifest_array.length;i++) {
						p1_manifest_array[i]=read1.readLine();	
					}
					read1.close();
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				String[] genre= {"Musical.csv", "Comedy.csv", "Animation.csv", "Adventure.csv", "Drama.csv", "Crime.csv", "Biography.csv",
						"Horror.csv", "Action.csv", "Documentary.csv", "Fantasy.csv", "Mystery.csv", "Sci-fi.csv", "Family.csv", "Romance.csv",
						"Thriller.csv","Western.csv"};
				for(int i=0;i<genre.length;i++) {
					fileOpener(genre[i]);
				}
				fileOpener("bad_movie_records.txt");
				PrintWriter pt2_man;
				try {
					pt2_man = new PrintWriter(new FileOutputStream("part2_manifest.txt"));
					for(int i=0;i<genre.length;i++) {
						pt2_man.println(genre[i]);
					}
					pt2_man.close();	
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BufferedReader read0;
				//creating an array that contains movies  for each csv file to manipulate the data more easily 
				String[]current_year_movies=null;
				for(int i=0;i<p1_manifest_array.length;i++) {
				
					try {	
						read0=new BufferedReader(new FileReader(p1_manifest_array[i]));
						String line0;
						int index_movie=0;
						while((line0=read0.readLine())!=null) {
							index_movie++;
						}
						read0.close();
						read0=new BufferedReader(new FileReader(p1_manifest_array[i]));
						current_year_movies=new String[index_movie];
						for(int j=0; j<current_year_movies.length;j++) {
							current_year_movies[j]=read0.readLine();
						}
						read0.close();			
						} 
					catch (FileNotFoundException e) {
						e.printStackTrace();
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
					for(int j=0; j<current_year_movies.length;j++) {
						String genre_file=null;
						boolean is_bad_movie=false;
						String error_type=null;
						String error=null;
						try {
							
							genre_file=Excptn_hndlng(current_year_movies[j]);
						} catch (NumberFormatException e) {
							is_bad_movie=true;
							error="invalid score";
							error_type="syntax";
							//e.printStackTrace();
						} catch (MissingQuotesException e) {
							is_bad_movie=true;
							error="missing quotes";
							error_type="syntax";
							//e.printStackTrace();
						} catch (ExcessFieldsException e) {
							error="excess fields";
							is_bad_movie=true;
							error_type="syntax";
							//e.printStackTrace();
						} catch (MissingFieldsException e) {
							is_bad_movie=true;
							error="missing fields";
							error_type="syntax";
							//e.printStackTrace();
						} catch (BadYearException e) {
							error="invalid year";
							is_bad_movie=true;
							error_type="semantic";
							//e.printStackTrace();
						} catch (BadDurationException e) {
							error="invalid duration";
							is_bad_movie=true;
							error_type="semantic";
							//e.printStackTrace();
						} catch (BadGenreException e) {
							error="invalid genre";
							is_bad_movie=true;
							error_type="semantic";
							//e.printStackTrace();
						} catch (BadRatingException e) {
							error="invalid rating";
							is_bad_movie=true;
							error_type="semantic";
							//e.printStackTrace();
						} catch (BadScoreException e) {
							is_bad_movie=true;
							error="invalid score";
							error_type="semantic";
							//e.printStackTrace();
						}
						if(is_bad_movie) {
							PrintWriter bad_movies;
							try {
								bad_movies = new PrintWriter(new FileOutputStream("bad_movie_records.txt",true));
								bad_movies.println(
										  "(a) Error and type: "+error+", "+error_type+"\r\n"
										+ "(b) "+current_year_movies[j]+"\r\n"
										+ "(c) "+p1_manifest_array[i]+"\r\n"
										+ "(d) Line number: "+(j+1)+"\r\n"
										+ "*****************************************************");
								bad_movies.close();	
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						else {
							PrintWriter valid_movie;
							try {
								valid_movie = new PrintWriter(new FileOutputStream(genre_file,true));
								valid_movie.println(current_year_movies[j]);
								valid_movie.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
		return "part2_manifest.txt";
	}

	private static Movie[][] do_part3(String part3_manifest) {
		
		Movie[][] all_movies=null;
		String[] manifest3_arr=null;
		int index_man3=0;
		BufferedReader pt3;
		try {
			pt3= new BufferedReader(new FileReader(part3_manifest));
			index_man3=indexCounter(pt3);
			pt3.close();
			manifest3_arr=new String[index_man3];
			pt3= new BufferedReader(new FileReader(part3_manifest));
			for(int i=0;i<manifest3_arr.length;i++) {
				manifest3_arr[i]=pt3.readLine();
			}
			pt3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		all_movies=new Movie[index_man3][];
		for(int i=0;i<manifest3_arr.length;i++) {
			ObjectInputStream bin_reader;
			int index_binfile=0;
			Movie[] temp_arr=null;
			try {
				bin_reader= new ObjectInputStream(new FileInputStream(manifest3_arr[i]));
				boolean bool=true;
				while(bool) {
					try {
						bin_reader.readObject();
						index_binfile++;
					}
					catch(EOFException e) {
						bool=false;
					}
				}
				bin_reader.close();
				temp_arr=new Movie[index_binfile];
				bin_reader= new ObjectInputStream(new FileInputStream(manifest3_arr[i]));
				bool=true;
				int b=0;
				while(bool) {
					try {
						temp_arr[b]=(Movie) bin_reader.readObject();
						b++;
					}
					catch(EOFException e) {
						bool=false;
					}
				}
				bin_reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			all_movies[i]=new Movie[index_binfile];
			for(int j=0;j<all_movies[i].length;j++) {
				all_movies[i][j]=temp_arr[j];
			}
		}
		/*for(int i=0;i<all_movies.length;i++) {
			for(int j=0;j<all_movies[i].length;j++) {
				System.out.println(all_movies[i][j]);
			}
			System.out.println("********************************************");
			System.out.println("new category");
			System.out.println("*********************************************");
		}*/
		return all_movies;
	}

	private static String do_part2(String inputfilename) {
		String part3_manifest_file="part3_manifest.txt";
		BufferedReader manifest2_reader;
		String[] manifest2_array=null;
		try {
			manifest2_reader=new BufferedReader(new FileReader(inputfilename));
			int index_m2=indexCounter(manifest2_reader);
			manifest2_reader.close();
			manifest2_array=new String[index_m2];
			manifest2_reader=new BufferedReader(new FileReader(inputfilename));
			for(int i=0; i<manifest2_array.length;i++) {
				manifest2_array[i]=manifest2_reader.readLine();
			}
			manifest2_reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		fileOpener(part3_manifest_file) ;
		for(int i=0; i<manifest2_array.length;i++) {
			String ser_filename=manifest2_array[i].substring(0, manifest2_array[i].length()-3)+"ser";
			BufferedReader movie;
			Movie[] movies=null;
			String[] movies_string=null;
			int index_movie=0;
			try {
				movie=new BufferedReader(new FileReader(manifest2_array[i]));
				index_movie=indexCounter(movie);
				movie.close();
				movies=new Movie[index_movie];
				movies_string=new String[index_movie];
				movie=new BufferedReader(new FileReader(manifest2_array[i]));
				for(int j=0;j<movies_string.length;j++) {
					movies_string[j]=movie.readLine();
				}
				movie.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int j=0;j<movies_string.length;j++) {
				int year=0,duration=0; 
				String title=null,genres=null,rating=null,director=null,actor_1=null, actor_2=null, actor_3=null;
				double score=0;
				int quotes=quotesCounter(movies_string[j]);
				if(quotes!=0) {
					StringTokenizer st1= new StringTokenizer(movies_string[j],"\"");
					String holder=st1.nextToken();
					year=Integer.parseInt(holder.substring(0,holder.length()-1));
					title=st1.nextToken();
					StringTokenizer st2=new StringTokenizer(st1.nextToken(),",");
					duration=Integer.parseInt(st2.nextToken());
					genres=st2.nextToken();
					rating=st2.nextToken();
					score=Double.parseDouble(st2.nextToken());
					director=st2.nextToken();
					actor_1=st2.nextToken();
					actor_2=st2.nextToken();
					actor_3=st2.nextToken();
				}
				else {
					StringTokenizer st3= new StringTokenizer(movies_string[j],",");
					year=Integer.parseInt(st3.nextToken());
					title=st3.nextToken();
					duration=Integer.parseInt(st3.nextToken());
					genres=st3.nextToken();
					rating=st3.nextToken();
					score=Double.parseDouble(st3.nextToken());
					director=st3.nextToken();
					actor_1=st3.nextToken();
					actor_2=st3.nextToken();
					actor_3=st3.nextToken();
				}
				movies[j]=new Movie(year, duration,title,genres,rating,director,actor_1,actor_2, actor_3,score);
			}
			ObjectOutputStream bin_file;
			try {
				bin_file=new ObjectOutputStream(new FileOutputStream(ser_filename));
				//bin_file.writeObject(movies[0]);
				//bin_file.close();
				//bin_file=new ObjectOutputStream(new FileOutputStream(ser_filename,true));
				for(int j=0;j<movies.length;j++) {
					bin_file.writeObject(movies[j]);
				}
				bin_file.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			PrintWriter manifest3;
			try {
				manifest3=new PrintWriter(new FileWriter(part3_manifest_file,true));
				manifest3.println(ser_filename);
				manifest3.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return part3_manifest_file;
	}
	
	public static String Excptn_hndlng(String movie) throws MissingQuotesException, ExcessFieldsException, MissingFieldsException, BadYearException, BadDurationException, BadGenreException, BadRatingException, BadScoreException,NumberFormatException {
		String[] movie_partition=new String[10];
		StringTokenizer st0=new StringTokenizer(movie,",");
		int n=st0.countTokens();
		
		
		int index;
		index = movie.indexOf("\"");
		
		int quotes=0;
		while (index >= 0) {
		    index = movie.indexOf("\"", index + 1);
		    quotes++;
		}
		
		
		if(quotes==1)
			throw new MissingQuotesException();
		
		if(n!=10) {
			if(n>10) {
				if(quotes==0)
					throw new ExcessFieldsException();
				else {
					StringTokenizer st1= new StringTokenizer(movie,"\"");
					String holder=st1.nextToken();
					movie_partition[0]=holder.substring(0,holder.length()-1);
					movie_partition[1]=st1.nextToken();
					StringTokenizer st2=new StringTokenizer(st1.nextToken(),",");
					for(int i=2;i<movie_partition.length;i++) {
						movie_partition[i]=st2.nextToken();
					}
				}
			}
			else if(n<10) {
				throw new MissingFieldsException();
			}
		}
		if(quotes!=1&&n==10) {
			for(int i=0;i<movie_partition.length;i++) {
				movie_partition[i]=st0.nextToken();
			}
		}
		int year=Integer.parseInt(movie_partition[0]);
		int duration=Integer.parseInt(movie_partition[2]);
		String genre=movie_partition[3];
		String rating=movie_partition[4];
		double score=Double.parseDouble(movie_partition[5]);
		if(year<1990||year>1999)
			throw new BadYearException();
		if (duration<30||duration>300)
			throw new BadDurationException();
		if(!(genre.equalsIgnoreCase("Musical")||genre.equalsIgnoreCase("Comedy")||genre.equalsIgnoreCase("Animation")||genre.equalsIgnoreCase("Adventure")
			||genre.equalsIgnoreCase("Drama")||genre.equalsIgnoreCase("Crime")||genre.equalsIgnoreCase("Biography")||genre.equalsIgnoreCase("Horror")
			||genre.equalsIgnoreCase("Action")||genre.equalsIgnoreCase("Documentary")||genre.equalsIgnoreCase("Fantasy")||genre.equalsIgnoreCase("Mystery")
			||genre.equalsIgnoreCase("Sci-fi")||genre.equalsIgnoreCase("Family")||genre.equalsIgnoreCase("Romance")||genre.equalsIgnoreCase("Thriller")||genre.equalsIgnoreCase("Western")))
			throw new BadGenreException();
		if(!(rating.equals("PG")||rating.equals("Unrated")||rating.equals("G")||rating.equals("PG-13")||rating.equals("R")||rating.equals("NC-17")))
			throw new BadRatingException();
		if(score<0||score>10)
			throw new BadScoreException();
		
		String genre_file=genre+".csv";
		return genre_file;
	}
	
	public static int indexCounter(BufferedReader read) {
		@SuppressWarnings("unused")
		String line;
		int index=0;
		try {
			while((line=read.readLine())!=null) {
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return index;
	}
	
	public static int quotesCounter(String string) {
		int index;
		index = string.indexOf("\"");
		int quotes=0;
		while (index >= 0) {
		    index = string.indexOf("\"", index + 1);
		    quotes++;
		}
		return quotes;
	}
	
	public static void fileOpener(String file) {
		BufferedWriter write;
		try {
			write=new BufferedWriter(new FileWriter(file));
			write.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void movieChoice(Movie[][]all_movies) {
		Scanner scan=new Scanner(System.in);
		BufferedReader part3_manifest;
		int index_counter=0;
		String[] movie_genres=null;
		try {
			part3_manifest=new BufferedReader(new FileReader("part3_manifest.txt"));
			index_counter=indexCounter(part3_manifest);
			part3_manifest.close();
			movie_genres=new String[index_counter];
			part3_manifest=new BufferedReader(new FileReader("part3_manifest.txt"));
			for(int i=0;i<movie_genres.length;i++) {
				String holder=part3_manifest.readLine();
				movie_genres[i]=holder.substring(0, holder.length()-4);
			}
			part3_manifest.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String choice="";
		int proper_index=0;
		do {
		choice=mainMenu(movie_genres,all_movies,proper_index);
			while(choice.equals("s")) {
				int genre_choice= genreSubmenu(movie_genres,all_movies);
				if(genre_choice<movie_genres.length+1) {
					proper_index=genre_choice-1;
					choice=mainMenu(movie_genres,all_movies, proper_index);
				}
				else
					break;
			}
			if(choice.equalsIgnoreCase("n")) {
				int n;
				if(all_movies[proper_index].length==0) {
					System.out.println("There are no movies to display in this category.");
				}
				else {
					Movie current_position=all_movies[proper_index][0];
					do {
						System.out.println("Navigating "+ movie_genres[proper_index] +" movies ("+all_movies[proper_index].length+")\r\n"
								+ "Enter Your Choice: ");
						n=scan.nextInt();
						int column_index=0,row_index=0;
						for(int i=0;i<all_movies.length;i++) {
							for(int j=0;j<all_movies[i].length;j++) {
								if(all_movies[i][j].equals(current_position))
								{column_index=j;row_index=i;}
							}
						}
						int index_to_display=Math.abs(n)-1;
						if(n>0) {
							if((row_index+index_to_display)>=all_movies.length) {
								System.out.println("EOF has been reached");
								for(int i=row_index;i<(all_movies.length);i++) {
									if(all_movies[i].length!=0) {
										System.out.println(all_movies[i][column_index]);
										current_position=all_movies[i][column_index];
									}
								}
								System.out.println();
							}
							else {
								for(int i=row_index;i<=(index_to_display+row_index);i++) {
									if(all_movies[i].length!=0) {
										System.out.println(all_movies[i][column_index]);
										current_position=all_movies[i][column_index];
									}
								}
								System.out.println();
							}
							
						}
						else if(n<0) {
							if((row_index-index_to_display)<0) {
								System.out.println("BOF has been reached");
								for(int i=0;i<=row_index;i++) {
									if(all_movies[i].length!=0)
										System.out.println(all_movies[i][column_index]);
								}
								System.out.println();
								for(int i=row_index;i>=0;i--) {
									if(all_movies[i].length!=0)
										current_position=all_movies[i][column_index];
								}
							}
							else {
								for(int i=row_index-index_to_display;i<=(row_index);i++) {
									if(all_movies[i].length!=0)
										System.out.println(all_movies[i][column_index]);
								}
								System.out.println();
								for(int i=row_index;i>=(row_index-index_to_display);i--) {
									if(all_movies[i].length!=0)
										current_position=all_movies[i][column_index];
								}
								
							}
						}
					}
					while(n!=0);
				}
			}
		}
		while (!(choice.equalsIgnoreCase("x")));
		scan.close();
	}
	public static String mainMenu(String[] movie,Movie[][]movies,int index) {
		System.out.print("-----------------------------\r\n"
				+ "Main Menu\r\n"
				+ "-----------------------------\r\n"
				+ "s Select a movie array to navigate\r\n"
				+ "n Navigate "+ movie[index] +" movies ("+movies[index].length+" records)\r\n"
				+ "x Exit\r\n"
				+ "-----------------------------\r\n"
				+ "Enter Your Choice: ");
		Scanner scan=new Scanner(System.in);
		System.out.println();
		String choice=scan.next();
		return choice;
	}
	public static int genreSubmenu(String[] movie,Movie[][]movies) {
		System.out.println("------------------------------\r\n"
				+ "Genre Sub-Menu\r\n"
				+ "------------------------------");
		for(int i=0; i<movie.length;i++) {
			if(i==4||i==5)
				System.out.println((i+1)+" "+movie[i]+"					"+"("+movies[i].length+" movies)");
			else
				System.out.println((i+1)+" "+movie[i]+"				"+"("+movies[i].length+" movies)");
		}
		System.out.print((movie.length+1)+ " Exit\r\n"
				+ "------------------------------\r\n"
				+ "Enter Your Choice: ");
		Scanner scan=new Scanner(System.in);
		System.out.println();
		int choice=scan.nextInt();
		return choice;
	}
}

