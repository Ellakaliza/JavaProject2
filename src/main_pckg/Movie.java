package main_pckg;
/*
 * Name(s) and ID(s) (Ange Akaliza 40270048)
	COMP249
	Assignment # (2)
	Due Date (March 27th 2024)
 */
import java.io.Serializable;

public class Movie implements Serializable{
	private int year,duration; 
	private String title,genres,rating,director,actor_1, actor_2, actor_3;
	private double score;
	public Movie(int year,int duration,String title,String genres,String rating,String director,String actor_1,String actor_2,String actor_3,double score) {
		this.setYear(year);
		this.setDuration(duration);
		this.setTitle(title);
		this.setGenres(genres);
		this.setRating(rating);
		this.setDirector(director);
		this.setActor_1(actor_1);
		this.setActor_2(actor_2);
		this.setActor_3(actor_3);
		this.setScore(score);
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor_1() {
		return actor_1;
	}
	public void setActor_1(String actor_1) {
		this.actor_1 = actor_1;
	}
	public String getActor_2() {
		return actor_2;
	}
	public void setActor_2(String actor_2) {
		this.actor_2 = actor_2;
	}
	public String getActor_3() {
		return actor_3;
	}
	public void setActor_3(String actor_3) {
		this.actor_3 = actor_3;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public boolean equals(Object object) {
		if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        Movie othermovie = (Movie) object;

        if (this.year==othermovie.year&&this.duration==othermovie.duration&&this.title.equals(othermovie.title)&&this.director.equals(othermovie.director)) {
            return true;
        } else {
            return false;
        }
	}
	public String toString() {
		return year+","+title+","+duration+","+genres+","+rating+","+score+","+director+","+actor_1+","+actor_2+","+actor_3+",";
	}
}
