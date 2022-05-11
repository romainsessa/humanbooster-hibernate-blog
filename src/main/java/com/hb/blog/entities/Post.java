package com.hb.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Integer id;

	@NotNull
	@Column(nullable = false)
	private String title;

	@OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
	private PostDetails details;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostComment> comments = new ArrayList<PostComment>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags = new ArrayList<Tag>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PostDetails getDetails() {
		return details;
	}

	public void setDetails(PostDetails details) {
		this.details = details;
		details.setPost(this);
	}

	public List<PostComment> getComments() {
		return comments;
	}

	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Post() {
		// TODO Auto-generated constructor stub
	}

	public Post(Integer id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public void removeDetails(PostDetails details) {
		this.details = null;
		details.setPost(null);
	}

	public void addComment(PostComment comment) {
		this.comments.add(comment);
		comment.setPost(this);
	}

	public void removeComment(PostComment comment) {
		this.comments.remove(comment);
		comment.setPost(null);
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.getPosts().add(this);
	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);
		tag.getPosts().remove(this);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", details=" + details + ", comments=" + comments + ", tags="
				+ tags + "]";
	}

}
