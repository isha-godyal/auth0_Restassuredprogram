package pojo;


// pojo class 
// Plain Old Java Object which contains private data members and public setter and getter methods for every data members.
// ref https://medium.com/@abdjabbar110/what-is-java-pojo-class-and-java-bean-class-with-example-e63c4c0811a9
public class GetCourse {
	private String url;
	private String services;
	private String expertise;
	private	Courses Courses;
	private String instructor;
	private String linkedIn;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public Courses getCourses() {
		return Courses;
	}
	public void setCourses(Courses courses) {
		Courses = courses;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
