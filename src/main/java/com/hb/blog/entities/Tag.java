package com.hb.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	private Integer id;

	@NotNull
	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "tags", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Post> posts = new ArrayList<Post>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Tag() {
		// TODO Auto-generated constructor stub
	}

	public Tag(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void addPost(Post post) {
		post.getTags().add(this);
		posts.add(post);
	}

	public void removePost(Post post) {
		post.getTags().remove(this);
		posts.remove(post);
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}

}
