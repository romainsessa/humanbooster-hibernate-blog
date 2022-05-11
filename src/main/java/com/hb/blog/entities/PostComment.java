package com.hb.blog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "comment")
public class PostComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Integer id;
	
	@NotNull
	@Column(nullable = false)
	private String review;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	public PostComment() {
		// TODO Auto-generated constructor stub
	}

	public PostComment(Integer id, String review) {
		super();
		this.id = id;
		this.review = review;
	}

	@Override
	public String toString() {
		return "PostComment [id=" + id + ", review=" + review + "]";
	}
	
	

}
