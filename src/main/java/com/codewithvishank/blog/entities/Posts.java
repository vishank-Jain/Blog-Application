package com.codewithvishank.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Posts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pId;
	private String content;
	private String title;
	private String imageName;
	private Date date;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name="CategoryId")
	@JsonIgnore
	private Category category;
	
	
	@OneToMany(mappedBy = "posts",cascade = CascadeType.ALL)
	private Set<Comment> comments=new HashSet<>(); 
	
	
}
